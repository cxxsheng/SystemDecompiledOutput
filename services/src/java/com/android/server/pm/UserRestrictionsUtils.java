package com.android.server.pm;

/* loaded from: classes2.dex */
public class UserRestrictionsUtils {
    private static final java.lang.String TAG = "UserRestrictionsUtils";
    public static final java.util.Set<java.lang.String> USER_RESTRICTIONS = newSetWithUniqueCheck(new java.lang.String[]{"no_config_wifi", "no_config_locale", "no_modify_accounts", "no_install_apps", "no_uninstall_apps", "no_share_location", "no_install_unknown_sources", "no_install_unknown_sources_globally", "no_config_bluetooth", "no_bluetooth", "no_bluetooth_sharing", "no_usb_file_transfer", "no_config_credentials", "no_remove_user", "no_remove_managed_profile", "no_debugging_features", "no_config_vpn", "no_config_date_time", "no_config_tethering", "no_network_reset", "no_factory_reset", "no_add_user", "no_add_managed_profile", "no_add_clone_profile", "no_add_private_profile", "ensure_verify_apps", "no_config_cell_broadcasts", "no_config_mobile_networks", "no_control_apps", "no_physical_media", "no_unmute_microphone", "no_adjust_volume", "no_outgoing_calls", "no_sms", "no_fun", "no_create_windows", "no_system_error_dialogs", "no_cross_profile_copy_paste", "no_outgoing_beam", "no_wallpaper", "no_safe_boot", "allow_parent_profile_app_linking", "no_record_audio", "no_camera", "no_run_in_background", "no_data_roaming", "no_set_user_icon", "no_set_wallpaper", "no_oem_unlock", "disallow_unmute_device", "no_autofill", "no_content_capture", "no_content_suggestions", "no_user_switch", "no_unified_password", "no_config_location", "no_airplane_mode", "no_config_brightness", "no_sharing_into_profile", "no_ambient_display", "no_config_screen_timeout", "no_printing", "disallow_config_private_dns", "disallow_microphone_toggle", "disallow_camera_toggle", "no_change_wifi_state", "no_wifi_tethering", "no_grant_admin", "no_sharing_admin_configured_wifi", "no_wifi_direct", "no_add_wifi_config", "no_cellular_2g", "no_ultra_wideband_radio", "disallow_config_default_apps", "no_near_field_communication_radio", "no_sim_globally", "no_assist_content"});
    public static final java.util.Set<java.lang.String> DEPRECATED_USER_RESTRICTIONS = com.google.android.collect.Sets.newArraySet(new java.lang.String[]{"no_add_managed_profile", "no_remove_managed_profile"});
    private static final java.util.Set<java.lang.String> NON_PERSIST_USER_RESTRICTIONS = com.google.android.collect.Sets.newArraySet(new java.lang.String[]{"no_record_audio"});
    private static final java.util.Set<java.lang.String> MAIN_USER_ONLY_RESTRICTIONS = com.google.android.collect.Sets.newArraySet(new java.lang.String[]{"no_bluetooth", "no_usb_file_transfer", "no_config_tethering", "no_network_reset", "no_factory_reset", "no_add_user", "no_config_cell_broadcasts", "no_config_mobile_networks", "no_physical_media", "no_sms", "no_fun", "no_safe_boot", "no_create_windows", "no_data_roaming", "no_airplane_mode"});
    private static final java.util.Set<java.lang.String> DEVICE_OWNER_ONLY_RESTRICTIONS = com.google.android.collect.Sets.newArraySet(new java.lang.String[]{"no_user_switch", "disallow_config_private_dns", "disallow_microphone_toggle", "disallow_camera_toggle", "no_change_wifi_state", "no_wifi_tethering", "no_wifi_direct", "no_add_wifi_config", "no_cellular_2g", "no_ultra_wideband_radio", "no_near_field_communication_radio"});
    private static final java.util.Set<java.lang.String> IMMUTABLE_BY_OWNERS = com.google.android.collect.Sets.newArraySet(new java.lang.String[]{"no_record_audio", "no_wallpaper", "no_oem_unlock", "no_add_private_profile"});
    private static final java.util.Set<java.lang.String> GLOBAL_RESTRICTIONS = com.google.android.collect.Sets.newArraySet(new java.lang.String[]{"no_adjust_volume", "no_bluetooth_sharing", "no_config_date_time", "no_system_error_dialogs", "no_run_in_background", "no_unmute_microphone", "disallow_unmute_device", "no_camera", "no_assist_content", "disallow_config_default_apps"});
    private static final java.util.Set<java.lang.String> PROFILE_OWNER_ORGANIZATION_OWNED_PARENT_GLOBAL_RESTRICTIONS = com.google.android.collect.Sets.newArraySet(new java.lang.String[]{"no_airplane_mode", "no_config_date_time", "disallow_config_private_dns", "no_change_wifi_state", "no_debugging_features", "no_wifi_tethering", "no_wifi_direct", "no_add_wifi_config", "no_cellular_2g", "no_ultra_wideband_radio", "no_near_field_communication_radio"});
    private static final java.util.Set<java.lang.String> PROFILE_OWNER_ORGANIZATION_OWNED_PROFILE_RESTRICTIONS = com.google.android.collect.Sets.newArraySet(new java.lang.String[]{"no_sim_globally"});
    private static final java.util.Set<java.lang.String> PROFILE_OWNER_ORGANIZATION_OWNED_LOCAL_RESTRICTIONS = com.google.android.collect.Sets.newArraySet(new java.lang.String[]{"no_config_bluetooth", "no_config_location", "no_config_wifi", "no_content_capture", "no_content_suggestions", "no_debugging_features", "no_share_location", "no_outgoing_calls", "no_camera", "no_bluetooth", "no_bluetooth_sharing", "no_config_cell_broadcasts", "no_config_mobile_networks", "no_config_tethering", "no_data_roaming", "no_safe_boot", "no_sms", "no_usb_file_transfer", "no_physical_media", "no_unmute_microphone", "disallow_config_default_apps"});
    private static final java.util.Set<java.lang.String> DEFAULT_ENABLED_FOR_MANAGED_PROFILES = com.google.android.collect.Sets.newArraySet(new java.lang.String[]{"no_bluetooth_sharing"});
    private static final java.util.Set<java.lang.String> PROFILE_GLOBAL_RESTRICTIONS = com.google.android.collect.Sets.newArraySet(new java.lang.String[]{"ensure_verify_apps", "no_airplane_mode", "no_install_unknown_sources_globally", "no_sim_globally"});
    private static final java.util.Set<java.lang.String> FINANCED_DEVICE_OWNER_RESTRICTIONS = com.google.android.collect.Sets.newArraySet(new java.lang.String[]{"no_add_user", "no_debugging_features", "no_install_unknown_sources", "no_safe_boot", "no_config_date_time", "no_outgoing_calls"});

    private UserRestrictionsUtils() {
    }

    private static java.util.Set<java.lang.String> newSetWithUniqueCheck(java.lang.String[] strArr) {
        android.util.ArraySet newArraySet = com.google.android.collect.Sets.newArraySet(strArr);
        com.android.internal.util.Preconditions.checkState(newArraySet.size() == strArr.length);
        return newArraySet;
    }

    public static boolean isValidRestriction(@android.annotation.NonNull java.lang.String str) {
        java.lang.String[] strArr;
        if (USER_RESTRICTIONS.contains(str)) {
            return true;
        }
        int callingUid = android.os.Binder.getCallingUid();
        try {
            strArr = android.app.AppGlobals.getPackageManager().getPackagesForUid(callingUid);
        } catch (android.os.RemoteException e) {
            strArr = null;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder("Unknown restriction queried by uid ");
        sb.append(callingUid);
        if (strArr != null && strArr.length > 0) {
            sb.append(" (");
            sb.append(strArr[0]);
            if (strArr.length > 1) {
                sb.append(" et al");
            }
            sb.append(")");
        }
        sb.append(": ");
        sb.append(str);
        if (str == null || !isSystemApp(callingUid, strArr)) {
            android.util.Slog.e(TAG, sb.toString());
        } else {
            android.util.Slog.wtf(TAG, sb.toString());
        }
        return false;
    }

    private static boolean isSystemApp(int i, java.lang.String[] strArr) {
        if (android.os.UserHandle.isCore(i)) {
            return true;
        }
        if (strArr == null) {
            return false;
        }
        android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
        for (java.lang.String str : strArr) {
            try {
                android.content.pm.ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 794624L, android.os.UserHandle.getUserId(i));
                if (applicationInfo != null && applicationInfo.isSystemApp()) {
                    return true;
                }
            } catch (android.os.RemoteException e) {
            }
        }
        return false;
    }

    public static void writeRestrictions(@android.annotation.NonNull org.xmlpull.v1.XmlSerializer xmlSerializer, @android.annotation.Nullable android.os.Bundle bundle, @android.annotation.NonNull java.lang.String str) throws java.io.IOException {
        writeRestrictions(com.android.internal.util.XmlUtils.makeTyped(xmlSerializer), bundle, str);
    }

    public static void writeRestrictions(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.Nullable android.os.Bundle bundle, @android.annotation.NonNull java.lang.String str) throws java.io.IOException {
        if (bundle == null) {
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, str);
        for (java.lang.String str2 : bundle.keySet()) {
            if (!NON_PERSIST_USER_RESTRICTIONS.contains(str2)) {
                if (!USER_RESTRICTIONS.contains(str2)) {
                    android.util.Log.w(TAG, "Unknown user restriction detected: " + str2);
                } else if (bundle.getBoolean(str2)) {
                    typedXmlSerializer.attributeBoolean((java.lang.String) null, str2, true);
                }
            }
        }
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    public static void readRestrictions(org.xmlpull.v1.XmlPullParser xmlPullParser, android.os.Bundle bundle) {
        readRestrictions(com.android.internal.util.XmlUtils.makeTyped(xmlPullParser), bundle);
    }

    public static void readRestrictions(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, android.os.Bundle bundle) {
        bundle.clear();
        for (java.lang.String str : USER_RESTRICTIONS) {
            if (typedXmlPullParser.getAttributeBoolean((java.lang.String) null, str, false)) {
                bundle.putBoolean(str, true);
            }
        }
    }

    public static android.os.Bundle readRestrictions(org.xmlpull.v1.XmlPullParser xmlPullParser) {
        return readRestrictions(com.android.internal.util.XmlUtils.makeTyped(xmlPullParser));
    }

    public static android.os.Bundle readRestrictions(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        android.os.Bundle bundle = new android.os.Bundle();
        readRestrictions(typedXmlPullParser, bundle);
        return bundle;
    }

    public static android.os.Bundle nonNull(@android.annotation.Nullable android.os.Bundle bundle) {
        return bundle != null ? bundle : new android.os.Bundle();
    }

    public static boolean contains(@android.annotation.Nullable android.os.Bundle bundle, java.lang.String str) {
        return bundle != null && bundle.getBoolean(str);
    }

    public static void merge(@android.annotation.NonNull android.os.Bundle bundle, @android.annotation.Nullable android.os.Bundle bundle2) {
        java.util.Objects.requireNonNull(bundle);
        com.android.internal.util.Preconditions.checkArgument(bundle != bundle2);
        if (bundle2 == null) {
            return;
        }
        for (java.lang.String str : bundle2.keySet()) {
            if (bundle2.getBoolean(str, false)) {
                bundle.putBoolean(str, true);
            }
        }
    }

    public static boolean canDeviceOwnerChange(java.lang.String str) {
        return !IMMUTABLE_BY_OWNERS.contains(str);
    }

    public static boolean canProfileOwnerChange(java.lang.String str, boolean z, boolean z2) {
        if (!android.app.admin.flags.Flags.esimManagementEnabled()) {
            return (IMMUTABLE_BY_OWNERS.contains(str) || DEVICE_OWNER_ONLY_RESTRICTIONS.contains(str) || (!z && MAIN_USER_ONLY_RESTRICTIONS.contains(str))) ? false : true;
        }
        if (IMMUTABLE_BY_OWNERS.contains(str) || DEVICE_OWNER_ONLY_RESTRICTIONS.contains(str)) {
            return false;
        }
        if (z || !MAIN_USER_ONLY_RESTRICTIONS.contains(str)) {
            return z2 || !PROFILE_OWNER_ORGANIZATION_OWNED_PROFILE_RESTRICTIONS.contains(str);
        }
        return false;
    }

    public static boolean canParentOfProfileOwnerOfOrganizationOwnedDeviceChange(java.lang.String str) {
        return PROFILE_OWNER_ORGANIZATION_OWNED_PARENT_GLOBAL_RESTRICTIONS.contains(str) || PROFILE_OWNER_ORGANIZATION_OWNED_LOCAL_RESTRICTIONS.contains(str);
    }

    @android.annotation.NonNull
    public static java.util.Set<java.lang.String> getDefaultEnabledForManagedProfiles() {
        return DEFAULT_ENABLED_FOR_MANAGED_PROFILES;
    }

    public static boolean canFinancedDeviceOwnerChange(java.lang.String str) {
        return FINANCED_DEVICE_OWNER_RESTRICTIONS.contains(str) && canDeviceOwnerChange(str);
    }

    public static boolean isGlobal(int i, java.lang.String str) {
        return (i == 0 && (MAIN_USER_ONLY_RESTRICTIONS.contains(str) || GLOBAL_RESTRICTIONS.contains(str))) || (i == 2 && PROFILE_OWNER_ORGANIZATION_OWNED_PARENT_GLOBAL_RESTRICTIONS.contains(str)) || PROFILE_GLOBAL_RESTRICTIONS.contains(str) || DEVICE_OWNER_ONLY_RESTRICTIONS.contains(str);
    }

    public static boolean isLocal(int i, java.lang.String str) {
        return !isGlobal(i, str);
    }

    public static boolean areEqual(@android.annotation.Nullable android.os.Bundle bundle, @android.annotation.Nullable android.os.Bundle bundle2) {
        if (bundle == bundle2) {
            return true;
        }
        if (com.android.server.BundleUtils.isEmpty(bundle)) {
            return com.android.server.BundleUtils.isEmpty(bundle2);
        }
        if (com.android.server.BundleUtils.isEmpty(bundle2)) {
            return false;
        }
        for (java.lang.String str : bundle.keySet()) {
            if (bundle.getBoolean(str) != bundle2.getBoolean(str)) {
                return false;
            }
        }
        for (java.lang.String str2 : bundle2.keySet()) {
            if (bundle.getBoolean(str2) != bundle2.getBoolean(str2)) {
                return false;
            }
        }
        return true;
    }

    public static void applyUserRestrictions(android.content.Context context, int i, android.os.Bundle bundle, android.os.Bundle bundle2) {
        for (java.lang.String str : USER_RESTRICTIONS) {
            boolean z = bundle.getBoolean(str);
            if (z != bundle2.getBoolean(str)) {
                applyUserRestriction(context, i, str, z);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static void applyUserRestriction(android.content.Context context, int i, java.lang.String str, boolean z) {
        char c;
        android.content.ContentResolver contentResolver = context.getContentResolver();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int i2 = 1;
            switch (str.hashCode()) {
                case -1475388515:
                    if (str.equals("no_ambient_display")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -1387500078:
                    if (str.equals("no_control_apps")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -1315771401:
                    if (str.equals("ensure_verify_apps")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1145953970:
                    if (str.equals("no_install_unknown_sources_globally")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1082175374:
                    if (str.equals("no_airplane_mode")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -6578707:
                    if (str.equals("no_uninstall_apps")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 387189153:
                    if (str.equals("no_install_unknown_sources")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 721128150:
                    if (str.equals("no_run_in_background")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 928851522:
                    if (str.equals("no_data_roaming")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 995816019:
                    if (str.equals("no_share_location")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1095593830:
                    if (str.equals("no_safe_boot")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1760762284:
                    if (str.equals("no_debugging_features")) {
                        c = 2;
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
                    if (z) {
                        java.util.List<android.telephony.SubscriptionInfo> activeSubscriptionInfoList = ((android.telephony.SubscriptionManager) context.getSystemService(android.telephony.SubscriptionManager.class)).getActiveSubscriptionInfoList();
                        if (activeSubscriptionInfoList != null) {
                            java.util.Iterator<android.telephony.SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
                            while (it.hasNext()) {
                                android.provider.Settings.Global.putStringForUser(contentResolver, "data_roaming" + it.next().getSubscriptionId(), "0", i);
                            }
                        }
                        android.provider.Settings.Global.putStringForUser(contentResolver, "data_roaming", "0", i);
                    }
                    return;
                case 1:
                    if (z) {
                        android.provider.Settings.Secure.putIntForUser(contentResolver, "location_mode", 0, i);
                    }
                    return;
                case 2:
                    if (z && i == 0) {
                        android.provider.Settings.Global.putStringForUser(contentResolver, "adb_enabled", "0", i);
                        android.provider.Settings.Global.putStringForUser(contentResolver, "adb_wifi_enabled", "0", i);
                    }
                    return;
                case 3:
                    if (z) {
                        android.provider.Settings.Global.putStringForUser(context.getContentResolver(), "verifier_verify_adb_installs", "1", i);
                    }
                    return;
                case 4:
                    setInstallMarketAppsRestriction(contentResolver, i, getNewUserRestrictionSetting(context, i, "no_install_unknown_sources", z));
                    return;
                case 5:
                    setInstallMarketAppsRestriction(contentResolver, i, getNewUserRestrictionSetting(context, i, "no_install_unknown_sources_globally", z));
                    return;
                case 6:
                    if (z) {
                        if (android.app.ActivityManager.getCurrentUser() != i && i != 0) {
                            try {
                                android.app.ActivityManager.getService().stopUser(i, false, (android.app.IStopUserCallback) null);
                            } catch (android.os.RemoteException e) {
                                throw e.rethrowAsRuntimeException();
                            }
                        }
                    }
                    return;
                case 7:
                    android.content.ContentResolver contentResolver2 = context.getContentResolver();
                    if (!z) {
                        i2 = 0;
                    }
                    android.provider.Settings.Global.putInt(contentResolver2, "safe_boot_disallowed", i2);
                    return;
                case '\b':
                    if (z) {
                        if (android.provider.Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 1) {
                            i2 = 0;
                        }
                        if (i2 != 0) {
                            android.provider.Settings.Global.putInt(context.getContentResolver(), "airplane_mode_on", 0);
                            android.content.Intent intent = new android.content.Intent("android.intent.action.AIRPLANE_MODE");
                            intent.putExtra("state", false);
                            context.sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
                        }
                    }
                    return;
                case '\t':
                    if (z) {
                        new android.hardware.display.AmbientDisplayConfiguration(context).disableDozeSettings(i);
                    }
                    return;
                case '\n':
                case 11:
                    android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
                    packageManagerInternal.removeAllNonSystemPackageSuspensions(i);
                    packageManagerInternal.removeAllDistractingPackageRestrictions(i);
                    packageManagerInternal.flushPackageRestrictions(i);
                    return;
                default:
                    return;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSettingRestrictedForUser(android.content.Context context, @android.annotation.NonNull java.lang.String str, int i, java.lang.String str2, int i2) {
        char c;
        java.lang.String str3;
        java.util.Objects.requireNonNull(str);
        android.os.UserManager userManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
        switch (str.hashCode()) {
            case -2099894345:
                if (str.equals("adb_wifi_enabled")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1796809747:
                if (str.equals("location_mode")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1490222856:
                if (str.equals("doze_enabled")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1115710219:
                if (str.equals("verifier_verify_adb_installs")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -970351711:
                if (str.equals("adb_enabled")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -693072130:
                if (str.equals("screen_brightness_mode")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -623873498:
                if (str.equals("always_on_vpn_app")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -416662510:
                if (str.equals("preferred_network_mode")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -101820922:
                if (str.equals("doze_always_on")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -32505807:
                if (str.equals("doze_pulse_on_long_press")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -16943007:
                if (str.equals("screen_brightness_float")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 58027029:
                if (str.equals("safe_boot_disallowed")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 258514750:
                if (str.equals("screen_off_timeout")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 683724341:
                if (str.equals("private_dns_mode")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 926123534:
                if (str.equals("airplane_mode_on")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1073289638:
                if (str.equals("doze_pulse_on_double_tap")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 1223734380:
                if (str.equals("private_dns_specifier")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 1275530062:
                if (str.equals("auto_time_zone")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 1334097968:
                if (str.equals("always_on_vpn_lockdown_whitelist")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1602982312:
                if (str.equals("doze_pulse_on_pick_up")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 1646894952:
                if (str.equals("always_on_vpn_lockdown")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1661297501:
                if (str.equals("auto_time")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 1701140351:
                if (str.equals("install_non_market_apps")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1735689732:
                if (str.equals("screen_brightness")) {
                    c = 16;
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
                if (userManager.hasUserRestriction("no_config_location", android.os.UserHandle.of(i)) && i2 != 1000) {
                    return true;
                }
                if (!java.lang.String.valueOf(0).equals(str2)) {
                    str3 = "no_share_location";
                    break;
                } else {
                    return false;
                }
            case 1:
                if (!"0".equals(str2)) {
                    str3 = "no_install_unknown_sources";
                    break;
                } else {
                    return false;
                }
            case 2:
            case 3:
                if (!"0".equals(str2)) {
                    str3 = "no_debugging_features";
                    break;
                } else {
                    return false;
                }
            case 4:
                if (!"1".equals(str2)) {
                    str3 = "ensure_verify_apps";
                    break;
                } else {
                    return false;
                }
            case 5:
                str3 = "no_config_mobile_networks";
                break;
            case 6:
            case 7:
            case '\b':
                int appId = android.os.UserHandle.getAppId(i2);
                if (appId != 1000 && appId != 0) {
                    str3 = "no_config_vpn";
                    break;
                } else {
                    return false;
                }
            case '\t':
                if (!"1".equals(str2)) {
                    str3 = "no_safe_boot";
                    break;
                } else {
                    return false;
                }
            case '\n':
                if (!"0".equals(str2)) {
                    str3 = "no_airplane_mode";
                    break;
                } else {
                    return false;
                }
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
                if (!"0".equals(str2)) {
                    str3 = "no_ambient_display";
                    break;
                } else {
                    return false;
                }
            case 16:
            case 17:
            case 18:
                if (i2 != 1000) {
                    str3 = "no_config_brightness";
                    break;
                } else {
                    return false;
                }
            case 19:
            case 20:
                if (i2 != 1000) {
                    str3 = "no_config_date_time";
                    break;
                } else {
                    return false;
                }
            case 21:
                if (i2 != 1000) {
                    str3 = "no_config_screen_timeout";
                    break;
                } else {
                    return false;
                }
            case 22:
            case 23:
                if (i2 != 1000) {
                    str3 = "disallow_config_private_dns";
                    break;
                } else {
                    return false;
                }
            default:
                if (str.startsWith("data_roaming") && !"0".equals(str2)) {
                    str3 = "no_data_roaming";
                    break;
                } else {
                    return false;
                }
        }
        return userManager.hasUserRestriction(str3, android.os.UserHandle.of(i));
    }

    public static void dumpRestrictions(java.io.PrintWriter printWriter, java.lang.String str, android.os.Bundle bundle) {
        if (bundle != null) {
            boolean z = true;
            for (java.lang.String str2 : bundle.keySet()) {
                if (bundle.getBoolean(str2, false)) {
                    printWriter.println(str + str2);
                    z = false;
                }
            }
            if (z) {
                printWriter.println(str + "none");
                return;
            }
            return;
        }
        printWriter.println(str + "null");
    }

    public static void moveRestriction(java.lang.String str, android.util.SparseArray<com.android.server.pm.RestrictionsSet> sparseArray, com.android.server.pm.RestrictionsSet restrictionsSet) {
        for (int i = 0; i < sparseArray.size(); i++) {
            sparseArray.valueAt(i).moveRestriction(restrictionsSet, str);
        }
    }

    public static boolean restrictionsChanged(android.os.Bundle bundle, android.os.Bundle bundle2, java.lang.String... strArr) {
        if (strArr.length == 0) {
            return areEqual(bundle, bundle2);
        }
        for (java.lang.String str : strArr) {
            if (bundle.getBoolean(str, false) != bundle2.getBoolean(str, false)) {
                return true;
            }
        }
        return false;
    }

    private static void setInstallMarketAppsRestriction(android.content.ContentResolver contentResolver, int i, int i2) {
        android.provider.Settings.Secure.putIntForUser(contentResolver, "install_non_market_apps", i2, i);
    }

    private static int getNewUserRestrictionSetting(android.content.Context context, int i, java.lang.String str, boolean z) {
        return (z || android.os.UserManager.get(context).hasUserRestriction(str, android.os.UserHandle.of(i))) ? 0 : 1;
    }
}
