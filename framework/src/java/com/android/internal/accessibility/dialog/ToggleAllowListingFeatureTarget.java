package com.android.internal.accessibility.dialog;

/* loaded from: classes4.dex */
class ToggleAllowListingFeatureTarget extends com.android.internal.accessibility.dialog.AccessibilityTarget {
    ToggleAllowListingFeatureTarget(android.content.Context context, int i, boolean z, java.lang.String str, int i2, java.lang.CharSequence charSequence, android.graphics.drawable.Drawable drawable, java.lang.String str2) {
        super(context, i, 2, z, str, i2, charSequence, drawable, str2);
        int i3;
        if (isFeatureEnabled()) {
            i3 = com.android.internal.R.string.accessibility_shortcut_menu_item_status_on;
        } else {
            i3 = com.android.internal.R.string.accessibility_shortcut_menu_item_status_off;
        }
        setStateDescription(getContext().getString(i3));
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.TargetOperations
    public void updateActionItem(com.android.internal.accessibility.dialog.TargetAdapter.ViewHolder viewHolder, int i) {
        super.updateActionItem(viewHolder, i);
        viewHolder.mStatusView.setVisibility(i == 1 ? 8 : 0);
        viewHolder.mStatusView.lambda$setTextAsync$0(getStateDescription());
    }

    private boolean isFeatureEnabled() {
        return android.provider.Settings.Secure.getInt(getContext().getContentResolver(), getKey(), 0) == 1;
    }
}
