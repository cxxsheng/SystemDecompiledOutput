package com.android.server.policy;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
class TalkbackShortcutController {
    private static final java.lang.String TALKBACK_LABEL = "TalkBack";
    private final android.content.Context mContext;
    private final android.content.pm.PackageManager mPackageManager;

    TalkbackShortcutController(android.content.Context context) {
        this.mContext = context;
        this.mPackageManager = this.mContext.getPackageManager();
    }

    boolean toggleTalkback(int i) {
        java.util.Set enabledServicesFromSettings = com.android.internal.accessibility.util.AccessibilityUtils.getEnabledServicesFromSettings(this.mContext, i);
        android.content.ComponentName talkbackComponent = getTalkbackComponent();
        if (talkbackComponent == null) {
            return false;
        }
        boolean contains = enabledServicesFromSettings.contains(talkbackComponent);
        if (isTalkBackShortcutGestureEnabled()) {
            contains = !contains;
            com.android.internal.accessibility.util.AccessibilityUtils.setAccessibilityServiceState(this.mContext, talkbackComponent, contains);
            if (contains) {
                logStemTriplePressAccessibilityTelemetry(talkbackComponent);
            }
        }
        return contains;
    }

    private android.content.ComponentName getTalkbackComponent() {
        java.util.Iterator<android.accessibilityservice.AccessibilityServiceInfo> it = ((android.view.accessibility.AccessibilityManager) this.mContext.getSystemService(android.view.accessibility.AccessibilityManager.class)).getInstalledAccessibilityServiceList().iterator();
        while (it.hasNext()) {
            android.content.pm.ServiceInfo serviceInfo = it.next().getResolveInfo().serviceInfo;
            if (isTalkback(serviceInfo)) {
                return new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name);
            }
        }
        return null;
    }

    boolean isTalkBackShortcutGestureEnabled() {
        return android.provider.Settings.System.getIntForUser(this.mContext.getContentResolver(), "wear_accessibility_gesture_enabled", 0, -2) == 1;
    }

    private void logStemTriplePressAccessibilityTelemetry(android.content.ComponentName componentName) {
        if (!com.android.internal.accessibility.util.AccessibilityUtils.isUserSetupCompleted(this.mContext)) {
            android.provider.Settings.Secure.putInt(this.mContext.getContentResolver(), "wear_accessibility_gesture_enabled_during_oobe", 1);
        } else {
            com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, componentName, 7, true);
        }
    }

    private boolean isTalkback(android.content.pm.ServiceInfo serviceInfo) {
        return TALKBACK_LABEL.equals(serviceInfo.loadLabel(this.mPackageManager).toString());
    }
}
