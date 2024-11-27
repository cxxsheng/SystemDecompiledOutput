package com.android.internal.accessibility.dialog;

/* loaded from: classes4.dex */
class ButtonTargetAdapter extends com.android.internal.accessibility.dialog.TargetAdapter {
    private java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> mTargets;

    ButtonTargetAdapter(java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> list) {
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
        android.view.View inflate = android.view.LayoutInflater.from(viewGroup.getContext()).inflate(com.android.internal.R.layout.accessibility_button_chooser_item, viewGroup, false);
        com.android.internal.accessibility.dialog.AccessibilityTarget accessibilityTarget = this.mTargets.get(i);
        android.widget.ImageView imageView = (android.widget.ImageView) inflate.findViewById(com.android.internal.R.id.accessibility_button_target_icon);
        android.widget.TextView textView = (android.widget.TextView) inflate.findViewById(com.android.internal.R.id.accessibility_button_target_label);
        imageView.lambda$setImageURIAsync$2(accessibilityTarget.getIcon());
        textView.lambda$setTextAsync$0(accessibilityTarget.getLabel());
        return inflate;
    }
}
