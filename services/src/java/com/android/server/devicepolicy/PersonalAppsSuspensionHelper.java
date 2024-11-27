package com.android.server.devicepolicy;

/* loaded from: classes.dex */
public final class PersonalAppsSuspensionHelper {
    private static final java.lang.String LOG_TAG = "DevicePolicyManager";
    private static final int PACKAGE_QUERY_FLAGS = 786432;
    private final android.content.Context mContext;
    private final android.content.pm.PackageManager mPackageManager;

    public static com.android.server.devicepolicy.PersonalAppsSuspensionHelper forUser(android.content.Context context, int i) {
        return new com.android.server.devicepolicy.PersonalAppsSuspensionHelper(context.createContextAsUser(android.os.UserHandle.of(i), 0));
    }

    private PersonalAppsSuspensionHelper(android.content.Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
    }

    java.lang.String[] getPersonalAppsForSuspension() {
        java.util.List<android.content.pm.PackageInfo> installedPackages = this.mPackageManager.getInstalledPackages(PACKAGE_QUERY_FLAGS);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (android.content.pm.PackageInfo packageInfo : installedPackages) {
            android.content.pm.ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if ((!applicationInfo.isSystemApp() && !applicationInfo.isUpdatedSystemApp()) || hasLauncherIntent(packageInfo.packageName)) {
                arraySet.add(packageInfo.packageName);
            }
        }
        arraySet.removeAll(getCriticalPackages());
        arraySet.removeAll(getSystemLauncherPackages());
        arraySet.removeAll(getAccessibilityServices());
        arraySet.removeAll(getInputMethodPackages());
        arraySet.remove(getDefaultSmsPackage());
        arraySet.remove(getSettingsPackageName());
        for (java.lang.String str : this.mPackageManager.getUnsuspendablePackages((java.lang.String[]) arraySet.toArray(new java.lang.String[0]))) {
            arraySet.remove(str);
        }
        if (android.util.Log.isLoggable(LOG_TAG, 4)) {
            com.android.server.utils.Slogf.i(LOG_TAG, "Packages subject to suspension: %s", java.lang.String.join(",", arraySet));
        }
        return (java.lang.String[]) arraySet.toArray(new java.lang.String[0]);
    }

    private java.util.List<java.lang.String> getSystemLauncherPackages() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        for (android.content.pm.ResolveInfo resolveInfo : this.mPackageManager.queryIntentActivities(intent, PACKAGE_QUERY_FLAGS)) {
            if (resolveInfo.activityInfo == null || android.text.TextUtils.isEmpty(resolveInfo.activityInfo.packageName)) {
                com.android.server.utils.Slogf.wtf(LOG_TAG, "Could not find package name for launcher app %s", resolveInfo);
            } else {
                java.lang.String str = resolveInfo.activityInfo.packageName;
                try {
                    android.content.pm.ApplicationInfo applicationInfo = this.mPackageManager.getApplicationInfo(str, PACKAGE_QUERY_FLAGS);
                    if (applicationInfo.isSystemApp() || applicationInfo.isUpdatedSystemApp()) {
                        arrayList.add(str);
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    com.android.server.utils.Slogf.e(LOG_TAG, "Could not find application info for launcher app: %s", str);
                }
            }
        }
        return arrayList;
    }

    private java.util.List<java.lang.String> getAccessibilityServices() {
        android.os.IBinder service = android.os.ServiceManager.getService("accessibility");
        android.view.accessibility.AccessibilityManager accessibilityManager = new android.view.accessibility.AccessibilityManager(this.mContext, service == null ? null : android.view.accessibility.IAccessibilityManager.Stub.asInterface(service), this.mContext.getUserId());
        try {
            java.util.List<android.accessibilityservice.AccessibilityServiceInfo> enabledAccessibilityServiceList = accessibilityManager.getEnabledAccessibilityServiceList(-1);
            accessibilityManager.removeClient();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<android.accessibilityservice.AccessibilityServiceInfo> it = enabledAccessibilityServiceList.iterator();
            while (it.hasNext()) {
                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(it.next().getId());
                if (unflattenFromString != null) {
                    arrayList.add(unflattenFromString.getPackageName());
                }
            }
            return arrayList;
        } catch (java.lang.Throwable th) {
            accessibilityManager.removeClient();
            throw th;
        }
    }

    private java.util.List<java.lang.String> getInputMethodPackages() {
        java.util.List<android.view.inputmethod.InputMethodInfo> enabledInputMethodListAsUser = com.android.server.inputmethod.InputMethodManagerInternal.get().getEnabledInputMethodListAsUser(this.mContext.getUserId());
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.view.inputmethod.InputMethodInfo> it = enabledInputMethodListAsUser.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getPackageName());
        }
        return arrayList;
    }

    @android.annotation.Nullable
    private java.lang.String getSettingsPackageName() {
        android.content.Intent intent = new android.content.Intent("android.settings.SETTINGS");
        intent.addCategory("android.intent.category.DEFAULT");
        android.content.pm.ResolveInfo resolveActivity = this.mPackageManager.resolveActivity(intent, PACKAGE_QUERY_FLAGS);
        if (resolveActivity != null) {
            return resolveActivity.activityInfo.packageName;
        }
        return null;
    }

    private java.util.List<java.lang.String> getCriticalPackages() {
        return java.util.Arrays.asList(this.mContext.getResources().getStringArray(android.R.array.config_oemUsbModeOverride));
    }

    private boolean hasLauncherIntent(java.lang.String str) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(str);
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = this.mPackageManager.queryIntentActivities(intent, PACKAGE_QUERY_FLAGS);
        return (queryIntentActivities == null || queryIntentActivities.isEmpty()) ? false : true;
    }

    private java.lang.String getDefaultSmsPackage() {
        if (android.app.admin.flags.Flags.defaultSmsPersonalAppSuspensionFixEnabled()) {
            return com.android.internal.telephony.SmsApplication.getDefaultSmsApplicationAsUser(this.mContext, false, this.mContext.getUser()).getPackageName();
        }
        return android.provider.Telephony.Sms.getDefaultSmsPackage(this.mContext);
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("PersonalAppsSuspensionHelper");
        indentingPrintWriter.increaseIndent();
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpApps(indentingPrintWriter, "critical packages", getCriticalPackages());
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpApps(indentingPrintWriter, "launcher packages", getSystemLauncherPackages());
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpApps(indentingPrintWriter, "accessibility services", getAccessibilityServices());
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpApps(indentingPrintWriter, "input method packages", getInputMethodPackages());
        indentingPrintWriter.printf("SMS package: %s\n", new java.lang.Object[]{getDefaultSmsPackage()});
        indentingPrintWriter.printf("Settings package: %s\n", new java.lang.Object[]{getSettingsPackageName()});
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpApps(indentingPrintWriter, "Packages subject to suspension", getPersonalAppsForSuspension());
        indentingPrintWriter.decreaseIndent();
    }
}
