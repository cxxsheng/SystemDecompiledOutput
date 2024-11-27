package com.android.internal.accessibility.util;

/* loaded from: classes4.dex */
public final class ShortcutUtils {
    private static final android.text.TextUtils.SimpleStringSplitter sStringColonSplitter = new android.text.TextUtils.SimpleStringSplitter(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);

    private ShortcutUtils() {
    }

    public static void optInValueToSettings(android.content.Context context, int i, java.lang.String str) {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(java.lang.String.valueOf(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR));
        java.lang.String convertToKey = convertToKey(i);
        java.lang.String string = android.provider.Settings.Secure.getString(context.getContentResolver(), convertToKey);
        if (isComponentIdExistingInSettings(context, i, str)) {
            return;
        }
        if (!android.text.TextUtils.isEmpty(string)) {
            stringJoiner.add(string);
        }
        stringJoiner.add(str);
        android.provider.Settings.Secure.putString(context.getContentResolver(), convertToKey, stringJoiner.toString());
    }

    public static void optOutValueFromSettings(android.content.Context context, int i, java.lang.String str) {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(java.lang.String.valueOf(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR));
        java.lang.String convertToKey = convertToKey(i);
        java.lang.String string = android.provider.Settings.Secure.getString(context.getContentResolver(), convertToKey);
        if (android.text.TextUtils.isEmpty(string)) {
            return;
        }
        sStringColonSplitter.setString(string);
        while (sStringColonSplitter.hasNext()) {
            java.lang.String next = sStringColonSplitter.next();
            if (!android.text.TextUtils.isEmpty(next) && !str.equals(next)) {
                stringJoiner.add(next);
            }
        }
        android.provider.Settings.Secure.putString(context.getContentResolver(), convertToKey, stringJoiner.toString());
    }

    public static boolean isComponentIdExistingInSettings(android.content.Context context, int i, java.lang.String str) {
        java.lang.String string = android.provider.Settings.Secure.getString(context.getContentResolver(), convertToKey(i));
        if (android.text.TextUtils.isEmpty(string)) {
            return false;
        }
        sStringColonSplitter.setString(string);
        while (sStringColonSplitter.hasNext()) {
            if (str.equals(sStringColonSplitter.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isShortcutContained(android.content.Context context, int i, java.lang.String str) {
        return ((android.view.accessibility.AccessibilityManager) context.getSystemService(android.content.Context.ACCESSIBILITY_SERVICE)).getAccessibilityShortcutTargets(i).contains(str);
    }

    public static java.lang.String convertToKey(int i) {
        switch (i) {
            case 1:
                return android.provider.Settings.Secure.ACCESSIBILITY_BUTTON_TARGETS;
            case 2:
                return android.provider.Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE;
            case 3:
            default:
                throw new java.lang.IllegalArgumentException("Unsupported user shortcut type: " + i);
            case 4:
                return android.provider.Settings.Secure.ACCESSIBILITY_DISPLAY_MAGNIFICATION_ENABLED;
        }
    }

    public static int convertToUserType(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            default:
                throw new java.lang.IllegalArgumentException("Unsupported shortcut type:" + i);
        }
    }

    public static void updateInvisibleToggleAccessibilityServiceEnableState(android.content.Context context, java.util.Set<java.lang.String> set, int i) {
        android.view.accessibility.AccessibilityManager accessibilityManager = (android.view.accessibility.AccessibilityManager) context.getSystemService(android.content.Context.ACCESSIBILITY_SERVICE);
        if (accessibilityManager == null) {
            return;
        }
        java.util.List<android.accessibilityservice.AccessibilityServiceInfo> installedAccessibilityServiceList = accessibilityManager.getInstalledAccessibilityServiceList();
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo : installedAccessibilityServiceList) {
            if (com.android.internal.accessibility.util.AccessibilityUtils.getAccessibilityServiceFragmentType(accessibilityServiceInfo) == 1) {
                arraySet.add(accessibilityServiceInfo.getComponentName().flattenToString());
            }
        }
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        for (int i2 : com.android.internal.accessibility.common.ShortcutConstants.USER_SHORTCUT_TYPES) {
            arraySet2.addAll(getShortcutTargetsFromSettings(context, i2, i));
        }
        for (java.lang.String str : set) {
            if (arraySet.contains(str)) {
                com.android.internal.accessibility.util.AccessibilityUtils.setAccessibilityServiceState(context, android.content.ComponentName.unflattenFromString(str), arraySet2.contains(str));
            }
        }
    }

    public static java.util.Set<java.lang.String> getShortcutTargetsFromSettings(android.content.Context context, int i, int i2) {
        java.lang.String convertToKey = convertToKey(i);
        if (android.provider.Settings.Secure.ACCESSIBILITY_DISPLAY_MAGNIFICATION_ENABLED.equals(convertToKey)) {
            return android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), convertToKey, 0, i2) == 1 ? java.util.Set.of("com.android.server.accessibility.MagnificationController") : java.util.Collections.emptySet();
        }
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(context.getContentResolver(), convertToKey, i2);
        if (android.text.TextUtils.isEmpty(stringForUser)) {
            return java.util.Collections.emptySet();
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        sStringColonSplitter.setString(stringForUser);
        while (sStringColonSplitter.hasNext()) {
            arraySet.add(sStringColonSplitter.next());
        }
        return java.util.Collections.unmodifiableSet(arraySet);
    }
}
