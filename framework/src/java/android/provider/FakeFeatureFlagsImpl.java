package android.provider;

/* loaded from: classes3.dex */
public class FakeFeatureFlagsImpl implements android.provider.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.provider.Flags.FLAG_BACKUP_TASKS_SETTINGS_SCREEN, false), java.util.Map.entry(android.provider.Flags.FLAG_SYSTEM_SETTINGS_DEFAULT, false), java.util.Map.entry(android.provider.Flags.FLAG_USER_KEYS, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.provider.Flags.FLAG_BACKUP_TASKS_SETTINGS_SCREEN, android.provider.Flags.FLAG_SYSTEM_SETTINGS_DEFAULT, android.provider.Flags.FLAG_USER_KEYS, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.provider.FeatureFlags
    public boolean backupTasksSettingsScreen() {
        return getValue(android.provider.Flags.FLAG_BACKUP_TASKS_SETTINGS_SCREEN);
    }

    @Override // android.provider.FeatureFlags
    public boolean systemSettingsDefault() {
        return getValue(android.provider.Flags.FLAG_SYSTEM_SETTINGS_DEFAULT);
    }

    @Override // android.provider.FeatureFlags
    public boolean userKeys() {
        return getValue(android.provider.Flags.FLAG_USER_KEYS);
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
