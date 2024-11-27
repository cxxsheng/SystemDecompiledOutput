package com.android.internal.widget;

/* loaded from: classes5.dex */
public class ActionBarView extends com.android.internal.widget.AbsActionBarView implements com.android.internal.widget.DecorToolbar {
    private static final int DEFAULT_CUSTOM_GRAVITY = 8388627;
    public static final int DISPLAY_DEFAULT = 0;
    private static final int DISPLAY_RELAYOUT_MASK = 63;
    private static final java.lang.String TAG = "ActionBarView";
    private com.android.internal.widget.ActionBarContextView mContextView;
    private android.view.View mCustomNavView;
    private int mDefaultUpDescription;
    private int mDisplayOptions;
    android.view.View mExpandedActionView;
    private final android.view.View.OnClickListener mExpandedActionViewUpListener;
    private com.android.internal.widget.ActionBarView.HomeView mExpandedHomeLayout;
    private com.android.internal.widget.ActionBarView.ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private java.lang.CharSequence mHomeDescription;
    private int mHomeDescriptionRes;
    private com.android.internal.widget.ActionBarView.HomeView mHomeLayout;
    private android.graphics.drawable.Drawable mIcon;
    private boolean mIncludeTabs;
    private final int mIndeterminateProgressStyle;
    private android.widget.ProgressBar mIndeterminateProgressView;
    private boolean mIsCollapsible;
    private int mItemPadding;
    private android.widget.LinearLayout mListNavLayout;
    private android.graphics.drawable.Drawable mLogo;
    private com.android.internal.view.menu.ActionMenuItem mLogoNavItem;
    private boolean mMenuPrepared;
    private android.widget.AdapterView.OnItemSelectedListener mNavItemSelectedListener;
    private int mNavigationMode;
    private com.android.internal.view.menu.MenuBuilder mOptionsMenu;
    private int mProgressBarPadding;
    private final int mProgressStyle;
    private android.widget.ProgressBar mProgressView;
    private android.widget.Spinner mSpinner;
    private android.widget.SpinnerAdapter mSpinnerAdapter;
    private java.lang.CharSequence mSubtitle;
    private final int mSubtitleStyleRes;
    private android.widget.TextView mSubtitleView;
    private com.android.internal.widget.ScrollingTabContainerView mTabScrollView;
    private java.lang.Runnable mTabSelector;
    private java.lang.CharSequence mTitle;
    private android.widget.LinearLayout mTitleLayout;
    private final int mTitleStyleRes;
    private android.widget.TextView mTitleView;
    private final android.view.View.OnClickListener mUpClickListener;
    private android.view.ViewGroup mUpGoerFive;
    private boolean mUserTitle;
    private boolean mWasHomeEnabled;
    android.view.Window.Callback mWindowCallback;

    public ActionBarView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDisplayOptions = -1;
        this.mDefaultUpDescription = com.android.internal.R.string.action_bar_up_description;
        this.mExpandedActionViewUpListener = new android.view.View.OnClickListener() { // from class: com.android.internal.widget.ActionBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                com.android.internal.view.menu.MenuItemImpl menuItemImpl = com.android.internal.widget.ActionBarView.this.mExpandedMenuPresenter.mCurrentExpandedItem;
                if (menuItemImpl != null) {
                    menuItemImpl.collapseActionView();
                }
            }
        };
        this.mUpClickListener = new android.view.View.OnClickListener() { // from class: com.android.internal.widget.ActionBarView.2
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                if (com.android.internal.widget.ActionBarView.this.mMenuPrepared) {
                    com.android.internal.widget.ActionBarView.this.mWindowCallback.onMenuItemSelected(0, com.android.internal.widget.ActionBarView.this.mLogoNavItem);
                }
            }
        };
        setBackgroundResource(0);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ActionBar, 16843470, 0);
        this.mNavigationMode = obtainStyledAttributes.getInt(7, 0);
        this.mTitle = obtainStyledAttributes.getText(5);
        this.mSubtitle = obtainStyledAttributes.getText(9);
        this.mLogo = obtainStyledAttributes.getDrawable(6);
        this.mIcon = obtainStyledAttributes.getDrawable(0);
        android.view.LayoutInflater from = android.view.LayoutInflater.from(context);
        int resourceId = obtainStyledAttributes.getResourceId(16, com.android.internal.R.layout.action_bar_home);
        this.mUpGoerFive = (android.view.ViewGroup) from.inflate(com.android.internal.R.layout.action_bar_up_container, (android.view.ViewGroup) this, false);
        this.mHomeLayout = (com.android.internal.widget.ActionBarView.HomeView) from.inflate(resourceId, this.mUpGoerFive, false);
        this.mExpandedHomeLayout = (com.android.internal.widget.ActionBarView.HomeView) from.inflate(resourceId, this.mUpGoerFive, false);
        this.mExpandedHomeLayout.setShowUp(true);
        this.mExpandedHomeLayout.setOnClickListener(this.mExpandedActionViewUpListener);
        this.mExpandedHomeLayout.setContentDescription(getResources().getText(this.mDefaultUpDescription));
        android.graphics.drawable.Drawable background = this.mUpGoerFive.getBackground();
        if (background != null) {
            this.mExpandedHomeLayout.setBackground(background.getConstantState().newDrawable());
        }
        this.mExpandedHomeLayout.setEnabled(true);
        this.mExpandedHomeLayout.setFocusable(true);
        this.mTitleStyleRes = obtainStyledAttributes.getResourceId(11, 0);
        this.mSubtitleStyleRes = obtainStyledAttributes.getResourceId(12, 0);
        this.mProgressStyle = obtainStyledAttributes.getResourceId(1, 0);
        this.mIndeterminateProgressStyle = obtainStyledAttributes.getResourceId(14, 0);
        this.mProgressBarPadding = obtainStyledAttributes.getDimensionPixelOffset(15, 0);
        this.mItemPadding = obtainStyledAttributes.getDimensionPixelOffset(17, 0);
        setDisplayOptions(obtainStyledAttributes.getInt(8, 0));
        int resourceId2 = obtainStyledAttributes.getResourceId(10, 0);
        if (resourceId2 != 0) {
            this.mCustomNavView = from.inflate(resourceId2, (android.view.ViewGroup) this, false);
            this.mNavigationMode = 0;
            setDisplayOptions(this.mDisplayOptions | 16);
        }
        this.mContentHeight = obtainStyledAttributes.getLayoutDimension(4, 0);
        obtainStyledAttributes.recycle();
        this.mLogoNavItem = new com.android.internal.view.menu.ActionMenuItem(context, 0, 16908332, 0, 0, this.mTitle);
        this.mUpGoerFive.setOnClickListener(this.mUpClickListener);
        this.mUpGoerFive.setClickable(true);
        this.mUpGoerFive.setFocusable(true);
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    @Override // com.android.internal.widget.AbsActionBarView, android.view.View
    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mTitleView = null;
        this.mSubtitleView = null;
        if (this.mTitleLayout != null && this.mTitleLayout.getParent() == this.mUpGoerFive) {
            this.mUpGoerFive.removeView(this.mTitleLayout);
        }
        this.mTitleLayout = null;
        if ((this.mDisplayOptions & 8) != 0) {
            initTitle();
        }
        if (this.mHomeDescriptionRes != 0) {
            setNavigationContentDescription(this.mHomeDescriptionRes);
        }
        if (this.mTabScrollView != null && this.mIncludeTabs) {
            android.view.ViewGroup.LayoutParams layoutParams = this.mTabScrollView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = -2;
                layoutParams.height = -1;
            }
            this.mTabScrollView.setAllowCollapse(true);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setWindowCallback(android.view.Window.Callback callback) {
        this.mWindowCallback = callback;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mTabSelector);
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.hideOverflowMenu();
            this.mActionMenuPresenter.hideSubMenus();
        }
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void initProgress() {
        this.mProgressView = new android.widget.ProgressBar(this.mContext, null, 0, this.mProgressStyle);
        this.mProgressView.setId(com.android.internal.R.id.progress_horizontal);
        this.mProgressView.setMax(10000);
        this.mProgressView.setVisibility(8);
        addView(this.mProgressView);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void initIndeterminateProgress() {
        this.mIndeterminateProgressView = new android.widget.ProgressBar(this.mContext, null, 0, this.mIndeterminateProgressStyle);
        this.mIndeterminateProgressView.setId(com.android.internal.R.id.progress_circular);
        this.mIndeterminateProgressView.setVisibility(8);
        addView(this.mIndeterminateProgressView);
    }

    @Override // com.android.internal.widget.AbsActionBarView
    public void setSplitToolbar(boolean z) {
        if (this.mSplitActionBar != z) {
            if (this.mMenuView != null) {
                android.view.ViewGroup viewGroup = (android.view.ViewGroup) this.mMenuView.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(this.mMenuView);
                }
                if (z) {
                    if (this.mSplitView != null) {
                        this.mSplitView.addView(this.mMenuView);
                    }
                    this.mMenuView.getLayoutParams().width = -1;
                } else {
                    addView(this.mMenuView);
                    this.mMenuView.getLayoutParams().width = -2;
                }
                this.mMenuView.requestLayout();
            }
            if (this.mSplitView != null) {
                this.mSplitView.setVisibility(z ? 0 : 8);
            }
            if (this.mActionMenuPresenter != null) {
                if (!z) {
                    this.mActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(com.android.internal.R.bool.action_bar_expanded_action_views_exclusive));
                } else {
                    this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
                    this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
                    this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
                }
            }
            super.setSplitToolbar(z);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean isSplit() {
        return this.mSplitActionBar;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean canSplit() {
        return true;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasEmbeddedTabs() {
        return this.mIncludeTabs;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setEmbeddedTabView(com.android.internal.widget.ScrollingTabContainerView scrollingTabContainerView) {
        if (this.mTabScrollView != null) {
            removeView(this.mTabScrollView);
        }
        this.mTabScrollView = scrollingTabContainerView;
        this.mIncludeTabs = scrollingTabContainerView != null;
        if (this.mIncludeTabs && this.mNavigationMode == 2) {
            addView(this.mTabScrollView);
            android.view.ViewGroup.LayoutParams layoutParams = this.mTabScrollView.getLayoutParams();
            layoutParams.width = -2;
            layoutParams.height = -1;
            scrollingTabContainerView.setAllowCollapse(true);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenuPrepared() {
        this.mMenuPrepared = true;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenu(android.view.Menu menu, com.android.internal.view.menu.MenuPresenter.Callback callback) {
        android.widget.ActionMenuView actionMenuView;
        android.view.ViewGroup viewGroup;
        if (menu == this.mOptionsMenu) {
            return;
        }
        if (this.mOptionsMenu != null) {
            this.mOptionsMenu.removeMenuPresenter(this.mActionMenuPresenter);
            this.mOptionsMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
        }
        com.android.internal.view.menu.MenuBuilder menuBuilder = (com.android.internal.view.menu.MenuBuilder) menu;
        this.mOptionsMenu = menuBuilder;
        if (this.mMenuView != null && (viewGroup = (android.view.ViewGroup) this.mMenuView.getParent()) != null) {
            viewGroup.removeView(this.mMenuView);
        }
        if (this.mActionMenuPresenter == null) {
            this.mActionMenuPresenter = new android.widget.ActionMenuPresenter(this.mContext);
            this.mActionMenuPresenter.setCallback(callback);
            this.mActionMenuPresenter.setId(com.android.internal.R.id.action_menu_presenter);
            this.mExpandedMenuPresenter = new com.android.internal.widget.ActionBarView.ExpandedActionViewMenuPresenter();
        }
        android.view.ViewGroup.LayoutParams layoutParams = new android.view.ViewGroup.LayoutParams(-2, -1);
        if (!this.mSplitActionBar) {
            this.mActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(com.android.internal.R.bool.action_bar_expanded_action_views_exclusive));
            configPresenters(menuBuilder);
            actionMenuView = (android.widget.ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
            android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) actionMenuView.getParent();
            if (viewGroup2 != null && viewGroup2 != this) {
                viewGroup2.removeView(actionMenuView);
            }
            addView(actionMenuView, layoutParams);
        } else {
            this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
            this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
            this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
            layoutParams.width = -1;
            layoutParams.height = -2;
            configPresenters(menuBuilder);
            actionMenuView = (android.widget.ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
            if (this.mSplitView != null) {
                android.view.ViewGroup viewGroup3 = (android.view.ViewGroup) actionMenuView.getParent();
                if (viewGroup3 != null && viewGroup3 != this.mSplitView) {
                    viewGroup3.removeView(actionMenuView);
                }
                actionMenuView.setVisibility(getAnimatedVisibility());
                this.mSplitView.addView(actionMenuView, layoutParams);
            } else {
                actionMenuView.setLayoutParams(layoutParams);
            }
        }
        this.mMenuView = actionMenuView;
    }

    private void configPresenters(com.android.internal.view.menu.MenuBuilder menuBuilder) {
        if (menuBuilder != null) {
            menuBuilder.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        } else {
            this.mActionMenuPresenter.initForMenu(this.mPopupContext, null);
            this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
            this.mActionMenuPresenter.updateMenuView(true);
            this.mExpandedMenuPresenter.updateMenuView(true);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasExpandedActionView() {
        return (this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null) ? false : true;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void collapseActionView() {
        com.android.internal.view.menu.MenuItemImpl menuItemImpl = this.mExpandedMenuPresenter == null ? null : this.mExpandedMenuPresenter.mCurrentExpandedItem;
        if (menuItemImpl != null) {
            menuItemImpl.collapseActionView();
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setCustomView(android.view.View view) {
        boolean z = (this.mDisplayOptions & 16) != 0;
        if (this.mCustomNavView != null && z) {
            removeView(this.mCustomNavView);
        }
        this.mCustomNavView = view;
        if (this.mCustomNavView != null && z) {
            addView(this.mCustomNavView);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public java.lang.CharSequence getTitle() {
        return this.mTitle;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setTitle(java.lang.CharSequence charSequence) {
        this.mUserTitle = true;
        setTitleImpl(charSequence);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setWindowTitle(java.lang.CharSequence charSequence) {
        if (!this.mUserTitle) {
            setTitleImpl(charSequence);
        }
    }

    private void setTitleImpl(java.lang.CharSequence charSequence) {
        this.mTitle = charSequence;
        if (this.mTitleView != null) {
            this.mTitleView.lambda$setTextAsync$0(charSequence);
            this.mTitleLayout.setVisibility(this.mExpandedActionView == null && (this.mDisplayOptions & 8) != 0 && (!android.text.TextUtils.isEmpty(this.mTitle) || !android.text.TextUtils.isEmpty(this.mSubtitle)) ? 0 : 8);
        }
        if (this.mLogoNavItem != null) {
            this.mLogoNavItem.setTitle(charSequence);
        }
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public java.lang.CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setSubtitle(java.lang.CharSequence charSequence) {
        this.mSubtitle = charSequence;
        if (this.mSubtitleView != null) {
            this.mSubtitleView.lambda$setTextAsync$0(charSequence);
            this.mSubtitleView.setVisibility(charSequence != null ? 0 : 8);
            this.mTitleLayout.setVisibility(this.mExpandedActionView == null && (this.mDisplayOptions & 8) != 0 && (!android.text.TextUtils.isEmpty(this.mTitle) || !android.text.TextUtils.isEmpty(this.mSubtitle)) ? 0 : 8);
        }
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setHomeButtonEnabled(boolean z) {
        setHomeButtonEnabled(z, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHomeButtonEnabled(boolean z, boolean z2) {
        if (z2) {
            this.mWasHomeEnabled = z;
        }
        if (this.mExpandedActionView != null) {
            return;
        }
        this.mUpGoerFive.setEnabled(z);
        this.mUpGoerFive.setFocusable(z);
        updateHomeAccessibility(z);
    }

    private void updateHomeAccessibility(boolean z) {
        if (!z) {
            this.mUpGoerFive.setContentDescription(null);
            this.mUpGoerFive.setImportantForAccessibility(2);
        } else {
            this.mUpGoerFive.setImportantForAccessibility(0);
            this.mUpGoerFive.setContentDescription(buildHomeContentDescription());
        }
    }

    private java.lang.CharSequence buildHomeContentDescription() {
        java.lang.CharSequence text;
        if (this.mHomeDescription != null) {
            text = this.mHomeDescription;
        } else if ((this.mDisplayOptions & 4) != 0) {
            text = this.mContext.getResources().getText(this.mDefaultUpDescription);
        } else {
            text = this.mContext.getResources().getText(com.android.internal.R.string.action_bar_home_description);
        }
        java.lang.CharSequence title = getTitle();
        java.lang.CharSequence subtitle = getSubtitle();
        if (!android.text.TextUtils.isEmpty(title)) {
            if (!android.text.TextUtils.isEmpty(subtitle)) {
                return getResources().getString(com.android.internal.R.string.action_bar_home_subtitle_description_format, title, subtitle, text);
            }
            return getResources().getString(com.android.internal.R.string.action_bar_home_description_format, title, text);
        }
        return text;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDisplayOptions(int i) {
        int i2 = this.mDisplayOptions != -1 ? i ^ this.mDisplayOptions : -1;
        this.mDisplayOptions = i;
        if ((i2 & 63) != 0) {
            if ((i2 & 4) != 0) {
                boolean z = (i & 4) != 0;
                this.mHomeLayout.setShowUp(z);
                if (z) {
                    setHomeButtonEnabled(true);
                }
            }
            if ((i2 & 1) != 0) {
                this.mHomeLayout.setIcon(this.mLogo != null && (i & 1) != 0 ? this.mLogo : this.mIcon);
            }
            if ((i2 & 8) != 0) {
                if ((i & 8) != 0) {
                    initTitle();
                } else {
                    this.mUpGoerFive.removeView(this.mTitleLayout);
                }
            }
            boolean z2 = (i & 2) != 0;
            boolean z3 = !z2 && ((this.mDisplayOptions & 4) != 0);
            this.mHomeLayout.setShowIcon(z2);
            this.mHomeLayout.setVisibility(((z2 || z3) && this.mExpandedActionView == null) ? 0 : 8);
            if ((i2 & 16) != 0 && this.mCustomNavView != null) {
                if ((i & 16) != 0) {
                    addView(this.mCustomNavView);
                } else {
                    removeView(this.mCustomNavView);
                }
            }
            if (this.mTitleLayout != null && (i2 & 32) != 0) {
                if ((i & 32) != 0) {
                    this.mTitleView.setSingleLine(false);
                    this.mTitleView.setMaxLines(2);
                } else {
                    this.mTitleView.setMaxLines(1);
                    this.mTitleView.setSingleLine(true);
                }
            }
            requestLayout();
        } else {
            invalidate();
        }
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setIcon(android.graphics.drawable.Drawable drawable) {
        this.mIcon = drawable;
        if (drawable != null && ((this.mDisplayOptions & 1) == 0 || this.mLogo == null)) {
            this.mHomeLayout.setIcon(drawable);
        }
        if (this.mExpandedActionView != null) {
            this.mExpandedHomeLayout.setIcon(this.mIcon.getConstantState().newDrawable(getResources()));
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setIcon(int i) {
        setIcon(i != 0 ? this.mContext.getDrawable(i) : null);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasIcon() {
        return this.mIcon != null;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setLogo(android.graphics.drawable.Drawable drawable) {
        this.mLogo = drawable;
        if (drawable != null && (this.mDisplayOptions & 1) != 0) {
            this.mHomeLayout.setIcon(drawable);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setLogo(int i) {
        setLogo(i != 0 ? this.mContext.getDrawable(i) : null);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasLogo() {
        return this.mLogo != null;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationMode(int i) {
        int i2 = this.mNavigationMode;
        if (i != i2) {
            switch (i2) {
                case 1:
                    if (this.mListNavLayout != null) {
                        removeView(this.mListNavLayout);
                        break;
                    }
                    break;
                case 2:
                    if (this.mTabScrollView != null && this.mIncludeTabs) {
                        removeView(this.mTabScrollView);
                        break;
                    }
                    break;
            }
            switch (i) {
                case 1:
                    if (this.mSpinner == null) {
                        this.mSpinner = new android.widget.Spinner(this.mContext, null, 16843479);
                        this.mSpinner.setId(com.android.internal.R.id.action_bar_spinner);
                        this.mListNavLayout = new android.widget.LinearLayout(this.mContext, null, 16843508);
                        android.widget.LinearLayout.LayoutParams layoutParams = new android.widget.LinearLayout.LayoutParams(-2, -1);
                        layoutParams.gravity = 17;
                        this.mListNavLayout.addView(this.mSpinner, layoutParams);
                    }
                    if (this.mSpinner.getAdapter() != this.mSpinnerAdapter) {
                        this.mSpinner.setAdapter(this.mSpinnerAdapter);
                    }
                    this.mSpinner.setOnItemSelectedListener(this.mNavItemSelectedListener);
                    addView(this.mListNavLayout);
                    break;
                case 2:
                    if (this.mTabScrollView != null && this.mIncludeTabs) {
                        addView(this.mTabScrollView);
                        break;
                    }
                    break;
            }
            this.mNavigationMode = i;
            requestLayout();
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDropdownParams(android.widget.SpinnerAdapter spinnerAdapter, android.widget.AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mSpinnerAdapter = spinnerAdapter;
        this.mNavItemSelectedListener = onItemSelectedListener;
        if (this.mSpinner != null) {
            this.mSpinner.setAdapter(spinnerAdapter);
            this.mSpinner.setOnItemSelectedListener(onItemSelectedListener);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDropdownItemCount() {
        if (this.mSpinnerAdapter != null) {
            return this.mSpinnerAdapter.getCount();
        }
        return 0;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDropdownSelectedPosition(int i) {
        this.mSpinner.setSelection(i);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDropdownSelectedPosition() {
        return this.mSpinner.getSelectedItemPosition();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public android.view.View getCustomView() {
        return this.mCustomNavView;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDisplayOptions() {
        return this.mDisplayOptions;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public android.view.ViewGroup getViewGroup() {
        return this;
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new android.app.ActionBar.LayoutParams(DEFAULT_CUSTOM_GRAVITY);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        android.view.ViewParent parent;
        super.onFinishInflate();
        this.mUpGoerFive.addView(this.mHomeLayout, 0);
        addView(this.mUpGoerFive);
        if (this.mCustomNavView != null && (this.mDisplayOptions & 16) != 0 && (parent = this.mCustomNavView.getParent()) != this) {
            if (parent instanceof android.view.ViewGroup) {
                ((android.view.ViewGroup) parent).removeView(this.mCustomNavView);
            }
            addView(this.mCustomNavView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTitle() {
        if (this.mTitleLayout == null) {
            this.mTitleLayout = (android.widget.LinearLayout) android.view.LayoutInflater.from(getContext()).inflate(com.android.internal.R.layout.action_bar_title_item, (android.view.ViewGroup) this, false);
            this.mTitleView = (android.widget.TextView) this.mTitleLayout.findViewById(com.android.internal.R.id.action_bar_title);
            this.mSubtitleView = (android.widget.TextView) this.mTitleLayout.findViewById(com.android.internal.R.id.action_bar_subtitle);
            if (this.mTitleStyleRes != 0) {
                this.mTitleView.setTextAppearance(this.mTitleStyleRes);
            }
            if (this.mTitle != null) {
                this.mTitleView.lambda$setTextAsync$0(this.mTitle);
            }
            if (this.mSubtitleStyleRes != 0) {
                this.mSubtitleView.setTextAppearance(this.mSubtitleStyleRes);
            }
            if (this.mSubtitle != null) {
                this.mSubtitleView.lambda$setTextAsync$0(this.mSubtitle);
                this.mSubtitleView.setVisibility(0);
            }
        }
        this.mUpGoerFive.addView(this.mTitleLayout);
        if (this.mExpandedActionView != null || (android.text.TextUtils.isEmpty(this.mTitle) && android.text.TextUtils.isEmpty(this.mSubtitle))) {
            this.mTitleLayout.setVisibility(8);
        } else {
            this.mTitleLayout.setVisibility(0);
        }
    }

    public void setContextView(com.android.internal.widget.ActionBarContextView actionBarContextView) {
        this.mContextView = actionBarContextView;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setCollapsible(boolean z) {
        this.mIsCollapsible = z;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean isTitleTruncated() {
        android.text.Layout layout;
        if (this.mTitleView == null || (layout = this.mTitleView.getLayout()) == null) {
            return false;
        }
        int lineCount = layout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            if (layout.getEllipsisCount(i) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int makeMeasureSpec;
        int i3;
        int i4;
        android.view.View view;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int childCount = getChildCount();
        if (this.mIsCollapsible) {
            int i11 = 0;
            for (int i12 = 0; i12 < childCount; i12++) {
                android.view.View childAt = getChildAt(i12);
                if (childAt.getVisibility() != 8 && ((childAt != this.mMenuView || this.mMenuView.getChildCount() != 0) && childAt != this.mUpGoerFive)) {
                    i11++;
                }
            }
            int childCount2 = this.mUpGoerFive.getChildCount();
            for (int i13 = 0; i13 < childCount2; i13++) {
                if (this.mUpGoerFive.getChildAt(i13).getVisibility() != 8) {
                    i11++;
                }
            }
            if (i11 == 0) {
                setMeasuredDimension(0, 0);
                return;
            }
        }
        if (android.view.View.MeasureSpec.getMode(i) != 1073741824) {
            throw new java.lang.IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_width=\"match_parent\" (or fill_parent)");
        }
        if (android.view.View.MeasureSpec.getMode(i2) != Integer.MIN_VALUE) {
            throw new java.lang.IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_height=\"wrap_content\"");
        }
        int size = android.view.View.MeasureSpec.getSize(i);
        int size2 = this.mContentHeight >= 0 ? this.mContentHeight : android.view.View.MeasureSpec.getSize(i2);
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int i14 = size2 - paddingTop;
        int makeMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec(i14, Integer.MIN_VALUE);
        int makeMeasureSpec3 = android.view.View.MeasureSpec.makeMeasureSpec(i14, 1073741824);
        int i15 = (size - paddingLeft) - paddingRight;
        int i16 = i15 / 2;
        boolean z = (this.mTitleLayout == null || this.mTitleLayout.getVisibility() == 8 || (this.mDisplayOptions & 8) == 0) ? false : true;
        com.android.internal.widget.ActionBarView.HomeView homeView = this.mExpandedActionView != null ? this.mExpandedHomeLayout : this.mHomeLayout;
        android.view.ViewGroup.LayoutParams layoutParams = homeView.getLayoutParams();
        if (layoutParams.width >= 0) {
            makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824);
        } else {
            makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(i15, Integer.MIN_VALUE);
        }
        homeView.measure(makeMeasureSpec, makeMeasureSpec3);
        if ((homeView.getVisibility() == 8 || homeView.getParent() != this.mUpGoerFive) && !z) {
            i3 = i16;
            i4 = 0;
        } else {
            i4 = homeView.getMeasuredWidth();
            int startOffset = homeView.getStartOffset() + i4;
            i15 = java.lang.Math.max(0, i15 - startOffset);
            i3 = java.lang.Math.max(0, i15 - startOffset);
        }
        if (this.mMenuView != null && this.mMenuView.getParent() == this) {
            i15 = measureChildView(this.mMenuView, i15, makeMeasureSpec3, 0);
            i16 = java.lang.Math.max(0, i16 - this.mMenuView.getMeasuredWidth());
        }
        if (this.mIndeterminateProgressView != null && this.mIndeterminateProgressView.getVisibility() != 8) {
            i15 = measureChildView(this.mIndeterminateProgressView, i15, makeMeasureSpec2, 0);
            i16 = java.lang.Math.max(0, i16 - this.mIndeterminateProgressView.getMeasuredWidth());
        }
        if (this.mExpandedActionView == null) {
            switch (this.mNavigationMode) {
                case 1:
                    if (this.mListNavLayout != null) {
                        int i17 = this.mItemPadding;
                        if (z) {
                            i17 *= 2;
                        }
                        int max = java.lang.Math.max(0, i15 - i17);
                        int max2 = java.lang.Math.max(0, i3 - i17);
                        this.mListNavLayout.measure(android.view.View.MeasureSpec.makeMeasureSpec(max, Integer.MIN_VALUE), android.view.View.MeasureSpec.makeMeasureSpec(i14, 1073741824));
                        int measuredWidth = this.mListNavLayout.getMeasuredWidth();
                        i15 = java.lang.Math.max(0, max - measuredWidth);
                        i3 = java.lang.Math.max(0, max2 - measuredWidth);
                        break;
                    }
                    break;
                case 2:
                    if (this.mTabScrollView != null) {
                        int i18 = this.mItemPadding;
                        if (z) {
                            i18 *= 2;
                        }
                        int max3 = java.lang.Math.max(0, i15 - i18);
                        int max4 = java.lang.Math.max(0, i3 - i18);
                        this.mTabScrollView.measure(android.view.View.MeasureSpec.makeMeasureSpec(max3, Integer.MIN_VALUE), android.view.View.MeasureSpec.makeMeasureSpec(i14, 1073741824));
                        int measuredWidth2 = this.mTabScrollView.getMeasuredWidth();
                        i15 = java.lang.Math.max(0, max3 - measuredWidth2);
                        i3 = java.lang.Math.max(0, max4 - measuredWidth2);
                        break;
                    }
                    break;
            }
        }
        if (this.mExpandedActionView != null) {
            view = this.mExpandedActionView;
        } else if ((this.mDisplayOptions & 16) != 0 && this.mCustomNavView != null) {
            view = this.mCustomNavView;
        } else {
            view = null;
        }
        if (view != null) {
            android.view.ViewGroup.LayoutParams generateLayoutParams = generateLayoutParams(view.getLayoutParams());
            android.app.ActionBar.LayoutParams layoutParams2 = generateLayoutParams instanceof android.app.ActionBar.LayoutParams ? (android.app.ActionBar.LayoutParams) generateLayoutParams : null;
            if (layoutParams2 == null) {
                i8 = 0;
                i9 = 0;
            } else {
                int i19 = layoutParams2.leftMargin + layoutParams2.rightMargin;
                i8 = layoutParams2.bottomMargin + layoutParams2.topMargin;
                i9 = i19;
            }
            i6 = size2;
            if (this.mContentHeight <= 0) {
                i10 = Integer.MIN_VALUE;
            } else {
                i10 = generateLayoutParams.height != -2 ? 1073741824 : Integer.MIN_VALUE;
            }
            if (generateLayoutParams.height >= 0) {
                i14 = java.lang.Math.min(generateLayoutParams.height, i14);
            }
            int max5 = java.lang.Math.max(0, i14 - i8);
            int i20 = generateLayoutParams.width != -2 ? 1073741824 : Integer.MIN_VALUE;
            i5 = size;
            int max6 = java.lang.Math.max(0, (generateLayoutParams.width >= 0 ? java.lang.Math.min(generateLayoutParams.width, i15) : i15) - i9);
            if (((layoutParams2 != null ? layoutParams2.gravity : DEFAULT_CUSTOM_GRAVITY) & 7) == 1 && generateLayoutParams.width == -1) {
                max6 = java.lang.Math.min(i3, i16) * 2;
            }
            view.measure(android.view.View.MeasureSpec.makeMeasureSpec(max6, i20), android.view.View.MeasureSpec.makeMeasureSpec(max5, i10));
            i15 -= i9 + view.getMeasuredWidth();
        } else {
            i5 = size;
            i6 = size2;
        }
        int i21 = 0;
        measureChildView(this.mUpGoerFive, i15 + i4, android.view.View.MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824), 0);
        if (this.mTitleLayout != null) {
            java.lang.Math.max(0, i3 - this.mTitleLayout.getMeasuredWidth());
        }
        if (this.mContentHeight <= 0) {
            for (int i22 = 0; i22 < childCount; i22++) {
                int measuredHeight = getChildAt(i22).getMeasuredHeight() + paddingTop;
                if (measuredHeight > i21) {
                    i21 = measuredHeight;
                }
            }
            i7 = i5;
            setMeasuredDimension(i7, i21);
        } else {
            i7 = i5;
            setMeasuredDimension(i7, i6);
        }
        if (this.mContextView != null) {
            this.mContextView.setContentHeight(getMeasuredHeight());
        }
        if (this.mProgressView != null && this.mProgressView.getVisibility() != 8) {
            this.mProgressView.measure(android.view.View.MeasureSpec.makeMeasureSpec(i7 - (this.mProgressBarPadding * 2), 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), Integer.MIN_VALUE));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:100:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0215  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        android.view.View view;
        int i7;
        int i8;
        int i9;
        int paddingBottom;
        int i10;
        int paddingTop = ((i4 - i2) - getPaddingTop()) - getPaddingBottom();
        if (paddingTop <= 0) {
            return;
        }
        boolean isLayoutRtl = isLayoutRtl();
        int i11 = isLayoutRtl ? 1 : -1;
        int paddingLeft = isLayoutRtl ? getPaddingLeft() : (i3 - i) - getPaddingRight();
        int paddingRight = isLayoutRtl ? (i3 - i) - getPaddingRight() : getPaddingLeft();
        int paddingTop2 = getPaddingTop();
        com.android.internal.widget.ActionBarView.HomeView homeView = this.mExpandedActionView != null ? this.mExpandedHomeLayout : this.mHomeLayout;
        boolean z2 = (this.mTitleLayout == null || this.mTitleLayout.getVisibility() == 8 || (this.mDisplayOptions & 8) == 0) ? false : true;
        if (homeView.getParent() == this.mUpGoerFive) {
            if (homeView.getVisibility() != 8) {
                i5 = homeView.getStartOffset();
            } else if (z2) {
                i5 = homeView.getUpWidth();
            }
            int next = next(paddingRight + positionChild(this.mUpGoerFive, next(paddingRight, i5, isLayoutRtl), paddingTop2, paddingTop, isLayoutRtl), i5, isLayoutRtl);
            if (this.mExpandedActionView == null) {
                switch (this.mNavigationMode) {
                    case 1:
                        if (this.mListNavLayout != null) {
                            if (!z2) {
                                i10 = next;
                            } else {
                                i10 = next(next, this.mItemPadding, isLayoutRtl);
                            }
                            i6 = next(i10 + positionChild(this.mListNavLayout, i10, paddingTop2, paddingTop, isLayoutRtl), this.mItemPadding, isLayoutRtl);
                            break;
                        }
                        break;
                    case 2:
                        if (this.mTabScrollView != null) {
                            if (z2) {
                                next = next(next, this.mItemPadding, isLayoutRtl);
                            }
                            int i12 = next;
                            i6 = next(i12 + positionChild(this.mTabScrollView, i12, paddingTop2, paddingTop, isLayoutRtl), this.mItemPadding, isLayoutRtl);
                            break;
                        }
                        break;
                }
                if (this.mMenuView != null && this.mMenuView.getParent() == this) {
                    positionChild(this.mMenuView, paddingLeft, paddingTop2, paddingTop, !isLayoutRtl);
                    paddingLeft += this.mMenuView.getMeasuredWidth() * i11;
                }
                if (this.mIndeterminateProgressView != null && this.mIndeterminateProgressView.getVisibility() != 8) {
                    positionChild(this.mIndeterminateProgressView, paddingLeft, paddingTop2, paddingTop, !isLayoutRtl);
                    paddingLeft += this.mIndeterminateProgressView.getMeasuredWidth() * i11;
                }
                if (this.mExpandedActionView != null) {
                    view = this.mExpandedActionView;
                } else if ((this.mDisplayOptions & 16) != 0 && this.mCustomNavView != null) {
                    view = this.mCustomNavView;
                } else {
                    view = null;
                }
                if (view != null) {
                    int layoutDirection = getLayoutDirection();
                    android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    android.app.ActionBar.LayoutParams layoutParams2 = layoutParams instanceof android.app.ActionBar.LayoutParams ? (android.app.ActionBar.LayoutParams) layoutParams : null;
                    int i13 = layoutParams2 != null ? layoutParams2.gravity : DEFAULT_CUSTOM_GRAVITY;
                    int measuredWidth = view.getMeasuredWidth();
                    if (layoutParams2 == null) {
                        i7 = 0;
                        i8 = 0;
                    } else {
                        i6 = next(i6, layoutParams2.getMarginStart(), isLayoutRtl);
                        paddingLeft += i11 * layoutParams2.getMarginEnd();
                        i8 = layoutParams2.topMargin;
                        i7 = layoutParams2.bottomMargin;
                    }
                    int i14 = 8388615 & i13;
                    if (i14 == 1) {
                        int i15 = ((this.mRight - this.mLeft) - measuredWidth) / 2;
                        if (isLayoutRtl) {
                            if (i15 + measuredWidth > i6) {
                                i14 = 5;
                            } else if (i15 < paddingLeft) {
                                i14 = 3;
                            }
                        } else {
                            int i16 = i15 + measuredWidth;
                            if (i15 < i6) {
                                i14 = 3;
                            } else if (i16 > paddingLeft) {
                                i14 = 5;
                            }
                        }
                    } else if (i13 == 0) {
                        i14 = android.view.Gravity.START;
                    }
                    switch (android.view.Gravity.getAbsoluteGravity(i14, layoutDirection)) {
                        case 1:
                            i9 = ((this.mRight - this.mLeft) - measuredWidth) / 2;
                            break;
                        case 2:
                        case 4:
                        default:
                            i9 = 0;
                            break;
                        case 3:
                            if (!isLayoutRtl) {
                                paddingLeft = i6;
                            }
                            i9 = paddingLeft;
                            break;
                        case 5:
                            if (!isLayoutRtl) {
                                i9 = paddingLeft - measuredWidth;
                                break;
                            } else {
                                i9 = i6 - measuredWidth;
                                break;
                            }
                    }
                    switch (i13 != 0 ? i13 & 112 : 16) {
                        case 16:
                            paddingBottom = ((((this.mBottom - this.mTop) - getPaddingBottom()) - getPaddingTop()) - view.getMeasuredHeight()) / 2;
                            break;
                        case 48:
                            paddingBottom = getPaddingTop() + i8;
                            break;
                        case 80:
                            paddingBottom = ((getHeight() - getPaddingBottom()) - view.getMeasuredHeight()) - i7;
                            break;
                        default:
                            paddingBottom = 0;
                            break;
                    }
                    int measuredWidth2 = view.getMeasuredWidth();
                    view.layout(i9, paddingBottom, i9 + measuredWidth2, view.getMeasuredHeight() + paddingBottom);
                    next(i6, measuredWidth2, isLayoutRtl);
                }
                if (this.mProgressView != null) {
                    this.mProgressView.bringToFront();
                    int measuredHeight = this.mProgressView.getMeasuredHeight() / 2;
                    this.mProgressView.layout(this.mProgressBarPadding, -measuredHeight, this.mProgressBarPadding + this.mProgressView.getMeasuredWidth(), measuredHeight);
                    return;
                }
                return;
            }
            i6 = next;
            if (this.mMenuView != null) {
                positionChild(this.mMenuView, paddingLeft, paddingTop2, paddingTop, !isLayoutRtl);
                paddingLeft += this.mMenuView.getMeasuredWidth() * i11;
            }
            if (this.mIndeterminateProgressView != null) {
                positionChild(this.mIndeterminateProgressView, paddingLeft, paddingTop2, paddingTop, !isLayoutRtl);
                paddingLeft += this.mIndeterminateProgressView.getMeasuredWidth() * i11;
            }
            if (this.mExpandedActionView != null) {
            }
            if (view != null) {
            }
            if (this.mProgressView != null) {
            }
        }
        i5 = 0;
        int next2 = next(paddingRight + positionChild(this.mUpGoerFive, next(paddingRight, i5, isLayoutRtl), paddingTop2, paddingTop, isLayoutRtl), i5, isLayoutRtl);
        if (this.mExpandedActionView == null) {
        }
        i6 = next2;
        if (this.mMenuView != null) {
        }
        if (this.mIndeterminateProgressView != null) {
        }
        if (this.mExpandedActionView != null) {
        }
        if (view != null) {
        }
        if (this.mProgressView != null) {
        }
    }

    @Override // android.view.ViewGroup
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.app.ActionBar.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            return generateDefaultLayoutParams();
        }
        return layoutParams;
    }

    @Override // android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        com.android.internal.widget.ActionBarView.SavedState savedState = new com.android.internal.widget.ActionBarView.SavedState(super.onSaveInstanceState());
        if (this.mExpandedMenuPresenter != null && this.mExpandedMenuPresenter.mCurrentExpandedItem != null) {
            savedState.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        savedState.isOverflowOpen = isOverflowMenuShowing();
        return savedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        android.view.MenuItem findItem;
        com.android.internal.widget.ActionBarView.SavedState savedState = (com.android.internal.widget.ActionBarView.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && this.mOptionsMenu != null && (findItem = this.mOptionsMenu.findItem(savedState.expandedMenuItemId)) != null) {
            findItem.expandActionView();
        }
        if (savedState.isOverflowOpen) {
            postShowOverflowMenu();
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationIcon(android.graphics.drawable.Drawable drawable) {
        this.mHomeLayout.setUpIndicator(drawable);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDefaultNavigationIcon(android.graphics.drawable.Drawable drawable) {
        this.mHomeLayout.setDefaultUpIndicator(drawable);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationIcon(int i) {
        this.mHomeLayout.setUpIndicator(i);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationContentDescription(java.lang.CharSequence charSequence) {
        this.mHomeDescription = charSequence;
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationContentDescription(int i) {
        this.mHomeDescriptionRes = i;
        this.mHomeDescription = i != 0 ? getResources().getText(i) : null;
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDefaultNavigationContentDescription(int i) {
        if (this.mDefaultUpDescription == i) {
            return;
        }
        this.mDefaultUpDescription = i;
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenuCallbacks(com.android.internal.view.menu.MenuPresenter.Callback callback, com.android.internal.view.menu.MenuBuilder.Callback callback2) {
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.setCallback(callback);
        }
        if (this.mOptionsMenu != null) {
            this.mOptionsMenu.setCallback(callback2);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public android.view.Menu getMenu() {
        return this.mOptionsMenu;
    }

    static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<com.android.internal.widget.ActionBarView.SavedState> CREATOR = new android.os.Parcelable.Creator<com.android.internal.widget.ActionBarView.SavedState>() { // from class: com.android.internal.widget.ActionBarView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.widget.ActionBarView.SavedState createFromParcel(android.os.Parcel parcel) {
                return new com.android.internal.widget.ActionBarView.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.widget.ActionBarView.SavedState[] newArray(int i) {
                return new com.android.internal.widget.ActionBarView.SavedState[i];
            }
        };
        int expandedMenuItemId;
        boolean isOverflowOpen;

        SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.expandedMenuItemId = parcel.readInt();
            this.isOverflowOpen = parcel.readInt() != 0;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.expandedMenuItemId);
            parcel.writeInt(this.isOverflowOpen ? 1 : 0);
        }
    }

    private static class HomeView extends android.widget.FrameLayout {
        private static final long DEFAULT_TRANSITION_DURATION = 150;
        private android.graphics.drawable.Drawable mDefaultUpIndicator;
        private android.widget.ImageView mIconView;
        private int mStartOffset;
        private android.graphics.drawable.Drawable mUpIndicator;
        private int mUpIndicatorRes;
        private android.widget.ImageView mUpView;
        private int mUpWidth;

        public HomeView(android.content.Context context) {
            this(context, null);
        }

        public HomeView(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            android.animation.LayoutTransition layoutTransition = getLayoutTransition();
            if (layoutTransition != null) {
                layoutTransition.setDuration(DEFAULT_TRANSITION_DURATION);
            }
        }

        public void setShowUp(boolean z) {
            this.mUpView.setVisibility(z ? 0 : 8);
        }

        public void setShowIcon(boolean z) {
            this.mIconView.setVisibility(z ? 0 : 8);
        }

        public void setIcon(android.graphics.drawable.Drawable drawable) {
            this.mIconView.lambda$setImageURIAsync$2(drawable);
        }

        public void setUpIndicator(android.graphics.drawable.Drawable drawable) {
            this.mUpIndicator = drawable;
            this.mUpIndicatorRes = 0;
            updateUpIndicator();
        }

        public void setDefaultUpIndicator(android.graphics.drawable.Drawable drawable) {
            this.mDefaultUpIndicator = drawable;
            updateUpIndicator();
        }

        public void setUpIndicator(int i) {
            this.mUpIndicatorRes = i;
            this.mUpIndicator = null;
            updateUpIndicator();
        }

        private void updateUpIndicator() {
            if (this.mUpIndicator != null) {
                this.mUpView.lambda$setImageURIAsync$2(this.mUpIndicator);
            } else if (this.mUpIndicatorRes != 0) {
                this.mUpView.lambda$setImageURIAsync$2(getContext().getDrawable(this.mUpIndicatorRes));
            } else {
                this.mUpView.lambda$setImageURIAsync$2(this.mDefaultUpIndicator);
            }
        }

        @Override // android.view.View
        protected void onConfigurationChanged(android.content.res.Configuration configuration) {
            super.onConfigurationChanged(configuration);
            if (this.mUpIndicatorRes != 0) {
                updateUpIndicator();
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchPopulateAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            onPopulateAccessibilityEvent(accessibilityEvent);
            return true;
        }

        @Override // android.view.View
        public void onPopulateAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEventInternal(accessibilityEvent);
            java.lang.CharSequence contentDescription = getContentDescription();
            if (!android.text.TextUtils.isEmpty(contentDescription)) {
                accessibilityEvent.getText().add(contentDescription);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchHoverEvent(android.view.MotionEvent motionEvent) {
            return onHoverEvent(motionEvent);
        }

        @Override // android.view.View
        protected void onFinishInflate() {
            this.mUpView = (android.widget.ImageView) findViewById(com.android.internal.R.id.up);
            this.mIconView = (android.widget.ImageView) findViewById(16908332);
            this.mDefaultUpIndicator = this.mUpView.getDrawable();
        }

        public int getStartOffset() {
            if (this.mUpView.getVisibility() == 8) {
                return this.mStartOffset;
            }
            return 0;
        }

        public int getUpWidth() {
            return this.mUpWidth;
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            measureChildWithMargins(this.mUpView, i, 0, i2, 0);
            android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) this.mUpView.getLayoutParams();
            int i3 = layoutParams.leftMargin + layoutParams.rightMargin;
            this.mUpWidth = this.mUpView.getMeasuredWidth();
            this.mStartOffset = this.mUpWidth + i3;
            int i4 = this.mUpView.getVisibility() == 8 ? 0 : this.mStartOffset;
            int measuredHeight = layoutParams.bottomMargin + layoutParams.topMargin + this.mUpView.getMeasuredHeight();
            if (this.mIconView.getVisibility() != 8) {
                measureChildWithMargins(this.mIconView, i, i4, i2, 0);
                android.widget.FrameLayout.LayoutParams layoutParams2 = (android.widget.FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
                i4 += layoutParams2.leftMargin + this.mIconView.getMeasuredWidth() + layoutParams2.rightMargin;
                measuredHeight = java.lang.Math.max(measuredHeight, layoutParams2.topMargin + this.mIconView.getMeasuredHeight() + layoutParams2.bottomMargin);
            } else if (i3 < 0) {
                i4 -= i3;
            }
            int mode = android.view.View.MeasureSpec.getMode(i);
            int mode2 = android.view.View.MeasureSpec.getMode(i2);
            int size = android.view.View.MeasureSpec.getSize(i);
            int size2 = android.view.View.MeasureSpec.getSize(i2);
            switch (mode) {
                case Integer.MIN_VALUE:
                    i4 = java.lang.Math.min(i4, size);
                    break;
                case 1073741824:
                    i4 = size;
                    break;
            }
            switch (mode2) {
                case Integer.MIN_VALUE:
                    measuredHeight = java.lang.Math.min(measuredHeight, size2);
                    break;
                case 1073741824:
                    measuredHeight = size2;
                    break;
            }
            setMeasuredDimension(i4, measuredHeight);
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int i5;
            int i6;
            int i7 = (i4 - i2) / 2;
            boolean isLayoutRtl = isLayoutRtl();
            int width = getWidth();
            int i8 = 0;
            if (this.mUpView.getVisibility() != 8) {
                android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) this.mUpView.getLayoutParams();
                int measuredHeight = this.mUpView.getMeasuredHeight();
                int measuredWidth = this.mUpView.getMeasuredWidth();
                int i9 = layoutParams.leftMargin + measuredWidth + layoutParams.rightMargin;
                int i10 = i7 - (measuredHeight / 2);
                int i11 = measuredHeight + i10;
                if (isLayoutRtl) {
                    i8 = width - measuredWidth;
                    i3 -= i9;
                    measuredWidth = width;
                } else {
                    i += i9;
                }
                this.mUpView.layout(i8, i10, measuredWidth, i11);
                i8 = i9;
            }
            android.widget.FrameLayout.LayoutParams layoutParams2 = (android.widget.FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
            int measuredHeight2 = this.mIconView.getMeasuredHeight();
            int measuredWidth2 = this.mIconView.getMeasuredWidth();
            int i12 = (i3 - i) / 2;
            int max = java.lang.Math.max(layoutParams2.topMargin, i7 - (measuredHeight2 / 2));
            int i13 = measuredHeight2 + max;
            int max2 = java.lang.Math.max(layoutParams2.getMarginStart(), i12 - (measuredWidth2 / 2));
            if (isLayoutRtl) {
                i6 = (width - i8) - max2;
                i5 = i6 - measuredWidth2;
            } else {
                i5 = i8 + max2;
                i6 = i5 + measuredWidth2;
            }
            this.mIconView.layout(i5, max, i6, i13);
        }
    }

    private class ExpandedActionViewMenuPresenter implements com.android.internal.view.menu.MenuPresenter {
        com.android.internal.view.menu.MenuItemImpl mCurrentExpandedItem;
        com.android.internal.view.menu.MenuBuilder mMenu;

        private ExpandedActionViewMenuPresenter() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void initForMenu(android.content.Context context, com.android.internal.view.menu.MenuBuilder menuBuilder) {
            if (this.mMenu != null && this.mCurrentExpandedItem != null) {
                this.mMenu.collapseItemActionView(this.mCurrentExpandedItem);
            }
            this.mMenu = menuBuilder;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public com.android.internal.view.menu.MenuView getMenuView(android.view.ViewGroup viewGroup) {
            return null;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void updateMenuView(boolean z) {
            if (this.mCurrentExpandedItem != null) {
                boolean z2 = false;
                if (this.mMenu != null) {
                    int size = this.mMenu.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            break;
                        }
                        if (this.mMenu.getItem(i) != this.mCurrentExpandedItem) {
                            i++;
                        } else {
                            z2 = true;
                            break;
                        }
                    }
                }
                if (!z2) {
                    collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
                }
            }
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void setCallback(com.android.internal.view.menu.MenuPresenter.Callback callback) {
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean onSubMenuSelected(com.android.internal.view.menu.SubMenuBuilder subMenuBuilder) {
            return false;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean flagActionItems() {
            return false;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean expandItemActionView(com.android.internal.view.menu.MenuBuilder menuBuilder, com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
            com.android.internal.widget.ActionBarView.this.mExpandedActionView = menuItemImpl.getActionView();
            com.android.internal.widget.ActionBarView.this.mExpandedHomeLayout.setIcon(com.android.internal.widget.ActionBarView.this.mIcon.getConstantState().newDrawable(com.android.internal.widget.ActionBarView.this.getResources()));
            this.mCurrentExpandedItem = menuItemImpl;
            if (com.android.internal.widget.ActionBarView.this.mExpandedActionView.getParent() != com.android.internal.widget.ActionBarView.this) {
                com.android.internal.widget.ActionBarView.this.addView(com.android.internal.widget.ActionBarView.this.mExpandedActionView);
            }
            if (com.android.internal.widget.ActionBarView.this.mExpandedHomeLayout.getParent() != com.android.internal.widget.ActionBarView.this.mUpGoerFive) {
                com.android.internal.widget.ActionBarView.this.mUpGoerFive.addView(com.android.internal.widget.ActionBarView.this.mExpandedHomeLayout);
            }
            com.android.internal.widget.ActionBarView.this.mHomeLayout.setVisibility(8);
            if (com.android.internal.widget.ActionBarView.this.mTitleLayout != null) {
                com.android.internal.widget.ActionBarView.this.mTitleLayout.setVisibility(8);
            }
            if (com.android.internal.widget.ActionBarView.this.mTabScrollView != null) {
                com.android.internal.widget.ActionBarView.this.mTabScrollView.setVisibility(8);
            }
            if (com.android.internal.widget.ActionBarView.this.mSpinner != null) {
                com.android.internal.widget.ActionBarView.this.mSpinner.setVisibility(8);
            }
            if (com.android.internal.widget.ActionBarView.this.mCustomNavView != null) {
                com.android.internal.widget.ActionBarView.this.mCustomNavView.setVisibility(8);
            }
            com.android.internal.widget.ActionBarView.this.setHomeButtonEnabled(false, false);
            com.android.internal.widget.ActionBarView.this.requestLayout();
            menuItemImpl.setActionViewExpanded(true);
            if (com.android.internal.widget.ActionBarView.this.mExpandedActionView instanceof android.view.CollapsibleActionView) {
                ((android.view.CollapsibleActionView) com.android.internal.widget.ActionBarView.this.mExpandedActionView).onActionViewExpanded();
            }
            return true;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean collapseItemActionView(com.android.internal.view.menu.MenuBuilder menuBuilder, com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
            if (com.android.internal.widget.ActionBarView.this.mExpandedActionView instanceof android.view.CollapsibleActionView) {
                ((android.view.CollapsibleActionView) com.android.internal.widget.ActionBarView.this.mExpandedActionView).onActionViewCollapsed();
            }
            com.android.internal.widget.ActionBarView.this.removeView(com.android.internal.widget.ActionBarView.this.mExpandedActionView);
            com.android.internal.widget.ActionBarView.this.mUpGoerFive.removeView(com.android.internal.widget.ActionBarView.this.mExpandedHomeLayout);
            com.android.internal.widget.ActionBarView.this.mExpandedActionView = null;
            if ((com.android.internal.widget.ActionBarView.this.mDisplayOptions & 2) != 0) {
                com.android.internal.widget.ActionBarView.this.mHomeLayout.setVisibility(0);
            }
            if ((com.android.internal.widget.ActionBarView.this.mDisplayOptions & 8) != 0) {
                if (com.android.internal.widget.ActionBarView.this.mTitleLayout == null) {
                    com.android.internal.widget.ActionBarView.this.initTitle();
                } else {
                    com.android.internal.widget.ActionBarView.this.mTitleLayout.setVisibility(0);
                }
            }
            if (com.android.internal.widget.ActionBarView.this.mTabScrollView != null) {
                com.android.internal.widget.ActionBarView.this.mTabScrollView.setVisibility(0);
            }
            if (com.android.internal.widget.ActionBarView.this.mSpinner != null) {
                com.android.internal.widget.ActionBarView.this.mSpinner.setVisibility(0);
            }
            if (com.android.internal.widget.ActionBarView.this.mCustomNavView != null) {
                com.android.internal.widget.ActionBarView.this.mCustomNavView.setVisibility(0);
            }
            com.android.internal.widget.ActionBarView.this.mExpandedHomeLayout.setIcon(null);
            this.mCurrentExpandedItem = null;
            com.android.internal.widget.ActionBarView.this.setHomeButtonEnabled(com.android.internal.widget.ActionBarView.this.mWasHomeEnabled);
            com.android.internal.widget.ActionBarView.this.requestLayout();
            menuItemImpl.setActionViewExpanded(false);
            return true;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public int getId() {
            return 0;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public android.os.Parcelable onSaveInstanceState() {
            return null;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        }
    }
}
