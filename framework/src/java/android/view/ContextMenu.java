package android.view;

/* loaded from: classes4.dex */
public interface ContextMenu extends android.view.Menu {

    public interface ContextMenuInfo {
    }

    void clearHeader();

    android.view.ContextMenu setHeaderIcon(int i);

    android.view.ContextMenu setHeaderIcon(android.graphics.drawable.Drawable drawable);

    android.view.ContextMenu setHeaderTitle(int i);

    android.view.ContextMenu setHeaderTitle(java.lang.CharSequence charSequence);

    android.view.ContextMenu setHeaderView(android.view.View view);
}
