package com.android.internal.view.menu;

/* loaded from: classes5.dex */
public class ContextMenuBuilder extends com.android.internal.view.menu.MenuBuilder implements android.view.ContextMenu {
    public ContextMenuBuilder(android.content.Context context) {
        super(context);
    }

    @Override // android.view.ContextMenu
    public android.view.ContextMenu setHeaderIcon(android.graphics.drawable.Drawable drawable) {
        return (android.view.ContextMenu) super.setHeaderIconInt(drawable);
    }

    @Override // android.view.ContextMenu
    public android.view.ContextMenu setHeaderIcon(int i) {
        return (android.view.ContextMenu) super.setHeaderIconInt(i);
    }

    @Override // android.view.ContextMenu
    public android.view.ContextMenu setHeaderTitle(java.lang.CharSequence charSequence) {
        return (android.view.ContextMenu) super.setHeaderTitleInt(charSequence);
    }

    @Override // android.view.ContextMenu
    public android.view.ContextMenu setHeaderTitle(int i) {
        return (android.view.ContextMenu) super.setHeaderTitleInt(i);
    }

    @Override // android.view.ContextMenu
    public android.view.ContextMenu setHeaderView(android.view.View view) {
        return (android.view.ContextMenu) super.setHeaderViewInt(view);
    }

    public com.android.internal.view.menu.MenuDialogHelper showDialog(android.view.View view, android.os.IBinder iBinder) {
        if (view != null) {
            view.createContextMenu(this);
        }
        if (getVisibleItems().size() > 0) {
            android.util.EventLog.writeEvent(android.os.health.ServiceHealthStats.MEASUREMENT_START_SERVICE_COUNT, 1);
            com.android.internal.view.menu.MenuDialogHelper menuDialogHelper = new com.android.internal.view.menu.MenuDialogHelper(this);
            menuDialogHelper.show(iBinder);
            return menuDialogHelper;
        }
        return null;
    }

    public com.android.internal.view.menu.MenuPopupHelper showPopup(android.content.Context context, android.view.View view, float f, float f2) {
        if (view != null) {
            view.createContextMenu(this);
        }
        if (getVisibleItems().size() > 0) {
            android.util.EventLog.writeEvent(android.os.health.ServiceHealthStats.MEASUREMENT_START_SERVICE_COUNT, 1);
            view.getLocationOnScreen(new int[2]);
            com.android.internal.view.menu.MenuPopupHelper menuPopupHelper = new com.android.internal.view.menu.MenuPopupHelper(context, this, view, false, 16844033);
            menuPopupHelper.show(java.lang.Math.round(f), java.lang.Math.round(f2));
            return menuPopupHelper;
        }
        return null;
    }
}
