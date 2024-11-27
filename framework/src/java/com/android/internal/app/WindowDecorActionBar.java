package com.android.internal.app;

/* loaded from: classes4.dex */
public class WindowDecorActionBar extends android.app.ActionBar implements com.android.internal.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int CONTEXT_DISPLAY_NORMAL = 0;
    private static final int CONTEXT_DISPLAY_SPLIT = 1;
    private static final long FADE_IN_DURATION_MS = 200;
    private static final long FADE_OUT_DURATION_MS = 100;
    private static final int INVALID_POSITION = -1;
    private static final java.lang.String TAG = "WindowDecorActionBar";
    android.view.ActionMode mActionMode;
    private android.app.Activity mActivity;
    private com.android.internal.widget.ActionBarContainer mContainerView;
    private android.view.View mContentView;
    private android.content.Context mContext;
    private int mContextDisplayMode;
    private com.android.internal.widget.ActionBarContextView mContextView;
    private android.animation.Animator mCurrentShowAnim;
    private com.android.internal.widget.DecorToolbar mDecorToolbar;
    android.view.ActionMode mDeferredDestroyActionMode;
    android.view.ActionMode.Callback mDeferredModeDestroyCallback;
    private android.app.Dialog mDialog;
    private boolean mDisplayHomeAsUpSet;
    private boolean mHasEmbeddedTabs;
    private boolean mHiddenByApp;
    private boolean mHiddenBySystem;
    boolean mHideOnContentScroll;
    private boolean mLastMenuVisibility;
    private com.android.internal.widget.ActionBarOverlayLayout mOverlayLayout;
    private com.android.internal.app.WindowDecorActionBar.TabImpl mSelectedTab;
    private boolean mShowHideAnimationEnabled;
    private boolean mShowingForMode;
    private com.android.internal.widget.ActionBarContainer mSplitView;
    private com.android.internal.widget.ScrollingTabContainerView mTabScrollView;
    private android.content.Context mThemedContext;
    private java.util.ArrayList<com.android.internal.app.WindowDecorActionBar.TabImpl> mTabs = new java.util.ArrayList<>();
    private int mSavedTabPosition = -1;
    private java.util.ArrayList<android.app.ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new java.util.ArrayList<>();
    private int mCurWindowVisibility = 0;
    private boolean mContentAnimations = true;
    private boolean mNowShowing = true;
    final android.animation.Animator.AnimatorListener mHideListener = new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.app.WindowDecorActionBar.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            if (com.android.internal.app.WindowDecorActionBar.this.mContentAnimations && com.android.internal.app.WindowDecorActionBar.this.mContentView != null) {
                com.android.internal.app.WindowDecorActionBar.this.mContentView.setTranslationY(0.0f);
                com.android.internal.app.WindowDecorActionBar.this.mContainerView.setTranslationY(0.0f);
            }
            if (com.android.internal.app.WindowDecorActionBar.this.mSplitView != null && com.android.internal.app.WindowDecorActionBar.this.mContextDisplayMode == 1) {
                com.android.internal.app.WindowDecorActionBar.this.mSplitView.setVisibility(8);
            }
            com.android.internal.app.WindowDecorActionBar.this.mContainerView.setVisibility(8);
            com.android.internal.app.WindowDecorActionBar.this.mContainerView.setTransitioning(false);
            com.android.internal.app.WindowDecorActionBar.this.mCurrentShowAnim = null;
            com.android.internal.app.WindowDecorActionBar.this.completeDeferredDestroyActionMode();
            if (com.android.internal.app.WindowDecorActionBar.this.mOverlayLayout != null) {
                com.android.internal.app.WindowDecorActionBar.this.mOverlayLayout.requestApplyInsets();
            }
        }
    };
    final android.animation.Animator.AnimatorListener mShowListener = new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.app.WindowDecorActionBar.2
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            com.android.internal.app.WindowDecorActionBar.this.mCurrentShowAnim = null;
            com.android.internal.app.WindowDecorActionBar.this.mContainerView.requestLayout();
        }
    };
    final android.animation.ValueAnimator.AnimatorUpdateListener mUpdateListener = new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.app.WindowDecorActionBar.3
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
            ((android.view.View) com.android.internal.app.WindowDecorActionBar.this.mContainerView.getParent()).invalidate();
        }
    };

    public WindowDecorActionBar(android.app.Activity activity) {
        this.mActivity = activity;
        android.view.View decorView = activity.getWindow().getDecorView();
        boolean hasFeature = this.mActivity.getWindow().hasFeature(9);
        init(decorView);
        if (!hasFeature) {
            this.mContentView = decorView.findViewById(16908290);
        }
    }

    public WindowDecorActionBar(android.app.Dialog dialog) {
        this.mDialog = dialog;
        init(dialog.getWindow().getDecorView());
    }

    public WindowDecorActionBar(android.view.View view) {
        init(view);
    }

    private void init(android.view.View view) {
        this.mOverlayLayout = (com.android.internal.widget.ActionBarOverlayLayout) view.findViewById(com.android.internal.R.id.decor_content_parent);
        if (this.mOverlayLayout != null) {
            this.mOverlayLayout.setActionBarVisibilityCallback(this);
        }
        this.mDecorToolbar = getDecorToolbar(view.findViewById(com.android.internal.R.id.action_bar));
        this.mContextView = (com.android.internal.widget.ActionBarContextView) view.findViewById(com.android.internal.R.id.action_context_bar);
        this.mContainerView = (com.android.internal.widget.ActionBarContainer) view.findViewById(com.android.internal.R.id.action_bar_container);
        this.mSplitView = (com.android.internal.widget.ActionBarContainer) view.findViewById(com.android.internal.R.id.split_action_bar);
        if (this.mDecorToolbar == null || this.mContextView == null || this.mContainerView == null) {
            throw new java.lang.IllegalStateException(getClass().getSimpleName() + " can only be used with a compatible window decor layout");
        }
        this.mContext = this.mDecorToolbar.getContext();
        this.mContextDisplayMode = this.mDecorToolbar.isSplit() ? 1 : 0;
        boolean z = (this.mDecorToolbar.getDisplayOptions() & 4) != 0;
        if (z) {
            this.mDisplayHomeAsUpSet = true;
        }
        com.android.internal.view.ActionBarPolicy actionBarPolicy = com.android.internal.view.ActionBarPolicy.get(this.mContext);
        setHomeButtonEnabled(actionBarPolicy.enableHomeButtonByDefault() || z);
        setHasEmbeddedTabs(actionBarPolicy.hasEmbeddedTabs());
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(null, com.android.internal.R.styleable.ActionBar, 16843470, 0);
        if (obtainStyledAttributes.getBoolean(21, false)) {
            setHideOnContentScrollEnabled(true);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(20, 0);
        if (dimensionPixelSize != 0) {
            setElevation(dimensionPixelSize);
        }
        obtainStyledAttributes.recycle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private com.android.internal.widget.DecorToolbar getDecorToolbar(android.view.View view) {
        if (view instanceof com.android.internal.widget.DecorToolbar) {
            return (com.android.internal.widget.DecorToolbar) view;
        }
        if (view instanceof android.widget.Toolbar) {
            return ((android.widget.Toolbar) view).getWrapper();
        }
        throw new java.lang.IllegalStateException("Can't make a decor toolbar out of " + view.getClass().getSimpleName());
    }

    @Override // android.app.ActionBar
    public void setElevation(float f) {
        this.mContainerView.setElevation(f);
        if (this.mSplitView != null) {
            this.mSplitView.setElevation(f);
        }
    }

    @Override // android.app.ActionBar
    public float getElevation() {
        return this.mContainerView.getElevation();
    }

    @Override // android.app.ActionBar
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        setHasEmbeddedTabs(com.android.internal.view.ActionBarPolicy.get(this.mContext).hasEmbeddedTabs());
    }

    private void setHasEmbeddedTabs(boolean z) {
        this.mHasEmbeddedTabs = z;
        if (!this.mHasEmbeddedTabs) {
            this.mDecorToolbar.setEmbeddedTabView(null);
            this.mContainerView.setTabContainer(this.mTabScrollView);
        } else {
            this.mContainerView.setTabContainer(null);
            this.mDecorToolbar.setEmbeddedTabView(this.mTabScrollView);
        }
        boolean z2 = getNavigationMode() == 2;
        if (this.mTabScrollView != null) {
            if (z2) {
                this.mTabScrollView.setVisibility(0);
                if (this.mOverlayLayout != null) {
                    this.mOverlayLayout.requestApplyInsets();
                }
            } else {
                this.mTabScrollView.setVisibility(8);
            }
        }
        this.mDecorToolbar.setCollapsible(!this.mHasEmbeddedTabs && z2);
        this.mOverlayLayout.setHasNonEmbeddedTabs(!this.mHasEmbeddedTabs && z2);
    }

    private void ensureTabsExist() {
        if (this.mTabScrollView != null) {
            return;
        }
        com.android.internal.widget.ScrollingTabContainerView scrollingTabContainerView = new com.android.internal.widget.ScrollingTabContainerView(this.mContext);
        if (this.mHasEmbeddedTabs) {
            scrollingTabContainerView.setVisibility(0);
            this.mDecorToolbar.setEmbeddedTabView(scrollingTabContainerView);
        } else {
            if (getNavigationMode() == 2) {
                scrollingTabContainerView.setVisibility(0);
                if (this.mOverlayLayout != null) {
                    this.mOverlayLayout.requestApplyInsets();
                }
            } else {
                scrollingTabContainerView.setVisibility(8);
            }
            this.mContainerView.setTabContainer(scrollingTabContainerView);
        }
        this.mTabScrollView = scrollingTabContainerView;
    }

    void completeDeferredDestroyActionMode() {
        if (this.mDeferredModeDestroyCallback != null) {
            this.mDeferredModeDestroyCallback.onDestroyActionMode(this.mDeferredDestroyActionMode);
            this.mDeferredDestroyActionMode = null;
            this.mDeferredModeDestroyCallback = null;
        }
    }

    @Override // com.android.internal.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void onWindowVisibilityChanged(int i) {
        this.mCurWindowVisibility = i;
    }

    @Override // android.app.ActionBar
    public void setShowHideAnimationEnabled(boolean z) {
        this.mShowHideAnimationEnabled = z;
        if (!z && this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.end();
        }
    }

    @Override // android.app.ActionBar
    public void addOnMenuVisibilityListener(android.app.ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.mMenuVisibilityListeners.add(onMenuVisibilityListener);
    }

    @Override // android.app.ActionBar
    public void removeOnMenuVisibilityListener(android.app.ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.mMenuVisibilityListeners.remove(onMenuVisibilityListener);
    }

    @Override // android.app.ActionBar
    public void dispatchMenuVisibilityChanged(boolean z) {
        if (z == this.mLastMenuVisibility) {
            return;
        }
        this.mLastMenuVisibility = z;
        int size = this.mMenuVisibilityListeners.size();
        for (int i = 0; i < size; i++) {
            this.mMenuVisibilityListeners.get(i).onMenuVisibilityChanged(z);
        }
    }

    @Override // android.app.ActionBar
    public void setCustomView(int i) {
        setCustomView(android.view.LayoutInflater.from(getThemedContext()).inflate(i, this.mDecorToolbar.getViewGroup(), false));
    }

    @Override // android.app.ActionBar
    public void setDisplayUseLogoEnabled(boolean z) {
        setDisplayOptions(z ? 1 : 0, 1);
    }

    @Override // android.app.ActionBar
    public void setDisplayShowHomeEnabled(boolean z) {
        setDisplayOptions(z ? 2 : 0, 2);
    }

    @Override // android.app.ActionBar
    public void setDisplayHomeAsUpEnabled(boolean z) {
        setDisplayOptions(z ? 4 : 0, 4);
    }

    @Override // android.app.ActionBar
    public void setDisplayShowTitleEnabled(boolean z) {
        setDisplayOptions(z ? 8 : 0, 8);
    }

    @Override // android.app.ActionBar
    public void setDisplayShowCustomEnabled(boolean z) {
        setDisplayOptions(z ? 16 : 0, 16);
    }

    @Override // android.app.ActionBar
    public void setHomeButtonEnabled(boolean z) {
        this.mDecorToolbar.setHomeButtonEnabled(z);
    }

    @Override // android.app.ActionBar
    public void setTitle(int i) {
        setTitle(this.mContext.getString(i));
    }

    @Override // android.app.ActionBar
    public void setSubtitle(int i) {
        setSubtitle(this.mContext.getString(i));
    }

    @Override // android.app.ActionBar
    public void setSelectedNavigationItem(int i) {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                this.mDecorToolbar.setDropdownSelectedPosition(i);
                return;
            case 2:
                selectTab(this.mTabs.get(i));
                return;
            default:
                throw new java.lang.IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
        }
    }

    @Override // android.app.ActionBar
    public void removeAllTabs() {
        cleanupTabs();
    }

    private void cleanupTabs() {
        if (this.mSelectedTab != null) {
            selectTab(null);
        }
        this.mTabs.clear();
        if (this.mTabScrollView != null) {
            this.mTabScrollView.removeAllTabs();
        }
        this.mSavedTabPosition = -1;
    }

    @Override // android.app.ActionBar
    public void setTitle(java.lang.CharSequence charSequence) {
        this.mDecorToolbar.setTitle(charSequence);
    }

    @Override // android.app.ActionBar
    public void setWindowTitle(java.lang.CharSequence charSequence) {
        this.mDecorToolbar.setWindowTitle(charSequence);
    }

    @Override // android.app.ActionBar
    public void setSubtitle(java.lang.CharSequence charSequence) {
        this.mDecorToolbar.setSubtitle(charSequence);
    }

    @Override // android.app.ActionBar
    public void setDisplayOptions(int i) {
        if ((i & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.mDecorToolbar.setDisplayOptions(i);
    }

    @Override // android.app.ActionBar
    public void setDisplayOptions(int i, int i2) {
        int displayOptions = this.mDecorToolbar.getDisplayOptions();
        if ((i2 & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.mDecorToolbar.setDisplayOptions((i & i2) | ((~i2) & displayOptions));
    }

    @Override // android.app.ActionBar
    public void setBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        this.mContainerView.setPrimaryBackground(drawable);
    }

    @Override // android.app.ActionBar
    public void setStackedBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        this.mContainerView.setStackedBackground(drawable);
    }

    @Override // android.app.ActionBar
    public void setSplitBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        if (this.mSplitView != null) {
            this.mSplitView.setSplitBackground(drawable);
        }
    }

    @Override // android.app.ActionBar
    public android.view.View getCustomView() {
        return this.mDecorToolbar.getCustomView();
    }

    @Override // android.app.ActionBar
    public java.lang.CharSequence getTitle() {
        return this.mDecorToolbar.getTitle();
    }

    @Override // android.app.ActionBar
    public java.lang.CharSequence getSubtitle() {
        return this.mDecorToolbar.getSubtitle();
    }

    @Override // android.app.ActionBar
    public int getNavigationMode() {
        return this.mDecorToolbar.getNavigationMode();
    }

    @Override // android.app.ActionBar
    public int getDisplayOptions() {
        return this.mDecorToolbar.getDisplayOptions();
    }

    @Override // android.app.ActionBar
    public android.view.ActionMode startActionMode(android.view.ActionMode.Callback callback) {
        if (this.mActionMode != null) {
            this.mActionMode.finish();
        }
        this.mOverlayLayout.setHideOnContentScrollEnabled(false);
        this.mContextView.killMode();
        com.android.internal.app.WindowDecorActionBar.ActionModeImpl actionModeImpl = new com.android.internal.app.WindowDecorActionBar.ActionModeImpl(this.mContextView.getContext(), callback);
        if (actionModeImpl.dispatchOnCreate()) {
            this.mActionMode = actionModeImpl;
            actionModeImpl.invalidate();
            this.mContextView.initForMode(actionModeImpl);
            animateToMode(true);
            if (this.mSplitView != null && this.mContextDisplayMode == 1 && this.mSplitView.getVisibility() != 0) {
                this.mSplitView.setVisibility(0);
                if (this.mOverlayLayout != null) {
                    this.mOverlayLayout.requestApplyInsets();
                }
            }
            this.mContextView.sendAccessibilityEvent(32);
            return actionModeImpl;
        }
        return null;
    }

    private void configureTab(android.app.ActionBar.Tab tab, int i) {
        com.android.internal.app.WindowDecorActionBar.TabImpl tabImpl = (com.android.internal.app.WindowDecorActionBar.TabImpl) tab;
        if (tabImpl.getCallback() == null) {
            throw new java.lang.IllegalStateException("Action Bar Tab must have a Callback");
        }
        tabImpl.setPosition(i);
        this.mTabs.add(i, tabImpl);
        int size = this.mTabs.size();
        while (true) {
            i++;
            if (i < size) {
                this.mTabs.get(i).setPosition(i);
            } else {
                return;
            }
        }
    }

    @Override // android.app.ActionBar
    public void addTab(android.app.ActionBar.Tab tab) {
        addTab(tab, this.mTabs.isEmpty());
    }

    @Override // android.app.ActionBar
    public void addTab(android.app.ActionBar.Tab tab, int i) {
        addTab(tab, i, this.mTabs.isEmpty());
    }

    @Override // android.app.ActionBar
    public void addTab(android.app.ActionBar.Tab tab, boolean z) {
        ensureTabsExist();
        this.mTabScrollView.addTab(tab, z);
        configureTab(tab, this.mTabs.size());
        if (z) {
            selectTab(tab);
        }
    }

    @Override // android.app.ActionBar
    public void addTab(android.app.ActionBar.Tab tab, int i, boolean z) {
        ensureTabsExist();
        this.mTabScrollView.addTab(tab, i, z);
        configureTab(tab, i);
        if (z) {
            selectTab(tab);
        }
    }

    @Override // android.app.ActionBar
    public android.app.ActionBar.Tab newTab() {
        return new com.android.internal.app.WindowDecorActionBar.TabImpl();
    }

    @Override // android.app.ActionBar
    public void removeTab(android.app.ActionBar.Tab tab) {
        removeTabAt(tab.getPosition());
    }

    @Override // android.app.ActionBar
    public void removeTabAt(int i) {
        if (this.mTabScrollView == null) {
            return;
        }
        int position = this.mSelectedTab != null ? this.mSelectedTab.getPosition() : this.mSavedTabPosition;
        this.mTabScrollView.removeTabAt(i);
        com.android.internal.app.WindowDecorActionBar.TabImpl remove = this.mTabs.remove(i);
        if (remove != null) {
            remove.setPosition(-1);
        }
        int size = this.mTabs.size();
        for (int i2 = i; i2 < size; i2++) {
            this.mTabs.get(i2).setPosition(i2);
        }
        if (position == i) {
            selectTab(this.mTabs.isEmpty() ? null : this.mTabs.get(java.lang.Math.max(0, i - 1)));
        }
    }

    @Override // android.app.ActionBar
    public void selectTab(android.app.ActionBar.Tab tab) {
        if (getNavigationMode() != 2) {
            this.mSavedTabPosition = tab != null ? tab.getPosition() : -1;
            return;
        }
        android.app.FragmentTransaction disallowAddToBackStack = this.mDecorToolbar.getViewGroup().isInEditMode() ? null : this.mActivity.getFragmentManager().beginTransaction().disallowAddToBackStack();
        if (this.mSelectedTab == tab) {
            if (this.mSelectedTab != null) {
                this.mSelectedTab.getCallback().onTabReselected(this.mSelectedTab, disallowAddToBackStack);
                this.mTabScrollView.animateToTab(tab.getPosition());
            }
        } else {
            this.mTabScrollView.setTabSelected(tab != null ? tab.getPosition() : -1);
            if (this.mSelectedTab != null) {
                this.mSelectedTab.getCallback().onTabUnselected(this.mSelectedTab, disallowAddToBackStack);
            }
            this.mSelectedTab = (com.android.internal.app.WindowDecorActionBar.TabImpl) tab;
            if (this.mSelectedTab != null) {
                this.mSelectedTab.getCallback().onTabSelected(this.mSelectedTab, disallowAddToBackStack);
            }
        }
        if (disallowAddToBackStack != null && !disallowAddToBackStack.isEmpty()) {
            disallowAddToBackStack.commit();
        }
    }

    @Override // android.app.ActionBar
    public android.app.ActionBar.Tab getSelectedTab() {
        return this.mSelectedTab;
    }

    @Override // android.app.ActionBar
    public int getHeight() {
        return this.mContainerView.getHeight();
    }

    @Override // com.android.internal.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void enableContentAnimations(boolean z) {
        this.mContentAnimations = z;
    }

    @Override // android.app.ActionBar
    public void show() {
        if (this.mHiddenByApp) {
            this.mHiddenByApp = false;
            updateVisibility(false);
        }
    }

    private void showForActionMode() {
        if (!this.mShowingForMode) {
            this.mShowingForMode = true;
            if (this.mOverlayLayout != null) {
                this.mOverlayLayout.setShowingForActionMode(true);
            }
            updateVisibility(false);
        }
    }

    @Override // com.android.internal.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void showForSystem() {
        if (this.mHiddenBySystem) {
            this.mHiddenBySystem = false;
            updateVisibility(true);
        }
    }

    @Override // android.app.ActionBar
    public void hide() {
        if (!this.mHiddenByApp) {
            this.mHiddenByApp = true;
            updateVisibility(false);
        }
    }

    private void hideForActionMode() {
        if (this.mShowingForMode) {
            this.mShowingForMode = false;
            if (this.mOverlayLayout != null) {
                this.mOverlayLayout.setShowingForActionMode(false);
            }
            updateVisibility(false);
        }
    }

    @Override // com.android.internal.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void hideForSystem() {
        if (!this.mHiddenBySystem) {
            this.mHiddenBySystem = true;
            updateVisibility(true);
        }
    }

    @Override // android.app.ActionBar
    public void setHideOnContentScrollEnabled(boolean z) {
        if (z && !this.mOverlayLayout.isInOverlayMode()) {
            throw new java.lang.IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
        }
        this.mHideOnContentScroll = z;
        this.mOverlayLayout.setHideOnContentScrollEnabled(z);
    }

    @Override // android.app.ActionBar
    public boolean isHideOnContentScrollEnabled() {
        return this.mOverlayLayout.isHideOnContentScrollEnabled();
    }

    @Override // android.app.ActionBar
    public int getHideOffset() {
        return this.mOverlayLayout.getActionBarHideOffset();
    }

    @Override // android.app.ActionBar
    public void setHideOffset(int i) {
        if (i != 0 && !this.mOverlayLayout.isInOverlayMode()) {
            throw new java.lang.IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to set a non-zero hide offset");
        }
        this.mOverlayLayout.setActionBarHideOffset(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean checkShowingFlags(boolean z, boolean z2, boolean z3) {
        if (z3) {
            return true;
        }
        if (!z && !z2) {
            return true;
        }
        return false;
    }

    private void updateVisibility(boolean z) {
        if (checkShowingFlags(this.mHiddenByApp, this.mHiddenBySystem, this.mShowingForMode)) {
            if (!this.mNowShowing) {
                this.mNowShowing = true;
                doShow(z);
                return;
            }
            return;
        }
        if (this.mNowShowing) {
            this.mNowShowing = false;
            doHide(z);
        }
    }

    public void doShow(boolean z) {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.end();
        }
        this.mContainerView.setVisibility(0);
        if (this.mCurWindowVisibility == 0 && (this.mShowHideAnimationEnabled || z)) {
            this.mContainerView.setTranslationY(0.0f);
            float f = -this.mContainerView.getHeight();
            if (z) {
                this.mContainerView.getLocationInWindow(new int[]{0, 0});
                f -= r9[1];
            }
            this.mContainerView.setTranslationY(f);
            android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
            android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(this.mContainerView, (android.util.Property<com.android.internal.widget.ActionBarContainer, java.lang.Float>) android.view.View.TRANSLATION_Y, 0.0f);
            ofFloat.addUpdateListener(this.mUpdateListener);
            android.animation.AnimatorSet.Builder play = animatorSet.play(ofFloat);
            if (this.mContentAnimations && this.mContentView != null) {
                play.with(android.animation.ObjectAnimator.ofFloat(this.mContentView, android.view.View.TRANSLATION_Y, f, 0.0f));
            }
            if (this.mSplitView != null && this.mContextDisplayMode == 1) {
                this.mSplitView.setTranslationY(this.mSplitView.getHeight());
                this.mSplitView.setVisibility(0);
                play.with(android.animation.ObjectAnimator.ofFloat(this.mSplitView, (android.util.Property<com.android.internal.widget.ActionBarContainer, java.lang.Float>) android.view.View.TRANSLATION_Y, 0.0f));
            }
            animatorSet.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(this.mContext, 17563651));
            animatorSet.setDuration(250L);
            animatorSet.addListener(this.mShowListener);
            this.mCurrentShowAnim = animatorSet;
            animatorSet.start();
        } else {
            this.mContainerView.setAlpha(1.0f);
            this.mContainerView.setTranslationY(0.0f);
            if (this.mContentAnimations && this.mContentView != null) {
                this.mContentView.setTranslationY(0.0f);
            }
            if (this.mSplitView != null && this.mContextDisplayMode == 1) {
                this.mSplitView.setAlpha(1.0f);
                this.mSplitView.setTranslationY(0.0f);
                this.mSplitView.setVisibility(0);
            }
            this.mShowListener.onAnimationEnd(null);
        }
        if (this.mOverlayLayout != null) {
            this.mOverlayLayout.requestApplyInsets();
        }
    }

    public void doHide(boolean z) {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.end();
        }
        if (this.mCurWindowVisibility == 0 && (this.mShowHideAnimationEnabled || z)) {
            this.mContainerView.setAlpha(1.0f);
            this.mContainerView.setTransitioning(true);
            android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
            float f = -this.mContainerView.getHeight();
            if (z) {
                this.mContainerView.getLocationInWindow(new int[]{0, 0});
                f -= r10[1];
            }
            android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(this.mContainerView, (android.util.Property<com.android.internal.widget.ActionBarContainer, java.lang.Float>) android.view.View.TRANSLATION_Y, f);
            ofFloat.addUpdateListener(this.mUpdateListener);
            android.animation.AnimatorSet.Builder play = animatorSet.play(ofFloat);
            if (this.mContentAnimations && this.mContentView != null) {
                play.with(android.animation.ObjectAnimator.ofFloat(this.mContentView, android.view.View.TRANSLATION_Y, 0.0f, f));
            }
            if (this.mSplitView != null && this.mSplitView.getVisibility() == 0) {
                this.mSplitView.setAlpha(1.0f);
                play.with(android.animation.ObjectAnimator.ofFloat(this.mSplitView, (android.util.Property<com.android.internal.widget.ActionBarContainer, java.lang.Float>) android.view.View.TRANSLATION_Y, this.mSplitView.getHeight()));
            }
            animatorSet.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(this.mContext, 17563650));
            animatorSet.setDuration(250L);
            animatorSet.addListener(this.mHideListener);
            this.mCurrentShowAnim = animatorSet;
            animatorSet.start();
            return;
        }
        this.mHideListener.onAnimationEnd(null);
    }

    @Override // android.app.ActionBar
    public boolean isShowing() {
        int height = getHeight();
        return this.mNowShowing && (height == 0 || getHideOffset() < height);
    }

    void animateToMode(boolean z) {
        android.animation.Animator animator;
        android.animation.Animator animator2;
        if (z) {
            showForActionMode();
        } else {
            hideForActionMode();
        }
        if (shouldAnimateContextView()) {
            if (z) {
                animator2 = this.mDecorToolbar.setupAnimatorToVisibility(8, FADE_OUT_DURATION_MS);
                animator = this.mContextView.setupAnimatorToVisibility(0, FADE_IN_DURATION_MS);
            } else {
                animator = this.mDecorToolbar.setupAnimatorToVisibility(0, FADE_IN_DURATION_MS);
                animator2 = this.mContextView.setupAnimatorToVisibility(8, FADE_OUT_DURATION_MS);
            }
            android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
            animatorSet.playSequentially(animator2, animator);
            animatorSet.start();
            return;
        }
        if (z) {
            this.mDecorToolbar.setVisibility(8);
            this.mContextView.setVisibility(0);
        } else {
            this.mDecorToolbar.setVisibility(0);
            this.mContextView.setVisibility(8);
        }
    }

    private boolean shouldAnimateContextView() {
        return this.mContainerView.isLaidOut();
    }

    @Override // android.app.ActionBar
    public android.content.Context getThemedContext() {
        if (this.mThemedContext == null) {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            this.mContext.getTheme().resolveAttribute(16843671, typedValue, true);
            int i = typedValue.resourceId;
            if (i != 0 && this.mContext.getThemeResId() != i) {
                this.mThemedContext = new android.view.ContextThemeWrapper(this.mContext, i);
            } else {
                this.mThemedContext = this.mContext;
            }
        }
        return this.mThemedContext;
    }

    @Override // android.app.ActionBar
    public boolean isTitleTruncated() {
        return this.mDecorToolbar != null && this.mDecorToolbar.isTitleTruncated();
    }

    @Override // android.app.ActionBar
    public void setHomeAsUpIndicator(android.graphics.drawable.Drawable drawable) {
        this.mDecorToolbar.setNavigationIcon(drawable);
    }

    @Override // android.app.ActionBar
    public void setHomeAsUpIndicator(int i) {
        this.mDecorToolbar.setNavigationIcon(i);
    }

    @Override // android.app.ActionBar
    public void setHomeActionContentDescription(java.lang.CharSequence charSequence) {
        this.mDecorToolbar.setNavigationContentDescription(charSequence);
    }

    @Override // android.app.ActionBar
    public void setHomeActionContentDescription(int i) {
        this.mDecorToolbar.setNavigationContentDescription(i);
    }

    @Override // com.android.internal.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void onContentScrollStarted() {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
            this.mCurrentShowAnim = null;
        }
    }

    @Override // com.android.internal.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void onContentScrollStopped() {
    }

    @Override // android.app.ActionBar
    public boolean collapseActionView() {
        if (this.mDecorToolbar != null && this.mDecorToolbar.hasExpandedActionView()) {
            this.mDecorToolbar.collapseActionView();
            return true;
        }
        return false;
    }

    public class ActionModeImpl extends android.view.ActionMode implements com.android.internal.view.menu.MenuBuilder.Callback {
        private final android.content.Context mActionModeContext;
        private android.view.ActionMode.Callback mCallback;
        private java.lang.ref.WeakReference<android.view.View> mCustomView;
        private final com.android.internal.view.menu.MenuBuilder mMenu;

        public ActionModeImpl(android.content.Context context, android.view.ActionMode.Callback callback) {
            this.mActionModeContext = context;
            this.mCallback = callback;
            this.mMenu = new com.android.internal.view.menu.MenuBuilder(context).setDefaultShowAsAction(1);
            this.mMenu.setCallback(this);
        }

        @Override // android.view.ActionMode
        public android.view.MenuInflater getMenuInflater() {
            return new android.view.MenuInflater(this.mActionModeContext);
        }

        @Override // android.view.ActionMode
        public android.view.Menu getMenu() {
            return this.mMenu;
        }

        @Override // android.view.ActionMode
        public void finish() {
            if (com.android.internal.app.WindowDecorActionBar.this.mActionMode != this) {
                return;
            }
            if (!com.android.internal.app.WindowDecorActionBar.checkShowingFlags(com.android.internal.app.WindowDecorActionBar.this.mHiddenByApp, com.android.internal.app.WindowDecorActionBar.this.mHiddenBySystem, false)) {
                com.android.internal.app.WindowDecorActionBar.this.mDeferredDestroyActionMode = this;
                com.android.internal.app.WindowDecorActionBar.this.mDeferredModeDestroyCallback = this.mCallback;
            } else {
                this.mCallback.onDestroyActionMode(this);
            }
            this.mCallback = null;
            com.android.internal.app.WindowDecorActionBar.this.animateToMode(false);
            com.android.internal.app.WindowDecorActionBar.this.mContextView.closeMode();
            com.android.internal.app.WindowDecorActionBar.this.mDecorToolbar.getViewGroup().sendAccessibilityEvent(32);
            com.android.internal.app.WindowDecorActionBar.this.mOverlayLayout.setHideOnContentScrollEnabled(com.android.internal.app.WindowDecorActionBar.this.mHideOnContentScroll);
            com.android.internal.app.WindowDecorActionBar.this.mActionMode = null;
        }

        @Override // android.view.ActionMode
        public void invalidate() {
            if (com.android.internal.app.WindowDecorActionBar.this.mActionMode != this) {
                return;
            }
            this.mMenu.stopDispatchingItemsChanged();
            try {
                this.mCallback.onPrepareActionMode(this, this.mMenu);
            } finally {
                this.mMenu.startDispatchingItemsChanged();
            }
        }

        public boolean dispatchOnCreate() {
            this.mMenu.stopDispatchingItemsChanged();
            try {
                return this.mCallback.onCreateActionMode(this, this.mMenu);
            } finally {
                this.mMenu.startDispatchingItemsChanged();
            }
        }

        @Override // android.view.ActionMode
        public void setCustomView(android.view.View view) {
            com.android.internal.app.WindowDecorActionBar.this.mContextView.setCustomView(view);
            this.mCustomView = new java.lang.ref.WeakReference<>(view);
        }

        @Override // android.view.ActionMode
        public void setSubtitle(java.lang.CharSequence charSequence) {
            com.android.internal.app.WindowDecorActionBar.this.mContextView.setSubtitle(charSequence);
        }

        @Override // android.view.ActionMode
        public void setTitle(java.lang.CharSequence charSequence) {
            com.android.internal.app.WindowDecorActionBar.this.mContextView.setTitle(charSequence);
        }

        @Override // android.view.ActionMode
        public void setTitle(int i) {
            setTitle(com.android.internal.app.WindowDecorActionBar.this.mContext.getResources().getString(i));
        }

        @Override // android.view.ActionMode
        public void setSubtitle(int i) {
            setSubtitle(com.android.internal.app.WindowDecorActionBar.this.mContext.getResources().getString(i));
        }

        @Override // android.view.ActionMode
        public java.lang.CharSequence getTitle() {
            return com.android.internal.app.WindowDecorActionBar.this.mContextView.getTitle();
        }

        @Override // android.view.ActionMode
        public java.lang.CharSequence getSubtitle() {
            return com.android.internal.app.WindowDecorActionBar.this.mContextView.getSubtitle();
        }

        @Override // android.view.ActionMode
        public void setTitleOptionalHint(boolean z) {
            super.setTitleOptionalHint(z);
            com.android.internal.app.WindowDecorActionBar.this.mContextView.setTitleOptional(z);
        }

        @Override // android.view.ActionMode
        public boolean isTitleOptional() {
            return com.android.internal.app.WindowDecorActionBar.this.mContextView.isTitleOptional();
        }

        @Override // android.view.ActionMode
        public android.view.View getCustomView() {
            if (this.mCustomView != null) {
                return this.mCustomView.get();
            }
            return null;
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.MenuItem menuItem) {
            if (this.mCallback != null) {
                return this.mCallback.onActionItemClicked(this, menuItem);
            }
            return false;
        }

        public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
        }

        public boolean onSubMenuSelected(com.android.internal.view.menu.SubMenuBuilder subMenuBuilder) {
            if (this.mCallback == null) {
                return false;
            }
            if (!subMenuBuilder.hasVisibleItems()) {
                return true;
            }
            new com.android.internal.view.menu.MenuPopupHelper(com.android.internal.app.WindowDecorActionBar.this.getThemedContext(), subMenuBuilder).show();
            return true;
        }

        public void onCloseSubMenu(com.android.internal.view.menu.SubMenuBuilder subMenuBuilder) {
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(com.android.internal.view.menu.MenuBuilder menuBuilder) {
            if (this.mCallback == null) {
                return;
            }
            invalidate();
            com.android.internal.app.WindowDecorActionBar.this.mContextView.showOverflowMenu();
        }
    }

    public class TabImpl extends android.app.ActionBar.Tab {
        private android.app.ActionBar.TabListener mCallback;
        private java.lang.CharSequence mContentDesc;
        private android.view.View mCustomView;
        private android.graphics.drawable.Drawable mIcon;
        private int mPosition = -1;
        private java.lang.Object mTag;
        private java.lang.CharSequence mText;

        public TabImpl() {
        }

        @Override // android.app.ActionBar.Tab
        public java.lang.Object getTag() {
            return this.mTag;
        }

        @Override // android.app.ActionBar.Tab
        public android.app.ActionBar.Tab setTag(java.lang.Object obj) {
            this.mTag = obj;
            return this;
        }

        public android.app.ActionBar.TabListener getCallback() {
            return this.mCallback;
        }

        @Override // android.app.ActionBar.Tab
        public android.app.ActionBar.Tab setTabListener(android.app.ActionBar.TabListener tabListener) {
            this.mCallback = tabListener;
            return this;
        }

        @Override // android.app.ActionBar.Tab
        public android.view.View getCustomView() {
            return this.mCustomView;
        }

        @Override // android.app.ActionBar.Tab
        public android.app.ActionBar.Tab setCustomView(android.view.View view) {
            this.mCustomView = view;
            if (this.mPosition >= 0) {
                com.android.internal.app.WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        @Override // android.app.ActionBar.Tab
        public android.app.ActionBar.Tab setCustomView(int i) {
            return setCustomView(android.view.LayoutInflater.from(com.android.internal.app.WindowDecorActionBar.this.getThemedContext()).inflate(i, (android.view.ViewGroup) null));
        }

        @Override // android.app.ActionBar.Tab
        public android.graphics.drawable.Drawable getIcon() {
            return this.mIcon;
        }

        @Override // android.app.ActionBar.Tab
        public int getPosition() {
            return this.mPosition;
        }

        public void setPosition(int i) {
            this.mPosition = i;
        }

        @Override // android.app.ActionBar.Tab
        public java.lang.CharSequence getText() {
            return this.mText;
        }

        @Override // android.app.ActionBar.Tab
        public android.app.ActionBar.Tab setIcon(android.graphics.drawable.Drawable drawable) {
            this.mIcon = drawable;
            if (this.mPosition >= 0) {
                com.android.internal.app.WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        @Override // android.app.ActionBar.Tab
        public android.app.ActionBar.Tab setIcon(int i) {
            return setIcon(com.android.internal.app.WindowDecorActionBar.this.mContext.getDrawable(i));
        }

        @Override // android.app.ActionBar.Tab
        public android.app.ActionBar.Tab setText(java.lang.CharSequence charSequence) {
            this.mText = charSequence;
            if (this.mPosition >= 0) {
                com.android.internal.app.WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        @Override // android.app.ActionBar.Tab
        public android.app.ActionBar.Tab setText(int i) {
            return setText(com.android.internal.app.WindowDecorActionBar.this.mContext.getResources().getText(i));
        }

        @Override // android.app.ActionBar.Tab
        public void select() {
            com.android.internal.app.WindowDecorActionBar.this.selectTab(this);
        }

        @Override // android.app.ActionBar.Tab
        public android.app.ActionBar.Tab setContentDescription(int i) {
            return setContentDescription(com.android.internal.app.WindowDecorActionBar.this.mContext.getResources().getText(i));
        }

        @Override // android.app.ActionBar.Tab
        public android.app.ActionBar.Tab setContentDescription(java.lang.CharSequence charSequence) {
            this.mContentDesc = charSequence;
            if (this.mPosition >= 0) {
                com.android.internal.app.WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        @Override // android.app.ActionBar.Tab
        public java.lang.CharSequence getContentDescription() {
            return this.mContentDesc;
        }
    }

    @Override // android.app.ActionBar
    public void setCustomView(android.view.View view) {
        this.mDecorToolbar.setCustomView(view);
    }

    @Override // android.app.ActionBar
    public void setCustomView(android.view.View view, android.app.ActionBar.LayoutParams layoutParams) {
        view.setLayoutParams(layoutParams);
        this.mDecorToolbar.setCustomView(view);
    }

    @Override // android.app.ActionBar
    public void setListNavigationCallbacks(android.widget.SpinnerAdapter spinnerAdapter, android.app.ActionBar.OnNavigationListener onNavigationListener) {
        this.mDecorToolbar.setDropdownParams(spinnerAdapter, new com.android.internal.app.NavItemSelectedListener(onNavigationListener));
    }

    @Override // android.app.ActionBar
    public int getSelectedNavigationIndex() {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                return this.mDecorToolbar.getDropdownSelectedPosition();
            case 2:
                if (this.mSelectedTab != null) {
                    return this.mSelectedTab.getPosition();
                }
                return -1;
            default:
                return -1;
        }
    }

    @Override // android.app.ActionBar
    public int getNavigationItemCount() {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                return this.mDecorToolbar.getDropdownItemCount();
            case 2:
                return this.mTabs.size();
            default:
                return 0;
        }
    }

    @Override // android.app.ActionBar
    public int getTabCount() {
        return this.mTabs.size();
    }

    @Override // android.app.ActionBar
    public void setNavigationMode(int i) {
        int navigationMode = this.mDecorToolbar.getNavigationMode();
        switch (navigationMode) {
            case 2:
                this.mSavedTabPosition = getSelectedNavigationIndex();
                selectTab(null);
                this.mTabScrollView.setVisibility(8);
                break;
        }
        if (navigationMode != i && !this.mHasEmbeddedTabs && this.mOverlayLayout != null) {
            this.mOverlayLayout.requestFitSystemWindows();
        }
        this.mDecorToolbar.setNavigationMode(i);
        boolean z = false;
        switch (i) {
            case 2:
                ensureTabsExist();
                this.mTabScrollView.setVisibility(0);
                if (this.mSavedTabPosition != -1) {
                    setSelectedNavigationItem(this.mSavedTabPosition);
                    this.mSavedTabPosition = -1;
                    break;
                }
                break;
        }
        this.mDecorToolbar.setCollapsible(i == 2 && !this.mHasEmbeddedTabs);
        com.android.internal.widget.ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
        if (i == 2 && !this.mHasEmbeddedTabs) {
            z = true;
        }
        actionBarOverlayLayout.setHasNonEmbeddedTabs(z);
    }

    @Override // android.app.ActionBar
    public android.app.ActionBar.Tab getTabAt(int i) {
        return this.mTabs.get(i);
    }

    @Override // android.app.ActionBar
    public void setIcon(int i) {
        this.mDecorToolbar.setIcon(i);
    }

    @Override // android.app.ActionBar
    public void setIcon(android.graphics.drawable.Drawable drawable) {
        this.mDecorToolbar.setIcon(drawable);
    }

    public boolean hasIcon() {
        return this.mDecorToolbar.hasIcon();
    }

    @Override // android.app.ActionBar
    public void setLogo(int i) {
        this.mDecorToolbar.setLogo(i);
    }

    @Override // android.app.ActionBar
    public void setLogo(android.graphics.drawable.Drawable drawable) {
        this.mDecorToolbar.setLogo(drawable);
    }

    public boolean hasLogo() {
        return this.mDecorToolbar.hasLogo();
    }

    @Override // android.app.ActionBar
    public void setDefaultDisplayHomeAsUpEnabled(boolean z) {
        if (!this.mDisplayHomeAsUpSet) {
            setDisplayHomeAsUpEnabled(z);
        }
    }
}
