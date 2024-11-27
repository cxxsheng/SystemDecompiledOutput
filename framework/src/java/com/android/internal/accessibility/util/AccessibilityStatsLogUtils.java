package com.android.internal.accessibility.util;

/* loaded from: classes4.dex */
public final class AccessibilityStatsLogUtils {
    private static final int UNKNOWN_STATUS = 0;
    public static int ACCESSIBILITY_PRIVACY_WARNING_STATUS_SHOWN = 1;
    public static int ACCESSIBILITY_PRIVACY_WARNING_STATUS_CLICKED = 2;
    public static int ACCESSIBILITY_PRIVACY_WARNING_STATUS_SERVICE_DISABLED = 3;

    private AccessibilityStatsLogUtils() {
    }

    public static void logAccessibilityShortcutActivated(android.content.Context context, android.content.ComponentName componentName, int i) {
        logAccessibilityShortcutActivatedInternal(componentName, convertToLoggingShortcutType(context, i), 0);
    }

    public static void logAccessibilityShortcutActivated(android.content.Context context, android.content.ComponentName componentName, int i, boolean z) {
        logAccessibilityShortcutActivatedInternal(componentName, convertToLoggingShortcutType(context, i), convertToLoggingServiceStatus(z));
    }

    private static void logAccessibilityShortcutActivatedInternal(android.content.ComponentName componentName, int i, int i2) {
        com.android.internal.util.FrameworkStatsLog.write(266, componentName.flattenToString(), i, i2);
    }

    public static void logMagnificationTripleTap(boolean z) {
        com.android.internal.util.FrameworkStatsLog.write(266, com.android.internal.accessibility.AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME.flattenToString(), 3, convertToLoggingServiceStatus(z));
    }

    public static void logMagnificationTwoFingerTripleTap(boolean z) {
        com.android.internal.util.FrameworkStatsLog.write(266, com.android.internal.accessibility.AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME.flattenToString(), 8, convertToLoggingServiceStatus(z));
    }

    public static void logAccessibilityButtonLongPressStatus(android.content.ComponentName componentName) {
        com.android.internal.util.FrameworkStatsLog.write(266, componentName.flattenToString(), 4, 0);
    }

    public static void logMagnificationUsageState(int i, long j, float f) {
        com.android.internal.util.FrameworkStatsLog.write(345, convertToLoggingMagnificationMode(i), j, convertToLoggingMagnificationScale(f));
    }

    public static void logMagnificationModeWithImeOn(int i) {
        com.android.internal.util.FrameworkStatsLog.write(346, convertToLoggingMagnificationMode(i));
    }

    public static void logMagnificationFollowTypingFocusSession(long j) {
        com.android.internal.util.FrameworkStatsLog.write(453, j);
    }

    public static void logMagnificationTripleTapAndHoldSession(long j) {
        com.android.internal.util.FrameworkStatsLog.write(452, j);
    }

    public static void logNonA11yToolServiceWarningReported(java.lang.String str, int i, long j) {
        com.android.internal.util.FrameworkStatsLog.write(384, str, i, j);
    }

    private static boolean isAccessibilityFloatingMenuEnabled(android.content.Context context) {
        return android.provider.Settings.Secure.getInt(context.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_BUTTON_MODE, -1) == 1;
    }

    private static boolean isAccessibilityGestureEnabled(android.content.Context context) {
        return android.provider.Settings.Secure.getInt(context.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_BUTTON_MODE, -1) == 2;
    }

    private static int convertToLoggingShortcutType(android.content.Context context, int i) {
        switch (i) {
            case 0:
                if (isAccessibilityFloatingMenuEnabled(context)) {
                    return 5;
                }
                if (isAccessibilityGestureEnabled(context)) {
                    return 6;
                }
                return 1;
            case 1:
                return 2;
            default:
                return 0;
        }
    }

    private static int convertToLoggingServiceStatus(boolean z) {
        return z ? 1 : 2;
    }

    private static int convertToLoggingMagnificationMode(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 0;
        }
    }

    private static int convertToLoggingMagnificationScale(float f) {
        return ((int) (f * 10.0f)) * 10;
    }
}
