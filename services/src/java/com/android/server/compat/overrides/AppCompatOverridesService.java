package com.android.server.compat.overrides;

/* loaded from: classes.dex */
public final class AppCompatOverridesService {
    private static final java.util.List<java.lang.String> SUPPORTED_NAMESPACES = java.util.Arrays.asList("app_compat_overrides");
    private static final java.lang.String TAG = "AppCompatOverridesService";
    private final android.content.Context mContext;
    private final java.util.List<com.android.server.compat.overrides.AppCompatOverridesService.DeviceConfigListener> mDeviceConfigListeners;
    private final com.android.server.compat.overrides.AppCompatOverridesParser mOverridesParser;
    private final android.content.pm.PackageManager mPackageManager;
    private final com.android.server.compat.overrides.AppCompatOverridesService.PackageReceiver mPackageReceiver;
    private final com.android.internal.compat.IPlatformCompat mPlatformCompat;
    private final java.util.List<java.lang.String> mSupportedNamespaces;

    private AppCompatOverridesService(android.content.Context context) {
        this(context, com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService("platform_compat")), SUPPORTED_NAMESPACES);
    }

    @com.android.internal.annotations.VisibleForTesting
    AppCompatOverridesService(android.content.Context context, com.android.internal.compat.IPlatformCompat iPlatformCompat, java.util.List<java.lang.String> list) {
        this.mContext = context;
        this.mPackageManager = this.mContext.getPackageManager();
        this.mPlatformCompat = iPlatformCompat;
        this.mSupportedNamespaces = list;
        this.mOverridesParser = new com.android.server.compat.overrides.AppCompatOverridesParser(this.mPackageManager);
        byte b = 0;
        this.mPackageReceiver = new com.android.server.compat.overrides.AppCompatOverridesService.PackageReceiver(this.mContext);
        this.mDeviceConfigListeners = new java.util.ArrayList();
        java.util.Iterator<java.lang.String> it = this.mSupportedNamespaces.iterator();
        while (it.hasNext()) {
            this.mDeviceConfigListeners.add(new com.android.server.compat.overrides.AppCompatOverridesService.DeviceConfigListener(this.mContext, it.next()));
        }
    }

    public void finalize() {
        unregisterDeviceConfigListeners();
        unregisterPackageReceiver();
    }

    @com.android.internal.annotations.VisibleForTesting
    void registerDeviceConfigListeners() {
        java.util.Iterator<com.android.server.compat.overrides.AppCompatOverridesService.DeviceConfigListener> it = this.mDeviceConfigListeners.iterator();
        while (it.hasNext()) {
            it.next().register();
        }
    }

    private void unregisterDeviceConfigListeners() {
        java.util.Iterator<com.android.server.compat.overrides.AppCompatOverridesService.DeviceConfigListener> it = this.mDeviceConfigListeners.iterator();
        while (it.hasNext()) {
            it.next().unregister();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void registerPackageReceiver() {
        this.mPackageReceiver.register();
    }

    private void unregisterPackageReceiver() {
        this.mPackageReceiver.unregister();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyAllOverrides(java.lang.String str, java.util.Set<java.lang.Long> set, java.util.Map<java.lang.String, java.util.Set<java.lang.Long>> map) {
        applyOverrides(android.provider.DeviceConfig.getProperties(str, new java.lang.String[0]), set, map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyOverrides(android.provider.DeviceConfig.Properties properties, java.util.Set<java.lang.Long> set, java.util.Map<java.lang.String, java.util.Set<java.lang.Long>> map) {
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet(properties.getKeyset());
        arraySet.remove("owned_change_ids");
        arraySet.remove("remove_overrides");
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        for (java.lang.String str : arraySet) {
            java.util.Set<java.lang.Long> orDefault = map.getOrDefault(str, java.util.Collections.emptySet());
            java.util.Map<java.lang.Long, android.app.compat.PackageOverride> emptyMap = java.util.Collections.emptyMap();
            java.lang.Long versionCodeOrNull = getVersionCodeOrNull(str);
            if (versionCodeOrNull != null) {
                emptyMap = this.mOverridesParser.parsePackageOverrides(properties.getString(str, ""), str, versionCodeOrNull.longValue(), orDefault);
            }
            if (!emptyMap.isEmpty()) {
                arrayMap.put(str, new com.android.internal.compat.CompatibilityOverrideConfig(emptyMap));
            }
            android.util.ArraySet arraySet2 = new android.util.ArraySet();
            for (java.lang.Long l : set) {
                if (!emptyMap.containsKey(l) && !orDefault.contains(l)) {
                    arraySet2.add(l);
                }
            }
            if (!arraySet2.isEmpty()) {
                arrayMap2.put(str, new com.android.internal.compat.CompatibilityOverridesToRemoveConfig(arraySet2));
            }
        }
        putAllPackageOverrides(arrayMap);
        removeAllPackageOverrides(arrayMap2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAllPackageOverrides(java.lang.String str) {
        java.lang.Long versionCodeOrNull = getVersionCodeOrNull(str);
        if (versionCodeOrNull == null) {
            return;
        }
        for (java.lang.String str2 : this.mSupportedNamespaces) {
            putPackageOverrides(str, this.mOverridesParser.parsePackageOverrides(android.provider.DeviceConfig.getString(str2, str, ""), str, versionCodeOrNull.longValue(), getOverridesToRemove(str2, getOwnedChangeIds(str2)).getOrDefault(str, java.util.Collections.emptySet())));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeAllPackageOverrides(java.lang.String str) {
        for (java.lang.String str2 : this.mSupportedNamespaces) {
            if (!android.provider.DeviceConfig.getString(str2, str, "").isEmpty()) {
                removePackageOverrides(str, getOwnedChangeIds(str2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeOverrides(java.util.Map<java.lang.String, java.util.Set<java.lang.Long>> map) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (java.util.Map.Entry<java.lang.String, java.util.Set<java.lang.Long>> entry : map.entrySet()) {
            arrayMap.put(entry.getKey(), new com.android.internal.compat.CompatibilityOverridesToRemoveConfig(entry.getValue()));
        }
        removeAllPackageOverrides(arrayMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.Map<java.lang.String, java.util.Set<java.lang.Long>> getOverridesToRemove(java.lang.String str, java.util.Set<java.lang.Long> set) {
        return this.mOverridesParser.parseRemoveOverrides(android.provider.DeviceConfig.getString(str, "remove_overrides", ""), set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.Set<java.lang.Long> getOwnedChangeIds(java.lang.String str) {
        return com.android.server.compat.overrides.AppCompatOverridesParser.parseOwnedChangeIds(android.provider.DeviceConfig.getString(str, "owned_change_ids", ""));
    }

    private void putAllPackageOverrides(java.util.Map<java.lang.String, com.android.internal.compat.CompatibilityOverrideConfig> map) {
        if (map.isEmpty()) {
            return;
        }
        try {
            this.mPlatformCompat.putAllOverridesOnReleaseBuilds(new com.android.internal.compat.CompatibilityOverridesByPackageConfig(map));
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to call IPlatformCompat#putAllOverridesOnReleaseBuilds", e);
        }
    }

    private void putPackageOverrides(java.lang.String str, java.util.Map<java.lang.Long, android.app.compat.PackageOverride> map) {
        if (map.isEmpty()) {
            return;
        }
        try {
            this.mPlatformCompat.putOverridesOnReleaseBuilds(new com.android.internal.compat.CompatibilityOverrideConfig(map), str);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to call IPlatformCompat#putOverridesOnReleaseBuilds", e);
        }
    }

    private void removeAllPackageOverrides(java.util.Map<java.lang.String, com.android.internal.compat.CompatibilityOverridesToRemoveConfig> map) {
        if (map.isEmpty()) {
            return;
        }
        try {
            this.mPlatformCompat.removeAllOverridesOnReleaseBuilds(new com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig(map));
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to call IPlatformCompat#removeAllOverridesOnReleaseBuilds", e);
        }
    }

    private void removePackageOverrides(java.lang.String str, java.util.Set<java.lang.Long> set) {
        if (set.isEmpty()) {
            return;
        }
        try {
            this.mPlatformCompat.removeOverridesOnReleaseBuilds(new com.android.internal.compat.CompatibilityOverridesToRemoveConfig(set), str);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to call IPlatformCompat#removeOverridesOnReleaseBuilds", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInstalledForAnyUser(java.lang.String str) {
        return getVersionCodeOrNull(str) != null;
    }

    @android.annotation.Nullable
    private java.lang.Long getVersionCodeOrNull(java.lang.String str) {
        try {
            return java.lang.Long.valueOf(this.mPackageManager.getApplicationInfo(str, 4194304).longVersionCode);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static final class Lifecycle extends com.android.server.SystemService {
        private com.android.server.compat.overrides.AppCompatOverridesService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mService = new com.android.server.compat.overrides.AppCompatOverridesService(getContext());
            this.mService.registerDeviceConfigListeners();
            this.mService.registerPackageReceiver();
        }
    }

    private final class DeviceConfigListener implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        private final android.content.Context mContext;
        private final java.lang.String mNamespace;

        private DeviceConfigListener(android.content.Context context, java.lang.String str) {
            this.mContext = context;
            this.mNamespace = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void register() {
            android.provider.DeviceConfig.addOnPropertiesChangedListener(this.mNamespace, this.mContext.getMainExecutor(), this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unregister() {
            android.provider.DeviceConfig.removeOnPropertiesChangedListener(this);
        }

        public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            boolean contains = properties.getKeyset().contains("remove_overrides");
            boolean contains2 = properties.getKeyset().contains("owned_change_ids");
            java.util.Set ownedChangeIds = com.android.server.compat.overrides.AppCompatOverridesService.getOwnedChangeIds(this.mNamespace);
            java.util.Map overridesToRemove = com.android.server.compat.overrides.AppCompatOverridesService.this.getOverridesToRemove(this.mNamespace, ownedChangeIds);
            if (contains || contains2) {
                com.android.server.compat.overrides.AppCompatOverridesService.this.removeOverrides(overridesToRemove);
            }
            if (contains) {
                com.android.server.compat.overrides.AppCompatOverridesService.this.applyAllOverrides(this.mNamespace, ownedChangeIds, overridesToRemove);
            } else {
                com.android.server.compat.overrides.AppCompatOverridesService.this.applyOverrides(properties, ownedChangeIds, overridesToRemove);
            }
        }
    }

    private final class PackageReceiver extends android.content.BroadcastReceiver {
        private final android.content.Context mContext;
        private final android.content.IntentFilter mIntentFilter;

        private PackageReceiver(android.content.Context context) {
            this.mContext = context;
            this.mIntentFilter = new android.content.IntentFilter();
            this.mIntentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            this.mIntentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            this.mIntentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            this.mIntentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void register() {
            this.mContext.registerReceiverForAllUsers(this, this.mIntentFilter, null, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unregister() {
            this.mContext.unregisterReceiver(this);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.Intent intent) {
            char c;
            android.net.Uri data = intent.getData();
            if (data == null) {
                android.util.Slog.w(com.android.server.compat.overrides.AppCompatOverridesService.TAG, "Failed to get package name in package receiver");
            }
            java.lang.String schemeSpecificPart = data.getSchemeSpecificPart();
            java.lang.String action = intent.getAction();
            if (action == null) {
                android.util.Slog.w(com.android.server.compat.overrides.AppCompatOverridesService.TAG, "Failed to get action in package receiver");
                return;
            }
            switch (action.hashCode()) {
                case 172491798:
                    if (action.equals("android.intent.action.PACKAGE_CHANGED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 525384130:
                    if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1544582882:
                    if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                        c = 0;
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
                case 1:
                    com.android.server.compat.overrides.AppCompatOverridesService.this.addAllPackageOverrides(schemeSpecificPart);
                    break;
                case 2:
                    if (!com.android.server.compat.overrides.AppCompatOverridesService.this.isInstalledForAnyUser(schemeSpecificPart)) {
                        com.android.server.compat.overrides.AppCompatOverridesService.this.removeAllPackageOverrides(schemeSpecificPart);
                        break;
                    }
                    break;
                default:
                    android.util.Slog.w(com.android.server.compat.overrides.AppCompatOverridesService.TAG, "Unsupported action in package receiver: " + action);
                    break;
            }
        }
    }
}
