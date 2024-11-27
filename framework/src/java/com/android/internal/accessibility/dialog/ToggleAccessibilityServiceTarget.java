package com.android.internal.accessibility.dialog;

/* loaded from: classes4.dex */
class ToggleAccessibilityServiceTarget extends com.android.internal.accessibility.dialog.AccessibilityServiceTarget {

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface StatusViewAlphaScale {
        public static final float DISABLED = 0.5f;
        public static final float OPAQUE = 1.0f;
    }

    ToggleAccessibilityServiceTarget(android.content.Context context, int i, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        super(context, i, 2, accessibilityServiceInfo);
        int i2;
        if (com.android.internal.accessibility.util.AccessibilityUtils.isAccessibilityServiceEnabled(getContext(), getId())) {
            i2 = com.android.internal.R.string.accessibility_shortcut_menu_item_status_on;
        } else {
            i2 = com.android.internal.R.string.accessibility_shortcut_menu_item_status_off;
        }
        setStateDescription(getContext().getString(i2));
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityServiceTarget, com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.TargetOperations
    public void updateActionItem(com.android.internal.accessibility.dialog.TargetAdapter.ViewHolder viewHolder, int i) {
        super.updateActionItem(viewHolder, i);
        boolean isAccessibilityTargetAllowed = com.android.internal.accessibility.dialog.AccessibilityTargetHelper.isAccessibilityTargetAllowed(getContext(), getComponentName().getPackageName(), getUid());
        viewHolder.mStatusView.setVisibility(i == 1 ? 8 : 0);
        viewHolder.mStatusView.lambda$setTextAsync$0(getStateDescription());
        viewHolder.mStatusView.setAlpha(isAccessibilityTargetAllowed ? 1.0f : 0.5f);
    }
}
