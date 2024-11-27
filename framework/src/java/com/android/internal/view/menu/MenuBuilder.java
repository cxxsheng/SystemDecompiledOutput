package com.android.internal.view.menu;

/* loaded from: classes5.dex */
public class MenuBuilder implements android.view.Menu {
    private static final java.lang.String ACTION_VIEW_STATES_KEY = "android:menu:actionviewstates";
    private static final java.lang.String EXPANDED_ACTION_VIEW_ID = "android:menu:expandedactionview";
    private static final java.lang.String PRESENTER_KEY = "android:menu:presenters";
    private static final java.lang.String TAG = "MenuBuilder";
    private static final int[] sCategoryToOrder = {1, 4, 5, 3, 2, 0};
    private com.android.internal.view.menu.MenuBuilder.Callback mCallback;
    private final android.content.Context mContext;
    private android.view.ContextMenu.ContextMenuInfo mCurrentMenuInfo;
    private com.android.internal.view.menu.MenuItemImpl mExpandedItem;
    private android.util.SparseArray<android.os.Parcelable> mFrozenViewStates;
    android.graphics.drawable.Drawable mHeaderIcon;
    java.lang.CharSequence mHeaderTitle;
    android.view.View mHeaderView;
    private boolean mQwertyMode;
    private final android.content.res.Resources mResources;
    private boolean mShortcutsVisible;
    private int mDefaultShowAsAction = 0;
    private boolean mPreventDispatchingItemsChanged = false;
    private boolean mItemsChangedWhileDispatchPrevented = false;
    private boolean mOptionalIconsVisible = false;
    private boolean mIsClosing = false;
    private java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> mTempShortcutItemList = new java.util.ArrayList<>();
    private java.util.concurrent.CopyOnWriteArrayList<java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter>> mPresenters = new java.util.concurrent.CopyOnWriteArrayList<>();
    private boolean mGroupDividerEnabled = false;
    private java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> mItems = new java.util.ArrayList<>();
    private java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> mVisibleItems = new java.util.ArrayList<>();
    private boolean mIsVisibleItemsStale = true;
    private java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> mActionItems = new java.util.ArrayList<>();
    private java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> mNonActionItems = new java.util.ArrayList<>();
    private boolean mIsActionItemsStale = true;

    public interface Callback {
        boolean onMenuItemSelected(com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.MenuItem menuItem);

        void onMenuModeChange(com.android.internal.view.menu.MenuBuilder menuBuilder);
    }

    public interface ItemInvoker {
        boolean invokeItem(com.android.internal.view.menu.MenuItemImpl menuItemImpl);
    }

    public MenuBuilder(android.content.Context context) {
        this.mContext = context;
        this.mResources = context.getResources();
        setShortcutsVisibleInner(true);
    }

    public com.android.internal.view.menu.MenuBuilder setDefaultShowAsAction(int i) {
        this.mDefaultShowAsAction = i;
        return this;
    }

    public void addMenuPresenter(com.android.internal.view.menu.MenuPresenter menuPresenter) {
        addMenuPresenter(menuPresenter, this.mContext);
    }

    public void addMenuPresenter(com.android.internal.view.menu.MenuPresenter menuPresenter, android.content.Context context) {
        this.mPresenters.add(new java.lang.ref.WeakReference<>(menuPresenter));
        menuPresenter.initForMenu(context, this);
        this.mIsActionItemsStale = true;
    }

    public void removeMenuPresenter(com.android.internal.view.menu.MenuPresenter menuPresenter) {
        java.util.Iterator<java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter>> it = this.mPresenters.iterator();
        while (it.hasNext()) {
            java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter> next = it.next();
            com.android.internal.view.menu.MenuPresenter menuPresenter2 = next.get();
            if (menuPresenter2 == null || menuPresenter2 == menuPresenter) {
                this.mPresenters.remove(next);
            }
        }
    }

    private void dispatchPresenterUpdate(boolean z) {
        if (this.mPresenters.isEmpty()) {
            return;
        }
        stopDispatchingItemsChanged();
        java.util.Iterator<java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter>> it = this.mPresenters.iterator();
        while (it.hasNext()) {
            java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter> next = it.next();
            com.android.internal.view.menu.MenuPresenter menuPresenter = next.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(next);
            } else {
                menuPresenter.updateMenuView(z);
            }
        }
        startDispatchingItemsChanged();
    }

    private boolean dispatchSubMenuSelected(com.android.internal.view.menu.SubMenuBuilder subMenuBuilder, com.android.internal.view.menu.MenuPresenter menuPresenter) {
        if (this.mPresenters.isEmpty()) {
            return false;
        }
        boolean onSubMenuSelected = menuPresenter != null ? menuPresenter.onSubMenuSelected(subMenuBuilder) : false;
        java.util.Iterator<java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter>> it = this.mPresenters.iterator();
        while (it.hasNext()) {
            java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter> next = it.next();
            com.android.internal.view.menu.MenuPresenter menuPresenter2 = next.get();
            if (menuPresenter2 == null) {
                this.mPresenters.remove(next);
            } else if (!onSubMenuSelected) {
                onSubMenuSelected = menuPresenter2.onSubMenuSelected(subMenuBuilder);
            }
        }
        return onSubMenuSelected;
    }

    private void dispatchSaveInstanceState(android.os.Bundle bundle) {
        android.os.Parcelable onSaveInstanceState;
        if (this.mPresenters.isEmpty()) {
            return;
        }
        android.util.SparseArray<? extends android.os.Parcelable> sparseArray = new android.util.SparseArray<>();
        java.util.Iterator<java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter>> it = this.mPresenters.iterator();
        while (it.hasNext()) {
            java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter> next = it.next();
            com.android.internal.view.menu.MenuPresenter menuPresenter = next.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(next);
            } else {
                int id = menuPresenter.getId();
                if (id > 0 && (onSaveInstanceState = menuPresenter.onSaveInstanceState()) != null) {
                    sparseArray.put(id, onSaveInstanceState);
                }
            }
        }
        bundle.putSparseParcelableArray(PRESENTER_KEY, sparseArray);
    }

    private void dispatchRestoreInstanceState(android.os.Bundle bundle) {
        android.os.Parcelable parcelable;
        android.util.SparseArray sparseParcelableArray = bundle.getSparseParcelableArray(PRESENTER_KEY);
        if (sparseParcelableArray == null || this.mPresenters.isEmpty()) {
            return;
        }
        java.util.Iterator<java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter>> it = this.mPresenters.iterator();
        while (it.hasNext()) {
            java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter> next = it.next();
            com.android.internal.view.menu.MenuPresenter menuPresenter = next.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(next);
            } else {
                int id = menuPresenter.getId();
                if (id > 0 && (parcelable = (android.os.Parcelable) sparseParcelableArray.get(id)) != null) {
                    menuPresenter.onRestoreInstanceState(parcelable);
                }
            }
        }
    }

    public void savePresenterStates(android.os.Bundle bundle) {
        dispatchSaveInstanceState(bundle);
    }

    public void restorePresenterStates(android.os.Bundle bundle) {
        dispatchRestoreInstanceState(bundle);
    }

    public void saveActionViewStates(android.os.Bundle bundle) {
        int size = size();
        android.util.SparseArray<? extends android.os.Parcelable> sparseArray = null;
        for (int i = 0; i < size; i++) {
            android.view.MenuItem item = getItem(i);
            android.view.View actionView = item.getActionView();
            if (actionView != null && actionView.getId() != -1) {
                if (sparseArray == null) {
                    sparseArray = new android.util.SparseArray<>();
                }
                actionView.saveHierarchyState(sparseArray);
                if (item.isActionViewExpanded()) {
                    bundle.putInt(EXPANDED_ACTION_VIEW_ID, item.getItemId());
                }
            }
            if (item.hasSubMenu()) {
                ((com.android.internal.view.menu.SubMenuBuilder) item.getSubMenu()).saveActionViewStates(bundle);
            }
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(getActionViewStatesKey(), sparseArray);
        }
    }

    public void restoreActionViewStates(android.os.Bundle bundle) {
        android.view.MenuItem findItem;
        if (bundle == null) {
            return;
        }
        android.util.SparseArray<android.os.Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(getActionViewStatesKey());
        int size = size();
        for (int i = 0; i < size; i++) {
            android.view.MenuItem item = getItem(i);
            android.view.View actionView = item.getActionView();
            if (actionView != null && actionView.getId() != -1) {
                actionView.restoreHierarchyState(sparseParcelableArray);
            }
            if (item.hasSubMenu()) {
                ((com.android.internal.view.menu.SubMenuBuilder) item.getSubMenu()).restoreActionViewStates(bundle);
            }
        }
        int i2 = bundle.getInt(EXPANDED_ACTION_VIEW_ID);
        if (i2 > 0 && (findItem = findItem(i2)) != null) {
            findItem.expandActionView();
        }
    }

    protected java.lang.String getActionViewStatesKey() {
        return ACTION_VIEW_STATES_KEY;
    }

    public void setCallback(com.android.internal.view.menu.MenuBuilder.Callback callback) {
        this.mCallback = callback;
    }

    private android.view.MenuItem addInternal(int i, int i2, int i3, java.lang.CharSequence charSequence) {
        int ordering = getOrdering(i3);
        com.android.internal.view.menu.MenuItemImpl createNewMenuItem = createNewMenuItem(i, i2, i3, ordering, charSequence, this.mDefaultShowAsAction);
        if (this.mCurrentMenuInfo != null) {
            createNewMenuItem.setMenuInfo(this.mCurrentMenuInfo);
        }
        this.mItems.add(findInsertIndex(this.mItems, ordering), createNewMenuItem);
        onItemsChanged(true);
        return createNewMenuItem;
    }

    private com.android.internal.view.menu.MenuItemImpl createNewMenuItem(int i, int i2, int i3, int i4, java.lang.CharSequence charSequence, int i5) {
        return new com.android.internal.view.menu.MenuItemImpl(this, i, i2, i3, i4, charSequence, i5);
    }

    @Override // android.view.Menu
    public android.view.MenuItem add(java.lang.CharSequence charSequence) {
        return addInternal(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public android.view.MenuItem add(int i) {
        return addInternal(0, 0, 0, this.mResources.getString(i));
    }

    @Override // android.view.Menu
    public android.view.MenuItem add(int i, int i2, int i3, java.lang.CharSequence charSequence) {
        return addInternal(i, i2, i3, charSequence);
    }

    @Override // android.view.Menu
    public android.view.MenuItem add(int i, int i2, int i3, int i4) {
        return addInternal(i, i2, i3, this.mResources.getString(i4));
    }

    @Override // android.view.Menu
    public android.view.SubMenu addSubMenu(java.lang.CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public android.view.SubMenu addSubMenu(int i) {
        return addSubMenu(0, 0, 0, this.mResources.getString(i));
    }

    @Override // android.view.Menu
    public android.view.SubMenu addSubMenu(int i, int i2, int i3, java.lang.CharSequence charSequence) {
        com.android.internal.view.menu.MenuItemImpl menuItemImpl = (com.android.internal.view.menu.MenuItemImpl) addInternal(i, i2, i3, charSequence);
        com.android.internal.view.menu.SubMenuBuilder subMenuBuilder = new com.android.internal.view.menu.SubMenuBuilder(this.mContext, this, menuItemImpl);
        menuItemImpl.setSubMenu(subMenuBuilder);
        return subMenuBuilder;
    }

    @Override // android.view.Menu
    public android.view.SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return addSubMenu(i, i2, i3, this.mResources.getString(i4));
    }

    @Override // android.view.Menu
    public void setGroupDividerEnabled(boolean z) {
        this.mGroupDividerEnabled = z;
    }

    public boolean isGroupDividerEnabled() {
        return this.mGroupDividerEnabled;
    }

    @Override // android.view.Menu
    public int addIntentOptions(int i, int i2, int i3, android.content.ComponentName componentName, android.content.Intent[] intentArr, android.content.Intent intent, int i4, android.view.MenuItem[] menuItemArr) {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0);
        int size = queryIntentActivityOptions != null ? queryIntentActivityOptions.size() : 0;
        if ((i4 & 1) == 0) {
            removeGroup(i);
        }
        for (int i5 = 0; i5 < size; i5++) {
            android.content.pm.ResolveInfo resolveInfo = queryIntentActivityOptions.get(i5);
            android.content.Intent intent2 = new android.content.Intent(resolveInfo.specificIndex < 0 ? intent : intentArr[resolveInfo.specificIndex]);
            intent2.setComponent(new android.content.ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name));
            android.view.MenuItem intent3 = add(i, i2, i3, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent(intent2);
            if (menuItemArr != null && resolveInfo.specificIndex >= 0) {
                menuItemArr[resolveInfo.specificIndex] = intent3;
            }
        }
        return size;
    }

    @Override // android.view.Menu
    public void removeItem(int i) {
        removeItemAtInt(findItemIndex(i), true);
    }

    @Override // android.view.Menu
    public void removeGroup(int i) {
        int findGroupIndex = findGroupIndex(i);
        if (findGroupIndex >= 0) {
            int size = this.mItems.size() - findGroupIndex;
            int i2 = 0;
            while (true) {
                int i3 = i2 + 1;
                if (i2 >= size || this.mItems.get(findGroupIndex).getGroupId() != i) {
                    break;
                }
                removeItemAtInt(findGroupIndex, false);
                i2 = i3;
            }
            onItemsChanged(true);
        }
    }

    private void removeItemAtInt(int i, boolean z) {
        if (i < 0 || i >= this.mItems.size()) {
            return;
        }
        this.mItems.remove(i);
        if (z) {
            onItemsChanged(true);
        }
    }

    public void removeItemAt(int i) {
        removeItemAtInt(i, true);
    }

    public void clearAll() {
        this.mPreventDispatchingItemsChanged = true;
        clear();
        clearHeader();
        this.mPresenters.clear();
        this.mPreventDispatchingItemsChanged = false;
        this.mItemsChangedWhileDispatchPrevented = false;
        onItemsChanged(true);
    }

    @Override // android.view.Menu
    public void clear() {
        if (this.mExpandedItem != null) {
            collapseItemActionView(this.mExpandedItem);
        }
        this.mItems.clear();
        onItemsChanged(true);
    }

    void setExclusiveItemChecked(android.view.MenuItem menuItem) {
        int groupId = menuItem.getGroupId();
        int size = this.mItems.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.view.menu.MenuItemImpl menuItemImpl = this.mItems.get(i);
            if (menuItemImpl.getGroupId() == groupId && menuItemImpl.isExclusiveCheckable() && menuItemImpl.isCheckable()) {
                menuItemImpl.setCheckedInt(menuItemImpl == menuItem);
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupCheckable(int i, boolean z, boolean z2) {
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.view.menu.MenuItemImpl menuItemImpl = this.mItems.get(i2);
            if (menuItemImpl.getGroupId() == i) {
                menuItemImpl.setExclusiveCheckable(z2);
                menuItemImpl.setCheckable(z);
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupVisible(int i, boolean z) {
        int size = this.mItems.size();
        boolean z2 = false;
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.view.menu.MenuItemImpl menuItemImpl = this.mItems.get(i2);
            if (menuItemImpl.getGroupId() == i && menuItemImpl.setVisibleInt(z)) {
                z2 = true;
            }
        }
        if (z2) {
            onItemsChanged(true);
        }
    }

    @Override // android.view.Menu
    public void setGroupEnabled(int i, boolean z) {
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.view.menu.MenuItemImpl menuItemImpl = this.mItems.get(i2);
            if (menuItemImpl.getGroupId() == i) {
                menuItemImpl.setEnabled(z);
            }
        }
    }

    @Override // android.view.Menu
    public boolean hasVisibleItems() {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.mItems.get(i).isVisible()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.Menu
    public android.view.MenuItem findItem(int i) {
        android.view.MenuItem findItem;
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.view.menu.MenuItemImpl menuItemImpl = this.mItems.get(i2);
            if (menuItemImpl.getItemId() == i) {
                return menuItemImpl;
            }
            if (menuItemImpl.hasSubMenu() && (findItem = menuItemImpl.getSubMenu().findItem(i)) != null) {
                return findItem;
            }
        }
        return null;
    }

    public int findItemIndex(int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            if (this.mItems.get(i2).getItemId() == i) {
                return i2;
            }
        }
        return -1;
    }

    public int findGroupIndex(int i) {
        return findGroupIndex(i, 0);
    }

    public int findGroupIndex(int i, int i2) {
        int size = size();
        if (i2 < 0) {
            i2 = 0;
        }
        while (i2 < size) {
            if (this.mItems.get(i2).getGroupId() != i) {
                i2++;
            } else {
                return i2;
            }
        }
        return -1;
    }

    @Override // android.view.Menu
    public int size() {
        return this.mItems.size();
    }

    @Override // android.view.Menu
    public android.view.MenuItem getItem(int i) {
        return this.mItems.get(i);
    }

    @Override // android.view.Menu
    public boolean isShortcutKey(int i, android.view.KeyEvent keyEvent) {
        return findItemWithShortcutForKey(i, keyEvent) != null;
    }

    @Override // android.view.Menu
    public void setQwertyMode(boolean z) {
        this.mQwertyMode = z;
        onItemsChanged(false);
    }

    private static int getOrdering(int i) {
        int i2 = ((-65536) & i) >> 16;
        if (i2 < 0 || i2 >= sCategoryToOrder.length) {
            throw new java.lang.IllegalArgumentException("order does not contain a valid category.");
        }
        return (i & 65535) | (sCategoryToOrder[i2] << 16);
    }

    boolean isQwertyMode() {
        return this.mQwertyMode;
    }

    public void setShortcutsVisible(boolean z) {
        if (this.mShortcutsVisible == z) {
            return;
        }
        setShortcutsVisibleInner(z);
        onItemsChanged(false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0017, code lost:
    
        if (android.view.ViewConfiguration.get(r1.mContext).shouldShowMenuShortcutsWhenKeyboardPresent() != false) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setShortcutsVisibleInner(boolean z) {
        boolean z2;
        if (z) {
            z2 = true;
            if (this.mResources.getConfiguration().keyboard != 1) {
            }
        }
        z2 = false;
        this.mShortcutsVisible = z2;
    }

    public boolean isShortcutsVisible() {
        return this.mShortcutsVisible;
    }

    android.content.res.Resources getResources() {
        return this.mResources;
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    boolean dispatchMenuItemSelected(com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.MenuItem menuItem) {
        return this.mCallback != null && this.mCallback.onMenuItemSelected(menuBuilder, menuItem);
    }

    public void changeMenuMode() {
        if (this.mCallback != null) {
            this.mCallback.onMenuModeChange(this);
        }
    }

    private static int findInsertIndex(java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> arrayList, int i) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size).getOrdering() <= i) {
                return size + 1;
            }
        }
        return 0;
    }

    @Override // android.view.Menu
    public boolean performShortcut(int i, android.view.KeyEvent keyEvent, int i2) {
        boolean z;
        com.android.internal.view.menu.MenuItemImpl findItemWithShortcutForKey = findItemWithShortcutForKey(i, keyEvent);
        if (findItemWithShortcutForKey == null) {
            z = false;
        } else {
            z = performItemAction(findItemWithShortcutForKey, i2);
        }
        if ((i2 & 2) != 0) {
            close(true);
        }
        return z;
    }

    void findItemsWithShortcutForKey(java.util.List<com.android.internal.view.menu.MenuItemImpl> list, int i, android.view.KeyEvent keyEvent) {
        boolean isQwertyMode = isQwertyMode();
        int modifiers = keyEvent.getModifiers();
        android.view.KeyCharacterMap.KeyData keyData = new android.view.KeyCharacterMap.KeyData();
        if (!keyEvent.getKeyData(keyData) && i != 67) {
            return;
        }
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.view.menu.MenuItemImpl menuItemImpl = this.mItems.get(i2);
            if (menuItemImpl.hasSubMenu()) {
                ((com.android.internal.view.menu.MenuBuilder) menuItemImpl.getSubMenu()).findItemsWithShortcutForKey(list, i, keyEvent);
            }
            char alphabeticShortcut = isQwertyMode ? menuItemImpl.getAlphabeticShortcut() : menuItemImpl.getNumericShortcut();
            if (((modifiers & android.view.Menu.SUPPORTED_MODIFIERS_MASK) == ((isQwertyMode ? menuItemImpl.getAlphabeticModifiers() : menuItemImpl.getNumericModifiers()) & android.view.Menu.SUPPORTED_MODIFIERS_MASK)) && alphabeticShortcut != 0 && ((alphabeticShortcut == keyData.meta[0] || alphabeticShortcut == keyData.meta[2] || (isQwertyMode && alphabeticShortcut == '\b' && i == 67)) && menuItemImpl.isEnabled())) {
                list.add(menuItemImpl);
            }
        }
    }

    com.android.internal.view.menu.MenuItemImpl findItemWithShortcutForKey(int i, android.view.KeyEvent keyEvent) {
        java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> arrayList = this.mTempShortcutItemList;
        arrayList.clear();
        findItemsWithShortcutForKey(arrayList, i, keyEvent);
        if (arrayList.isEmpty()) {
            return null;
        }
        int metaState = keyEvent.getMetaState();
        android.view.KeyCharacterMap.KeyData keyData = new android.view.KeyCharacterMap.KeyData();
        keyEvent.getKeyData(keyData);
        int size = arrayList.size();
        if (size == 1) {
            return arrayList.get(0);
        }
        boolean isQwertyMode = isQwertyMode();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.view.menu.MenuItemImpl menuItemImpl = arrayList.get(i2);
            char alphabeticShortcut = isQwertyMode ? menuItemImpl.getAlphabeticShortcut() : menuItemImpl.getNumericShortcut();
            if ((alphabeticShortcut == keyData.meta[0] && (metaState & 2) == 0) || ((alphabeticShortcut == keyData.meta[2] && (metaState & 2) != 0) || (isQwertyMode && alphabeticShortcut == '\b' && i == 67))) {
                return menuItemImpl;
            }
        }
        return null;
    }

    @Override // android.view.Menu
    public boolean performIdentifierAction(int i, int i2) {
        return performItemAction(findItem(i), i2);
    }

    public boolean performItemAction(android.view.MenuItem menuItem, int i) {
        return performItemAction(menuItem, null, i);
    }

    public boolean performItemAction(android.view.MenuItem menuItem, com.android.internal.view.menu.MenuPresenter menuPresenter, int i) {
        com.android.internal.view.menu.MenuItemImpl menuItemImpl = (com.android.internal.view.menu.MenuItemImpl) menuItem;
        boolean z = false;
        if (menuItemImpl == null || !menuItemImpl.isEnabled()) {
            return false;
        }
        boolean invoke = menuItemImpl.invoke();
        android.view.ActionProvider actionProvider = menuItem.getActionProvider();
        if (actionProvider != null && actionProvider.hasSubMenu()) {
            z = true;
        }
        if (menuItemImpl.hasCollapsibleActionView()) {
            invoke |= menuItemImpl.expandActionView();
            if (invoke) {
                close(true);
            }
        } else if (menuItemImpl.hasSubMenu() || z) {
            if (!menuItemImpl.hasSubMenu()) {
                menuItemImpl.setSubMenu(new com.android.internal.view.menu.SubMenuBuilder(getContext(), this, menuItemImpl));
            }
            com.android.internal.view.menu.SubMenuBuilder subMenuBuilder = (com.android.internal.view.menu.SubMenuBuilder) menuItemImpl.getSubMenu();
            if (z) {
                actionProvider.onPrepareSubMenu(subMenuBuilder);
            }
            invoke |= dispatchSubMenuSelected(subMenuBuilder, menuPresenter);
            if (!invoke) {
                close(true);
            }
        } else if ((i & 1) == 0) {
            close(true);
        }
        return invoke;
    }

    public final void close(boolean z) {
        if (this.mIsClosing) {
            return;
        }
        this.mIsClosing = true;
        java.util.Iterator<java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter>> it = this.mPresenters.iterator();
        while (it.hasNext()) {
            java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter> next = it.next();
            com.android.internal.view.menu.MenuPresenter menuPresenter = next.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(next);
            } else {
                menuPresenter.onCloseMenu(this, z);
            }
        }
        this.mIsClosing = false;
    }

    @Override // android.view.Menu
    public void close() {
        close(true);
    }

    public void onItemsChanged(boolean z) {
        if (!this.mPreventDispatchingItemsChanged) {
            if (z) {
                this.mIsVisibleItemsStale = true;
                this.mIsActionItemsStale = true;
            }
            dispatchPresenterUpdate(z);
            return;
        }
        this.mItemsChangedWhileDispatchPrevented = true;
    }

    public void stopDispatchingItemsChanged() {
        if (!this.mPreventDispatchingItemsChanged) {
            this.mPreventDispatchingItemsChanged = true;
            this.mItemsChangedWhileDispatchPrevented = false;
        }
    }

    public void startDispatchingItemsChanged() {
        this.mPreventDispatchingItemsChanged = false;
        if (this.mItemsChangedWhileDispatchPrevented) {
            this.mItemsChangedWhileDispatchPrevented = false;
            onItemsChanged(true);
        }
    }

    void onItemVisibleChanged(com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        this.mIsVisibleItemsStale = true;
        onItemsChanged(true);
    }

    void onItemActionRequestChanged(com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        this.mIsActionItemsStale = true;
        onItemsChanged(true);
    }

    public java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> getVisibleItems() {
        if (!this.mIsVisibleItemsStale) {
            return this.mVisibleItems;
        }
        this.mVisibleItems.clear();
        int size = this.mItems.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.view.menu.MenuItemImpl menuItemImpl = this.mItems.get(i);
            if (menuItemImpl.isVisible()) {
                this.mVisibleItems.add(menuItemImpl);
            }
        }
        this.mIsVisibleItemsStale = false;
        this.mIsActionItemsStale = true;
        return this.mVisibleItems;
    }

    public void flagActionItems() {
        java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> visibleItems = getVisibleItems();
        if (!this.mIsActionItemsStale) {
            return;
        }
        java.util.Iterator<java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter>> it = this.mPresenters.iterator();
        boolean z = false;
        while (it.hasNext()) {
            java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter> next = it.next();
            com.android.internal.view.menu.MenuPresenter menuPresenter = next.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(next);
            } else {
                z |= menuPresenter.flagActionItems();
            }
        }
        if (z) {
            this.mActionItems.clear();
            this.mNonActionItems.clear();
            int size = visibleItems.size();
            for (int i = 0; i < size; i++) {
                com.android.internal.view.menu.MenuItemImpl menuItemImpl = visibleItems.get(i);
                if (menuItemImpl.isActionButton()) {
                    this.mActionItems.add(menuItemImpl);
                } else {
                    this.mNonActionItems.add(menuItemImpl);
                }
            }
        } else {
            this.mActionItems.clear();
            this.mNonActionItems.clear();
            this.mNonActionItems.addAll(getVisibleItems());
        }
        this.mIsActionItemsStale = false;
    }

    public java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> getActionItems() {
        flagActionItems();
        return this.mActionItems;
    }

    public java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> getNonActionItems() {
        flagActionItems();
        return this.mNonActionItems;
    }

    public void clearHeader() {
        this.mHeaderIcon = null;
        this.mHeaderTitle = null;
        this.mHeaderView = null;
        onItemsChanged(false);
    }

    private void setHeaderInternal(int i, java.lang.CharSequence charSequence, int i2, android.graphics.drawable.Drawable drawable, android.view.View view) {
        android.content.res.Resources resources = getResources();
        if (view != null) {
            this.mHeaderView = view;
            this.mHeaderTitle = null;
            this.mHeaderIcon = null;
        } else {
            if (i > 0) {
                this.mHeaderTitle = resources.getText(i);
            } else if (charSequence != null) {
                this.mHeaderTitle = charSequence;
            }
            if (i2 > 0) {
                this.mHeaderIcon = getContext().getDrawable(i2);
            } else if (drawable != null) {
                this.mHeaderIcon = drawable;
            }
            this.mHeaderView = null;
        }
        onItemsChanged(false);
    }

    protected com.android.internal.view.menu.MenuBuilder setHeaderTitleInt(java.lang.CharSequence charSequence) {
        setHeaderInternal(0, charSequence, 0, null, null);
        return this;
    }

    protected com.android.internal.view.menu.MenuBuilder setHeaderTitleInt(int i) {
        setHeaderInternal(i, null, 0, null, null);
        return this;
    }

    protected com.android.internal.view.menu.MenuBuilder setHeaderIconInt(android.graphics.drawable.Drawable drawable) {
        setHeaderInternal(0, null, 0, drawable, null);
        return this;
    }

    protected com.android.internal.view.menu.MenuBuilder setHeaderIconInt(int i) {
        setHeaderInternal(0, null, i, null, null);
        return this;
    }

    protected com.android.internal.view.menu.MenuBuilder setHeaderViewInt(android.view.View view) {
        setHeaderInternal(0, null, 0, null, view);
        return this;
    }

    public java.lang.CharSequence getHeaderTitle() {
        return this.mHeaderTitle;
    }

    public android.graphics.drawable.Drawable getHeaderIcon() {
        return this.mHeaderIcon;
    }

    public android.view.View getHeaderView() {
        return this.mHeaderView;
    }

    public com.android.internal.view.menu.MenuBuilder getRootMenu() {
        return this;
    }

    public void setCurrentMenuInfo(android.view.ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.mCurrentMenuInfo = contextMenuInfo;
    }

    @Override // android.view.Menu
    public void setOptionalIconsVisible(boolean z) {
        this.mOptionalIconsVisible = z;
    }

    boolean getOptionalIconsVisible() {
        return this.mOptionalIconsVisible;
    }

    public boolean expandItemActionView(com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        boolean z = false;
        if (this.mPresenters.isEmpty()) {
            return false;
        }
        stopDispatchingItemsChanged();
        java.util.Iterator<java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter>> it = this.mPresenters.iterator();
        while (it.hasNext()) {
            java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter> next = it.next();
            com.android.internal.view.menu.MenuPresenter menuPresenter = next.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(next);
            } else {
                z = menuPresenter.expandItemActionView(this, menuItemImpl);
                if (z) {
                    break;
                }
            }
        }
        startDispatchingItemsChanged();
        if (z) {
            this.mExpandedItem = menuItemImpl;
        }
        return z;
    }

    public boolean collapseItemActionView(com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        boolean z = false;
        if (this.mPresenters.isEmpty() || this.mExpandedItem != menuItemImpl) {
            return false;
        }
        stopDispatchingItemsChanged();
        java.util.Iterator<java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter>> it = this.mPresenters.iterator();
        while (it.hasNext()) {
            java.lang.ref.WeakReference<com.android.internal.view.menu.MenuPresenter> next = it.next();
            com.android.internal.view.menu.MenuPresenter menuPresenter = next.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(next);
            } else {
                z = menuPresenter.collapseItemActionView(this, menuItemImpl);
                if (z) {
                    break;
                }
            }
        }
        startDispatchingItemsChanged();
        if (z) {
            this.mExpandedItem = null;
        }
        return z;
    }

    public com.android.internal.view.menu.MenuItemImpl getExpandedItem() {
        return this.mExpandedItem;
    }
}
