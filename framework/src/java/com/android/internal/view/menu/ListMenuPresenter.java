package com.android.internal.view.menu;

/* loaded from: classes5.dex */
public class ListMenuPresenter implements com.android.internal.view.menu.MenuPresenter, android.widget.AdapterView.OnItemClickListener {
    private static final java.lang.String TAG = "ListMenuPresenter";
    public static final java.lang.String VIEWS_TAG = "android:menu:list";
    com.android.internal.view.menu.ListMenuPresenter.MenuAdapter mAdapter;
    private com.android.internal.view.menu.MenuPresenter.Callback mCallback;
    android.content.Context mContext;
    private int mId;
    android.view.LayoutInflater mInflater;
    private int mItemIndexOffset;
    int mItemLayoutRes;
    com.android.internal.view.menu.MenuBuilder mMenu;
    com.android.internal.view.menu.ExpandedMenuView mMenuView;
    int mThemeRes;

    public ListMenuPresenter(android.content.Context context, int i) {
        this(i, 0);
        this.mContext = context;
        this.mInflater = android.view.LayoutInflater.from(this.mContext);
    }

    public ListMenuPresenter(int i, int i2) {
        this.mItemLayoutRes = i;
        this.mThemeRes = i2;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void initForMenu(android.content.Context context, com.android.internal.view.menu.MenuBuilder menuBuilder) {
        if (this.mThemeRes != 0) {
            this.mContext = new android.view.ContextThemeWrapper(context, this.mThemeRes);
            this.mInflater = android.view.LayoutInflater.from(this.mContext);
        } else if (this.mContext != null) {
            this.mContext = context;
            if (this.mInflater == null) {
                this.mInflater = android.view.LayoutInflater.from(this.mContext);
            }
        }
        this.mMenu = menuBuilder;
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public com.android.internal.view.menu.MenuView getMenuView(android.view.ViewGroup viewGroup) {
        if (this.mMenuView == null) {
            this.mMenuView = (com.android.internal.view.menu.ExpandedMenuView) this.mInflater.inflate(com.android.internal.R.layout.expanded_menu_layout, viewGroup, false);
            if (this.mAdapter == null) {
                this.mAdapter = new com.android.internal.view.menu.ListMenuPresenter.MenuAdapter();
            }
            this.mMenuView.setAdapter((android.widget.ListAdapter) this.mAdapter);
            this.mMenuView.setOnItemClickListener(this);
        }
        return this.mMenuView;
    }

    public android.widget.ListAdapter getAdapter() {
        if (this.mAdapter == null) {
            this.mAdapter = new com.android.internal.view.menu.ListMenuPresenter.MenuAdapter();
        }
        return this.mAdapter;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void setCallback(com.android.internal.view.menu.MenuPresenter.Callback callback) {
        this.mCallback = callback;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public boolean onSubMenuSelected(com.android.internal.view.menu.SubMenuBuilder subMenuBuilder) {
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        new com.android.internal.view.menu.MenuDialogHelper(subMenuBuilder).show(null);
        if (this.mCallback != null) {
            this.mCallback.onOpenSubMenu(subMenuBuilder);
            return true;
        }
        return true;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu(menuBuilder, z);
        }
    }

    int getItemIndexOffset() {
        return this.mItemIndexOffset;
    }

    public void setItemIndexOffset(int i) {
        this.mItemIndexOffset = i;
        if (this.mMenuView != null) {
            updateMenuView(false);
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
        this.mMenu.performItemAction(this.mAdapter.getItem(i), this, 0);
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

    public void saveHierarchyState(android.os.Bundle bundle) {
        android.util.SparseArray<android.os.Parcelable> sparseArray = new android.util.SparseArray<>();
        if (this.mMenuView != null) {
            this.mMenuView.saveHierarchyState(sparseArray);
        }
        bundle.putSparseParcelableArray(VIEWS_TAG, sparseArray);
    }

    public void restoreHierarchyState(android.os.Bundle bundle) {
        android.util.SparseArray<android.os.Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(VIEWS_TAG);
        if (sparseParcelableArray != null) {
            this.mMenuView.restoreHierarchyState(sparseParcelableArray);
        }
    }

    public void setId(int i) {
        this.mId = i;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public int getId() {
        return this.mId;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public android.os.Parcelable onSaveInstanceState() {
        if (this.mMenuView == null) {
            return null;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        saveHierarchyState(bundle);
        return bundle;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        restoreHierarchyState((android.os.Bundle) parcelable);
    }

    private class MenuAdapter extends android.widget.BaseAdapter {
        private int mExpandedIndex = -1;

        public MenuAdapter() {
            findExpandedIndex();
        }

        @Override // android.widget.Adapter
        public int getCount() {
            int size = com.android.internal.view.menu.ListMenuPresenter.this.mMenu.getNonActionItems().size() - com.android.internal.view.menu.ListMenuPresenter.this.mItemIndexOffset;
            if (this.mExpandedIndex < 0) {
                return size;
            }
            return size - 1;
        }

        @Override // android.widget.Adapter
        public com.android.internal.view.menu.MenuItemImpl getItem(int i) {
            java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> nonActionItems = com.android.internal.view.menu.ListMenuPresenter.this.mMenu.getNonActionItems();
            int i2 = i + com.android.internal.view.menu.ListMenuPresenter.this.mItemIndexOffset;
            if (this.mExpandedIndex >= 0 && i2 >= this.mExpandedIndex) {
                i2++;
            }
            return nonActionItems.get(i2);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            if (view == null) {
                view = com.android.internal.view.menu.ListMenuPresenter.this.mInflater.inflate(com.android.internal.view.menu.ListMenuPresenter.this.mItemLayoutRes, viewGroup, false);
            }
            ((com.android.internal.view.menu.MenuView.ItemView) view).initialize(getItem(i), 0);
            return view;
        }

        void findExpandedIndex() {
            com.android.internal.view.menu.MenuItemImpl expandedItem = com.android.internal.view.menu.ListMenuPresenter.this.mMenu.getExpandedItem();
            if (expandedItem != null) {
                java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> nonActionItems = com.android.internal.view.menu.ListMenuPresenter.this.mMenu.getNonActionItems();
                int size = nonActionItems.size();
                for (int i = 0; i < size; i++) {
                    if (nonActionItems.get(i) == expandedItem) {
                        this.mExpandedIndex = i;
                        return;
                    }
                }
            }
            this.mExpandedIndex = -1;
        }

        @Override // android.widget.BaseAdapter
        public void notifyDataSetChanged() {
            findExpandedIndex();
            super.notifyDataSetChanged();
        }
    }
}
