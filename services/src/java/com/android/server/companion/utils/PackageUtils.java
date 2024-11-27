package com.android.server.companion.utils;

/* loaded from: classes.dex */
public final class PackageUtils {
    private static final android.content.Intent COMPANION_SERVICE_INTENT = new android.content.Intent("android.companion.CompanionDeviceService");
    private static final java.lang.String PROPERTY_PRIMARY_TAG = "android.companion.PROPERTY_PRIMARY_COMPANION_DEVICE_SERVICE";
    private static final java.lang.String TAG = "CDM_PackageUtils";

    @android.annotation.Nullable
    public static android.content.pm.PackageInfo getPackageInfo(@android.annotation.NonNull android.content.Context context, final int i, @android.annotation.NonNull final java.lang.String str) {
        final android.content.pm.PackageManager packageManager = context.getPackageManager();
        final android.content.pm.PackageManager.PackageInfoFlags of = android.content.pm.PackageManager.PackageInfoFlags.of(20480L);
        return (android.content.pm.PackageInfo) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.companion.utils.PackageUtils$$ExternalSyntheticLambda1
            public final java.lang.Object getOrThrow() {
                android.content.pm.PackageInfo lambda$getPackageInfo$0;
                lambda$getPackageInfo$0 = com.android.server.companion.utils.PackageUtils.lambda$getPackageInfo$0(packageManager, str, of, i);
                return lambda$getPackageInfo$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.content.pm.PackageInfo lambda$getPackageInfo$0(android.content.pm.PackageManager packageManager, java.lang.String str, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags, int i) throws java.lang.Exception {
        try {
            return packageManager.getPackageInfoAsUser(str, packageInfoFlags, i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Package [" + str + "] is not found.");
            return null;
        }
    }

    public static void enforceUsesCompanionDeviceFeature(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull java.lang.String str) {
        if (android.os.Binder.getCallingUid() == 1000) {
            return;
        }
        android.content.pm.PackageInfo packageInfo = getPackageInfo(context, i, str);
        if (packageInfo == null) {
            throw new java.lang.IllegalArgumentException("Package " + str + " doesn't exist.");
        }
        android.content.pm.FeatureInfo[] featureInfoArr = packageInfo.reqFeatures;
        if (featureInfoArr != null) {
            for (android.content.pm.FeatureInfo featureInfo : featureInfoArr) {
                if ("android.software.companion_device_setup".equals(featureInfo.name)) {
                    return;
                }
            }
        }
        throw new java.lang.IllegalStateException("Must declare uses-feature android.software.companion_device_setup in manifest to use this API");
    }

    @android.annotation.NonNull
    public static java.util.Map<java.lang.String, java.util.List<android.content.ComponentName>> getCompanionServicesForUser(@android.annotation.NonNull android.content.Context context, int i) {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        java.util.List<android.content.pm.ResolveInfo> queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(COMPANION_SERVICE_INTENT, android.content.pm.PackageManager.ResolveInfoFlags.of(0L), i);
        java.util.HashMap hashMap = new java.util.HashMap(queryIntentServicesAsUser.size());
        for (android.content.pm.ResolveInfo resolveInfo : queryIntentServicesAsUser) {
            android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            if (!"android.permission.BIND_COMPANION_DEVICE_SERVICE".equals(resolveInfo.serviceInfo.permission)) {
                android.util.Slog.w(TAG, "CompanionDeviceService " + serviceInfo.getComponentName().flattenToShortString() + " must require android.permission.BIND_COMPANION_DEVICE_SERVICE");
            } else {
                java.util.ArrayList arrayList = (java.util.ArrayList) hashMap.computeIfAbsent(serviceInfo.packageName, new java.util.function.Function() { // from class: com.android.server.companion.utils.PackageUtils$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        java.util.List lambda$getCompanionServicesForUser$1;
                        lambda$getCompanionServicesForUser$1 = com.android.server.companion.utils.PackageUtils.lambda$getCompanionServicesForUser$1((java.lang.String) obj);
                        return lambda$getCompanionServicesForUser$1;
                    }
                });
                android.content.ComponentName componentName = serviceInfo.getComponentName();
                if (isPrimaryCompanionDeviceService(packageManager, componentName, i)) {
                    arrayList.add(0, componentName);
                } else {
                    arrayList.add(componentName);
                }
            }
        }
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.List lambda$getCompanionServicesForUser$1(java.lang.String str) {
        return new java.util.ArrayList(1);
    }

    private static boolean isPrimaryCompanionDeviceService(@android.annotation.NonNull android.content.pm.PackageManager packageManager, @android.annotation.NonNull android.content.ComponentName componentName, int i) {
        try {
            return packageManager.getPropertyAsUser(PROPERTY_PRIMARY_TAG, componentName.getPackageName(), componentName.getClassName(), i).getBoolean();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isPackageAllowlisted(android.content.Context context, android.content.pm.PackageManagerInternal packageManagerInternal, @android.annotation.NonNull java.lang.String str) {
        java.lang.String[] stringArray = context.getResources().getStringArray(android.R.array.config_companionDevicePackages);
        boolean z = false;
        if (!com.android.internal.util.ArrayUtils.contains(stringArray, str)) {
            android.util.Slog.d(TAG, str + " is not allowlisted.");
            return false;
        }
        java.lang.String[] stringArray2 = context.getResources().getStringArray(android.R.array.config_companionDeviceCerts);
        java.util.HashSet hashSet = new java.util.HashSet();
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(str)) {
                hashSet.add(stringArray2[i].replaceAll(":", ""));
            }
        }
        java.lang.String[] computeSignaturesSha256Digests = android.util.PackageUtils.computeSignaturesSha256Digests(packageManagerInternal.getPackage(str).getSigningDetails().getSignatures());
        int length = computeSignaturesSha256Digests.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            if (!hashSet.contains(computeSignaturesSha256Digests[i2])) {
                i2++;
            } else {
                z = true;
                break;
            }
        }
        if (!z) {
            android.util.Slog.w(TAG, "Certificate mismatch for allowlisted package " + str);
        }
        return z;
    }

    public static boolean isRestrictedSettingsAllowed(android.content.Context context, java.lang.String str, int i) {
        return ((android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class)).noteOpNoThrow(119, i, str, (java.lang.String) null, (java.lang.String) null) == 0;
    }
}
