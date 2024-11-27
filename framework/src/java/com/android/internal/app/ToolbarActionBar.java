package com.android.internal.app;

/* loaded from: classes4.dex */
public class ToolbarActionBar extends android.app.ActionBar {
    private com.android.internal.widget.DecorToolbar mDecorToolbar;
    private boolean mLastMenuVisibility;
    private boolean mMenuCallbackSet;
    private boolean mToolbarMenuPrepared;
    private android.view.Window.Callback mWindowCallback;
    private java.util.ArrayList<android.app.ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new java.util.ArrayList<>();
    private final java.lang.Runnable mMenuInvalidator = new java.lang.Runnable() { // from class: com.android.internal.app.ToolbarActionBar.1
        @Override // java.lang.Runnable
        public void run() {
            com.android.internal.app.ToolbarActionBar.this.populateOptionsMenu();
        }
    };
    private final android.widget.Toolbar.OnMenuItemClickListener mMenuClicker = new android.widget.Toolbar.OnMenuItemClickListener() { // from class: com.android.internal.app.ToolbarActionBar.2
        @Override // android.widget.Toolbar.OnMenuItemClickListener
        public boolean onMenuItemClick(android.view.MenuItem menuItem) {
            return com.android.internal.app.ToolbarActionBar.this.mWindowCallback.onMenuItemSelected(0, menuItem);
        }
    };

    public ToolbarActionBar(android.widget.Toolbar toolbar, java.lang.CharSequence charSequence, android.view.Window.Callback callback) {
        this.mDecorToolbar = new com.android.internal.widget.ToolbarWidgetWrapper(toolbar, false);
        this.mWindowCallback = new com.android.internal.app.ToolbarActionBar.ToolbarCallbackWrapper(callback);
        this.mDecorToolbar.setWindowCallback(this.mWindowCallback);
        toolbar.setOnMenuItemClickListener(this.mMenuClicker);
        this.mDecorToolbar.setWindowTitle(charSequence);
    }

    public android.view.Window.Callback getWrappedWindowCallback() {
        return this.mWindowCallback;
    }

    @Override // android.app.ActionBar
    public void setCustomView(android.view.View view) {
        setCustomView(view, new android.app.ActionBar.LayoutParams(-2, -2));
    }

    @Override // android.app.ActionBar
    public void setCustomView(android.view.View view, android.app.ActionBar.LayoutParams layoutParams) {
        if (view != null) {
            view.setLayoutParams(layoutParams);
        }
        this.mDecorToolbar.setCustomView(view);
    }

    @Override // android.app.ActionBar
    public void setCustomView(int i) {
        setCustomView(android.view.LayoutInflater.from(this.mDecorToolbar.getContext()).inflate(i, this.mDecorToolbar.getViewGroup(), false));
    }

    @Override // android.app.ActionBar
    public void setIcon(int i) {
        this.mDecorToolbar.setIcon(i);
    }

    @Override // android.app.ActionBar
    public void setIcon(android.graphics.drawable.Drawable drawable) {
        this.mDecorToolbar.setIcon(drawable);
    }

    @Override // android.app.ActionBar
    public void setLogo(int i) {
        this.mDecorToolbar.setLogo(i);
    }

    @Override // android.app.ActionBar
    public void setLogo(android.graphics.drawable.Drawable drawable) {
        this.mDecorToolbar.setLogo(drawable);
    }

    @Override // android.app.ActionBar
    public void setStackedBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
    }

    @Override // android.app.ActionBar
    public void setSplitBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
    }

    @Override // android.app.ActionBar
    public void setHomeButtonEnabled(boolean z) {
    }

    @Override // android.app.ActionBar
    public void setElevation(float f) {
        this.mDecorToolbar.getViewGroup().setElevation(f);
    }

    @Override // android.app.ActionBar
    public float getElevation() {
        return this.mDecorToolbar.getViewGroup().getElevation();
    }

    @Override // android.app.ActionBar
    public android.content.Context getThemedContext() {
        return this.mDecorToolbar.getContext();
    }

    @Override // android.app.ActionBar
    public boolean isTitleTruncated() {
        return super.isTitleTruncated();
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
    public void setDefaultDisplayHomeAsUpEnabled(boolean z) {
    }

    @Override // android.app.ActionBar
    public void setHomeActionContentDescription(int i) {
        this.mDecorToolbar.setNavigationContentDescription(i);
    }

    @Override // android.app.ActionBar
    public void setShowHideAnimationEnabled(boolean z) {
    }

    @Override // android.app.ActionBar
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.ActionBar
    public android.view.ActionMode startActionMode(android.view.ActionMode.Callback callback) {
        return null;
    }

    @Override // android.app.ActionBar
    public void setListNavigationCallbacks(android.widget.SpinnerAdapter spinnerAdapter, android.app.ActionBar.OnNavigationListener onNavigationListener) {
        this.mDecorToolbar.setDropdownParams(spinnerAdapter, new com.android.internal.app.NavItemSelectedListener(onNavigationListener));
    }

    @Override // android.app.ActionBar
    public void setSelectedNavigationItem(int i) {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                this.mDecorToolbar.setDropdownSelectedPosition(i);
                return;
            default:
                throw new java.lang.IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
        }
    }

    @Override // android.app.ActionBar
    public int getSelectedNavigationIndex() {
        return -1;
    }

    @Override // android.app.ActionBar
    public int getNavigationItemCount() {
        return 0;
    }

    @Override // android.app.ActionBar
    public void setTitle(java.lang.CharSequence charSequence) {
        this.mDecorToolbar.setTitle(charSequence);
    }

    @Override // android.app.ActionBar
    public void setTitle(int i) {
        this.mDecorToolbar.setTitle(i != 0 ? this.mDecorToolbar.getContext().getText(i) : null);
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
    public void setSubtitle(int i) {
        this.mDecorToolbar.setSubtitle(i != 0 ? this.mDecorToolbar.getContext().getText(i) : null);
    }

    @Override // android.app.ActionBar
    public void setDisplayOptions(int i) {
        setDisplayOptions(i, -1);
    }

    @Override // android.app.ActionBar
    public void setDisplayOptions(int i, int i2) {
        this.mDecorToolbar.setDisplayOptions((i & i2) | ((~i2) & this.mDecorToolbar.getDisplayOptions()));
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
    public void setBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        this.mDecorToolbar.setBackgroundDrawable(drawable);
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
        return 0;
    }

    @Override // android.app.ActionBar
    public void setNavigationMode(int i) {
        if (i == 2) {
            throw new java.lang.IllegalArgumentException("Tabs not supported in this configuration");
        }
        this.mDecorToolbar.setNavigationMode(i);
    }

    @Override // android.app.ActionBar
    public int getDisplayOptions() {
        return this.mDecorToolbar.getDisplayOptions();
    }

    @Override // android.app.ActionBar
    public android.app.ActionBar.Tab newTab() {
        throw new java.lang.UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void addTab(android.app.ActionBar.Tab tab) {
        throw new java.lang.UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void addTab(android.app.ActionBar.Tab tab, boolean z) {
        throw new java.lang.UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void addTab(android.app.ActionBar.Tab tab, int i) {
        throw new java.lang.UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void addTab(android.app.ActionBar.Tab tab, int i, boolean z) {
        throw new java.lang.UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void removeTab(android.app.ActionBar.Tab tab) {
        throw new java.lang.UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void removeTabAt(int i) {
        throw new java.lang.UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void removeAllTabs() {
        throw new java.lang.UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public void selectTab(android.app.ActionBar.Tab tab) {
        throw new java.lang.UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public android.app.ActionBar.Tab getSelectedTab() {
        throw new java.lang.UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public android.app.ActionBar.Tab getTabAt(int i) {
        throw new java.lang.UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // android.app.ActionBar
    public int getTabCount() {
        return 0;
    }

    @Override // android.app.ActionBar
    public int getHeight() {
        return this.mDecorToolbar.getHeight();
    }

    @Override // android.app.ActionBar
    public void show() {
        this.mDecorToolbar.setVisibility(0);
    }

    @Override // android.app.ActionBar
    public void hide() {
        this.mDecorToolbar.setVisibility(8);
    }

    @Override // android.app.ActionBar
    public boolean isShowing() {
        return this.mDecorToolbar.getVisibility() == 0;
    }

    @Override // android.app.ActionBar
    public boolean openOptionsMenu() {
        return this.mDecorToolbar.showOverflowMenu();
    }

    @Override // android.app.ActionBar
    public boolean closeOptionsMenu() {
        return this.mDecorToolbar.hideOverflowMenu();
    }

    @Override // android.app.ActionBar
    public boolean invalidateOptionsMenu() {
        this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
        this.mDecorToolbar.getViewGroup().postOnAnimation(this.mMenuInvalidator);
        return true;
    }

    @Override // android.app.ActionBar
    public boolean collapseActionView() {
        if (this.mDecorToolbar.hasExpandedActionView()) {
            this.mDecorToolbar.collapseActionView();
            return true;
        }
        return false;
    }

    void populateOptionsMenu() {
        byte b = 0;
        if (!this.mMenuCallbackSet) {
            this.mDecorToolbar.setMenuCallbacks(new com.android.internal.app.ToolbarActionBar.ActionMenuPresenterCallback(), new com.android.internal.app.ToolbarActionBar.MenuBuilderCallback());
            this.mMenuCallbackSet = true;
        }
        android.view.Menu menu = this.mDecorToolbar.getMenu();
        com.android.internal.view.menu.MenuBuilder menuBuilder = menu instanceof com.android.internal.view.menu.MenuBuilder ? (com.android.internal.view.menu.MenuBuilder) menu : null;
        if (menuBuilder != null) {
            menuBuilder.stopDispatchingItemsChanged();
        }
        try {
            menu.clear();
            if (!this.mWindowCallback.onCreatePanelMenu(0, menu) || !this.mWindowCallback.onPreparePanel(0, null, menu)) {
                menu.clear();
            }
        } finally {
            if (menuBuilder != null) {
                menuBuilder.startDispatchingItemsChanged();
            }
        }
    }

    @Override // android.app.ActionBar
    public boolean onMenuKeyEvent(android.view.KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            openOptionsMenu();
        }
        return true;
    }

    @Override // android.app.ActionBar
    public boolean onKeyShortcut(int i, android.view.KeyEvent keyEvent) {
        android.view.Menu menu = this.mDecorToolbar.getMenu();
        if (menu == null) {
            return false;
        }
        menu.setQwertyMode(android.view.KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
        return menu.performShortcut(i, keyEvent, 0);
    }

    @Override // android.app.ActionBar
    public void onDestroy() {
        this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
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

    private class ToolbarCallbackWrapper extends android.view.WindowCallbackWrapper {
        public ToolbarCallbackWrapper(android.view.Window.Callback callback) {
            super(callback);
        }

        @Override // android.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onPreparePanel(int i, android.view.View view, android.view.Menu menu) {
            boolean onPreparePanel = super.onPreparePanel(i, view, menu);
            if (onPreparePanel && !com.android.internal.app.ToolbarActionBar.this.mToolbarMenuPrepared) {
                com.android.internal.app.ToolbarActionBar.this.mDecorToolbar.setMenuPrepared();
                com.android.internal.app.ToolbarActionBar.this.mToolbarMenuPrepared = true;
            }
            return onPreparePanel;
        }

        @Override // android.view.WindowCallbackWrapper, android.view.Window.Callback
        public android.view.View onCreatePanelView(int i) {
            if (i == 0) {
                return new android.view.View(com.android.internal.app.ToolbarActionBar.this.mDecorToolbar.getContext());
            }
            return super.onCreatePanelView(i);
        }
    }

    private final class ActionMenuPresenterCallback implements com.android.internal.view.menu.MenuPresenter.Callback {
        private boolean mClosingActionMenu;

        private ActionMenuPresenterCallback() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(com.android.internal.view.menu.MenuBuilder menuBuilder) {
            if (com.android.internal.app.ToolbarActionBar.this.mWindowCallback != null) {
                com.android.internal.app.ToolbarActionBar.this.mWindowCallback.onMenuOpened(8, menuBuilder);
                return true;
            }
            return false;
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
            if (this.mClosingActionMenu) {
                return;
            }
            this.mClosingActionMenu = true;
            com.android.internal.app.ToolbarActionBar.this.mDecorToolbar.dismissPopupMenus();
            if (com.android.internal.app.ToolbarActionBar.this.mWindowCallback != null) {
                com.android.internal.app.ToolbarActionBar.this.mWindowCallback.onPanelClosed(8, menuBuilder);
            }
            this.mClosingActionMenu = false;
        }
    }

    private final class MenuBuilderCallback implements com.android.internal.view.menu.MenuBuilder.Callback {
        private MenuBuilderCallback() {
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.MenuItem menuItem) {
            return false;
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(com.android.internal.view.menu.MenuBuilder menuBuilder) {
            if (com.android.internal.app.ToolbarActionBar.this.mWindowCallback != null) {
                if (com.android.internal.app.ToolbarActionBar.this.mDecorToolbar.isOverflowMenuShowing()) {
                    com.android.internal.app.ToolbarActionBar.this.mWindowCallback.onPanelClosed(8, menuBuilder);
                } else if (com.android.internal.app.ToolbarActionBar.this.mWindowCallback.onPreparePanel(0, null, menuBuilder)) {
                    com.android.internal.app.ToolbarActionBar.this.mWindowCallback.onMenuOpened(8, menuBuilder);
                }
            }
        }
    }
}
