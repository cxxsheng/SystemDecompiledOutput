package com.android.internal.accessibility.dialog;

/* loaded from: classes4.dex */
class VolumeShortcutToggleAccessibilityServiceTarget extends com.android.internal.accessibility.dialog.AccessibilityServiceTarget {
    VolumeShortcutToggleAccessibilityServiceTarget(android.content.Context context, int i, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        super(context, i, 0, accessibilityServiceInfo);
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.OnTargetCheckedChangeListener
    public void onCheckedChanged(boolean z) {
        switch (getShortcutType()) {
            case 0:
                onCheckedFromAccessibilityButton(z);
                return;
            case 1:
                super.onCheckedChanged(z);
                return;
            default:
                throw new java.lang.IllegalStateException("Unexpected shortcut type");
        }
    }

    private void onCheckedFromAccessibilityButton(boolean z) {
        setShortcutEnabled(z);
        com.android.internal.accessibility.util.AccessibilityUtils.setAccessibilityServiceState(getContext(), android.content.ComponentName.unflattenFromString(getId()), z);
        if (!z) {
            com.android.internal.accessibility.util.ShortcutUtils.optOutValueFromSettings(getContext(), 2, getId());
            android.widget.Toast.makeText(getContext(), getContext().getString(com.android.internal.R.string.accessibility_uncheck_legacy_item_warning, getLabel()), 0).show();
        }
    }
}
