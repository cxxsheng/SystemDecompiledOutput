package com.android.internal.globalactions;

/* loaded from: classes4.dex */
public abstract class SinglePressAction implements com.android.internal.globalactions.Action {
    private final android.graphics.drawable.Drawable mIcon;
    private final int mIconResId;
    private final java.lang.CharSequence mMessage;
    private final int mMessageResId;

    @Override // com.android.internal.globalactions.Action
    public abstract void onPress();

    protected SinglePressAction(int i, int i2) {
        this.mIconResId = i;
        this.mMessageResId = i2;
        this.mMessage = null;
        this.mIcon = null;
    }

    protected SinglePressAction(int i, android.graphics.drawable.Drawable drawable, java.lang.CharSequence charSequence) {
        this.mIconResId = i;
        this.mMessageResId = 0;
        this.mMessage = charSequence;
        this.mIcon = drawable;
    }

    @Override // com.android.internal.globalactions.Action
    public boolean isEnabled() {
        return true;
    }

    public java.lang.String getStatus() {
        return null;
    }

    @Override // com.android.internal.globalactions.Action
    public java.lang.CharSequence getLabelForAccessibility(android.content.Context context) {
        if (this.mMessage != null) {
            return this.mMessage;
        }
        return context.getString(this.mMessageResId);
    }

    @Override // com.android.internal.globalactions.Action
    public android.view.View create(android.content.Context context, android.view.View view, android.view.ViewGroup viewGroup, android.view.LayoutInflater layoutInflater) {
        android.view.View inflate = layoutInflater.inflate(com.android.internal.R.layout.global_actions_item, viewGroup, false);
        android.widget.ImageView imageView = (android.widget.ImageView) inflate.findViewById(16908294);
        android.widget.TextView textView = (android.widget.TextView) inflate.findViewById(16908299);
        android.widget.TextView textView2 = (android.widget.TextView) inflate.findViewById(com.android.internal.R.id.status);
        java.lang.String status = getStatus();
        if (textView2 != null) {
            if (!android.text.TextUtils.isEmpty(status)) {
                textView2.lambda$setTextAsync$0(status);
            } else {
                textView2.setVisibility(8);
            }
        }
        if (imageView != null) {
            if (this.mIcon != null) {
                imageView.lambda$setImageURIAsync$2(this.mIcon);
                imageView.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
            } else if (this.mIconResId != 0) {
                imageView.lambda$setImageURIAsync$2(context.getDrawable(this.mIconResId));
            }
        }
        if (textView != null) {
            if (this.mMessage != null) {
                textView.lambda$setTextAsync$0(this.mMessage);
            } else {
                textView.setText(this.mMessageResId);
            }
        }
        return inflate;
    }
}
