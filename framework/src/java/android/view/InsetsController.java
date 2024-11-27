package android.view;

/* loaded from: classes4.dex */
public class InsetsController implements android.view.WindowInsetsController, android.view.InsetsAnimationControlCallbacks {
    private static final int ANIMATION_DELAY_DIM_MS = 500;
    private static final int ANIMATION_DURATION_FADE_IN_MS = 500;
    private static final int ANIMATION_DURATION_FADE_OUT_MS = 1500;
    private static final int ANIMATION_DURATION_MOVE_IN_MS = 275;
    private static final int ANIMATION_DURATION_MOVE_OUT_MS = 340;
    public static final int ANIMATION_DURATION_RESIZE = 300;
    private static final int ANIMATION_DURATION_SYNC_IME_MS = 285;
    private static final int ANIMATION_DURATION_UNSYNC_IME_MS = 200;
    public static final int ANIMATION_TYPE_HIDE = 1;
    public static final int ANIMATION_TYPE_NONE = -1;
    public static final int ANIMATION_TYPE_RESIZE = 3;
    public static final int ANIMATION_TYPE_SHOW = 0;
    public static final int ANIMATION_TYPE_USER = 2;
    static final boolean DEBUG = false;
    private static final int FLOATING_IME_BOTTOM_INSET_DP = -80;
    public static final int LAYOUT_INSETS_DURING_ANIMATION_HIDDEN = 1;
    public static final int LAYOUT_INSETS_DURING_ANIMATION_SHOWN = 0;
    private static final int PENDING_CONTROL_TIMEOUT_MS = 2000;
    private static final java.lang.String TAG = "InsetsController";
    static final boolean WARN = false;
    private final java.lang.Runnable mAnimCallback;
    private boolean mAnimCallbackScheduled;
    private boolean mAnimationsDisabled;
    private int mCaptionInsetsHeight;
    private boolean mCompatSysUiVisibilityStaled;
    private final com.android.internal.util.function.TriFunction<android.view.InsetsController, java.lang.Integer, java.lang.Integer, android.view.InsetsSourceConsumer> mConsumerCreator;
    private final java.util.ArrayList<android.view.WindowInsetsController.OnControllableInsetsChangedListener> mControllableInsetsChangedListeners;
    private int mControllableTypes;
    private int mExistingTypes;
    private final android.graphics.Rect mFrame;
    private final android.os.Handler mHandler;
    private final android.view.InsetsController.Host mHost;
    private int mImeCaptionBarInsetsHeight;
    private final android.view.InsetsSourceConsumer mImeSourceConsumer;
    private final java.lang.Runnable mInvokeControllableInsetsChangedListeners;
    private final android.view.inputmethod.ImeTracker.InputMethodJankContext mJankContext;
    private int mLastActivityType;
    private final android.view.InsetsState mLastDispatchedState;
    private android.view.WindowInsets mLastInsets;
    private int mLastLegacySoftInputMode;
    private int mLastLegacySystemUiFlags;
    private int mLastLegacyWindowFlags;
    private int mLastStartedAnimTypes;
    private android.view.WindowInsetsAnimationControlListener mLoggingListener;
    private final java.lang.Runnable mPendingControlTimeout;
    private android.view.InsetsController.PendingControlRequest mPendingImeControlRequest;
    private final android.view.InsetsState.OnTraverseCallbacks mRemoveGoneSources;
    private int mReportedRequestedVisibleTypes;
    private int mRequestedVisibleTypes;
    private final java.util.ArrayList<android.view.InsetsController.RunningAnimation> mRunningAnimations;
    private final android.util.SparseArray<android.view.InsetsSourceConsumer> mSourceConsumers;
    private final android.view.InsetsState.OnTraverseCallbacks mStartResizingAnimationIfNeeded;
    private boolean mStartingAnimation;
    private final android.view.InsetsState mState;
    private final android.util.SparseArray<android.view.InsetsSourceControl> mTmpControlArray;
    private int mTypesBeingCancelled;
    private int mVisibleTypes;
    private int mWindowType;
    private static final android.view.animation.Interpolator SYSTEM_BARS_INSETS_INTERPOLATOR = new android.view.animation.PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    private static final android.view.animation.Interpolator SYSTEM_BARS_ALPHA_INTERPOLATOR = new android.view.animation.PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
    private static final android.view.animation.Interpolator SYSTEM_BARS_DIM_INTERPOLATOR = new android.view.animation.Interpolator() { // from class: android.view.InsetsController$$ExternalSyntheticLambda5
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return android.view.InsetsController.lambda$static$0(f);
        }
    };
    private static final android.view.animation.Interpolator SYNC_IME_INTERPOLATOR = new android.view.animation.PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
    private static final android.view.animation.Interpolator LINEAR_OUT_SLOW_IN_INTERPOLATOR = new android.view.animation.PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
    private static final android.view.animation.Interpolator FAST_OUT_LINEAR_IN_INTERPOLATOR = new android.view.animation.PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    public static final android.view.animation.Interpolator RESIZE_INTERPOLATOR = new android.view.animation.LinearInterpolator();
    private static final int ID_CAPTION_BAR = android.view.InsetsSource.createId(null, 0, android.view.WindowInsets.Type.captionBar());
    private static android.animation.TypeEvaluator<android.graphics.Insets> sEvaluator = new android.animation.TypeEvaluator() { // from class: android.view.InsetsController$$ExternalSyntheticLambda6
        @Override // android.animation.TypeEvaluator
        public final java.lang.Object evaluate(float f, java.lang.Object obj, java.lang.Object obj2) {
            android.graphics.Insets of;
            android.graphics.Insets insets = (android.graphics.Insets) obj;
            android.graphics.Insets insets2 = (android.graphics.Insets) obj2;
            of = android.graphics.Insets.of((int) (insets.left + ((insets2.left - insets.left) * f)), (int) (insets.top + ((insets2.top - insets.top) * f)), (int) (insets.right + ((insets2.right - insets.right) * f)), (int) (insets.bottom + (f * (insets2.bottom - insets.bottom))));
            return of;
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface LayoutInsetsDuringAnimation {
    }

    public interface Host {
        void addOnPreDrawRunnable(java.lang.Runnable runnable);

        void applySurfaceParams(android.view.SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr);

        int dipToPx(int i);

        void dispatchWindowInsetsAnimationEnd(android.view.WindowInsetsAnimation windowInsetsAnimation);

        void dispatchWindowInsetsAnimationPrepare(android.view.WindowInsetsAnimation windowInsetsAnimation);

        android.view.WindowInsets dispatchWindowInsetsAnimationProgress(android.view.WindowInsets windowInsets, java.util.List<android.view.WindowInsetsAnimation> list);

        android.view.WindowInsetsAnimation.Bounds dispatchWindowInsetsAnimationStart(android.view.WindowInsetsAnimation windowInsetsAnimation, android.view.WindowInsetsAnimation.Bounds bounds);

        android.os.Handler getHandler();

        android.view.inputmethod.InputMethodManager getInputMethodManager();

        java.lang.String getRootViewTitle();

        int getSystemBarsAppearance();

        int getSystemBarsBehavior();

        android.os.IBinder getWindowToken();

        boolean hasAnimationCallbacks();

        void notifyInsetsChanged();

        void postInsetsAnimationCallback(java.lang.Runnable runnable);

        void releaseSurfaceControlFromRt(android.view.SurfaceControl surfaceControl);

        void setSystemBarsAppearance(int i, int i2);

        void setSystemBarsBehavior(int i);

        void updateRequestedVisibleTypes(int i);

        default void updateCompatSysUiVisibility(int i, int i2, int i3) {
        }

        default boolean isSystemBarsAppearanceControlled() {
            return false;
        }

        default boolean isSystemBarsBehaviorControlled() {
            return false;
        }

        default android.content.Context getRootViewContext() {
            return null;
        }

        default android.content.res.CompatibilityInfo.Translator getTranslator() {
            return null;
        }

        default void notifyAnimationRunningStateChanged(boolean z) {
        }

        default boolean isHandlingPointerEvent() {
            return false;
        }
    }

    static /* synthetic */ float lambda$static$0(float f) {
        float f2 = 1.0f - f;
        if (f2 <= 0.33333334f) {
            return 1.0f;
        }
        return 1.0f - SYSTEM_BARS_ALPHA_INTERPOLATOR.getInterpolation((f2 - 0.33333334f) / 0.6666666f);
    }

    public static class InternalAnimationControlListener implements android.view.WindowInsetsAnimationControlListener {
        private android.animation.ValueAnimator mAnimator;
        private final int mBehavior;
        private android.view.WindowInsetsAnimationController mController;
        private final boolean mDisable;
        private final int mFloatingImeBottomInset;
        private final boolean mHasAnimationCallbacks;
        private final android.view.inputmethod.ImeTracker.InputMethodJankContext mInputMethodJankContext;
        private final android.view.WindowInsetsAnimationControlListener mLoggingListener;
        private final int mRequestedTypes;
        private final boolean mShow;
        private final java.lang.ThreadLocal<android.animation.AnimationHandler> mSfAnimationHandlerThreadLocal = new java.lang.ThreadLocal<android.animation.AnimationHandler>() { // from class: android.view.InsetsController.InternalAnimationControlListener.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.lang.ThreadLocal
            public android.animation.AnimationHandler initialValue() {
                android.animation.AnimationHandler animationHandler = new android.animation.AnimationHandler();
                animationHandler.setProvider(new com.android.internal.graphics.SfVsyncFrameCallbackProvider());
                return animationHandler;
            }
        };
        private final long mDurationMs = calculateDurationMs();

        public InternalAnimationControlListener(boolean z, boolean z2, int i, int i2, boolean z3, int i3, android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, android.view.inputmethod.ImeTracker.InputMethodJankContext inputMethodJankContext) {
            this.mShow = z;
            this.mHasAnimationCallbacks = z2;
            this.mRequestedTypes = i;
            this.mBehavior = i2;
            this.mDisable = z3;
            this.mFloatingImeBottomInset = i3;
            this.mLoggingListener = windowInsetsAnimationControlListener;
            this.mInputMethodJankContext = inputMethodJankContext;
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public void onReady(final android.view.WindowInsetsAnimationController windowInsetsAnimationController, int i) {
            final android.graphics.Insets shownStateInsets;
            final android.graphics.Insets insets;
            this.mController = windowInsetsAnimationController;
            if (this.mLoggingListener != null) {
                this.mLoggingListener.onReady(windowInsetsAnimationController, i);
            }
            if (this.mDisable) {
                onAnimationFinish();
                return;
            }
            this.mAnimator = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mAnimator.setDuration(this.mDurationMs);
            this.mAnimator.setInterpolator(new android.view.animation.LinearInterpolator());
            android.graphics.Insets hiddenStateInsets = windowInsetsAnimationController.getHiddenStateInsets();
            if (windowInsetsAnimationController.hasZeroInsetsIme()) {
                hiddenStateInsets = android.graphics.Insets.of(hiddenStateInsets.left, hiddenStateInsets.top, hiddenStateInsets.right, this.mFloatingImeBottomInset);
            }
            if (this.mShow) {
                shownStateInsets = hiddenStateInsets;
            } else {
                shownStateInsets = windowInsetsAnimationController.getShownStateInsets();
            }
            if (this.mShow) {
                insets = windowInsetsAnimationController.getShownStateInsets();
            } else {
                insets = hiddenStateInsets;
            }
            final android.view.animation.Interpolator insetsInterpolator = getInsetsInterpolator();
            final android.view.animation.Interpolator alphaInterpolator = getAlphaInterpolator();
            this.mAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                    android.view.InsetsController.InternalAnimationControlListener.this.lambda$onReady$0(insetsInterpolator, windowInsetsAnimationController, shownStateInsets, insets, alphaInterpolator, valueAnimator);
                }
            });
            this.mAnimator.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.view.InsetsController.InternalAnimationControlListener.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(android.animation.Animator animator) {
                    if (android.view.InsetsController.InternalAnimationControlListener.this.mInputMethodJankContext == null) {
                        return;
                    }
                    android.view.inputmethod.ImeTracker.forJank().onRequestAnimation(android.view.InsetsController.InternalAnimationControlListener.this.mInputMethodJankContext, android.view.InsetsController.InternalAnimationControlListener.this.getAnimationType(), !android.view.InsetsController.InternalAnimationControlListener.this.mHasAnimationCallbacks);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(android.animation.Animator animator) {
                    if (android.view.InsetsController.InternalAnimationControlListener.this.mInputMethodJankContext == null) {
                        return;
                    }
                    android.view.inputmethod.ImeTracker.forJank().onCancelAnimation(android.view.InsetsController.InternalAnimationControlListener.this.getAnimationType());
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(android.animation.Animator animator) {
                    android.view.InsetsController.InternalAnimationControlListener.this.onAnimationFinish();
                    if (android.view.InsetsController.InternalAnimationControlListener.this.mInputMethodJankContext == null) {
                        return;
                    }
                    android.view.inputmethod.ImeTracker.forJank().onFinishAnimation(android.view.InsetsController.InternalAnimationControlListener.this.getAnimationType());
                }
            });
            if (!this.mHasAnimationCallbacks) {
                this.mAnimator.setAnimationHandler(this.mSfAnimationHandlerThreadLocal.get());
            }
            this.mAnimator.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReady$0(android.view.animation.Interpolator interpolator, android.view.WindowInsetsAnimationController windowInsetsAnimationController, android.graphics.Insets insets, android.graphics.Insets insets2, android.view.animation.Interpolator interpolator2, android.animation.ValueAnimator valueAnimator) {
            float f;
            float animatedFraction = valueAnimator.getAnimatedFraction();
            if (this.mShow) {
                f = animatedFraction;
            } else {
                f = 1.0f - animatedFraction;
            }
            windowInsetsAnimationController.setInsetsAndAlpha((android.graphics.Insets) android.view.InsetsController.sEvaluator.evaluate(interpolator.getInterpolation(animatedFraction), insets, insets2), interpolator2.getInterpolation(f), animatedFraction);
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public void onFinished(android.view.WindowInsetsAnimationController windowInsetsAnimationController) {
            if (this.mLoggingListener != null) {
                this.mLoggingListener.onFinished(windowInsetsAnimationController);
            }
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public void onCancelled(android.view.WindowInsetsAnimationController windowInsetsAnimationController) {
            if (this.mAnimator != null) {
                this.mAnimator.cancel();
            }
            if (this.mLoggingListener != null) {
                this.mLoggingListener.onCancelled(windowInsetsAnimationController);
            }
        }

        protected android.view.animation.Interpolator getInsetsInterpolator() {
            if ((this.mRequestedTypes & android.view.WindowInsets.Type.ime()) != 0) {
                if (this.mHasAnimationCallbacks) {
                    return android.view.InsetsController.SYNC_IME_INTERPOLATOR;
                }
                if (this.mShow) {
                    return android.view.InsetsController.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
                }
                return android.view.InsetsController.FAST_OUT_LINEAR_IN_INTERPOLATOR;
            }
            if (this.mBehavior == 2) {
                return android.view.InsetsController.SYSTEM_BARS_INSETS_INTERPOLATOR;
            }
            return new android.view.animation.Interpolator() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda1
                @Override // android.animation.TimeInterpolator
                public final float getInterpolation(float f) {
                    float lambda$getInsetsInterpolator$1;
                    lambda$getInsetsInterpolator$1 = android.view.InsetsController.InternalAnimationControlListener.this.lambda$getInsetsInterpolator$1(f);
                    return lambda$getInsetsInterpolator$1;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ float lambda$getInsetsInterpolator$1(float f) {
            return this.mShow ? 1.0f : 0.0f;
        }

        android.view.animation.Interpolator getAlphaInterpolator() {
            if ((this.mRequestedTypes & android.view.WindowInsets.Type.ime()) != 0) {
                if (this.mHasAnimationCallbacks) {
                    return new android.view.animation.Interpolator() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda2
                        @Override // android.animation.TimeInterpolator
                        public final float getInterpolation(float f) {
                            return android.view.InsetsController.InternalAnimationControlListener.lambda$getAlphaInterpolator$2(f);
                        }
                    };
                }
                if (this.mShow) {
                    return new android.view.animation.Interpolator() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda3
                        @Override // android.animation.TimeInterpolator
                        public final float getInterpolation(float f) {
                            float min;
                            min = java.lang.Math.min(1.0f, f * 2.0f);
                            return min;
                        }
                    };
                }
                return android.view.InsetsController.FAST_OUT_LINEAR_IN_INTERPOLATOR;
            }
            if (this.mBehavior == 2) {
                return new android.view.animation.Interpolator() { // from class: android.view.InsetsController$InternalAnimationControlListener$$ExternalSyntheticLambda4
                    @Override // android.animation.TimeInterpolator
                    public final float getInterpolation(float f) {
                        return android.view.InsetsController.InternalAnimationControlListener.lambda$getAlphaInterpolator$4(f);
                    }
                };
            }
            if (this.mShow) {
                return android.view.InsetsController.SYSTEM_BARS_ALPHA_INTERPOLATOR;
            }
            return android.view.InsetsController.SYSTEM_BARS_DIM_INTERPOLATOR;
        }

        static /* synthetic */ float lambda$getAlphaInterpolator$2(float f) {
            return 1.0f;
        }

        static /* synthetic */ float lambda$getAlphaInterpolator$4(float f) {
            return 1.0f;
        }

        protected void onAnimationFinish() {
            this.mController.finish(this.mShow);
        }

        public long getDurationMs() {
            return this.mDurationMs;
        }

        private long calculateDurationMs() {
            if ((this.mRequestedTypes & android.view.WindowInsets.Type.ime()) == 0) {
                return this.mBehavior == 2 ? this.mShow ? 275L : 340L : this.mShow ? 500L : 1500L;
            }
            if (this.mHasAnimationCallbacks) {
                return 285L;
            }
            return 200L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getAnimationType() {
            return !this.mShow ? 1 : 0;
        }
    }

    private static class RunningAnimation {
        final android.view.InsetsAnimationControlRunner runner;
        boolean startDispatched;
        final int type;

        RunningAnimation(android.view.InsetsAnimationControlRunner insetsAnimationControlRunner, int i) {
            this.runner = insetsAnimationControlRunner;
            this.type = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class PendingControlRequest {
        final int animationType;
        final android.os.CancellationSignal cancellationSignal;
        final long durationMs;
        final android.view.animation.Interpolator interpolator;
        final int layoutInsetsDuringAnimation;
        final android.view.WindowInsetsAnimationControlListener listener;
        int types;
        final boolean useInsetsAnimationThread;

        PendingControlRequest(int i, android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, long j, android.view.animation.Interpolator interpolator, int i2, int i3, android.os.CancellationSignal cancellationSignal, boolean z) {
            this.types = i;
            this.listener = windowInsetsAnimationControlListener;
            this.durationMs = j;
            this.interpolator = interpolator;
            this.animationType = i2;
            this.layoutInsetsDuringAnimation = i3;
            this.cancellationSignal = cancellationSignal;
            this.useInsetsAnimationThread = z;
        }
    }

    public InsetsController(android.view.InsetsController.Host host) {
        this(host, new com.android.internal.util.function.TriFunction() { // from class: android.view.InsetsController$$ExternalSyntheticLambda1
            @Override // com.android.internal.util.function.TriFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                return android.view.InsetsController.lambda$new$2((android.view.InsetsController) obj, (java.lang.Integer) obj2, (java.lang.Integer) obj3);
            }
        }, host.getHandler());
    }

    static /* synthetic */ android.view.InsetsSourceConsumer lambda$new$2(android.view.InsetsController insetsController, java.lang.Integer num, java.lang.Integer num2) {
        if (num2.intValue() == android.view.WindowInsets.Type.ime()) {
            return new android.view.ImeInsetsSourceConsumer(num.intValue(), insetsController.mState, new android.view.InsetsController$$ExternalSyntheticLambda12(), insetsController);
        }
        return new android.view.InsetsSourceConsumer(num.intValue(), num2.intValue(), insetsController.mState, new android.view.InsetsController$$ExternalSyntheticLambda12(), insetsController);
    }

    public InsetsController(android.view.InsetsController.Host host, com.android.internal.util.function.TriFunction<android.view.InsetsController, java.lang.Integer, java.lang.Integer, android.view.InsetsSourceConsumer> triFunction, android.os.Handler handler) {
        this.mJankContext = new android.view.inputmethod.ImeTracker.InputMethodJankContext() { // from class: android.view.InsetsController.1
            @Override // android.view.inputmethod.ImeTracker.InputMethodJankContext
            public android.content.Context getDisplayContext() {
                if (android.view.InsetsController.this.mHost != null) {
                    return android.view.InsetsController.this.mHost.getRootViewContext();
                }
                return null;
            }

            @Override // android.view.inputmethod.ImeTracker.InputMethodJankContext
            public android.view.SurfaceControl getTargetSurfaceControl() {
                android.view.InsetsSourceControl control = android.view.InsetsController.this.getImeSourceConsumer().getControl();
                if (control != null) {
                    return control.getLeash();
                }
                return null;
            }

            @Override // android.view.inputmethod.ImeTracker.InputMethodJankContext
            public java.lang.String getHostPackageName() {
                if (android.view.InsetsController.this.mHost != null) {
                    return android.view.InsetsController.this.mHost.getRootViewContext().getPackageName();
                }
                return null;
            }
        };
        this.mState = new android.view.InsetsState();
        this.mLastDispatchedState = new android.view.InsetsState();
        this.mFrame = new android.graphics.Rect();
        this.mSourceConsumers = new android.util.SparseArray<>();
        this.mTmpControlArray = new android.util.SparseArray<>();
        this.mRunningAnimations = new java.util.ArrayList<>();
        this.mCaptionInsetsHeight = 0;
        this.mImeCaptionBarInsetsHeight = 0;
        this.mPendingControlTimeout = new java.lang.Runnable() { // from class: android.view.InsetsController$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                android.view.InsetsController.this.abortPendingImeControlRequest();
            }
        };
        this.mControllableInsetsChangedListeners = new java.util.ArrayList<>();
        this.mExistingTypes = 0;
        this.mVisibleTypes = android.view.WindowInsets.Type.defaultVisible();
        this.mRequestedVisibleTypes = android.view.WindowInsets.Type.defaultVisible();
        this.mReportedRequestedVisibleTypes = android.view.WindowInsets.Type.defaultVisible();
        this.mInvokeControllableInsetsChangedListeners = new java.lang.Runnable() { // from class: android.view.InsetsController$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                android.view.InsetsController.this.invokeControllableInsetsChangedListeners();
            }
        };
        this.mRemoveGoneSources = new android.view.InsetsState.OnTraverseCallbacks() { // from class: android.view.InsetsController.2
            private final android.util.IntArray mPendingRemoveIndexes = new android.util.IntArray();

            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onIdNotFoundInState2(int i, android.view.InsetsSource insetsSource) {
                if ((!android.view.ViewRootImpl.CAPTION_ON_SHELL && insetsSource.getType() == android.view.WindowInsets.Type.captionBar()) || insetsSource.getId() == android.view.InsetsSource.ID_IME_CAPTION_BAR) {
                    return;
                }
                this.mPendingRemoveIndexes.add(i);
            }

            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onFinish(android.view.InsetsState insetsState, android.view.InsetsState insetsState2) {
                for (int size = this.mPendingRemoveIndexes.size() - 1; size >= 0; size--) {
                    insetsState.removeSourceAt(this.mPendingRemoveIndexes.get(size));
                }
                this.mPendingRemoveIndexes.clear();
            }
        };
        this.mStartResizingAnimationIfNeeded = new android.view.InsetsState.OnTraverseCallbacks() { // from class: android.view.InsetsController.3
            private android.view.InsetsState mToState;
            private int mTypes;

            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onStart(android.view.InsetsState insetsState, android.view.InsetsState insetsState2) {
                this.mTypes = 0;
                this.mToState = null;
            }

            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onIdMatch(android.view.InsetsSource insetsSource, android.view.InsetsSource insetsSource2) {
                int type = insetsSource.getType();
                if ((android.view.WindowInsets.Type.systemBars() & type) != 0 && insetsSource.isVisible() && insetsSource2.isVisible() && !insetsSource.getFrame().equals(insetsSource2.getFrame())) {
                    if (!android.graphics.Rect.intersects(android.view.InsetsController.this.mFrame, insetsSource.getFrame()) && !android.graphics.Rect.intersects(android.view.InsetsController.this.mFrame, insetsSource2.getFrame())) {
                        return;
                    }
                    this.mTypes |= type;
                    if (this.mToState == null) {
                        this.mToState = new android.view.InsetsState();
                    }
                    this.mToState.addSource(new android.view.InsetsSource(insetsSource2));
                }
            }

            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onFinish(android.view.InsetsState insetsState, android.view.InsetsState insetsState2) {
                if (this.mTypes == 0) {
                    return;
                }
                android.view.InsetsController.this.cancelExistingControllers(this.mTypes);
                android.view.InsetsResizeAnimationRunner insetsResizeAnimationRunner = new android.view.InsetsResizeAnimationRunner(android.view.InsetsController.this.mFrame, insetsState, this.mToState, android.view.InsetsController.RESIZE_INTERPOLATOR, 300L, this.mTypes, android.view.InsetsController.this);
                if (android.view.InsetsController.this.mRunningAnimations.isEmpty()) {
                    android.view.InsetsController.this.mHost.notifyAnimationRunningStateChanged(true);
                }
                android.view.InsetsController.this.mRunningAnimations.add(new android.view.InsetsController.RunningAnimation(insetsResizeAnimationRunner, insetsResizeAnimationRunner.getAnimationType()));
            }
        };
        this.mHost = host;
        this.mConsumerCreator = triFunction;
        this.mHandler = handler;
        this.mAnimCallback = new java.lang.Runnable() { // from class: android.view.InsetsController$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                android.view.InsetsController.this.lambda$new$3();
            }
        };
        this.mImeSourceConsumer = getSourceConsumer(android.view.InsetsSource.ID_IME, android.view.WindowInsets.Type.ime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3() {
        this.mAnimCallbackScheduled = false;
        if (this.mRunningAnimations.isEmpty()) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        android.view.InsetsState insetsState = new android.view.InsetsState(this.mState, true);
        for (int size = this.mRunningAnimations.size() - 1; size >= 0; size--) {
            android.view.InsetsController.RunningAnimation runningAnimation = this.mRunningAnimations.get(size);
            android.view.InsetsAnimationControlRunner insetsAnimationControlRunner = runningAnimation.runner;
            if (insetsAnimationControlRunner instanceof android.view.WindowInsetsAnimationController) {
                if (runningAnimation.startDispatched) {
                    arrayList.add(insetsAnimationControlRunner.getAnimation());
                }
                if (((android.view.InternalInsetsAnimationController) insetsAnimationControlRunner).applyChangeInsets(insetsState)) {
                    arrayList2.add(insetsAnimationControlRunner.getAnimation());
                }
            }
        }
        this.mHost.dispatchWindowInsetsAnimationProgress(insetsState.calculateInsets(this.mFrame, this.mState, this.mLastInsets.isRound(), this.mLastLegacySoftInputMode, this.mLastLegacyWindowFlags, this.mLastLegacySystemUiFlags, this.mWindowType, this.mLastActivityType, null), java.util.Collections.unmodifiableList(arrayList));
        for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
            dispatchAnimationEnd((android.view.WindowInsetsAnimation) arrayList2.get(size2));
        }
    }

    public void onFrameChanged(android.graphics.Rect rect) {
        if (this.mFrame.equals(rect)) {
            return;
        }
        if (this.mImeCaptionBarInsetsHeight != 0) {
            setImeCaptionBarInsetsHeight(this.mImeCaptionBarInsetsHeight);
        }
        this.mHost.notifyInsetsChanged();
        this.mFrame.set(rect);
    }

    @Override // android.view.WindowInsetsController
    public android.view.InsetsState getState() {
        return this.mState;
    }

    @Override // android.view.WindowInsetsController
    public int getRequestedVisibleTypes() {
        return this.mRequestedVisibleTypes;
    }

    public android.view.InsetsState getLastDispatchedState() {
        return this.mLastDispatchedState;
    }

    public boolean onStateChanged(android.view.InsetsState insetsState) {
        boolean z;
        if (!android.view.ViewRootImpl.CAPTION_ON_SHELL) {
            z = !this.mState.equals(insetsState, true, false) || captionInsetsUnchanged();
        } else {
            z = !this.mState.equals(insetsState, false, false);
        }
        if (!z && this.mLastDispatchedState.equals(insetsState)) {
            return false;
        }
        this.mLastDispatchedState.set(insetsState, true);
        android.view.InsetsState insetsState2 = new android.view.InsetsState(this.mState, true);
        updateState(insetsState);
        applyLocalVisibilityOverride();
        updateCompatSysUiVisibility();
        if (!this.mState.equals(insetsState2, false, true)) {
            this.mHost.notifyInsetsChanged();
            if (insetsState2.getDisplayFrame().equals(this.mState.getDisplayFrame())) {
                android.view.InsetsState.traverse(insetsState2, this.mState, this.mStartResizingAnimationIfNeeded);
            }
        }
        return true;
    }

    private void updateState(android.view.InsetsState insetsState) {
        this.mState.set(insetsState, 0);
        final int[] iArr = {0};
        int sourceSize = insetsState.sourceSize();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < sourceSize; i3++) {
            android.view.InsetsSource sourceAt = insetsState.sourceAt(i3);
            int type = sourceAt.getType();
            int animationType = getAnimationType(type);
            android.view.InsetsSourceConsumer insetsSourceConsumer = this.mSourceConsumers.get(sourceAt.getId());
            if (insetsSourceConsumer != null) {
                insetsSourceConsumer.updateSource(sourceAt, animationType);
            } else {
                this.mState.addSource(sourceAt);
            }
            i2 |= type;
            if (sourceAt.isVisible()) {
                i |= type;
            }
        }
        int defaultVisible = (android.view.WindowInsets.Type.defaultVisible() & (~i2)) | i;
        if (this.mVisibleTypes != defaultVisible) {
            if (android.view.WindowInsets.Type.hasCompatSystemBars(this.mVisibleTypes ^ defaultVisible)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            this.mVisibleTypes = defaultVisible;
        }
        if (this.mExistingTypes != i2) {
            if (android.view.WindowInsets.Type.hasCompatSystemBars(this.mExistingTypes ^ i2)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            this.mExistingTypes = i2;
        }
        android.view.InsetsState.traverse(this.mState, insetsState, this.mRemoveGoneSources);
        if (iArr[0] != 0) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.view.InsetsController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.InsetsController.this.lambda$updateState$4(iArr);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateState$4(int[] iArr) {
        show(iArr[0]);
    }

    private boolean captionInsetsUnchanged() {
        if (android.view.ViewRootImpl.CAPTION_ON_SHELL) {
            return false;
        }
        android.view.InsetsSource peekSource = this.mState.peekSource(ID_CAPTION_BAR);
        if (peekSource == null && this.mCaptionInsetsHeight == 0) {
            return false;
        }
        return peekSource == null || this.mCaptionInsetsHeight != peekSource.getFrame().height();
    }

    public android.view.WindowInsets calculateInsets(boolean z, int i, int i2, int i3, int i4, int i5) {
        this.mWindowType = i;
        this.mLastActivityType = i2;
        this.mLastLegacySoftInputMode = i3;
        this.mLastLegacyWindowFlags = i4;
        this.mLastLegacySystemUiFlags = i5;
        this.mLastInsets = this.mState.calculateInsets(this.mFrame, null, z, i3, i4, i5, i, i2, null);
        return this.mLastInsets;
    }

    public android.graphics.Insets calculateVisibleInsets(int i, int i2, int i3, int i4) {
        return this.mState.calculateVisibleInsets(this.mFrame, i, i2, i3, i4);
    }

    public void onControlsChanged(android.view.InsetsSourceControl[] insetsSourceControlArr) {
        if (insetsSourceControlArr != null) {
            for (android.view.InsetsSourceControl insetsSourceControl : insetsSourceControlArr) {
                if (insetsSourceControl != null) {
                    this.mTmpControlArray.put(insetsSourceControl.getId(), insetsSourceControl);
                }
            }
        }
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        int i = 0;
        int i2 = 0;
        for (int size = this.mSourceConsumers.size() - 1; size >= 0; size--) {
            android.view.InsetsSourceConsumer valueAt = this.mSourceConsumers.valueAt(size);
            if (valueAt.getId() != android.view.InsetsSource.ID_IME_CAPTION_BAR) {
                android.view.InsetsSourceControl insetsSourceControl2 = this.mTmpControlArray.get(valueAt.getId());
                if (insetsSourceControl2 != null) {
                    i2 |= insetsSourceControl2.getType();
                    i++;
                }
                valueAt.setControl(insetsSourceControl2, iArr, iArr2);
            }
        }
        if (i != this.mTmpControlArray.size()) {
            for (int size2 = this.mTmpControlArray.size() - 1; size2 >= 0; size2--) {
                android.view.InsetsSourceControl valueAt2 = this.mTmpControlArray.valueAt(size2);
                getSourceConsumer(valueAt2.getId(), valueAt2.getType()).setControl(valueAt2, iArr, iArr2);
            }
        }
        if (this.mTmpControlArray.size() > 0) {
            for (int size3 = this.mRunningAnimations.size() - 1; size3 >= 0; size3--) {
                this.mRunningAnimations.get(size3).runner.updateSurfacePosition(this.mTmpControlArray);
            }
        }
        this.mTmpControlArray.clear();
        int invokeControllableInsetsChangedListeners = invokeControllableInsetsChangedListeners();
        int i3 = iArr[0];
        int i4 = ~invokeControllableInsetsChangedListeners;
        iArr[0] = i3 & i4;
        iArr2[0] = i4 & iArr2[0];
        if (iArr[0] != 0) {
            applyAnimation(iArr[0], true, false, (iArr[0] & android.view.WindowInsets.Type.ime()) == 0 ? null : android.view.inputmethod.ImeTracker.forLogging().onStart(1, 5, 46, this.mHost.isHandlingPointerEvent()));
        }
        if (iArr2[0] != 0) {
            applyAnimation(iArr2[0], false, false, (iArr2[0] & android.view.WindowInsets.Type.ime()) != 0 ? android.view.inputmethod.ImeTracker.forLogging().onStart(2, 5, 46, this.mHost.isHandlingPointerEvent()) : null);
        }
        if (this.mControllableTypes != i2) {
            if (android.view.WindowInsets.Type.hasCompatSystemBars(this.mControllableTypes ^ i2)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            this.mControllableTypes = i2;
        }
        reportRequestedVisibleTypes();
    }

    @Override // android.view.WindowInsetsController
    public void show(int i) {
        show(i, false, null);
    }

    public void show(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) {
        if ((android.view.WindowInsets.Type.ime() & i) != 0) {
            android.util.Log.d(TAG, "show(ime(), fromIme=" + z + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            if (token == null) {
                token = android.view.inputmethod.ImeTracker.forLogging().onStart(1, 5, 26, this.mHost.isHandlingPointerEvent());
            }
        }
        if (z) {
            com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("InsetsController#show", this.mHost.getInputMethodManager(), null);
            android.os.Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
            android.os.Trace.asyncTraceBegin(8L, "IC.showRequestFromIme", 0);
        } else {
            android.os.Trace.asyncTraceBegin(8L, "IC.showRequestFromApi", 0);
            android.os.Trace.asyncTraceBegin(8L, "IC.showRequestFromApiToImeReady", 0);
        }
        if (z && this.mPendingImeControlRequest != null) {
            if ((i & android.view.WindowInsets.Type.ime()) != 0) {
                android.view.inputmethod.ImeTracker.forLatency().onShown(token, new android.view.InsetsController$$ExternalSyntheticLambda2());
            }
            handlePendingControlRequest(token);
            return;
        }
        boolean isSourceOrDefaultVisible = this.mState.isSourceOrDefaultVisible(this.mImeSourceConsumer.getId(), android.view.WindowInsets.Type.ime());
        int i2 = 1;
        int i3 = 0;
        while (i2 <= 512) {
            if ((i & i2) != 0) {
                int animationType = getAnimationType(i2);
                boolean z2 = (this.mRequestedVisibleTypes & i2) != 0;
                boolean z3 = i2 == android.view.WindowInsets.Type.ime();
                boolean z4 = z2 && (!z3 || isSourceOrDefaultVisible) && animationType == -1;
                boolean z5 = animationType == 0;
                if (z4 || z5) {
                    if (z3) {
                        android.view.inputmethod.ImeTracker.forLogging().onCancelled(token, 32);
                    }
                } else if (z && animationType == 2) {
                    if (z3) {
                        android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 32);
                    }
                } else {
                    if (z3) {
                        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 32);
                    }
                    i3 |= i2;
                }
            }
            i2 <<= 1;
        }
        if (z && (android.view.WindowInsets.Type.ime() & i3) != 0) {
            android.view.inputmethod.ImeTracker.forLatency().onShown(token, new android.view.InsetsController$$ExternalSyntheticLambda2());
        }
        applyAnimation(i3, true, z, token);
    }

    private void handlePendingControlRequest(android.view.inputmethod.ImeTracker.Token token) {
        android.view.InsetsController.PendingControlRequest pendingControlRequest = this.mPendingImeControlRequest;
        this.mPendingImeControlRequest = null;
        this.mHandler.removeCallbacks(this.mPendingControlTimeout);
        controlAnimationUnchecked(pendingControlRequest.types, pendingControlRequest.cancellationSignal, pendingControlRequest.listener, null, true, pendingControlRequest.durationMs, pendingControlRequest.interpolator, pendingControlRequest.animationType, pendingControlRequest.layoutInsetsDuringAnimation, pendingControlRequest.useInsetsAnimationThread, token);
    }

    @Override // android.view.WindowInsetsController
    public void hide(int i) {
        hide(i, false, null);
    }

    public void hide(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) {
        if ((android.view.WindowInsets.Type.ime() & i) != 0) {
            android.util.Log.d(TAG, "hide(ime(), fromIme=" + z + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            if (token == null) {
                token = android.view.inputmethod.ImeTracker.forLogging().onStart(2, 5, 28, this.mHost.isHandlingPointerEvent());
            }
        }
        if (!z) {
            android.os.Trace.asyncTraceBegin(8L, "IC.hideRequestFromApi", 0);
        } else {
            com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("InsetsController#hide", this.mHost.getInputMethodManager(), null);
            android.os.Trace.asyncTraceBegin(8L, "IC.hideRequestFromIme", 0);
        }
        boolean z2 = this.mPendingImeControlRequest != null;
        int i2 = 1;
        boolean z3 = false;
        int i3 = 0;
        while (i2 <= 512) {
            if ((i & i2) != 0) {
                int animationType = getAnimationType(i2);
                boolean z4 = (this.mRequestedVisibleTypes & i2) != 0;
                boolean z5 = i2 == android.view.WindowInsets.Type.ime();
                if (this.mPendingImeControlRequest != null && !z4) {
                    this.mPendingImeControlRequest.types &= ~i2;
                    if (this.mPendingImeControlRequest.types == 0) {
                        abortPendingImeControlRequest();
                    }
                }
                if (z5 && !z4 && animationType == -1) {
                    if (z2 || getImeSourceConsumer().isRequestedVisibleAwaitingControl()) {
                        getImeSourceConsumer().requestHide(z, token);
                    }
                    z3 = true;
                }
                if ((!z4 && animationType == -1) || animationType == 1) {
                    if (z5) {
                        android.view.inputmethod.ImeTracker.forLogging().onCancelled(token, 32);
                    }
                } else {
                    if (z5) {
                        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 32);
                    }
                    i3 |= i2;
                }
            }
            i2 <<= 1;
        }
        if (z3 && this.mPendingImeControlRequest != null) {
            handlePendingControlRequest(token);
            getImeSourceConsumer().removeSurface();
        }
        applyAnimation(i3, false, z, token);
    }

    @Override // android.view.WindowInsetsController
    public void controlWindowInsetsAnimation(int i, long j, android.view.animation.Interpolator interpolator, android.os.CancellationSignal cancellationSignal, android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener) {
        controlWindowInsetsAnimation(i, cancellationSignal, windowInsetsAnimationControlListener, false, j, interpolator, 2);
    }

    private void controlWindowInsetsAnimation(int i, android.os.CancellationSignal cancellationSignal, android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, boolean z, long j, android.view.animation.Interpolator interpolator, int i2) {
        if ((this.mState.calculateUncontrollableInsetsFromFrame(this.mFrame) & i) != 0) {
            windowInsetsAnimationControlListener.onCancelled(null);
            return;
        }
        if (z) {
            com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("InsetsController#controlWindowInsetsAnimation", this.mHost.getInputMethodManager(), null);
        }
        controlAnimationUnchecked(i, cancellationSignal, windowInsetsAnimationControlListener, this.mFrame, z, j, interpolator, i2, getLayoutInsetsDuringAnimationMode(i), false, null);
    }

    private void controlAnimationUnchecked(int i, android.os.CancellationSignal cancellationSignal, android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, android.graphics.Rect rect, boolean z, long j, android.view.animation.Interpolator interpolator, int i2, int i3, boolean z2, android.view.inputmethod.ImeTracker.Token token) {
        setRequestedVisibleTypes(i3 == 0 ? i : 0, i);
        controlAnimationUncheckedInner(i, cancellationSignal, windowInsetsAnimationControlListener, rect, z, j, interpolator, i2, i3, z2, token);
        reportRequestedVisibleTypes();
    }

    private void controlAnimationUncheckedInner(int i, android.os.CancellationSignal cancellationSignal, android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, android.graphics.Rect rect, boolean z, long j, android.view.animation.Interpolator interpolator, int i2, int i3, boolean z2, android.view.inputmethod.ImeTracker.Token token) {
        int i4;
        final android.view.InsetsAnimationControlRunner insetsAnimationControlImpl;
        int i5;
        android.view.inputmethod.ImeTracker.Token token2;
        boolean z3;
        long j2;
        int i6;
        boolean z4 = true;
        if ((this.mTypesBeingCancelled & i) != 0) {
            if (i2 != 0 && i2 != 1) {
                z4 = false;
            }
            if (z4 && (android.view.WindowInsets.Type.ime() & i) != 0) {
                if (i2 == 0) {
                    android.view.inputmethod.ImeTracker.forLatency().onShowCancelled(token, 40, new android.view.InsetsController$$ExternalSyntheticLambda2());
                } else {
                    android.view.inputmethod.ImeTracker.forLatency().onHideCancelled(token, 40, new android.view.InsetsController$$ExternalSyntheticLambda2());
                }
                android.view.inputmethod.ImeTracker.forLogging().onCancelled(token, 33);
            }
            throw new java.lang.IllegalStateException("Cannot start a new insets animation of " + android.view.WindowInsets.Type.toString(i) + " while an existing " + android.view.WindowInsets.Type.toString(this.mTypesBeingCancelled) + " is being cancelled.");
        }
        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 33);
        if (i != 0) {
            this.mLastStartedAnimTypes |= i;
            android.util.SparseArray<android.view.InsetsSourceControl> sparseArray = new android.util.SparseArray<>();
            android.util.Pair<java.lang.Integer, java.lang.Boolean> collectSourceControls = collectSourceControls(z, i, sparseArray, i2, token);
            int intValue = collectSourceControls.first.intValue();
            if (!collectSourceControls.second.booleanValue()) {
                abortPendingImeControlRequest();
                final android.view.InsetsController.PendingControlRequest pendingControlRequest = new android.view.InsetsController.PendingControlRequest(i, windowInsetsAnimationControlListener, j, interpolator, i2, i3, cancellationSignal, z2);
                this.mPendingImeControlRequest = pendingControlRequest;
                this.mHandler.postDelayed(this.mPendingControlTimeout, 2000L);
                if (cancellationSignal != null) {
                    cancellationSignal.setOnCancelListener(new android.os.CancellationSignal.OnCancelListener() { // from class: android.view.InsetsController$$ExternalSyntheticLambda3
                        @Override // android.os.CancellationSignal.OnCancelListener
                        public final void onCancel() {
                            android.view.InsetsController.this.lambda$controlAnimationUncheckedInner$5(pendingControlRequest);
                        }
                    });
                }
                releaseControls(sparseArray);
                setRequestedVisibleTypes(this.mReportedRequestedVisibleTypes, i);
                android.os.Trace.asyncTraceEnd(8L, "IC.showRequestFromApi", 0);
                if (!z) {
                    android.os.Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
                    return;
                }
                return;
            }
            if (intValue != 0) {
                cancelExistingControllers(intValue);
                if (z2) {
                    i4 = intValue;
                    insetsAnimationControlImpl = new android.view.InsetsAnimationThreadControlRunner(sparseArray, rect, this.mState, windowInsetsAnimationControlListener, intValue, this, j, interpolator, i2, i3, this.mHost.getTranslator(), this.mHost.getHandler(), token);
                } else {
                    i4 = intValue;
                    insetsAnimationControlImpl = new android.view.InsetsAnimationControlImpl(sparseArray, rect, this.mState, windowInsetsAnimationControlListener, intValue, this, j, interpolator, i2, i3, this.mHost.getTranslator(), token);
                }
                if ((i4 & android.view.WindowInsets.Type.ime()) == 0) {
                    i5 = i2;
                    token2 = token;
                    z3 = true;
                } else {
                    com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("InsetsAnimationControlImpl", this.mHost.getInputMethodManager(), null);
                    i5 = i2;
                    z3 = true;
                    if (i5 != 1) {
                        token2 = token;
                    } else {
                        token2 = token;
                        android.view.inputmethod.ImeTracker.forLatency().onHidden(token2, new android.view.InsetsController$$ExternalSyntheticLambda2());
                    }
                }
                android.view.inputmethod.ImeTracker.forLogging().onProgress(token2, 39);
                if (this.mRunningAnimations.isEmpty()) {
                    this.mHost.notifyAnimationRunningStateChanged(z3);
                }
                this.mRunningAnimations.add(new android.view.InsetsController.RunningAnimation(insetsAnimationControlImpl, i5));
                if (cancellationSignal != null) {
                    cancellationSignal.setOnCancelListener(new android.os.CancellationSignal.OnCancelListener() { // from class: android.view.InsetsController$$ExternalSyntheticLambda4
                        @Override // android.os.CancellationSignal.OnCancelListener
                        public final void onCancel() {
                            android.view.InsetsController.this.lambda$controlAnimationUncheckedInner$6(insetsAnimationControlImpl);
                        }
                    });
                    j2 = 8;
                    i6 = 0;
                } else {
                    j2 = 8;
                    i6 = 0;
                    android.os.Trace.asyncTraceBegin(8L, "IC.pendingAnim", 0);
                }
                onAnimationStateChanged(i, z3);
                if (z) {
                    switch (i5) {
                        case 0:
                            android.os.Trace.asyncTraceEnd(j2, "IC.showRequestFromIme", i6);
                            return;
                        case 1:
                            android.os.Trace.asyncTraceEnd(j2, "IC.hideRequestFromIme", i6);
                            return;
                        default:
                            return;
                    }
                }
                if (i5 == z3) {
                    android.os.Trace.asyncTraceEnd(j2, "IC.hideRequestFromApi", i6);
                    return;
                }
                return;
            }
            windowInsetsAnimationControlListener.onCancelled(null);
            android.os.Trace.asyncTraceEnd(8L, "IC.showRequestFromApi", 0);
            if (!z) {
                android.os.Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
                return;
            }
            return;
        }
        windowInsetsAnimationControlListener.onCancelled(null);
        android.os.Trace.asyncTraceEnd(8L, "IC.showRequestFromApi", 0);
        android.os.Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$controlAnimationUncheckedInner$5(android.view.InsetsController.PendingControlRequest pendingControlRequest) {
        if (this.mPendingImeControlRequest == pendingControlRequest) {
            abortPendingImeControlRequest();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$controlAnimationUncheckedInner$6(android.view.InsetsAnimationControlRunner insetsAnimationControlRunner) {
        cancelAnimation(insetsAnimationControlRunner, true);
    }

    static void releaseControls(android.util.SparseArray<android.view.InsetsSourceControl> sparseArray) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            sparseArray.valueAt(size).release(new android.view.InsetsController$$ExternalSyntheticLambda7());
        }
    }

    @Override // android.view.WindowInsetsController
    public void setSystemDrivenInsetsAnimationLoggingListener(android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener) {
        this.mLoggingListener = windowInsetsAnimationControlListener;
    }

    private android.util.Pair<java.lang.Integer, java.lang.Boolean> collectSourceControls(boolean z, int i, android.util.SparseArray<android.view.InsetsSourceControl> sparseArray, int i2, android.view.inputmethod.ImeTracker.Token token) {
        boolean z2;
        java.lang.String str;
        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 35);
        boolean z3 = true;
        int i3 = 0;
        for (int size = this.mSourceConsumers.size() - 1; size >= 0; size--) {
            android.view.InsetsSourceConsumer valueAt = this.mSourceConsumers.valueAt(size);
            if ((valueAt.getType() & i) != 0) {
                if (i2 == 0 || i2 == 2) {
                    switch (valueAt.requestShow(z, token)) {
                        case 0:
                        default:
                            z2 = true;
                            break;
                        case 1:
                            z2 = true;
                            z3 = false;
                            break;
                        case 2:
                            setRequestedVisibleTypes(0, valueAt.getType());
                            z2 = false;
                            break;
                    }
                } else {
                    valueAt.requestHide(z, token);
                    z2 = true;
                }
                if (z2) {
                    android.view.InsetsSourceControl control = valueAt.getControl();
                    if (control != null && (control.getLeash() != null || control.getId() == android.view.InsetsSource.ID_IME_CAPTION_BAR)) {
                        sparseArray.put(control.getId(), new android.view.InsetsSourceControl(control));
                        i3 |= valueAt.getType();
                    } else if (z) {
                        java.lang.StringBuilder append = new java.lang.StringBuilder().append("collectSourceControls can't continue for type: ime, fromIme: true requires a control with a leash but we have ");
                        if (control == null) {
                            str = "control: null";
                        } else {
                            str = "control: non-null and control.getLeash(): null";
                        }
                        android.util.Log.w(TAG, append.append(str).toString());
                        android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 35);
                    }
                }
            }
        }
        return new android.util.Pair<>(java.lang.Integer.valueOf(i3), java.lang.Boolean.valueOf(z3));
    }

    private int getLayoutInsetsDuringAnimationMode(int i) {
        if ((this.mRequestedVisibleTypes & i) != i) {
            return 0;
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelExistingControllers(int i) {
        int i2 = this.mTypesBeingCancelled;
        this.mTypesBeingCancelled |= i;
        try {
            for (int size = this.mRunningAnimations.size() - 1; size >= 0; size--) {
                android.view.InsetsAnimationControlRunner insetsAnimationControlRunner = this.mRunningAnimations.get(size).runner;
                if ((insetsAnimationControlRunner.getTypes() & i) != 0) {
                    cancelAnimation(insetsAnimationControlRunner, true);
                }
            }
            if ((i & android.view.WindowInsets.Type.ime()) != 0) {
                abortPendingImeControlRequest();
            }
        } finally {
            this.mTypesBeingCancelled = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abortPendingImeControlRequest() {
        if (this.mPendingImeControlRequest != null) {
            this.mPendingImeControlRequest.listener.onCancelled(null);
            this.mPendingImeControlRequest = null;
            this.mHandler.removeCallbacks(this.mPendingControlTimeout);
        }
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void notifyFinished(android.view.InsetsAnimationControlRunner insetsAnimationControlRunner, boolean z) {
        setRequestedVisibleTypes(z ? insetsAnimationControlRunner.getTypes() : 0, insetsAnimationControlRunner.getTypes());
        cancelAnimation(insetsAnimationControlRunner, false);
        if (insetsAnimationControlRunner.getAnimationType() == 3) {
            return;
        }
        android.view.inputmethod.ImeTracker.Token statsToken = insetsAnimationControlRunner.getStatsToken();
        if (z) {
            android.view.inputmethod.ImeTracker.forLogging().onProgress(statsToken, 41);
            android.view.inputmethod.ImeTracker.forLogging().onShown(statsToken);
        } else {
            android.view.inputmethod.ImeTracker.forLogging().onProgress(statsToken, 42);
            android.view.inputmethod.ImeTracker.forLogging().onHidden(statsToken);
        }
        reportRequestedVisibleTypes();
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void applySurfaceParams(android.view.SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr) {
        this.mHost.applySurfaceParams(surfaceParamsArr);
    }

    void notifyControlRevoked(android.view.InsetsSourceConsumer insetsSourceConsumer) {
        int type = insetsSourceConsumer.getType();
        for (int size = this.mRunningAnimations.size() - 1; size >= 0; size--) {
            android.view.InsetsAnimationControlRunner insetsAnimationControlRunner = this.mRunningAnimations.get(size).runner;
            insetsAnimationControlRunner.notifyControlRevoked(type);
            if (insetsAnimationControlRunner.getControllingTypes() == 0) {
                cancelAnimation(insetsAnimationControlRunner, true);
            }
        }
        if (type == android.view.WindowInsets.Type.ime()) {
            abortPendingImeControlRequest();
        }
        if (insetsSourceConsumer.getType() != android.view.WindowInsets.Type.ime()) {
            this.mSourceConsumers.remove(insetsSourceConsumer.getId());
        }
    }

    private void cancelAnimation(android.view.InsetsAnimationControlRunner insetsAnimationControlRunner, boolean z) {
        int i;
        if (z) {
            android.view.inputmethod.ImeTracker.forLogging().onCancelled(insetsAnimationControlRunner.getStatsToken(), 40);
            insetsAnimationControlRunner.cancel();
        } else {
            android.view.inputmethod.ImeTracker.forLogging().onProgress(insetsAnimationControlRunner.getStatsToken(), 40);
        }
        int size = this.mRunningAnimations.size();
        while (true) {
            size--;
            if (size < 0) {
                i = 0;
                break;
            }
            android.view.InsetsController.RunningAnimation runningAnimation = this.mRunningAnimations.get(size);
            if (runningAnimation.runner == insetsAnimationControlRunner) {
                this.mRunningAnimations.remove(size);
                i = insetsAnimationControlRunner.getTypes();
                if (z) {
                    dispatchAnimationEnd(runningAnimation.runner.getAnimation());
                }
            }
        }
        if (this.mRunningAnimations.isEmpty()) {
            this.mHost.notifyAnimationRunningStateChanged(false);
        }
        onAnimationStateChanged(i, false);
    }

    private void onAnimationStateChanged(int i, boolean z) {
        boolean z2 = false;
        for (int size = this.mSourceConsumers.size() - 1; size >= 0; size--) {
            android.view.InsetsSourceConsumer valueAt = this.mSourceConsumers.valueAt(size);
            if ((valueAt.getType() & i) != 0) {
                z2 |= valueAt.onAnimationStateChanged(z);
            }
        }
        if (z2) {
            notifyVisibilityChanged();
        }
    }

    private void applyLocalVisibilityOverride() {
        for (int size = this.mSourceConsumers.size() - 1; size >= 0; size--) {
            this.mSourceConsumers.valueAt(size).applyLocalVisibilityOverride();
        }
    }

    public android.view.InsetsSourceConsumer getSourceConsumer(int i, int i2) {
        android.view.InsetsSourceConsumer apply;
        android.view.InsetsSourceConsumer insetsSourceConsumer = this.mSourceConsumers.get(i);
        if (insetsSourceConsumer != null) {
            return insetsSourceConsumer;
        }
        if (i2 == android.view.WindowInsets.Type.ime() && this.mImeSourceConsumer != null) {
            this.mSourceConsumers.remove(this.mImeSourceConsumer.getId());
            apply = this.mImeSourceConsumer;
            apply.setId(i);
        } else {
            apply = this.mConsumerCreator.apply(this, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        this.mSourceConsumers.put(i, apply);
        return apply;
    }

    public android.view.InsetsSourceConsumer getImeSourceConsumer() {
        return this.mImeSourceConsumer;
    }

    void notifyVisibilityChanged() {
        this.mHost.notifyInsetsChanged();
    }

    public void updateCompatSysUiVisibility() {
        if (this.mCompatSysUiVisibilityStaled) {
            this.mCompatSysUiVisibilityStaled = false;
            this.mHost.updateCompatSysUiVisibility(this.mVisibleTypes, this.mRequestedVisibleTypes, this.mControllableTypes | (~this.mExistingTypes));
        }
    }

    public void onWindowFocusGained(boolean z) {
        this.mImeSourceConsumer.onWindowFocusGained(z);
    }

    public void onWindowFocusLost() {
        this.mImeSourceConsumer.onWindowFocusLost();
    }

    public int getAnimationType(int i) {
        for (int size = this.mRunningAnimations.size() - 1; size >= 0; size--) {
            if (this.mRunningAnimations.get(size).runner.controlsType(i)) {
                return this.mRunningAnimations.get(size).type;
            }
        }
        return -1;
    }

    public void setRequestedVisibleTypes(int i, int i2) {
        int i3 = (i & i2) | (this.mRequestedVisibleTypes & (~i2));
        if (this.mRequestedVisibleTypes != i3) {
            this.mRequestedVisibleTypes = i3;
        }
    }

    private void reportRequestedVisibleTypes() {
        if (this.mReportedRequestedVisibleTypes != this.mRequestedVisibleTypes) {
            if (android.view.WindowInsets.Type.hasCompatSystemBars(this.mRequestedVisibleTypes ^ this.mReportedRequestedVisibleTypes)) {
                this.mCompatSysUiVisibilityStaled = true;
            }
            this.mReportedRequestedVisibleTypes = this.mRequestedVisibleTypes;
            this.mHost.updateRequestedVisibleTypes(this.mReportedRequestedVisibleTypes);
        }
        updateCompatSysUiVisibility();
    }

    public void applyAnimation(int i, boolean z, boolean z2, android.view.inputmethod.ImeTracker.Token token) {
        boolean z3;
        android.view.InsetsSourceControl control;
        boolean z4 = false;
        if ((android.view.WindowInsets.Type.ime() & i) != 0 && (control = this.mImeSourceConsumer.getControl()) != null) {
            if (control.getAndClearSkipAnimationOnce() && z && this.mImeSourceConsumer.hasViewFocusWhenWindowFocusGain()) {
                z4 = true;
            }
            z3 = z4;
        } else {
            z3 = false;
        }
        applyAnimation(i, z, z2, z3, token);
    }

    public void applyAnimation(int i, boolean z, boolean z2, boolean z3, android.view.inputmethod.ImeTracker.Token token) {
        if (i == 0) {
            android.os.Trace.asyncTraceEnd(8L, "IC.showRequestFromApi", 0);
            if (!z2) {
                android.os.Trace.asyncTraceEnd(8L, "IC.showRequestFromApiToImeReady", 0);
                return;
            }
            return;
        }
        boolean hasAnimationCallbacks = this.mHost.hasAnimationCallbacks();
        android.view.InsetsController.InternalAnimationControlListener internalAnimationControlListener = new android.view.InsetsController.InternalAnimationControlListener(z, hasAnimationCallbacks, i, this.mHost.getSystemBarsBehavior(), z3 || this.mAnimationsDisabled, this.mHost.dipToPx(-80), this.mLoggingListener, this.mJankContext);
        controlAnimationUnchecked(i, null, internalAnimationControlListener, null, z2, internalAnimationControlListener.getDurationMs(), internalAnimationControlListener.getInsetsInterpolator(), !z ? 1 : 0, !z ? 1 : 0, true ^ hasAnimationCallbacks, token);
    }

    public void cancelExistingAnimations() {
        cancelExistingControllers(android.view.WindowInsets.Type.all());
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.println("InsetsController:");
        this.mState.dump(str + "  ", printWriter);
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        this.mState.dumpDebug(protoOutputStream, 1146756268033L);
        for (int size = this.mRunningAnimations.size() - 1; size >= 0; size--) {
            this.mRunningAnimations.get(size).runner.dumpDebug(protoOutputStream, 2246267895810L);
        }
        protoOutputStream.end(start);
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public <T extends android.view.InsetsAnimationControlRunner & android.view.InternalInsetsAnimationController> void startAnimation(final T t, final android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, final int i, final android.view.WindowInsetsAnimation windowInsetsAnimation, final android.view.WindowInsetsAnimation.Bounds bounds) {
        this.mHost.dispatchWindowInsetsAnimationPrepare(windowInsetsAnimation);
        this.mHost.addOnPreDrawRunnable(new java.lang.Runnable() { // from class: android.view.InsetsController$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                android.view.InsetsController.this.lambda$startAnimation$7(t, i, windowInsetsAnimation, bounds, windowInsetsAnimationControlListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAnimation$7(android.view.InsetsAnimationControlRunner insetsAnimationControlRunner, int i, android.view.WindowInsetsAnimation windowInsetsAnimation, android.view.WindowInsetsAnimation.Bounds bounds, android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener) {
        android.view.WindowInsetsAnimationController windowInsetsAnimationController = (android.view.WindowInsetsAnimationController) insetsAnimationControlRunner;
        if (windowInsetsAnimationController.isCancelled()) {
            return;
        }
        android.os.Trace.asyncTraceBegin(8L, "InsetsAnimation: " + android.view.WindowInsets.Type.toString(i), i);
        for (int size = this.mRunningAnimations.size() - 1; size >= 0; size--) {
            android.view.InsetsController.RunningAnimation runningAnimation = this.mRunningAnimations.get(size);
            if (runningAnimation.runner == insetsAnimationControlRunner) {
                runningAnimation.startDispatched = true;
            }
        }
        android.os.Trace.asyncTraceEnd(8L, "IC.pendingAnim", 0);
        this.mHost.dispatchWindowInsetsAnimationStart(windowInsetsAnimation, bounds);
        this.mStartingAnimation = true;
        ((android.view.InternalInsetsAnimationController) insetsAnimationControlRunner).setReadyDispatched(true);
        windowInsetsAnimationControlListener.onReady(windowInsetsAnimationController, i);
        this.mStartingAnimation = false;
    }

    public void dispatchAnimationEnd(android.view.WindowInsetsAnimation windowInsetsAnimation) {
        android.os.Trace.asyncTraceEnd(8L, "InsetsAnimation: " + android.view.WindowInsets.Type.toString(windowInsetsAnimation.getTypeMask()), windowInsetsAnimation.getTypeMask());
        this.mHost.dispatchWindowInsetsAnimationEnd(windowInsetsAnimation);
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void scheduleApplyChangeInsets(android.view.InsetsAnimationControlRunner insetsAnimationControlRunner) {
        if (this.mStartingAnimation || insetsAnimationControlRunner.getAnimationType() == 2) {
            this.mAnimCallback.run();
            this.mAnimCallbackScheduled = false;
        } else if (!this.mAnimCallbackScheduled) {
            this.mHost.postInsetsAnimationCallback(this.mAnimCallback);
            this.mAnimCallbackScheduled = true;
        }
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsAppearance(int i, int i2) {
        this.mHost.setSystemBarsAppearance(i, i2);
    }

    @Override // android.view.WindowInsetsController
    public int getSystemBarsAppearance() {
        if (!this.mHost.isSystemBarsAppearanceControlled()) {
            return 0;
        }
        return this.mHost.getSystemBarsAppearance();
    }

    @Override // android.view.WindowInsetsController
    public void setCaptionInsetsHeight(int i) {
        if (!android.view.ViewRootImpl.CAPTION_ON_SHELL && this.mCaptionInsetsHeight != i) {
            this.mCaptionInsetsHeight = i;
            if (this.mCaptionInsetsHeight != 0) {
                this.mState.getOrCreateSource(ID_CAPTION_BAR, android.view.WindowInsets.Type.captionBar()).setFrame(this.mFrame.left, this.mFrame.top, this.mFrame.right, this.mFrame.top + this.mCaptionInsetsHeight);
            } else {
                this.mState.removeSource(ID_CAPTION_BAR);
            }
            this.mHost.notifyInsetsChanged();
        }
    }

    @Override // android.view.WindowInsetsController
    public void setImeCaptionBarInsetsHeight(int i) {
        android.graphics.Rect rect = new android.graphics.Rect(this.mFrame.left, this.mFrame.bottom - i, this.mFrame.right, this.mFrame.bottom);
        android.view.InsetsSource peekSource = this.mState.peekSource(android.view.InsetsSource.ID_IME_CAPTION_BAR);
        if (this.mImeCaptionBarInsetsHeight != i || (peekSource != null && !rect.equals(peekSource.getFrame()))) {
            this.mImeCaptionBarInsetsHeight = i;
            if (this.mImeCaptionBarInsetsHeight != 0) {
                this.mState.getOrCreateSource(android.view.InsetsSource.ID_IME_CAPTION_BAR, android.view.WindowInsets.Type.captionBar()).setFrame(rect);
                getSourceConsumer(android.view.InsetsSource.ID_IME_CAPTION_BAR, android.view.WindowInsets.Type.captionBar()).setControl(new android.view.InsetsSourceControl(android.view.InsetsSource.ID_IME_CAPTION_BAR, android.view.WindowInsets.Type.captionBar(), null, false, new android.graphics.Point(), android.graphics.Insets.NONE), new int[1], new int[1]);
            } else {
                this.mState.removeSource(android.view.InsetsSource.ID_IME_CAPTION_BAR);
                android.view.InsetsSourceConsumer insetsSourceConsumer = this.mSourceConsumers.get(android.view.InsetsSource.ID_IME_CAPTION_BAR);
                if (insetsSourceConsumer != null) {
                    insetsSourceConsumer.setControl(null, new int[1], new int[1]);
                }
            }
            this.mHost.notifyInsetsChanged();
        }
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsBehavior(int i) {
        this.mHost.setSystemBarsBehavior(i);
    }

    @Override // android.view.WindowInsetsController
    public int getSystemBarsBehavior() {
        if (!this.mHost.isSystemBarsBehaviorControlled()) {
            return 0;
        }
        return this.mHost.getSystemBarsBehavior();
    }

    @Override // android.view.WindowInsetsController
    public void setAnimationsDisabled(boolean z) {
        this.mAnimationsDisabled = z;
    }

    private int calculateControllableTypes() {
        int i = 0;
        for (int size = this.mSourceConsumers.size() - 1; size >= 0; size--) {
            android.view.InsetsSourceConsumer valueAt = this.mSourceConsumers.valueAt(size);
            android.view.InsetsSource peekSource = this.mState.peekSource(valueAt.getId());
            if (valueAt.getControl() != null && peekSource != null) {
                i |= valueAt.getType();
            }
        }
        return (~this.mState.calculateUncontrollableInsetsFromFrame(this.mFrame)) & i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int invokeControllableInsetsChangedListeners() {
        this.mHandler.removeCallbacks(this.mInvokeControllableInsetsChangedListeners);
        this.mLastStartedAnimTypes = 0;
        int calculateControllableTypes = calculateControllableTypes();
        int size = this.mControllableInsetsChangedListeners.size();
        for (int i = 0; i < size; i++) {
            this.mControllableInsetsChangedListeners.get(i).onControllableInsetsChanged(this, calculateControllableTypes);
        }
        return this.mLastStartedAnimTypes;
    }

    @Override // android.view.WindowInsetsController
    public void addOnControllableInsetsChangedListener(android.view.WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        java.util.Objects.requireNonNull(onControllableInsetsChangedListener);
        this.mControllableInsetsChangedListeners.add(onControllableInsetsChangedListener);
        onControllableInsetsChangedListener.onControllableInsetsChanged(this, calculateControllableTypes());
    }

    @Override // android.view.WindowInsetsController
    public void removeOnControllableInsetsChangedListener(android.view.WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        java.util.Objects.requireNonNull(onControllableInsetsChangedListener);
        this.mControllableInsetsChangedListeners.remove(onControllableInsetsChangedListener);
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void releaseSurfaceControlFromRt(android.view.SurfaceControl surfaceControl) {
        this.mHost.releaseSurfaceControlFromRt(surfaceControl);
    }

    @Override // android.view.InsetsAnimationControlCallbacks
    public void reportPerceptible(int i, boolean z) {
        int size = this.mSourceConsumers.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.view.InsetsSourceConsumer valueAt = this.mSourceConsumers.valueAt(i2);
            if ((valueAt.getType() & i) != 0) {
                valueAt.onPerceptible(z);
            }
        }
    }

    android.view.InsetsController.Host getHost() {
        return this.mHost;
    }
}
