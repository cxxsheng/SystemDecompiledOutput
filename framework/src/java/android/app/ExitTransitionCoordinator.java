package android.app;

/* loaded from: classes.dex */
public class ExitTransitionCoordinator extends android.app.ActivityTransitionCoordinator {
    private static final java.lang.String TAG = "ExitTransitionCoordinator";
    static long sMaxWaitMillis = 1000;
    private android.animation.ObjectAnimator mBackgroundAnimator;
    private android.app.ExitTransitionCoordinator.ExitTransitionCallbacks mExitCallbacks;
    private boolean mExitNotified;
    private android.os.Bundle mExitSharedElementBundle;
    private android.os.Handler mHandler;
    private boolean mIsBackgroundReady;
    private boolean mIsCanceled;
    private boolean mIsExitStarted;
    private boolean mIsHidden;
    private android.os.Bundle mSharedElementBundle;
    private boolean mSharedElementNotified;
    private boolean mSharedElementsHidden;

    @Override // android.app.ActivityTransitionCoordinator
    public /* bridge */ /* synthetic */ java.util.ArrayList copyMappedViews() {
        return super.copyMappedViews();
    }

    @Override // android.app.ActivityTransitionCoordinator
    public /* bridge */ /* synthetic */ java.util.ArrayList getAcceptedNames() {
        return super.getAcceptedNames();
    }

    @Override // android.app.ActivityTransitionCoordinator
    public /* bridge */ /* synthetic */ android.view.ViewGroup getDecor() {
        return super.getDecor();
    }

    @Override // android.app.ActivityTransitionCoordinator
    public /* bridge */ /* synthetic */ java.util.ArrayList getMappedNames() {
        return super.getMappedNames();
    }

    @Override // android.app.ActivityTransitionCoordinator
    public /* bridge */ /* synthetic */ boolean isTransitionRunning() {
        return super.isTransitionRunning();
    }

    public ExitTransitionCoordinator(android.app.ExitTransitionCoordinator.ExitTransitionCallbacks exitTransitionCallbacks, android.view.Window window, android.app.SharedElementCallback sharedElementCallback, java.util.ArrayList<java.lang.String> arrayList, java.util.ArrayList<java.lang.String> arrayList2, java.util.ArrayList<android.view.View> arrayList3, boolean z) {
        super(window, arrayList, sharedElementCallback, z);
        viewsReady(mapSharedElements(arrayList2, arrayList3));
        stripOffscreenViews();
        this.mIsBackgroundReady = !z;
        this.mExitCallbacks = exitTransitionCallbacks;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(int i, android.os.Bundle bundle) {
        switch (i) {
            case 100:
                stopCancel();
                this.mResultReceiver = (android.os.ResultReceiver) bundle.getParcelable("android:remoteReceiver", android.os.ResultReceiver.class);
                if (this.mIsCanceled) {
                    this.mResultReceiver.send(106, null);
                    this.mResultReceiver = null;
                    break;
                } else {
                    notifyComplete();
                    break;
                }
            case 101:
                stopCancel();
                if (!this.mIsCanceled) {
                    hideSharedElements();
                    break;
                }
                break;
            case 105:
                this.mHandler.removeMessages(106);
                startExit();
                break;
            case 106:
                this.mIsCanceled = true;
                finish();
                break;
            case 107:
                this.mExitSharedElementBundle = bundle;
                sharedElementExitBack();
                break;
        }
    }

    private void stopCancel() {
        if (this.mHandler != null) {
            this.mHandler.removeMessages(106);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delayCancel() {
        if (this.mHandler != null) {
            this.mHandler.sendEmptyMessageDelayed(106, sMaxWaitMillis);
        }
    }

    public void resetViews() {
        android.view.ViewGroup decor = getDecor();
        if (decor != null) {
            android.transition.TransitionManager.endTransitions(decor);
        }
        if (this.mTransitioningViews != null) {
            showViews(this.mTransitioningViews, true);
            setTransitioningViewsVisiblity(0, true);
        }
        showViews(this.mSharedElements, true);
        this.mIsHidden = true;
        if (!this.mIsReturning && decor != null) {
            decor.suppressLayout(false);
        }
        moveSharedElementsFromOverlay();
        clearState();
    }

    private void sharedElementExitBack() {
        final android.view.ViewGroup decor = getDecor();
        if (decor != null) {
            decor.suppressLayout(true);
        }
        if (decor != null && this.mExitSharedElementBundle != null && !this.mExitSharedElementBundle.isEmpty() && !this.mSharedElements.isEmpty() && getSharedElementTransition() != null) {
            startTransition(new java.lang.Runnable() { // from class: android.app.ExitTransitionCoordinator.1
                @Override // java.lang.Runnable
                public void run() {
                    android.app.ExitTransitionCoordinator.this.startSharedElementExit(decor);
                }
            });
        } else {
            sharedElementTransitionComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSharedElementExit(android.view.ViewGroup viewGroup) {
        android.transition.Transition sharedElementExitTransition = getSharedElementExitTransition();
        sharedElementExitTransition.addListener(new android.transition.TransitionListenerAdapter() { // from class: android.app.ExitTransitionCoordinator.2
            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionEnd(android.transition.Transition transition) {
                transition.removeListener(this);
                if (android.app.ExitTransitionCoordinator.this.isViewsTransitionComplete()) {
                    android.app.ExitTransitionCoordinator.this.delayCancel();
                }
            }
        });
        final java.util.ArrayList<android.view.View> createSnapshots = createSnapshots(this.mExitSharedElementBundle, this.mSharedElementNames);
        com.android.internal.view.OneShotPreDrawListener.add(viewGroup, new java.lang.Runnable() { // from class: android.app.ExitTransitionCoordinator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.app.ExitTransitionCoordinator.this.lambda$startSharedElementExit$0(createSnapshots);
            }
        });
        lambda$scheduleGhostVisibilityChange$1(4);
        scheduleGhostVisibilityChange(4);
        if (this.mListener != null) {
            this.mListener.onSharedElementEnd(this.mSharedElementNames, this.mSharedElements, createSnapshots);
        }
        android.transition.TransitionManager.beginDelayedTransition(viewGroup, sharedElementExitTransition);
        scheduleGhostVisibilityChange(0);
        lambda$scheduleGhostVisibilityChange$1(0);
        viewGroup.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startSharedElementExit$0(java.util.ArrayList arrayList) {
        setSharedElementState(this.mExitSharedElementBundle, arrayList);
    }

    private void hideSharedElements() {
        moveSharedElementsFromOverlay();
        if (this.mExitCallbacks != null) {
            this.mExitCallbacks.hideSharedElements();
        }
        if (!this.mIsHidden) {
            hideViews(this.mSharedElements);
        }
        this.mSharedElementsHidden = true;
        finishIfNecessary();
    }

    public void startExit() {
        if (!this.mIsExitStarted) {
            backgroundAnimatorComplete();
            this.mIsExitStarted = true;
            pauseInput();
            android.view.ViewGroup decor = getDecor();
            if (decor != null) {
                decor.suppressLayout(true);
            }
            moveSharedElementsToOverlay();
            startTransition(new java.lang.Runnable() { // from class: android.app.ExitTransitionCoordinator$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.ExitTransitionCoordinator.this.beginTransitions();
                }
            });
        }
    }

    public void startExit(android.app.Activity activity) {
        int i = activity.mResultCode;
        android.content.Intent intent = activity.mResultData;
        if (!this.mIsExitStarted) {
            boolean z = true;
            this.mIsExitStarted = true;
            pauseInput();
            android.view.ViewGroup decor = getDecor();
            if (decor != null) {
                decor.suppressLayout(true);
            }
            this.mHandler = new android.os.Handler() { // from class: android.app.ExitTransitionCoordinator.3
                @Override // android.os.Handler
                public void handleMessage(android.os.Message message) {
                    android.app.ExitTransitionCoordinator.this.mIsCanceled = true;
                    android.app.ExitTransitionCoordinator.this.finish();
                }
            };
            delayCancel();
            moveSharedElementsToOverlay();
            if (decor != null && decor.getBackground() == null) {
                getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(0));
            }
            if (decor != null && decor.getContext().getApplicationInfo().targetSdkVersion < 23) {
                z = false;
            }
            activity.convertToTranslucent(new android.app.Activity.TranslucentConversionListener() { // from class: android.app.ExitTransitionCoordinator.4
                @Override // android.app.Activity.TranslucentConversionListener
                public void onTranslucentConversionComplete(boolean z2) {
                    if (!android.app.ExitTransitionCoordinator.this.mIsCanceled) {
                        android.app.ExitTransitionCoordinator.this.fadeOutBackground();
                    }
                }
            }, android.app.ActivityOptions.makeSceneTransitionAnimation(activity, this, z ? this.mSharedElementNames : this.mAllSharedElementNames, i, intent));
            startTransition(new java.lang.Runnable() { // from class: android.app.ExitTransitionCoordinator$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.ExitTransitionCoordinator.this.startExitTransition();
                }
            });
        }
    }

    public void stop(android.app.Activity activity) {
        if (this.mIsReturning && this.mExitCallbacks != null) {
            activity.convertToTranslucent(null, null);
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startExitTransition() {
        android.transition.Transition exitTransition = getExitTransition();
        android.view.ViewGroup decor = getDecor();
        if (exitTransition != null && decor != null && this.mTransitioningViews != null) {
            setTransitioningViewsVisiblity(0, false);
            android.transition.TransitionManager.beginDelayedTransition(decor, exitTransition);
            setTransitioningViewsVisiblity(4, false);
            decor.invalidate();
            return;
        }
        transitionStarted();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fadeOutBackground() {
        android.graphics.drawable.Drawable background;
        if (this.mBackgroundAnimator == null) {
            android.view.ViewGroup decor = getDecor();
            if (decor != null && (background = decor.getBackground()) != null) {
                android.graphics.drawable.Drawable mutate = background.mutate();
                getWindow().setBackgroundDrawable(mutate);
                this.mBackgroundAnimator = android.animation.ObjectAnimator.ofInt(mutate, "alpha", 0);
                this.mBackgroundAnimator.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.app.ExitTransitionCoordinator.5
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(android.animation.Animator animator) {
                        android.app.ExitTransitionCoordinator.this.mBackgroundAnimator = null;
                        if (!android.app.ExitTransitionCoordinator.this.mIsCanceled) {
                            android.app.ExitTransitionCoordinator.this.mIsBackgroundReady = true;
                            android.app.ExitTransitionCoordinator.this.notifyComplete();
                        }
                        android.app.ExitTransitionCoordinator.this.backgroundAnimatorComplete();
                    }
                });
                this.mBackgroundAnimator.setDuration(getFadeDuration());
                this.mBackgroundAnimator.start();
                return;
            }
            backgroundAnimatorComplete();
            this.mIsBackgroundReady = true;
        }
    }

    private android.transition.Transition getExitTransition() {
        android.transition.Transition transition = null;
        if (this.mTransitioningViews != null && !this.mTransitioningViews.isEmpty()) {
            android.transition.Transition configureTransition = configureTransition(getViewsTransition(), true);
            removeExcludedViews(configureTransition, this.mTransitioningViews);
            if (!this.mTransitioningViews.isEmpty()) {
                transition = configureTransition;
            }
        }
        if (transition == null) {
            viewsTransitionComplete();
        } else {
            final java.util.ArrayList<android.view.View> arrayList = this.mTransitioningViews;
            transition.addListener(new android.app.ActivityTransitionCoordinator.ContinueTransitionListener() { // from class: android.app.ExitTransitionCoordinator.6
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // android.app.ActivityTransitionCoordinator.ContinueTransitionListener, android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionEnd(android.transition.Transition transition2) {
                    android.app.ExitTransitionCoordinator.this.viewsTransitionComplete();
                    if (android.app.ExitTransitionCoordinator.this.mIsHidden && arrayList != null) {
                        android.app.ExitTransitionCoordinator.this.showViews(arrayList, true);
                        android.app.ExitTransitionCoordinator.this.setTransitioningViewsVisiblity(0, true);
                    }
                    if (android.app.ExitTransitionCoordinator.this.mSharedElementBundle != null) {
                        android.app.ExitTransitionCoordinator.this.delayCancel();
                    }
                    super.onTransitionEnd(transition2);
                }
            });
        }
        return transition;
    }

    private android.transition.Transition getSharedElementExitTransition() {
        android.transition.Transition transition;
        if (this.mSharedElements.isEmpty()) {
            transition = null;
        } else {
            transition = configureTransition(getSharedElementTransition(), false);
        }
        if (transition == null) {
            sharedElementTransitionComplete();
        } else {
            transition.addListener(new android.app.ActivityTransitionCoordinator.ContinueTransitionListener() { // from class: android.app.ExitTransitionCoordinator.7
                @Override // android.app.ActivityTransitionCoordinator.ContinueTransitionListener, android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionEnd(android.transition.Transition transition2) {
                    android.app.ExitTransitionCoordinator.this.sharedElementTransitionComplete();
                    if (android.app.ExitTransitionCoordinator.this.mIsHidden) {
                        android.app.ExitTransitionCoordinator.this.showViews(android.app.ExitTransitionCoordinator.this.mSharedElements, true);
                    }
                    super.onTransitionEnd(transition2);
                }
            });
            this.mSharedElements.get(0).invalidate();
        }
        return transition;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void beginTransitions() {
        android.transition.Transition sharedElementExitTransition = getSharedElementExitTransition();
        android.transition.Transition exitTransition = getExitTransition();
        android.transition.Transition mergeTransitions = mergeTransitions(sharedElementExitTransition, exitTransition);
        android.view.ViewGroup decor = getDecor();
        if (mergeTransitions != null && decor != null) {
            lambda$scheduleGhostVisibilityChange$1(4);
            scheduleGhostVisibilityChange(4);
            if (exitTransition != null) {
                setTransitioningViewsVisiblity(0, false);
            }
            android.transition.TransitionManager.beginDelayedTransition(decor, mergeTransitions);
            scheduleGhostVisibilityChange(0);
            lambda$scheduleGhostVisibilityChange$1(0);
            if (exitTransition != null) {
                setTransitioningViewsVisiblity(4, false);
            }
            decor.invalidate();
            return;
        }
        transitionStarted();
    }

    protected boolean isReadyToNotify() {
        return (this.mSharedElementBundle == null || this.mResultReceiver == null || !this.mIsBackgroundReady) ? false : true;
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected void sharedElementTransitionComplete() {
        this.mSharedElementBundle = this.mExitSharedElementBundle == null ? captureSharedElementState() : captureExitSharedElementsState();
        super.sharedElementTransitionComplete();
    }

    private android.os.Bundle captureExitSharedElementsState() {
        android.os.Bundle bundle = new android.os.Bundle();
        android.graphics.RectF rectF = new android.graphics.RectF();
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        for (int i = 0; i < this.mSharedElements.size(); i++) {
            java.lang.String str = this.mSharedElementNames.get(i);
            android.os.Bundle bundle2 = this.mExitSharedElementBundle.getBundle(str);
            if (bundle2 != null) {
                bundle.putBundle(str, bundle2);
            } else {
                captureSharedElementState(this.mSharedElements.get(i), str, bundle, matrix, rectF);
            }
        }
        return bundle;
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected void onTransitionsComplete() {
        notifyComplete();
    }

    protected void notifyComplete() {
        if (isReadyToNotify()) {
            if (!this.mSharedElementNotified) {
                this.mSharedElementNotified = true;
                delayCancel();
                if (this.mExitCallbacks != null && this.mExitCallbacks.isReturnTransitionAllowed()) {
                    this.mResultReceiver.send(108, null);
                }
                if (this.mListener == null) {
                    this.mResultReceiver.send(103, this.mSharedElementBundle);
                    notifyExitComplete();
                    return;
                } else {
                    final android.os.ResultReceiver resultReceiver = this.mResultReceiver;
                    final android.os.Bundle bundle = this.mSharedElementBundle;
                    this.mListener.onSharedElementsArrived(this.mSharedElementNames, this.mSharedElements, new android.app.SharedElementCallback.OnSharedElementsReadyListener() { // from class: android.app.ExitTransitionCoordinator.8
                        @Override // android.app.SharedElementCallback.OnSharedElementsReadyListener
                        public void onSharedElementsReady() {
                            resultReceiver.send(103, bundle);
                            android.app.ExitTransitionCoordinator.this.notifyExitComplete();
                        }
                    });
                    return;
                }
            }
            notifyExitComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyExitComplete() {
        if (!this.mExitNotified && isViewsTransitionComplete()) {
            this.mExitNotified = true;
            this.mResultReceiver.send(104, null);
            this.mResultReceiver = null;
            android.view.ViewGroup decor = getDecor();
            if (!this.mIsReturning && decor != null) {
                decor.suppressLayout(false);
            }
            finishIfNecessary();
        }
    }

    private void finishIfNecessary() {
        if (this.mIsReturning && this.mExitNotified && this.mExitCallbacks != null) {
            if (this.mSharedElements.isEmpty() || this.mSharedElementsHidden) {
                finish();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish() {
        stopCancel();
        if (this.mExitCallbacks != null) {
            this.mExitCallbacks.onFinish();
            this.mExitCallbacks = null;
        }
        clearState();
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected void clearState() {
        this.mHandler = null;
        this.mSharedElementBundle = null;
        if (this.mBackgroundAnimator != null) {
            this.mBackgroundAnimator.cancel();
            this.mBackgroundAnimator = null;
        }
        this.mExitSharedElementBundle = null;
        super.clearState();
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected boolean moveSharedElementWithParent() {
        return !this.mIsReturning;
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected android.transition.Transition getViewsTransition() {
        if (this.mIsReturning) {
            return getWindow().getReturnTransition();
        }
        return getWindow().getExitTransition();
    }

    protected android.transition.Transition getSharedElementTransition() {
        if (this.mIsReturning) {
            return getWindow().getSharedElementReturnTransition();
        }
        return getWindow().getSharedElementExitTransition();
    }

    public interface ExitTransitionCallbacks {
        boolean isReturnTransitionAllowed();

        void onFinish();

        default void hideSharedElements() {
        }
    }

    public static class ActivityExitTransitionCallbacks implements android.app.ExitTransitionCoordinator.ExitTransitionCallbacks {
        final android.app.Activity mActivity;

        ActivityExitTransitionCallbacks(android.app.Activity activity) {
            this.mActivity = activity;
        }

        @Override // android.app.ExitTransitionCoordinator.ExitTransitionCallbacks
        public boolean isReturnTransitionAllowed() {
            return true;
        }

        @Override // android.app.ExitTransitionCoordinator.ExitTransitionCallbacks
        public void onFinish() {
            this.mActivity.mActivityTransitionState.clear();
            this.mActivity.finish();
            this.mActivity.overridePendingTransition(0, 0);
        }
    }
}
