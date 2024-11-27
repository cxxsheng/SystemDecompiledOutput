package com.android.internal.view.menu;

/* loaded from: classes5.dex */
public interface MenuView {

    public interface ItemView {
        com.android.internal.view.menu.MenuItemImpl getItemData();

        void initialize(com.android.internal.view.menu.MenuItemImpl menuItemImpl, int i);

        boolean prefersCondensedTitle();

        void setCheckable(boolean z);

        void setChecked(boolean z);

        void setEnabled(boolean z);

        void setIcon(android.graphics.drawable.Drawable drawable);

        void setShortcut(boolean z, char c);

        void setTitle(java.lang.CharSequence charSequence);

        boolean showsIcon();
    }

    int getWindowAnimations();

    void initialize(com.android.internal.view.menu.MenuBuilder menuBuilder);
}
