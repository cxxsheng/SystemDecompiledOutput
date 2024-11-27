package com.android.internal.telephony;

/* loaded from: classes5.dex */
public final class CarrierAppUtils {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "CarrierAppUtils";

    private CarrierAppUtils() {
    }

    public static synchronized void disableCarrierAppsUntilPrivileged(java.lang.String str, android.telephony.TelephonyManager telephonyManager, int i, android.content.Context context) {
        synchronized (com.android.internal.telephony.CarrierAppUtils.class) {
            android.os.SystemConfigManager systemConfigManager = (android.os.SystemConfigManager) context.getSystemService(android.os.SystemConfigManager.class);
            disableCarrierAppsUntilPrivileged(str, telephonyManager, getContentResolverForUser(context, i), i, systemConfigManager.getDisabledUntilUsedPreinstalledCarrierApps(), systemConfigManager.getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries(), context);
        }
    }

    public static synchronized void disableCarrierAppsUntilPrivileged(java.lang.String str, int i, android.content.Context context) {
        synchronized (com.android.internal.telephony.CarrierAppUtils.class) {
            android.os.SystemConfigManager systemConfigManager = (android.os.SystemConfigManager) context.getSystemService(android.os.SystemConfigManager.class);
            disableCarrierAppsUntilPrivileged(str, null, getContentResolverForUser(context, i), i, systemConfigManager.getDisabledUntilUsedPreinstalledCarrierApps(), systemConfigManager.getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries(), context);
        }
    }

    private static android.content.ContentResolver getContentResolverForUser(android.content.Context context, int i) {
        return context.createContextAsUser(android.os.UserHandle.of(i), 0).getContentResolver();
    }

    private static boolean isUpdatedSystemApp(android.content.pm.ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & 128) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007e A[Catch: NameNotFoundException -> 0x02cb, TryCatch #0 {NameNotFoundException -> 0x02cb, blocks: (B:12:0x004c, B:13:0x0050, B:15:0x0056, B:17:0x0064, B:20:0x0072, B:22:0x007e, B:23:0x0082, B:25:0x0088, B:28:0x00a4, B:31:0x00bf, B:35:0x00d6, B:37:0x0116, B:38:0x011a, B:40:0x0120, B:50:0x0151, B:55:0x01a1, B:60:0x00d0, B:62:0x01ab, B:66:0x01bb, B:68:0x01c1, B:70:0x01ef, B:71:0x01f3, B:73:0x01f9, B:76:0x0203, B:78:0x0208, B:80:0x020e, B:84:0x021c, B:90:0x0243, B:111:0x02aa, B:113:0x02b0, B:118:0x029d), top: B:11:0x004c }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00bf A[Catch: NameNotFoundException -> 0x02cb, TRY_ENTER, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x02cb, blocks: (B:12:0x004c, B:13:0x0050, B:15:0x0056, B:17:0x0064, B:20:0x0072, B:22:0x007e, B:23:0x0082, B:25:0x0088, B:28:0x00a4, B:31:0x00bf, B:35:0x00d6, B:37:0x0116, B:38:0x011a, B:40:0x0120, B:50:0x0151, B:55:0x01a1, B:60:0x00d0, B:62:0x01ab, B:66:0x01bb, B:68:0x01c1, B:70:0x01ef, B:71:0x01f3, B:73:0x01f9, B:76:0x0203, B:78:0x0208, B:80:0x020e, B:84:0x021c, B:90:0x0243, B:111:0x02aa, B:113:0x02b0, B:118:0x029d), top: B:11:0x004c }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01ab A[Catch: NameNotFoundException -> 0x02cb, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x02cb, blocks: (B:12:0x004c, B:13:0x0050, B:15:0x0056, B:17:0x0064, B:20:0x0072, B:22:0x007e, B:23:0x0082, B:25:0x0088, B:28:0x00a4, B:31:0x00bf, B:35:0x00d6, B:37:0x0116, B:38:0x011a, B:40:0x0120, B:50:0x0151, B:55:0x01a1, B:60:0x00d0, B:62:0x01ab, B:66:0x01bb, B:68:0x01c1, B:70:0x01ef, B:71:0x01f3, B:73:0x01f9, B:76:0x0203, B:78:0x0208, B:80:0x020e, B:84:0x021c, B:90:0x0243, B:111:0x02aa, B:113:0x02b0, B:118:0x029d), top: B:11:0x004c }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x023a  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x023c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void disableCarrierAppsUntilPrivileged(java.lang.String str, android.telephony.TelephonyManager telephonyManager, android.content.ContentResolver contentResolver, int i, java.util.Set<java.lang.String> set, java.util.Map<java.lang.String, java.util.List<android.os.CarrierAssociatedAppEntry>> map, android.content.Context context) {
        android.permission.LegacyPermissionManager legacyPermissionManager;
        boolean z;
        java.util.List<com.android.internal.telephony.CarrierAppUtils.AssociatedAppInfo> list;
        java.util.Map<java.lang.String, java.util.List<com.android.internal.telephony.CarrierAppUtils.AssociatedAppInfo>> map2;
        java.lang.String str2;
        int i2;
        int i3;
        boolean z2;
        android.telephony.TelephonyManager telephonyManager2 = telephonyManager;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        android.permission.LegacyPermissionManager legacyPermissionManager2 = (android.permission.LegacyPermissionManager) context.getSystemService(android.content.Context.LEGACY_PERMISSION_SERVICE);
        java.util.List<android.content.pm.ApplicationInfo> defaultCarrierAppCandidatesHelper = getDefaultCarrierAppCandidatesHelper(i, set, context);
        if (defaultCarrierAppCandidatesHelper == null || defaultCarrierAppCandidatesHelper.isEmpty()) {
            return;
        }
        java.util.Map<java.lang.String, java.util.List<com.android.internal.telephony.CarrierAppUtils.AssociatedAppInfo>> defaultCarrierAssociatedAppsHelper = getDefaultCarrierAssociatedAppsHelper(i, map, context);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int userId = contentResolver.getUserId();
        java.lang.String str3 = android.provider.Settings.Secure.CARRIER_APPS_HANDLED;
        int intForUser = android.provider.Settings.Secure.getIntForUser(contentResolver, android.provider.Settings.Secure.CARRIER_APPS_HANDLED, 0, userId);
        boolean z3 = intForUser != 0;
        boolean z4 = intForUser == android.os.Build.VERSION.SDK_INT;
        try {
            java.util.Iterator<android.content.pm.ApplicationInfo> it = defaultCarrierAppCandidatesHelper.iterator();
            while (it.hasNext()) {
                android.content.pm.ApplicationInfo next = it.next();
                java.util.Iterator<android.content.pm.ApplicationInfo> it2 = it;
                java.lang.String str4 = next.packageName;
                if (telephonyManager2 == null) {
                    legacyPermissionManager = legacyPermissionManager2;
                } else {
                    legacyPermissionManager = legacyPermissionManager2;
                    if (telephonyManager2.checkCarrierPrivilegesForPackageAnyPhone(str4) == 1) {
                        z = true;
                        packageManager.setSystemAppState(str4, 0);
                        list = defaultCarrierAssociatedAppsHelper.get(str4);
                        if (list != null) {
                            map2 = defaultCarrierAssociatedAppsHelper;
                        } else {
                            java.util.Iterator<com.android.internal.telephony.CarrierAppUtils.AssociatedAppInfo> it3 = list.iterator();
                            while (it3.hasNext()) {
                                packageManager.setSystemAppState(it3.next().appInfo.packageName, 0);
                                defaultCarrierAssociatedAppsHelper = defaultCarrierAssociatedAppsHelper;
                            }
                            map2 = defaultCarrierAssociatedAppsHelper;
                        }
                        int applicationEnabledSetting = context.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().getApplicationEnabledSetting(str4);
                        android.content.pm.PackageManager packageManager2 = packageManager;
                        if (!z) {
                            str2 = str3;
                            int i4 = intForUser;
                            if ((!isUpdatedSystemApp(next) && applicationEnabledSetting == 0) || applicationEnabledSetting == 4 || (next.flags & 8388608) == 0) {
                                android.util.Log.i(TAG, "Update state (" + str4 + "): ENABLED for user " + i);
                                context.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().setSystemAppState(str4, 2);
                                context.createPackageContextAsUser(str, 0, android.os.UserHandle.of(i)).getPackageManager().setApplicationEnabledSetting(str4, 1, 1);
                            }
                            if (list != null) {
                                for (com.android.internal.telephony.CarrierAppUtils.AssociatedAppInfo associatedAppInfo : list) {
                                    int applicationEnabledSetting2 = context.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().getApplicationEnabledSetting(associatedAppInfo.appInfo.packageName);
                                    boolean z5 = (associatedAppInfo.appInfo.flags & 8388608) != 0;
                                    if (applicationEnabledSetting2 != 0 && applicationEnabledSetting2 != 4 && z5) {
                                    }
                                    android.util.Log.i(TAG, "Update associated state (" + associatedAppInfo.appInfo.packageName + "): ENABLED for user " + i);
                                    context.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().setSystemAppState(associatedAppInfo.appInfo.packageName, 2);
                                    context.createPackageContextAsUser(str, 0, android.os.UserHandle.of(i)).getPackageManager().setApplicationEnabledSetting(associatedAppInfo.appInfo.packageName, 1, 1);
                                }
                            }
                            arrayList.add(next.packageName);
                            i2 = i4;
                        } else {
                            int i5 = intForUser;
                            str2 = str3;
                            if (!isUpdatedSystemApp(next) && applicationEnabledSetting == 0 && (next.flags & 8388608) != 0) {
                                android.util.Log.i(TAG, "Update state (" + str4 + "): DISABLED_UNTIL_USED for user " + i);
                                context.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().setSystemAppState(str4, 3);
                            }
                            if (list == null) {
                                i2 = i5;
                            } else {
                                for (com.android.internal.telephony.CarrierAppUtils.AssociatedAppInfo associatedAppInfo2 : list) {
                                    if (z3) {
                                        if (z4 || associatedAppInfo2.addedInSdk == -1) {
                                            i3 = i5;
                                        } else {
                                            i3 = i5;
                                            if (associatedAppInfo2.addedInSdk > i3 && associatedAppInfo2.addedInSdk <= android.os.Build.VERSION.SDK_INT) {
                                            }
                                        }
                                        z2 = false;
                                        int applicationEnabledSetting3 = context.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().getApplicationEnabledSetting(associatedAppInfo2.appInfo.packageName);
                                        boolean z6 = (associatedAppInfo2.appInfo.flags & 8388608) == 0;
                                        if (!z2 && applicationEnabledSetting3 == 0 && z6) {
                                            android.util.Log.i(TAG, "Update associated state (" + associatedAppInfo2.appInfo.packageName + "): DISABLED_UNTIL_USED for user " + i);
                                            context.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().setSystemAppState(associatedAppInfo2.appInfo.packageName, 3);
                                        }
                                        i5 = i3;
                                    } else {
                                        i3 = i5;
                                    }
                                    z2 = true;
                                    int applicationEnabledSetting32 = context.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().getApplicationEnabledSetting(associatedAppInfo2.appInfo.packageName);
                                    if ((associatedAppInfo2.appInfo.flags & 8388608) == 0) {
                                    }
                                    if (!z2) {
                                    }
                                    i5 = i3;
                                }
                                i2 = i5;
                            }
                        }
                        telephonyManager2 = telephonyManager;
                        intForUser = i2;
                        it = it2;
                        legacyPermissionManager2 = legacyPermissionManager;
                        packageManager = packageManager2;
                        defaultCarrierAssociatedAppsHelper = map2;
                        str3 = str2;
                    }
                }
                z = false;
                packageManager.setSystemAppState(str4, 0);
                list = defaultCarrierAssociatedAppsHelper.get(str4);
                if (list != null) {
                }
                int applicationEnabledSetting4 = context.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().getApplicationEnabledSetting(str4);
                android.content.pm.PackageManager packageManager22 = packageManager;
                if (!z) {
                }
                telephonyManager2 = telephonyManager;
                intForUser = i2;
                it = it2;
                legacyPermissionManager2 = legacyPermissionManager;
                packageManager = packageManager22;
                defaultCarrierAssociatedAppsHelper = map2;
                str3 = str2;
            }
            android.permission.LegacyPermissionManager legacyPermissionManager3 = legacyPermissionManager2;
            java.lang.String str5 = str3;
            if (!z3 || !z4) {
                android.provider.Settings.Secure.putIntForUser(contentResolver, str5, android.os.Build.VERSION.SDK_INT, contentResolver.getUserId());
            }
            if (!arrayList.isEmpty()) {
                java.lang.String[] strArr = new java.lang.String[arrayList.size()];
                arrayList.toArray(strArr);
                legacyPermissionManager3.grantDefaultPermissionsToEnabledCarrierApps(strArr, android.os.UserHandle.of(i), com.android.internal.telephony.util.TelephonyUtils.DIRECT_EXECUTOR, new java.util.function.Consumer() { // from class: com.android.internal.telephony.CarrierAppUtils$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.internal.telephony.CarrierAppUtils.lambda$disableCarrierAppsUntilPrivileged$0((java.lang.Boolean) obj);
                    }
                });
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, "Could not reach PackageManager", e);
        }
    }

    static /* synthetic */ void lambda$disableCarrierAppsUntilPrivileged$0(java.lang.Boolean bool) {
    }

    public static java.util.List<android.content.pm.ApplicationInfo> getDefaultCarrierApps(android.telephony.TelephonyManager telephonyManager, int i, android.content.Context context) {
        java.util.List<android.content.pm.ApplicationInfo> defaultCarrierAppCandidates = getDefaultCarrierAppCandidates(i, context);
        if (defaultCarrierAppCandidates == null || defaultCarrierAppCandidates.isEmpty()) {
            return null;
        }
        for (int size = defaultCarrierAppCandidates.size() - 1; size >= 0; size--) {
            if (!(telephonyManager.checkCarrierPrivilegesForPackageAnyPhone(defaultCarrierAppCandidates.get(size).packageName) == 1)) {
                defaultCarrierAppCandidates.remove(size);
            }
        }
        return defaultCarrierAppCandidates;
    }

    public static java.util.List<android.content.pm.ApplicationInfo> getDefaultCarrierAppCandidates(int i, android.content.Context context) {
        return getDefaultCarrierAppCandidatesHelper(i, ((android.os.SystemConfigManager) context.getSystemService(android.os.SystemConfigManager.class)).getDisabledUntilUsedPreinstalledCarrierApps(), context);
    }

    private static java.util.List<android.content.pm.ApplicationInfo> getDefaultCarrierAppCandidatesHelper(int i, java.util.Set<java.lang.String> set, android.content.Context context) {
        if (set == null || set.isEmpty()) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(set.size());
        java.util.Iterator<java.lang.String> it = set.iterator();
        while (it.hasNext()) {
            android.content.pm.ApplicationInfo applicationInfoIfSystemApp = getApplicationInfoIfSystemApp(i, it.next(), context);
            if (applicationInfoIfSystemApp != null) {
                arrayList.add(applicationInfoIfSystemApp);
            }
        }
        return arrayList;
    }

    private static java.util.Map<java.lang.String, java.util.List<com.android.internal.telephony.CarrierAppUtils.AssociatedAppInfo>> getDefaultCarrierAssociatedAppsHelper(int i, java.util.Map<java.lang.String, java.util.List<android.os.CarrierAssociatedAppEntry>> map, android.content.Context context) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(map.size());
        for (java.util.Map.Entry<java.lang.String, java.util.List<android.os.CarrierAssociatedAppEntry>> entry : map.entrySet()) {
            java.lang.String key = entry.getKey();
            java.util.List<android.os.CarrierAssociatedAppEntry> value = entry.getValue();
            for (int i2 = 0; i2 < value.size(); i2++) {
                android.os.CarrierAssociatedAppEntry carrierAssociatedAppEntry = value.get(i2);
                android.content.pm.ApplicationInfo applicationInfoIfSystemApp = getApplicationInfoIfSystemApp(i, carrierAssociatedAppEntry.packageName, context);
                if (applicationInfoIfSystemApp != null && !isUpdatedSystemApp(applicationInfoIfSystemApp)) {
                    java.util.List list = (java.util.List) arrayMap.get(key);
                    if (list == null) {
                        list = new java.util.ArrayList();
                        arrayMap.put(key, list);
                    }
                    list.add(new com.android.internal.telephony.CarrierAppUtils.AssociatedAppInfo(applicationInfoIfSystemApp, carrierAssociatedAppEntry.addedInSdk));
                }
            }
        }
        return arrayMap;
    }

    private static android.content.pm.ApplicationInfo getApplicationInfoIfSystemApp(int i, java.lang.String str, android.content.Context context) {
        try {
            android.content.pm.ApplicationInfo applicationInfo = context.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().getApplicationInfo(str, 537952256);
            if (applicationInfo != null) {
                return applicationInfo;
            }
            return null;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, "Could not reach PackageManager", e);
            return null;
        }
    }

    private static final class AssociatedAppInfo {
        public final int addedInSdk;
        public final android.content.pm.ApplicationInfo appInfo;

        AssociatedAppInfo(android.content.pm.ApplicationInfo applicationInfo, int i) {
            this.appInfo = applicationInfo;
            this.addedInSdk = i;
        }
    }
}
