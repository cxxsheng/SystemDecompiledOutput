package com.android.internal.view;

/* loaded from: classes5.dex */
public class StandaloneActionMode extends android.view.ActionMode implements com.android.internal.view.menu.MenuBuilder.Callback {
    private android.view.ActionMode.Callback mCallback;
    private android.content.Context mContext;
    private com.android.internal.widget.ActionBarContextView mContextView;
    private java.lang.ref.WeakReference<android.view.View> mCustomView;
    private boolean mFinished;
    private boolean mFocusable;
    private com.android.internal.view.menu.MenuBuilder mMenu;

    public StandaloneActionMode(android.content.Context context, com.android.internal.widget.ActionBarContextView actionBarContextView, android.view.ActionMode.Callback callback, boolean z) {
        this.mContext = context;
        this.mContextView = actionBarContextView;
        this.mCallback = callback;
        this.mMenu = new com.android.internal.view.menu.MenuBuilder(actionBarContextView.getContext()).setDefaultShowAsAction(1);
        this.mMenu.setCallback(this);
        this.mFocusable = z;
    }

    @Override // android.view.ActionMode
    public void setTitle(java.lang.CharSequence charSequence) {
        this.mContextView.setTitle(charSequence);
    }

    @Override // android.view.ActionMode
    public void setSubtitle(java.lang.CharSequence charSequence) {
        this.mContextView.setSubtitle(charSequence);
    }

    @Override // android.view.ActionMode
    public void setTitle(int i) {
        setTitle(i != 0 ? this.mContext.getString(i) : null);
    }

    @Override // android.view.ActionMode
    public void setSubtitle(int i) {
        setSubtitle(i != 0 ? this.mContext.getString(i) : null);
    }

    @Override // android.view.ActionMode
    public void setTitleOptionalHint(boolean z) {
        super.setTitleOptionalHint(z);
        this.mContextView.setTitleOptional(z);
    }

    @Override // android.view.ActionMode
    public boolean isTitleOptional() {
        return this.mContextView.isTitleOptional();
    }

    @Override // android.view.ActionMode
    public void setCustomView(android.view.View view) {
        this.mContextView.setCustomView(view);
        this.mCustomView = view != null ? new java.lang.ref.WeakReference<>(view) : null;
    }

    @Override // android.view.ActionMode
    public void invalidate() {
        this.mCallback.onPrepareActionMode(this, this.mMenu);
    }

    @Override // android.view.ActionMode
    public void finish() {
        if (this.mFinished) {
            return;
        }
        this.mFinished = true;
        this.mContextView.sendAccessibilityEvent(32);
        this.mCallback.onDestroyActionMode(this);
    }

    @Override // android.view.ActionMode
    public android.view.Menu getMenu() {
        return this.mMenu;
    }

    @Override // android.view.ActionMode
    public java.lang.CharSequence getTitle() {
        return this.mContextView.getTitle();
    }

    @Override // android.view.ActionMode
    public java.lang.CharSequence getSubtitle() {
        return this.mContextView.getSubtitle();
    }

    @Override // android.view.ActionMode
    public android.view.View getCustomView() {
        if (this.mCustomView != null) {
            return this.mCustomView.get();
        }
        return null;
    }

    @Override // android.view.ActionMode
    public android.view.MenuInflater getMenuInflater() {
        return new android.view.MenuInflater(this.mContextView.getContext());
    }

    @Override // com.android.internal.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.MenuItem menuItem) {
        return this.mCallback.onActionItemClicked(this, menuItem);
    }

    public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
    }

    public boolean onSubMenuSelected(com.android.internal.view.menu.SubMenuBuilder subMenuBuilder) {
        if (!subMenuBuilder.hasVisibleItems()) {
            return true;
        }
        new com.android.internal.view.menu.MenuPopupHelper(this.mContextView.getContext(), subMenuBuilder).show();
        return true;
    }

    public void onCloseSubMenu(com.android.internal.view.menu.SubMenuBuilder subMenuBuilder) {
    }

    @Override // com.android.internal.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(com.android.internal.view.menu.MenuBuilder menuBuilder) {
        invalidate();
        this.mContextView.showOverflowMenu();
    }

    @Override // android.view.ActionMode
    public boolean isUiFocusable() {
        return this.mFocusable;
    }
}
