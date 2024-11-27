package com.android.server.backup;

/* loaded from: classes5.dex */
public class FakeFeatureFlagsImpl implements com.android.server.backup.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.server.backup.Flags.FLAG_ENABLE_CLEAR_PIPE_AFTER_RESTORE_FILE, false), java.util.Map.entry(com.android.server.backup.Flags.FLAG_ENABLE_INCREASE_DATATYPES_FOR_AGENT_LOGGING, false), java.util.Map.entry(com.android.server.backup.Flags.FLAG_ENABLE_MAX_SIZE_WRITES_TO_PIPES, false), java.util.Map.entry(com.android.server.backup.Flags.FLAG_ENABLE_METRICS_SYSTEM_BACKUP_AGENTS, false), java.util.Map.entry(com.android.server.backup.Flags.FLAG_ENABLE_SKIPPING_RESTORE_LAUNCHED_APPS, false), java.util.Map.entry(com.android.server.backup.Flags.FLAG_ENABLE_V_TO_U_RESTORE_FOR_SYSTEM_COMPONENTS_IN_ALLOWLIST, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.server.backup.Flags.FLAG_ENABLE_CLEAR_PIPE_AFTER_RESTORE_FILE, com.android.server.backup.Flags.FLAG_ENABLE_INCREASE_DATATYPES_FOR_AGENT_LOGGING, com.android.server.backup.Flags.FLAG_ENABLE_MAX_SIZE_WRITES_TO_PIPES, com.android.server.backup.Flags.FLAG_ENABLE_METRICS_SYSTEM_BACKUP_AGENTS, com.android.server.backup.Flags.FLAG_ENABLE_SKIPPING_RESTORE_LAUNCHED_APPS, com.android.server.backup.Flags.FLAG_ENABLE_V_TO_U_RESTORE_FOR_SYSTEM_COMPONENTS_IN_ALLOWLIST, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableClearPipeAfterRestoreFile() {
        return getValue(com.android.server.backup.Flags.FLAG_ENABLE_CLEAR_PIPE_AFTER_RESTORE_FILE);
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableIncreaseDatatypesForAgentLogging() {
        return getValue(com.android.server.backup.Flags.FLAG_ENABLE_INCREASE_DATATYPES_FOR_AGENT_LOGGING);
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableMaxSizeWritesToPipes() {
        return getValue(com.android.server.backup.Flags.FLAG_ENABLE_MAX_SIZE_WRITES_TO_PIPES);
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableMetricsSystemBackupAgents() {
        return getValue(com.android.server.backup.Flags.FLAG_ENABLE_METRICS_SYSTEM_BACKUP_AGENTS);
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableSkippingRestoreLaunchedApps() {
        return getValue(com.android.server.backup.Flags.FLAG_ENABLE_SKIPPING_RESTORE_LAUNCHED_APPS);
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableVToURestoreForSystemComponentsInAllowlist() {
        return getValue(com.android.server.backup.Flags.FLAG_ENABLE_V_TO_U_RESTORE_FOR_SYSTEM_COMPONENTS_IN_ALLOWLIST);
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
