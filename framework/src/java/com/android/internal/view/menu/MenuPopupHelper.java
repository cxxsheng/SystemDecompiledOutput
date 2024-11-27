package com.android.internal.view.menu;

/* loaded from: classes5.dex */
public class MenuPopupHelper implements com.android.internal.view.menu.MenuHelper {
    private static final int TOUCH_EPICENTER_SIZE_DP = 48;
    private android.view.View mAnchorView;
    private final android.content.Context mContext;
    private int mDropDownGravity;
    private boolean mForceShowIcon;
    private final android.widget.PopupWindow.OnDismissListener mInternalOnDismissListener;
    private final com.android.internal.view.menu.MenuBuilder mMenu;
    private android.widget.PopupWindow.OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    private com.android.internal.view.menu.MenuPopup mPopup;
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private com.android.internal.view.menu.MenuPresenter.Callback mPresenterCallback;

    public MenuPopupHelper(android.content.Context context, com.android.internal.view.menu.MenuBuilder menuBuilder) {
        this(context, menuBuilder, null, false, 16843520, 0);
    }

    public MenuPopupHelper(android.content.Context context, com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.View view) {
        this(context, menuBuilder, view, false, 16843520, 0);
    }

    public MenuPopupHelper(android.content.Context context, com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.View view, boolean z, int i) {
        this(context, menuBuilder, view, z, i, 0);
    }

    public MenuPopupHelper(android.content.Context context, com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.View view, boolean z, int i, int i2) {
        this.mDropDownGravity = android.view.Gravity.START;
        this.mInternalOnDismissListener = new android.widget.PopupWindow.OnDismissListener() { // from class: com.android.internal.view.menu.MenuPopupHelper.1
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                com.android.internal.view.menu.MenuPopupHelper.this.onDismiss();
            }
        };
        this.mContext = context;
        this.mMenu = menuBuilder;
        this.mAnchorView = view;
        this.mOverflowOnly = z;
        this.mPopupStyleAttr = i;
        this.mPopupStyleRes = i2;
    }

    public void setOnDismissListener(android.widget.PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setAnchorView(android.view.View view) {
        this.mAnchorView = view;
    }

    public void setForceShowIcon(boolean z) {
        this.mForceShowIcon = z;
        if (this.mPopup != null) {
            this.mPopup.setForceShowIcon(z);
        }
    }

    public void setGravity(int i) {
        this.mDropDownGravity = i;
    }

    public int getGravity() {
        return this.mDropDownGravity;
    }

    public void show() {
        if (!tryShow()) {
            throw new java.lang.IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public void show(int i, int i2) {
        if (!tryShow(i, i2)) {
            throw new java.lang.IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public com.android.internal.view.menu.MenuPopup getPopup() {
        if (this.mPopup == null) {
            this.mPopup = createPopup();
        }
        return this.mPopup;
    }

    public boolean tryShow() {
        if (isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        showPopup(0, 0, false, false);
        return true;
    }

    public boolean tryShow(int i, int i2) {
        if (isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        showPopup(i, i2, true, true);
        return true;
    }

    private com.android.internal.view.menu.MenuPopup createPopup() {
        com.android.internal.view.menu.MenuPopup standardMenuPopup;
        android.graphics.Rect bounds = ((android.view.WindowManager) this.mContext.getSystemService(android.view.WindowManager.class)).getMaximumWindowMetrics().getBounds();
        if (java.lang.Math.min(bounds.width(), bounds.height()) >= this.mContext.getResources().getDimensionPixelSize(com.android.internal.R.dimen.cascading_menus_min_smallest_width)) {
            standardMenuPopup = new com.android.internal.view.menu.CascadingMenuPopup(this.mContext, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly);
        } else {
            standardMenuPopup = new com.android.internal.view.menu.StandardMenuPopup(this.mContext, this.mMenu, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly);
        }
        standardMenuPopup.addMenu(this.mMenu);
        standardMenuPopup.setOnDismissListener(this.mInternalOnDismissListener);
        standardMenuPopup.setAnchorView(this.mAnchorView);
        standardMenuPopup.setCallback(this.mPresenterCallback);
        standardMenuPopup.setForceShowIcon(this.mForceShowIcon);
        standardMenuPopup.setGravity(this.mDropDownGravity);
        return standardMenuPopup;
    }

    private void showPopup(int i, int i2, boolean z, boolean z2) {
        com.android.internal.view.menu.MenuPopup popup = getPopup();
        popup.setShowTitle(z2);
        if (z) {
            if ((android.view.Gravity.getAbsoluteGravity(this.mDropDownGravity, this.mAnchorView.getLayoutDirection()) & 7) == 5) {
                i -= this.mAnchorView.getWidth();
            }
            popup.setHorizontalOffset(i);
            popup.setVerticalOffset(i2);
            int i3 = (int) ((this.mContext.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            popup.setEpicenterBounds(new android.graphics.Rect(i - i3, i2 - i3, i + i3, i2 + i3));
        }
        popup.show();
    }

    @Override // com.android.internal.view.menu.MenuHelper
    public void dismiss() {
        if (isShowing()) {
            this.mPopup.dismiss();
        }
    }

    protected void onDismiss() {
        this.mPopup = null;
        if (this.mOnDismissListener != null) {
            this.mOnDismissListener.onDismiss();
        }
    }

    public boolean isShowing() {
        return this.mPopup != null && this.mPopup.isShowing();
    }

    @Override // com.android.internal.view.menu.MenuHelper
    public void setPresenterCallback(com.android.internal.view.menu.MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
        if (this.mPopup != null) {
            this.mPopup.setCallback(callback);
        }
    }
}
