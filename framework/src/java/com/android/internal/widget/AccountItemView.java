package com.android.internal.widget;

/* loaded from: classes5.dex */
public class AccountItemView extends android.widget.LinearLayout {
    private android.widget.ImageView mAccountIcon;
    private android.widget.TextView mAccountName;
    private android.widget.TextView mAccountNumber;

    public AccountItemView(android.content.Context context) {
        this(context, null);
    }

    public AccountItemView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        android.view.View inflate = ((android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(com.android.internal.R.layout.simple_account_item, (android.view.ViewGroup) null);
        addView(inflate);
        initViewItem(inflate);
    }

    private void initViewItem(android.view.View view) {
        this.mAccountIcon = (android.widget.ImageView) view.findViewById(16908294);
        this.mAccountName = (android.widget.TextView) view.findViewById(16908310);
        this.mAccountNumber = (android.widget.TextView) view.findViewById(16908304);
    }

    public void setViewItem(com.android.internal.widget.AccountViewAdapter.AccountElements accountElements) {
        android.graphics.drawable.Drawable drawable = accountElements.getDrawable();
        if (drawable != null) {
            setAccountIcon(drawable);
        } else {
            setAccountIcon(accountElements.getIcon());
        }
        setAccountName(accountElements.getName());
        setAccountNumber(accountElements.getNumber());
    }

    public void setAccountIcon(int i) {
        this.mAccountIcon.setImageResource(i);
    }

    public void setAccountIcon(android.graphics.drawable.Drawable drawable) {
        this.mAccountIcon.setBackgroundDrawable(drawable);
    }

    public void setAccountName(java.lang.String str) {
        setText(this.mAccountName, str);
    }

    public void setAccountNumber(java.lang.String str) {
        setText(this.mAccountNumber, str);
    }

    private void setText(android.widget.TextView textView, java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            textView.setVisibility(8);
        } else {
            textView.lambda$setTextAsync$0(str);
            textView.setVisibility(0);
        }
    }
}
