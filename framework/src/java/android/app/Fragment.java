package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class Fragment implements android.content.ComponentCallbacks2, android.view.View.OnCreateContextMenuListener {
    static final int ACTIVITY_CREATED = 2;
    static final int CREATED = 1;
    static final int INITIALIZING = 0;
    static final int INVALID_STATE = -1;
    static final int RESUMED = 5;
    static final int STARTED = 4;
    static final int STOPPED = 3;
    boolean mAdded;
    android.app.Fragment.AnimationInfo mAnimationInfo;
    android.os.Bundle mArguments;
    int mBackStackNesting;
    boolean mCalled;
    boolean mCheckedForLoaderManager;
    android.app.FragmentManagerImpl mChildFragmentManager;
    android.app.FragmentManagerNonConfig mChildNonConfig;
    android.view.ViewGroup mContainer;
    int mContainerId;
    boolean mDeferStart;
    boolean mDetached;
    int mFragmentId;
    android.app.FragmentManagerImpl mFragmentManager;
    boolean mFromLayout;
    boolean mHasMenu;
    boolean mHidden;
    boolean mHiddenChanged;
    android.app.FragmentHostCallback mHost;
    boolean mInLayout;
    boolean mIsCreated;
    boolean mIsNewlyAdded;
    android.view.LayoutInflater mLayoutInflater;
    android.app.LoaderManagerImpl mLoaderManager;
    boolean mLoadersStarted;
    android.app.Fragment mParentFragment;
    boolean mPerformedCreateView;
    boolean mRemoving;
    boolean mRestored;
    boolean mRetainInstance;
    boolean mRetaining;
    android.os.Bundle mSavedFragmentState;
    android.util.SparseArray<android.os.Parcelable> mSavedViewState;
    java.lang.String mTag;
    android.app.Fragment mTarget;
    int mTargetRequestCode;
    android.view.View mView;
    java.lang.String mWho;
    private static final android.util.ArrayMap<java.lang.String, java.lang.Class<?>> sClassMap = new android.util.ArrayMap<>();
    private static final android.transition.Transition USE_DEFAULT_TRANSITION = new android.transition.TransitionSet();
    int mState = 0;
    int mIndex = -1;
    int mTargetIndex = -1;
    boolean mMenuVisible = true;
    boolean mUserVisibleHint = true;

    interface OnStartEnterTransitionListener {
        void onStartEnterTransition();

        void startListening();
    }

    @java.lang.Deprecated
    public static class SavedState implements android.os.Parcelable {
        public static final android.os.Parcelable.ClassLoaderCreator<android.app.Fragment.SavedState> CREATOR = new android.os.Parcelable.ClassLoaderCreator<android.app.Fragment.SavedState>() { // from class: android.app.Fragment.SavedState.1
            @Override // android.os.Parcelable.Creator
            public android.app.Fragment.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.app.Fragment.SavedState(parcel, null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public android.app.Fragment.SavedState createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
                return new android.app.Fragment.SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public android.app.Fragment.SavedState[] newArray(int i) {
                return new android.app.Fragment.SavedState[i];
            }
        };
        final android.os.Bundle mState;

        SavedState(android.os.Bundle bundle) {
            this.mState = bundle;
        }

        SavedState(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            this.mState = parcel.readBundle();
            if (classLoader != null && this.mState != null) {
                this.mState.setClassLoader(classLoader);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeBundle(this.mState);
        }
    }

    @java.lang.Deprecated
    public static class InstantiationException extends android.util.AndroidRuntimeException {
        public InstantiationException(java.lang.String str, java.lang.Exception exc) {
            super(str, exc);
        }
    }

    public static android.app.Fragment instantiate(android.content.Context context, java.lang.String str) {
        return instantiate(context, str, null);
    }

    public static android.app.Fragment instantiate(android.content.Context context, java.lang.String str, android.os.Bundle bundle) {
        try {
            java.lang.Class<?> cls = sClassMap.get(str);
            if (cls == null) {
                cls = context.getClassLoader().loadClass(str);
                if (!android.app.Fragment.class.isAssignableFrom(cls)) {
                    throw new android.app.Fragment.InstantiationException("Trying to instantiate a class " + str + " that is not a Fragment", new java.lang.ClassCastException());
                }
                sClassMap.put(str, cls);
            }
            android.app.Fragment fragment = (android.app.Fragment) cls.getConstructor(new java.lang.Class[0]).newInstance(new java.lang.Object[0]);
            if (bundle != null) {
                bundle.setClassLoader(fragment.getClass().getClassLoader());
                fragment.setArguments(bundle);
            }
            return fragment;
        } catch (java.lang.ClassNotFoundException e) {
            throw new android.app.Fragment.InstantiationException("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e);
        } catch (java.lang.IllegalAccessException e2) {
            throw new android.app.Fragment.InstantiationException("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e2);
        } catch (java.lang.InstantiationException e3) {
            throw new android.app.Fragment.InstantiationException("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e3);
        } catch (java.lang.NoSuchMethodException e4) {
            throw new android.app.Fragment.InstantiationException("Unable to instantiate fragment " + str + ": could not find Fragment constructor", e4);
        } catch (java.lang.reflect.InvocationTargetException e5) {
            throw new android.app.Fragment.InstantiationException("Unable to instantiate fragment " + str + ": calling Fragment constructor caused an exception", e5);
        }
    }

    final void restoreViewState(android.os.Bundle bundle) {
        if (this.mSavedViewState != null) {
            this.mView.restoreHierarchyState(this.mSavedViewState);
            this.mSavedViewState = null;
        }
        this.mCalled = false;
        onViewStateRestored(bundle);
        if (!this.mCalled) {
            throw new android.util.SuperNotCalledException("Fragment " + this + " did not call through to super.onViewStateRestored()");
        }
    }

    final void setIndex(int i, android.app.Fragment fragment) {
        this.mIndex = i;
        if (fragment != null) {
            this.mWho = fragment.mWho + ":" + this.mIndex;
        } else {
            this.mWho = "android:fragment:" + this.mIndex;
        }
    }

    final boolean isInBackStack() {
        return this.mBackStackNesting > 0;
    }

    public final boolean equals(java.lang.Object obj) {
        return super.equals(obj);
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        android.util.DebugUtils.buildShortClassTag(this, sb);
        if (this.mIndex >= 0) {
            sb.append(" #");
            sb.append(this.mIndex);
        }
        if (this.mFragmentId != 0) {
            sb.append(" id=0x");
            sb.append(java.lang.Integer.toHexString(this.mFragmentId));
        }
        if (this.mTag != null) {
            sb.append(" ");
            sb.append(this.mTag);
        }
        sb.append('}');
        return sb.toString();
    }

    public final int getId() {
        return this.mFragmentId;
    }

    public final java.lang.String getTag() {
        return this.mTag;
    }

    public void setArguments(android.os.Bundle bundle) {
        if (this.mIndex >= 0 && isStateSaved()) {
            throw new java.lang.IllegalStateException("Fragment already active");
        }
        this.mArguments = bundle;
    }

    public final android.os.Bundle getArguments() {
        return this.mArguments;
    }

    public final boolean isStateSaved() {
        if (this.mFragmentManager == null) {
            return false;
        }
        return this.mFragmentManager.isStateSaved();
    }

    public void setInitialSavedState(android.app.Fragment.SavedState savedState) {
        if (this.mIndex >= 0) {
            throw new java.lang.IllegalStateException("Fragment already active");
        }
        this.mSavedFragmentState = (savedState == null || savedState.mState == null) ? null : savedState.mState;
    }

    public void setTargetFragment(android.app.Fragment fragment, int i) {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentManager fragmentManager2 = fragment != null ? fragment.getFragmentManager() : null;
        if (fragmentManager != null && fragmentManager2 != null && fragmentManager != fragmentManager2) {
            throw new java.lang.IllegalArgumentException("Fragment " + fragment + " must share the same FragmentManager to be set as a target fragment");
        }
        for (android.app.Fragment fragment2 = fragment; fragment2 != null; fragment2 = fragment2.getTargetFragment()) {
            if (fragment2 == this) {
                throw new java.lang.IllegalArgumentException("Setting " + fragment + " as the target of " + this + " would create a target cycle");
            }
        }
        this.mTarget = fragment;
        this.mTargetRequestCode = i;
    }

    public final android.app.Fragment getTargetFragment() {
        return this.mTarget;
    }

    public final int getTargetRequestCode() {
        return this.mTargetRequestCode;
    }

    public android.content.Context getContext() {
        if (this.mHost == null) {
            return null;
        }
        return this.mHost.getContext();
    }

    public final android.app.Activity getActivity() {
        if (this.mHost == null) {
            return null;
        }
        return this.mHost.getActivity();
    }

    public final java.lang.Object getHost() {
        if (this.mHost == null) {
            return null;
        }
        return this.mHost.onGetHost();
    }

    public final android.content.res.Resources getResources() {
        if (this.mHost == null) {
            throw new java.lang.IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        return this.mHost.getContext().getResources();
    }

    public final java.lang.CharSequence getText(int i) {
        return getResources().getText(i);
    }

    public final java.lang.String getString(int i) {
        return getResources().getString(i);
    }

    public final java.lang.String getString(int i, java.lang.Object... objArr) {
        return getResources().getString(i, objArr);
    }

    public final android.app.FragmentManager getFragmentManager() {
        return this.mFragmentManager;
    }

    public final android.app.FragmentManager getChildFragmentManager() {
        if (this.mChildFragmentManager == null) {
            instantiateChildFragmentManager();
            if (this.mState >= 5) {
                this.mChildFragmentManager.dispatchResume();
            } else if (this.mState >= 4) {
                this.mChildFragmentManager.dispatchStart();
            } else if (this.mState >= 2) {
                this.mChildFragmentManager.dispatchActivityCreated();
            } else if (this.mState >= 1) {
                this.mChildFragmentManager.dispatchCreate();
            }
        }
        return this.mChildFragmentManager;
    }

    public final android.app.Fragment getParentFragment() {
        return this.mParentFragment;
    }

    public final boolean isAdded() {
        return this.mHost != null && this.mAdded;
    }

    public final boolean isDetached() {
        return this.mDetached;
    }

    public final boolean isRemoving() {
        return this.mRemoving;
    }

    public final boolean isInLayout() {
        return this.mInLayout;
    }

    public final boolean isResumed() {
        return this.mState >= 5;
    }

    public final boolean isVisible() {
        return (!isAdded() || isHidden() || this.mView == null || this.mView.getWindowToken() == null || this.mView.getVisibility() != 0) ? false : true;
    }

    public final boolean isHidden() {
        return this.mHidden;
    }

    public void onHiddenChanged(boolean z) {
    }

    public void setRetainInstance(boolean z) {
        this.mRetainInstance = z;
    }

    public final boolean getRetainInstance() {
        return this.mRetainInstance;
    }

    public void setHasOptionsMenu(boolean z) {
        if (this.mHasMenu != z) {
            this.mHasMenu = z;
            if (isAdded() && !isHidden()) {
                this.mFragmentManager.invalidateOptionsMenu();
            }
        }
    }

    public void setMenuVisibility(boolean z) {
        if (this.mMenuVisible != z) {
            this.mMenuVisible = z;
            if (this.mHasMenu && isAdded() && !isHidden()) {
                this.mFragmentManager.invalidateOptionsMenu();
            }
        }
    }

    public void setUserVisibleHint(boolean z) {
        boolean z2;
        boolean z3;
        android.content.Context context = getContext();
        if (this.mFragmentManager != null && this.mFragmentManager.mHost != null) {
            context = this.mFragmentManager.mHost.getContext();
        }
        if (context == null) {
            z2 = false;
        } else {
            z2 = context.getApplicationInfo().targetSdkVersion <= 23;
        }
        if (z2) {
            z3 = !this.mUserVisibleHint && z && this.mState < 4 && this.mFragmentManager != null;
        } else {
            z3 = !this.mUserVisibleHint && z && this.mState < 4 && this.mFragmentManager != null && isAdded();
        }
        if (z3) {
            this.mFragmentManager.performPendingDeferredStart(this);
        }
        this.mUserVisibleHint = z;
        this.mDeferStart = this.mState < 4 && !z;
    }

    public boolean getUserVisibleHint() {
        return this.mUserVisibleHint;
    }

    @java.lang.Deprecated
    public android.app.LoaderManager getLoaderManager() {
        if (this.mLoaderManager != null) {
            return this.mLoaderManager;
        }
        if (this.mHost == null) {
            throw new java.lang.IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mCheckedForLoaderManager = true;
        this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, true);
        return this.mLoaderManager;
    }

    public void startActivity(android.content.Intent intent) {
        startActivity(intent, null);
    }

    public void startActivity(android.content.Intent intent, android.os.Bundle bundle) {
        if (this.mHost == null) {
            throw new java.lang.IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        if (bundle != null) {
            this.mHost.onStartActivityFromFragment(this, intent, -1, bundle);
        } else {
            this.mHost.onStartActivityFromFragment(this, intent, -1, null);
        }
    }

    public void startActivityForResult(android.content.Intent intent, int i) {
        startActivityForResult(intent, i, null);
    }

    public void startActivityForResult(android.content.Intent intent, int i, android.os.Bundle bundle) {
        if (this.mHost == null) {
            throw new java.lang.IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mHost.onStartActivityFromFragment(this, intent, i, bundle);
    }

    public void startActivityForResultAsUser(android.content.Intent intent, int i, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        if (this.mHost == null) {
            throw new java.lang.IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mHost.onStartActivityAsUserFromFragment(this, intent, i, bundle, userHandle);
    }

    public void startIntentSenderForResult(android.content.IntentSender intentSender, int i, android.content.Intent intent, int i2, int i3, int i4, android.os.Bundle bundle) throws android.content.IntentSender.SendIntentException {
        if (this.mHost == null) {
            throw new java.lang.IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mHost.onStartIntentSenderFromFragment(this, intentSender, i, intent, i2, i3, i4, bundle);
    }

    public void onActivityResult(int i, int i2, android.content.Intent intent) {
    }

    public final void requestPermissions(java.lang.String[] strArr, int i) {
        if (this.mHost == null) {
            throw new java.lang.IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mHost.onRequestPermissionsFromFragment(this, strArr, i);
    }

    public void onRequestPermissionsResult(int i, java.lang.String[] strArr, int[] iArr) {
    }

    public boolean shouldShowRequestPermissionRationale(java.lang.String str) {
        if (this.mHost != null) {
            return this.mHost.getContext().getPackageManager().shouldShowRequestPermissionRationale(str);
        }
        return false;
    }

    public android.view.LayoutInflater onGetLayoutInflater(android.os.Bundle bundle) {
        if (this.mHost == null) {
            throw new java.lang.IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
        }
        android.view.LayoutInflater onGetLayoutInflater = this.mHost.onGetLayoutInflater();
        if (this.mHost.onUseFragmentManagerInflaterFactory()) {
            getChildFragmentManager();
            onGetLayoutInflater.setPrivateFactory(this.mChildFragmentManager.getLayoutInflaterFactory());
        }
        return onGetLayoutInflater;
    }

    public final android.view.LayoutInflater getLayoutInflater() {
        if (this.mLayoutInflater == null) {
            return performGetLayoutInflater(null);
        }
        return this.mLayoutInflater;
    }

    android.view.LayoutInflater performGetLayoutInflater(android.os.Bundle bundle) {
        this.mLayoutInflater = onGetLayoutInflater(bundle);
        return this.mLayoutInflater;
    }

    @java.lang.Deprecated
    public void onInflate(android.util.AttributeSet attributeSet, android.os.Bundle bundle) {
        this.mCalled = true;
    }

    public void onInflate(android.content.Context context, android.util.AttributeSet attributeSet, android.os.Bundle bundle) {
        boolean z;
        boolean z2;
        onInflate(attributeSet, bundle);
        this.mCalled = true;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Fragment);
        setEnterTransition(loadTransition(context, obtainStyledAttributes, getEnterTransition(), null, 4));
        setReturnTransition(loadTransition(context, obtainStyledAttributes, getReturnTransition(), USE_DEFAULT_TRANSITION, 6));
        setExitTransition(loadTransition(context, obtainStyledAttributes, getExitTransition(), null, 3));
        setReenterTransition(loadTransition(context, obtainStyledAttributes, getReenterTransition(), USE_DEFAULT_TRANSITION, 8));
        setSharedElementEnterTransition(loadTransition(context, obtainStyledAttributes, getSharedElementEnterTransition(), null, 5));
        setSharedElementReturnTransition(loadTransition(context, obtainStyledAttributes, getSharedElementReturnTransition(), USE_DEFAULT_TRANSITION, 7));
        if (this.mAnimationInfo == null) {
            z = false;
            z2 = false;
        } else {
            z = this.mAnimationInfo.mAllowEnterTransitionOverlap != null;
            z2 = this.mAnimationInfo.mAllowReturnTransitionOverlap != null;
        }
        if (!z) {
            setAllowEnterTransitionOverlap(obtainStyledAttributes.getBoolean(9, true));
        }
        if (!z2) {
            setAllowReturnTransitionOverlap(obtainStyledAttributes.getBoolean(10, true));
        }
        obtainStyledAttributes.recycle();
        android.app.Activity activity = this.mHost != null ? this.mHost.getActivity() : null;
        if (activity != null) {
            this.mCalled = false;
            onInflate(activity, attributeSet, bundle);
        }
    }

    @java.lang.Deprecated
    public void onInflate(android.app.Activity activity, android.util.AttributeSet attributeSet, android.os.Bundle bundle) {
        this.mCalled = true;
    }

    public void onAttachFragment(android.app.Fragment fragment) {
    }

    public void onAttach(android.content.Context context) {
        this.mCalled = true;
        android.app.Activity activity = this.mHost == null ? null : this.mHost.getActivity();
        if (activity != null) {
            this.mCalled = false;
            onAttach(activity);
        }
    }

    @java.lang.Deprecated
    public void onAttach(android.app.Activity activity) {
        this.mCalled = true;
    }

    public android.animation.Animator onCreateAnimator(int i, boolean z, int i2) {
        return null;
    }

    public void onCreate(android.os.Bundle bundle) {
        this.mCalled = true;
        android.content.Context context = getContext();
        if ((context != null ? context.getApplicationInfo().targetSdkVersion : 0) >= 24) {
            restoreChildFragmentState(bundle, true);
            if (this.mChildFragmentManager != null && !this.mChildFragmentManager.isStateAtLeast(1)) {
                this.mChildFragmentManager.dispatchCreate();
            }
        }
    }

    void restoreChildFragmentState(android.os.Bundle bundle, boolean z) {
        android.os.Parcelable parcelable;
        if (bundle != null && (parcelable = bundle.getParcelable("android:fragments")) != null) {
            if (this.mChildFragmentManager == null) {
                instantiateChildFragmentManager();
            }
            this.mChildFragmentManager.restoreAllState(parcelable, z ? this.mChildNonConfig : null);
            this.mChildNonConfig = null;
            this.mChildFragmentManager.dispatchCreate();
        }
    }

    public android.view.View onCreateView(android.view.LayoutInflater layoutInflater, android.view.ViewGroup viewGroup, android.os.Bundle bundle) {
        return null;
    }

    public void onViewCreated(android.view.View view, android.os.Bundle bundle) {
    }

    public android.view.View getView() {
        return this.mView;
    }

    public void onActivityCreated(android.os.Bundle bundle) {
        this.mCalled = true;
    }

    public void onViewStateRestored(android.os.Bundle bundle) {
        this.mCalled = true;
    }

    public void onStart() {
        this.mCalled = true;
        if (!this.mLoadersStarted) {
            this.mLoadersStarted = true;
            if (!this.mCheckedForLoaderManager) {
                this.mCheckedForLoaderManager = true;
                this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
            } else if (this.mLoaderManager != null) {
                this.mLoaderManager.doStart();
            }
        }
    }

    public void onResume() {
        this.mCalled = true;
    }

    public void onSaveInstanceState(android.os.Bundle bundle) {
    }

    public void onMultiWindowModeChanged(boolean z, android.content.res.Configuration configuration) {
        onMultiWindowModeChanged(z);
    }

    @java.lang.Deprecated
    public void onMultiWindowModeChanged(boolean z) {
    }

    public void onPictureInPictureModeChanged(boolean z, android.content.res.Configuration configuration) {
        onPictureInPictureModeChanged(z);
    }

    @java.lang.Deprecated
    public void onPictureInPictureModeChanged(boolean z) {
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        this.mCalled = true;
    }

    public void onPause() {
        this.mCalled = true;
    }

    public void onStop() {
        this.mCalled = true;
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
        this.mCalled = true;
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        this.mCalled = true;
    }

    public void onDestroyView() {
        this.mCalled = true;
    }

    public void onDestroy() {
        this.mCalled = true;
        if (!this.mCheckedForLoaderManager) {
            this.mCheckedForLoaderManager = true;
            this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
        }
        if (this.mLoaderManager != null) {
            this.mLoaderManager.doDestroy();
        }
    }

    void initState() {
        this.mIndex = -1;
        this.mWho = null;
        this.mAdded = false;
        this.mRemoving = false;
        this.mFromLayout = false;
        this.mInLayout = false;
        this.mRestored = false;
        this.mBackStackNesting = 0;
        this.mFragmentManager = null;
        this.mChildFragmentManager = null;
        this.mHost = null;
        this.mFragmentId = 0;
        this.mContainerId = 0;
        this.mTag = null;
        this.mHidden = false;
        this.mDetached = false;
        this.mRetaining = false;
        this.mLoaderManager = null;
        this.mLoadersStarted = false;
        this.mCheckedForLoaderManager = false;
    }

    public void onDetach() {
        this.mCalled = true;
    }

    public void onCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater menuInflater) {
    }

    public void onPrepareOptionsMenu(android.view.Menu menu) {
    }

    public void onDestroyOptionsMenu() {
    }

    public boolean onOptionsItemSelected(android.view.MenuItem menuItem) {
        return false;
    }

    public void onOptionsMenuClosed(android.view.Menu menu) {
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(android.view.ContextMenu contextMenu, android.view.View view, android.view.ContextMenu.ContextMenuInfo contextMenuInfo) {
        getActivity().onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    public void registerForContextMenu(android.view.View view) {
        view.setOnCreateContextMenuListener(this);
    }

    public void unregisterForContextMenu(android.view.View view) {
        view.setOnCreateContextMenuListener(null);
    }

    public boolean onContextItemSelected(android.view.MenuItem menuItem) {
        return false;
    }

    public void setEnterSharedElementCallback(android.app.SharedElementCallback sharedElementCallback) {
        if (sharedElementCallback == null) {
            if (this.mAnimationInfo == null) {
                return;
            } else {
                sharedElementCallback = android.app.SharedElementCallback.NULL_CALLBACK;
            }
        }
        ensureAnimationInfo().mEnterTransitionCallback = sharedElementCallback;
    }

    public void setExitSharedElementCallback(android.app.SharedElementCallback sharedElementCallback) {
        if (sharedElementCallback == null) {
            if (this.mAnimationInfo == null) {
                return;
            } else {
                sharedElementCallback = android.app.SharedElementCallback.NULL_CALLBACK;
            }
        }
        ensureAnimationInfo().mExitTransitionCallback = sharedElementCallback;
    }

    public void setEnterTransition(android.transition.Transition transition) {
        if (shouldChangeTransition(transition, null)) {
            ensureAnimationInfo().mEnterTransition = transition;
        }
    }

    public android.transition.Transition getEnterTransition() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        return this.mAnimationInfo.mEnterTransition;
    }

    public void setReturnTransition(android.transition.Transition transition) {
        if (shouldChangeTransition(transition, USE_DEFAULT_TRANSITION)) {
            ensureAnimationInfo().mReturnTransition = transition;
        }
    }

    public android.transition.Transition getReturnTransition() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        return this.mAnimationInfo.mReturnTransition == USE_DEFAULT_TRANSITION ? getEnterTransition() : this.mAnimationInfo.mReturnTransition;
    }

    public void setExitTransition(android.transition.Transition transition) {
        if (shouldChangeTransition(transition, null)) {
            ensureAnimationInfo().mExitTransition = transition;
        }
    }

    public android.transition.Transition getExitTransition() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        return this.mAnimationInfo.mExitTransition;
    }

    public void setReenterTransition(android.transition.Transition transition) {
        if (shouldChangeTransition(transition, USE_DEFAULT_TRANSITION)) {
            ensureAnimationInfo().mReenterTransition = transition;
        }
    }

    public android.transition.Transition getReenterTransition() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        return this.mAnimationInfo.mReenterTransition == USE_DEFAULT_TRANSITION ? getExitTransition() : this.mAnimationInfo.mReenterTransition;
    }

    public void setSharedElementEnterTransition(android.transition.Transition transition) {
        if (shouldChangeTransition(transition, null)) {
            ensureAnimationInfo().mSharedElementEnterTransition = transition;
        }
    }

    public android.transition.Transition getSharedElementEnterTransition() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        return this.mAnimationInfo.mSharedElementEnterTransition;
    }

    public void setSharedElementReturnTransition(android.transition.Transition transition) {
        if (shouldChangeTransition(transition, USE_DEFAULT_TRANSITION)) {
            ensureAnimationInfo().mSharedElementReturnTransition = transition;
        }
    }

    public android.transition.Transition getSharedElementReturnTransition() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        if (this.mAnimationInfo.mSharedElementReturnTransition == USE_DEFAULT_TRANSITION) {
            return getSharedElementEnterTransition();
        }
        return this.mAnimationInfo.mSharedElementReturnTransition;
    }

    public void setAllowEnterTransitionOverlap(boolean z) {
        ensureAnimationInfo().mAllowEnterTransitionOverlap = java.lang.Boolean.valueOf(z);
    }

    public boolean getAllowEnterTransitionOverlap() {
        if (this.mAnimationInfo == null || this.mAnimationInfo.mAllowEnterTransitionOverlap == null) {
            return true;
        }
        return this.mAnimationInfo.mAllowEnterTransitionOverlap.booleanValue();
    }

    public void setAllowReturnTransitionOverlap(boolean z) {
        ensureAnimationInfo().mAllowReturnTransitionOverlap = java.lang.Boolean.valueOf(z);
    }

    public boolean getAllowReturnTransitionOverlap() {
        if (this.mAnimationInfo == null || this.mAnimationInfo.mAllowReturnTransitionOverlap == null) {
            return true;
        }
        return this.mAnimationInfo.mAllowReturnTransitionOverlap.booleanValue();
    }

    public void postponeEnterTransition() {
        ensureAnimationInfo().mEnterTransitionPostponed = true;
    }

    public void startPostponedEnterTransition() {
        if (this.mFragmentManager == null || this.mFragmentManager.mHost == null) {
            ensureAnimationInfo().mEnterTransitionPostponed = false;
        } else if (android.os.Looper.myLooper() != this.mFragmentManager.mHost.getHandler().getLooper()) {
            this.mFragmentManager.mHost.getHandler().postAtFrontOfQueue(new java.lang.Runnable() { // from class: android.app.Fragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.Fragment.this.callStartTransitionListener();
                }
            });
        } else {
            callStartTransitionListener();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callStartTransitionListener() {
        android.app.Fragment.OnStartEnterTransitionListener onStartEnterTransitionListener = null;
        if (this.mAnimationInfo != null) {
            this.mAnimationInfo.mEnterTransitionPostponed = false;
            android.app.Fragment.OnStartEnterTransitionListener onStartEnterTransitionListener2 = this.mAnimationInfo.mStartEnterTransitionListener;
            this.mAnimationInfo.mStartEnterTransitionListener = null;
            onStartEnterTransitionListener = onStartEnterTransitionListener2;
        }
        if (onStartEnterTransitionListener != null) {
            onStartEnterTransitionListener.onStartEnterTransition();
        }
    }

    private boolean shouldChangeTransition(android.transition.Transition transition, android.transition.Transition transition2) {
        return (transition == transition2 && this.mAnimationInfo == null) ? false : true;
    }

    public void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.print(str);
        printWriter.print("mFragmentId=#");
        printWriter.print(java.lang.Integer.toHexString(this.mFragmentId));
        printWriter.print(" mContainerId=#");
        printWriter.print(java.lang.Integer.toHexString(this.mContainerId));
        printWriter.print(" mTag=");
        printWriter.println(this.mTag);
        printWriter.print(str);
        printWriter.print("mState=");
        printWriter.print(this.mState);
        printWriter.print(" mIndex=");
        printWriter.print(this.mIndex);
        printWriter.print(" mWho=");
        printWriter.print(this.mWho);
        printWriter.print(" mBackStackNesting=");
        printWriter.println(this.mBackStackNesting);
        printWriter.print(str);
        printWriter.print("mAdded=");
        printWriter.print(this.mAdded);
        printWriter.print(" mRemoving=");
        printWriter.print(this.mRemoving);
        printWriter.print(" mFromLayout=");
        printWriter.print(this.mFromLayout);
        printWriter.print(" mInLayout=");
        printWriter.println(this.mInLayout);
        printWriter.print(str);
        printWriter.print("mHidden=");
        printWriter.print(this.mHidden);
        printWriter.print(" mDetached=");
        printWriter.print(this.mDetached);
        printWriter.print(" mMenuVisible=");
        printWriter.print(this.mMenuVisible);
        printWriter.print(" mHasMenu=");
        printWriter.println(this.mHasMenu);
        printWriter.print(str);
        printWriter.print("mRetainInstance=");
        printWriter.print(this.mRetainInstance);
        printWriter.print(" mRetaining=");
        printWriter.print(this.mRetaining);
        printWriter.print(" mUserVisibleHint=");
        printWriter.println(this.mUserVisibleHint);
        if (this.mFragmentManager != null) {
            printWriter.print(str);
            printWriter.print("mFragmentManager=");
            printWriter.println(this.mFragmentManager);
        }
        if (this.mHost != null) {
            printWriter.print(str);
            printWriter.print("mHost=");
            printWriter.println(this.mHost);
        }
        if (this.mParentFragment != null) {
            printWriter.print(str);
            printWriter.print("mParentFragment=");
            printWriter.println(this.mParentFragment);
        }
        if (this.mArguments != null) {
            printWriter.print(str);
            printWriter.print("mArguments=");
            printWriter.println(this.mArguments);
        }
        if (this.mSavedFragmentState != null) {
            printWriter.print(str);
            printWriter.print("mSavedFragmentState=");
            printWriter.println(this.mSavedFragmentState);
        }
        if (this.mSavedViewState != null) {
            printWriter.print(str);
            printWriter.print("mSavedViewState=");
            printWriter.println(this.mSavedViewState);
        }
        if (this.mTarget != null) {
            printWriter.print(str);
            printWriter.print("mTarget=");
            printWriter.print(this.mTarget);
            printWriter.print(" mTargetRequestCode=");
            printWriter.println(this.mTargetRequestCode);
        }
        if (getNextAnim() != 0) {
            printWriter.print(str);
            printWriter.print("mNextAnim=");
            printWriter.println(getNextAnim());
        }
        if (this.mContainer != null) {
            printWriter.print(str);
            printWriter.print("mContainer=");
            printWriter.println(this.mContainer);
        }
        if (this.mView != null) {
            printWriter.print(str);
            printWriter.print("mView=");
            printWriter.println(this.mView);
        }
        if (getAnimatingAway() != null) {
            printWriter.print(str);
            printWriter.print("mAnimatingAway=");
            printWriter.println(getAnimatingAway());
            printWriter.print(str);
            printWriter.print("mStateAfterAnimating=");
            printWriter.println(getStateAfterAnimating());
        }
        if (this.mLoaderManager != null) {
            printWriter.print(str);
            printWriter.println("Loader Manager:");
            this.mLoaderManager.dump(str + "  ", fileDescriptor, printWriter, strArr);
        }
        if (this.mChildFragmentManager != null) {
            printWriter.print(str);
            printWriter.println("Child " + this.mChildFragmentManager + ":");
            this.mChildFragmentManager.dump(str + "  ", fileDescriptor, printWriter, strArr);
        }
    }

    android.app.Fragment findFragmentByWho(java.lang.String str) {
        if (str.equals(this.mWho)) {
            return this;
        }
        if (this.mChildFragmentManager != null) {
            return this.mChildFragmentManager.findFragmentByWho(str);
        }
        return null;
    }

    void instantiateChildFragmentManager() {
        this.mChildFragmentManager = new android.app.FragmentManagerImpl();
        this.mChildFragmentManager.attachController(this.mHost, new android.app.FragmentContainer() { // from class: android.app.Fragment.1
            @Override // android.app.FragmentContainer
            public <T extends android.view.View> T onFindViewById(int i) {
                if (android.app.Fragment.this.mView == null) {
                    throw new java.lang.IllegalStateException("Fragment does not have a view");
                }
                return (T) android.app.Fragment.this.mView.findViewById(i);
            }

            @Override // android.app.FragmentContainer
            public boolean onHasView() {
                return android.app.Fragment.this.mView != null;
            }
        }, this);
    }

    void performCreate(android.os.Bundle bundle) {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
        }
        this.mState = 1;
        this.mCalled = false;
        onCreate(bundle);
        this.mIsCreated = true;
        if (!this.mCalled) {
            throw new android.util.SuperNotCalledException("Fragment " + this + " did not call through to super.onCreate()");
        }
        android.content.Context context = getContext();
        if ((context != null ? context.getApplicationInfo().targetSdkVersion : 0) < 24) {
            restoreChildFragmentState(bundle, false);
        }
    }

    android.view.View performCreateView(android.view.LayoutInflater layoutInflater, android.view.ViewGroup viewGroup, android.os.Bundle bundle) {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
        }
        this.mPerformedCreateView = true;
        return onCreateView(layoutInflater, viewGroup, bundle);
    }

    void performActivityCreated(android.os.Bundle bundle) {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
        }
        this.mState = 2;
        this.mCalled = false;
        onActivityCreated(bundle);
        if (!this.mCalled) {
            throw new android.util.SuperNotCalledException("Fragment " + this + " did not call through to super.onActivityCreated()");
        }
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchActivityCreated();
        }
    }

    void performStart() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
            this.mChildFragmentManager.execPendingActions();
        }
        this.mState = 4;
        this.mCalled = false;
        onStart();
        if (!this.mCalled) {
            throw new android.util.SuperNotCalledException("Fragment " + this + " did not call through to super.onStart()");
        }
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchStart();
        }
        if (this.mLoaderManager != null) {
            this.mLoaderManager.doReportStart();
        }
    }

    void performResume() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
            this.mChildFragmentManager.execPendingActions();
        }
        this.mState = 5;
        this.mCalled = false;
        onResume();
        if (!this.mCalled) {
            throw new android.util.SuperNotCalledException("Fragment " + this + " did not call through to super.onResume()");
        }
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchResume();
            this.mChildFragmentManager.execPendingActions();
        }
    }

    void noteStateNotSaved() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
        }
    }

    @java.lang.Deprecated
    void performMultiWindowModeChanged(boolean z) {
        onMultiWindowModeChanged(z);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchMultiWindowModeChanged(z);
        }
    }

    void performMultiWindowModeChanged(boolean z, android.content.res.Configuration configuration) {
        onMultiWindowModeChanged(z, configuration);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchMultiWindowModeChanged(z, configuration);
        }
    }

    @java.lang.Deprecated
    void performPictureInPictureModeChanged(boolean z) {
        onPictureInPictureModeChanged(z);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchPictureInPictureModeChanged(z);
        }
    }

    void performPictureInPictureModeChanged(boolean z, android.content.res.Configuration configuration) {
        onPictureInPictureModeChanged(z, configuration);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchPictureInPictureModeChanged(z, configuration);
        }
    }

    void performConfigurationChanged(android.content.res.Configuration configuration) {
        onConfigurationChanged(configuration);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchConfigurationChanged(configuration);
        }
    }

    void performLowMemory() {
        onLowMemory();
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchLowMemory();
        }
    }

    void performTrimMemory(int i) {
        onTrimMemory(i);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchTrimMemory(i);
        }
    }

    boolean performCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater menuInflater) {
        boolean z = false;
        if (this.mHidden) {
            return false;
        }
        if (this.mHasMenu && this.mMenuVisible) {
            onCreateOptionsMenu(menu, menuInflater);
            z = true;
        }
        if (this.mChildFragmentManager != null) {
            return z | this.mChildFragmentManager.dispatchCreateOptionsMenu(menu, menuInflater);
        }
        return z;
    }

    boolean performPrepareOptionsMenu(android.view.Menu menu) {
        boolean z = false;
        if (this.mHidden) {
            return false;
        }
        if (this.mHasMenu && this.mMenuVisible) {
            onPrepareOptionsMenu(menu);
            z = true;
        }
        if (this.mChildFragmentManager != null) {
            return z | this.mChildFragmentManager.dispatchPrepareOptionsMenu(menu);
        }
        return z;
    }

    boolean performOptionsItemSelected(android.view.MenuItem menuItem) {
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible && onOptionsItemSelected(menuItem)) {
                return true;
            }
            return this.mChildFragmentManager != null && this.mChildFragmentManager.dispatchOptionsItemSelected(menuItem);
        }
        return false;
    }

    boolean performContextItemSelected(android.view.MenuItem menuItem) {
        if (!this.mHidden) {
            if (onContextItemSelected(menuItem)) {
                return true;
            }
            return this.mChildFragmentManager != null && this.mChildFragmentManager.dispatchContextItemSelected(menuItem);
        }
        return false;
    }

    void performOptionsMenuClosed(android.view.Menu menu) {
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible) {
                onOptionsMenuClosed(menu);
            }
            if (this.mChildFragmentManager != null) {
                this.mChildFragmentManager.dispatchOptionsMenuClosed(menu);
            }
        }
    }

    void performSaveInstanceState(android.os.Bundle bundle) {
        android.os.Parcelable saveAllState;
        onSaveInstanceState(bundle);
        if (this.mChildFragmentManager != null && (saveAllState = this.mChildFragmentManager.saveAllState()) != null) {
            bundle.putParcelable("android:fragments", saveAllState);
        }
    }

    void performPause() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchPause();
        }
        this.mState = 4;
        this.mCalled = false;
        onPause();
        if (!this.mCalled) {
            throw new android.util.SuperNotCalledException("Fragment " + this + " did not call through to super.onPause()");
        }
    }

    void performStop() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchStop();
        }
        this.mState = 3;
        this.mCalled = false;
        onStop();
        if (!this.mCalled) {
            throw new android.util.SuperNotCalledException("Fragment " + this + " did not call through to super.onStop()");
        }
        if (this.mLoadersStarted) {
            this.mLoadersStarted = false;
            if (!this.mCheckedForLoaderManager) {
                this.mCheckedForLoaderManager = true;
                this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
            }
            if (this.mLoaderManager != null) {
                if (this.mHost.getRetainLoaders()) {
                    this.mLoaderManager.doRetain();
                } else {
                    this.mLoaderManager.doStop();
                }
            }
        }
    }

    void performDestroyView() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchDestroyView();
        }
        this.mState = 1;
        this.mCalled = false;
        onDestroyView();
        if (!this.mCalled) {
            throw new android.util.SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroyView()");
        }
        if (this.mLoaderManager != null) {
            this.mLoaderManager.doReportNextStart();
        }
        this.mPerformedCreateView = false;
    }

    void performDestroy() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchDestroy();
        }
        this.mState = 0;
        this.mCalled = false;
        this.mIsCreated = false;
        onDestroy();
        if (!this.mCalled) {
            throw new android.util.SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroy()");
        }
        this.mChildFragmentManager = null;
    }

    void performDetach() {
        this.mCalled = false;
        onDetach();
        this.mLayoutInflater = null;
        if (!this.mCalled) {
            throw new android.util.SuperNotCalledException("Fragment " + this + " did not call through to super.onDetach()");
        }
        if (this.mChildFragmentManager != null) {
            if (!this.mRetaining) {
                throw new java.lang.IllegalStateException("Child FragmentManager of " + this + " was not  destroyed and this fragment is not retaining instance");
            }
            this.mChildFragmentManager.dispatchDestroy();
            this.mChildFragmentManager = null;
        }
    }

    void setOnStartEnterTransitionListener(android.app.Fragment.OnStartEnterTransitionListener onStartEnterTransitionListener) {
        ensureAnimationInfo();
        if (onStartEnterTransitionListener == this.mAnimationInfo.mStartEnterTransitionListener) {
            return;
        }
        if (onStartEnterTransitionListener != null && this.mAnimationInfo.mStartEnterTransitionListener != null) {
            throw new java.lang.IllegalStateException("Trying to set a replacement startPostponedEnterTransition on " + this);
        }
        if (this.mAnimationInfo.mEnterTransitionPostponed) {
            this.mAnimationInfo.mStartEnterTransitionListener = onStartEnterTransitionListener;
        }
        if (onStartEnterTransitionListener != null) {
            onStartEnterTransitionListener.startListening();
        }
    }

    private static android.transition.Transition loadTransition(android.content.Context context, android.content.res.TypedArray typedArray, android.transition.Transition transition, android.transition.Transition transition2, int i) {
        if (transition != transition2) {
            return transition;
        }
        int resourceId = typedArray.getResourceId(i, 0);
        if (resourceId != 0 && resourceId != 17760256) {
            android.transition.Transition inflateTransition = android.transition.TransitionInflater.from(context).inflateTransition(resourceId);
            if ((inflateTransition instanceof android.transition.TransitionSet) && ((android.transition.TransitionSet) inflateTransition).getTransitionCount() == 0) {
                return null;
            }
            return inflateTransition;
        }
        return transition2;
    }

    private android.app.Fragment.AnimationInfo ensureAnimationInfo() {
        if (this.mAnimationInfo == null) {
            this.mAnimationInfo = new android.app.Fragment.AnimationInfo();
        }
        return this.mAnimationInfo;
    }

    int getNextAnim() {
        if (this.mAnimationInfo == null) {
            return 0;
        }
        return this.mAnimationInfo.mNextAnim;
    }

    void setNextAnim(int i) {
        if (this.mAnimationInfo == null && i == 0) {
            return;
        }
        ensureAnimationInfo().mNextAnim = i;
    }

    int getNextTransition() {
        if (this.mAnimationInfo == null) {
            return 0;
        }
        return this.mAnimationInfo.mNextTransition;
    }

    void setNextTransition(int i, int i2) {
        if (this.mAnimationInfo == null && i == 0 && i2 == 0) {
            return;
        }
        ensureAnimationInfo();
        this.mAnimationInfo.mNextTransition = i;
        this.mAnimationInfo.mNextTransitionStyle = i2;
    }

    int getNextTransitionStyle() {
        if (this.mAnimationInfo == null) {
            return 0;
        }
        return this.mAnimationInfo.mNextTransitionStyle;
    }

    android.app.SharedElementCallback getEnterTransitionCallback() {
        if (this.mAnimationInfo == null) {
            return android.app.SharedElementCallback.NULL_CALLBACK;
        }
        return this.mAnimationInfo.mEnterTransitionCallback;
    }

    android.app.SharedElementCallback getExitTransitionCallback() {
        if (this.mAnimationInfo == null) {
            return android.app.SharedElementCallback.NULL_CALLBACK;
        }
        return this.mAnimationInfo.mExitTransitionCallback;
    }

    android.animation.Animator getAnimatingAway() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        return this.mAnimationInfo.mAnimatingAway;
    }

    void setAnimatingAway(android.animation.Animator animator) {
        ensureAnimationInfo().mAnimatingAway = animator;
    }

    int getStateAfterAnimating() {
        if (this.mAnimationInfo == null) {
            return 0;
        }
        return this.mAnimationInfo.mStateAfterAnimating;
    }

    void setStateAfterAnimating(int i) {
        ensureAnimationInfo().mStateAfterAnimating = i;
    }

    boolean isPostponed() {
        if (this.mAnimationInfo == null) {
            return false;
        }
        return this.mAnimationInfo.mEnterTransitionPostponed;
    }

    boolean isHideReplaced() {
        if (this.mAnimationInfo == null) {
            return false;
        }
        return this.mAnimationInfo.mIsHideReplaced;
    }

    void setHideReplaced(boolean z) {
        ensureAnimationInfo().mIsHideReplaced = z;
    }

    static class AnimationInfo {
        private java.lang.Boolean mAllowEnterTransitionOverlap;
        private java.lang.Boolean mAllowReturnTransitionOverlap;
        android.animation.Animator mAnimatingAway;
        boolean mEnterTransitionPostponed;
        boolean mIsHideReplaced;
        int mNextAnim;
        int mNextTransition;
        int mNextTransitionStyle;
        android.app.Fragment.OnStartEnterTransitionListener mStartEnterTransitionListener;
        int mStateAfterAnimating;
        private android.transition.Transition mEnterTransition = null;
        private android.transition.Transition mReturnTransition = android.app.Fragment.USE_DEFAULT_TRANSITION;
        private android.transition.Transition mExitTransition = null;
        private android.transition.Transition mReenterTransition = android.app.Fragment.USE_DEFAULT_TRANSITION;
        private android.transition.Transition mSharedElementEnterTransition = null;
        private android.transition.Transition mSharedElementReturnTransition = android.app.Fragment.USE_DEFAULT_TRANSITION;
        android.app.SharedElementCallback mEnterTransitionCallback = android.app.SharedElementCallback.NULL_CALLBACK;
        android.app.SharedElementCallback mExitTransitionCallback = android.app.SharedElementCallback.NULL_CALLBACK;

        AnimationInfo() {
        }
    }
}
