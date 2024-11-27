package android.widget;

/* loaded from: classes4.dex */
public class PopupMenu {
    private final android.view.View mAnchor;
    private final android.content.Context mContext;
    private android.view.View.OnTouchListener mDragListener;
    private final com.android.internal.view.menu.MenuBuilder mMenu;
    private android.widget.PopupMenu.OnMenuItemClickListener mMenuItemClickListener;
    private android.widget.PopupMenu.OnDismissListener mOnDismissListener;
    private final com.android.internal.view.menu.MenuPopupHelper mPopup;

    public interface OnDismissListener {
        void onDismiss(android.widget.PopupMenu popupMenu);
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(android.view.MenuItem menuItem);
    }

    public PopupMenu(android.content.Context context, android.view.View view) {
        this(context, view, 0);
    }

    public PopupMenu(android.content.Context context, android.view.View view, int i) {
        this(context, view, i, 16843520, 0);
    }

    public PopupMenu(android.content.Context context, android.view.View view, int i, int i2, int i3) {
        this.mContext = context;
        this.mAnchor = view;
        this.mMenu = new com.android.internal.view.menu.MenuBuilder(context);
        this.mMenu.setCallback(new com.android.internal.view.menu.MenuBuilder.Callback() { // from class: android.widget.PopupMenu.1
            @Override // com.android.internal.view.menu.MenuBuilder.Callback
            public boolean onMenuItemSelected(com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.MenuItem menuItem) {
                if (android.widget.PopupMenu.this.mMenuItemClickListener != null) {
                    return android.widget.PopupMenu.this.mMenuItemClickListener.onMenuItemClick(menuItem);
                }
                return false;
            }

            @Override // com.android.internal.view.menu.MenuBuilder.Callback
            public void onMenuModeChange(com.android.internal.view.menu.MenuBuilder menuBuilder) {
            }
        });
        this.mPopup = new com.android.internal.view.menu.MenuPopupHelper(context, this.mMenu, view, false, i2, i3);
        this.mPopup.setGravity(i);
        this.mPopup.setOnDismissListener(new android.widget.PopupWindow.OnDismissListener() { // from class: android.widget.PopupMenu.2
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                if (android.widget.PopupMenu.this.mOnDismissListener != null) {
                    android.widget.PopupMenu.this.mOnDismissListener.onDismiss(android.widget.PopupMenu.this);
                }
            }
        });
    }

    public void setGravity(int i) {
        this.mPopup.setGravity(i);
    }

    public int getGravity() {
        return this.mPopup.getGravity();
    }

    public android.view.View.OnTouchListener getDragToOpenListener() {
        if (this.mDragListener == null) {
            this.mDragListener = new android.widget.ForwardingListener(this.mAnchor) { // from class: android.widget.PopupMenu.3
                @Override // android.widget.ForwardingListener
                protected boolean onForwardingStarted() {
                    android.widget.PopupMenu.this.show();
                    return true;
                }

                @Override // android.widget.ForwardingListener
                protected boolean onForwardingStopped() {
                    android.widget.PopupMenu.this.dismiss();
                    return true;
                }

                @Override // android.widget.ForwardingListener
                public com.android.internal.view.menu.ShowableListMenu getPopup() {
                    return android.widget.PopupMenu.this.mPopup.getPopup();
                }
            };
        }
        return this.mDragListener;
    }

    public android.view.Menu getMenu() {
        return this.mMenu;
    }

    public android.view.MenuInflater getMenuInflater() {
        return new android.view.MenuInflater(this.mContext);
    }

    public void inflate(int i) {
        getMenuInflater().inflate(i, this.mMenu);
    }

    public void show() {
        this.mPopup.show();
    }

    public void dismiss() {
        this.mPopup.dismiss();
    }

    public void setOnMenuItemClickListener(android.widget.PopupMenu.OnMenuItemClickListener onMenuItemClickListener) {
        this.mMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOnDismissListener(android.widget.PopupMenu.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setForceShowIcon(boolean z) {
        this.mPopup.setForceShowIcon(z);
    }

    public android.widget.ListView getMenuListView() {
        if (!this.mPopup.isShowing()) {
            return null;
        }
        return this.mPopup.getPopup().getListView();
    }
}
