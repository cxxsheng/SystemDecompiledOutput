package com.android.internal.view.menu;

/* loaded from: classes5.dex */
public class ActionMenu implements android.view.Menu {
    private android.content.Context mContext;
    private boolean mIsQwerty;
    private java.util.ArrayList<com.android.internal.view.menu.ActionMenuItem> mItems = new java.util.ArrayList<>();

    public ActionMenu(android.content.Context context) {
        this.mContext = context;
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    @Override // android.view.Menu
    public android.view.MenuItem add(java.lang.CharSequence charSequence) {
        return add(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public android.view.MenuItem add(int i) {
        return add(0, 0, 0, i);
    }

    @Override // android.view.Menu
    public android.view.MenuItem add(int i, int i2, int i3, int i4) {
        return add(i, i2, i3, this.mContext.getResources().getString(i4));
    }

    @Override // android.view.Menu
    public android.view.MenuItem add(int i, int i2, int i3, java.lang.CharSequence charSequence) {
        com.android.internal.view.menu.ActionMenuItem actionMenuItem = new com.android.internal.view.menu.ActionMenuItem(getContext(), i, i2, 0, i3, charSequence);
        this.mItems.add(i3, actionMenuItem);
        return actionMenuItem;
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
    public android.view.SubMenu addSubMenu(java.lang.CharSequence charSequence) {
        return null;
    }

    @Override // android.view.Menu
    public android.view.SubMenu addSubMenu(int i) {
        return null;
    }

    @Override // android.view.Menu
    public android.view.SubMenu addSubMenu(int i, int i2, int i3, java.lang.CharSequence charSequence) {
        return null;
    }

    @Override // android.view.Menu
    public android.view.SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return null;
    }

    @Override // android.view.Menu
    public void clear() {
        this.mItems.clear();
    }

    @Override // android.view.Menu
    public void close() {
    }

    private int findItemIndex(int i) {
        java.util.ArrayList<com.android.internal.view.menu.ActionMenuItem> arrayList = this.mItems;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (arrayList.get(i2).getItemId() == i) {
                return i2;
            }
        }
        return -1;
    }

    @Override // android.view.Menu
    public android.view.MenuItem findItem(int i) {
        return this.mItems.get(findItemIndex(i));
    }

    @Override // android.view.Menu
    public android.view.MenuItem getItem(int i) {
        return this.mItems.get(i);
    }

    @Override // android.view.Menu
    public boolean hasVisibleItems() {
        java.util.ArrayList<com.android.internal.view.menu.ActionMenuItem> arrayList = this.mItems;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (arrayList.get(i).isVisible()) {
                return true;
            }
        }
        return false;
    }

    private com.android.internal.view.menu.ActionMenuItem findItemWithShortcut(int i, android.view.KeyEvent keyEvent) {
        boolean z = this.mIsQwerty;
        java.util.ArrayList<com.android.internal.view.menu.ActionMenuItem> arrayList = this.mItems;
        int size = arrayList.size();
        int modifiers = keyEvent.getModifiers();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.view.menu.ActionMenuItem actionMenuItem = arrayList.get(i2);
            char alphabeticShortcut = z ? actionMenuItem.getAlphabeticShortcut() : actionMenuItem.getNumericShortcut();
            boolean z2 = (modifiers & android.view.Menu.SUPPORTED_MODIFIERS_MASK) == ((z ? actionMenuItem.getAlphabeticModifiers() : actionMenuItem.getNumericModifiers()) & android.view.Menu.SUPPORTED_MODIFIERS_MASK);
            if (i == alphabeticShortcut && z2) {
                return actionMenuItem;
            }
        }
        return null;
    }

    @Override // android.view.Menu
    public boolean isShortcutKey(int i, android.view.KeyEvent keyEvent) {
        return findItemWithShortcut(i, keyEvent) != null;
    }

    @Override // android.view.Menu
    public boolean performIdentifierAction(int i, int i2) {
        int findItemIndex = findItemIndex(i);
        if (findItemIndex < 0) {
            return false;
        }
        return this.mItems.get(findItemIndex).invoke();
    }

    @Override // android.view.Menu
    public boolean performShortcut(int i, android.view.KeyEvent keyEvent, int i2) {
        com.android.internal.view.menu.ActionMenuItem findItemWithShortcut = findItemWithShortcut(i, keyEvent);
        if (findItemWithShortcut == null) {
            return false;
        }
        return findItemWithShortcut.invoke();
    }

    @Override // android.view.Menu
    public void removeGroup(int i) {
        java.util.ArrayList<com.android.internal.view.menu.ActionMenuItem> arrayList = this.mItems;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            if (arrayList.get(i2).getGroupId() == i) {
                arrayList.remove(i2);
                size--;
            } else {
                i2++;
            }
        }
    }

    @Override // android.view.Menu
    public void removeItem(int i) {
        this.mItems.remove(findItemIndex(i));
    }

    @Override // android.view.Menu
    public void setGroupCheckable(int i, boolean z, boolean z2) {
        java.util.ArrayList<com.android.internal.view.menu.ActionMenuItem> arrayList = this.mItems;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.view.menu.ActionMenuItem actionMenuItem = arrayList.get(i2);
            if (actionMenuItem.getGroupId() == i) {
                actionMenuItem.setCheckable(z);
                actionMenuItem.setExclusiveCheckable(z2);
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupEnabled(int i, boolean z) {
        java.util.ArrayList<com.android.internal.view.menu.ActionMenuItem> arrayList = this.mItems;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.view.menu.ActionMenuItem actionMenuItem = arrayList.get(i2);
            if (actionMenuItem.getGroupId() == i) {
                actionMenuItem.setEnabled(z);
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupVisible(int i, boolean z) {
        java.util.ArrayList<com.android.internal.view.menu.ActionMenuItem> arrayList = this.mItems;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.view.menu.ActionMenuItem actionMenuItem = arrayList.get(i2);
            if (actionMenuItem.getGroupId() == i) {
                actionMenuItem.setVisible(z);
            }
        }
    }

    @Override // android.view.Menu
    public void setQwertyMode(boolean z) {
        this.mIsQwerty = z;
    }

    @Override // android.view.Menu
    public int size() {
        return this.mItems.size();
    }
}
