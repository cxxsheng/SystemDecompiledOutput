package com.android.internal.view;

/* loaded from: classes5.dex */
public class ActionBarPolicy {
    private android.content.Context mContext;

    public static com.android.internal.view.ActionBarPolicy get(android.content.Context context) {
        return new com.android.internal.view.ActionBarPolicy(context);
    }

    private ActionBarPolicy(android.content.Context context) {
        this.mContext = context;
    }

    public int getMaxActionButtons() {
        android.content.res.Configuration configuration = this.mContext.getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        int i2 = configuration.screenHeightDp;
        if (configuration.smallestScreenWidthDp > 600) {
            return 5;
        }
        if (i > 960 && i2 > 720) {
            return 5;
        }
        if (i > 720 && i2 > 960) {
            return 5;
        }
        if (i >= 500) {
            return 4;
        }
        if (i > 640 && i2 > 480) {
            return 4;
        }
        if (i > 480 && i2 > 640) {
            return 4;
        }
        if (i >= 360) {
            return 3;
        }
        return 2;
    }

    public boolean showsOverflowMenuButton() {
        return true;
    }

    public int getEmbeddedMenuWidthLimit() {
        return this.mContext.getResources().getDisplayMetrics().widthPixels / 2;
    }

    public boolean hasEmbeddedTabs() {
        if (this.mContext.getApplicationInfo().targetSdkVersion >= 16) {
            return this.mContext.getResources().getBoolean(com.android.internal.R.bool.action_bar_embed_tabs);
        }
        android.content.res.Configuration configuration = this.mContext.getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        return configuration.orientation == 2 || i >= 480 || (i >= 640 && configuration.screenHeightDp >= 480);
    }

    public int getTabContainerHeight() {
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(null, com.android.internal.R.styleable.ActionBar, 16843470, 0);
        int layoutDimension = obtainStyledAttributes.getLayoutDimension(4, 0);
        android.content.res.Resources resources = this.mContext.getResources();
        if (!hasEmbeddedTabs()) {
            layoutDimension = java.lang.Math.min(layoutDimension, resources.getDimensionPixelSize(com.android.internal.R.dimen.action_bar_stacked_max_height));
        }
        obtainStyledAttributes.recycle();
        return layoutDimension;
    }

    public boolean enableHomeButtonByDefault() {
        return this.mContext.getApplicationInfo().targetSdkVersion < 14;
    }

    public int getStackedTabMaxWidth() {
        return this.mContext.getResources().getDimensionPixelSize(com.android.internal.R.dimen.action_bar_stacked_tab_max_width);
    }
}
