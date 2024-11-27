package com.android.internal.widget;

/* loaded from: classes5.dex */
public interface DecorContentParent {
    boolean canShowOverflowMenu();

    void dismissPopups();

    java.lang.CharSequence getTitle();

    boolean hasIcon();

    boolean hasLogo();

    boolean hideOverflowMenu();

    void initFeature(int i);

    boolean isOverflowMenuShowPending();

    boolean isOverflowMenuShowing();

    void restoreToolbarHierarchyState(android.util.SparseArray<android.os.Parcelable> sparseArray);

    void saveToolbarHierarchyState(android.util.SparseArray<android.os.Parcelable> sparseArray);

    void setIcon(int i);

    void setIcon(android.graphics.drawable.Drawable drawable);

    void setLogo(int i);

    void setMenu(android.view.Menu menu, com.android.internal.view.menu.MenuPresenter.Callback callback);

    void setMenuPrepared();

    void setUiOptions(int i);

    void setWindowCallback(android.view.Window.Callback callback);

    void setWindowTitle(java.lang.CharSequence charSequence);

    boolean showOverflowMenu();
}
