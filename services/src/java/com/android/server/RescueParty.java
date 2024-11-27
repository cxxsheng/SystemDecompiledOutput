package com.android.server;

/* loaded from: classes.dex */
public class RescueParty {

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_FACTORY_RESET_THROTTLE_DURATION_MIN = 10;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_OBSERVING_DURATION_MS = java.util.concurrent.TimeUnit.DAYS.toMillis(2);

    @com.android.internal.annotations.VisibleForTesting
    static final int DEVICE_CONFIG_RESET_MODE = 4;

    @com.android.internal.annotations.VisibleForTesting
    static final int LEVEL_FACTORY_RESET = 5;

    @com.android.internal.annotations.VisibleForTesting
    static final int LEVEL_NONE = 0;

    @com.android.internal.annotations.VisibleForTesting
    static final int LEVEL_RESET_SETTINGS_TRUSTED_DEFAULTS = 3;

    @com.android.internal.annotations.VisibleForTesting
    static final int LEVEL_RESET_SETTINGS_UNTRUSTED_CHANGES = 2;

    @com.android.internal.annotations.VisibleForTesting
    static final int LEVEL_RESET_SETTINGS_UNTRUSTED_DEFAULTS = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final int LEVEL_WARM_REBOOT = 4;
    private static final java.lang.String NAME = "rescue-party-observer";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String NAMESPACE_CONFIGURATION = "configuration";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String NAMESPACE_TO_PACKAGE_MAPPING_FLAG = "namespace_to_package_mapping";
    private static final int PERSISTENT_MASK = 9;
    private static final java.lang.String PROP_DEVICE_CONFIG_DISABLE_FLAG = "persist.device_config.configuration.disable_rescue_party";
    private static final java.lang.String PROP_DISABLE_FACTORY_RESET_FLAG = "persist.device_config.configuration.disable_rescue_party_factory_reset";
    private static final java.lang.String PROP_DISABLE_RESCUE = "persist.sys.disable_rescue";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String PROP_ENABLE_RESCUE = "persist.sys.enable_rescue";
    private static final java.lang.String PROP_THROTTLE_DURATION_MIN_FLAG = "persist.device_config.configuration.rescue_party_throttle_duration_min";
    private static final java.lang.String PROP_VIRTUAL_DEVICE = "ro.hardware.virtual_device";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String TAG = "RescueParty";

    public static void registerHealthObserver(android.content.Context context) {
        com.android.server.PackageWatchdog.getInstance(context).registerHealthObserver(com.android.server.RescueParty.RescuePartyObserver.getInstance(context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isDisabled() {
        if (android.os.SystemProperties.getBoolean(PROP_ENABLE_RESCUE, false)) {
            return false;
        }
        if (android.os.SystemProperties.getBoolean(PROP_DEVICE_CONFIG_DISABLE_FLAG, false)) {
            android.util.Slog.v(TAG, "Disabled because of DeviceConfig flag");
            return true;
        }
        if (android.os.Build.TYPE.equals("eng")) {
            android.util.Slog.v(TAG, "Disabled because of eng build");
            return true;
        }
        if (android.os.Build.TYPE.equals("userdebug") && isUsbActive()) {
            android.util.Slog.v(TAG, "Disabled because of active USB connection");
            return true;
        }
        if (!android.os.SystemProperties.getBoolean(PROP_DISABLE_RESCUE, false)) {
            return false;
        }
        android.util.Slog.v(TAG, "Disabled because of manual property");
        return true;
    }

    public static boolean isAttemptingFactoryReset() {
        return isFactoryResetPropertySet() || isRebootPropertySet();
    }

    static boolean isFactoryResetPropertySet() {
        return ((java.lang.Boolean) android.sysprop.CrashRecoveryProperties.attemptingFactoryReset().orElse(false)).booleanValue();
    }

    static boolean isRebootPropertySet() {
        return ((java.lang.Boolean) android.sysprop.CrashRecoveryProperties.attemptingReboot().orElse(false)).booleanValue();
    }

    protected static long getLastFactoryResetTimeMs() {
        return ((java.lang.Long) android.sysprop.CrashRecoveryProperties.lastFactoryResetTimeMs().orElse(0L)).longValue();
    }

    protected static int getMaxRescueLevelAttempted() {
        return ((java.lang.Integer) android.sysprop.CrashRecoveryProperties.maxRescueLevelAttempted().orElse(0)).intValue();
    }

    protected static void setFactoryResetProperty(boolean z) {
        android.sysprop.CrashRecoveryProperties.attemptingFactoryReset(java.lang.Boolean.valueOf(z));
    }

    protected static void setRebootProperty(boolean z) {
        android.sysprop.CrashRecoveryProperties.attemptingReboot(java.lang.Boolean.valueOf(z));
    }

    protected static void setLastFactoryResetTimeMs(long j) {
        android.sysprop.CrashRecoveryProperties.lastFactoryResetTimeMs(java.lang.Long.valueOf(j));
    }

    protected static void setMaxRescueLevelAttempted(int i) {
        android.sysprop.CrashRecoveryProperties.maxRescueLevelAttempted(java.lang.Integer.valueOf(i));
    }

    public static void onSettingsProviderPublished(android.content.Context context) {
        handleNativeRescuePartyResets();
        android.provider.DeviceConfig.setMonitorCallback(context.getContentResolver(), java.util.concurrent.Executors.newSingleThreadExecutor(), new com.android.server.RescueParty.RescuePartyMonitorCallback(context));
    }

    public static void resetDeviceConfigForPackages(java.util.List<java.lang.String> list) {
        if (list == null) {
            return;
        }
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet();
        java.util.Iterator<java.lang.String> it = list.iterator();
        com.android.server.RescueParty.RescuePartyObserver instanceIfCreated = com.android.server.RescueParty.RescuePartyObserver.getInstanceIfCreated();
        if (instanceIfCreated != null) {
            while (it.hasNext()) {
                java.util.Set affectedNamespaceSet = instanceIfCreated.getAffectedNamespaceSet(it.next());
                if (affectedNamespaceSet != null) {
                    arraySet.addAll(affectedNamespaceSet);
                }
            }
        }
        java.util.Set<java.lang.String> presetNamespacesForPackages = getPresetNamespacesForPackages(list);
        if (presetNamespacesForPackages != null) {
            arraySet.addAll(presetNamespacesForPackages);
        }
        for (java.lang.String str : arraySet) {
            try {
                if (!android.provider.DeviceConfig.setProperties(new android.provider.DeviceConfig.Properties.Builder(str).build())) {
                    com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(6, "Failed to clear properties under " + str + ". Running `device_config get_sync_disabled_for_tests` will confirm if config-bulk-update is enabled.");
                }
            } catch (android.provider.DeviceConfig.BadConfigException e) {
                com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "namespace " + str + " is already banned, skip reset.");
            }
        }
    }

    private static java.util.Set<java.lang.String> getPresetNamespacesForPackages(java.util.List<java.lang.String> list) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        try {
            try {
                java.lang.String[] split = android.provider.DeviceConfig.getString(NAMESPACE_CONFIGURATION, NAMESPACE_TO_PACKAGE_MAPPING_FLAG, "").split(",");
                for (int i = 0; i < split.length; i++) {
                    if (!android.text.TextUtils.isEmpty(split[i])) {
                        java.lang.String[] split2 = split[i].split(":");
                        if (split2.length != 2) {
                            throw new java.lang.RuntimeException("Invalid mapping entry: " + split[i]);
                        }
                        java.lang.String str = split2[0];
                        if (list.contains(split2[1])) {
                            arraySet.add(str);
                        }
                    }
                }
                return arraySet;
            } catch (java.lang.Exception e) {
                arraySet.clear();
                android.util.Slog.e(TAG, "Failed to read preset package to namespaces mapping.", e);
                return arraySet;
            }
        } catch (java.lang.Throwable th) {
            return arraySet;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static long getElapsedRealtime() {
        return android.os.SystemClock.elapsedRealtime();
    }

    private static class RescuePartyMonitorCallback implements android.provider.DeviceConfig.MonitorCallback {
        android.content.Context mContext;

        RescuePartyMonitorCallback(android.content.Context context) {
            this.mContext = context;
        }

        public void onNamespaceUpdate(@android.annotation.NonNull java.lang.String str) {
            com.android.server.RescueParty.startObservingPackages(this.mContext, str);
        }

        public void onDeviceConfigAccess(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
            com.android.server.RescueParty.RescuePartyObserver.getInstance(this.mContext).recordDeviceConfigAccess(str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void startObservingPackages(android.content.Context context, @android.annotation.NonNull java.lang.String str) {
        com.android.server.RescueParty.RescuePartyObserver rescuePartyObserver = com.android.server.RescueParty.RescuePartyObserver.getInstance(context);
        java.util.Set callingPackagesSet = rescuePartyObserver.getCallingPackagesSet(str);
        if (callingPackagesSet == null) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.addAll(callingPackagesSet);
        android.util.Slog.i(TAG, "Starting to observe: " + arrayList + ", updated namespace: " + str);
        com.android.server.PackageWatchdog.getInstance(context).startObservingHealth(rescuePartyObserver, arrayList, DEFAULT_OBSERVING_DURATION_MS);
    }

    private static void handleNativeRescuePartyResets() {
        if (com.android.server.am.SettingsToPropertiesMapper.isNativeFlagsResetPerformed()) {
            java.lang.String[] resetNativeCategories = com.android.server.am.SettingsToPropertiesMapper.getResetNativeCategories();
            for (int i = 0; i < resetNativeCategories.length; i++) {
                if (!NAMESPACE_CONFIGURATION.equals(resetNativeCategories[i])) {
                    android.provider.DeviceConfig.resetToDefaults(4, resetNativeCategories[i]);
                }
            }
        }
    }

    private static int getMaxRescueLevel(boolean z) {
        if (!z || android.os.SystemProperties.getBoolean(PROP_DISABLE_FACTORY_RESET_FLAG, false)) {
            return 3;
        }
        return 5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getRescueLevel(int i, boolean z) {
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            return 2;
        }
        if (i == 3) {
            return 3;
        }
        if (i == 4) {
            return java.lang.Math.min(getMaxRescueLevel(z), 4);
        }
        if (i >= 5) {
            return java.lang.Math.min(getMaxRescueLevel(z), 5);
        }
        android.util.Slog.w(TAG, "Expected positive mitigation count, was " + i);
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void executeRescueLevel(android.content.Context context, @android.annotation.Nullable java.lang.String str, int i) {
        android.util.Slog.w(TAG, "Attempting rescue level " + levelToString(i));
        try {
            executeRescueLevelInternal(context, i, str);
            com.android.server.EventLogTags.writeRescueSuccess(i);
            java.lang.String str2 = "Finished rescue level " + levelToString(i);
            if (!android.text.TextUtils.isEmpty(str)) {
                str2 = str2 + " for package " + str;
            }
            com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(3, str2);
        } catch (java.lang.Throwable th) {
            logRescueException(i, str, th);
        }
    }

    private static void executeRescueLevelInternal(final android.content.Context context, final int i, @android.annotation.Nullable final java.lang.String str) throws java.lang.Exception {
        if (i <= 3) {
            return;
        }
        com.android.server.crashrecovery.proto.CrashRecoveryStatsLog.write(122, i, levelToString(i));
        java.lang.Exception e = null;
        switch (i) {
            case 1:
                try {
                    resetAllSettingsIfNecessary(context, 2, i);
                } catch (java.lang.Exception e2) {
                    e = e2;
                }
                try {
                    resetDeviceConfig(context, true, str);
                    break;
                } catch (java.lang.Exception e3) {
                    e = e3;
                    break;
                }
            case 2:
                try {
                    resetAllSettingsIfNecessary(context, 3, i);
                } catch (java.lang.Exception e4) {
                    e = e4;
                }
                try {
                    resetDeviceConfig(context, true, str);
                    break;
                } catch (java.lang.Exception e5) {
                    e = e5;
                    break;
                }
            case 3:
                try {
                    resetAllSettingsIfNecessary(context, 4, i);
                } catch (java.lang.Exception e6) {
                    e = e6;
                }
                try {
                    resetDeviceConfig(context, false, str);
                    break;
                } catch (java.lang.Exception e7) {
                    e = e7;
                    break;
                }
            case 4:
                setRebootProperty(true);
                new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.RescueParty$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.RescueParty.lambda$executeRescueLevelInternal$0(context, i, str);
                    }
                }).start();
                break;
            case 5:
                if (!isRebootPropertySet()) {
                    setFactoryResetProperty(true);
                    setLastFactoryResetTimeMs(java.lang.System.currentTimeMillis());
                    new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.RescueParty.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                android.os.RecoverySystem.rebootPromptAndWipeUserData(context, com.android.server.RescueParty.TAG);
                            } catch (java.lang.Throwable th) {
                                com.android.server.RescueParty.logRescueException(i, str, th);
                            }
                        }
                    }).start();
                    break;
                }
                break;
        }
        if (e != null) {
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$executeRescueLevelInternal$0(android.content.Context context, int i, java.lang.String str) {
        try {
            android.os.PowerManager powerManager = (android.os.PowerManager) context.getSystemService(android.os.PowerManager.class);
            if (powerManager != null) {
                powerManager.reboot(TAG);
            }
        } catch (java.lang.Throwable th) {
            logRescueException(i, str, th);
        }
    }

    private static java.lang.String getCompleteMessage(java.lang.Throwable th) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(th.getMessage());
        while (true) {
            th = th.getCause();
            if (th != null) {
                sb.append(": ");
                sb.append(th.getMessage());
            } else {
                return sb.toString();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logRescueException(int i, @android.annotation.Nullable java.lang.String str, java.lang.Throwable th) {
        java.lang.String completeMessage = getCompleteMessage(th);
        com.android.server.EventLogTags.writeRescueFailure(i, completeMessage);
        java.lang.String str2 = "Failed rescue level " + levelToString(i);
        if (!android.text.TextUtils.isEmpty(str)) {
            str2 = str2 + " for package " + str;
        }
        com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(6, str2 + ": " + completeMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int mapRescueLevelToUserImpact(int i) {
        switch (i) {
            case 1:
            case 2:
                return 10;
            case 3:
            case 4:
                return 50;
            case 5:
                return 100;
            default:
                return 0;
        }
    }

    private static void resetAllSettingsIfNecessary(android.content.Context context, int i, int i2) throws java.lang.Exception {
        java.lang.RuntimeException runtimeException;
        if (getMaxRescueLevelAttempted() >= i2) {
            return;
        }
        setMaxRescueLevelAttempted(i2);
        android.content.ContentResolver contentResolver = context.getContentResolver();
        try {
            android.provider.Settings.Global.resetToDefaultsAsUser(contentResolver, null, i, android.os.UserHandle.SYSTEM.getIdentifier());
            runtimeException = null;
        } catch (java.lang.Exception e) {
            runtimeException = new java.lang.RuntimeException("Failed to reset global settings", e);
        }
        for (int i3 : getAllUserIds()) {
            try {
                android.provider.Settings.Secure.resetToDefaultsAsUser(contentResolver, null, i, i3);
            } catch (java.lang.Exception e2) {
                runtimeException = new java.lang.RuntimeException("Failed to reset secure settings for " + i3, e2);
            }
        }
        if (runtimeException != null) {
            throw runtimeException;
        }
    }

    private static void resetDeviceConfig(android.content.Context context, boolean z, @android.annotation.Nullable java.lang.String str) throws java.lang.Exception {
        context.getContentResolver();
        try {
            if (!z || str == null) {
                resetAllAffectedNamespaces(context);
            } else {
                performScopedReset(context, str);
            }
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException("Failed to reset config settings", e);
        }
    }

    private static void resetAllAffectedNamespaces(android.content.Context context) {
        java.util.Set<java.lang.String> allAffectedNamespaceSet = com.android.server.RescueParty.RescuePartyObserver.getInstance(context).getAllAffectedNamespaceSet();
        android.util.Slog.w(TAG, "Performing reset for all affected namespaces: " + java.util.Arrays.toString(allAffectedNamespaceSet.toArray()));
        for (java.lang.String str : allAffectedNamespaceSet) {
            if (!NAMESPACE_CONFIGURATION.equals(str)) {
                android.provider.DeviceConfig.resetToDefaults(4, str);
            }
        }
    }

    private static void performScopedReset(android.content.Context context, @android.annotation.NonNull java.lang.String str) {
        java.util.Set<java.lang.String> affectedNamespaceSet = com.android.server.RescueParty.RescuePartyObserver.getInstance(context).getAffectedNamespaceSet(str);
        if (affectedNamespaceSet != null) {
            android.util.Slog.w(TAG, "Performing scoped reset for package: " + str + ", affected namespaces: " + java.util.Arrays.toString(affectedNamespaceSet.toArray()));
            for (java.lang.String str2 : affectedNamespaceSet) {
                if (!NAMESPACE_CONFIGURATION.equals(str2)) {
                    android.provider.DeviceConfig.resetToDefaults(4, str2);
                }
            }
        }
    }

    public static class RescuePartyObserver implements com.android.server.PackageWatchdog.PackageHealthObserver {

        @com.android.internal.annotations.GuardedBy({"RescuePartyObserver.class"})
        static com.android.server.RescueParty.RescuePartyObserver sRescuePartyObserver;
        private final android.content.Context mContext;
        private final java.util.Map<java.lang.String, java.util.Set<java.lang.String>> mCallingPackageNamespaceSetMap = new java.util.HashMap();
        private final java.util.Map<java.lang.String, java.util.Set<java.lang.String>> mNamespaceCallingPackageSetMap = new java.util.HashMap();

        private RescuePartyObserver(android.content.Context context) {
            this.mContext = context;
        }

        public static com.android.server.RescueParty.RescuePartyObserver getInstance(android.content.Context context) {
            com.android.server.RescueParty.RescuePartyObserver rescuePartyObserver;
            synchronized (com.android.server.RescueParty.RescuePartyObserver.class) {
                try {
                    if (sRescuePartyObserver == null) {
                        sRescuePartyObserver = new com.android.server.RescueParty.RescuePartyObserver(context);
                    }
                    rescuePartyObserver = sRescuePartyObserver;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return rescuePartyObserver;
        }

        @android.annotation.Nullable
        public static com.android.server.RescueParty.RescuePartyObserver getInstanceIfCreated() {
            com.android.server.RescueParty.RescuePartyObserver rescuePartyObserver;
            synchronized (com.android.server.RescueParty.RescuePartyObserver.class) {
                rescuePartyObserver = sRescuePartyObserver;
            }
            return rescuePartyObserver;
        }

        @com.android.internal.annotations.VisibleForTesting
        static void reset() {
            synchronized (com.android.server.RescueParty.RescuePartyObserver.class) {
                sRescuePartyObserver = null;
            }
        }

        @Override // com.android.server.PackageWatchdog.PackageHealthObserver
        public int onHealthCheckFailed(@android.annotation.Nullable android.content.pm.VersionedPackage versionedPackage, int i, int i2) {
            if (com.android.server.RescueParty.isDisabled()) {
                return 0;
            }
            if (i == 3 || i == 4) {
                return com.android.server.RescueParty.mapRescueLevelToUserImpact(com.android.server.RescueParty.getRescueLevel(i2, mayPerformReboot(versionedPackage)));
            }
            return 0;
        }

        @Override // com.android.server.PackageWatchdog.PackageHealthObserver
        public boolean execute(@android.annotation.Nullable android.content.pm.VersionedPackage versionedPackage, int i, int i2) {
            if (com.android.server.RescueParty.isDisabled()) {
                return false;
            }
            if (i != 3 && i != 4) {
                return false;
            }
            com.android.server.RescueParty.executeRescueLevel(this.mContext, versionedPackage == null ? null : versionedPackage.getPackageName(), com.android.server.RescueParty.getRescueLevel(i2, mayPerformReboot(versionedPackage)));
            return true;
        }

        @Override // com.android.server.PackageWatchdog.PackageHealthObserver
        public boolean isPersistent() {
            return true;
        }

        @Override // com.android.server.PackageWatchdog.PackageHealthObserver
        public boolean mayObservePackage(java.lang.String str) {
            try {
                if (this.mContext.getPackageManager().getModuleInfo(str, 0) != null) {
                    return true;
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
            return isPersistentSystemApp(str);
        }

        @Override // com.android.server.PackageWatchdog.PackageHealthObserver
        public int onBootLoop(int i) {
            if (com.android.server.RescueParty.isDisabled()) {
                return 0;
            }
            return com.android.server.RescueParty.mapRescueLevelToUserImpact(com.android.server.RescueParty.getRescueLevel(i, true));
        }

        @Override // com.android.server.PackageWatchdog.PackageHealthObserver
        public boolean executeBootLoopMitigation(int i) {
            if (com.android.server.RescueParty.isDisabled()) {
                return false;
            }
            com.android.server.RescueParty.executeRescueLevel(this.mContext, null, com.android.server.RescueParty.getRescueLevel(i, !shouldThrottleReboot()));
            return true;
        }

        @Override // com.android.server.PackageWatchdog.PackageHealthObserver
        public java.lang.String getName() {
            return com.android.server.RescueParty.NAME;
        }

        private boolean mayPerformReboot(@android.annotation.Nullable android.content.pm.VersionedPackage versionedPackage) {
            if (versionedPackage == null || shouldThrottleReboot()) {
                return false;
            }
            return isPersistentSystemApp(versionedPackage.getPackageName());
        }

        private boolean shouldThrottleReboot() {
            return java.lang.System.currentTimeMillis() < java.lang.Long.valueOf(com.android.server.RescueParty.getLastFactoryResetTimeMs()).longValue() + java.util.concurrent.TimeUnit.MINUTES.toMillis(android.os.SystemProperties.getLong(com.android.server.RescueParty.PROP_THROTTLE_DURATION_MIN_FLAG, com.android.server.RescueParty.DEFAULT_FACTORY_RESET_THROTTLE_DURATION_MIN));
        }

        private boolean isPersistentSystemApp(@android.annotation.NonNull java.lang.String str) {
            try {
                return (this.mContext.getPackageManager().getApplicationInfo(str, 0).flags & 9) == 9;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void recordDeviceConfigAccess(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
            try {
                java.util.Set<java.lang.String> set = this.mCallingPackageNamespaceSetMap.get(str);
                if (set == null) {
                    set = new android.util.ArraySet<>();
                    this.mCallingPackageNamespaceSetMap.put(str, set);
                }
                set.add(str2);
                java.util.Set<java.lang.String> set2 = this.mNamespaceCallingPackageSetMap.get(str2);
                if (set2 == null) {
                    set2 = new android.util.ArraySet<>();
                }
                set2.add(str);
                this.mNamespaceCallingPackageSetMap.put(str2, set2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized java.util.Set<java.lang.String> getAffectedNamespaceSet(java.lang.String str) {
            return this.mCallingPackageNamespaceSetMap.get(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized java.util.Set<java.lang.String> getAllAffectedNamespaceSet() {
            return new java.util.HashSet(this.mNamespaceCallingPackageSetMap.keySet());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized java.util.Set<java.lang.String> getCallingPackagesSet(java.lang.String str) {
            return this.mNamespaceCallingPackageSetMap.get(str);
        }
    }

    private static int[] getAllUserIds() {
        int identifier = android.os.UserHandle.SYSTEM.getIdentifier();
        int[] iArr = {identifier};
        try {
            for (java.io.File file : android.os.FileUtils.listFilesOrEmpty(android.os.Environment.getDataSystemDeDirectory())) {
                try {
                    int parseInt = java.lang.Integer.parseInt(file.getName());
                    if (parseInt != identifier) {
                        iArr = com.android.internal.util.ArrayUtils.appendInt(iArr, parseInt);
                    }
                } catch (java.lang.NumberFormatException e) {
                }
            }
        } catch (java.lang.Throwable th) {
            android.util.Slog.w(TAG, "Trouble discovering users", th);
        }
        return iArr;
    }

    private static boolean isUsbActive() {
        if (android.os.SystemProperties.getBoolean(PROP_VIRTUAL_DEVICE, false)) {
            android.util.Slog.v(TAG, "Assuming virtual device is connected over USB");
            return true;
        }
        try {
            return "CONFIGURED".equals(android.os.FileUtils.readTextFile(new java.io.File("/sys/class/android_usb/android0/state"), 128, "").trim());
        } catch (java.lang.Throwable th) {
            android.util.Slog.w(TAG, "Failed to determine if device was on USB", th);
            return false;
        }
    }

    private static java.lang.String levelToString(int i) {
        switch (i) {
            case 0:
                return "NONE";
            case 1:
                return "RESET_SETTINGS_UNTRUSTED_DEFAULTS";
            case 2:
                return "RESET_SETTINGS_UNTRUSTED_CHANGES";
            case 3:
                return "RESET_SETTINGS_TRUSTED_DEFAULTS";
            case 4:
                return "WARM_REBOOT";
            case 5:
                return "FACTORY_RESET";
            default:
                return java.lang.Integer.toString(i);
        }
    }
}
