package com.android.internal.view.menu;

/* loaded from: classes5.dex */
public interface MenuPresenter {

    public interface Callback {
        void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z);

        boolean onOpenSubMenu(com.android.internal.view.menu.MenuBuilder menuBuilder);
    }

    boolean collapseItemActionView(com.android.internal.view.menu.MenuBuilder menuBuilder, com.android.internal.view.menu.MenuItemImpl menuItemImpl);

    boolean expandItemActionView(com.android.internal.view.menu.MenuBuilder menuBuilder, com.android.internal.view.menu.MenuItemImpl menuItemImpl);

    boolean flagActionItems();

    int getId();

    com.android.internal.view.menu.MenuView getMenuView(android.view.ViewGroup viewGroup);

    void initForMenu(android.content.Context context, com.android.internal.view.menu.MenuBuilder menuBuilder);

    void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z);

    void onRestoreInstanceState(android.os.Parcelable parcelable);

    android.os.Parcelable onSaveInstanceState();

    boolean onSubMenuSelected(com.android.internal.view.menu.SubMenuBuilder subMenuBuilder);

    void setCallback(com.android.internal.view.menu.MenuPresenter.Callback callback);

    void updateMenuView(boolean z);
}
