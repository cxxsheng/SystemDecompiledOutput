package com.android.internal.accessibility.util;

/* loaded from: classes4.dex */
public final class AccessibilityUtils {
    public static final android.content.ComponentName ACCESSIBILITY_MENU_IN_SYSTEM = new android.content.ComponentName("com.android.systemui.accessibility.accessibilitymenu", "com.android.systemui.accessibility.accessibilitymenu.AccessibilityMenuService");
    public static final java.lang.String MENU_SERVICE_RELATIVE_CLASS_NAME = ".AccessibilityMenuService";
    public static final int NONE = 0;
    public static final int PARCELABLE_SPAN = 2;
    public static final int TEXT = 1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface A11yTextChangeType {
    }

    private AccessibilityUtils() {
    }

    public static java.util.Set<android.content.ComponentName> getEnabledServicesFromSettings(android.content.Context context, int i) {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(context.getContentResolver(), android.provider.Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, i);
        if (android.text.TextUtils.isEmpty(stringForUser)) {
            return java.util.Collections.emptySet();
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        simpleStringSplitter.setString(stringForUser);
        java.util.Iterator<java.lang.String> it = simpleStringSplitter.iterator();
        while (it.hasNext()) {
            android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(it.next());
            if (unflattenFromString != null) {
                hashSet.add(unflattenFromString);
            }
        }
        return hashSet;
    }

    public static void setAccessibilityServiceState(android.content.Context context, android.content.ComponentName componentName, boolean z) {
        setAccessibilityServiceState(context, componentName, z, android.os.UserHandle.myUserId());
    }

    public static void setAccessibilityServiceState(android.content.Context context, android.content.ComponentName componentName, boolean z, int i) {
        java.util.Set enabledServicesFromSettings = getEnabledServicesFromSettings(context, i);
        if (enabledServicesFromSettings.isEmpty()) {
            enabledServicesFromSettings = new android.util.ArraySet(1);
        }
        if (z) {
            enabledServicesFromSettings.add(componentName);
        } else {
            enabledServicesFromSettings.remove(componentName);
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.util.Iterator it = enabledServicesFromSettings.iterator();
        while (it.hasNext()) {
            sb.append(((android.content.ComponentName) it.next()).flattenToString());
            sb.append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        }
        int length = sb.length();
        if (length > 0) {
            sb.deleteCharAt(length - 1);
        }
        android.provider.Settings.Secure.putStringForUser(context.getContentResolver(), android.provider.Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, sb.toString(), i);
    }

    public static int getAccessibilityServiceFragmentType(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        int i = accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion;
        boolean z = (accessibilityServiceInfo.flags & 256) != 0;
        if (i <= 29) {
            return 0;
        }
        return z ? 1 : 2;
    }

    public static boolean isAccessibilityServiceEnabled(android.content.Context context, java.lang.String str) {
        java.util.Iterator<android.accessibilityservice.AccessibilityServiceInfo> it = ((android.view.accessibility.AccessibilityManager) context.getSystemService(android.content.Context.ACCESSIBILITY_SERVICE)).getEnabledAccessibilityServiceList(-1).iterator();
        while (it.hasNext()) {
            if (it.next().getComponentName().flattenToString().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean interceptHeadsetHookForActiveCall(android.content.Context context) {
        android.telecom.TelecomManager telecomManager = (android.telecom.TelecomManager) context.getSystemService(android.telecom.TelecomManager.class);
        int callState = telecomManager != null ? telecomManager.getCallState() : 0;
        if (callState == 1) {
            telecomManager.acceptRingingCall();
            return true;
        }
        if (callState != 2) {
            return false;
        }
        telecomManager.endCall();
        return true;
    }

    public static boolean isUserSetupCompleted(android.content.Context context) {
        return android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), android.provider.Settings.Secure.USER_SETUP_COMPLETE, 0, -2) != 0;
    }

    public static int textOrSpanChanged(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        if (!android.text.TextUtils.equals(charSequence, charSequence2)) {
            return 1;
        }
        if (((charSequence instanceof android.text.Spanned) || (charSequence2 instanceof android.text.Spanned)) && !parcelableSpansEquals(charSequence, charSequence2)) {
            return 2;
        }
        return 0;
    }

    private static boolean parcelableSpansEquals(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        android.text.Spanned spanned;
        java.lang.Object[] objArr = libcore.util.EmptyArray.OBJECT;
        java.lang.Object[] objArr2 = libcore.util.EmptyArray.OBJECT;
        android.text.Spanned spanned2 = null;
        if (!(charSequence instanceof android.text.Spanned)) {
            spanned = null;
        } else {
            spanned = (android.text.Spanned) charSequence;
            objArr = spanned.getSpans(0, spanned.length(), android.text.ParcelableSpan.class);
        }
        if (charSequence2 instanceof android.text.Spanned) {
            spanned2 = (android.text.Spanned) charSequence2;
            objArr2 = spanned2.getSpans(0, spanned2.length(), android.text.ParcelableSpan.class);
        }
        if (objArr.length != objArr2.length) {
            return false;
        }
        for (int i = 0; i < objArr.length; i++) {
            java.lang.Object obj = objArr[i];
            java.lang.Object obj2 = objArr2[i];
            if (obj.getClass() != obj2.getClass() || spanned.getSpanStart(obj) != spanned2.getSpanStart(obj2) || spanned.getSpanEnd(obj) != spanned2.getSpanEnd(obj2) || spanned.getSpanFlags(obj) != spanned2.getSpanFlags(obj2)) {
                return false;
            }
        }
        return true;
    }

    public static android.content.ComponentName getAccessibilityMenuComponentToMigrate(android.content.pm.PackageManager packageManager, int i) {
        java.util.Set<android.content.ComponentName> findA11yMenuComponentNames = findA11yMenuComponentNames(packageManager, i);
        java.util.Optional<android.content.ComponentName> findFirst = findA11yMenuComponentNames.stream().filter(new java.util.function.Predicate() { // from class: com.android.internal.accessibility.util.AccessibilityUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return com.android.internal.accessibility.util.AccessibilityUtils.lambda$getAccessibilityMenuComponentToMigrate$0((android.content.ComponentName) obj);
            }
        }).findFirst();
        if (findA11yMenuComponentNames.size() == 2 && findA11yMenuComponentNames.contains(ACCESSIBILITY_MENU_IN_SYSTEM) && findFirst.isPresent()) {
            return findFirst.get();
        }
        return null;
    }

    static /* synthetic */ boolean lambda$getAccessibilityMenuComponentToMigrate$0(android.content.ComponentName componentName) {
        return !componentName.equals(ACCESSIBILITY_MENU_IN_SYSTEM);
    }

    private static java.util.Set<android.content.ComponentName> findA11yMenuComponentNames(android.content.pm.PackageManager packageManager, int i) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.Iterator<android.content.pm.ResolveInfo> it = packageManager.queryIntentServicesAsUser(new android.content.Intent(android.accessibilityservice.AccessibilityService.SERVICE_INTERFACE), android.content.pm.PackageManager.ResolveInfoFlags.of(786944L), i).iterator();
        while (it.hasNext()) {
            android.content.ComponentName componentName = it.next().serviceInfo.getComponentName();
            if (componentName.getClassName().endsWith(MENU_SERVICE_RELATIVE_CLASS_NAME)) {
                arraySet.add(componentName);
            }
        }
        return arraySet;
    }
}
