package android.app;

/* compiled from: FragmentManager.java */
/* loaded from: classes.dex */
final class FragmentManagerImpl extends android.app.FragmentManager implements android.view.LayoutInflater.Factory2 {
    static boolean DEBUG = false;
    static final java.lang.String TAG = "FragmentManager";
    static final java.lang.String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    static final java.lang.String TARGET_STATE_TAG = "android:target_state";
    static final java.lang.String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    static final java.lang.String VIEW_STATE_TAG = "android:view_state";
    android.util.SparseArray<android.app.Fragment> mActive;
    boolean mAllowOldReentrantBehavior;
    java.util.ArrayList<java.lang.Integer> mAvailBackStackIndices;
    java.util.ArrayList<android.app.BackStackRecord> mBackStack;
    java.util.ArrayList<android.app.FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
    java.util.ArrayList<android.app.BackStackRecord> mBackStackIndices;
    android.app.FragmentContainer mContainer;
    java.util.ArrayList<android.app.Fragment> mCreatedMenus;
    boolean mDestroyed;
    boolean mExecutingActions;
    boolean mHavePendingDeferredStart;
    android.app.FragmentHostCallback<?> mHost;
    boolean mNeedMenuInvalidate;
    java.lang.String mNoTransactionsBecause;
    android.app.Fragment mParent;
    java.util.ArrayList<android.app.FragmentManagerImpl.OpGenerator> mPendingActions;
    java.util.ArrayList<android.app.FragmentManagerImpl.StartEnterTransitionListener> mPostponedTransactions;
    android.app.Fragment mPrimaryNav;
    android.app.FragmentManagerNonConfig mSavedNonConfig;
    boolean mStateSaved;
    java.util.ArrayList<android.app.Fragment> mTmpAddedFragments;
    java.util.ArrayList<java.lang.Boolean> mTmpIsPop;
    java.util.ArrayList<android.app.BackStackRecord> mTmpRecords;
    int mNextFragmentIndex = 0;
    final java.util.ArrayList<android.app.Fragment> mAdded = new java.util.ArrayList<>();
    final java.util.concurrent.CopyOnWriteArrayList<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> mLifecycleCallbacks = new java.util.concurrent.CopyOnWriteArrayList<>();
    int mCurState = 0;
    android.os.Bundle mStateBundle = null;
    android.util.SparseArray<android.os.Parcelable> mStateArray = null;
    java.lang.Runnable mExecCommit = new java.lang.Runnable() { // from class: android.app.FragmentManagerImpl.1
        @Override // java.lang.Runnable
        public void run() {
            android.app.FragmentManagerImpl.this.execPendingActions();
        }
    };

    /* compiled from: FragmentManager.java */
    interface OpGenerator {
        boolean generateOps(java.util.ArrayList<android.app.BackStackRecord> arrayList, java.util.ArrayList<java.lang.Boolean> arrayList2);
    }

    FragmentManagerImpl() {
    }

    /* compiled from: FragmentManager.java */
    static class AnimateOnHWLayerIfNeededListener implements android.animation.Animator.AnimatorListener {
        private boolean mShouldRunOnHWLayer = false;
        private android.view.View mView;

        public AnimateOnHWLayerIfNeededListener(android.view.View view) {
            if (view == null) {
                return;
            }
            this.mView = view;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
            this.mShouldRunOnHWLayer = android.app.FragmentManagerImpl.shouldRunOnHWLayer(this.mView, animator);
            if (this.mShouldRunOnHWLayer) {
                this.mView.setLayerType(2, null);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            if (this.mShouldRunOnHWLayer) {
                this.mView.setLayerType(0, null);
            }
            this.mView = null;
            animator.removeListener(this);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(android.animation.Animator animator) {
        }
    }

    private void throwException(java.lang.RuntimeException runtimeException) {
        android.util.Log.e(TAG, runtimeException.getMessage());
        com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter((java.io.Writer) new android.util.LogWriter(6, TAG), false, 1024);
        if (this.mHost != null) {
            android.util.Log.e(TAG, "Activity state:");
            try {
                this.mHost.onDump("  ", null, fastPrintWriter, new java.lang.String[0]);
            } catch (java.lang.Exception e) {
                fastPrintWriter.flush();
                android.util.Log.e(TAG, "Failed dumping state", e);
            }
        } else {
            android.util.Log.e(TAG, "Fragment manager state:");
            try {
                dump("  ", null, fastPrintWriter, new java.lang.String[0]);
            } catch (java.lang.Exception e2) {
                fastPrintWriter.flush();
                android.util.Log.e(TAG, "Failed dumping state", e2);
            }
        }
        fastPrintWriter.flush();
        throw runtimeException;
    }

    static boolean modifiesAlpha(android.animation.Animator animator) {
        if (animator == null) {
            return false;
        }
        if (animator instanceof android.animation.ValueAnimator) {
            for (android.animation.PropertyValuesHolder propertyValuesHolder : ((android.animation.ValueAnimator) animator).getValues()) {
                if ("alpha".equals(propertyValuesHolder.getPropertyName())) {
                    return true;
                }
            }
        } else if (animator instanceof android.animation.AnimatorSet) {
            java.util.ArrayList<android.animation.Animator> childAnimations = ((android.animation.AnimatorSet) animator).getChildAnimations();
            for (int i = 0; i < childAnimations.size(); i++) {
                if (modifiesAlpha(childAnimations.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean shouldRunOnHWLayer(android.view.View view, android.animation.Animator animator) {
        return view != null && animator != null && view.getLayerType() == 0 && view.hasOverlappingRendering() && modifiesAlpha(animator);
    }

    private void setHWLayerAnimListenerIfAlpha(android.view.View view, android.animation.Animator animator) {
        if (view != null && animator != null && shouldRunOnHWLayer(view, animator)) {
            animator.addListener(new android.app.FragmentManagerImpl.AnimateOnHWLayerIfNeededListener(view));
        }
    }

    @Override // android.app.FragmentManager
    public android.app.FragmentTransaction beginTransaction() {
        return new android.app.BackStackRecord(this);
    }

    @Override // android.app.FragmentManager
    public boolean executePendingTransactions() {
        boolean execPendingActions = execPendingActions();
        forcePostponedTransactions();
        return execPendingActions;
    }

    @Override // android.app.FragmentManager
    public void popBackStack() {
        enqueueAction(new android.app.FragmentManagerImpl.PopBackStackState(null, -1, 0), false);
    }

    @Override // android.app.FragmentManager
    public boolean popBackStackImmediate() {
        checkStateLoss();
        return popBackStackImmediate(null, -1, 0);
    }

    @Override // android.app.FragmentManager
    public void popBackStack(java.lang.String str, int i) {
        enqueueAction(new android.app.FragmentManagerImpl.PopBackStackState(str, -1, i), false);
    }

    @Override // android.app.FragmentManager
    public boolean popBackStackImmediate(java.lang.String str, int i) {
        checkStateLoss();
        return popBackStackImmediate(str, -1, i);
    }

    @Override // android.app.FragmentManager
    public void popBackStack(int i, int i2) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Bad id: " + i);
        }
        enqueueAction(new android.app.FragmentManagerImpl.PopBackStackState(null, i, i2), false);
    }

    @Override // android.app.FragmentManager
    public boolean popBackStackImmediate(int i, int i2) {
        checkStateLoss();
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Bad id: " + i);
        }
        return popBackStackImmediate(null, i, i2);
    }

    private boolean popBackStackImmediate(java.lang.String str, int i, int i2) {
        android.app.FragmentManagerImpl fragmentManagerImpl;
        execPendingActions();
        ensureExecReady(true);
        if (this.mPrimaryNav != null && i < 0 && str == null && (fragmentManagerImpl = this.mPrimaryNav.mChildFragmentManager) != null && fragmentManagerImpl.popBackStackImmediate()) {
            return true;
        }
        boolean popBackStackState = popBackStackState(this.mTmpRecords, this.mTmpIsPop, str, i, i2);
        if (popBackStackState) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        doPendingDeferredStart();
        burpActive();
        return popBackStackState;
    }

    @Override // android.app.FragmentManager
    public int getBackStackEntryCount() {
        if (this.mBackStack != null) {
            return this.mBackStack.size();
        }
        return 0;
    }

    @Override // android.app.FragmentManager
    public android.app.FragmentManager.BackStackEntry getBackStackEntryAt(int i) {
        return this.mBackStack.get(i);
    }

    @Override // android.app.FragmentManager
    public void addOnBackStackChangedListener(android.app.FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new java.util.ArrayList<>();
        }
        this.mBackStackChangeListeners.add(onBackStackChangedListener);
    }

    @Override // android.app.FragmentManager
    public void removeOnBackStackChangedListener(android.app.FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners != null) {
            this.mBackStackChangeListeners.remove(onBackStackChangedListener);
        }
    }

    @Override // android.app.FragmentManager
    public void putFragment(android.os.Bundle bundle, java.lang.String str, android.app.Fragment fragment) {
        if (fragment.mIndex < 0) {
            throwException(new java.lang.IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putInt(str, fragment.mIndex);
    }

    @Override // android.app.FragmentManager
    public android.app.Fragment getFragment(android.os.Bundle bundle, java.lang.String str) {
        int i = bundle.getInt(str, -1);
        if (i == -1) {
            return null;
        }
        android.app.Fragment fragment = this.mActive.get(i);
        if (fragment == null) {
            throwException(new java.lang.IllegalStateException("Fragment no longer exists for key " + str + ": index " + i));
        }
        return fragment;
    }

    @Override // android.app.FragmentManager
    public java.util.List<android.app.Fragment> getFragments() {
        java.util.List<android.app.Fragment> list;
        if (this.mAdded.isEmpty()) {
            return java.util.Collections.EMPTY_LIST;
        }
        synchronized (this.mAdded) {
            list = (java.util.List) this.mAdded.clone();
        }
        return list;
    }

    @Override // android.app.FragmentManager
    public android.app.Fragment.SavedState saveFragmentInstanceState(android.app.Fragment fragment) {
        android.os.Bundle saveFragmentBasicState;
        if (fragment.mIndex < 0) {
            throwException(new java.lang.IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        if (fragment.mState <= 0 || (saveFragmentBasicState = saveFragmentBasicState(fragment)) == null) {
            return null;
        }
        return new android.app.Fragment.SavedState(saveFragmentBasicState);
    }

    @Override // android.app.FragmentManager
    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(" in ");
        if (this.mParent != null) {
            android.util.DebugUtils.buildShortClassTag(this.mParent, sb);
        } else {
            android.util.DebugUtils.buildShortClassTag(this.mHost, sb);
        }
        sb.append("}}");
        return sb.toString();
    }

    @Override // android.app.FragmentManager
    public void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        int size;
        int size2;
        int size3;
        int size4;
        int size5;
        java.lang.String str2 = str + "    ";
        if (this.mActive != null && (size5 = this.mActive.size()) > 0) {
            printWriter.print(str);
            printWriter.print("Active Fragments in ");
            printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            printWriter.println(":");
            for (int i = 0; i < size5; i++) {
                android.app.Fragment valueAt = this.mActive.valueAt(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(valueAt);
                if (valueAt != null) {
                    valueAt.dump(str2, fileDescriptor, printWriter, strArr);
                }
            }
        }
        int size6 = this.mAdded.size();
        if (size6 > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i2 = 0; i2 < size6; i2++) {
                android.app.Fragment fragment = this.mAdded.get(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(fragment.toString());
            }
        }
        if (this.mCreatedMenus != null && (size4 = this.mCreatedMenus.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i3 = 0; i3 < size4; i3++) {
                android.app.Fragment fragment2 = this.mCreatedMenus.get(i3);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(fragment2.toString());
            }
        }
        if (this.mBackStack != null && (size3 = this.mBackStack.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i4 = 0; i4 < size3; i4++) {
                android.app.BackStackRecord backStackRecord = this.mBackStack.get(i4);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i4);
                printWriter.print(": ");
                printWriter.println(backStackRecord.toString());
                backStackRecord.dump(str2, fileDescriptor, printWriter, strArr);
            }
        }
        synchronized (this) {
            if (this.mBackStackIndices != null && (size2 = this.mBackStackIndices.size()) > 0) {
                printWriter.print(str);
                printWriter.println("Back Stack Indices:");
                for (int i5 = 0; i5 < size2; i5++) {
                    java.lang.Object obj = (android.app.BackStackRecord) this.mBackStackIndices.get(i5);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i5);
                    printWriter.print(": ");
                    printWriter.println(obj);
                }
            }
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                printWriter.print(str);
                printWriter.print("mAvailBackStackIndices: ");
                printWriter.println(java.util.Arrays.toString(this.mAvailBackStackIndices.toArray()));
            }
        }
        if (this.mPendingActions != null && (size = this.mPendingActions.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Pending Actions:");
            for (int i6 = 0; i6 < size; i6++) {
                java.lang.Object obj2 = (android.app.FragmentManagerImpl.OpGenerator) this.mPendingActions.get(i6);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i6);
                printWriter.print(": ");
                printWriter.println(obj2);
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.mCurState);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause != null) {
            printWriter.print(str);
            printWriter.print("  mNoTransactionsBecause=");
            printWriter.println(this.mNoTransactionsBecause);
        }
    }

    android.animation.Animator loadAnimator(android.app.Fragment fragment, int i, boolean z, int i2) {
        int transitToStyleIndex;
        android.animation.Animator loadAnimator;
        android.animation.Animator onCreateAnimator = fragment.onCreateAnimator(i, z, fragment.getNextAnim());
        if (onCreateAnimator != null) {
            return onCreateAnimator;
        }
        if (fragment.getNextAnim() != 0 && (loadAnimator = android.animation.AnimatorInflater.loadAnimator(this.mHost.getContext(), fragment.getNextAnim())) != null) {
            return loadAnimator;
        }
        if (i == 0 || (transitToStyleIndex = transitToStyleIndex(i, z)) < 0) {
            return null;
        }
        if (i2 == 0 && this.mHost.onHasWindowAnimations()) {
            i2 = this.mHost.onGetWindowAnimations();
        }
        if (i2 == 0) {
            return null;
        }
        android.content.res.TypedArray obtainStyledAttributes = this.mHost.getContext().obtainStyledAttributes(i2, com.android.internal.R.styleable.FragmentAnimation);
        int resourceId = obtainStyledAttributes.getResourceId(transitToStyleIndex, 0);
        obtainStyledAttributes.recycle();
        if (resourceId == 0) {
            return null;
        }
        return android.animation.AnimatorInflater.loadAnimator(this.mHost.getContext(), resourceId);
    }

    public void performPendingDeferredStart(android.app.Fragment fragment) {
        if (fragment.mDeferStart) {
            if (this.mExecutingActions) {
                this.mHavePendingDeferredStart = true;
            } else {
                fragment.mDeferStart = false;
                moveToState(fragment, this.mCurState, 0, 0, false);
            }
        }
    }

    boolean isStateAtLeast(int i) {
        return this.mCurState >= i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x02a8  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x02cc  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0349  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0402  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01a2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void moveToState(final android.app.Fragment fragment, int i, int i2, int i3, boolean z) {
        int i4;
        android.animation.Animator animator;
        int i5;
        android.view.ViewGroup viewGroup;
        java.lang.String str;
        android.app.FragmentManagerImpl fragmentManagerImpl;
        java.lang.String str2;
        int i6 = 1;
        if (!fragment.mAdded || fragment.mDetached) {
            i4 = i;
            if (i4 > 1) {
                i4 = 1;
            }
        } else {
            i4 = i;
        }
        if (fragment.mRemoving && i4 > fragment.mState) {
            if (fragment.mState == 0 && fragment.isInBackStack()) {
                i4 = 1;
            } else {
                i4 = fragment.mState;
            }
        }
        if (fragment.mDeferStart && fragment.mState < 4 && i4 > 3) {
            i4 = 3;
        }
        if (fragment.mState <= i4) {
            if (fragment.mFromLayout && !fragment.mInLayout) {
                return;
            }
            if (fragment.getAnimatingAway() != null) {
                fragment.setAnimatingAway(null);
                moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, true);
            }
            switch (fragment.mState) {
                case 0:
                    if (i4 > 0) {
                        if (DEBUG) {
                            android.util.Log.v(TAG, "moveto CREATED: " + fragment);
                        }
                        if (fragment.mSavedFragmentState != null) {
                            fragment.mSavedViewState = fragment.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                            fragment.mTarget = getFragment(fragment.mSavedFragmentState, TARGET_STATE_TAG);
                            if (fragment.mTarget != null) {
                                fragment.mTargetRequestCode = fragment.mSavedFragmentState.getInt(TARGET_REQUEST_CODE_STATE_TAG, 0);
                            }
                            fragment.mUserVisibleHint = fragment.mSavedFragmentState.getBoolean(USER_VISIBLE_HINT_TAG, true);
                            if (!fragment.mUserVisibleHint) {
                                fragment.mDeferStart = true;
                                if (i4 > 3) {
                                    i4 = 3;
                                }
                            }
                        }
                        fragment.mHost = this.mHost;
                        fragment.mParentFragment = this.mParent;
                        if (this.mParent == null) {
                            fragmentManagerImpl = this.mHost.getFragmentManagerImpl();
                        } else {
                            fragmentManagerImpl = this.mParent.mChildFragmentManager;
                        }
                        fragment.mFragmentManager = fragmentManagerImpl;
                        if (fragment.mTarget == null) {
                            str2 = "Fragment ";
                        } else {
                            if (this.mActive.get(fragment.mTarget.mIndex) != fragment.mTarget) {
                                throw new java.lang.IllegalStateException("Fragment " + fragment + " declared target fragment " + fragment.mTarget + " that does not belong to this FragmentManager!");
                            }
                            if (fragment.mTarget.mState >= 1) {
                                str2 = "Fragment ";
                            } else {
                                str2 = "Fragment ";
                                moveToState(fragment.mTarget, 1, 0, 0, true);
                            }
                        }
                        dispatchOnFragmentPreAttached(fragment, this.mHost.getContext(), false);
                        fragment.mCalled = false;
                        fragment.onAttach(this.mHost.getContext());
                        if (!fragment.mCalled) {
                            throw new android.util.SuperNotCalledException(str2 + fragment + " did not call through to super.onAttach()");
                        }
                        if (fragment.mParentFragment == null) {
                            this.mHost.onAttachFragment(fragment);
                        } else {
                            fragment.mParentFragment.onAttachFragment(fragment);
                        }
                        dispatchOnFragmentAttached(fragment, this.mHost.getContext(), false);
                        if (!fragment.mIsCreated) {
                            dispatchOnFragmentPreCreated(fragment, fragment.mSavedFragmentState, false);
                            fragment.performCreate(fragment.mSavedFragmentState);
                            dispatchOnFragmentCreated(fragment, fragment.mSavedFragmentState, false);
                        } else {
                            fragment.restoreChildFragmentState(fragment.mSavedFragmentState, true);
                            fragment.mState = 1;
                        }
                        fragment.mRetaining = false;
                        i5 = i4;
                        ensureInflatedFragmentView(fragment);
                        if (i5 > 1) {
                            if (DEBUG) {
                                android.util.Log.v(TAG, "moveto ACTIVITY_CREATED: " + fragment);
                            }
                            if (!fragment.mFromLayout) {
                                if (fragment.mContainerId == 0) {
                                    viewGroup = null;
                                } else {
                                    if (fragment.mContainerId == -1) {
                                        throwException(new java.lang.IllegalArgumentException("Cannot create fragment " + fragment + " for a container view with no id"));
                                    }
                                    viewGroup = (android.view.ViewGroup) this.mContainer.onFindViewById(fragment.mContainerId);
                                    if (viewGroup == null && !fragment.mRestored) {
                                        try {
                                            str = fragment.getResources().getResourceName(fragment.mContainerId);
                                        } catch (android.content.res.Resources.NotFoundException e) {
                                            str = "unknown";
                                        }
                                        throwException(new java.lang.IllegalArgumentException("No view found for id 0x" + java.lang.Integer.toHexString(fragment.mContainerId) + " (" + str + ") for fragment " + fragment));
                                    }
                                }
                                fragment.mContainer = viewGroup;
                                fragment.mView = fragment.performCreateView(fragment.performGetLayoutInflater(fragment.mSavedFragmentState), viewGroup, fragment.mSavedFragmentState);
                                if (fragment.mView != null) {
                                    fragment.mView.setSaveFromParentEnabled(false);
                                    if (viewGroup != null) {
                                        viewGroup.addView(fragment.mView);
                                    }
                                    if (fragment.mHidden) {
                                        fragment.mView.setVisibility(8);
                                    }
                                    fragment.onViewCreated(fragment.mView, fragment.mSavedFragmentState);
                                    dispatchOnFragmentViewCreated(fragment, fragment.mView, fragment.mSavedFragmentState, false);
                                    fragment.mIsNewlyAdded = fragment.mView.getVisibility() == 0 && fragment.mContainer != null;
                                }
                            }
                            fragment.performActivityCreated(fragment.mSavedFragmentState);
                            dispatchOnFragmentActivityCreated(fragment, fragment.mSavedFragmentState, false);
                            if (fragment.mView != null) {
                                fragment.restoreViewState(fragment.mSavedFragmentState);
                            }
                            fragment.mSavedFragmentState = null;
                        }
                        i4 = i5;
                        if (i4 > 2) {
                            fragment.mState = 3;
                        }
                        if (i4 > 3) {
                            if (DEBUG) {
                                android.util.Log.v(TAG, "moveto STARTED: " + fragment);
                            }
                            fragment.performStart();
                            dispatchOnFragmentStarted(fragment, false);
                        }
                        if (i4 > 4) {
                            if (DEBUG) {
                                android.util.Log.v(TAG, "moveto RESUMED: " + fragment);
                            }
                            fragment.performResume();
                            dispatchOnFragmentResumed(fragment, false);
                            fragment.mSavedFragmentState = null;
                            fragment.mSavedViewState = null;
                        }
                        i6 = i4;
                        break;
                    }
                    break;
                case 1:
                    i5 = i4;
                    ensureInflatedFragmentView(fragment);
                    if (i5 > 1) {
                    }
                    i4 = i5;
                    if (i4 > 2) {
                    }
                    if (i4 > 3) {
                    }
                    if (i4 > 4) {
                    }
                    i6 = i4;
                    break;
                case 2:
                    if (i4 > 2) {
                    }
                    break;
                case 3:
                    if (i4 > 3) {
                    }
                    break;
                case 4:
                    if (i4 > 4) {
                    }
                    i6 = i4;
                    break;
                default:
                    i6 = i4;
                    break;
            }
        } else {
            if (fragment.mState > i4) {
                switch (fragment.mState) {
                    case 1:
                        if (i4 < 1) {
                            if (this.mDestroyed && fragment.getAnimatingAway() != null) {
                                android.animation.Animator animatingAway = fragment.getAnimatingAway();
                                fragment.setAnimatingAway(null);
                                animatingAway.cancel();
                            }
                            if (fragment.getAnimatingAway() != null) {
                                fragment.setStateAfterAnimating(i4);
                                break;
                            } else {
                                if (DEBUG) {
                                    android.util.Log.v(TAG, "movefrom CREATED: " + fragment);
                                }
                                if (!fragment.mRetaining) {
                                    fragment.performDestroy();
                                    dispatchOnFragmentDestroyed(fragment, false);
                                } else {
                                    fragment.mState = 0;
                                }
                                fragment.performDetach();
                                dispatchOnFragmentDetached(fragment, false);
                                if (!z) {
                                    if (!fragment.mRetaining) {
                                        makeInactive(fragment);
                                        break;
                                    } else {
                                        fragment.mHost = null;
                                        fragment.mParentFragment = null;
                                        fragment.mFragmentManager = null;
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    case 2:
                    case 3:
                        if (i4 < 2) {
                            if (DEBUG) {
                                android.util.Log.v(TAG, "movefrom ACTIVITY_CREATED: " + fragment);
                            }
                            if (fragment.mView != null && this.mHost.onShouldSaveFragmentState(fragment) && fragment.mSavedViewState == null) {
                                saveFragmentViewState(fragment);
                            }
                            fragment.performDestroyView();
                            dispatchOnFragmentViewDestroyed(fragment, false);
                            if (fragment.mView != null && fragment.mContainer != null) {
                                if (getTargetSdk() >= 26) {
                                    fragment.mView.clearAnimation();
                                    fragment.mContainer.endViewTransition(fragment.mView);
                                }
                                if (this.mCurState > 0 && !this.mDestroyed && fragment.mView.getVisibility() == 0 && fragment.mView.getTransitionAlpha() > 0.0f) {
                                    animator = loadAnimator(fragment, i2, false, i3);
                                } else {
                                    animator = null;
                                }
                                fragment.mView.setTransitionAlpha(1.0f);
                                if (animator != null) {
                                    final android.view.ViewGroup viewGroup2 = fragment.mContainer;
                                    final android.view.View view = fragment.mView;
                                    viewGroup2.startViewTransition(view);
                                    fragment.setAnimatingAway(animator);
                                    fragment.setStateAfterAnimating(i4);
                                    animator.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.app.FragmentManagerImpl.2
                                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                        public void onAnimationEnd(android.animation.Animator animator2) {
                                            viewGroup2.endViewTransition(view);
                                            android.animation.Animator animatingAway2 = fragment.getAnimatingAway();
                                            fragment.setAnimatingAway(null);
                                            if (viewGroup2.indexOfChild(view) == -1 && animatingAway2 != null) {
                                                android.app.FragmentManagerImpl.this.moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, false);
                                            }
                                        }
                                    });
                                    animator.setTarget(fragment.mView);
                                    setHWLayerAnimListenerIfAlpha(fragment.mView, animator);
                                    animator.start();
                                }
                                fragment.mContainer.removeView(fragment.mView);
                            }
                            fragment.mContainer = null;
                            fragment.mView = null;
                            fragment.mInLayout = false;
                        }
                        if (i4 < 1) {
                        }
                        break;
                    case 4:
                        if (i4 < 4) {
                            if (DEBUG) {
                                android.util.Log.v(TAG, "movefrom STARTED: " + fragment);
                            }
                            fragment.performStop();
                            dispatchOnFragmentStopped(fragment, false);
                        }
                        if (i4 < 2) {
                        }
                        if (i4 < 1) {
                        }
                        break;
                    case 5:
                        if (i4 < 5) {
                            if (DEBUG) {
                                android.util.Log.v(TAG, "movefrom RESUMED: " + fragment);
                            }
                            fragment.performPause();
                            dispatchOnFragmentPaused(fragment, false);
                        }
                        if (i4 < 4) {
                        }
                        if (i4 < 2) {
                        }
                        if (i4 < 1) {
                        }
                        break;
                }
            }
            i6 = i4;
        }
        if (fragment.mState != i6) {
            android.util.Log.w(TAG, "moveToState: Fragment state for " + fragment + " not updated inline; expected state " + i6 + " found " + fragment.mState);
            fragment.mState = i6;
        }
    }

    void moveToState(android.app.Fragment fragment) {
        moveToState(fragment, this.mCurState, 0, 0, false);
    }

    void ensureInflatedFragmentView(android.app.Fragment fragment) {
        if (fragment.mFromLayout && !fragment.mPerformedCreateView) {
            fragment.mView = fragment.performCreateView(fragment.performGetLayoutInflater(fragment.mSavedFragmentState), null, fragment.mSavedFragmentState);
            if (fragment.mView != null) {
                fragment.mView.setSaveFromParentEnabled(false);
                if (fragment.mHidden) {
                    fragment.mView.setVisibility(8);
                }
                fragment.onViewCreated(fragment.mView, fragment.mSavedFragmentState);
                dispatchOnFragmentViewCreated(fragment, fragment.mView, fragment.mSavedFragmentState, false);
            }
        }
    }

    void completeShowHideFragment(android.app.Fragment fragment) {
        int i;
        if (fragment.mView != null) {
            android.animation.Animator loadAnimator = loadAnimator(fragment, fragment.getNextTransition(), !fragment.mHidden, fragment.getNextTransitionStyle());
            if (loadAnimator != null) {
                loadAnimator.setTarget(fragment.mView);
                if (fragment.mHidden) {
                    if (fragment.isHideReplaced()) {
                        fragment.setHideReplaced(false);
                    } else {
                        final android.view.ViewGroup viewGroup = fragment.mContainer;
                        final android.view.View view = fragment.mView;
                        if (viewGroup != null) {
                            viewGroup.startViewTransition(view);
                        }
                        loadAnimator.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.app.FragmentManagerImpl.3
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(android.animation.Animator animator) {
                                if (viewGroup != null) {
                                    viewGroup.endViewTransition(view);
                                }
                                animator.removeListener(this);
                                view.setVisibility(8);
                            }
                        });
                    }
                } else {
                    fragment.mView.setVisibility(0);
                }
                setHWLayerAnimListenerIfAlpha(fragment.mView, loadAnimator);
                loadAnimator.start();
            } else {
                if (fragment.mHidden && !fragment.isHideReplaced()) {
                    i = 8;
                } else {
                    i = 0;
                }
                fragment.mView.setVisibility(i);
                if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                }
            }
        }
        if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mHiddenChanged = false;
        fragment.onHiddenChanged(fragment.mHidden);
    }

    void moveFragmentToExpectedState(android.app.Fragment fragment) {
        int i;
        if (fragment == null) {
            return;
        }
        int i2 = this.mCurState;
        if (!fragment.mRemoving) {
            i = i2;
        } else if (fragment.isInBackStack()) {
            i = java.lang.Math.min(i2, 1);
        } else {
            i = java.lang.Math.min(i2, 0);
        }
        moveToState(fragment, i, fragment.getNextTransition(), fragment.getNextTransitionStyle(), false);
        if (fragment.mView != null) {
            android.app.Fragment findFragmentUnder = findFragmentUnder(fragment);
            if (findFragmentUnder != null) {
                android.view.View view = findFragmentUnder.mView;
                android.view.ViewGroup viewGroup = fragment.mContainer;
                int indexOfChild = viewGroup.indexOfChild(view);
                int indexOfChild2 = viewGroup.indexOfChild(fragment.mView);
                if (indexOfChild2 < indexOfChild) {
                    viewGroup.removeViewAt(indexOfChild2);
                    viewGroup.addView(fragment.mView, indexOfChild);
                }
            }
            if (fragment.mIsNewlyAdded && fragment.mContainer != null) {
                fragment.mView.setTransitionAlpha(1.0f);
                fragment.mIsNewlyAdded = false;
                android.animation.Animator loadAnimator = loadAnimator(fragment, fragment.getNextTransition(), true, fragment.getNextTransitionStyle());
                if (loadAnimator != null) {
                    loadAnimator.setTarget(fragment.mView);
                    setHWLayerAnimListenerIfAlpha(fragment.mView, loadAnimator);
                    loadAnimator.start();
                }
            }
        }
        if (fragment.mHiddenChanged) {
            completeShowHideFragment(fragment);
        }
    }

    void moveToState(int i, boolean z) {
        if (this.mHost == null && i != 0) {
            throw new java.lang.IllegalStateException("No activity");
        }
        if (!z && this.mCurState == i) {
            return;
        }
        this.mCurState = i;
        if (this.mActive != null) {
            int size = this.mAdded.size();
            boolean z2 = false;
            for (int i2 = 0; i2 < size; i2++) {
                android.app.Fragment fragment = this.mAdded.get(i2);
                moveFragmentToExpectedState(fragment);
                if (fragment.mLoaderManager != null) {
                    z2 |= fragment.mLoaderManager.hasRunningLoaders();
                }
            }
            int size2 = this.mActive.size();
            for (int i3 = 0; i3 < size2; i3++) {
                android.app.Fragment valueAt = this.mActive.valueAt(i3);
                if (valueAt != null && ((valueAt.mRemoving || valueAt.mDetached) && !valueAt.mIsNewlyAdded)) {
                    moveFragmentToExpectedState(valueAt);
                    if (valueAt.mLoaderManager != null) {
                        z2 |= valueAt.mLoaderManager.hasRunningLoaders();
                    }
                }
            }
            if (!z2) {
                startPendingDeferredFragments();
            }
            if (this.mNeedMenuInvalidate && this.mHost != null && this.mCurState == 5) {
                this.mHost.onInvalidateOptionsMenu();
                this.mNeedMenuInvalidate = false;
            }
        }
    }

    void startPendingDeferredFragments() {
        if (this.mActive == null) {
            return;
        }
        for (int i = 0; i < this.mActive.size(); i++) {
            android.app.Fragment valueAt = this.mActive.valueAt(i);
            if (valueAt != null) {
                performPendingDeferredStart(valueAt);
            }
        }
    }

    void makeActive(android.app.Fragment fragment) {
        if (fragment.mIndex >= 0) {
            return;
        }
        int i = this.mNextFragmentIndex;
        this.mNextFragmentIndex = i + 1;
        fragment.setIndex(i, this.mParent);
        if (this.mActive == null) {
            this.mActive = new android.util.SparseArray<>();
        }
        this.mActive.put(fragment.mIndex, fragment);
        if (DEBUG) {
            android.util.Log.v(TAG, "Allocated fragment index " + fragment);
        }
    }

    void makeInactive(android.app.Fragment fragment) {
        if (fragment.mIndex < 0) {
            return;
        }
        if (DEBUG) {
            android.util.Log.v(TAG, "Freeing fragment index " + fragment);
        }
        this.mActive.put(fragment.mIndex, null);
        this.mHost.inactivateFragment(fragment.mWho);
        fragment.initState();
    }

    public void addFragment(android.app.Fragment fragment, boolean z) {
        if (DEBUG) {
            android.util.Log.v(TAG, "add: " + fragment);
        }
        makeActive(fragment);
        if (!fragment.mDetached) {
            if (this.mAdded.contains(fragment)) {
                throw new java.lang.IllegalStateException("Fragment already added: " + fragment);
            }
            synchronized (this.mAdded) {
                this.mAdded.add(fragment);
            }
            fragment.mAdded = true;
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            if (z) {
                moveToState(fragment);
            }
        }
    }

    public void removeFragment(android.app.Fragment fragment) {
        if (DEBUG) {
            android.util.Log.v(TAG, "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        boolean z = !fragment.isInBackStack();
        if (!fragment.mDetached || z) {
            synchronized (this.mAdded) {
                this.mAdded.remove(fragment);
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mAdded = false;
            fragment.mRemoving = true;
        }
    }

    public void hideFragment(android.app.Fragment fragment) {
        if (DEBUG) {
            android.util.Log.v(TAG, "hide: " + fragment);
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
        }
    }

    public void showFragment(android.app.Fragment fragment) {
        if (DEBUG) {
            android.util.Log.v(TAG, "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged = !fragment.mHiddenChanged;
        }
    }

    public void detachFragment(android.app.Fragment fragment) {
        if (DEBUG) {
            android.util.Log.v(TAG, "detach: " + fragment);
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                if (DEBUG) {
                    android.util.Log.v(TAG, "remove from detach: " + fragment);
                }
                synchronized (this.mAdded) {
                    this.mAdded.remove(fragment);
                }
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                fragment.mAdded = false;
            }
        }
    }

    public void attachFragment(android.app.Fragment fragment) {
        if (DEBUG) {
            android.util.Log.v(TAG, "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                if (this.mAdded.contains(fragment)) {
                    throw new java.lang.IllegalStateException("Fragment already added: " + fragment);
                }
                if (DEBUG) {
                    android.util.Log.v(TAG, "add from attach: " + fragment);
                }
                synchronized (this.mAdded) {
                    this.mAdded.add(fragment);
                }
                fragment.mAdded = true;
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
            }
        }
    }

    @Override // android.app.FragmentManager
    public android.app.Fragment findFragmentById(int i) {
        for (int size = this.mAdded.size() - 1; size >= 0; size--) {
            android.app.Fragment fragment = this.mAdded.get(size);
            if (fragment != null && fragment.mFragmentId == i) {
                return fragment;
            }
        }
        if (this.mActive != null) {
            for (int size2 = this.mActive.size() - 1; size2 >= 0; size2--) {
                android.app.Fragment valueAt = this.mActive.valueAt(size2);
                if (valueAt != null && valueAt.mFragmentId == i) {
                    return valueAt;
                }
            }
            return null;
        }
        return null;
    }

    @Override // android.app.FragmentManager
    public android.app.Fragment findFragmentByTag(java.lang.String str) {
        if (str != null) {
            for (int size = this.mAdded.size() - 1; size >= 0; size--) {
                android.app.Fragment fragment = this.mAdded.get(size);
                if (fragment != null && str.equals(fragment.mTag)) {
                    return fragment;
                }
            }
        }
        if (this.mActive != null && str != null) {
            for (int size2 = this.mActive.size() - 1; size2 >= 0; size2--) {
                android.app.Fragment valueAt = this.mActive.valueAt(size2);
                if (valueAt != null && str.equals(valueAt.mTag)) {
                    return valueAt;
                }
            }
            return null;
        }
        return null;
    }

    public android.app.Fragment findFragmentByWho(java.lang.String str) {
        android.app.Fragment findFragmentByWho;
        if (this.mActive != null && str != null) {
            for (int size = this.mActive.size() - 1; size >= 0; size--) {
                android.app.Fragment valueAt = this.mActive.valueAt(size);
                if (valueAt != null && (findFragmentByWho = valueAt.findFragmentByWho(str)) != null) {
                    return findFragmentByWho;
                }
            }
            return null;
        }
        return null;
    }

    private void checkStateLoss() {
        if (this.mStateSaved) {
            throw new java.lang.IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.mNoTransactionsBecause != null) {
            throw new java.lang.IllegalStateException("Can not perform this action inside of " + this.mNoTransactionsBecause);
        }
    }

    @Override // android.app.FragmentManager
    public boolean isStateSaved() {
        return this.mStateSaved;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0027, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void enqueueAction(android.app.FragmentManagerImpl.OpGenerator opGenerator, boolean z) {
        if (!z) {
            checkStateLoss();
        }
        synchronized (this) {
            if (!this.mDestroyed && this.mHost != null) {
                if (this.mPendingActions == null) {
                    this.mPendingActions = new java.util.ArrayList<>();
                }
                this.mPendingActions.add(opGenerator);
                scheduleCommit();
                return;
            }
            throw new java.lang.IllegalStateException("Activity has been destroyed");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleCommit() {
        synchronized (this) {
            boolean z = false;
            boolean z2 = (this.mPostponedTransactions == null || this.mPostponedTransactions.isEmpty()) ? false : true;
            if (this.mPendingActions != null && this.mPendingActions.size() == 1) {
                z = true;
            }
            if (z2 || z) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
            }
        }
    }

    public int allocBackStackIndex(android.app.BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                int intValue = this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1).intValue();
                if (DEBUG) {
                    android.util.Log.v(TAG, "Adding back stack index " + intValue + " with " + backStackRecord);
                }
                this.mBackStackIndices.set(intValue, backStackRecord);
                return intValue;
            }
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new java.util.ArrayList<>();
            }
            int size = this.mBackStackIndices.size();
            if (DEBUG) {
                android.util.Log.v(TAG, "Setting back stack index " + size + " to " + backStackRecord);
            }
            this.mBackStackIndices.add(backStackRecord);
            return size;
        }
    }

    public void setBackStackIndex(int i, android.app.BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new java.util.ArrayList<>();
            }
            int size = this.mBackStackIndices.size();
            if (i < size) {
                if (DEBUG) {
                    android.util.Log.v(TAG, "Setting back stack index " + i + " to " + backStackRecord);
                }
                this.mBackStackIndices.set(i, backStackRecord);
            } else {
                while (size < i) {
                    this.mBackStackIndices.add(null);
                    if (this.mAvailBackStackIndices == null) {
                        this.mAvailBackStackIndices = new java.util.ArrayList<>();
                    }
                    if (DEBUG) {
                        android.util.Log.v(TAG, "Adding available back stack index " + size);
                    }
                    this.mAvailBackStackIndices.add(java.lang.Integer.valueOf(size));
                    size++;
                }
                if (DEBUG) {
                    android.util.Log.v(TAG, "Adding back stack index " + i + " with " + backStackRecord);
                }
                this.mBackStackIndices.add(backStackRecord);
            }
        }
    }

    public void freeBackStackIndex(int i) {
        synchronized (this) {
            this.mBackStackIndices.set(i, null);
            if (this.mAvailBackStackIndices == null) {
                this.mAvailBackStackIndices = new java.util.ArrayList<>();
            }
            if (DEBUG) {
                android.util.Log.v(TAG, "Freeing back stack index " + i);
            }
            this.mAvailBackStackIndices.add(java.lang.Integer.valueOf(i));
        }
    }

    private void ensureExecReady(boolean z) {
        if (this.mExecutingActions) {
            throw new java.lang.IllegalStateException("FragmentManager is already executing transactions");
        }
        if (android.os.Looper.myLooper() != this.mHost.getHandler().getLooper()) {
            throw new java.lang.IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!z) {
            checkStateLoss();
        }
        if (this.mTmpRecords == null) {
            this.mTmpRecords = new java.util.ArrayList<>();
            this.mTmpIsPop = new java.util.ArrayList<>();
        }
        this.mExecutingActions = true;
        try {
            executePostponedTransaction(null, null);
        } finally {
            this.mExecutingActions = false;
        }
    }

    public void execSingleAction(android.app.FragmentManagerImpl.OpGenerator opGenerator, boolean z) {
        if (z && (this.mHost == null || this.mDestroyed)) {
            return;
        }
        ensureExecReady(z);
        if (opGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        doPendingDeferredStart();
        burpActive();
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    public boolean execPendingActions() {
        ensureExecReady(true);
        boolean z = false;
        while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                cleanupExec();
                z = true;
            } catch (java.lang.Throwable th) {
                cleanupExec();
                throw th;
            }
        }
        doPendingDeferredStart();
        burpActive();
        return z;
    }

    private void executePostponedTransaction(java.util.ArrayList<android.app.BackStackRecord> arrayList, java.util.ArrayList<java.lang.Boolean> arrayList2) {
        int indexOf;
        int indexOf2;
        int size = this.mPostponedTransactions == null ? 0 : this.mPostponedTransactions.size();
        int i = 0;
        while (i < size) {
            android.app.FragmentManagerImpl.StartEnterTransitionListener startEnterTransitionListener = this.mPostponedTransactions.get(i);
            if (arrayList != null && !startEnterTransitionListener.mIsBack && (indexOf2 = arrayList.indexOf(startEnterTransitionListener.mRecord)) != -1 && arrayList2.get(indexOf2).booleanValue()) {
                startEnterTransitionListener.cancelTransaction();
            } else if (startEnterTransitionListener.isReady() || (arrayList != null && startEnterTransitionListener.mRecord.interactsWith(arrayList, 0, arrayList.size()))) {
                this.mPostponedTransactions.remove(i);
                i--;
                size--;
                if (arrayList != null && !startEnterTransitionListener.mIsBack && (indexOf = arrayList.indexOf(startEnterTransitionListener.mRecord)) != -1 && arrayList2.get(indexOf).booleanValue()) {
                    startEnterTransitionListener.cancelTransaction();
                } else {
                    startEnterTransitionListener.completeTransaction();
                }
            }
            i++;
        }
    }

    private void removeRedundantOperationsAndExecute(java.util.ArrayList<android.app.BackStackRecord> arrayList, java.util.ArrayList<java.lang.Boolean> arrayList2) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        if (arrayList2 == null || arrayList.size() != arrayList2.size()) {
            throw new java.lang.IllegalStateException("Internal error with the back stack records");
        }
        executePostponedTransaction(arrayList, arrayList2);
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            if (!arrayList.get(i).mReorderingAllowed) {
                if (i2 != i) {
                    executeOpsTogether(arrayList, arrayList2, i2, i);
                }
                i2 = i + 1;
                if (arrayList2.get(i).booleanValue()) {
                    while (i2 < size && arrayList2.get(i2).booleanValue() && !arrayList.get(i2).mReorderingAllowed) {
                        i2++;
                    }
                }
                executeOpsTogether(arrayList, arrayList2, i, i2);
                i = i2 - 1;
            }
            i++;
        }
        if (i2 != size) {
            executeOpsTogether(arrayList, arrayList2, i2, size);
        }
    }

    private void executeOpsTogether(java.util.ArrayList<android.app.BackStackRecord> arrayList, java.util.ArrayList<java.lang.Boolean> arrayList2, int i, int i2) {
        int i3;
        int i4 = i;
        boolean z = arrayList.get(i4).mReorderingAllowed;
        if (this.mTmpAddedFragments == null) {
            this.mTmpAddedFragments = new java.util.ArrayList<>();
        } else {
            this.mTmpAddedFragments.clear();
        }
        this.mTmpAddedFragments.addAll(this.mAdded);
        android.app.Fragment primaryNavigationFragment = getPrimaryNavigationFragment();
        boolean z2 = false;
        for (int i5 = i4; i5 < i2; i5++) {
            android.app.BackStackRecord backStackRecord = arrayList.get(i5);
            if (!arrayList2.get(i5).booleanValue()) {
                primaryNavigationFragment = backStackRecord.expandOps(this.mTmpAddedFragments, primaryNavigationFragment);
            } else {
                backStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments);
            }
            z2 = z2 || backStackRecord.mAddToBackStack;
        }
        this.mTmpAddedFragments.clear();
        if (!z) {
            android.app.FragmentTransition.startTransitions(this, arrayList, arrayList2, i, i2, false);
        }
        executeOps(arrayList, arrayList2, i, i2);
        if (!z) {
            i3 = i2;
        } else {
            android.util.ArraySet<android.app.Fragment> arraySet = new android.util.ArraySet<>();
            addAddedFragments(arraySet);
            int postponePostponableTransactions = postponePostponableTransactions(arrayList, arrayList2, i, i2, arraySet);
            makeRemovedFragmentsInvisible(arraySet);
            i3 = postponePostponableTransactions;
        }
        if (i3 != i4 && z) {
            android.app.FragmentTransition.startTransitions(this, arrayList, arrayList2, i, i3, true);
            moveToState(this.mCurState, true);
        }
        while (i4 < i2) {
            android.app.BackStackRecord backStackRecord2 = arrayList.get(i4);
            if (arrayList2.get(i4).booleanValue() && backStackRecord2.mIndex >= 0) {
                freeBackStackIndex(backStackRecord2.mIndex);
                backStackRecord2.mIndex = -1;
            }
            backStackRecord2.runOnCommitRunnables();
            i4++;
        }
        if (z2) {
            reportBackStackChanged();
        }
    }

    private void makeRemovedFragmentsInvisible(android.util.ArraySet<android.app.Fragment> arraySet) {
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            android.app.Fragment valueAt = arraySet.valueAt(i);
            if (!valueAt.mAdded) {
                valueAt.getView().setTransitionAlpha(0.0f);
            }
        }
    }

    private int postponePostponableTransactions(java.util.ArrayList<android.app.BackStackRecord> arrayList, java.util.ArrayList<java.lang.Boolean> arrayList2, int i, int i2, android.util.ArraySet<android.app.Fragment> arraySet) {
        int i3 = i2;
        for (int i4 = i2 - 1; i4 >= i; i4--) {
            android.app.BackStackRecord backStackRecord = arrayList.get(i4);
            boolean booleanValue = arrayList2.get(i4).booleanValue();
            if (backStackRecord.isPostponed() && !backStackRecord.interactsWith(arrayList, i4 + 1, i2)) {
                if (this.mPostponedTransactions == null) {
                    this.mPostponedTransactions = new java.util.ArrayList<>();
                }
                android.app.FragmentManagerImpl.StartEnterTransitionListener startEnterTransitionListener = new android.app.FragmentManagerImpl.StartEnterTransitionListener(backStackRecord, booleanValue);
                this.mPostponedTransactions.add(startEnterTransitionListener);
                backStackRecord.setOnStartPostponedListener(startEnterTransitionListener);
                if (booleanValue) {
                    backStackRecord.executeOps();
                } else {
                    backStackRecord.executePopOps(false);
                }
                i3--;
                if (i4 != i3) {
                    arrayList.remove(i4);
                    arrayList.add(i3, backStackRecord);
                }
                addAddedFragments(arraySet);
            }
        }
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void completeExecute(android.app.BackStackRecord backStackRecord, boolean z, boolean z2, boolean z3) {
        if (z) {
            backStackRecord.executePopOps(z3);
        } else {
            backStackRecord.executeOps();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(1);
        java.util.ArrayList arrayList2 = new java.util.ArrayList(1);
        arrayList.add(backStackRecord);
        arrayList2.add(java.lang.Boolean.valueOf(z));
        if (z2) {
            android.app.FragmentTransition.startTransitions(this, arrayList, arrayList2, 0, 1, true);
        }
        if (z3) {
            moveToState(this.mCurState, true);
        }
        if (this.mActive != null) {
            int size = this.mActive.size();
            for (int i = 0; i < size; i++) {
                android.app.Fragment valueAt = this.mActive.valueAt(i);
                if (valueAt != null && valueAt.mView != null && valueAt.mIsNewlyAdded && backStackRecord.interactsWith(valueAt.mContainerId)) {
                    valueAt.mIsNewlyAdded = false;
                }
            }
        }
    }

    private android.app.Fragment findFragmentUnder(android.app.Fragment fragment) {
        android.view.ViewGroup viewGroup = fragment.mContainer;
        android.view.View view = fragment.mView;
        if (viewGroup == null || view == null) {
            return null;
        }
        for (int indexOf = this.mAdded.indexOf(fragment) - 1; indexOf >= 0; indexOf--) {
            android.app.Fragment fragment2 = this.mAdded.get(indexOf);
            if (fragment2.mContainer == viewGroup && fragment2.mView != null) {
                return fragment2;
            }
        }
        return null;
    }

    private static void executeOps(java.util.ArrayList<android.app.BackStackRecord> arrayList, java.util.ArrayList<java.lang.Boolean> arrayList2, int i, int i2) {
        while (i < i2) {
            android.app.BackStackRecord backStackRecord = arrayList.get(i);
            if (arrayList2.get(i).booleanValue()) {
                backStackRecord.bumpBackStackNesting(-1);
                backStackRecord.executePopOps(i == i2 + (-1));
            } else {
                backStackRecord.bumpBackStackNesting(1);
                backStackRecord.executeOps();
            }
            i++;
        }
    }

    private void addAddedFragments(android.util.ArraySet<android.app.Fragment> arraySet) {
        if (this.mCurState < 1) {
            return;
        }
        int min = java.lang.Math.min(this.mCurState, 4);
        int size = this.mAdded.size();
        for (int i = 0; i < size; i++) {
            android.app.Fragment fragment = this.mAdded.get(i);
            if (fragment.mState < min) {
                moveToState(fragment, min, fragment.getNextAnim(), fragment.getNextTransition(), false);
                if (fragment.mView != null && !fragment.mHidden && fragment.mIsNewlyAdded) {
                    arraySet.add(fragment);
                }
            }
        }
    }

    private void forcePostponedTransactions() {
        if (this.mPostponedTransactions != null) {
            while (!this.mPostponedTransactions.isEmpty()) {
                this.mPostponedTransactions.remove(0).completeTransaction();
            }
        }
    }

    private void endAnimatingAwayFragments() {
        int size = this.mActive == null ? 0 : this.mActive.size();
        for (int i = 0; i < size; i++) {
            android.app.Fragment valueAt = this.mActive.valueAt(i);
            if (valueAt != null && valueAt.getAnimatingAway() != null) {
                valueAt.getAnimatingAway().end();
            }
        }
    }

    private boolean generateOpsForPendingActions(java.util.ArrayList<android.app.BackStackRecord> arrayList, java.util.ArrayList<java.lang.Boolean> arrayList2) {
        synchronized (this) {
            if (this.mPendingActions != null && this.mPendingActions.size() != 0) {
                int size = this.mPendingActions.size();
                boolean z = false;
                for (int i = 0; i < size; i++) {
                    z |= this.mPendingActions.get(i).generateOps(arrayList, arrayList2);
                }
                this.mPendingActions.clear();
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                return z;
            }
            return false;
        }
    }

    void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            boolean z = false;
            for (int i = 0; i < this.mActive.size(); i++) {
                android.app.Fragment valueAt = this.mActive.valueAt(i);
                if (valueAt != null && valueAt.mLoaderManager != null) {
                    z |= valueAt.mLoaderManager.hasRunningLoaders();
                }
            }
            if (!z) {
                this.mHavePendingDeferredStart = false;
                startPendingDeferredFragments();
            }
        }
    }

    void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
                this.mBackStackChangeListeners.get(i).onBackStackChanged();
            }
        }
    }

    void addBackStackState(android.app.BackStackRecord backStackRecord) {
        if (this.mBackStack == null) {
            this.mBackStack = new java.util.ArrayList<>();
        }
        this.mBackStack.add(backStackRecord);
    }

    boolean popBackStackState(java.util.ArrayList<android.app.BackStackRecord> arrayList, java.util.ArrayList<java.lang.Boolean> arrayList2, java.lang.String str, int i, int i2) {
        int i3;
        if (this.mBackStack == null) {
            return false;
        }
        if (str == null && i < 0 && (i2 & 1) == 0) {
            int size = this.mBackStack.size() - 1;
            if (size < 0) {
                return false;
            }
            arrayList.add(this.mBackStack.remove(size));
            arrayList2.add(true);
        } else {
            if (str != null || i >= 0) {
                int size2 = this.mBackStack.size() - 1;
                while (size2 >= 0) {
                    android.app.BackStackRecord backStackRecord = this.mBackStack.get(size2);
                    if ((str != null && str.equals(backStackRecord.getName())) || (i >= 0 && i == backStackRecord.mIndex)) {
                        break;
                    }
                    size2--;
                }
                if (size2 < 0) {
                    return false;
                }
                if ((i2 & 1) == 0) {
                    i3 = size2;
                } else {
                    int i4 = size2 - 1;
                    while (i4 >= 0) {
                        android.app.BackStackRecord backStackRecord2 = this.mBackStack.get(i4);
                        if ((str == null || !str.equals(backStackRecord2.getName())) && (i < 0 || i != backStackRecord2.mIndex)) {
                            break;
                        }
                        i4--;
                    }
                    i3 = i4;
                }
            } else {
                i3 = -1;
            }
            if (i3 == this.mBackStack.size() - 1) {
                return false;
            }
            for (int size3 = this.mBackStack.size() - 1; size3 > i3; size3--) {
                arrayList.add(this.mBackStack.remove(size3));
                arrayList2.add(true);
            }
        }
        return true;
    }

    android.app.FragmentManagerNonConfig retainNonConfig() {
        setRetaining(this.mSavedNonConfig);
        return this.mSavedNonConfig;
    }

    private static void setRetaining(android.app.FragmentManagerNonConfig fragmentManagerNonConfig) {
        if (fragmentManagerNonConfig == null) {
            return;
        }
        java.util.List<android.app.Fragment> fragments = fragmentManagerNonConfig.getFragments();
        if (fragments != null) {
            java.util.Iterator<android.app.Fragment> it = fragments.iterator();
            while (it.hasNext()) {
                it.next().mRetaining = true;
            }
        }
        java.util.List<android.app.FragmentManagerNonConfig> childNonConfigs = fragmentManagerNonConfig.getChildNonConfigs();
        if (childNonConfigs != null) {
            java.util.Iterator<android.app.FragmentManagerNonConfig> it2 = childNonConfigs.iterator();
            while (it2.hasNext()) {
                setRetaining(it2.next());
            }
        }
    }

    void saveNonConfig() {
        java.util.ArrayList arrayList;
        java.util.ArrayList arrayList2;
        android.app.FragmentManagerNonConfig fragmentManagerNonConfig;
        if (this.mActive == null) {
            arrayList = null;
            arrayList2 = null;
        } else {
            arrayList = null;
            arrayList2 = null;
            for (int i = 0; i < this.mActive.size(); i++) {
                android.app.Fragment valueAt = this.mActive.valueAt(i);
                if (valueAt != null) {
                    if (valueAt.mRetainInstance) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(valueAt);
                        valueAt.mTargetIndex = valueAt.mTarget != null ? valueAt.mTarget.mIndex : -1;
                        if (DEBUG) {
                            android.util.Log.v(TAG, "retainNonConfig: keeping retained " + valueAt);
                        }
                    }
                    if (valueAt.mChildFragmentManager != null) {
                        valueAt.mChildFragmentManager.saveNonConfig();
                        fragmentManagerNonConfig = valueAt.mChildFragmentManager.mSavedNonConfig;
                    } else {
                        fragmentManagerNonConfig = valueAt.mChildNonConfig;
                    }
                    if (arrayList2 == null && fragmentManagerNonConfig != null) {
                        arrayList2 = new java.util.ArrayList(this.mActive.size());
                        for (int i2 = 0; i2 < i; i2++) {
                            arrayList2.add(null);
                        }
                    }
                    if (arrayList2 != null) {
                        arrayList2.add(fragmentManagerNonConfig);
                    }
                }
            }
        }
        if (arrayList == null && arrayList2 == null) {
            this.mSavedNonConfig = null;
        } else {
            this.mSavedNonConfig = new android.app.FragmentManagerNonConfig(arrayList, arrayList2);
        }
    }

    void saveFragmentViewState(android.app.Fragment fragment) {
        if (fragment.mView == null) {
            return;
        }
        if (this.mStateArray == null) {
            this.mStateArray = new android.util.SparseArray<>();
        } else {
            this.mStateArray.clear();
        }
        fragment.mView.saveHierarchyState(this.mStateArray);
        if (this.mStateArray.size() > 0) {
            fragment.mSavedViewState = this.mStateArray;
            this.mStateArray = null;
        }
    }

    android.os.Bundle saveFragmentBasicState(android.app.Fragment fragment) {
        if (this.mStateBundle == null) {
            this.mStateBundle = new android.os.Bundle();
        }
        fragment.performSaveInstanceState(this.mStateBundle);
        dispatchOnFragmentSaveInstanceState(fragment, this.mStateBundle, false);
        android.os.Bundle bundle = null;
        if (!this.mStateBundle.isEmpty()) {
            android.os.Bundle bundle2 = this.mStateBundle;
            this.mStateBundle = null;
            bundle = bundle2;
        }
        if (fragment.mView != null) {
            saveFragmentViewState(fragment);
        }
        if (fragment.mSavedViewState != null) {
            if (bundle == null) {
                bundle = new android.os.Bundle();
            }
            bundle.putSparseParcelableArray(VIEW_STATE_TAG, fragment.mSavedViewState);
        }
        if (!fragment.mUserVisibleHint) {
            if (bundle == null) {
                bundle = new android.os.Bundle();
            }
            bundle.putBoolean(USER_VISIBLE_HINT_TAG, fragment.mUserVisibleHint);
        }
        return bundle;
    }

    android.os.Parcelable saveAllState() {
        int[] iArr;
        int size;
        forcePostponedTransactions();
        endAnimatingAwayFragments();
        execPendingActions();
        this.mStateSaved = true;
        android.app.BackStackState[] backStackStateArr = null;
        this.mSavedNonConfig = null;
        if (this.mActive == null || this.mActive.size() <= 0) {
            return null;
        }
        int size2 = this.mActive.size();
        android.app.FragmentState[] fragmentStateArr = new android.app.FragmentState[size2];
        boolean z = false;
        for (int i = 0; i < size2; i++) {
            android.app.Fragment valueAt = this.mActive.valueAt(i);
            if (valueAt != null) {
                if (valueAt.mIndex < 0) {
                    throwException(new java.lang.IllegalStateException("Failure saving state: active " + valueAt + " has cleared index: " + valueAt.mIndex));
                }
                android.app.FragmentState fragmentState = new android.app.FragmentState(valueAt);
                fragmentStateArr[i] = fragmentState;
                if (valueAt.mState > 0 && fragmentState.mSavedFragmentState == null) {
                    fragmentState.mSavedFragmentState = saveFragmentBasicState(valueAt);
                    if (valueAt.mTarget != null) {
                        if (valueAt.mTarget.mIndex < 0) {
                            throwException(new java.lang.IllegalStateException("Failure saving state: " + valueAt + " has target not in fragment manager: " + valueAt.mTarget));
                        }
                        if (fragmentState.mSavedFragmentState == null) {
                            fragmentState.mSavedFragmentState = new android.os.Bundle();
                        }
                        putFragment(fragmentState.mSavedFragmentState, TARGET_STATE_TAG, valueAt.mTarget);
                        if (valueAt.mTargetRequestCode != 0) {
                            fragmentState.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, valueAt.mTargetRequestCode);
                        }
                    }
                } else {
                    fragmentState.mSavedFragmentState = valueAt.mSavedFragmentState;
                }
                if (DEBUG) {
                    android.util.Log.v(TAG, "Saved state of " + valueAt + ": " + fragmentState.mSavedFragmentState);
                }
                z = true;
            }
        }
        if (!z) {
            if (DEBUG) {
                android.util.Log.v(TAG, "saveAllState: no fragments!");
            }
            return null;
        }
        int size3 = this.mAdded.size();
        if (size3 <= 0) {
            iArr = null;
        } else {
            iArr = new int[size3];
            for (int i2 = 0; i2 < size3; i2++) {
                iArr[i2] = this.mAdded.get(i2).mIndex;
                if (iArr[i2] < 0) {
                    throwException(new java.lang.IllegalStateException("Failure saving state: active " + this.mAdded.get(i2) + " has cleared index: " + iArr[i2]));
                }
                if (DEBUG) {
                    android.util.Log.v(TAG, "saveAllState: adding fragment #" + i2 + ": " + this.mAdded.get(i2));
                }
            }
        }
        if (this.mBackStack != null && (size = this.mBackStack.size()) > 0) {
            backStackStateArr = new android.app.BackStackState[size];
            for (int i3 = 0; i3 < size; i3++) {
                backStackStateArr[i3] = new android.app.BackStackState(this, this.mBackStack.get(i3));
                if (DEBUG) {
                    android.util.Log.v(TAG, "saveAllState: adding back stack #" + i3 + ": " + this.mBackStack.get(i3));
                }
            }
        }
        android.app.FragmentManagerState fragmentManagerState = new android.app.FragmentManagerState();
        fragmentManagerState.mActive = fragmentStateArr;
        fragmentManagerState.mAdded = iArr;
        fragmentManagerState.mBackStack = backStackStateArr;
        fragmentManagerState.mNextFragmentIndex = this.mNextFragmentIndex;
        if (this.mPrimaryNav != null) {
            fragmentManagerState.mPrimaryNavActiveIndex = this.mPrimaryNav.mIndex;
        }
        saveNonConfig();
        return fragmentManagerState;
    }

    void restoreAllState(android.os.Parcelable parcelable, android.app.FragmentManagerNonConfig fragmentManagerNonConfig) {
        java.util.List<android.app.FragmentManagerNonConfig> list;
        android.app.FragmentManagerNonConfig fragmentManagerNonConfig2;
        if (parcelable == null) {
            return;
        }
        android.app.FragmentManagerState fragmentManagerState = (android.app.FragmentManagerState) parcelable;
        if (fragmentManagerState.mActive == null) {
            return;
        }
        if (fragmentManagerNonConfig == null) {
            list = null;
        } else {
            java.util.List<android.app.Fragment> fragments = fragmentManagerNonConfig.getFragments();
            list = fragmentManagerNonConfig.getChildNonConfigs();
            int size = fragments != null ? fragments.size() : 0;
            for (int i = 0; i < size; i++) {
                android.app.Fragment fragment = fragments.get(i);
                if (DEBUG) {
                    android.util.Log.v(TAG, "restoreAllState: re-attaching retained " + fragment);
                }
                int i2 = 0;
                while (i2 < fragmentManagerState.mActive.length && fragmentManagerState.mActive[i2].mIndex != fragment.mIndex) {
                    i2++;
                }
                if (i2 == fragmentManagerState.mActive.length) {
                    throwException(new java.lang.IllegalStateException("Could not find active fragment with index " + fragment.mIndex));
                }
                android.app.FragmentState fragmentState = fragmentManagerState.mActive[i2];
                fragmentState.mInstance = fragment;
                fragment.mSavedViewState = null;
                fragment.mBackStackNesting = 0;
                fragment.mInLayout = false;
                fragment.mAdded = false;
                fragment.mTarget = null;
                if (fragmentState.mSavedFragmentState != null) {
                    fragmentState.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
                    fragment.mSavedViewState = fragmentState.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                    fragment.mSavedFragmentState = fragmentState.mSavedFragmentState;
                }
            }
        }
        this.mActive = new android.util.SparseArray<>(fragmentManagerState.mActive.length);
        for (int i3 = 0; i3 < fragmentManagerState.mActive.length; i3++) {
            android.app.FragmentState fragmentState2 = fragmentManagerState.mActive[i3];
            if (fragmentState2 != null) {
                if (list != null && i3 < list.size()) {
                    fragmentManagerNonConfig2 = list.get(i3);
                } else {
                    fragmentManagerNonConfig2 = null;
                }
                android.app.Fragment instantiate = fragmentState2.instantiate(this.mHost, this.mContainer, this.mParent, fragmentManagerNonConfig2);
                if (DEBUG) {
                    android.util.Log.v(TAG, "restoreAllState: active #" + i3 + ": " + instantiate);
                }
                this.mActive.put(instantiate.mIndex, instantiate);
                fragmentState2.mInstance = null;
            }
        }
        if (fragmentManagerNonConfig != null) {
            java.util.List<android.app.Fragment> fragments2 = fragmentManagerNonConfig.getFragments();
            int size2 = fragments2 != null ? fragments2.size() : 0;
            for (int i4 = 0; i4 < size2; i4++) {
                android.app.Fragment fragment2 = fragments2.get(i4);
                if (fragment2.mTargetIndex >= 0) {
                    fragment2.mTarget = this.mActive.get(fragment2.mTargetIndex);
                    if (fragment2.mTarget == null) {
                        android.util.Log.w(TAG, "Re-attaching retained fragment " + fragment2 + " target no longer exists: " + fragment2.mTargetIndex);
                        fragment2.mTarget = null;
                    }
                }
            }
        }
        this.mAdded.clear();
        if (fragmentManagerState.mAdded != null) {
            for (int i5 = 0; i5 < fragmentManagerState.mAdded.length; i5++) {
                android.app.Fragment fragment3 = this.mActive.get(fragmentManagerState.mAdded[i5]);
                if (fragment3 == null) {
                    throwException(new java.lang.IllegalStateException("No instantiated fragment for index #" + fragmentManagerState.mAdded[i5]));
                }
                fragment3.mAdded = true;
                if (DEBUG) {
                    android.util.Log.v(TAG, "restoreAllState: added #" + i5 + ": " + fragment3);
                }
                if (this.mAdded.contains(fragment3)) {
                    throw new java.lang.IllegalStateException("Already added!");
                }
                synchronized (this.mAdded) {
                    this.mAdded.add(fragment3);
                }
            }
        }
        if (fragmentManagerState.mBackStack != null) {
            this.mBackStack = new java.util.ArrayList<>(fragmentManagerState.mBackStack.length);
            for (int i6 = 0; i6 < fragmentManagerState.mBackStack.length; i6++) {
                android.app.BackStackRecord instantiate2 = fragmentManagerState.mBackStack[i6].instantiate(this);
                if (DEBUG) {
                    android.util.Log.v(TAG, "restoreAllState: back stack #" + i6 + " (index " + instantiate2.mIndex + "): " + instantiate2);
                    com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter((java.io.Writer) new android.util.LogWriter(2, TAG), false, 1024);
                    instantiate2.dump("  ", fastPrintWriter, false);
                    fastPrintWriter.flush();
                }
                this.mBackStack.add(instantiate2);
                if (instantiate2.mIndex >= 0) {
                    setBackStackIndex(instantiate2.mIndex, instantiate2);
                }
            }
        } else {
            this.mBackStack = null;
        }
        if (fragmentManagerState.mPrimaryNavActiveIndex >= 0) {
            this.mPrimaryNav = this.mActive.get(fragmentManagerState.mPrimaryNavActiveIndex);
        }
        this.mNextFragmentIndex = fragmentManagerState.mNextFragmentIndex;
    }

    private void burpActive() {
        if (this.mActive != null) {
            for (int size = this.mActive.size() - 1; size >= 0; size--) {
                if (this.mActive.valueAt(size) == null) {
                    this.mActive.delete(this.mActive.keyAt(size));
                }
            }
        }
    }

    public void attachController(android.app.FragmentHostCallback<?> fragmentHostCallback, android.app.FragmentContainer fragmentContainer, android.app.Fragment fragment) {
        if (this.mHost != null) {
            throw new java.lang.IllegalStateException("Already attached");
        }
        this.mHost = fragmentHostCallback;
        this.mContainer = fragmentContainer;
        this.mParent = fragment;
        this.mAllowOldReentrantBehavior = getTargetSdk() <= 25;
    }

    int getTargetSdk() {
        android.content.Context context;
        android.content.pm.ApplicationInfo applicationInfo;
        if (this.mHost != null && (context = this.mHost.getContext()) != null && (applicationInfo = context.getApplicationInfo()) != null) {
            return applicationInfo.targetSdkVersion;
        }
        return 0;
    }

    public void noteStateNotSaved() {
        this.mSavedNonConfig = null;
        this.mStateSaved = false;
        int size = this.mAdded.size();
        for (int i = 0; i < size; i++) {
            android.app.Fragment fragment = this.mAdded.get(i);
            if (fragment != null) {
                fragment.noteStateNotSaved();
            }
        }
    }

    public void dispatchCreate() {
        this.mStateSaved = false;
        dispatchMoveToState(1);
    }

    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        dispatchMoveToState(2);
    }

    public void dispatchStart() {
        this.mStateSaved = false;
        dispatchMoveToState(4);
    }

    public void dispatchResume() {
        this.mStateSaved = false;
        dispatchMoveToState(5);
    }

    public void dispatchPause() {
        dispatchMoveToState(4);
    }

    public void dispatchStop() {
        dispatchMoveToState(3);
    }

    public void dispatchDestroyView() {
        dispatchMoveToState(1);
    }

    public void dispatchDestroy() {
        this.mDestroyed = true;
        execPendingActions();
        dispatchMoveToState(0);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
    }

    private void dispatchMoveToState(int i) {
        if (this.mAllowOldReentrantBehavior) {
            moveToState(i, false);
        } else {
            try {
                this.mExecutingActions = true;
                moveToState(i, false);
            } finally {
                this.mExecutingActions = false;
            }
        }
        execPendingActions();
    }

    @java.lang.Deprecated
    public void dispatchMultiWindowModeChanged(boolean z) {
        for (int size = this.mAdded.size() - 1; size >= 0; size--) {
            android.app.Fragment fragment = this.mAdded.get(size);
            if (fragment != null) {
                fragment.performMultiWindowModeChanged(z);
            }
        }
    }

    public void dispatchMultiWindowModeChanged(boolean z, android.content.res.Configuration configuration) {
        for (int size = this.mAdded.size() - 1; size >= 0; size--) {
            android.app.Fragment fragment = this.mAdded.get(size);
            if (fragment != null) {
                fragment.performMultiWindowModeChanged(z, configuration);
            }
        }
    }

    @java.lang.Deprecated
    public void dispatchPictureInPictureModeChanged(boolean z) {
        for (int size = this.mAdded.size() - 1; size >= 0; size--) {
            android.app.Fragment fragment = this.mAdded.get(size);
            if (fragment != null) {
                fragment.performPictureInPictureModeChanged(z);
            }
        }
    }

    public void dispatchPictureInPictureModeChanged(boolean z, android.content.res.Configuration configuration) {
        for (int size = this.mAdded.size() - 1; size >= 0; size--) {
            android.app.Fragment fragment = this.mAdded.get(size);
            if (fragment != null) {
                fragment.performPictureInPictureModeChanged(z, configuration);
            }
        }
    }

    public void dispatchConfigurationChanged(android.content.res.Configuration configuration) {
        for (int i = 0; i < this.mAdded.size(); i++) {
            android.app.Fragment fragment = this.mAdded.get(i);
            if (fragment != null) {
                fragment.performConfigurationChanged(configuration);
            }
        }
    }

    public void dispatchLowMemory() {
        for (int i = 0; i < this.mAdded.size(); i++) {
            android.app.Fragment fragment = this.mAdded.get(i);
            if (fragment != null) {
                fragment.performLowMemory();
            }
        }
    }

    public void dispatchTrimMemory(int i) {
        for (int i2 = 0; i2 < this.mAdded.size(); i2++) {
            android.app.Fragment fragment = this.mAdded.get(i2);
            if (fragment != null) {
                fragment.performTrimMemory(i);
            }
        }
    }

    public boolean dispatchCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater menuInflater) {
        if (this.mCurState < 1) {
            return false;
        }
        java.util.ArrayList<android.app.Fragment> arrayList = null;
        boolean z = false;
        for (int i = 0; i < this.mAdded.size(); i++) {
            android.app.Fragment fragment = this.mAdded.get(i);
            if (fragment != null && fragment.performCreateOptionsMenu(menu, menuInflater)) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList<>();
                }
                arrayList.add(fragment);
                z = true;
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i2 = 0; i2 < this.mCreatedMenus.size(); i2++) {
                android.app.Fragment fragment2 = this.mCreatedMenus.get(i2);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = arrayList;
        return z;
    }

    public boolean dispatchPrepareOptionsMenu(android.view.Menu menu) {
        if (this.mCurState < 1) {
            return false;
        }
        boolean z = false;
        for (int i = 0; i < this.mAdded.size(); i++) {
            android.app.Fragment fragment = this.mAdded.get(i);
            if (fragment != null && fragment.performPrepareOptionsMenu(menu)) {
                z = true;
            }
        }
        return z;
    }

    public boolean dispatchOptionsItemSelected(android.view.MenuItem menuItem) {
        if (this.mCurState < 1) {
            return false;
        }
        for (int i = 0; i < this.mAdded.size(); i++) {
            android.app.Fragment fragment = this.mAdded.get(i);
            if (fragment != null && fragment.performOptionsItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public boolean dispatchContextItemSelected(android.view.MenuItem menuItem) {
        if (this.mCurState < 1) {
            return false;
        }
        for (int i = 0; i < this.mAdded.size(); i++) {
            android.app.Fragment fragment = this.mAdded.get(i);
            if (fragment != null && fragment.performContextItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(android.view.Menu menu) {
        if (this.mCurState < 1) {
            return;
        }
        for (int i = 0; i < this.mAdded.size(); i++) {
            android.app.Fragment fragment = this.mAdded.get(i);
            if (fragment != null) {
                fragment.performOptionsMenuClosed(menu);
            }
        }
    }

    public void setPrimaryNavigationFragment(android.app.Fragment fragment) {
        if (fragment != null && (this.mActive.get(fragment.mIndex) != fragment || (fragment.mHost != null && fragment.getFragmentManager() != this))) {
            throw new java.lang.IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
        }
        this.mPrimaryNav = fragment;
    }

    @Override // android.app.FragmentManager
    public android.app.Fragment getPrimaryNavigationFragment() {
        return this.mPrimaryNav;
    }

    @Override // android.app.FragmentManager
    public void registerFragmentLifecycleCallbacks(android.app.FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z) {
        this.mLifecycleCallbacks.add(new android.util.Pair<>(fragmentLifecycleCallbacks, java.lang.Boolean.valueOf(z)));
    }

    @Override // android.app.FragmentManager
    public void unregisterFragmentLifecycleCallbacks(android.app.FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        synchronized (this.mLifecycleCallbacks) {
            int size = this.mLifecycleCallbacks.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (this.mLifecycleCallbacks.get(i).first != fragmentLifecycleCallbacks) {
                    i++;
                } else {
                    this.mLifecycleCallbacks.remove(i);
                    break;
                }
            }
        }
    }

    void dispatchOnFragmentPreAttached(android.app.Fragment fragment, android.content.Context context, boolean z) {
        if (this.mParent != null) {
            android.app.FragmentManager fragmentManager = this.mParent.getFragmentManager();
            if (fragmentManager instanceof android.app.FragmentManagerImpl) {
                ((android.app.FragmentManagerImpl) fragmentManager).dispatchOnFragmentPreAttached(fragment, context, true);
            }
        }
        java.util.Iterator<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentPreAttached(this, fragment, context);
            }
        }
    }

    void dispatchOnFragmentAttached(android.app.Fragment fragment, android.content.Context context, boolean z) {
        if (this.mParent != null) {
            android.app.FragmentManager fragmentManager = this.mParent.getFragmentManager();
            if (fragmentManager instanceof android.app.FragmentManagerImpl) {
                ((android.app.FragmentManagerImpl) fragmentManager).dispatchOnFragmentAttached(fragment, context, true);
            }
        }
        java.util.Iterator<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentAttached(this, fragment, context);
            }
        }
    }

    void dispatchOnFragmentPreCreated(android.app.Fragment fragment, android.os.Bundle bundle, boolean z) {
        if (this.mParent != null) {
            android.app.FragmentManager fragmentManager = this.mParent.getFragmentManager();
            if (fragmentManager instanceof android.app.FragmentManagerImpl) {
                ((android.app.FragmentManagerImpl) fragmentManager).dispatchOnFragmentPreCreated(fragment, bundle, true);
            }
        }
        java.util.Iterator<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentPreCreated(this, fragment, bundle);
            }
        }
    }

    void dispatchOnFragmentCreated(android.app.Fragment fragment, android.os.Bundle bundle, boolean z) {
        if (this.mParent != null) {
            android.app.FragmentManager fragmentManager = this.mParent.getFragmentManager();
            if (fragmentManager instanceof android.app.FragmentManagerImpl) {
                ((android.app.FragmentManagerImpl) fragmentManager).dispatchOnFragmentCreated(fragment, bundle, true);
            }
        }
        java.util.Iterator<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentCreated(this, fragment, bundle);
            }
        }
    }

    void dispatchOnFragmentActivityCreated(android.app.Fragment fragment, android.os.Bundle bundle, boolean z) {
        if (this.mParent != null) {
            android.app.FragmentManager fragmentManager = this.mParent.getFragmentManager();
            if (fragmentManager instanceof android.app.FragmentManagerImpl) {
                ((android.app.FragmentManagerImpl) fragmentManager).dispatchOnFragmentActivityCreated(fragment, bundle, true);
            }
        }
        java.util.Iterator<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentActivityCreated(this, fragment, bundle);
            }
        }
    }

    void dispatchOnFragmentViewCreated(android.app.Fragment fragment, android.view.View view, android.os.Bundle bundle, boolean z) {
        if (this.mParent != null) {
            android.app.FragmentManager fragmentManager = this.mParent.getFragmentManager();
            if (fragmentManager instanceof android.app.FragmentManagerImpl) {
                ((android.app.FragmentManagerImpl) fragmentManager).dispatchOnFragmentViewCreated(fragment, view, bundle, true);
            }
        }
        java.util.Iterator<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentViewCreated(this, fragment, view, bundle);
            }
        }
    }

    void dispatchOnFragmentStarted(android.app.Fragment fragment, boolean z) {
        if (this.mParent != null) {
            android.app.FragmentManager fragmentManager = this.mParent.getFragmentManager();
            if (fragmentManager instanceof android.app.FragmentManagerImpl) {
                ((android.app.FragmentManagerImpl) fragmentManager).dispatchOnFragmentStarted(fragment, true);
            }
        }
        java.util.Iterator<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentStarted(this, fragment);
            }
        }
    }

    void dispatchOnFragmentResumed(android.app.Fragment fragment, boolean z) {
        if (this.mParent != null) {
            android.app.FragmentManager fragmentManager = this.mParent.getFragmentManager();
            if (fragmentManager instanceof android.app.FragmentManagerImpl) {
                ((android.app.FragmentManagerImpl) fragmentManager).dispatchOnFragmentResumed(fragment, true);
            }
        }
        java.util.Iterator<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentResumed(this, fragment);
            }
        }
    }

    void dispatchOnFragmentPaused(android.app.Fragment fragment, boolean z) {
        if (this.mParent != null) {
            android.app.FragmentManager fragmentManager = this.mParent.getFragmentManager();
            if (fragmentManager instanceof android.app.FragmentManagerImpl) {
                ((android.app.FragmentManagerImpl) fragmentManager).dispatchOnFragmentPaused(fragment, true);
            }
        }
        java.util.Iterator<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentPaused(this, fragment);
            }
        }
    }

    void dispatchOnFragmentStopped(android.app.Fragment fragment, boolean z) {
        if (this.mParent != null) {
            android.app.FragmentManager fragmentManager = this.mParent.getFragmentManager();
            if (fragmentManager instanceof android.app.FragmentManagerImpl) {
                ((android.app.FragmentManagerImpl) fragmentManager).dispatchOnFragmentStopped(fragment, true);
            }
        }
        java.util.Iterator<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentStopped(this, fragment);
            }
        }
    }

    void dispatchOnFragmentSaveInstanceState(android.app.Fragment fragment, android.os.Bundle bundle, boolean z) {
        if (this.mParent != null) {
            android.app.FragmentManager fragmentManager = this.mParent.getFragmentManager();
            if (fragmentManager instanceof android.app.FragmentManagerImpl) {
                ((android.app.FragmentManagerImpl) fragmentManager).dispatchOnFragmentSaveInstanceState(fragment, bundle, true);
            }
        }
        java.util.Iterator<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentSaveInstanceState(this, fragment, bundle);
            }
        }
    }

    void dispatchOnFragmentViewDestroyed(android.app.Fragment fragment, boolean z) {
        if (this.mParent != null) {
            android.app.FragmentManager fragmentManager = this.mParent.getFragmentManager();
            if (fragmentManager instanceof android.app.FragmentManagerImpl) {
                ((android.app.FragmentManagerImpl) fragmentManager).dispatchOnFragmentViewDestroyed(fragment, true);
            }
        }
        java.util.Iterator<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentViewDestroyed(this, fragment);
            }
        }
    }

    void dispatchOnFragmentDestroyed(android.app.Fragment fragment, boolean z) {
        if (this.mParent != null) {
            android.app.FragmentManager fragmentManager = this.mParent.getFragmentManager();
            if (fragmentManager instanceof android.app.FragmentManagerImpl) {
                ((android.app.FragmentManagerImpl) fragmentManager).dispatchOnFragmentDestroyed(fragment, true);
            }
        }
        java.util.Iterator<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentDestroyed(this, fragment);
            }
        }
    }

    void dispatchOnFragmentDetached(android.app.Fragment fragment, boolean z) {
        if (this.mParent != null) {
            android.app.FragmentManager fragmentManager = this.mParent.getFragmentManager();
            if (fragmentManager instanceof android.app.FragmentManagerImpl) {
                ((android.app.FragmentManagerImpl) fragmentManager).dispatchOnFragmentDetached(fragment, true);
            }
        }
        java.util.Iterator<android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            android.util.Pair<android.app.FragmentManager.FragmentLifecycleCallbacks, java.lang.Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentDetached(this, fragment);
            }
        }
    }

    @Override // android.app.FragmentManager
    public void invalidateOptionsMenu() {
        if (this.mHost != null && this.mCurState == 5) {
            this.mHost.onInvalidateOptionsMenu();
        } else {
            this.mNeedMenuInvalidate = true;
        }
    }

    public static int reverseTransit(int i) {
        switch (i) {
            case 4097:
                return 8194;
            case 4099:
                return 4099;
            case 8194:
                return 4097;
            default:
                return 0;
        }
    }

    public static int transitToStyleIndex(int i, boolean z) {
        switch (i) {
            case 4097:
                if (z) {
                    return 0;
                }
                return 1;
            case 4099:
                if (z) {
                    return 4;
                }
                return 5;
            case 8194:
                if (z) {
                    return 2;
                }
                return 3;
            default:
                return -1;
        }
    }

    @Override // android.view.LayoutInflater.Factory2
    public android.view.View onCreateView(android.view.View view, java.lang.String str, android.content.Context context, android.util.AttributeSet attributeSet) {
        java.lang.String str2;
        android.app.Fragment fragment;
        if (!"fragment".equals(str)) {
            return null;
        }
        java.lang.String attributeValue = attributeSet.getAttributeValue(null, "class");
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Fragment);
        if (attributeValue != null) {
            str2 = attributeValue;
        } else {
            str2 = obtainStyledAttributes.getString(0);
        }
        int resourceId = obtainStyledAttributes.getResourceId(1, -1);
        java.lang.String string = obtainStyledAttributes.getString(2);
        obtainStyledAttributes.recycle();
        int id = view != null ? view.getId() : 0;
        if (id == -1 && resourceId == -1 && string == null) {
            throw new java.lang.IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + str2);
        }
        android.app.Fragment findFragmentById = resourceId != -1 ? findFragmentById(resourceId) : null;
        if (findFragmentById == null && string != null) {
            findFragmentById = findFragmentByTag(string);
        }
        if (findFragmentById == null && id != -1) {
            findFragmentById = findFragmentById(id);
        }
        if (DEBUG) {
            android.util.Log.v(TAG, "onCreateView: id=0x" + java.lang.Integer.toHexString(resourceId) + " fname=" + str2 + " existing=" + findFragmentById);
        }
        if (findFragmentById == null) {
            android.app.Fragment instantiate = this.mContainer.instantiate(context, str2, null);
            instantiate.mFromLayout = true;
            instantiate.mFragmentId = resourceId != 0 ? resourceId : id;
            instantiate.mContainerId = id;
            instantiate.mTag = string;
            instantiate.mInLayout = true;
            instantiate.mFragmentManager = this;
            instantiate.mHost = this.mHost;
            instantiate.onInflate(this.mHost.getContext(), attributeSet, instantiate.mSavedFragmentState);
            addFragment(instantiate, true);
            fragment = instantiate;
        } else {
            if (findFragmentById.mInLayout) {
                throw new java.lang.IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + java.lang.Integer.toHexString(resourceId) + ", tag " + string + ", or parent id 0x" + java.lang.Integer.toHexString(id) + " with another fragment for " + str2);
            }
            findFragmentById.mInLayout = true;
            findFragmentById.mHost = this.mHost;
            if (!findFragmentById.mRetaining) {
                findFragmentById.onInflate(this.mHost.getContext(), attributeSet, findFragmentById.mSavedFragmentState);
            }
            fragment = findFragmentById;
        }
        if (this.mCurState < 1 && fragment.mFromLayout) {
            moveToState(fragment, 1, 0, 0, false);
        } else {
            moveToState(fragment);
        }
        if (fragment.mView == null) {
            throw new java.lang.IllegalStateException("Fragment " + str2 + " did not create a view.");
        }
        if (resourceId != 0) {
            fragment.mView.setId(resourceId);
        }
        if (fragment.mView.getTag() == null) {
            fragment.mView.setTag(string);
        }
        return fragment.mView;
    }

    @Override // android.view.LayoutInflater.Factory
    public android.view.View onCreateView(java.lang.String str, android.content.Context context, android.util.AttributeSet attributeSet) {
        return null;
    }

    android.view.LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this;
    }

    /* compiled from: FragmentManager.java */
    private class PopBackStackState implements android.app.FragmentManagerImpl.OpGenerator {
        final int mFlags;
        final int mId;
        final java.lang.String mName;

        public PopBackStackState(java.lang.String str, int i, int i2) {
            this.mName = str;
            this.mId = i;
            this.mFlags = i2;
        }

        @Override // android.app.FragmentManagerImpl.OpGenerator
        public boolean generateOps(java.util.ArrayList<android.app.BackStackRecord> arrayList, java.util.ArrayList<java.lang.Boolean> arrayList2) {
            android.app.FragmentManagerImpl fragmentManagerImpl;
            if (android.app.FragmentManagerImpl.this.mPrimaryNav != null && this.mId < 0 && this.mName == null && (fragmentManagerImpl = android.app.FragmentManagerImpl.this.mPrimaryNav.mChildFragmentManager) != null && fragmentManagerImpl.popBackStackImmediate()) {
                return false;
            }
            return android.app.FragmentManagerImpl.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
        }
    }

    /* compiled from: FragmentManager.java */
    static class StartEnterTransitionListener implements android.app.Fragment.OnStartEnterTransitionListener {
        private final boolean mIsBack;
        private int mNumPostponed;
        private final android.app.BackStackRecord mRecord;

        public StartEnterTransitionListener(android.app.BackStackRecord backStackRecord, boolean z) {
            this.mIsBack = z;
            this.mRecord = backStackRecord;
        }

        @Override // android.app.Fragment.OnStartEnterTransitionListener
        public void onStartEnterTransition() {
            this.mNumPostponed--;
            if (this.mNumPostponed != 0) {
                return;
            }
            this.mRecord.mManager.scheduleCommit();
        }

        @Override // android.app.Fragment.OnStartEnterTransitionListener
        public void startListening() {
            this.mNumPostponed++;
        }

        public boolean isReady() {
            return this.mNumPostponed == 0;
        }

        public void completeTransaction() {
            boolean z = this.mNumPostponed > 0;
            android.app.FragmentManagerImpl fragmentManagerImpl = this.mRecord.mManager;
            int size = fragmentManagerImpl.mAdded.size();
            for (int i = 0; i < size; i++) {
                android.app.Fragment fragment = fragmentManagerImpl.mAdded.get(i);
                fragment.setOnStartEnterTransitionListener(null);
                if (z && fragment.isPostponed()) {
                    fragment.startPostponedEnterTransition();
                }
            }
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, !z, true);
        }

        public void cancelTransaction() {
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
        }
    }
}
