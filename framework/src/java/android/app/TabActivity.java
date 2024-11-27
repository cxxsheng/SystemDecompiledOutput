package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class TabActivity extends android.app.ActivityGroup {
    private java.lang.String mDefaultTab = null;
    private int mDefaultTabIndex = -1;
    private android.widget.TabHost mTabHost;

    public void setDefaultTab(java.lang.String str) {
        this.mDefaultTab = str;
        this.mDefaultTabIndex = -1;
    }

    public void setDefaultTab(int i) {
        this.mDefaultTab = null;
        this.mDefaultTabIndex = i;
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(android.os.Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        ensureTabHost();
        java.lang.String string = bundle.getString("currentTab");
        if (string != null) {
            this.mTabHost.setCurrentTabByTag(string);
        }
        if (this.mTabHost.getCurrentTab() < 0) {
            if (this.mDefaultTab != null) {
                this.mTabHost.setCurrentTabByTag(this.mDefaultTab);
            } else if (this.mDefaultTabIndex >= 0) {
                this.mTabHost.setCurrentTab(this.mDefaultTabIndex);
            }
        }
    }

    @Override // android.app.Activity
    protected void onPostCreate(android.os.Bundle bundle) {
        super.onPostCreate(bundle);
        ensureTabHost();
        if (this.mTabHost.getCurrentTab() == -1) {
            this.mTabHost.setCurrentTab(0);
        }
    }

    @Override // android.app.ActivityGroup, android.app.Activity
    protected void onSaveInstanceState(android.os.Bundle bundle) {
        super.onSaveInstanceState(bundle);
        java.lang.String currentTabTag = this.mTabHost.getCurrentTabTag();
        if (currentTabTag != null) {
            bundle.putString("currentTab", currentTabTag);
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onContentChanged() {
        super.onContentChanged();
        this.mTabHost = (android.widget.TabHost) findViewById(16908306);
        if (this.mTabHost == null) {
            throw new java.lang.RuntimeException("Your content must have a TabHost whose id attribute is 'android.R.id.tabhost'");
        }
        this.mTabHost.setup(getLocalActivityManager());
    }

    private void ensureTabHost() {
        if (this.mTabHost == null) {
            setContentView(com.android.internal.R.layout.tab_content);
        }
    }

    @Override // android.app.Activity
    protected void onChildTitleChanged(android.app.Activity activity, java.lang.CharSequence charSequence) {
        android.view.View currentTabView;
        if (getLocalActivityManager().getCurrentActivity() == activity && (currentTabView = this.mTabHost.getCurrentTabView()) != null && (currentTabView instanceof android.widget.TextView)) {
            ((android.widget.TextView) currentTabView).lambda$setTextAsync$0(charSequence);
        }
    }

    public android.widget.TabHost getTabHost() {
        ensureTabHost();
        return this.mTabHost;
    }

    public android.widget.TabWidget getTabWidget() {
        return this.mTabHost.getTabWidget();
    }
}
