package com.android.internal.view.menu;

/* loaded from: classes5.dex */
public class IconMenuPresenter extends com.android.internal.view.menu.BaseMenuPresenter {
    private static final java.lang.String OPEN_SUBMENU_KEY = "android:menu:icon:submenu";
    private static final java.lang.String VIEWS_TAG = "android:menu:icon";
    private int mMaxItems;
    private com.android.internal.view.menu.IconMenuItemView mMoreView;
    com.android.internal.view.menu.MenuDialogHelper mOpenSubMenu;
    int mOpenSubMenuId;
    com.android.internal.view.menu.IconMenuPresenter.SubMenuPresenterCallback mSubMenuPresenterCallback;

    public IconMenuPresenter(android.content.Context context) {
        super(new android.view.ContextThemeWrapper(context, com.android.internal.R.style.Theme_IconMenu), com.android.internal.R.layout.icon_menu_layout, com.android.internal.R.layout.icon_menu_item_layout);
        this.mMaxItems = -1;
        this.mSubMenuPresenterCallback = new com.android.internal.view.menu.IconMenuPresenter.SubMenuPresenterCallback();
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public void initForMenu(android.content.Context context, com.android.internal.view.menu.MenuBuilder menuBuilder) {
        super.initForMenu(context, menuBuilder);
        this.mMaxItems = -1;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    public void bindItemView(com.android.internal.view.menu.MenuItemImpl menuItemImpl, com.android.internal.view.menu.MenuView.ItemView itemView) {
        com.android.internal.view.menu.IconMenuItemView iconMenuItemView = (com.android.internal.view.menu.IconMenuItemView) itemView;
        iconMenuItemView.setItemData(menuItemImpl);
        iconMenuItemView.initialize(menuItemImpl.getTitleForItemView(iconMenuItemView), menuItemImpl.getIcon());
        iconMenuItemView.setVisibility(menuItemImpl.isVisible() ? 0 : 8);
        iconMenuItemView.setEnabled(iconMenuItemView.isEnabled());
        iconMenuItemView.setLayoutParams(iconMenuItemView.getTextAppropriateLayoutParams());
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    public boolean shouldIncludeItem(int i, com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        return ((this.mMenu.getNonActionItems().size() == this.mMaxItems && i < this.mMaxItems) || i < this.mMaxItems - 1) && !menuItemImpl.isActionButton();
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    protected void addItemView(android.view.View view, int i) {
        com.android.internal.view.menu.IconMenuItemView iconMenuItemView = (com.android.internal.view.menu.IconMenuItemView) view;
        com.android.internal.view.menu.IconMenuView iconMenuView = (com.android.internal.view.menu.IconMenuView) this.mMenuView;
        iconMenuItemView.setIconMenuView(iconMenuView);
        iconMenuItemView.setItemInvoker(iconMenuView);
        iconMenuItemView.setBackgroundDrawable(iconMenuView.getItemBackgroundDrawable());
        super.addItemView(view, i);
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public boolean onSubMenuSelected(com.android.internal.view.menu.SubMenuBuilder subMenuBuilder) {
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        com.android.internal.view.menu.MenuDialogHelper menuDialogHelper = new com.android.internal.view.menu.MenuDialogHelper(subMenuBuilder);
        menuDialogHelper.setPresenterCallback(this.mSubMenuPresenterCallback);
        menuDialogHelper.show(null);
        this.mOpenSubMenu = menuDialogHelper;
        this.mOpenSubMenuId = subMenuBuilder.getItem().getItemId();
        super.onSubMenuSelected(subMenuBuilder);
        return true;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        com.android.internal.view.menu.IconMenuView iconMenuView = (com.android.internal.view.menu.IconMenuView) this.mMenuView;
        if (this.mMaxItems < 0) {
            this.mMaxItems = iconMenuView.getMaxItems();
        }
        java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> nonActionItems = this.mMenu.getNonActionItems();
        boolean z2 = nonActionItems.size() > this.mMaxItems;
        super.updateMenuView(z);
        if (z2 && (this.mMoreView == null || this.mMoreView.getParent() != iconMenuView)) {
            if (this.mMoreView == null) {
                this.mMoreView = iconMenuView.createMoreItemView();
                this.mMoreView.setBackgroundDrawable(iconMenuView.getItemBackgroundDrawable());
            }
            iconMenuView.addView(this.mMoreView);
        } else if (!z2 && this.mMoreView != null) {
            iconMenuView.removeView(this.mMoreView);
        }
        iconMenuView.setNumActualItemsShown(z2 ? this.mMaxItems - 1 : nonActionItems.size());
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    protected boolean filterLeftoverView(android.view.ViewGroup viewGroup, int i) {
        if (viewGroup.getChildAt(i) != this.mMoreView) {
            return super.filterLeftoverView(viewGroup, i);
        }
        return false;
    }

    public int getNumActualItemsShown() {
        return ((com.android.internal.view.menu.IconMenuView) this.mMenuView).getNumActualItemsShown();
    }

    public void saveHierarchyState(android.os.Bundle bundle) {
        android.util.SparseArray<android.os.Parcelable> sparseArray = new android.util.SparseArray<>();
        if (this.mMenuView != null) {
            ((android.view.View) this.mMenuView).saveHierarchyState(sparseArray);
        }
        bundle.putSparseParcelableArray(VIEWS_TAG, sparseArray);
    }

    public void restoreHierarchyState(android.os.Bundle bundle) {
        android.view.MenuItem findItem;
        android.util.SparseArray<android.os.Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(VIEWS_TAG);
        if (sparseParcelableArray != null) {
            ((android.view.View) this.mMenuView).restoreHierarchyState(sparseParcelableArray);
        }
        int i = bundle.getInt(OPEN_SUBMENU_KEY, 0);
        if (i > 0 && this.mMenu != null && (findItem = this.mMenu.findItem(i)) != null) {
            onSubMenuSelected((com.android.internal.view.menu.SubMenuBuilder) findItem.getSubMenu());
        }
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public android.os.Parcelable onSaveInstanceState() {
        if (this.mMenuView == null) {
            return null;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        saveHierarchyState(bundle);
        if (this.mOpenSubMenuId > 0) {
            bundle.putInt(OPEN_SUBMENU_KEY, this.mOpenSubMenuId);
        }
        return bundle;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        restoreHierarchyState((android.os.Bundle) parcelable);
    }

    class SubMenuPresenterCallback implements com.android.internal.view.menu.MenuPresenter.Callback {
        SubMenuPresenterCallback() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
            com.android.internal.view.menu.IconMenuPresenter.this.mOpenSubMenuId = 0;
            if (com.android.internal.view.menu.IconMenuPresenter.this.mOpenSubMenu != null) {
                com.android.internal.view.menu.IconMenuPresenter.this.mOpenSubMenu.dismiss();
                com.android.internal.view.menu.IconMenuPresenter.this.mOpenSubMenu = null;
            }
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(com.android.internal.view.menu.MenuBuilder menuBuilder) {
            if (menuBuilder != null) {
                com.android.internal.view.menu.IconMenuPresenter.this.mOpenSubMenuId = ((com.android.internal.view.menu.SubMenuBuilder) menuBuilder).getItem().getItemId();
                return false;
            }
            return false;
        }
    }
}
