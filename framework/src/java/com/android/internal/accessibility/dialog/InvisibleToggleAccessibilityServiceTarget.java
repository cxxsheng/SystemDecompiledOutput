package com.android.internal.accessibility.dialog;

/* loaded from: classes4.dex */
public class InvisibleToggleAccessibilityServiceTarget extends com.android.internal.accessibility.dialog.AccessibilityServiceTarget {
    @Override // com.android.internal.accessibility.dialog.AccessibilityServiceTarget
    public /* bridge */ /* synthetic */ android.accessibilityservice.AccessibilityServiceInfo getAccessibilityServiceInfo() {
        return super.getAccessibilityServiceInfo();
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityServiceTarget, com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.TargetOperations
    public /* bridge */ /* synthetic */ void updateActionItem(com.android.internal.accessibility.dialog.TargetAdapter.ViewHolder viewHolder, int i) {
        super.updateActionItem(viewHolder, i);
    }

    public InvisibleToggleAccessibilityServiceTarget(android.content.Context context, int i, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        super(context, i, 1, accessibilityServiceInfo);
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.OnTargetCheckedChangeListener
    public void onCheckedChanged(boolean z) {
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(getId());
        if (android.view.accessibility.Flags.updateAlwaysOnA11yService()) {
            super.onCheckedChanged(z);
            com.android.internal.accessibility.util.ShortcutUtils.updateInvisibleToggleAccessibilityServiceEnableState(getContext(), java.util.Set.of(unflattenFromString.flattenToString()), android.os.UserHandle.myUserId());
        } else {
            if (!isComponentIdExistingInOtherShortcut()) {
                com.android.internal.accessibility.util.AccessibilityUtils.setAccessibilityServiceState(getContext(), unflattenFromString, z);
            }
            super.onCheckedChanged(z);
        }
    }

    private boolean isComponentIdExistingInOtherShortcut() {
        switch (getShortcutType()) {
            case 0:
                return com.android.internal.accessibility.util.ShortcutUtils.isComponentIdExistingInSettings(getContext(), 2, getId());
            case 1:
                return com.android.internal.accessibility.util.ShortcutUtils.isComponentIdExistingInSettings(getContext(), 1, getId());
            default:
                throw new java.lang.IllegalStateException("Unexpected shortcut type");
        }
    }
}
