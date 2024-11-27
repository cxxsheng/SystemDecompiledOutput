package android.view;

/* loaded from: classes4.dex */
public interface MenuItem {
    public static final int SHOW_AS_ACTION_ALWAYS = 2;
    public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;
    public static final int SHOW_AS_ACTION_NEVER = 0;
    public static final int SHOW_AS_ACTION_WITH_TEXT = 4;

    public interface OnActionExpandListener {
        boolean onMenuItemActionCollapse(android.view.MenuItem menuItem);

        boolean onMenuItemActionExpand(android.view.MenuItem menuItem);
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(android.view.MenuItem menuItem);
    }

    boolean collapseActionView();

    boolean expandActionView();

    android.view.ActionProvider getActionProvider();

    android.view.View getActionView();

    char getAlphabeticShortcut();

    int getGroupId();

    android.graphics.drawable.Drawable getIcon();

    android.content.Intent getIntent();

    int getItemId();

    android.view.ContextMenu.ContextMenuInfo getMenuInfo();

    char getNumericShortcut();

    int getOrder();

    android.view.SubMenu getSubMenu();

    java.lang.CharSequence getTitle();

    java.lang.CharSequence getTitleCondensed();

    boolean hasSubMenu();

    boolean isActionViewExpanded();

    boolean isCheckable();

    boolean isChecked();

    boolean isEnabled();

    boolean isVisible();

    android.view.MenuItem setActionProvider(android.view.ActionProvider actionProvider);

    android.view.MenuItem setActionView(int i);

    android.view.MenuItem setActionView(android.view.View view);

    android.view.MenuItem setAlphabeticShortcut(char c);

    android.view.MenuItem setCheckable(boolean z);

    android.view.MenuItem setChecked(boolean z);

    android.view.MenuItem setEnabled(boolean z);

    android.view.MenuItem setIcon(int i);

    android.view.MenuItem setIcon(android.graphics.drawable.Drawable drawable);

    android.view.MenuItem setIntent(android.content.Intent intent);

    android.view.MenuItem setNumericShortcut(char c);

    android.view.MenuItem setOnActionExpandListener(android.view.MenuItem.OnActionExpandListener onActionExpandListener);

    android.view.MenuItem setOnMenuItemClickListener(android.view.MenuItem.OnMenuItemClickListener onMenuItemClickListener);

    android.view.MenuItem setShortcut(char c, char c2);

    void setShowAsAction(int i);

    android.view.MenuItem setShowAsActionFlags(int i);

    android.view.MenuItem setTitle(int i);

    android.view.MenuItem setTitle(java.lang.CharSequence charSequence);

    android.view.MenuItem setTitleCondensed(java.lang.CharSequence charSequence);

    android.view.MenuItem setVisible(boolean z);

    default android.view.MenuItem setIconTintList(android.content.res.ColorStateList colorStateList) {
        return this;
    }

    default android.content.res.ColorStateList getIconTintList() {
        return null;
    }

    default android.view.MenuItem setIconTintMode(android.graphics.PorterDuff.Mode mode) {
        return this;
    }

    default android.view.MenuItem setIconTintBlendMode(android.graphics.BlendMode blendMode) {
        android.graphics.PorterDuff.Mode blendModeToPorterDuffMode = android.graphics.BlendMode.blendModeToPorterDuffMode(blendMode);
        if (blendModeToPorterDuffMode != null) {
            return setIconTintMode(blendModeToPorterDuffMode);
        }
        return this;
    }

    default android.graphics.PorterDuff.Mode getIconTintMode() {
        return null;
    }

    default android.graphics.BlendMode getIconTintBlendMode() {
        android.graphics.PorterDuff.Mode iconTintMode = getIconTintMode();
        if (iconTintMode != null) {
            return android.graphics.BlendMode.fromValue(iconTintMode.nativeInt);
        }
        return null;
    }

    default android.view.MenuItem setShortcut(char c, char c2, int i, int i2) {
        if ((i2 & android.view.Menu.SUPPORTED_MODIFIERS_MASK) == 4096 && (i & android.view.Menu.SUPPORTED_MODIFIERS_MASK) == 4096) {
            return setShortcut(c, c2);
        }
        return this;
    }

    default android.view.MenuItem setNumericShortcut(char c, int i) {
        if ((i & android.view.Menu.SUPPORTED_MODIFIERS_MASK) == 4096) {
            return setNumericShortcut(c);
        }
        return this;
    }

    default int getNumericModifiers() {
        return 4096;
    }

    default android.view.MenuItem setAlphabeticShortcut(char c, int i) {
        if ((i & android.view.Menu.SUPPORTED_MODIFIERS_MASK) == 4096) {
            return setAlphabeticShortcut(c);
        }
        return this;
    }

    default int getAlphabeticModifiers() {
        return 4096;
    }

    default android.view.MenuItem setContentDescription(java.lang.CharSequence charSequence) {
        return this;
    }

    default java.lang.CharSequence getContentDescription() {
        return null;
    }

    default android.view.MenuItem setTooltipText(java.lang.CharSequence charSequence) {
        return this;
    }

    default java.lang.CharSequence getTooltipText() {
        return null;
    }

    default boolean requiresActionButton() {
        return false;
    }

    default boolean requiresOverflow() {
        return true;
    }
}
