package com.android.internal.view.menu;

/* loaded from: classes5.dex */
public class SubMenuBuilder extends com.android.internal.view.menu.MenuBuilder implements android.view.SubMenu {
    private com.android.internal.view.menu.MenuItemImpl mItem;
    private com.android.internal.view.menu.MenuBuilder mParentMenu;

    public SubMenuBuilder(android.content.Context context, com.android.internal.view.menu.MenuBuilder menuBuilder, com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        super(context);
        this.mParentMenu = menuBuilder;
        this.mItem = menuItemImpl;
    }

    @Override // com.android.internal.view.menu.MenuBuilder, android.view.Menu
    public void setQwertyMode(boolean z) {
        this.mParentMenu.setQwertyMode(z);
    }

    @Override // com.android.internal.view.menu.MenuBuilder
    public boolean isQwertyMode() {
        return this.mParentMenu.isQwertyMode();
    }

    @Override // com.android.internal.view.menu.MenuBuilder
    public void setShortcutsVisible(boolean z) {
        this.mParentMenu.setShortcutsVisible(z);
    }

    @Override // com.android.internal.view.menu.MenuBuilder
    public boolean isShortcutsVisible() {
        return this.mParentMenu.isShortcutsVisible();
    }

    public android.view.Menu getParentMenu() {
        return this.mParentMenu;
    }

    @Override // android.view.SubMenu
    public android.view.MenuItem getItem() {
        return this.mItem;
    }

    @Override // com.android.internal.view.menu.MenuBuilder
    public void setCallback(com.android.internal.view.menu.MenuBuilder.Callback callback) {
        this.mParentMenu.setCallback(callback);
    }

    @Override // com.android.internal.view.menu.MenuBuilder
    public com.android.internal.view.menu.MenuBuilder getRootMenu() {
        return this.mParentMenu.getRootMenu();
    }

    @Override // com.android.internal.view.menu.MenuBuilder
    boolean dispatchMenuItemSelected(com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.MenuItem menuItem) {
        return super.dispatchMenuItemSelected(menuBuilder, menuItem) || this.mParentMenu.dispatchMenuItemSelected(menuBuilder, menuItem);
    }

    @Override // android.view.SubMenu
    public android.view.SubMenu setIcon(android.graphics.drawable.Drawable drawable) {
        this.mItem.setIcon(drawable);
        return this;
    }

    @Override // android.view.SubMenu
    public android.view.SubMenu setIcon(int i) {
        this.mItem.setIcon(i);
        return this;
    }

    @Override // android.view.SubMenu
    public android.view.SubMenu setHeaderIcon(android.graphics.drawable.Drawable drawable) {
        return (android.view.SubMenu) super.setHeaderIconInt(drawable);
    }

    @Override // android.view.SubMenu
    public android.view.SubMenu setHeaderIcon(int i) {
        return (android.view.SubMenu) super.setHeaderIconInt(i);
    }

    @Override // android.view.SubMenu
    public android.view.SubMenu setHeaderTitle(java.lang.CharSequence charSequence) {
        return (android.view.SubMenu) super.setHeaderTitleInt(charSequence);
    }

    @Override // android.view.SubMenu
    public android.view.SubMenu setHeaderTitle(int i) {
        return (android.view.SubMenu) super.setHeaderTitleInt(i);
    }

    @Override // android.view.SubMenu
    public android.view.SubMenu setHeaderView(android.view.View view) {
        return (android.view.SubMenu) super.setHeaderViewInt(view);
    }

    @Override // com.android.internal.view.menu.MenuBuilder
    public boolean expandItemActionView(com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        return this.mParentMenu.expandItemActionView(menuItemImpl);
    }

    @Override // com.android.internal.view.menu.MenuBuilder
    public boolean collapseItemActionView(com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        return this.mParentMenu.collapseItemActionView(menuItemImpl);
    }

    @Override // com.android.internal.view.menu.MenuBuilder
    public java.lang.String getActionViewStatesKey() {
        int itemId = this.mItem != null ? this.mItem.getItemId() : 0;
        if (itemId == 0) {
            return null;
        }
        return super.getActionViewStatesKey() + ":" + itemId;
    }

    @Override // com.android.internal.view.menu.MenuBuilder, android.view.Menu
    public void setGroupDividerEnabled(boolean z) {
        this.mParentMenu.setGroupDividerEnabled(z);
    }

    @Override // com.android.internal.view.menu.MenuBuilder
    public boolean isGroupDividerEnabled() {
        return this.mParentMenu.isGroupDividerEnabled();
    }
}
