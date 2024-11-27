package android.app;

/* loaded from: classes.dex */
class EnterTransitionCoordinator extends android.app.ActivityTransitionCoordinator {
    private static final int MIN_ANIMATION_FRAMES = 2;
    private static final java.lang.String TAG = "EnterTransitionCoordinator";
    private android.app.Activity mActivity;
    private boolean mAreViewsReady;
    private android.animation.ObjectAnimator mBackgroundAnimator;
    private android.transition.Transition mEnterViewsTransition;
    private boolean mHasStopped;
    private boolean mIsCanceled;
    private final boolean mIsCrossTask;
    private boolean mIsExitTransitionComplete;
    private boolean mIsReadyForTransition;
    private boolean mIsTaskRoot;
    private boolean mIsViewsTransitionStarted;
    private java.lang.Runnable mOnTransitionComplete;
    private java.util.ArrayList<java.lang.String> mPendingExitNames;
    private android.graphics.drawable.Drawable mReplacedBackground;
    private boolean mSharedElementTransitionStarted;
    private android.os.Bundle mSharedElementsBundle;
    private com.android.internal.view.OneShotPreDrawListener mViewsReadyListener;
    private boolean mWasOpaque;

    EnterTransitionCoordinator(android.app.Activity activity, android.os.ResultReceiver resultReceiver, java.util.ArrayList<java.lang.String> arrayList, boolean z, boolean z2) {
        super(activity.getWindow(), arrayList, getListener(activity, z && !z2), z);
        this.mActivity = activity;
        this.mIsCrossTask = z2;
        setResultReceiver(resultReceiver);
        prepareEnter();
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable("android:remoteReceiver", this);
        this.mResultReceiver.send(100, bundle);
        final android.view.ViewGroup decor = getDecor();
        if (decor != null) {
            final android.view.ViewTreeObserver viewTreeObserver = decor.getViewTreeObserver();
            viewTreeObserver.addOnPreDrawListener(new android.view.ViewTreeObserver.OnPreDrawListener() { // from class: android.app.EnterTransitionCoordinator.1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    if (android.app.EnterTransitionCoordinator.this.mIsReadyForTransition) {
                        if (viewTreeObserver.isAlive()) {
                            viewTreeObserver.removeOnPreDrawListener(this);
                            return false;
                        }
                        decor.getViewTreeObserver().removeOnPreDrawListener(this);
                        return false;
                    }
                    return false;
                }
            });
        }
    }

    boolean isCrossTask() {
        return this.mIsCrossTask;
    }

    public void viewInstancesReady(java.util.ArrayList<java.lang.String> arrayList, java.util.ArrayList<java.lang.String> arrayList2, java.util.ArrayList<android.view.View> arrayList3) {
        boolean z = false;
        for (int i = 0; i < arrayList3.size(); i++) {
            android.view.View view = arrayList3.get(i);
            if (!android.text.TextUtils.equals(view.getTransitionName(), arrayList2.get(i)) || !view.isAttachedToWindow()) {
                z = true;
                break;
            }
        }
        if (z) {
            triggerViewsReady(mapNamedElements(arrayList, arrayList2));
        } else {
            triggerViewsReady(mapSharedElements(arrayList, arrayList3));
        }
    }

    public void namedViewsReady(java.util.ArrayList<java.lang.String> arrayList, java.util.ArrayList<java.lang.String> arrayList2) {
        triggerViewsReady(mapNamedElements(arrayList, arrayList2));
    }

    public android.transition.Transition getEnterViewsTransition() {
        return this.mEnterViewsTransition;
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected void viewsReady(android.util.ArrayMap<java.lang.String, android.view.View> arrayMap) {
        super.viewsReady(arrayMap);
        this.mIsReadyForTransition = true;
        hideViews(this.mSharedElements);
        android.transition.Transition viewsTransition = getViewsTransition();
        if (viewsTransition != null && this.mTransitioningViews != null) {
            removeExcludedViews(viewsTransition, this.mTransitioningViews);
            stripOffscreenViews();
            hideViews(this.mTransitioningViews);
        }
        if (this.mIsReturning) {
            sendSharedElementDestination();
        } else {
            moveSharedElementsToOverlay();
        }
        if (this.mSharedElementsBundle != null) {
            onTakeSharedElements();
        }
    }

    private void triggerViewsReady(final android.util.ArrayMap<java.lang.String, android.view.View> arrayMap) {
        if (this.mAreViewsReady) {
            return;
        }
        this.mAreViewsReady = true;
        android.view.ViewGroup decor = getDecor();
        if (decor == null || (decor.isAttachedToWindow() && (arrayMap.isEmpty() || !arrayMap.valueAt(0).isLayoutRequested()))) {
            viewsReady(arrayMap);
        } else {
            this.mViewsReadyListener = com.android.internal.view.OneShotPreDrawListener.add(decor, new java.lang.Runnable() { // from class: android.app.EnterTransitionCoordinator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.EnterTransitionCoordinator.this.lambda$triggerViewsReady$0(arrayMap);
                }
            });
            decor.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$triggerViewsReady$0(android.util.ArrayMap arrayMap) {
        this.mViewsReadyListener = null;
        viewsReady(arrayMap);
    }

    private android.util.ArrayMap<java.lang.String, android.view.View> mapNamedElements(java.util.ArrayList<java.lang.String> arrayList, java.util.ArrayList<java.lang.String> arrayList2) {
        android.view.View view;
        android.util.ArrayMap<java.lang.String, android.view.View> arrayMap = new android.util.ArrayMap<>();
        android.view.ViewGroup decor = getDecor();
        if (decor != null) {
            decor.findNamedViews(arrayMap);
        }
        if (arrayList != null) {
            for (int i = 0; i < arrayList2.size(); i++) {
                java.lang.String str = arrayList2.get(i);
                java.lang.String str2 = arrayList.get(i);
                if (str != null && !str.equals(str2) && (view = arrayMap.get(str)) != null) {
                    arrayMap.put(str2, view);
                }
            }
        }
        return arrayMap;
    }

    private void sendSharedElementDestination() {
        android.view.ViewGroup decor = getDecor();
        boolean z = false;
        if (!allowOverlappingTransitions() || getEnterViewsTransition() == null) {
            if (decor == null) {
                z = true;
            } else {
                boolean isLayoutRequested = true ^ decor.isLayoutRequested();
                if (isLayoutRequested) {
                    for (int i = 0; i < this.mSharedElements.size(); i++) {
                        if (this.mSharedElements.get(i).isLayoutRequested()) {
                            break;
                        }
                    }
                }
                z = isLayoutRequested;
            }
        }
        if (z) {
            android.os.Bundle captureSharedElementState = captureSharedElementState();
            moveSharedElementsToOverlay();
            this.mResultReceiver.send(107, captureSharedElementState);
        } else if (decor != null) {
            com.android.internal.view.OneShotPreDrawListener.add(decor, new java.lang.Runnable() { // from class: android.app.EnterTransitionCoordinator$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.EnterTransitionCoordinator.this.lambda$sendSharedElementDestination$1();
                }
            });
        }
        if (allowOverlappingTransitions()) {
            startEnterTransitionOnly();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendSharedElementDestination$1() {
        if (this.mResultReceiver != null) {
            android.os.Bundle captureSharedElementState = captureSharedElementState();
            moveSharedElementsToOverlay();
            this.mResultReceiver.send(107, captureSharedElementState);
        }
    }

    private static android.app.SharedElementCallback getListener(android.app.Activity activity, boolean z) {
        return z ? activity.mExitTransitionListener : activity.mEnterTransitionListener;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(int i, android.os.Bundle bundle) {
        switch (i) {
            case 103:
                if (!this.mIsCanceled) {
                    this.mSharedElementsBundle = bundle;
                    onTakeSharedElements();
                    break;
                }
                break;
            case 104:
                if (!this.mIsCanceled) {
                    this.mIsExitTransitionComplete = true;
                    if (this.mSharedElementTransitionStarted) {
                        onRemoteExitTransitionComplete();
                        break;
                    }
                }
                break;
            case 106:
                cancel();
                break;
            case 108:
                if (!this.mIsCanceled && !this.mIsTaskRoot) {
                    this.mPendingExitNames = this.mAllSharedElementNames;
                    break;
                }
                break;
        }
    }

    public boolean isWaitingForRemoteExit() {
        return this.mIsReturning && this.mResultReceiver != null;
    }

    public java.util.ArrayList<java.lang.String> getPendingExitSharedElementNames() {
        return this.mPendingExitNames;
    }

    public void forceViewsToAppear() {
        if (!this.mIsReturning) {
            return;
        }
        if (!this.mIsReadyForTransition) {
            this.mIsReadyForTransition = true;
            if (getDecor() != null && this.mViewsReadyListener != null) {
                this.mViewsReadyListener.removeListener();
                this.mViewsReadyListener = null;
            }
            showViews(this.mTransitioningViews, true);
            setTransitioningViewsVisiblity(0, true);
            this.mSharedElements.clear();
            this.mAllSharedElementNames.clear();
            this.mTransitioningViews.clear();
            this.mIsReadyForTransition = true;
            viewsTransitionComplete();
            sharedElementTransitionComplete();
        } else {
            if (!this.mSharedElementTransitionStarted) {
                moveSharedElementsFromOverlay();
                this.mSharedElementTransitionStarted = true;
                showViews(this.mSharedElements, true);
                this.mSharedElements.clear();
                sharedElementTransitionComplete();
            }
            if (!this.mIsViewsTransitionStarted) {
                this.mIsViewsTransitionStarted = true;
                showViews(this.mTransitioningViews, true);
                setTransitioningViewsVisiblity(0, true);
                this.mTransitioningViews.clear();
                viewsTransitionComplete();
            }
            cancelPendingTransitions();
        }
        this.mAreViewsReady = true;
        if (this.mResultReceiver != null) {
            this.mResultReceiver.send(106, null);
            this.mResultReceiver = null;
        }
    }

    private void cancel() {
        if (!this.mIsCanceled) {
            this.mIsCanceled = true;
            if (getViewsTransition() == null || this.mIsViewsTransitionStarted) {
                showViews(this.mSharedElements, true);
            } else if (this.mTransitioningViews != null) {
                this.mTransitioningViews.addAll(this.mSharedElements);
            }
            moveSharedElementsFromOverlay();
            this.mSharedElementNames.clear();
            this.mSharedElements.clear();
            this.mAllSharedElementNames.clear();
            startSharedElementTransition(null);
            onRemoteExitTransitionComplete();
        }
    }

    public boolean isReturning() {
        return this.mIsReturning;
    }

    protected void prepareEnter() {
        android.graphics.drawable.Drawable mutate;
        android.view.ViewGroup decor = getDecor();
        if (this.mActivity == null || decor == null) {
            return;
        }
        this.mIsTaskRoot = this.mActivity.isTaskRoot();
        if (!isCrossTask()) {
            this.mActivity.overridePendingTransition(0, 0);
        }
        if (!this.mIsReturning) {
            this.mWasOpaque = this.mActivity.convertToTranslucent(null, null);
            android.graphics.drawable.Drawable background = decor.getBackground();
            if (background == null) {
                mutate = new android.graphics.drawable.ColorDrawable(0);
                this.mReplacedBackground = mutate;
            } else {
                getWindow().setBackgroundDrawable(null);
                mutate = background.mutate();
                mutate.setAlpha(0);
            }
            getWindow().setBackgroundDrawable(mutate);
            return;
        }
        this.mActivity = null;
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected android.transition.Transition getViewsTransition() {
        android.view.Window window = getWindow();
        if (window == null) {
            return null;
        }
        if (this.mIsReturning) {
            return window.getReenterTransition();
        }
        return window.getEnterTransition();
    }

    protected android.transition.Transition getSharedElementTransition() {
        android.view.Window window = getWindow();
        if (window == null) {
            return null;
        }
        if (this.mIsReturning) {
            return window.getSharedElementReenterTransition();
        }
        return window.getSharedElementEnterTransition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSharedElementTransition(android.os.Bundle bundle) {
        android.view.ViewGroup decor = getDecor();
        if (decor == null) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mAllSharedElementNames);
        arrayList.removeAll(this.mSharedElementNames);
        java.util.ArrayList<android.view.View> createSnapshots = createSnapshots(bundle, arrayList);
        if (this.mListener != null) {
            this.mListener.onRejectSharedElements(createSnapshots);
        }
        removeNullViews(createSnapshots);
        startRejectedAnimations(createSnapshots);
        java.util.ArrayList<android.view.View> createSnapshots2 = createSnapshots(bundle, this.mSharedElementNames);
        showViews(this.mSharedElements, true);
        scheduleSetSharedElementEnd(createSnapshots2);
        java.util.ArrayList<android.app.ActivityTransitionCoordinator.SharedElementOriginalState> sharedElementState = setSharedElementState(bundle, createSnapshots2);
        requestLayoutForSharedElements();
        boolean z = allowOverlappingTransitions() && !this.mIsReturning;
        lambda$scheduleGhostVisibilityChange$1(4);
        scheduleGhostVisibilityChange(4);
        pauseInput();
        android.transition.Transition beginTransition = beginTransition(decor, z, true);
        scheduleGhostVisibilityChange(0);
        lambda$scheduleGhostVisibilityChange$1(0);
        if (z) {
            startEnterTransition(beginTransition);
        }
        setOriginalSharedElementState(this.mSharedElements, sharedElementState);
        if (this.mResultReceiver != null) {
            decor.postOnAnimation(new java.lang.Runnable() { // from class: android.app.EnterTransitionCoordinator.2
                int mAnimations;

                @Override // java.lang.Runnable
                public void run() {
                    int i = this.mAnimations;
                    this.mAnimations = i + 1;
                    if (i < 2) {
                        android.view.ViewGroup decor2 = android.app.EnterTransitionCoordinator.this.getDecor();
                        if (decor2 != null) {
                            decor2.postOnAnimation(this);
                            return;
                        }
                        return;
                    }
                    if (android.app.EnterTransitionCoordinator.this.mResultReceiver != null) {
                        android.app.EnterTransitionCoordinator.this.mResultReceiver.send(101, null);
                        android.app.EnterTransitionCoordinator.this.mResultReceiver = null;
                    }
                }
            });
        }
    }

    private static void removeNullViews(java.util.ArrayList<android.view.View> arrayList) {
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (arrayList.get(size) == null) {
                    arrayList.remove(size);
                }
            }
        }
    }

    private void onTakeSharedElements() {
        if (!this.mIsReadyForTransition || this.mSharedElementsBundle == null) {
            return;
        }
        android.os.Bundle bundle = this.mSharedElementsBundle;
        this.mSharedElementsBundle = null;
        android.app.EnterTransitionCoordinator.AnonymousClass3 anonymousClass3 = new android.app.EnterTransitionCoordinator.AnonymousClass3(bundle);
        if (this.mListener == null) {
            anonymousClass3.onSharedElementsReady();
        } else {
            this.mListener.onSharedElementsArrived(this.mSharedElementNames, this.mSharedElements, anonymousClass3);
        }
    }

    /* renamed from: android.app.EnterTransitionCoordinator$3, reason: invalid class name */
    class AnonymousClass3 implements android.app.SharedElementCallback.OnSharedElementsReadyListener {
        final /* synthetic */ android.os.Bundle val$sharedElementState;

        AnonymousClass3(android.os.Bundle bundle) {
            this.val$sharedElementState = bundle;
        }

        @Override // android.app.SharedElementCallback.OnSharedElementsReadyListener
        public void onSharedElementsReady() {
            android.view.ViewGroup decor = android.app.EnterTransitionCoordinator.this.getDecor();
            if (decor != null) {
                final android.os.Bundle bundle = this.val$sharedElementState;
                com.android.internal.view.OneShotPreDrawListener.add(decor, false, new java.lang.Runnable() { // from class: android.app.EnterTransitionCoordinator$3$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.EnterTransitionCoordinator.AnonymousClass3.this.lambda$onSharedElementsReady$1(bundle);
                    }
                });
                decor.invalidate();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSharedElementsReady$1(final android.os.Bundle bundle) {
            android.app.EnterTransitionCoordinator.this.startTransition(new java.lang.Runnable() { // from class: android.app.EnterTransitionCoordinator$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.EnterTransitionCoordinator.AnonymousClass3.this.lambda$onSharedElementsReady$0(bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSharedElementsReady$0(android.os.Bundle bundle) {
            android.app.EnterTransitionCoordinator.this.startSharedElementTransition(bundle);
        }
    }

    private void requestLayoutForSharedElements() {
        int size = this.mSharedElements.size();
        for (int i = 0; i < size; i++) {
            this.mSharedElements.get(i).requestLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.transition.Transition beginTransition(android.view.ViewGroup viewGroup, boolean z, boolean z2) {
        android.transition.Transition transition;
        android.transition.Transition transition2 = null;
        if (!z2) {
            transition = null;
        } else {
            if (!this.mSharedElementNames.isEmpty()) {
                transition = configureTransition(getSharedElementTransition(), false);
            } else {
                transition = null;
            }
            if (transition == null) {
                sharedElementTransitionStarted();
                sharedElementTransitionComplete();
            } else {
                transition.addListener(new android.transition.TransitionListenerAdapter() { // from class: android.app.EnterTransitionCoordinator.4
                    @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                    public void onTransitionStart(android.transition.Transition transition3) {
                        android.app.EnterTransitionCoordinator.this.sharedElementTransitionStarted();
                    }

                    @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                    public void onTransitionEnd(android.transition.Transition transition3) {
                        transition3.removeListener(this);
                        android.app.EnterTransitionCoordinator.this.sharedElementTransitionComplete();
                    }
                });
            }
        }
        if (z) {
            this.mIsViewsTransitionStarted = true;
            if (this.mTransitioningViews != null && !this.mTransitioningViews.isEmpty()) {
                transition2 = configureTransition(getViewsTransition(), true);
            }
            if (transition2 == null) {
                viewsTransitionComplete();
            } else {
                final java.util.ArrayList<android.view.View> arrayList = this.mTransitioningViews;
                transition2.addListener(new android.app.ActivityTransitionCoordinator.ContinueTransitionListener() { // from class: android.app.EnterTransitionCoordinator.5
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super();
                    }

                    @Override // android.app.ActivityTransitionCoordinator.ContinueTransitionListener, android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                    public void onTransitionStart(android.transition.Transition transition3) {
                        android.app.EnterTransitionCoordinator.this.mEnterViewsTransition = transition3;
                        if (arrayList != null) {
                            android.app.EnterTransitionCoordinator.this.showViews(arrayList, false);
                        }
                        super.onTransitionStart(transition3);
                    }

                    @Override // android.app.ActivityTransitionCoordinator.ContinueTransitionListener, android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                    public void onTransitionEnd(android.transition.Transition transition3) {
                        android.app.EnterTransitionCoordinator.this.mEnterViewsTransition = null;
                        transition3.removeListener(this);
                        android.app.EnterTransitionCoordinator.this.viewsTransitionComplete();
                        super.onTransitionEnd(transition3);
                    }
                });
            }
        }
        android.transition.Transition mergeTransitions = mergeTransitions(transition, transition2);
        if (mergeTransitions != null) {
            mergeTransitions.addListener(new android.app.ActivityTransitionCoordinator.ContinueTransitionListener());
            if (z) {
                setTransitioningViewsVisiblity(4, false);
            }
            android.transition.TransitionManager.beginDelayedTransition(viewGroup, mergeTransitions);
            if (z) {
                setTransitioningViewsVisiblity(0, false);
            }
            viewGroup.invalidate();
        } else {
            transitionStarted();
        }
        return mergeTransitions;
    }

    public void runAfterTransitionsComplete(java.lang.Runnable runnable) {
        if (!isTransitionRunning()) {
            onTransitionsComplete();
        } else {
            this.mOnTransitionComplete = runnable;
        }
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected void onTransitionsComplete() {
        moveSharedElementsFromOverlay();
        android.view.ViewGroup decor = getDecor();
        if (decor != null) {
            decor.sendAccessibilityEvent(2048);
            android.view.Window window = getWindow();
            if (window != null && this.mReplacedBackground == decor.getBackground()) {
                window.setBackgroundDrawable(null);
            }
        }
        if (this.mOnTransitionComplete != null) {
            this.mOnTransitionComplete.run();
            this.mOnTransitionComplete = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sharedElementTransitionStarted() {
        this.mSharedElementTransitionStarted = true;
        if (this.mIsExitTransitionComplete) {
            send(104, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startEnterTransition(android.transition.Transition transition) {
        android.view.ViewGroup decor = getDecor();
        if (!this.mIsReturning && decor != null) {
            android.graphics.drawable.Drawable background = decor.getBackground();
            if (background != null) {
                android.graphics.drawable.Drawable mutate = background.mutate();
                getWindow().setBackgroundDrawable(mutate);
                this.mBackgroundAnimator = android.animation.ObjectAnimator.ofInt(mutate, "alpha", 255);
                this.mBackgroundAnimator.setDuration(getFadeDuration());
                this.mBackgroundAnimator.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.app.EnterTransitionCoordinator.6
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(android.animation.Animator animator) {
                        android.app.EnterTransitionCoordinator.this.makeOpaque();
                        android.app.EnterTransitionCoordinator.this.backgroundAnimatorComplete();
                    }
                });
                this.mBackgroundAnimator.start();
                return;
            }
            if (transition != null) {
                transition.addListener(new android.transition.TransitionListenerAdapter() { // from class: android.app.EnterTransitionCoordinator.7
                    @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                    public void onTransitionEnd(android.transition.Transition transition2) {
                        transition2.removeListener(this);
                        android.app.EnterTransitionCoordinator.this.makeOpaque();
                    }
                });
                backgroundAnimatorComplete();
                return;
            } else {
                makeOpaque();
                backgroundAnimatorComplete();
                return;
            }
        }
        backgroundAnimatorComplete();
    }

    public void stop() {
        android.view.ViewGroup decor;
        android.graphics.drawable.Drawable background;
        if (this.mBackgroundAnimator != null) {
            this.mBackgroundAnimator.end();
            this.mBackgroundAnimator = null;
        } else if (this.mWasOpaque && (decor = getDecor()) != null && (background = decor.getBackground()) != null) {
            background.setAlpha(255);
        }
        makeOpaque();
        this.mIsCanceled = true;
        this.mResultReceiver = null;
        this.mActivity = null;
        moveSharedElementsFromOverlay();
        if (this.mTransitioningViews != null) {
            showViews(this.mTransitioningViews, true);
            setTransitioningViewsVisiblity(0, true);
        }
        showViews(this.mSharedElements, true);
        clearState();
    }

    public boolean cancelEnter() {
        lambda$scheduleGhostVisibilityChange$1(4);
        this.mHasStopped = true;
        this.mIsCanceled = true;
        clearState();
        return super.cancelPendingTransitions();
    }

    @Override // android.app.ActivityTransitionCoordinator
    protected void clearState() {
        this.mSharedElementsBundle = null;
        this.mEnterViewsTransition = null;
        this.mResultReceiver = null;
        if (this.mBackgroundAnimator != null) {
            this.mBackgroundAnimator.cancel();
            this.mBackgroundAnimator = null;
        }
        if (this.mOnTransitionComplete != null) {
            this.mOnTransitionComplete.run();
            this.mOnTransitionComplete = null;
        }
        super.clearState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void makeOpaque() {
        if (!this.mHasStopped && this.mActivity != null) {
            if (this.mWasOpaque) {
                this.mActivity.convertFromTranslucent();
            }
            this.mActivity = null;
        }
    }

    private boolean allowOverlappingTransitions() {
        android.view.Window window = getWindow();
        if (window == null) {
            return false;
        }
        return this.mIsReturning ? window.getAllowReturnTransitionOverlap() : window.getAllowEnterTransitionOverlap();
    }

    private void startRejectedAnimations(final java.util.ArrayList<android.view.View> arrayList) {
        final android.view.ViewGroup decor;
        if (arrayList != null && !arrayList.isEmpty() && (decor = getDecor()) != null) {
            android.view.ViewGroupOverlay overlay = decor.getOverlay();
            int size = arrayList.size();
            android.animation.ObjectAnimator objectAnimator = null;
            for (int i = 0; i < size; i++) {
                android.view.View view = arrayList.get(i);
                overlay.add(view);
                objectAnimator = android.animation.ObjectAnimator.ofFloat(view, android.view.View.ALPHA, 1.0f, 0.0f);
                objectAnimator.start();
            }
            objectAnimator.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.app.EnterTransitionCoordinator.8
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(android.animation.Animator animator) {
                    android.view.ViewGroupOverlay overlay2 = decor.getOverlay();
                    int size2 = arrayList.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        overlay2.remove((android.view.View) arrayList.get(i2));
                    }
                }
            });
        }
    }

    protected void onRemoteExitTransitionComplete() {
        if (!allowOverlappingTransitions()) {
            startEnterTransitionOnly();
        }
    }

    private void startEnterTransitionOnly() {
        startTransition(new java.lang.Runnable() { // from class: android.app.EnterTransitionCoordinator.9
            @Override // java.lang.Runnable
            public void run() {
                android.view.ViewGroup decor = android.app.EnterTransitionCoordinator.this.getDecor();
                if (decor != null) {
                    android.app.EnterTransitionCoordinator.this.startEnterTransition(android.app.EnterTransitionCoordinator.this.beginTransition(decor, true, false));
                }
            }
        });
    }
}
