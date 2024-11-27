package com.android.internal.view.menu;

/* loaded from: classes5.dex */
public class MenuDialogHelper implements com.android.internal.view.menu.MenuHelper, android.content.DialogInterface.OnKeyListener, android.content.DialogInterface.OnClickListener, android.content.DialogInterface.OnDismissListener, com.android.internal.view.menu.MenuPresenter.Callback {
    private android.app.AlertDialog mDialog;
    private com.android.internal.view.menu.MenuBuilder mMenu;
    com.android.internal.view.menu.ListMenuPresenter mPresenter;
    private com.android.internal.view.menu.MenuPresenter.Callback mPresenterCallback;

    public MenuDialogHelper(com.android.internal.view.menu.MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    public void show(android.os.IBinder iBinder) {
        com.android.internal.view.menu.MenuBuilder menuBuilder = this.mMenu;
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(menuBuilder.getContext());
        this.mPresenter = new com.android.internal.view.menu.ListMenuPresenter(builder.getContext(), com.android.internal.R.layout.list_menu_item_layout);
        this.mPresenter.setCallback(this);
        this.mMenu.addMenuPresenter(this.mPresenter);
        builder.setAdapter(this.mPresenter.getAdapter(), this);
        android.view.View headerView = menuBuilder.getHeaderView();
        if (headerView != null) {
            builder.setCustomTitle(headerView);
        } else {
            builder.setIcon(menuBuilder.getHeaderIcon()).setTitle(menuBuilder.getHeaderTitle());
        }
        builder.setOnKeyListener(this);
        this.mDialog = builder.create();
        this.mDialog.setOnDismissListener(this);
        android.view.WindowManager.LayoutParams attributes = this.mDialog.getWindow().getAttributes();
        attributes.type = 1003;
        if (iBinder != null) {
            attributes.token = iBinder;
        }
        attributes.flags |= 131072;
        this.mDialog.show();
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(android.content.DialogInterface dialogInterface, int i, android.view.KeyEvent keyEvent) {
        android.view.Window window;
        android.view.View decorView;
        android.view.KeyEvent.DispatcherState keyDispatcherState;
        android.view.View decorView2;
        android.view.KeyEvent.DispatcherState keyDispatcherState2;
        if (i == 82 || i == 4) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                android.view.Window window2 = this.mDialog.getWindow();
                if (window2 != null && (decorView2 = window2.getDecorView()) != null && (keyDispatcherState2 = decorView2.getKeyDispatcherState()) != null) {
                    keyDispatcherState2.startTracking(keyEvent, this);
                    return true;
                }
            } else if (keyEvent.getAction() == 1 && !keyEvent.isCanceled() && (window = this.mDialog.getWindow()) != null && (decorView = window.getDecorView()) != null && (keyDispatcherState = decorView.getKeyDispatcherState()) != null && keyDispatcherState.isTracking(keyEvent)) {
                this.mMenu.close(true);
                dialogInterface.dismiss();
                return true;
            }
        }
        return this.mMenu.performShortcut(i, keyEvent, 0);
    }

    @Override // com.android.internal.view.menu.MenuHelper
    public void setPresenterCallback(com.android.internal.view.menu.MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
    }

    @Override // com.android.internal.view.menu.MenuHelper
    public void dismiss() {
        if (this.mDialog != null) {
            this.mDialog.dismiss();
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(android.content.DialogInterface dialogInterface) {
        this.mPresenter.onCloseMenu(this.mMenu, true);
    }

    @Override // com.android.internal.view.menu.MenuPresenter.Callback
    public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
        if (z || menuBuilder == this.mMenu) {
            dismiss();
        }
        if (this.mPresenterCallback != null) {
            this.mPresenterCallback.onCloseMenu(menuBuilder, z);
        }
    }

    @Override // com.android.internal.view.menu.MenuPresenter.Callback
    public boolean onOpenSubMenu(com.android.internal.view.menu.MenuBuilder menuBuilder) {
        if (this.mPresenterCallback != null) {
            return this.mPresenterCallback.onOpenSubMenu(menuBuilder);
        }
        return false;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        this.mMenu.performItemAction((com.android.internal.view.menu.MenuItemImpl) this.mPresenter.getAdapter().getItem(i), 0);
    }
}
