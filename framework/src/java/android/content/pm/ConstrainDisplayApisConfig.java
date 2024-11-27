package android.content.pm;

/* loaded from: classes.dex */
public final class ConstrainDisplayApisConfig {
    private static final java.lang.String FLAG_ALWAYS_CONSTRAIN_DISPLAY_APIS = "always_constrain_display_apis";
    private static final java.lang.String FLAG_NEVER_CONSTRAIN_DISPLAY_APIS = "never_constrain_display_apis";
    private static final java.lang.String FLAG_NEVER_CONSTRAIN_DISPLAY_APIS_ALL_PACKAGES = "never_constrain_display_apis_all_packages";
    private static final java.lang.String TAG = android.content.pm.ConstrainDisplayApisConfig.class.getSimpleName();
    private android.util.ArrayMap<java.lang.String, android.util.Pair<java.lang.Long, java.lang.Long>> mAlwaysConstrainConfigMap;
    private android.util.ArrayMap<java.lang.String, android.util.Pair<java.lang.Long, java.lang.Long>> mNeverConstrainConfigMap;
    private boolean mNeverConstrainDisplayApisAllPackages;

    public ConstrainDisplayApisConfig() {
        updateCache();
        android.provider.DeviceConfig.addOnPropertiesChangedListener("constrain_display_apis", com.android.internal.os.BackgroundThread.getExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: android.content.pm.ConstrainDisplayApisConfig$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                android.content.pm.ConstrainDisplayApisConfig.this.lambda$new$0(properties);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.provider.DeviceConfig.Properties properties) {
        updateCache();
    }

    public boolean getNeverConstrainDisplayApis(android.content.pm.ApplicationInfo applicationInfo) {
        if (this.mNeverConstrainDisplayApisAllPackages) {
            return true;
        }
        return flagHasMatchingPackageEntry(this.mNeverConstrainConfigMap, applicationInfo);
    }

    public boolean getAlwaysConstrainDisplayApis(android.content.pm.ApplicationInfo applicationInfo) {
        return flagHasMatchingPackageEntry(this.mAlwaysConstrainConfigMap, applicationInfo);
    }

    private void updateCache() {
        this.mNeverConstrainDisplayApisAllPackages = android.provider.DeviceConfig.getBoolean("constrain_display_apis", FLAG_NEVER_CONSTRAIN_DISPLAY_APIS_ALL_PACKAGES, false);
        this.mNeverConstrainConfigMap = buildConfigMap(android.provider.DeviceConfig.getString("constrain_display_apis", FLAG_NEVER_CONSTRAIN_DISPLAY_APIS, ""));
        this.mAlwaysConstrainConfigMap = buildConfigMap(android.provider.DeviceConfig.getString("constrain_display_apis", FLAG_ALWAYS_CONSTRAIN_DISPLAY_APIS, ""));
    }

    private static android.util.ArrayMap<java.lang.String, android.util.Pair<java.lang.Long, java.lang.Long>> buildConfigMap(java.lang.String str) {
        android.util.ArrayMap<java.lang.String, android.util.Pair<java.lang.Long, java.lang.Long>> arrayMap = new android.util.ArrayMap<>();
        if (str.isEmpty()) {
            return arrayMap;
        }
        for (java.lang.String str2 : str.split(",")) {
            java.util.List asList = java.util.Arrays.asList(str2.split(":", 3));
            if (asList.size() != 3) {
                android.util.Slog.w(TAG, "Invalid package entry in flag 'never/always_constrain_display_apis': " + str2);
            } else {
                java.lang.String str3 = (java.lang.String) asList.get(0);
                java.lang.String str4 = (java.lang.String) asList.get(1);
                java.lang.String str5 = (java.lang.String) asList.get(2);
                try {
                    arrayMap.put(str3, new android.util.Pair<>(java.lang.Long.valueOf(str4.isEmpty() ? Long.MIN_VALUE : java.lang.Long.parseLong(str4)), java.lang.Long.valueOf(str5.isEmpty() ? Long.MAX_VALUE : java.lang.Long.parseLong(str5))));
                } catch (java.lang.NumberFormatException e) {
                    android.util.Slog.w(TAG, "Invalid APK version code in package entry: " + str2);
                }
            }
        }
        return arrayMap;
    }

    private static boolean flagHasMatchingPackageEntry(java.util.Map<java.lang.String, android.util.Pair<java.lang.Long, java.lang.Long>> map, android.content.pm.ApplicationInfo applicationInfo) {
        if (!map.isEmpty() && map.containsKey(applicationInfo.packageName)) {
            return matchesApplicationInfo(map.get(applicationInfo.packageName), applicationInfo);
        }
        return false;
    }

    private static boolean matchesApplicationInfo(android.util.Pair<java.lang.Long, java.lang.Long> pair, android.content.pm.ApplicationInfo applicationInfo) {
        return applicationInfo.longVersionCode >= pair.first.longValue() && applicationInfo.longVersionCode <= pair.second.longValue();
    }
}
