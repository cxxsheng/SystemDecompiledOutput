package com.android.internal.view.menu;

/* loaded from: classes5.dex */
final class CascadingMenuPopup extends com.android.internal.view.menu.MenuPopup implements com.android.internal.view.menu.MenuPresenter, android.view.View.OnKeyListener, android.widget.PopupWindow.OnDismissListener {
    private static final int HORIZ_POSITION_LEFT = 0;
    private static final int HORIZ_POSITION_RIGHT = 1;
    private static final int SUBMENU_TIMEOUT_MS = 200;
    private android.view.View mAnchorView;
    private final android.content.Context mContext;
    private boolean mHasXOffset;
    private boolean mHasYOffset;
    private final int mItemLayout;
    private final int mMenuMaxWidth;
    private android.widget.PopupWindow.OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private com.android.internal.view.menu.MenuPresenter.Callback mPresenterCallback;
    private boolean mShouldCloseImmediately;
    private boolean mShowTitle;
    private android.view.View mShownAnchorView;
    private final android.os.Handler mSubMenuHoverHandler;
    private android.view.ViewTreeObserver mTreeObserver;
    private int mXOffset;
    private int mYOffset;
    private final java.util.List<com.android.internal.view.menu.MenuBuilder> mPendingMenus = new java.util.ArrayList();
    private final java.util.List<com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo> mShowingMenus = new java.util.ArrayList();
    private final android.view.ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new android.view.ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.internal.view.menu.CascadingMenuPopup.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (com.android.internal.view.menu.CascadingMenuPopup.this.isShowing() && com.android.internal.view.menu.CascadingMenuPopup.this.mShowingMenus.size() > 0 && !((com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo) com.android.internal.view.menu.CascadingMenuPopup.this.mShowingMenus.get(0)).window.isModal()) {
                android.view.View view = com.android.internal.view.menu.CascadingMenuPopup.this.mShownAnchorView;
                if (view == null || !view.isShown()) {
                    com.android.internal.view.menu.CascadingMenuPopup.this.dismiss();
                    return;
                }
                java.util.Iterator it = com.android.internal.view.menu.CascadingMenuPopup.this.mShowingMenus.iterator();
                while (it.hasNext()) {
                    ((com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo) it.next()).window.show();
                }
            }
        }
    };
    private final android.view.View.OnAttachStateChangeListener mAttachStateChangeListener = new android.view.View.OnAttachStateChangeListener() { // from class: com.android.internal.view.menu.CascadingMenuPopup.2
        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(android.view.View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(android.view.View view) {
            if (com.android.internal.view.menu.CascadingMenuPopup.this.mTreeObserver != null) {
                if (!com.android.internal.view.menu.CascadingMenuPopup.this.mTreeObserver.isAlive()) {
                    com.android.internal.view.menu.CascadingMenuPopup.this.mTreeObserver = view.getViewTreeObserver();
                }
                com.android.internal.view.menu.CascadingMenuPopup.this.mTreeObserver.removeGlobalOnLayoutListener(com.android.internal.view.menu.CascadingMenuPopup.this.mGlobalLayoutListener);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    };
    private final android.widget.MenuItemHoverListener mMenuItemHoverListener = new android.widget.MenuItemHoverListener() { // from class: com.android.internal.view.menu.CascadingMenuPopup.3
        @Override // android.widget.MenuItemHoverListener
        public void onItemHoverExit(com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.MenuItem menuItem) {
            com.android.internal.view.menu.CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages(menuBuilder);
        }

        @Override // android.widget.MenuItemHoverListener
        public void onItemHoverEnter(final com.android.internal.view.menu.MenuBuilder menuBuilder, final android.view.MenuItem menuItem) {
            com.android.internal.view.menu.CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages(null);
            int size = com.android.internal.view.menu.CascadingMenuPopup.this.mShowingMenus.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    i = -1;
                    break;
                } else if (menuBuilder == ((com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo) com.android.internal.view.menu.CascadingMenuPopup.this.mShowingMenus.get(i)).menu) {
                    break;
                } else {
                    i++;
                }
            }
            if (i == -1) {
                return;
            }
            int i2 = i + 1;
            final com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo cascadingMenuInfo = i2 < com.android.internal.view.menu.CascadingMenuPopup.this.mShowingMenus.size() ? (com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo) com.android.internal.view.menu.CascadingMenuPopup.this.mShowingMenus.get(i2) : null;
            com.android.internal.view.menu.CascadingMenuPopup.this.mSubMenuHoverHandler.postAtTime(new java.lang.Runnable() { // from class: com.android.internal.view.menu.CascadingMenuPopup.3.1
                @Override // java.lang.Runnable
                public void run() {
                    if (cascadingMenuInfo != null) {
                        com.android.internal.view.menu.CascadingMenuPopup.this.mShouldCloseImmediately = true;
                        cascadingMenuInfo.menu.close(false);
                        com.android.internal.view.menu.CascadingMenuPopup.this.mShouldCloseImmediately = false;
                    }
                    if (menuItem.isEnabled() && menuItem.hasSubMenu()) {
                        menuBuilder.performItemAction(menuItem, 0);
                    }
                }
            }, menuBuilder, android.os.SystemClock.uptimeMillis() + 200);
        }
    };
    private int mRawDropDownGravity = 0;
    private int mDropDownGravity = 0;
    private boolean mForceShowIcon = false;
    private int mLastPosition = getInitialMenuPosition();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HorizPosition {
    }

    public CascadingMenuPopup(android.content.Context context, android.view.View view, int i, int i2, boolean z) {
        int i3;
        this.mContext = (android.content.Context) com.android.internal.util.Preconditions.checkNotNull(context);
        this.mAnchorView = (android.view.View) com.android.internal.util.Preconditions.checkNotNull(view);
        this.mPopupStyleAttr = i;
        this.mPopupStyleRes = i2;
        this.mOverflowOnly = z;
        android.content.res.Resources resources = context.getResources();
        this.mMenuMaxWidth = java.lang.Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(com.android.internal.R.dimen.config_prefDialogWidth));
        this.mSubMenuHoverHandler = new android.os.Handler();
        if (android.app.AppGlobals.getIntCoreSetting(android.text.TextFlags.KEY_ENABLE_NEW_CONTEXT_MENU, 1) != 0) {
            i3 = com.android.internal.R.layout.cascading_menu_item_layout_material;
        } else {
            i3 = com.android.internal.R.layout.cascading_menu_item_layout;
        }
        this.mItemLayout = i3;
    }

    @Override // com.android.internal.view.menu.MenuPopup
    public void setForceShowIcon(boolean z) {
        this.mForceShowIcon = z;
    }

    private android.widget.MenuPopupWindow createPopupWindow() {
        android.widget.MenuPopupWindow menuPopupWindow = new android.widget.MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
        menuPopupWindow.setHoverListener(this.mMenuItemHoverListener);
        menuPopupWindow.setOnItemClickListener(this);
        menuPopupWindow.setOnDismissListener(this);
        menuPopupWindow.setAnchorView(this.mAnchorView);
        menuPopupWindow.setDropDownGravity(this.mDropDownGravity);
        menuPopupWindow.setModal(true);
        menuPopupWindow.setInputMethodMode(2);
        return menuPopupWindow;
    }

    @Override // com.android.internal.view.menu.ShowableListMenu
    public void show() {
        if (isShowing()) {
            return;
        }
        java.util.Iterator<com.android.internal.view.menu.MenuBuilder> it = this.mPendingMenus.iterator();
        while (it.hasNext()) {
            showMenu(it.next());
        }
        this.mPendingMenus.clear();
        this.mShownAnchorView = this.mAnchorView;
        if (this.mShownAnchorView != null) {
            boolean z = this.mTreeObserver == null;
            this.mTreeObserver = this.mShownAnchorView.getViewTreeObserver();
            if (z) {
                this.mTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener);
            }
            this.mShownAnchorView.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
        }
    }

    @Override // com.android.internal.view.menu.ShowableListMenu
    public void dismiss() {
        int size = this.mShowingMenus.size();
        if (size > 0) {
            com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo[] cascadingMenuInfoArr = (com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo[]) this.mShowingMenus.toArray(new com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo[size]);
            for (int i = size - 1; i >= 0; i--) {
                com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo cascadingMenuInfo = cascadingMenuInfoArr[i];
                if (cascadingMenuInfo.window.isShowing()) {
                    cascadingMenuInfo.window.dismiss();
                }
            }
        }
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(android.view.View view, int i, android.view.KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1 && i == 82) {
            dismiss();
            return true;
        }
        return false;
    }

    private int getInitialMenuPosition() {
        return this.mAnchorView.getLayoutDirection() == 1 ? 0 : 1;
    }

    private int getNextMenuPosition(int i) {
        android.widget.ListView listView = this.mShowingMenus.get(this.mShowingMenus.size() - 1).getListView();
        int[] iArr = new int[2];
        listView.getLocationOnScreen(iArr);
        android.graphics.Rect rect = new android.graphics.Rect();
        this.mShownAnchorView.getWindowVisibleDisplayFrame(rect);
        return this.mLastPosition == 1 ? (iArr[0] + listView.getWidth()) + i > rect.right ? 0 : 1 : iArr[0] - i < 0 ? 1 : 0;
    }

    @Override // com.android.internal.view.menu.MenuPopup
    public void addMenu(com.android.internal.view.menu.MenuBuilder menuBuilder) {
        menuBuilder.addMenuPresenter(this, this.mContext);
        if (isShowing()) {
            showMenu(menuBuilder);
        } else {
            this.mPendingMenus.add(menuBuilder);
        }
    }

    private void showMenu(com.android.internal.view.menu.MenuBuilder menuBuilder) {
        com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo cascadingMenuInfo;
        android.view.View view;
        android.view.LayoutInflater from = android.view.LayoutInflater.from(this.mContext);
        com.android.internal.view.menu.MenuAdapter menuAdapter = new com.android.internal.view.menu.MenuAdapter(menuBuilder, from, this.mOverflowOnly, this.mItemLayout);
        if (!isShowing() && this.mForceShowIcon) {
            menuAdapter.setForceShowIcon(true);
        } else if (isShowing()) {
            menuAdapter.setForceShowIcon(com.android.internal.view.menu.MenuPopup.shouldPreserveIconSpacing(menuBuilder));
        }
        int measureIndividualMenuWidth = measureIndividualMenuWidth(menuAdapter, null, this.mContext, this.mMenuMaxWidth);
        android.widget.MenuPopupWindow createPopupWindow = createPopupWindow();
        createPopupWindow.setAdapter(menuAdapter);
        createPopupWindow.setContentWidth(measureIndividualMenuWidth);
        createPopupWindow.setDropDownGravity(this.mDropDownGravity);
        if (this.mShowingMenus.size() > 0) {
            cascadingMenuInfo = this.mShowingMenus.get(this.mShowingMenus.size() - 1);
            view = findParentViewForSubmenu(cascadingMenuInfo, menuBuilder);
        } else {
            cascadingMenuInfo = null;
            view = null;
        }
        if (view != null) {
            createPopupWindow.setAnchorView(view);
            createPopupWindow.setTouchModal(false);
            createPopupWindow.setEnterTransition(null);
            int nextMenuPosition = getNextMenuPosition(measureIndividualMenuWidth);
            boolean z = nextMenuPosition == 1;
            this.mLastPosition = nextMenuPosition;
            if ((this.mDropDownGravity & 5) == 5) {
                if (!z) {
                    measureIndividualMenuWidth = -view.getWidth();
                }
            } else if (z) {
                measureIndividualMenuWidth = view.getWidth();
            } else {
                measureIndividualMenuWidth = -measureIndividualMenuWidth;
            }
            createPopupWindow.setHorizontalOffset(measureIndividualMenuWidth);
            createPopupWindow.setOverlapAnchor(true);
            createPopupWindow.setVerticalOffset(0);
        } else {
            if (this.mHasXOffset) {
                createPopupWindow.setHorizontalOffset(this.mXOffset);
            }
            if (this.mHasYOffset) {
                createPopupWindow.setVerticalOffset(this.mYOffset);
            }
            createPopupWindow.setEpicenterBounds(getEpicenterBounds());
        }
        this.mShowingMenus.add(new com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo(createPopupWindow, menuBuilder, this.mLastPosition));
        createPopupWindow.show();
        android.widget.ListView listView = createPopupWindow.getListView();
        listView.setOnKeyListener(this);
        if (cascadingMenuInfo == null && this.mShowTitle && menuBuilder.getHeaderTitle() != null) {
            android.widget.FrameLayout frameLayout = (android.widget.FrameLayout) from.inflate(com.android.internal.R.layout.popup_menu_header_item_layout, (android.view.ViewGroup) listView, false);
            android.widget.TextView textView = (android.widget.TextView) frameLayout.findViewById(16908310);
            frameLayout.setEnabled(false);
            textView.lambda$setTextAsync$0(menuBuilder.getHeaderTitle());
            listView.addHeaderView(frameLayout, null, false);
            createPopupWindow.show();
        }
    }

    private android.view.MenuItem findMenuItemForSubmenu(com.android.internal.view.menu.MenuBuilder menuBuilder, com.android.internal.view.menu.MenuBuilder menuBuilder2) {
        int size = menuBuilder.size();
        for (int i = 0; i < size; i++) {
            android.view.MenuItem item = menuBuilder.getItem(i);
            if (item.hasSubMenu() && menuBuilder2 == item.getSubMenu()) {
                return item;
            }
        }
        return null;
    }

    private android.view.View findParentViewForSubmenu(com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo cascadingMenuInfo, com.android.internal.view.menu.MenuBuilder menuBuilder) {
        com.android.internal.view.menu.MenuAdapter menuAdapter;
        int i;
        int firstVisiblePosition;
        android.view.MenuItem findMenuItemForSubmenu = findMenuItemForSubmenu(cascadingMenuInfo.menu, menuBuilder);
        if (findMenuItemForSubmenu == null) {
            return null;
        }
        android.widget.ListView listView = cascadingMenuInfo.getListView();
        android.widget.ListAdapter adapter = listView.getAdapter();
        int i2 = 0;
        if (adapter instanceof android.widget.HeaderViewListAdapter) {
            android.widget.HeaderViewListAdapter headerViewListAdapter = (android.widget.HeaderViewListAdapter) adapter;
            i = headerViewListAdapter.getHeadersCount();
            menuAdapter = (com.android.internal.view.menu.MenuAdapter) headerViewListAdapter.getWrappedAdapter();
        } else {
            menuAdapter = (com.android.internal.view.menu.MenuAdapter) adapter;
            i = 0;
        }
        int count = menuAdapter.getCount();
        while (true) {
            if (i2 >= count) {
                i2 = -1;
                break;
            }
            if (findMenuItemForSubmenu == menuAdapter.getItem(i2)) {
                break;
            }
            i2++;
        }
        if (i2 == -1 || (firstVisiblePosition = (i2 + i) - listView.getFirstVisiblePosition()) < 0 || firstVisiblePosition >= listView.getChildCount()) {
            return null;
        }
        return listView.getChildAt(firstVisiblePosition);
    }

    @Override // com.android.internal.view.menu.ShowableListMenu
    public boolean isShowing() {
        return this.mShowingMenus.size() > 0 && this.mShowingMenus.get(0).window.isShowing();
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo cascadingMenuInfo;
        int size = this.mShowingMenus.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                cascadingMenuInfo = null;
                break;
            }
            cascadingMenuInfo = this.mShowingMenus.get(i);
            if (!cascadingMenuInfo.window.isShowing()) {
                break;
            } else {
                i++;
            }
        }
        if (cascadingMenuInfo != null) {
            cascadingMenuInfo.menu.close(false);
        }
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        java.util.Iterator<com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo> it = this.mShowingMenus.iterator();
        while (it.hasNext()) {
            toMenuAdapter(it.next().getListView().getAdapter()).notifyDataSetChanged();
        }
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void setCallback(com.android.internal.view.menu.MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public boolean onSubMenuSelected(com.android.internal.view.menu.SubMenuBuilder subMenuBuilder) {
        for (com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo cascadingMenuInfo : this.mShowingMenus) {
            if (subMenuBuilder == cascadingMenuInfo.menu) {
                cascadingMenuInfo.getListView().requestFocus();
                return true;
            }
        }
        if (subMenuBuilder.hasVisibleItems()) {
            addMenu(subMenuBuilder);
            if (this.mPresenterCallback != null) {
                this.mPresenterCallback.onOpenSubMenu(subMenuBuilder);
            }
            return true;
        }
        return false;
    }

    private int findIndexOfAddedMenu(com.android.internal.view.menu.MenuBuilder menuBuilder) {
        int size = this.mShowingMenus.size();
        for (int i = 0; i < size; i++) {
            if (menuBuilder == this.mShowingMenus.get(i).menu) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
        int findIndexOfAddedMenu = findIndexOfAddedMenu(menuBuilder);
        if (findIndexOfAddedMenu < 0) {
            return;
        }
        int i = findIndexOfAddedMenu + 1;
        if (i < this.mShowingMenus.size()) {
            this.mShowingMenus.get(i).menu.close(false);
        }
        com.android.internal.view.menu.CascadingMenuPopup.CascadingMenuInfo remove = this.mShowingMenus.remove(findIndexOfAddedMenu);
        remove.menu.removeMenuPresenter(this);
        if (this.mShouldCloseImmediately) {
            remove.window.setExitTransition(null);
            remove.window.setAnimationStyle(0);
        }
        remove.window.dismiss();
        int size = this.mShowingMenus.size();
        if (size > 0) {
            this.mLastPosition = this.mShowingMenus.get(size - 1).position;
        } else {
            this.mLastPosition = getInitialMenuPosition();
        }
        if (size == 0) {
            dismiss();
            if (this.mPresenterCallback != null) {
                this.mPresenterCallback.onCloseMenu(menuBuilder, true);
            }
            if (this.mTreeObserver != null) {
                if (this.mTreeObserver.isAlive()) {
                    this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
                }
                this.mTreeObserver = null;
            }
            this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
            this.mOnDismissListener.onDismiss();
            return;
        }
        if (z) {
            this.mShowingMenus.get(0).menu.close(false);
        }
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public boolean flagActionItems() {
        return false;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public android.os.Parcelable onSaveInstanceState() {
        return null;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
    }

    @Override // com.android.internal.view.menu.MenuPopup
    public void setGravity(int i) {
        if (this.mRawDropDownGravity != i) {
            this.mRawDropDownGravity = i;
            this.mDropDownGravity = android.view.Gravity.getAbsoluteGravity(i, this.mAnchorView.getLayoutDirection());
        }
    }

    @Override // com.android.internal.view.menu.MenuPopup
    public void setAnchorView(android.view.View view) {
        if (this.mAnchorView != view) {
            this.mAnchorView = view;
            this.mDropDownGravity = android.view.Gravity.getAbsoluteGravity(this.mRawDropDownGravity, this.mAnchorView.getLayoutDirection());
        }
    }

    @Override // com.android.internal.view.menu.MenuPopup
    public void setOnDismissListener(android.widget.PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    @Override // com.android.internal.view.menu.ShowableListMenu
    public android.widget.ListView getListView() {
        if (this.mShowingMenus.isEmpty()) {
            return null;
        }
        return this.mShowingMenus.get(this.mShowingMenus.size() - 1).getListView();
    }

    @Override // com.android.internal.view.menu.MenuPopup
    public void setHorizontalOffset(int i) {
        this.mHasXOffset = true;
        this.mXOffset = i;
    }

    @Override // com.android.internal.view.menu.MenuPopup
    public void setVerticalOffset(int i) {
        this.mHasYOffset = true;
        this.mYOffset = i;
    }

    @Override // com.android.internal.view.menu.MenuPopup
    public void setShowTitle(boolean z) {
        this.mShowTitle = z;
    }

    private static class CascadingMenuInfo {
        public final com.android.internal.view.menu.MenuBuilder menu;
        public final int position;
        public final android.widget.MenuPopupWindow window;

        public CascadingMenuInfo(android.widget.MenuPopupWindow menuPopupWindow, com.android.internal.view.menu.MenuBuilder menuBuilder, int i) {
            this.window = menuPopupWindow;
            this.menu = menuBuilder;
            this.position = i;
        }

        public android.widget.ListView getListView() {
            return this.window.getListView();
        }
    }
}
