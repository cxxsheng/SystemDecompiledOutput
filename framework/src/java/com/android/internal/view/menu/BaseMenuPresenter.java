package com.android.internal.view.menu;

/* loaded from: classes5.dex */
public abstract class BaseMenuPresenter implements com.android.internal.view.menu.MenuPresenter {
    private com.android.internal.view.menu.MenuPresenter.Callback mCallback;
    protected android.content.Context mContext;
    private int mId;
    protected android.view.LayoutInflater mInflater;
    private int mItemLayoutRes;
    protected com.android.internal.view.menu.MenuBuilder mMenu;
    private int mMenuLayoutRes;
    protected com.android.internal.view.menu.MenuView mMenuView;
    protected android.content.Context mSystemContext;
    protected android.view.LayoutInflater mSystemInflater;

    public abstract void bindItemView(com.android.internal.view.menu.MenuItemImpl menuItemImpl, com.android.internal.view.menu.MenuView.ItemView itemView);

    public BaseMenuPresenter(android.content.Context context, int i, int i2) {
        this.mSystemContext = context;
        this.mSystemInflater = android.view.LayoutInflater.from(context);
        this.mMenuLayoutRes = i;
        this.mItemLayoutRes = i2;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void initForMenu(android.content.Context context, com.android.internal.view.menu.MenuBuilder menuBuilder) {
        this.mContext = context;
        this.mInflater = android.view.LayoutInflater.from(this.mContext);
        this.mMenu = menuBuilder;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public com.android.internal.view.menu.MenuView getMenuView(android.view.ViewGroup viewGroup) {
        if (this.mMenuView == null) {
            this.mMenuView = (com.android.internal.view.menu.MenuView) this.mSystemInflater.inflate(this.mMenuLayoutRes, viewGroup, false);
            this.mMenuView.initialize(this.mMenu);
            updateMenuView(true);
        }
        return this.mMenuView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.internal.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) this.mMenuView;
        if (viewGroup == null) {
            return;
        }
        int i = 0;
        if (this.mMenu != null) {
            this.mMenu.flagActionItems();
            java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> visibleItems = this.mMenu.getVisibleItems();
            int size = visibleItems.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                com.android.internal.view.menu.MenuItemImpl menuItemImpl = visibleItems.get(i3);
                if (shouldIncludeItem(i2, menuItemImpl)) {
                    android.view.View childAt = viewGroup.getChildAt(i2);
                    com.android.internal.view.menu.MenuItemImpl itemData = childAt instanceof com.android.internal.view.menu.MenuView.ItemView ? ((com.android.internal.view.menu.MenuView.ItemView) childAt).getItemData() : null;
                    android.view.View itemView = getItemView(menuItemImpl, childAt, viewGroup);
                    if (menuItemImpl != itemData) {
                        itemView.setPressed(false);
                        itemView.jumpDrawablesToCurrentState();
                    }
                    if (itemView != childAt) {
                        addItemView(itemView, i2);
                    }
                    i2++;
                }
            }
            i = i2;
        }
        while (i < viewGroup.getChildCount()) {
            if (!filterLeftoverView(viewGroup, i)) {
                i++;
            }
        }
    }

    protected void addItemView(android.view.View view, int i) {
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        ((android.view.ViewGroup) this.mMenuView).addView(view, i);
    }

    protected boolean filterLeftoverView(android.view.ViewGroup viewGroup, int i) {
        viewGroup.removeViewAt(i);
        return true;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void setCallback(com.android.internal.view.menu.MenuPresenter.Callback callback) {
        this.mCallback = callback;
    }

    public com.android.internal.view.menu.MenuPresenter.Callback getCallback() {
        return this.mCallback;
    }

    public com.android.internal.view.menu.MenuView.ItemView createItemView(android.view.ViewGroup viewGroup) {
        return (com.android.internal.view.menu.MenuView.ItemView) this.mSystemInflater.inflate(this.mItemLayoutRes, viewGroup, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public android.view.View getItemView(com.android.internal.view.menu.MenuItemImpl menuItemImpl, android.view.View view, android.view.ViewGroup viewGroup) {
        com.android.internal.view.menu.MenuView.ItemView itemView;
        if (view instanceof com.android.internal.view.menu.MenuView.ItemView) {
            itemView = (com.android.internal.view.menu.MenuView.ItemView) view;
        } else {
            itemView = createItemView(viewGroup);
        }
        bindItemView(menuItemImpl, itemView);
        return (android.view.View) itemView;
    }

    public boolean shouldIncludeItem(int i, com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        return true;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu(menuBuilder, z);
        }
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public boolean onSubMenuSelected(com.android.internal.view.menu.SubMenuBuilder subMenuBuilder) {
        if (this.mCallback != null) {
            return this.mCallback.onOpenSubMenu(subMenuBuilder);
        }
        return false;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public boolean flagActionItems() {
        return false;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public boolean expandItemActionView(com.android.internal.view.menu.MenuBuilder menuBuilder, com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public boolean collapseItemActionView(com.android.internal.view.menu.MenuBuilder menuBuilder, com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public int getId() {
        return this.mId;
    }

    public void setId(int i) {
        this.mId = i;
    }
}
