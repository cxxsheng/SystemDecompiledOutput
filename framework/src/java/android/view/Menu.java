package android.view;

/* loaded from: classes4.dex */
public interface Menu {
    public static final int CATEGORY_ALTERNATIVE = 262144;
    public static final int CATEGORY_CONTAINER = 65536;
    public static final int CATEGORY_MASK = -65536;
    public static final int CATEGORY_SECONDARY = 196608;
    public static final int CATEGORY_SHIFT = 16;
    public static final int CATEGORY_SYSTEM = 131072;
    public static final int FIRST = 1;
    public static final int FLAG_ALWAYS_PERFORM_CLOSE = 2;
    public static final int FLAG_APPEND_TO_GROUP = 1;
    public static final int FLAG_PERFORM_NO_CLOSE = 1;
    public static final int NONE = 0;
    public static final int SUPPORTED_MODIFIERS_MASK = 69647;
    public static final int USER_MASK = 65535;
    public static final int USER_SHIFT = 0;

    android.view.MenuItem add(int i);

    android.view.MenuItem add(int i, int i2, int i3, int i4);

    android.view.MenuItem add(int i, int i2, int i3, java.lang.CharSequence charSequence);

    android.view.MenuItem add(java.lang.CharSequence charSequence);

    int addIntentOptions(int i, int i2, int i3, android.content.ComponentName componentName, android.content.Intent[] intentArr, android.content.Intent intent, int i4, android.view.MenuItem[] menuItemArr);

    android.view.SubMenu addSubMenu(int i);

    android.view.SubMenu addSubMenu(int i, int i2, int i3, int i4);

    android.view.SubMenu addSubMenu(int i, int i2, int i3, java.lang.CharSequence charSequence);

    android.view.SubMenu addSubMenu(java.lang.CharSequence charSequence);

    void clear();

    void close();

    android.view.MenuItem findItem(int i);

    android.view.MenuItem getItem(int i);

    boolean hasVisibleItems();

    boolean isShortcutKey(int i, android.view.KeyEvent keyEvent);

    boolean performIdentifierAction(int i, int i2);

    boolean performShortcut(int i, android.view.KeyEvent keyEvent, int i2);

    void removeGroup(int i);

    void removeItem(int i);

    void setGroupCheckable(int i, boolean z, boolean z2);

    void setGroupEnabled(int i, boolean z);

    void setGroupVisible(int i, boolean z);

    void setQwertyMode(boolean z);

    int size();

    default void setOptionalIconsVisible(boolean z) {
    }

    default void setGroupDividerEnabled(boolean z) {
    }
}
