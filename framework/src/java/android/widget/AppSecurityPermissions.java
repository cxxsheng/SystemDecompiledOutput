package android.widget;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class AppSecurityPermissions {
    public static android.view.View getPermissionItemView(android.content.Context context, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, boolean z) {
        return getPermissionItemViewOld(context, (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE), charSequence, charSequence2, z, context.getDrawable(z ? com.android.internal.R.drawable.ic_bullet_key_permission : com.android.internal.R.drawable.ic_text_dot));
    }

    private static android.view.View getPermissionItemViewOld(android.content.Context context, android.view.LayoutInflater layoutInflater, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, boolean z, android.graphics.drawable.Drawable drawable) {
        android.view.View inflate = layoutInflater.inflate(com.android.internal.R.layout.app_permission_item_old, (android.view.ViewGroup) null);
        android.widget.TextView textView = (android.widget.TextView) inflate.findViewById(com.android.internal.R.id.permission_group);
        android.widget.TextView textView2 = (android.widget.TextView) inflate.findViewById(com.android.internal.R.id.permission_list);
        ((android.widget.ImageView) inflate.findViewById(com.android.internal.R.id.perm_icon)).lambda$setImageURIAsync$2(drawable);
        if (charSequence != null) {
            textView.lambda$setTextAsync$0(charSequence);
            textView2.lambda$setTextAsync$0(charSequence2);
        } else {
            textView.lambda$setTextAsync$0(charSequence2);
            textView2.setVisibility(8);
        }
        return inflate;
    }
}
