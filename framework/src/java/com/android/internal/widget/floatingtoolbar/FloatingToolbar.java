package com.android.internal.widget.floatingtoolbar;

/* loaded from: classes5.dex */
public final class FloatingToolbar {
    public static final java.lang.String FLOATING_TOOLBAR_TAG = "floating_toolbar";
    private static final android.view.MenuItem.OnMenuItemClickListener NO_OP_MENUITEM_CLICK_LISTENER = new android.view.MenuItem.OnMenuItemClickListener() { // from class: com.android.internal.widget.floatingtoolbar.FloatingToolbar$$ExternalSyntheticLambda1
        @Override // android.view.MenuItem.OnMenuItemClickListener
        public final boolean onMenuItemClick(android.view.MenuItem menuItem) {
            return com.android.internal.widget.floatingtoolbar.FloatingToolbar.lambda$static$0(menuItem);
        }
    };
    private android.view.Menu mMenu;
    private final com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup mPopup;
    private final android.view.Window mWindow;
    private final android.graphics.Rect mContentRect = new android.graphics.Rect();
    private android.view.MenuItem.OnMenuItemClickListener mMenuItemClickListener = NO_OP_MENUITEM_CLICK_LISTENER;
    private final android.view.View.OnLayoutChangeListener mOrientationChangeHandler = new android.view.View.OnLayoutChangeListener() { // from class: com.android.internal.widget.floatingtoolbar.FloatingToolbar.1
        private final android.graphics.Rect mNewRect = new android.graphics.Rect();
        private final android.graphics.Rect mOldRect = new android.graphics.Rect();

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(android.view.View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.mNewRect.set(i, i2, i3, i4);
            this.mOldRect.set(i5, i6, i7, i8);
            if (com.android.internal.widget.floatingtoolbar.FloatingToolbar.this.mPopup.isShowing() && !this.mNewRect.equals(this.mOldRect)) {
                com.android.internal.widget.floatingtoolbar.FloatingToolbar.this.mPopup.setWidthChanged(true);
                com.android.internal.widget.floatingtoolbar.FloatingToolbar.this.updateLayout();
            }
        }
    };
    private final java.util.Comparator<android.view.MenuItem> mMenuItemComparator = new java.util.Comparator() { // from class: com.android.internal.widget.floatingtoolbar.FloatingToolbar$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
            return com.android.internal.widget.floatingtoolbar.FloatingToolbar.lambda$new$1((android.view.MenuItem) obj, (android.view.MenuItem) obj2);
        }
    };

    static /* synthetic */ boolean lambda$static$0(android.view.MenuItem menuItem) {
        return false;
    }

    static /* synthetic */ int lambda$new$1(android.view.MenuItem menuItem, android.view.MenuItem menuItem2) {
        if (menuItem.getItemId() == 16908353) {
            return menuItem2.getItemId() == 16908353 ? 0 : -1;
        }
        if (menuItem2.getItemId() == 16908353) {
            return 1;
        }
        if (menuItem.requiresActionButton()) {
            return menuItem2.requiresActionButton() ? 0 : -1;
        }
        if (menuItem2.requiresActionButton()) {
            return 1;
        }
        if (menuItem.requiresOverflow()) {
            return !menuItem2.requiresOverflow() ? 1 : 0;
        }
        if (menuItem2.requiresOverflow()) {
            return -1;
        }
        return menuItem.getOrder() - menuItem2.getOrder();
    }

    public FloatingToolbar(android.view.Window window) {
        this.mWindow = (android.view.Window) java.util.Objects.requireNonNull(window);
        this.mPopup = com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup.createInstance(window.getContext(), window.getDecorView());
    }

    public com.android.internal.widget.floatingtoolbar.FloatingToolbar setMenu(android.view.Menu menu) {
        this.mMenu = (android.view.Menu) java.util.Objects.requireNonNull(menu);
        return this;
    }

    public com.android.internal.widget.floatingtoolbar.FloatingToolbar setOnMenuItemClickListener(android.view.MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        if (onMenuItemClickListener != null) {
            this.mMenuItemClickListener = onMenuItemClickListener;
        } else {
            this.mMenuItemClickListener = NO_OP_MENUITEM_CLICK_LISTENER;
        }
        return this;
    }

    public com.android.internal.widget.floatingtoolbar.FloatingToolbar setContentRect(android.graphics.Rect rect) {
        this.mContentRect.set((android.graphics.Rect) java.util.Objects.requireNonNull(rect));
        return this;
    }

    public com.android.internal.widget.floatingtoolbar.FloatingToolbar setSuggestedWidth(int i) {
        this.mPopup.setSuggestedWidth(i);
        return this;
    }

    public com.android.internal.widget.floatingtoolbar.FloatingToolbar show() {
        registerOrientationHandler();
        doShow();
        return this;
    }

    public com.android.internal.widget.floatingtoolbar.FloatingToolbar updateLayout() {
        if (this.mPopup.isShowing()) {
            doShow();
        }
        return this;
    }

    public void dismiss() {
        unregisterOrientationHandler();
        this.mPopup.dismiss();
    }

    public void hide() {
        this.mPopup.hide();
    }

    public boolean isShowing() {
        return this.mPopup.isShowing();
    }

    public boolean isHidden() {
        return this.mPopup.isHidden();
    }

    public void setOutsideTouchable(boolean z, android.widget.PopupWindow.OnDismissListener onDismissListener) {
        this.mPopup.setOutsideTouchable(z, onDismissListener);
    }

    private void doShow() {
        java.util.List<android.view.MenuItem> visibleAndEnabledMenuItems = getVisibleAndEnabledMenuItems(this.mMenu);
        visibleAndEnabledMenuItems.sort(this.mMenuItemComparator);
        this.mPopup.show(visibleAndEnabledMenuItems, this.mMenuItemClickListener, this.mContentRect);
    }

    private static java.util.List<android.view.MenuItem> getVisibleAndEnabledMenuItems(android.view.Menu menu) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; menu != null && i < menu.size(); i++) {
            android.view.MenuItem item = menu.getItem(i);
            if (item.isVisible() && item.isEnabled()) {
                android.view.SubMenu subMenu = item.getSubMenu();
                if (subMenu != null) {
                    arrayList.addAll(getVisibleAndEnabledMenuItems(subMenu));
                } else {
                    arrayList.add(item);
                }
            }
        }
        return arrayList;
    }

    private void registerOrientationHandler() {
        unregisterOrientationHandler();
        this.mWindow.getDecorView().addOnLayoutChangeListener(this.mOrientationChangeHandler);
    }

    private void unregisterOrientationHandler() {
        this.mWindow.getDecorView().removeOnLayoutChangeListener(this.mOrientationChangeHandler);
    }
}
