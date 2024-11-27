package android.app;

/* loaded from: classes.dex */
class ActivityTransitionState {
    private static final java.lang.String EXITING_MAPPED_FROM = "android:exitingMappedFrom";
    private static final java.lang.String EXITING_MAPPED_TO = "android:exitingMappedTo";
    private static final java.lang.String PENDING_EXIT_SHARED_ELEMENTS = "android:pendingExitSharedElements";
    private android.app.ExitTransitionCoordinator mCalledExitCoordinator;
    private android.app.ActivityOptions.SceneTransitionInfo mEnterSceneTransitionInfo;
    private android.app.EnterTransitionCoordinator mEnterTransitionCoordinator;
    private android.util.SparseArray<java.lang.ref.WeakReference<android.app.ExitTransitionCoordinator>> mExitTransitionCoordinators;
    private int mExitTransitionCoordinatorsKey = 1;
    private java.util.ArrayList<java.lang.String> mExitingFrom;
    private java.util.ArrayList<java.lang.String> mExitingTo;
    private java.util.ArrayList<android.view.View> mExitingToView;
    private boolean mHasExited;
    private boolean mIsEnterPostponed;
    private boolean mIsEnterTriggered;
    private java.util.ArrayList<java.lang.String> mPendingExitNames;
    private android.app.ExitTransitionCoordinator mReturnExitCoordinator;

    public int addExitTransitionCoordinator(android.app.ExitTransitionCoordinator exitTransitionCoordinator) {
        if (this.mExitTransitionCoordinators == null) {
            this.mExitTransitionCoordinators = new android.util.SparseArray<>();
        }
        java.lang.ref.WeakReference<android.app.ExitTransitionCoordinator> weakReference = new java.lang.ref.WeakReference<>(exitTransitionCoordinator);
        for (int size = this.mExitTransitionCoordinators.size() - 1; size >= 0; size--) {
            if (this.mExitTransitionCoordinators.valueAt(size).refersTo(null)) {
                this.mExitTransitionCoordinators.removeAt(size);
            }
        }
        int i = this.mExitTransitionCoordinatorsKey;
        this.mExitTransitionCoordinatorsKey = i + 1;
        this.mExitTransitionCoordinators.append(i, weakReference);
        return i;
    }

    public void readState(android.os.Bundle bundle) {
        if (bundle != null) {
            if (this.mEnterTransitionCoordinator == null || this.mEnterTransitionCoordinator.isReturning()) {
                this.mPendingExitNames = bundle.getStringArrayList(PENDING_EXIT_SHARED_ELEMENTS);
            }
            if (this.mEnterTransitionCoordinator == null) {
                this.mExitingFrom = bundle.getStringArrayList(EXITING_MAPPED_FROM);
                this.mExitingTo = bundle.getStringArrayList(EXITING_MAPPED_TO);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.ArrayList<java.lang.String> getPendingExitNames() {
        if (this.mPendingExitNames == null && this.mEnterTransitionCoordinator != null && !this.mEnterTransitionCoordinator.isReturning()) {
            this.mPendingExitNames = this.mEnterTransitionCoordinator.getPendingExitSharedElementNames();
        }
        return this.mPendingExitNames;
    }

    public void saveState(android.os.Bundle bundle) {
        java.util.ArrayList<java.lang.String> pendingExitNames = getPendingExitNames();
        if (pendingExitNames != null) {
            bundle.putStringArrayList(PENDING_EXIT_SHARED_ELEMENTS, pendingExitNames);
        }
        if (this.mExitingFrom != null) {
            bundle.putStringArrayList(EXITING_MAPPED_FROM, this.mExitingFrom);
            bundle.putStringArrayList(EXITING_MAPPED_TO, this.mExitingTo);
        }
    }

    public void setEnterSceneTransitionInfo(android.app.Activity activity, android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo) {
        android.view.Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        window.getDecorView();
        if (window.hasFeature(13) && sceneTransitionInfo != null && this.mEnterSceneTransitionInfo == null && this.mEnterTransitionCoordinator == null) {
            this.mEnterSceneTransitionInfo = sceneTransitionInfo;
            this.mIsEnterTriggered = false;
            if (this.mEnterSceneTransitionInfo.isReturning()) {
                restoreExitedViews();
                int resultCode = this.mEnterSceneTransitionInfo.getResultCode();
                if (resultCode != 0) {
                    android.content.Intent resultData = this.mEnterSceneTransitionInfo.getResultData();
                    if (resultData != null) {
                        resultData.setExtrasClassLoader(activity.getClassLoader());
                    }
                    activity.onActivityReenter(resultCode, resultData);
                }
            }
        }
    }

    public void enterReady(android.app.Activity activity) {
        if (this.mEnterSceneTransitionInfo == null || this.mIsEnterTriggered) {
            return;
        }
        this.mIsEnterTriggered = true;
        this.mHasExited = false;
        java.util.ArrayList<java.lang.String> sharedElementNames = this.mEnterSceneTransitionInfo.getSharedElementNames();
        android.os.ResultReceiver resultReceiver = this.mEnterSceneTransitionInfo.getResultReceiver();
        if (this.mEnterSceneTransitionInfo.isReturning()) {
            restoreExitedViews();
            activity.getWindow().getDecorView().setVisibility(0);
        }
        getPendingExitNames();
        this.mEnterTransitionCoordinator = new android.app.EnterTransitionCoordinator(activity, resultReceiver, sharedElementNames, this.mEnterSceneTransitionInfo.isReturning(), this.mEnterSceneTransitionInfo.isCrossTask());
        if (this.mEnterSceneTransitionInfo.isCrossTask() && sharedElementNames != null) {
            this.mExitingFrom = new java.util.ArrayList<>(sharedElementNames);
            this.mExitingTo = new java.util.ArrayList<>(sharedElementNames);
        }
        if (!this.mIsEnterPostponed) {
            startEnter();
        }
    }

    public void postponeEnterTransition() {
        this.mIsEnterPostponed = true;
    }

    public void startPostponedEnterTransition() {
        if (this.mIsEnterPostponed) {
            this.mIsEnterPostponed = false;
            if (this.mEnterTransitionCoordinator != null) {
                startEnter();
            }
        }
    }

    private void startEnter() {
        if (this.mEnterTransitionCoordinator.isReturning()) {
            if (this.mExitingToView != null) {
                this.mEnterTransitionCoordinator.viewInstancesReady(this.mExitingFrom, this.mExitingTo, this.mExitingToView);
            } else {
                this.mEnterTransitionCoordinator.namedViewsReady(this.mExitingFrom, this.mExitingTo);
            }
        } else {
            this.mEnterTransitionCoordinator.namedViewsReady(null, null);
            this.mPendingExitNames = null;
        }
        this.mExitingFrom = null;
        this.mExitingTo = null;
        this.mExitingToView = null;
        this.mEnterSceneTransitionInfo = null;
    }

    public void onStop(android.app.Activity activity) {
        restoreExitedViews();
        if (this.mEnterTransitionCoordinator != null) {
            getPendingExitNames();
            this.mEnterTransitionCoordinator.stop();
            this.mEnterTransitionCoordinator = null;
        }
        if (this.mReturnExitCoordinator != null) {
            this.mReturnExitCoordinator.stop(activity);
            this.mReturnExitCoordinator = null;
        }
    }

    public void onResume(android.app.Activity activity) {
        if (this.mEnterTransitionCoordinator == null || activity.isTopOfTask()) {
            restoreExitedViews();
            restoreReenteringViews();
        } else {
            activity.mHandler.postDelayed(new android.app.ActivityTransitionState.AnonymousClass1(), 1000L);
        }
    }

    /* renamed from: android.app.ActivityTransitionState$1, reason: invalid class name */
    class AnonymousClass1 implements java.lang.Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (android.app.ActivityTransitionState.this.mEnterTransitionCoordinator == null || android.app.ActivityTransitionState.this.mEnterTransitionCoordinator.isWaitingForRemoteExit()) {
                android.app.ActivityTransitionState.this.restoreExitedViews();
                android.app.ActivityTransitionState.this.restoreReenteringViews();
            } else if (android.app.ActivityTransitionState.this.mEnterTransitionCoordinator.isReturning()) {
                android.app.ActivityTransitionState.this.mEnterTransitionCoordinator.runAfterTransitionsComplete(new java.lang.Runnable() { // from class: android.app.ActivityTransitionState$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.ActivityTransitionState.AnonymousClass1.this.lambda$run$0();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0() {
            android.app.ActivityTransitionState.this.getPendingExitNames();
            android.app.ActivityTransitionState.this.mEnterTransitionCoordinator = null;
        }
    }

    public void clear() {
        this.mPendingExitNames = null;
        this.mExitingFrom = null;
        this.mExitingTo = null;
        this.mExitingToView = null;
        this.mCalledExitCoordinator = null;
        this.mEnterTransitionCoordinator = null;
        this.mEnterSceneTransitionInfo = null;
        this.mExitTransitionCoordinators = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreExitedViews() {
        if (this.mCalledExitCoordinator != null) {
            this.mCalledExitCoordinator.resetViews();
            this.mCalledExitCoordinator = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreReenteringViews() {
        if (this.mEnterTransitionCoordinator != null && this.mEnterTransitionCoordinator.isReturning() && !this.mEnterTransitionCoordinator.isCrossTask()) {
            this.mEnterTransitionCoordinator.forceViewsToAppear();
            this.mExitingFrom = null;
            this.mExitingTo = null;
            this.mExitingToView = null;
        }
    }

    public boolean startExitBackTransition(final android.app.Activity activity) {
        boolean z;
        android.transition.Transition transition;
        android.view.ViewGroup viewGroup;
        java.util.ArrayList<java.lang.String> pendingExitNames = getPendingExitNames();
        if (pendingExitNames == null || this.mCalledExitCoordinator != null) {
            return false;
        }
        if (!this.mHasExited) {
            this.mHasExited = true;
            if (this.mEnterTransitionCoordinator == null) {
                z = false;
                transition = null;
                viewGroup = null;
            } else {
                android.transition.Transition enterViewsTransition = this.mEnterTransitionCoordinator.getEnterViewsTransition();
                android.view.ViewGroup decor = this.mEnterTransitionCoordinator.getDecor();
                boolean cancelEnter = this.mEnterTransitionCoordinator.cancelEnter();
                this.mEnterTransitionCoordinator = null;
                if (enterViewsTransition != null && decor != null) {
                    enterViewsTransition.pause(decor);
                }
                transition = enterViewsTransition;
                viewGroup = decor;
                z = cancelEnter;
            }
            this.mReturnExitCoordinator = new android.app.ExitTransitionCoordinator(new android.app.ExitTransitionCoordinator.ActivityExitTransitionCallbacks(activity), activity.getWindow(), activity.mEnterTransitionListener, pendingExitNames, null, null, true);
            if (transition != null && viewGroup != null) {
                transition.resume(viewGroup);
            }
            if (z && viewGroup != null) {
                com.android.internal.view.OneShotPreDrawListener.add(viewGroup, new java.lang.Runnable() { // from class: android.app.ActivityTransitionState$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.ActivityTransitionState.this.lambda$startExitBackTransition$0(activity);
                    }
                });
            } else {
                this.mReturnExitCoordinator.startExit(activity);
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startExitBackTransition$0(android.app.Activity activity) {
        if (this.mReturnExitCoordinator != null) {
            this.mReturnExitCoordinator.startExit(activity);
        }
    }

    public boolean isTransitionRunning() {
        if (this.mEnterTransitionCoordinator != null && this.mEnterTransitionCoordinator.isTransitionRunning()) {
            return true;
        }
        if (this.mCalledExitCoordinator == null || !this.mCalledExitCoordinator.isTransitionRunning()) {
            return this.mReturnExitCoordinator != null && this.mReturnExitCoordinator.isTransitionRunning();
        }
        return true;
    }

    public void startExitOutTransition(android.app.Activity activity, android.os.Bundle bundle) {
        android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo;
        getPendingExitNames();
        this.mEnterTransitionCoordinator = null;
        if (activity.getWindow().hasFeature(13) && this.mExitTransitionCoordinators != null && (sceneTransitionInfo = new android.app.ActivityOptions(bundle).getSceneTransitionInfo()) != null) {
            int indexOfKey = this.mExitTransitionCoordinators.indexOfKey(sceneTransitionInfo.getExitCoordinatorKey());
            if (indexOfKey >= 0) {
                this.mCalledExitCoordinator = this.mExitTransitionCoordinators.valueAt(indexOfKey).get();
                this.mExitTransitionCoordinators.removeAt(indexOfKey);
                if (this.mCalledExitCoordinator != null) {
                    this.mExitingFrom = this.mCalledExitCoordinator.getAcceptedNames();
                    this.mExitingTo = this.mCalledExitCoordinator.getMappedNames();
                    this.mExitingToView = this.mCalledExitCoordinator.copyMappedViews();
                    this.mCalledExitCoordinator.startExit();
                }
            }
        }
    }
}
