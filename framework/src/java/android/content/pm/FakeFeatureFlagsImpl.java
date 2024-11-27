package android.content.pm;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.content.pm.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.content.pm.Flags.FLAG_ALLOW_SDK_SANDBOX_QUERY_INTENT_ACTIVITIES, false), java.util.Map.entry(android.content.pm.Flags.FLAG_ARCHIVING, false), java.util.Map.entry(android.content.pm.Flags.FLAG_ASL_IN_APK_APP_METADATA_SOURCE, false), java.util.Map.entry(android.content.pm.Flags.FLAG_DISALLOW_SDK_LIBS_TO_BE_APPS, false), java.util.Map.entry(android.content.pm.Flags.FLAG_EMERGENCY_INSTALL_PERMISSION, false), java.util.Map.entry(android.content.pm.Flags.FLAG_ENCODE_APP_INTENT, false), java.util.Map.entry(android.content.pm.Flags.FLAG_FIX_DUPLICATED_FLAGS, false), java.util.Map.entry(android.content.pm.Flags.FLAG_FIX_SYSTEM_APPS_FIRST_INSTALL_TIME, false), java.util.Map.entry(android.content.pm.Flags.FLAG_FORCE_MULTI_ARCH_NATIVE_LIBS_MATCH, false), java.util.Map.entry(android.content.pm.Flags.FLAG_GET_PACKAGE_INFO, false), java.util.Map.entry(android.content.pm.Flags.FLAG_GET_RESOLVED_APK_PATH, false), java.util.Map.entry(android.content.pm.Flags.FLAG_IMPROVE_HOME_APP_BEHAVIOR, false), java.util.Map.entry(android.content.pm.Flags.FLAG_IMPROVE_INSTALL_DONT_KILL, false), java.util.Map.entry(android.content.pm.Flags.FLAG_IMPROVE_INSTALL_FREEZE, false), java.util.Map.entry(android.content.pm.Flags.FLAG_INTRODUCE_MEDIA_PROCESSING_TYPE, false), java.util.Map.entry(android.content.pm.Flags.FLAG_LIGHTWEIGHT_INVISIBLE_LABEL_DETECTION, false), java.util.Map.entry(android.content.pm.Flags.FLAG_MIN_TARGET_SDK_24, false), java.util.Map.entry(android.content.pm.Flags.FLAG_NULLABLE_DATA_DIR, false), java.util.Map.entry(android.content.pm.Flags.FLAG_PROVIDE_INFO_OF_APK_IN_APEX, false), java.util.Map.entry(android.content.pm.Flags.FLAG_QUARANTINED_ENABLED, false), java.util.Map.entry(android.content.pm.Flags.FLAG_READ_INSTALL_INFO, false), java.util.Map.entry(android.content.pm.Flags.FLAG_RECOVERABILITY_DETECTION, false), java.util.Map.entry(android.content.pm.Flags.FLAG_RELATIVE_REFERENCE_INTENT_FILTERS, false), java.util.Map.entry(android.content.pm.Flags.FLAG_RESTRICT_NONPRELOADS_SYSTEM_SHAREDUIDS, false), java.util.Map.entry(android.content.pm.Flags.FLAG_ROLLBACK_LIFETIME, false), java.util.Map.entry(android.content.pm.Flags.FLAG_SDK_LIB_INDEPENDENCE, false), java.util.Map.entry(android.content.pm.Flags.FLAG_SET_PRE_VERIFIED_DOMAINS, false), java.util.Map.entry(android.content.pm.Flags.FLAG_STAY_STOPPED, false), java.util.Map.entry(android.content.pm.Flags.FLAG_USE_ART_SERVICE_V2, false), java.util.Map.entry(android.content.pm.Flags.FLAG_USE_PIA_V2, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.content.pm.Flags.FLAG_ALLOW_SDK_SANDBOX_QUERY_INTENT_ACTIVITIES, android.content.pm.Flags.FLAG_ARCHIVING, android.content.pm.Flags.FLAG_ASL_IN_APK_APP_METADATA_SOURCE, android.content.pm.Flags.FLAG_DISALLOW_SDK_LIBS_TO_BE_APPS, android.content.pm.Flags.FLAG_EMERGENCY_INSTALL_PERMISSION, android.content.pm.Flags.FLAG_ENCODE_APP_INTENT, android.content.pm.Flags.FLAG_FIX_DUPLICATED_FLAGS, android.content.pm.Flags.FLAG_FIX_SYSTEM_APPS_FIRST_INSTALL_TIME, android.content.pm.Flags.FLAG_FORCE_MULTI_ARCH_NATIVE_LIBS_MATCH, android.content.pm.Flags.FLAG_GET_PACKAGE_INFO, android.content.pm.Flags.FLAG_GET_RESOLVED_APK_PATH, android.content.pm.Flags.FLAG_IMPROVE_HOME_APP_BEHAVIOR, android.content.pm.Flags.FLAG_IMPROVE_INSTALL_DONT_KILL, android.content.pm.Flags.FLAG_IMPROVE_INSTALL_FREEZE, android.content.pm.Flags.FLAG_INTRODUCE_MEDIA_PROCESSING_TYPE, android.content.pm.Flags.FLAG_LIGHTWEIGHT_INVISIBLE_LABEL_DETECTION, android.content.pm.Flags.FLAG_MIN_TARGET_SDK_24, android.content.pm.Flags.FLAG_NULLABLE_DATA_DIR, android.content.pm.Flags.FLAG_PROVIDE_INFO_OF_APK_IN_APEX, android.content.pm.Flags.FLAG_QUARANTINED_ENABLED, android.content.pm.Flags.FLAG_READ_INSTALL_INFO, android.content.pm.Flags.FLAG_RECOVERABILITY_DETECTION, android.content.pm.Flags.FLAG_RELATIVE_REFERENCE_INTENT_FILTERS, android.content.pm.Flags.FLAG_RESTRICT_NONPRELOADS_SYSTEM_SHAREDUIDS, android.content.pm.Flags.FLAG_ROLLBACK_LIFETIME, android.content.pm.Flags.FLAG_SDK_LIB_INDEPENDENCE, android.content.pm.Flags.FLAG_SET_PRE_VERIFIED_DOMAINS, android.content.pm.Flags.FLAG_STAY_STOPPED, android.content.pm.Flags.FLAG_USE_ART_SERVICE_V2, android.content.pm.Flags.FLAG_USE_PIA_V2, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.content.pm.FeatureFlags
    public boolean allowSdkSandboxQueryIntentActivities() {
        return getValue(android.content.pm.Flags.FLAG_ALLOW_SDK_SANDBOX_QUERY_INTENT_ACTIVITIES);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean archiving() {
        return getValue(android.content.pm.Flags.FLAG_ARCHIVING);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean aslInApkAppMetadataSource() {
        return getValue(android.content.pm.Flags.FLAG_ASL_IN_APK_APP_METADATA_SOURCE);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean disallowSdkLibsToBeApps() {
        return getValue(android.content.pm.Flags.FLAG_DISALLOW_SDK_LIBS_TO_BE_APPS);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean emergencyInstallPermission() {
        return getValue(android.content.pm.Flags.FLAG_EMERGENCY_INSTALL_PERMISSION);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean encodeAppIntent() {
        return getValue(android.content.pm.Flags.FLAG_ENCODE_APP_INTENT);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean fixDuplicatedFlags() {
        return getValue(android.content.pm.Flags.FLAG_FIX_DUPLICATED_FLAGS);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean fixSystemAppsFirstInstallTime() {
        return getValue(android.content.pm.Flags.FLAG_FIX_SYSTEM_APPS_FIRST_INSTALL_TIME);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean forceMultiArchNativeLibsMatch() {
        return getValue(android.content.pm.Flags.FLAG_FORCE_MULTI_ARCH_NATIVE_LIBS_MATCH);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean getPackageInfo() {
        return getValue(android.content.pm.Flags.FLAG_GET_PACKAGE_INFO);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean getResolvedApkPath() {
        return getValue(android.content.pm.Flags.FLAG_GET_RESOLVED_APK_PATH);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean improveHomeAppBehavior() {
        return getValue(android.content.pm.Flags.FLAG_IMPROVE_HOME_APP_BEHAVIOR);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean improveInstallDontKill() {
        return getValue(android.content.pm.Flags.FLAG_IMPROVE_INSTALL_DONT_KILL);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean improveInstallFreeze() {
        return getValue(android.content.pm.Flags.FLAG_IMPROVE_INSTALL_FREEZE);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean introduceMediaProcessingType() {
        return getValue(android.content.pm.Flags.FLAG_INTRODUCE_MEDIA_PROCESSING_TYPE);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean lightweightInvisibleLabelDetection() {
        return getValue(android.content.pm.Flags.FLAG_LIGHTWEIGHT_INVISIBLE_LABEL_DETECTION);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean minTargetSdk24() {
        return getValue(android.content.pm.Flags.FLAG_MIN_TARGET_SDK_24);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean nullableDataDir() {
        return getValue(android.content.pm.Flags.FLAG_NULLABLE_DATA_DIR);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean provideInfoOfApkInApex() {
        return getValue(android.content.pm.Flags.FLAG_PROVIDE_INFO_OF_APK_IN_APEX);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean quarantinedEnabled() {
        return getValue(android.content.pm.Flags.FLAG_QUARANTINED_ENABLED);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean readInstallInfo() {
        return getValue(android.content.pm.Flags.FLAG_READ_INSTALL_INFO);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean recoverabilityDetection() {
        return getValue(android.content.pm.Flags.FLAG_RECOVERABILITY_DETECTION);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean relativeReferenceIntentFilters() {
        return getValue(android.content.pm.Flags.FLAG_RELATIVE_REFERENCE_INTENT_FILTERS);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean restrictNonpreloadsSystemShareduids() {
        return getValue(android.content.pm.Flags.FLAG_RESTRICT_NONPRELOADS_SYSTEM_SHAREDUIDS);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean rollbackLifetime() {
        return getValue(android.content.pm.Flags.FLAG_ROLLBACK_LIFETIME);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean sdkLibIndependence() {
        return getValue(android.content.pm.Flags.FLAG_SDK_LIB_INDEPENDENCE);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean setPreVerifiedDomains() {
        return getValue(android.content.pm.Flags.FLAG_SET_PRE_VERIFIED_DOMAINS);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean stayStopped() {
        return getValue(android.content.pm.Flags.FLAG_STAY_STOPPED);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean useArtServiceV2() {
        return getValue(android.content.pm.Flags.FLAG_USE_ART_SERVICE_V2);
    }

    @Override // android.content.pm.FeatureFlags
    public boolean usePiaV2() {
        return getValue(android.content.pm.Flags.FLAG_USE_PIA_V2);
    }

    public void setFlag(java.lang.String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, java.lang.Boolean.valueOf(z));
    }

    public void resetAll() {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }

    public boolean isFlagReadOnlyOptimized(java.lang.String str) {
        if (this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean getValue(java.lang.String str) {
        java.lang.Boolean bool = this.mFlagMap.get(str);
        if (bool == null) {
            throw new java.lang.IllegalArgumentException(str + " is not set");
        }
        return bool.booleanValue();
    }

    private boolean isOptimizationEnabled() {
        return false;
    }
}
