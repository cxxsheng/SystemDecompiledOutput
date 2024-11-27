package com.android.internal.widget;

/* loaded from: classes5.dex */
public class ToolbarWidgetWrapper implements com.android.internal.widget.DecorToolbar {
    private static final int AFFECTS_LOGO_MASK = 3;
    private static final long DEFAULT_FADE_DURATION_MS = 200;
    private static final java.lang.String TAG = "ToolbarWidgetWrapper";
    private android.widget.ActionMenuPresenter mActionMenuPresenter;
    private android.view.View mCustomView;
    private int mDefaultNavigationContentDescription;
    private android.graphics.drawable.Drawable mDefaultNavigationIcon;
    private int mDisplayOpts;
    private java.lang.CharSequence mHomeDescription;
    private android.graphics.drawable.Drawable mIcon;
    private android.graphics.drawable.Drawable mLogo;
    private boolean mMenuPrepared;
    private android.graphics.drawable.Drawable mNavIcon;
    private int mNavigationMode;
    private android.widget.Spinner mSpinner;
    private java.lang.CharSequence mSubtitle;
    private android.view.View mTabView;
    private java.lang.CharSequence mTitle;
    private boolean mTitleSet;
    private android.widget.Toolbar mToolbar;
    private android.view.Window.Callback mWindowCallback;

    public ToolbarWidgetWrapper(android.widget.Toolbar toolbar, boolean z) {
        this(toolbar, z, com.android.internal.R.string.action_bar_up_description);
    }

    public ToolbarWidgetWrapper(android.widget.Toolbar toolbar, boolean z, int i) {
        this.mNavigationMode = 0;
        this.mDefaultNavigationContentDescription = 0;
        this.mToolbar = toolbar;
        this.mTitle = toolbar.getTitle();
        this.mSubtitle = toolbar.getSubtitle();
        this.mTitleSet = this.mTitle != null;
        this.mNavIcon = this.mToolbar.getNavigationIcon();
        android.content.res.TypedArray obtainStyledAttributes = toolbar.getContext().obtainStyledAttributes(null, com.android.internal.R.styleable.ActionBar, 16843470, 0);
        this.mDefaultNavigationIcon = obtainStyledAttributes.getDrawable(13);
        if (z) {
            java.lang.CharSequence text = obtainStyledAttributes.getText(5);
            if (!android.text.TextUtils.isEmpty(text)) {
                setTitle(text);
            }
            java.lang.CharSequence text2 = obtainStyledAttributes.getText(9);
            if (!android.text.TextUtils.isEmpty(text2)) {
                setSubtitle(text2);
            }
            android.graphics.drawable.Drawable drawable = obtainStyledAttributes.getDrawable(6);
            if (drawable != null) {
                setLogo(drawable);
            }
            android.graphics.drawable.Drawable drawable2 = obtainStyledAttributes.getDrawable(0);
            if (drawable2 != null) {
                setIcon(drawable2);
            }
            if (this.mNavIcon == null && this.mDefaultNavigationIcon != null) {
                setNavigationIcon(this.mDefaultNavigationIcon);
            }
            setDisplayOptions(obtainStyledAttributes.getInt(8, 0));
            int resourceId = obtainStyledAttributes.getResourceId(10, 0);
            if (resourceId != 0) {
                setCustomView(android.view.LayoutInflater.from(this.mToolbar.getContext()).inflate(resourceId, (android.view.ViewGroup) this.mToolbar, false));
                setDisplayOptions(this.mDisplayOpts | 16);
            }
            int layoutDimension = obtainStyledAttributes.getLayoutDimension(4, 0);
            if (layoutDimension > 0) {
                android.view.ViewGroup.LayoutParams layoutParams = this.mToolbar.getLayoutParams();
                layoutParams.height = layoutDimension;
                this.mToolbar.setLayoutParams(layoutParams);
            }
            int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(22, -1);
            int dimensionPixelOffset2 = obtainStyledAttributes.getDimensionPixelOffset(23, -1);
            if (dimensionPixelOffset >= 0 || dimensionPixelOffset2 >= 0) {
                this.mToolbar.setContentInsetsRelative(java.lang.Math.max(dimensionPixelOffset, 0), java.lang.Math.max(dimensionPixelOffset2, 0));
            }
            int resourceId2 = obtainStyledAttributes.getResourceId(11, 0);
            if (resourceId2 != 0) {
                this.mToolbar.setTitleTextAppearance(this.mToolbar.getContext(), resourceId2);
            }
            int resourceId3 = obtainStyledAttributes.getResourceId(12, 0);
            if (resourceId3 != 0) {
                this.mToolbar.setSubtitleTextAppearance(this.mToolbar.getContext(), resourceId3);
            }
            int resourceId4 = obtainStyledAttributes.getResourceId(26, 0);
            if (resourceId4 != 0) {
                this.mToolbar.setPopupTheme(resourceId4);
            }
        } else {
            this.mDisplayOpts = detectDisplayOptions();
        }
        obtainStyledAttributes.recycle();
        setDefaultNavigationContentDescription(i);
        this.mHomeDescription = this.mToolbar.getNavigationContentDescription();
        this.mToolbar.setNavigationOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.widget.ToolbarWidgetWrapper.1
            final com.android.internal.view.menu.ActionMenuItem mNavItem;

            {
                this.mNavItem = new com.android.internal.view.menu.ActionMenuItem(com.android.internal.widget.ToolbarWidgetWrapper.this.mToolbar.getContext(), 0, 16908332, 0, 0, com.android.internal.widget.ToolbarWidgetWrapper.this.mTitle);
            }

            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                if (com.android.internal.widget.ToolbarWidgetWrapper.this.mWindowCallback != null && com.android.internal.widget.ToolbarWidgetWrapper.this.mMenuPrepared) {
                    com.android.internal.widget.ToolbarWidgetWrapper.this.mWindowCallback.onMenuItemSelected(0, this.mNavItem);
                }
            }
        });
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDefaultNavigationContentDescription(int i) {
        if (i == this.mDefaultNavigationContentDescription) {
            return;
        }
        this.mDefaultNavigationContentDescription = i;
        if (android.text.TextUtils.isEmpty(this.mToolbar.getNavigationContentDescription())) {
            setNavigationContentDescription(this.mDefaultNavigationContentDescription);
        }
    }

    private int detectDisplayOptions() {
        if (this.mToolbar.getNavigationIcon() == null) {
            return 11;
        }
        this.mDefaultNavigationIcon = this.mToolbar.getNavigationIcon();
        return 15;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public android.view.ViewGroup getViewGroup() {
        return this.mToolbar;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public android.content.Context getContext() {
        return this.mToolbar.getContext();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean isSplit() {
        return false;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasExpandedActionView() {
        return this.mToolbar.hasExpandedActionView();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void collapseActionView() {
        this.mToolbar.collapseActionView();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setWindowCallback(android.view.Window.Callback callback) {
        this.mWindowCallback = callback;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setWindowTitle(java.lang.CharSequence charSequence) {
        if (!this.mTitleSet) {
            setTitleInt(charSequence);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public java.lang.CharSequence getTitle() {
        return this.mToolbar.getTitle();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setTitle(java.lang.CharSequence charSequence) {
        this.mTitleSet = true;
        setTitleInt(charSequence);
    }

    private void setTitleInt(java.lang.CharSequence charSequence) {
        this.mTitle = charSequence;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setTitle(charSequence);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public java.lang.CharSequence getSubtitle() {
        return this.mToolbar.getSubtitle();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setSubtitle(java.lang.CharSequence charSequence) {
        this.mSubtitle = charSequence;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setSubtitle(charSequence);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void initProgress() {
        android.util.Log.i(TAG, "Progress display unsupported");
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void initIndeterminateProgress() {
        android.util.Log.i(TAG, "Progress display unsupported");
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean canSplit() {
        return false;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setSplitView(android.view.ViewGroup viewGroup) {
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setSplitToolbar(boolean z) {
        if (z) {
            throw new java.lang.UnsupportedOperationException("Cannot split an android.widget.Toolbar");
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setSplitWhenNarrow(boolean z) {
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasIcon() {
        return this.mIcon != null;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasLogo() {
        return this.mLogo != null;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setIcon(int i) {
        setIcon(i != 0 ? getContext().getDrawable(i) : null);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setIcon(android.graphics.drawable.Drawable drawable) {
        this.mIcon = drawable;
        updateToolbarLogo();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setLogo(int i) {
        setLogo(i != 0 ? getContext().getDrawable(i) : null);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setLogo(android.graphics.drawable.Drawable drawable) {
        this.mLogo = drawable;
        updateToolbarLogo();
    }

    private void updateToolbarLogo() {
        android.graphics.drawable.Drawable drawable;
        if ((this.mDisplayOpts & 2) == 0) {
            drawable = null;
        } else if ((this.mDisplayOpts & 1) != 0) {
            drawable = this.mLogo != null ? this.mLogo : this.mIcon;
        } else {
            drawable = this.mIcon;
        }
        this.mToolbar.setLogo(drawable);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean canShowOverflowMenu() {
        return this.mToolbar.canShowOverflowMenu();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean isOverflowMenuShowing() {
        return this.mToolbar.isOverflowMenuShowing();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean isOverflowMenuShowPending() {
        return this.mToolbar.isOverflowMenuShowPending();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean showOverflowMenu() {
        return this.mToolbar.showOverflowMenu();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hideOverflowMenu() {
        return this.mToolbar.hideOverflowMenu();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenuPrepared() {
        this.mMenuPrepared = true;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenu(android.view.Menu menu, com.android.internal.view.menu.MenuPresenter.Callback callback) {
        if (this.mActionMenuPresenter == null) {
            this.mActionMenuPresenter = new android.widget.ActionMenuPresenter(this.mToolbar.getContext());
            this.mActionMenuPresenter.setId(com.android.internal.R.id.action_menu_presenter);
        }
        this.mActionMenuPresenter.setCallback(callback);
        this.mToolbar.setMenu((com.android.internal.view.menu.MenuBuilder) menu, this.mActionMenuPresenter);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void dismissPopupMenus() {
        this.mToolbar.dismissPopupMenus();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDisplayOptions() {
        return this.mDisplayOpts;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDisplayOptions(int i) {
        int i2 = this.mDisplayOpts ^ i;
        this.mDisplayOpts = i;
        if (i2 != 0) {
            if ((i2 & 4) != 0) {
                if ((i & 4) != 0) {
                    updateHomeAccessibility();
                }
                updateNavigationIcon();
            }
            if ((i2 & 3) != 0) {
                updateToolbarLogo();
            }
            if ((i2 & 8) != 0) {
                if ((i & 8) != 0) {
                    this.mToolbar.setTitle(this.mTitle);
                    this.mToolbar.setSubtitle(this.mSubtitle);
                } else {
                    this.mToolbar.setTitle((java.lang.CharSequence) null);
                    this.mToolbar.setSubtitle((java.lang.CharSequence) null);
                }
            }
            if ((i2 & 16) != 0 && this.mCustomView != null) {
                if ((i & 16) != 0) {
                    this.mToolbar.addView(this.mCustomView);
                } else {
                    this.mToolbar.removeView(this.mCustomView);
                }
            }
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setEmbeddedTabView(com.android.internal.widget.ScrollingTabContainerView scrollingTabContainerView) {
        if (this.mTabView != null && this.mTabView.getParent() == this.mToolbar) {
            this.mToolbar.removeView(this.mTabView);
        }
        this.mTabView = scrollingTabContainerView;
        if (scrollingTabContainerView != null && this.mNavigationMode == 2) {
            this.mToolbar.addView(this.mTabView, 0);
            android.widget.Toolbar.LayoutParams layoutParams = (android.widget.Toolbar.LayoutParams) this.mTabView.getLayoutParams();
            layoutParams.width = -2;
            layoutParams.height = -2;
            layoutParams.gravity = 8388691;
            scrollingTabContainerView.setAllowCollapse(true);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasEmbeddedTabs() {
        return this.mTabView != null;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean isTitleTruncated() {
        return this.mToolbar.isTitleTruncated();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setCollapsible(boolean z) {
        this.mToolbar.setCollapsible(z);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setHomeButtonEnabled(boolean z) {
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationMode(int i) {
        int i2 = this.mNavigationMode;
        if (i != i2) {
            switch (i2) {
                case 1:
                    if (this.mSpinner != null && this.mSpinner.getParent() == this.mToolbar) {
                        this.mToolbar.removeView(this.mSpinner);
                        break;
                    }
                    break;
                case 2:
                    if (this.mTabView != null && this.mTabView.getParent() == this.mToolbar) {
                        this.mToolbar.removeView(this.mTabView);
                        break;
                    }
                    break;
            }
            this.mNavigationMode = i;
            switch (i) {
                case 0:
                    return;
                case 1:
                    ensureSpinner();
                    this.mToolbar.addView(this.mSpinner, 0);
                    return;
                case 2:
                    if (this.mTabView != null) {
                        this.mToolbar.addView(this.mTabView, 0);
                        android.widget.Toolbar.LayoutParams layoutParams = (android.widget.Toolbar.LayoutParams) this.mTabView.getLayoutParams();
                        layoutParams.width = -2;
                        layoutParams.height = -2;
                        layoutParams.gravity = 8388691;
                        return;
                    }
                    return;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid navigation mode " + i);
            }
        }
    }

    private void ensureSpinner() {
        if (this.mSpinner == null) {
            this.mSpinner = new android.widget.Spinner(getContext(), null, 16843479);
            this.mSpinner.setLayoutParams(new android.widget.Toolbar.LayoutParams(-2, -2, 8388627));
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDropdownParams(android.widget.SpinnerAdapter spinnerAdapter, android.widget.AdapterView.OnItemSelectedListener onItemSelectedListener) {
        ensureSpinner();
        this.mSpinner.setAdapter(spinnerAdapter);
        this.mSpinner.setOnItemSelectedListener(onItemSelectedListener);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDropdownSelectedPosition(int i) {
        if (this.mSpinner == null) {
            throw new java.lang.IllegalStateException("Can't set dropdown selected position without an adapter");
        }
        this.mSpinner.setSelection(i);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDropdownSelectedPosition() {
        if (this.mSpinner != null) {
            return this.mSpinner.getSelectedItemPosition();
        }
        return 0;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDropdownItemCount() {
        if (this.mSpinner != null) {
            return this.mSpinner.getCount();
        }
        return 0;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setCustomView(android.view.View view) {
        if (this.mCustomView != null && (this.mDisplayOpts & 16) != 0) {
            this.mToolbar.removeView(this.mCustomView);
        }
        this.mCustomView = view;
        if (view != null && (this.mDisplayOpts & 16) != 0) {
            this.mToolbar.addView(this.mCustomView);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public android.view.View getCustomView() {
        return this.mCustomView;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void animateToVisibility(int i) {
        android.animation.Animator animator = setupAnimatorToVisibility(i, DEFAULT_FADE_DURATION_MS);
        if (animator != null) {
            animator.start();
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public android.animation.Animator setupAnimatorToVisibility(int i, long j) {
        if (i == 8) {
            android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(this.mToolbar, (android.util.Property<android.widget.Toolbar, java.lang.Float>) android.view.View.ALPHA, 1.0f, 0.0f);
            ofFloat.setDuration(j);
            ofFloat.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.ToolbarWidgetWrapper.2
                private boolean mCanceled = false;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(android.animation.Animator animator) {
                    if (!this.mCanceled) {
                        com.android.internal.widget.ToolbarWidgetWrapper.this.mToolbar.setVisibility(8);
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(android.animation.Animator animator) {
                    this.mCanceled = true;
                }
            });
            return ofFloat;
        }
        if (i == 0) {
            android.animation.ObjectAnimator ofFloat2 = android.animation.ObjectAnimator.ofFloat(this.mToolbar, (android.util.Property<android.widget.Toolbar, java.lang.Float>) android.view.View.ALPHA, 0.0f, 1.0f);
            ofFloat2.setDuration(j);
            ofFloat2.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.ToolbarWidgetWrapper.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(android.animation.Animator animator) {
                    com.android.internal.widget.ToolbarWidgetWrapper.this.mToolbar.setVisibility(0);
                }
            });
            return ofFloat2;
        }
        return null;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationIcon(android.graphics.drawable.Drawable drawable) {
        this.mNavIcon = drawable;
        updateNavigationIcon();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationIcon(int i) {
        setNavigationIcon(i != 0 ? this.mToolbar.getContext().getDrawable(i) : null);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDefaultNavigationIcon(android.graphics.drawable.Drawable drawable) {
        if (this.mDefaultNavigationIcon != drawable) {
            this.mDefaultNavigationIcon = drawable;
            updateNavigationIcon();
        }
    }

    private void updateNavigationIcon() {
        if ((this.mDisplayOpts & 4) != 0) {
            this.mToolbar.setNavigationIcon(this.mNavIcon != null ? this.mNavIcon : this.mDefaultNavigationIcon);
        } else {
            this.mToolbar.setNavigationIcon((android.graphics.drawable.Drawable) null);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationContentDescription(java.lang.CharSequence charSequence) {
        this.mHomeDescription = charSequence;
        updateHomeAccessibility();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationContentDescription(int i) {
        setNavigationContentDescription(i == 0 ? null : getContext().getString(i));
    }

    private void updateHomeAccessibility() {
        if ((this.mDisplayOpts & 4) != 0) {
            if (android.text.TextUtils.isEmpty(this.mHomeDescription)) {
                this.mToolbar.setNavigationContentDescription(this.mDefaultNavigationContentDescription);
            } else {
                this.mToolbar.setNavigationContentDescription(this.mHomeDescription);
            }
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void saveHierarchyState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        this.mToolbar.saveHierarchyState(sparseArray);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void restoreHierarchyState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        this.mToolbar.restoreHierarchyState(sparseArray);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        this.mToolbar.setBackgroundDrawable(drawable);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getHeight() {
        return this.mToolbar.getHeight();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setVisibility(int i) {
        this.mToolbar.setVisibility(i);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getVisibility() {
        return this.mToolbar.getVisibility();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenuCallbacks(com.android.internal.view.menu.MenuPresenter.Callback callback, com.android.internal.view.menu.MenuBuilder.Callback callback2) {
        this.mToolbar.setMenuCallbacks(callback, callback2);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public android.view.Menu getMenu() {
        return this.mToolbar.getMenu();
    }
}
