package com.android.internal.globalactions;

/* loaded from: classes4.dex */
public class ActionsAdapter extends android.widget.BaseAdapter {
    private final android.content.Context mContext;
    private final java.util.function.BooleanSupplier mDeviceProvisioned;
    private final java.util.List<com.android.internal.globalactions.Action> mItems;
    private final java.util.function.BooleanSupplier mKeyguardShowing;

    public ActionsAdapter(android.content.Context context, java.util.List<com.android.internal.globalactions.Action> list, java.util.function.BooleanSupplier booleanSupplier, java.util.function.BooleanSupplier booleanSupplier2) {
        this.mContext = context;
        this.mItems = list;
        this.mDeviceProvisioned = booleanSupplier;
        this.mKeyguardShowing = booleanSupplier2;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        boolean asBoolean = this.mKeyguardShowing.getAsBoolean();
        boolean asBoolean2 = this.mDeviceProvisioned.getAsBoolean();
        int i = 0;
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            com.android.internal.globalactions.Action action = this.mItems.get(i2);
            if ((!asBoolean || action.showDuringKeyguard()) && (asBoolean2 || action.showBeforeProvisioning())) {
                i++;
            }
        }
        return i;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return getItem(i).isEnabled();
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override // android.widget.Adapter
    public com.android.internal.globalactions.Action getItem(int i) {
        boolean asBoolean = this.mKeyguardShowing.getAsBoolean();
        boolean asBoolean2 = this.mDeviceProvisioned.getAsBoolean();
        int i2 = 0;
        for (int i3 = 0; i3 < this.mItems.size(); i3++) {
            com.android.internal.globalactions.Action action = this.mItems.get(i3);
            if ((!asBoolean || action.showDuringKeyguard()) && (asBoolean2 || action.showBeforeProvisioning())) {
                if (i2 == i) {
                    return action;
                }
                i2++;
            }
        }
        throw new java.lang.IllegalArgumentException("position " + i + " out of range of showable actions, filtered count=" + getCount() + ", keyguardshowing=" + asBoolean + ", provisioned=" + asBoolean2);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        return getItem(i).create(this.mContext, view, viewGroup, android.view.LayoutInflater.from(this.mContext));
    }
}
