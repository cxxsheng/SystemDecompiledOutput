package com.android.internal.accessibility.dialog;

/* loaded from: classes4.dex */
class AccessibilityServiceTarget extends com.android.internal.accessibility.dialog.AccessibilityTarget {
    private final android.accessibilityservice.AccessibilityServiceInfo mAccessibilityServiceInfo;

    AccessibilityServiceTarget(android.content.Context context, int i, int i2, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        super(context, i, i2, com.android.internal.accessibility.util.ShortcutUtils.isShortcutContained(context, i, accessibilityServiceInfo.getComponentName().flattenToString()), accessibilityServiceInfo.getComponentName().flattenToString(), accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.uid, accessibilityServiceInfo.getResolveInfo().loadLabel(context.getPackageManager()), accessibilityServiceInfo.getResolveInfo().loadIcon(context.getPackageManager()), com.android.internal.accessibility.util.ShortcutUtils.convertToKey(com.android.internal.accessibility.util.ShortcutUtils.convertToUserType(i)));
        this.mAccessibilityServiceInfo = accessibilityServiceInfo;
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.TargetOperations
    public void updateActionItem(com.android.internal.accessibility.dialog.TargetAdapter.ViewHolder viewHolder, int i) {
        super.updateActionItem(viewHolder, i);
        boolean isAccessibilityTargetAllowed = com.android.internal.accessibility.dialog.AccessibilityTargetHelper.isAccessibilityTargetAllowed(getContext(), getComponentName().getPackageName(), getUid());
        boolean z = false;
        boolean z2 = i == 1;
        if (isAccessibilityTargetAllowed || (z2 && isShortcutEnabled())) {
            z = true;
        }
        viewHolder.mCheckBoxView.setEnabled(z);
        viewHolder.mIconView.setEnabled(z);
        viewHolder.mLabelView.setEnabled(z);
        viewHolder.mStatusView.setEnabled(z);
    }

    public android.accessibilityservice.AccessibilityServiceInfo getAccessibilityServiceInfo() {
        return this.mAccessibilityServiceInfo;
    }
}
