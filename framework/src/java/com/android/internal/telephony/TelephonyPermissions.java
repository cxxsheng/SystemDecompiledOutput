package com.android.internal.telephony;

/* loaded from: classes5.dex */
public final class TelephonyPermissions {
    private static final boolean DBG = false;
    private static final java.lang.String LOG_TAG = "TelephonyPermissions";
    private static final java.lang.String PROPERTY_DEVICE_IDENTIFIER_ACCESS_RESTRICTIONS_DISABLED = "device_identifier_access_restrictions_disabled";
    private static final com.android.internal.telephony.flags.FeatureFlags sFeatureFlag = new com.android.internal.telephony.flags.FeatureFlagsImpl();
    private static final java.util.Map<java.lang.String, java.util.Set<java.lang.String>> sReportedDeviceIDPackages = new java.util.HashMap();

    private TelephonyPermissions() {
    }

    public static boolean checkCallingOrSelfReadPhoneState(android.content.Context context, int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return checkReadPhoneState(context, i, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), str, str2, str3);
    }

    public static boolean checkCallingOrSelfReadPhoneStateNoThrow(android.content.Context context, int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        try {
            return checkCallingOrSelfReadPhoneState(context, i, str, str2, str3);
        } catch (java.lang.SecurityException e) {
            return false;
        }
    }

    public static boolean checkInternetPermissionNoThrow(android.content.Context context, java.lang.String str) {
        try {
            context.enforcePermission(android.Manifest.permission.INTERNET, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), str);
            return true;
        } catch (java.lang.SecurityException e) {
            return false;
        }
    }

    public static boolean checkCallingOrSelfReadNonDangerousPhoneStateNoThrow(android.content.Context context, java.lang.String str) {
        try {
            context.enforcePermission(android.Manifest.permission.READ_BASIC_PHONE_STATE, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), str);
            return true;
        } catch (java.lang.SecurityException e) {
            return false;
        }
    }

    public static boolean checkReadPhoneState(android.content.Context context, int i, int i2, int i3, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        try {
            context.enforcePermission(android.Manifest.permission.READ_PRIVILEGED_PHONE_STATE, i2, i3, str3);
            return true;
        } catch (java.lang.SecurityException e) {
            try {
                context.enforcePermission(android.Manifest.permission.READ_PHONE_STATE, i2, i3, str3);
                return ((android.app.AppOpsManager) context.getSystemService(android.content.Context.APP_OPS_SERVICE)).noteOpNoThrow(android.app.AppOpsManager.OPSTR_READ_PHONE_STATE, i3, str, str2, (java.lang.String) null) == 0;
            } catch (java.lang.SecurityException e2) {
                if (android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
                    enforceCarrierPrivilege(context, i, i3, str3);
                    return true;
                }
                throw e2;
            }
        }
    }

    public static boolean checkCarrierPrivilegeForSubId(android.content.Context context, int i) {
        return android.telephony.SubscriptionManager.isValidSubscriptionId(i) && getCarrierPrivilegeStatus(context, i, android.os.Binder.getCallingUid()) == 1;
    }

    public static boolean checkReadPhoneStateOnAnyActiveSub(android.content.Context context, int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        try {
            context.enforcePermission(android.Manifest.permission.READ_PRIVILEGED_PHONE_STATE, i, i2, str3);
            return true;
        } catch (java.lang.SecurityException e) {
            try {
                context.enforcePermission(android.Manifest.permission.READ_PHONE_STATE, i, i2, str3);
                return ((android.app.AppOpsManager) context.getSystemService(android.content.Context.APP_OPS_SERVICE)).noteOpNoThrow(android.app.AppOpsManager.OPSTR_READ_PHONE_STATE, i2, str, str2, (java.lang.String) null) == 0;
            } catch (java.lang.SecurityException e2) {
                return checkCarrierPrivilegeForAnySubId(context, i2);
            }
        }
    }

    public static boolean checkCallingOrSelfReadDeviceIdentifiers(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return checkCallingOrSelfReadDeviceIdentifiers(context, -1, str, str2, str3);
    }

    public static boolean checkCallingOrSelfReadDeviceIdentifiers(android.content.Context context, int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (checkCallingOrSelfUseIccAuthWithDeviceIdentifier(context, str, str2, str3)) {
            return true;
        }
        return checkPrivilegedReadPermissionOrCarrierPrivilegePermission(context, i, str, str2, str3, true, true);
    }

    public static boolean checkCallingOrSelfReadSubscriberIdentifiers(android.content.Context context, int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return checkCallingOrSelfReadSubscriberIdentifiers(context, i, str, str2, str3, true);
    }

    public static boolean checkCallingOrSelfReadSubscriberIdentifiers(android.content.Context context, int i, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) {
        if (checkCallingOrSelfUseIccAuthWithDeviceIdentifier(context, str, str2, str3)) {
            return true;
        }
        return checkPrivilegedReadPermissionOrCarrierPrivilegePermission(context, i, str, str2, str3, false, z);
    }

    private static void throwSecurityExceptionAsUidDoesNotHaveAccess(java.lang.String str, int i) {
        throw new java.lang.SecurityException(str + ": The uid " + i + " does not meet the requirements to access device identifiers.");
    }

    private static boolean checkPrivilegedReadPermissionOrCarrierPrivilegePermission(android.content.Context context, int i, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, boolean z2) {
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        if (checkCarrierPrivilegeForSubId(context, i)) {
            return true;
        }
        if (z && checkCarrierPrivilegeForAnySubId(context, callingUid)) {
            return true;
        }
        try {
            if (((android.permission.LegacyPermissionManager) context.getSystemService(android.content.Context.LEGACY_PERMISSION_SERVICE)).checkDeviceIdentifierAccess(str, str3, str2, callingPid, callingUid) == 0) {
                return true;
            }
        } catch (java.lang.SecurityException e) {
            throwSecurityExceptionAsUidDoesNotHaveAccess(str3, callingUid);
        }
        if (z2) {
            return reportAccessDeniedToReadIdentifiers(context, i, callingPid, callingUid, str, str3);
        }
        return false;
    }

    private static boolean reportAccessDeniedToReadIdentifiers(android.content.Context context, int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
        android.content.pm.ApplicationInfo applicationInfo;
        java.util.Set<java.lang.String> set;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfoAsUser(str, 0, android.os.UserHandle.getUserHandleForUid(i3));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(LOG_TAG, "Exception caught obtaining package info for package " + str, e);
            applicationInfo = null;
        }
        boolean containsKey = sReportedDeviceIDPackages.containsKey(str);
        if (!containsKey || !sReportedDeviceIDPackages.get(str).contains(str2)) {
            if (!containsKey) {
                set = new java.util.HashSet<>();
                sReportedDeviceIDPackages.put(str, set);
            } else {
                set = sReportedDeviceIDPackages.get(str);
            }
            set.add(str2);
            com.android.internal.telephony.TelephonyCommonStatsLog.write(172, str, str2, false, false);
        }
        android.util.Log.w(LOG_TAG, "reportAccessDeniedToReadIdentifiers:" + str + ":" + str2 + ":" + i);
        if (applicationInfo != null && applicationInfo.targetSdkVersion < 29 && (context.checkPermission(android.Manifest.permission.READ_PHONE_STATE, i2, i3) == 0 || checkCarrierPrivilegeForSubId(context, i))) {
            return false;
        }
        throwSecurityExceptionAsUidDoesNotHaveAccess(str2, i3);
        return true;
    }

    public static boolean checkCallingOrSelfUseIccAuthWithDeviceIdentifier(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (str == null) {
            return false;
        }
        switch (((android.app.AppOpsManager) context.getSystemService(android.content.Context.APP_OPS_SERVICE)).noteOpNoThrow(android.app.AppOpsManager.OPSTR_USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER, android.os.Binder.getCallingUid(), str, str2, str3)) {
            case 3:
                if (context.checkCallingOrSelfPermission(android.Manifest.permission.USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER) == 0) {
                    break;
                }
                break;
        }
        return false;
    }

    public static boolean checkReadCallLog(android.content.Context context, int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
        if (context.checkPermission(android.Manifest.permission.READ_CALL_LOG, i2, i3) == 0) {
            return ((android.app.AppOpsManager) context.getSystemService(android.content.Context.APP_OPS_SERVICE)).noteOpNoThrow(android.app.AppOpsManager.OPSTR_READ_CALL_LOG, i3, str, str2, (java.lang.String) null) == 0;
        }
        if (!android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            return false;
        }
        enforceCarrierPrivilege(context, i, i3, "readCallLog");
        return true;
    }

    public static boolean checkCallingOrSelfReadPhoneNumber(android.content.Context context, int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return checkReadPhoneNumber(context, i, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), str, str2, str3);
    }

    public static boolean checkReadPhoneNumber(android.content.Context context, int i, int i2, int i3, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        int checkPhoneNumberAccess = ((android.permission.LegacyPermissionManager) context.getSystemService(android.content.Context.LEGACY_PERMISSION_SERVICE)).checkPhoneNumberAccess(str, str3, str2, i2, i3);
        if (checkPhoneNumberAccess == 0) {
            return true;
        }
        if (android.telephony.SubscriptionManager.isValidSubscriptionId(i) && getCarrierPrivilegeStatus(context, i, i3) == 1) {
            return true;
        }
        if (checkPhoneNumberAccess == 1) {
            return false;
        }
        throw new java.lang.SecurityException(str3 + ": Neither user " + i3 + " nor current process has " + android.Manifest.permission.READ_PHONE_STATE + ", " + android.Manifest.permission.READ_SMS + ", or " + android.Manifest.permission.READ_PHONE_NUMBERS);
    }

    public static void enforceCallingOrSelfModifyPermissionOrCarrierPrivilege(android.content.Context context, int i, java.lang.String str) {
        if (context.checkCallingOrSelfPermission(android.Manifest.permission.MODIFY_PHONE_STATE) == 0) {
            return;
        }
        enforceCallingOrSelfCarrierPrivilege(context, i, str);
    }

    public static boolean checkLastKnownCellIdAccessPermission(android.content.Context context) {
        return context.checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_LAST_KNOWN_CELL_ID) == 0;
    }

    public static void enforceCallingOrSelfReadPhoneStatePermissionOrCarrierPrivilege(android.content.Context context, int i, java.lang.String str) {
        if (context.checkCallingOrSelfPermission(android.Manifest.permission.READ_PHONE_STATE) == 0) {
            return;
        }
        enforceCallingOrSelfCarrierPrivilege(context, i, str);
    }

    public static void enforceCallingOrSelfReadPrivilegedPhoneStatePermissionOrCarrierPrivilege(android.content.Context context, int i, java.lang.String str) {
        if (context.checkCallingOrSelfPermission(android.Manifest.permission.READ_PRIVILEGED_PHONE_STATE) == 0) {
            return;
        }
        enforceCallingOrSelfCarrierPrivilege(context, i, str);
    }

    public static void enforceCallingOrSelfReadPrecisePhoneStatePermissionOrCarrierPrivilege(android.content.Context context, int i, java.lang.String str) {
        if (context.checkCallingOrSelfPermission(android.Manifest.permission.READ_PRIVILEGED_PHONE_STATE) == 0 || context.checkCallingOrSelfPermission(android.Manifest.permission.READ_PRECISE_PHONE_STATE) == 0) {
            return;
        }
        enforceCallingOrSelfCarrierPrivilege(context, i, str);
    }

    public static void enforceCallingOrSelfCarrierPrivilege(android.content.Context context, int i, java.lang.String str) {
        enforceCarrierPrivilege(context, i, android.os.Binder.getCallingUid(), str);
    }

    private static void enforceCarrierPrivilege(android.content.Context context, int i, int i2, java.lang.String str) {
        if (getCarrierPrivilegeStatus(context, i, i2) != 1) {
            throw new java.lang.SecurityException(str);
        }
    }

    private static boolean checkCarrierPrivilegeForAnySubId(android.content.Context context, int i) {
        android.telephony.SubscriptionManager subscriptionManager = (android.telephony.SubscriptionManager) context.getSystemService(android.content.Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int[] completeActiveSubscriptionIdList = subscriptionManager.getCompleteActiveSubscriptionIdList();
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            for (int i2 : completeActiveSubscriptionIdList) {
                if (getCarrierPrivilegeStatus(context, i2, i) == 1) {
                    return true;
                }
            }
            return false;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private static int getCarrierPrivilegeStatus(android.content.Context context, int i, int i2) {
        if (i2 == 1000 || i2 == 1001) {
            return 1;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return ((android.telephony.TelephonyManager) context.getSystemService("phone")).createForSubscriptionId(i).getCarrierPrivilegeStatus(i2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static void enforceAnyPermissionGranted(android.content.Context context, int i, java.lang.String str, java.lang.String... strArr) {
        int i2;
        boolean z;
        if (strArr.length == 0) {
            return;
        }
        int length = strArr.length;
        int i3 = 0;
        while (true) {
            if (i3 < length) {
                if (context.checkCallingOrSelfPermission(strArr[i3]) != 0) {
                    i3++;
                } else {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        if (z) {
            return;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str);
        sb.append(": Neither user ");
        sb.append(i);
        sb.append(" nor current process has ");
        sb.append(strArr[0]);
        for (i2 = 1; i2 < strArr.length; i2++) {
            sb.append(" or ");
            sb.append(strArr[i2]);
        }
        throw new java.lang.SecurityException(sb.toString());
    }

    public static void enforceAnyPermissionGrantedOrCarrierPrivileges(android.content.Context context, int i, int i2, java.lang.String str, java.lang.String... strArr) {
        enforceAnyPermissionGrantedOrCarrierPrivileges(context, i, i2, false, str, strArr);
    }

    public static void enforceAnyPermissionGrantedOrCarrierPrivileges(android.content.Context context, int i, int i2, boolean z, java.lang.String str, java.lang.String... strArr) {
        int i3;
        boolean z2;
        if (strArr.length == 0) {
            return;
        }
        int length = strArr.length;
        int i4 = 0;
        while (true) {
            if (i4 < length) {
                if (context.checkCallingOrSelfPermission(strArr[i4]) != 0) {
                    i4++;
                } else {
                    z2 = true;
                    break;
                }
            } else {
                z2 = false;
                break;
            }
        }
        if (z2) {
            return;
        }
        if (z) {
            if (checkCarrierPrivilegeForAnySubId(context, android.os.Binder.getCallingUid())) {
                return;
            }
        } else if (checkCarrierPrivilegeForSubId(context, i)) {
            return;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str);
        sb.append(": Neither user ");
        sb.append(i2);
        sb.append(" nor current process has ");
        sb.append(strArr[0]);
        for (i3 = 1; i3 < strArr.length; i3++) {
            sb.append(" or ");
            sb.append(strArr[i3]);
        }
        sb.append(" or carrier privileges. subId=" + i + ", allowCarrierPrivilegeOnAnySub=" + z);
        throw new java.lang.SecurityException(sb.toString());
    }

    public static void enforceShellOnly(int i, java.lang.String str) {
        if (i == 2000 || i == 0) {
        } else {
            throw new java.lang.SecurityException(str + ": Only shell user can call it");
        }
    }

    public static int getTargetSdk(android.content.Context context, java.lang.String str) {
        try {
            android.content.pm.ApplicationInfo applicationInfoAsUser = context.getPackageManager().getApplicationInfoAsUser(str, 0, android.os.UserHandle.getUserHandleForUid(android.os.Binder.getCallingUid()));
            if (applicationInfoAsUser != null) {
                return applicationInfoAsUser.targetSdkVersion;
            }
            return Integer.MAX_VALUE;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(LOG_TAG, "Failed to get package info for pkg=" + str + ", uid=" + android.os.Binder.getCallingUid());
            return Integer.MAX_VALUE;
        }
    }

    public static boolean checkSubscriptionAssociatedWithUser(android.content.Context context, int i, android.os.UserHandle userHandle, java.lang.String str) {
        android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) context.getSystemService("phone");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (telephonyManager != null) {
                try {
                    if (telephonyManager.isEmergencyNumber(str)) {
                        android.util.Log.d(LOG_TAG, "checkSubscriptionAssociatedWithUser: destAddr is emergency number");
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                } catch (java.lang.Exception e) {
                    android.util.Log.e(LOG_TAG, "Cannot verify if destAddr is an emergency number: " + e);
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return checkSubscriptionAssociatedWithUser(context, i, userHandle);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static boolean checkSubscriptionAssociatedWithUser(android.content.Context context, int i, android.os.UserHandle userHandle) {
        if (!sFeatureFlag.rejectBadSubIdInteraction() && !android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            return true;
        }
        android.telephony.SubscriptionManager subscriptionManager = (android.telephony.SubscriptionManager) context.getSystemService(android.content.Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        if (subscriptionManager != null) {
            try {
                if (!subscriptionManager.isSubscriptionAssociatedWithUser(i, userHandle)) {
                    android.util.Log.e(LOG_TAG, "User[User ID:" + userHandle.getIdentifier() + "] is not associated with Subscription ID:" + i);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.e(LOG_TAG, "Subscription[Subscription ID:" + i + "] has no records on device");
                return !sFeatureFlag.rejectBadSubIdInteraction();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return true;
    }

    public static boolean checkCallingOrSelfReadPrivilegedPhoneStatePermissionOrReadPhoneNumber(android.content.Context context, int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            return context.checkCallingOrSelfPermission(android.Manifest.permission.READ_PRIVILEGED_PHONE_STATE) == 0 || checkCallingOrSelfReadPhoneNumber(context, i, str, str2, str3);
        }
        return false;
    }
}
