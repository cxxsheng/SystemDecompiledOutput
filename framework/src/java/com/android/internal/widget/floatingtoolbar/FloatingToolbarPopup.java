package com.android.internal.widget.floatingtoolbar;

/* loaded from: classes5.dex */
public interface FloatingToolbarPopup {
    void dismiss();

    void hide();

    boolean isHidden();

    boolean isShowing();

    boolean setOutsideTouchable(boolean z, android.widget.PopupWindow.OnDismissListener onDismissListener);

    void setSuggestedWidth(int i);

    void setWidthChanged(boolean z);

    void show(java.util.List<android.view.MenuItem> list, android.view.MenuItem.OnMenuItemClickListener onMenuItemClickListener, android.graphics.Rect rect);

    static com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup createInstance(android.content.Context context, android.view.View view) {
        return new com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup(context, view);
    }
}
