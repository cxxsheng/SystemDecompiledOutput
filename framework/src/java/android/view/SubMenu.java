package android.view;

/* loaded from: classes4.dex */
public interface SubMenu extends android.view.Menu {
    void clearHeader();

    android.view.MenuItem getItem();

    android.view.SubMenu setHeaderIcon(int i);

    android.view.SubMenu setHeaderIcon(android.graphics.drawable.Drawable drawable);

    android.view.SubMenu setHeaderTitle(int i);

    android.view.SubMenu setHeaderTitle(java.lang.CharSequence charSequence);

    android.view.SubMenu setHeaderView(android.view.View view);

    android.view.SubMenu setIcon(int i);

    android.view.SubMenu setIcon(android.graphics.drawable.Drawable drawable);
}
