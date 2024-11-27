package com.android.server.devicepolicy;

/* loaded from: classes.dex */
public class OverlayPackagesProvider {
    protected static final java.lang.String TAG = "OverlayPackagesProvider";
    private static final java.util.Map<java.lang.String, java.lang.String> sActionToMetadataKeyMap = new java.util.HashMap();
    private static final java.util.Set<java.lang.String> sAllowedActions = new java.util.HashSet();
    private final android.content.Context mContext;
    private final com.android.server.devicepolicy.OverlayPackagesProvider.Injector mInjector;
    private final android.content.pm.PackageManager mPm;

    @com.android.internal.annotations.VisibleForTesting
    interface Injector {
        java.lang.String getActiveApexPackageNameContainingPackage(java.lang.String str);

        java.lang.String getDevicePolicyManagementRoleHolderPackageName(android.content.Context context);

        @android.annotation.NonNull
        java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodListAsUser(int i);
    }

    public OverlayPackagesProvider(android.content.Context context) {
        this(context, new com.android.server.devicepolicy.OverlayPackagesProvider.DefaultInjector());
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class DefaultInjector implements com.android.server.devicepolicy.OverlayPackagesProvider.Injector {
        private DefaultInjector() {
        }

        @Override // com.android.server.devicepolicy.OverlayPackagesProvider.Injector
        @android.annotation.NonNull
        public java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodListAsUser(int i) {
            return com.android.server.inputmethod.InputMethodManagerInternal.get().getInputMethodListAsUser(i);
        }

        @Override // com.android.server.devicepolicy.OverlayPackagesProvider.Injector
        public java.lang.String getActiveApexPackageNameContainingPackage(java.lang.String str) {
            return com.android.server.pm.ApexManager.getInstance().getActiveApexPackageNameContainingPackage(str);
        }

        @Override // com.android.server.devicepolicy.OverlayPackagesProvider.Injector
        public java.lang.String getDevicePolicyManagementRoleHolderPackageName(final android.content.Context context) {
            return (java.lang.String) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.OverlayPackagesProvider$DefaultInjector$$ExternalSyntheticLambda0
                public final java.lang.Object getOrThrow() {
                    java.lang.String lambda$getDevicePolicyManagementRoleHolderPackageName$0;
                    lambda$getDevicePolicyManagementRoleHolderPackageName$0 = com.android.server.devicepolicy.OverlayPackagesProvider.DefaultInjector.lambda$getDevicePolicyManagementRoleHolderPackageName$0(context);
                    return lambda$getDevicePolicyManagementRoleHolderPackageName$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.String lambda$getDevicePolicyManagementRoleHolderPackageName$0(android.content.Context context) throws java.lang.Exception {
            java.util.List roleHolders = ((android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class)).getRoleHolders("android.app.role.DEVICE_POLICY_MANAGEMENT");
            if (roleHolders.isEmpty()) {
                return null;
            }
            return (java.lang.String) roleHolders.get(0);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    OverlayPackagesProvider(android.content.Context context, com.android.server.devicepolicy.OverlayPackagesProvider.Injector injector) {
        sActionToMetadataKeyMap.put("android.app.action.PROVISION_MANAGED_USER", "android.app.REQUIRED_APP_MANAGED_USER");
        sActionToMetadataKeyMap.put("android.app.action.PROVISION_MANAGED_PROFILE", "android.app.REQUIRED_APP_MANAGED_PROFILE");
        sActionToMetadataKeyMap.put("android.app.action.PROVISION_MANAGED_DEVICE", "android.app.REQUIRED_APP_MANAGED_DEVICE");
        sAllowedActions.add("android.app.action.PROVISION_MANAGED_USER");
        sAllowedActions.add("android.app.action.PROVISION_MANAGED_PROFILE");
        sAllowedActions.add("android.app.action.PROVISION_MANAGED_DEVICE");
        this.mContext = context;
        this.mPm = (android.content.pm.PackageManager) com.android.internal.util.Preconditions.checkNotNull(context.getPackageManager());
        this.mInjector = (com.android.server.devicepolicy.OverlayPackagesProvider.Injector) com.android.internal.util.Preconditions.checkNotNull(injector);
    }

    @android.annotation.NonNull
    public java.util.Set<java.lang.String> getNonRequiredApps(@android.annotation.NonNull android.content.ComponentName componentName, int i, @android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(componentName);
        com.android.internal.util.Preconditions.checkArgument(sAllowedActions.contains(str));
        java.util.Set<java.lang.String> launchableApps = getLaunchableApps(i);
        launchableApps.removeAll(getRequiredApps(str, componentName.getPackageName()));
        launchableApps.removeAll(getSystemInputMethods(i));
        launchableApps.addAll(getDisallowedApps(str));
        launchableApps.removeAll(getRequiredAppsMainlineModules(launchableApps, str));
        launchableApps.removeAll(getDeviceManagerRoleHolders());
        return launchableApps;
    }

    private java.util.Set<java.lang.String> getDeviceManagerRoleHolders() {
        java.util.HashSet hashSet = new java.util.HashSet();
        java.lang.String devicePolicyManagementRoleHolderPackageName = this.mInjector.getDevicePolicyManagementRoleHolderPackageName(this.mContext);
        if (devicePolicyManagementRoleHolderPackageName != null) {
            hashSet.add(devicePolicyManagementRoleHolderPackageName);
        }
        return hashSet;
    }

    private java.util.Set<java.lang.String> getRequiredAppsMainlineModules(java.util.Set<java.lang.String> set, java.lang.String str) {
        java.util.HashSet hashSet = new java.util.HashSet();
        for (java.lang.String str2 : set) {
            if (isMainlineModule(str2) && isRequiredAppDeclaredInMetadata(str2, str)) {
                hashSet.add(str2);
            }
        }
        return hashSet;
    }

    private boolean isRequiredAppDeclaredInMetadata(java.lang.String str, java.lang.String str2) {
        try {
            android.content.pm.PackageInfo packageInfo = this.mPm.getPackageInfo(str, 128);
            return packageInfo.applicationInfo.metaData.getBoolean(sActionToMetadataKeyMap.get(str2));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private boolean isMainlineModule(java.lang.String str) {
        return isRegularMainlineModule(str) || isApkInApexMainlineModule(str);
    }

    private boolean isRegularMainlineModule(java.lang.String str) {
        try {
            this.mPm.getModuleInfo(str, 0);
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private boolean isApkInApexMainlineModule(java.lang.String str) {
        return this.mInjector.getActiveApexPackageNameContainingPackage(str) != null;
    }

    private java.util.Set<java.lang.String> getLaunchableApps(int i) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        java.util.List queryIntentActivitiesAsUser = this.mPm.queryIntentActivitiesAsUser(intent, 795136, i);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.Iterator it = queryIntentActivitiesAsUser.iterator();
        while (it.hasNext()) {
            arraySet.add(((android.content.pm.ResolveInfo) it.next()).activityInfo.packageName);
        }
        return arraySet;
    }

    private java.util.Set<java.lang.String> getSystemInputMethods(int i) {
        java.util.List<android.view.inputmethod.InputMethodInfo> inputMethodListAsUser = this.mInjector.getInputMethodListAsUser(i);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (android.view.inputmethod.InputMethodInfo inputMethodInfo : inputMethodListAsUser) {
            if (inputMethodInfo.getServiceInfo().applicationInfo.isSystemApp()) {
                arraySet.add(inputMethodInfo.getPackageName());
            }
        }
        return arraySet;
    }

    private java.util.Set<java.lang.String> getRequiredApps(java.lang.String str, java.lang.String str2) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        arraySet.addAll(getRequiredAppsSet(str));
        arraySet.addAll(getVendorRequiredAppsSet(str));
        arraySet.add(str2);
        return arraySet;
    }

    private java.util.Set<java.lang.String> getDisallowedApps(java.lang.String str) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        arraySet.addAll(getDisallowedAppsSet(str));
        arraySet.addAll(getVendorDisallowedAppsSet(str));
        return arraySet;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private java.util.Set<java.lang.String> getRequiredAppsSet(java.lang.String str) {
        char c;
        int i;
        switch (str.hashCode()) {
            case -920528692:
                if (str.equals("android.app.action.PROVISION_MANAGED_DEVICE")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -514404415:
                if (str.equals("android.app.action.PROVISION_MANAGED_USER")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -340845101:
                if (str.equals("android.app.action.PROVISION_MANAGED_PROFILE")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                i = android.R.array.radioAttributes;
                break;
            case 1:
                i = android.R.array.preloaded_freeform_multi_window_drawables;
                break;
            case 2:
                i = android.R.array.preloaded_drawables;
                break;
            default:
                throw new java.lang.IllegalArgumentException("Provisioning type " + str + " not supported.");
        }
        return new android.util.ArraySet(java.util.Arrays.asList(this.mContext.getResources().getStringArray(i)));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private java.util.Set<java.lang.String> getDisallowedAppsSet(java.lang.String str) {
        char c;
        int i;
        switch (str.hashCode()) {
            case -920528692:
                if (str.equals("android.app.action.PROVISION_MANAGED_DEVICE")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -514404415:
                if (str.equals("android.app.action.PROVISION_MANAGED_USER")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -340845101:
                if (str.equals("android.app.action.PROVISION_MANAGED_PROFILE")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                i = android.R.array.dial_string_replace;
                break;
            case 1:
                i = android.R.array.device_state_notification_thermal_titles;
                break;
            case 2:
                i = android.R.array.device_state_notification_thermal_contents;
                break;
            default:
                throw new java.lang.IllegalArgumentException("Provisioning type " + str + " not supported.");
        }
        return new android.util.ArraySet(java.util.Arrays.asList(this.mContext.getResources().getStringArray(i)));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private java.util.Set<java.lang.String> getVendorRequiredAppsSet(java.lang.String str) {
        char c;
        int i;
        switch (str.hashCode()) {
            case -920528692:
                if (str.equals("android.app.action.PROVISION_MANAGED_DEVICE")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -514404415:
                if (str.equals("android.app.action.PROVISION_MANAGED_USER")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -340845101:
                if (str.equals("android.app.action.PROVISION_MANAGED_PROFILE")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                i = android.R.array.vendor_policy_exempt_apps;
                break;
            case 1:
                i = android.R.array.vendor_disallowed_apps_managed_user;
                break;
            case 2:
                i = android.R.array.vendor_disallowed_apps_managed_profile;
                break;
            default:
                throw new java.lang.IllegalArgumentException("Provisioning type " + str + " not supported.");
        }
        return new android.util.ArraySet(java.util.Arrays.asList(this.mContext.getResources().getStringArray(i)));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private java.util.Set<java.lang.String> getVendorDisallowedAppsSet(java.lang.String str) {
        char c;
        int i;
        switch (str.hashCode()) {
            case -920528692:
                if (str.equals("android.app.action.PROVISION_MANAGED_DEVICE")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -514404415:
                if (str.equals("android.app.action.PROVISION_MANAGED_USER")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -340845101:
                if (str.equals("android.app.action.PROVISION_MANAGED_PROFILE")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                i = android.R.array.vendor_cross_profile_apps;
                break;
            case 1:
                i = android.R.array.unloggable_phone_numbers;
                break;
            case 2:
                i = android.R.array.supported_locales;
                break;
            default:
                throw new java.lang.IllegalArgumentException("Provisioning type " + str + " not supported.");
        }
        return new android.util.ArraySet(java.util.Arrays.asList(this.mContext.getResources().getStringArray(i)));
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println(TAG);
        indentingPrintWriter.increaseIndent();
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpResources(indentingPrintWriter, this.mContext, "required_apps_managed_device", android.R.array.preloaded_drawables);
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpResources(indentingPrintWriter, this.mContext, "required_apps_managed_user", android.R.array.radioAttributes);
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpResources(indentingPrintWriter, this.mContext, "required_apps_managed_profile", android.R.array.preloaded_freeform_multi_window_drawables);
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpResources(indentingPrintWriter, this.mContext, "disallowed_apps_managed_device", android.R.array.device_state_notification_thermal_contents);
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpResources(indentingPrintWriter, this.mContext, "disallowed_apps_managed_user", android.R.array.dial_string_replace);
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpResources(indentingPrintWriter, this.mContext, "disallowed_apps_managed_device", android.R.array.device_state_notification_thermal_contents);
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpResources(indentingPrintWriter, this.mContext, "vendor_required_apps_managed_device", android.R.array.vendor_disallowed_apps_managed_profile);
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpResources(indentingPrintWriter, this.mContext, "vendor_required_apps_managed_user", android.R.array.vendor_policy_exempt_apps);
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpResources(indentingPrintWriter, this.mContext, "vendor_required_apps_managed_profile", android.R.array.vendor_disallowed_apps_managed_user);
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpResources(indentingPrintWriter, this.mContext, "vendor_disallowed_apps_managed_user", android.R.array.vendor_cross_profile_apps);
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpResources(indentingPrintWriter, this.mContext, "vendor_disallowed_apps_managed_device", android.R.array.supported_locales);
        com.android.server.devicepolicy.DevicePolicyManagerService.dumpResources(indentingPrintWriter, this.mContext, "vendor_disallowed_apps_managed_profile", android.R.array.unloggable_phone_numbers);
        indentingPrintWriter.decreaseIndent();
    }
}
