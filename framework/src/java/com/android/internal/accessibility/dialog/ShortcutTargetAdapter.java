package com.android.internal.accessibility.dialog;

/* loaded from: classes4.dex */
class ShortcutTargetAdapter extends com.android.internal.accessibility.dialog.TargetAdapter {
    private int mShortcutMenuMode = 0;
    private final java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> mTargets;

    ShortcutTargetAdapter(java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> list) {
        this.mTargets = list;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mTargets.size();
    }

    @Override // android.widget.Adapter
    public java.lang.Object getItem(int i) {
        return this.mTargets.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        com.android.internal.accessibility.dialog.TargetAdapter.ViewHolder viewHolder;
        android.content.Context context = viewGroup.getContext();
        if (view == null) {
            view = android.view.LayoutInflater.from(context).inflate(com.android.internal.R.layout.accessibility_shortcut_chooser_item, viewGroup, false);
            viewHolder = new com.android.internal.accessibility.dialog.TargetAdapter.ViewHolder();
            viewHolder.mCheckBoxView = (android.widget.CheckBox) view.findViewById(com.android.internal.R.id.accessibility_shortcut_target_checkbox);
            viewHolder.mIconView = (android.widget.ImageView) view.findViewById(com.android.internal.R.id.accessibility_shortcut_target_icon);
            viewHolder.mLabelView = (android.widget.TextView) view.findViewById(com.android.internal.R.id.accessibility_shortcut_target_label);
            viewHolder.mStatusView = (android.widget.TextView) view.findViewById(com.android.internal.R.id.accessibility_shortcut_target_status);
            view.setTag(viewHolder);
        } else {
            viewHolder = (com.android.internal.accessibility.dialog.TargetAdapter.ViewHolder) view.getTag();
        }
        this.mTargets.get(i).updateActionItem(viewHolder, this.mShortcutMenuMode);
        return view;
    }

    void setShortcutMenuMode(int i) {
        this.mShortcutMenuMode = i;
    }

    int getShortcutMenuMode() {
        return this.mShortcutMenuMode;
    }
}
