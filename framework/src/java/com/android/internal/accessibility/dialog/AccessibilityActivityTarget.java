package com.android.internal.accessibility.dialog;

/* loaded from: classes4.dex */
class AccessibilityActivityTarget extends com.android.internal.accessibility.dialog.AccessibilityTarget {
    AccessibilityActivityTarget(android.content.Context context, int i, android.accessibilityservice.AccessibilityShortcutInfo accessibilityShortcutInfo) {
        super(context, i, 3, com.android.internal.accessibility.util.ShortcutUtils.isShortcutContained(context, i, accessibilityShortcutInfo.getComponentName().flattenToString()), accessibilityShortcutInfo.getComponentName().flattenToString(), accessibilityShortcutInfo.getActivityInfo().applicationInfo.uid, accessibilityShortcutInfo.getActivityInfo().loadLabel(context.getPackageManager()), accessibilityShortcutInfo.getActivityInfo().loadIcon(context.getPackageManager()), com.android.internal.accessibility.util.ShortcutUtils.convertToKey(com.android.internal.accessibility.util.ShortcutUtils.convertToUserType(i)));
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
}
