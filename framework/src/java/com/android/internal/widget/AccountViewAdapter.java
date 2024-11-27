package com.android.internal.widget;

/* loaded from: classes5.dex */
public class AccountViewAdapter extends android.widget.BaseAdapter {
    private android.content.Context mContext;
    private java.util.List<com.android.internal.widget.AccountViewAdapter.AccountElements> mData;

    public AccountViewAdapter(android.content.Context context, java.util.List<com.android.internal.widget.AccountViewAdapter.AccountElements> list) {
        this.mContext = context;
        this.mData = list;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mData.size();
    }

    @Override // android.widget.Adapter
    public java.lang.Object getItem(int i) {
        return this.mData.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public void updateData(java.util.List<com.android.internal.widget.AccountViewAdapter.AccountElements> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    @Override // android.widget.Adapter
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        com.android.internal.widget.AccountItemView accountItemView;
        if (view == null) {
            accountItemView = new com.android.internal.widget.AccountItemView(this.mContext);
        } else {
            accountItemView = (com.android.internal.widget.AccountItemView) view;
        }
        accountItemView.setViewItem((com.android.internal.widget.AccountViewAdapter.AccountElements) getItem(i));
        return accountItemView;
    }

    public static class AccountElements {
        private android.graphics.drawable.Drawable mDrawable;
        private int mIcon;
        private java.lang.String mName;
        private java.lang.String mNumber;

        public AccountElements(int i, java.lang.String str, java.lang.String str2) {
            this(i, null, str, str2);
        }

        public AccountElements(android.graphics.drawable.Drawable drawable, java.lang.String str, java.lang.String str2) {
            this(0, drawable, str, str2);
        }

        private AccountElements(int i, android.graphics.drawable.Drawable drawable, java.lang.String str, java.lang.String str2) {
            this.mIcon = i;
            this.mDrawable = drawable;
            this.mName = str;
            this.mNumber = str2;
        }

        public int getIcon() {
            return this.mIcon;
        }

        public java.lang.String getName() {
            return this.mName;
        }

        public java.lang.String getNumber() {
            return this.mNumber;
        }

        public android.graphics.drawable.Drawable getDrawable() {
            return this.mDrawable;
        }
    }
}
