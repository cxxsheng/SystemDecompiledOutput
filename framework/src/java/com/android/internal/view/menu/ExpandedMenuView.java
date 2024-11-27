package com.android.internal.view.menu;

/* loaded from: classes5.dex */
public final class ExpandedMenuView extends android.widget.ListView implements com.android.internal.view.menu.MenuBuilder.ItemInvoker, com.android.internal.view.menu.MenuView, android.widget.AdapterView.OnItemClickListener {
    private int mAnimations;
    private com.android.internal.view.menu.MenuBuilder mMenu;

    public ExpandedMenuView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.MenuView, 0, 0);
        this.mAnimations = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        setOnItemClickListener(this);
    }

    @Override // com.android.internal.view.menu.MenuView
    public void initialize(com.android.internal.view.menu.MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setChildrenDrawingCacheEnabled(false);
    }

    @Override // com.android.internal.view.menu.MenuBuilder.ItemInvoker
    public boolean invokeItem(com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        return this.mMenu.performItemAction(menuItemImpl, 0);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(android.widget.AdapterView adapterView, android.view.View view, int i, long j) {
        invokeItem((com.android.internal.view.menu.MenuItemImpl) getAdapter().getItem(i));
    }

    @Override // com.android.internal.view.menu.MenuView
    public int getWindowAnimations() {
        return this.mAnimations;
    }
}
