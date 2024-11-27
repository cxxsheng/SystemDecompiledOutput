package android.widget;

/* loaded from: classes4.dex */
public class MenuPopupWindow extends android.widget.ListPopupWindow implements android.widget.MenuItemHoverListener {
    private android.widget.MenuItemHoverListener mHoverListener;

    public MenuPopupWindow(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.ListPopupWindow
    android.widget.DropDownListView createDropDownListView(android.content.Context context, boolean z) {
        android.widget.MenuPopupWindow.MenuDropDownListView menuDropDownListView = new android.widget.MenuPopupWindow.MenuDropDownListView(context, z);
        menuDropDownListView.setHoverListener(this);
        return menuDropDownListView;
    }

    public void setEnterTransition(android.transition.Transition transition) {
        this.mPopup.setEnterTransition(transition);
    }

    public void setExitTransition(android.transition.Transition transition) {
        this.mPopup.setExitTransition(transition);
    }

    public void setHoverListener(android.widget.MenuItemHoverListener menuItemHoverListener) {
        this.mHoverListener = menuItemHoverListener;
    }

    public void setTouchModal(boolean z) {
        this.mPopup.setTouchModal(z);
    }

    @Override // android.widget.MenuItemHoverListener
    public void onItemHoverEnter(com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.MenuItem menuItem) {
        if (this.mHoverListener != null) {
            this.mHoverListener.onItemHoverEnter(menuBuilder, menuItem);
        }
    }

    @Override // android.widget.MenuItemHoverListener
    public void onItemHoverExit(com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.MenuItem menuItem) {
        if (this.mHoverListener != null) {
            this.mHoverListener.onItemHoverExit(menuBuilder, menuItem);
        }
    }

    public static class MenuDropDownListView extends android.widget.DropDownListView {
        final int mAdvanceKey;
        private android.widget.MenuItemHoverListener mHoverListener;
        private android.view.MenuItem mHoveredMenuItem;
        final int mRetreatKey;

        public MenuDropDownListView(android.content.Context context, boolean z) {
            super(context, z);
            if (context.getResources().getConfiguration().getLayoutDirection() == 1) {
                this.mAdvanceKey = 21;
                this.mRetreatKey = 22;
            } else {
                this.mAdvanceKey = 22;
                this.mRetreatKey = 21;
            }
        }

        public void setHoverListener(android.widget.MenuItemHoverListener menuItemHoverListener) {
            this.mHoverListener = menuItemHoverListener;
        }

        public void clearSelection() {
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
        }

        @Override // android.widget.ListView, android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
        public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
            com.android.internal.view.menu.ListMenuItemView listMenuItemView = (com.android.internal.view.menu.ListMenuItemView) getSelectedView();
            if (listMenuItemView != null && i == this.mAdvanceKey) {
                if (listMenuItemView.isEnabled() && listMenuItemView.getItemData().hasSubMenu()) {
                    performItemClick(listMenuItemView, getSelectedItemPosition(), getSelectedItemId());
                }
                return true;
            }
            if (listMenuItemView != null && i == this.mRetreatKey) {
                setSelectedPositionInt(-1);
                setNextSelectedPositionInt(-1);
                ((com.android.internal.view.menu.MenuAdapter) getAdapter()).getAdapterMenu().close(false);
                return true;
            }
            return super.onKeyDown(i, keyEvent);
        }

        @Override // android.widget.DropDownListView, android.view.View
        public boolean onHoverEvent(android.view.MotionEvent motionEvent) {
            com.android.internal.view.menu.MenuAdapter menuAdapter;
            int i;
            com.android.internal.view.menu.MenuItemImpl menuItemImpl;
            int pointToPosition;
            int i2;
            if (this.mHoverListener != null) {
                android.widget.ListAdapter adapter = getAdapter();
                if (adapter instanceof android.widget.HeaderViewListAdapter) {
                    android.widget.HeaderViewListAdapter headerViewListAdapter = (android.widget.HeaderViewListAdapter) adapter;
                    i = headerViewListAdapter.getHeadersCount();
                    menuAdapter = (com.android.internal.view.menu.MenuAdapter) headerViewListAdapter.getWrappedAdapter();
                } else {
                    menuAdapter = (com.android.internal.view.menu.MenuAdapter) adapter;
                    i = 0;
                }
                if (motionEvent.getAction() != 10 && (pointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY())) != -1 && (i2 = pointToPosition - i) >= 0 && i2 < menuAdapter.getCount()) {
                    menuItemImpl = menuAdapter.getItem(i2);
                } else {
                    menuItemImpl = null;
                }
                android.view.MenuItem menuItem = this.mHoveredMenuItem;
                if (menuItem != menuItemImpl) {
                    com.android.internal.view.menu.MenuBuilder adapterMenu = menuAdapter.getAdapterMenu();
                    if (menuItem != null) {
                        this.mHoverListener.onItemHoverExit(adapterMenu, menuItem);
                    }
                    this.mHoveredMenuItem = menuItemImpl;
                    if (menuItemImpl != null) {
                        this.mHoverListener.onItemHoverEnter(adapterMenu, menuItemImpl);
                    }
                }
            }
            return super.onHoverEvent(motionEvent);
        }
    }
}
