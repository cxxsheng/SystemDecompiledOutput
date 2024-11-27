package android.app;

/* loaded from: classes.dex */
public abstract class ActionBar {
    public static final int DISPLAY_HOME_AS_UP = 4;
    public static final int DISPLAY_SHOW_CUSTOM = 16;
    public static final int DISPLAY_SHOW_HOME = 2;
    public static final int DISPLAY_SHOW_TITLE = 8;
    public static final int DISPLAY_TITLE_MULTIPLE_LINES = 32;
    public static final int DISPLAY_USE_LOGO = 1;

    @java.lang.Deprecated
    public static final int NAVIGATION_MODE_LIST = 1;

    @java.lang.Deprecated
    public static final int NAVIGATION_MODE_STANDARD = 0;

    @java.lang.Deprecated
    public static final int NAVIGATION_MODE_TABS = 2;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DisplayOptions {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NavigationMode {
    }

    public interface OnMenuVisibilityListener {
        void onMenuVisibilityChanged(boolean z);
    }

    @java.lang.Deprecated
    public interface OnNavigationListener {
        boolean onNavigationItemSelected(int i, long j);
    }

    @java.lang.Deprecated
    public static abstract class Tab {
        public static final int INVALID_POSITION = -1;

        public abstract java.lang.CharSequence getContentDescription();

        public abstract android.view.View getCustomView();

        public abstract android.graphics.drawable.Drawable getIcon();

        public abstract int getPosition();

        public abstract java.lang.Object getTag();

        public abstract java.lang.CharSequence getText();

        public abstract void select();

        public abstract android.app.ActionBar.Tab setContentDescription(int i);

        public abstract android.app.ActionBar.Tab setContentDescription(java.lang.CharSequence charSequence);

        public abstract android.app.ActionBar.Tab setCustomView(int i);

        public abstract android.app.ActionBar.Tab setCustomView(android.view.View view);

        public abstract android.app.ActionBar.Tab setIcon(int i);

        public abstract android.app.ActionBar.Tab setIcon(android.graphics.drawable.Drawable drawable);

        public abstract android.app.ActionBar.Tab setTabListener(android.app.ActionBar.TabListener tabListener);

        public abstract android.app.ActionBar.Tab setTag(java.lang.Object obj);

        public abstract android.app.ActionBar.Tab setText(int i);

        public abstract android.app.ActionBar.Tab setText(java.lang.CharSequence charSequence);
    }

    @java.lang.Deprecated
    public interface TabListener {
        void onTabReselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction);

        void onTabSelected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction);

        void onTabUnselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction);
    }

    public abstract void addOnMenuVisibilityListener(android.app.ActionBar.OnMenuVisibilityListener onMenuVisibilityListener);

    @java.lang.Deprecated
    public abstract void addTab(android.app.ActionBar.Tab tab);

    @java.lang.Deprecated
    public abstract void addTab(android.app.ActionBar.Tab tab, int i);

    @java.lang.Deprecated
    public abstract void addTab(android.app.ActionBar.Tab tab, int i, boolean z);

    @java.lang.Deprecated
    public abstract void addTab(android.app.ActionBar.Tab tab, boolean z);

    public abstract android.view.View getCustomView();

    public abstract int getDisplayOptions();

    public abstract int getHeight();

    @java.lang.Deprecated
    public abstract int getNavigationItemCount();

    @java.lang.Deprecated
    public abstract int getNavigationMode();

    @java.lang.Deprecated
    public abstract int getSelectedNavigationIndex();

    @java.lang.Deprecated
    public abstract android.app.ActionBar.Tab getSelectedTab();

    public abstract java.lang.CharSequence getSubtitle();

    @java.lang.Deprecated
    public abstract android.app.ActionBar.Tab getTabAt(int i);

    @java.lang.Deprecated
    public abstract int getTabCount();

    public abstract java.lang.CharSequence getTitle();

    public abstract void hide();

    public abstract boolean isShowing();

    @java.lang.Deprecated
    public abstract android.app.ActionBar.Tab newTab();

    @java.lang.Deprecated
    public abstract void removeAllTabs();

    public abstract void removeOnMenuVisibilityListener(android.app.ActionBar.OnMenuVisibilityListener onMenuVisibilityListener);

    @java.lang.Deprecated
    public abstract void removeTab(android.app.ActionBar.Tab tab);

    @java.lang.Deprecated
    public abstract void removeTabAt(int i);

    @java.lang.Deprecated
    public abstract void selectTab(android.app.ActionBar.Tab tab);

    public abstract void setBackgroundDrawable(android.graphics.drawable.Drawable drawable);

    public abstract void setCustomView(int i);

    public abstract void setCustomView(android.view.View view);

    public abstract void setCustomView(android.view.View view, android.app.ActionBar.LayoutParams layoutParams);

    public abstract void setDisplayHomeAsUpEnabled(boolean z);

    public abstract void setDisplayOptions(int i);

    public abstract void setDisplayOptions(int i, int i2);

    public abstract void setDisplayShowCustomEnabled(boolean z);

    public abstract void setDisplayShowHomeEnabled(boolean z);

    public abstract void setDisplayShowTitleEnabled(boolean z);

    public abstract void setDisplayUseLogoEnabled(boolean z);

    public abstract void setIcon(int i);

    public abstract void setIcon(android.graphics.drawable.Drawable drawable);

    @java.lang.Deprecated
    public abstract void setListNavigationCallbacks(android.widget.SpinnerAdapter spinnerAdapter, android.app.ActionBar.OnNavigationListener onNavigationListener);

    public abstract void setLogo(int i);

    public abstract void setLogo(android.graphics.drawable.Drawable drawable);

    @java.lang.Deprecated
    public abstract void setNavigationMode(int i);

    @java.lang.Deprecated
    public abstract void setSelectedNavigationItem(int i);

    public abstract void setSubtitle(int i);

    public abstract void setSubtitle(java.lang.CharSequence charSequence);

    public abstract void setTitle(int i);

    public abstract void setTitle(java.lang.CharSequence charSequence);

    public abstract void show();

    public static class LayoutParams extends android.view.ViewGroup.MarginLayoutParams {

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT, mapping = {@android.view.ViewDebug.IntToString(from = -1, to = android.security.keystore.KeyProperties.DIGEST_NONE), @android.view.ViewDebug.IntToString(from = 0, to = android.security.keystore.KeyProperties.DIGEST_NONE), @android.view.ViewDebug.IntToString(from = 48, to = "TOP"), @android.view.ViewDebug.IntToString(from = 80, to = "BOTTOM"), @android.view.ViewDebug.IntToString(from = 3, to = "LEFT"), @android.view.ViewDebug.IntToString(from = 5, to = "RIGHT"), @android.view.ViewDebug.IntToString(from = android.view.Gravity.START, to = "START"), @android.view.ViewDebug.IntToString(from = android.view.Gravity.END, to = "END"), @android.view.ViewDebug.IntToString(from = 16, to = "CENTER_VERTICAL"), @android.view.ViewDebug.IntToString(from = 112, to = "FILL_VERTICAL"), @android.view.ViewDebug.IntToString(from = 1, to = "CENTER_HORIZONTAL"), @android.view.ViewDebug.IntToString(from = 7, to = "FILL_HORIZONTAL"), @android.view.ViewDebug.IntToString(from = 17, to = "CENTER"), @android.view.ViewDebug.IntToString(from = 119, to = "FILL")})
        public int gravity;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.app.ActionBar.LayoutParams> {
            private int mLayout_gravityId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
                this.mLayout_gravityId = propertyMapper.mapGravity("layout_gravity", 16842931);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(android.app.ActionBar.LayoutParams layoutParams, android.view.inspector.PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readGravity(this.mLayout_gravityId, layoutParams.gravity);
            }
        }

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = 0;
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ActionBar_LayoutParams);
            this.gravity = obtainStyledAttributes.getInt(0, 0);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = 0;
            this.gravity = 8388627;
        }

        public LayoutParams(int i, int i2, int i3) {
            super(i, i2);
            this.gravity = 0;
            this.gravity = i3;
        }

        public LayoutParams(int i) {
            this(-2, -1, i);
        }

        public LayoutParams(android.app.ActionBar.LayoutParams layoutParams) {
            super((android.view.ViewGroup.MarginLayoutParams) layoutParams);
            this.gravity = 0;
            this.gravity = layoutParams.gravity;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = 0;
        }

        @Override // android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("gravity", this.gravity);
        }
    }

    public void setStackedBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
    }

    public void setSplitBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
    }

    public void setHomeButtonEnabled(boolean z) {
    }

    public android.content.Context getThemedContext() {
        return null;
    }

    public boolean isTitleTruncated() {
        return false;
    }

    public void setHomeAsUpIndicator(android.graphics.drawable.Drawable drawable) {
    }

    public void setHomeAsUpIndicator(int i) {
    }

    public void setHomeActionContentDescription(java.lang.CharSequence charSequence) {
    }

    public void setHomeActionContentDescription(int i) {
    }

    public void setHideOnContentScrollEnabled(boolean z) {
        if (z) {
            throw new java.lang.UnsupportedOperationException("Hide on content scroll is not supported in this action bar configuration.");
        }
    }

    public boolean isHideOnContentScrollEnabled() {
        return false;
    }

    public int getHideOffset() {
        return 0;
    }

    public void setHideOffset(int i) {
        if (i != 0) {
            throw new java.lang.UnsupportedOperationException("Setting an explicit action bar hide offset is not supported in this action bar configuration.");
        }
    }

    public void setElevation(float f) {
        if (f != 0.0f) {
            throw new java.lang.UnsupportedOperationException("Setting a non-zero elevation is not supported in this action bar configuration.");
        }
    }

    public float getElevation() {
        return 0.0f;
    }

    public void setDefaultDisplayHomeAsUpEnabled(boolean z) {
    }

    public void setShowHideAnimationEnabled(boolean z) {
    }

    public void onConfigurationChanged(android.content.res.Configuration configuration) {
    }

    public void dispatchMenuVisibilityChanged(boolean z) {
    }

    public android.view.ActionMode startActionMode(android.view.ActionMode.Callback callback) {
        return null;
    }

    public boolean openOptionsMenu() {
        return false;
    }

    public boolean closeOptionsMenu() {
        return false;
    }

    public boolean invalidateOptionsMenu() {
        return false;
    }

    public boolean onMenuKeyEvent(android.view.KeyEvent keyEvent) {
        return false;
    }

    public boolean onKeyShortcut(int i, android.view.KeyEvent keyEvent) {
        return false;
    }

    public boolean collapseActionView() {
        return false;
    }

    public void setWindowTitle(java.lang.CharSequence charSequence) {
    }

    public void onDestroy() {
    }
}
