package android.database.sqlite;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.database.sqlite.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.database.sqlite.Flags.FLAG_SQLITE_ALLOW_TEMP_TABLES, false), java.util.Map.entry(android.database.sqlite.Flags.FLAG_SQLITE_APIS_35, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.database.sqlite.Flags.FLAG_SQLITE_ALLOW_TEMP_TABLES, android.database.sqlite.Flags.FLAG_SQLITE_APIS_35, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean sqliteAllowTempTables() {
        return getValue(android.database.sqlite.Flags.FLAG_SQLITE_ALLOW_TEMP_TABLES);
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean sqliteApis35() {
        return getValue(android.database.sqlite.Flags.FLAG_SQLITE_APIS_35);
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
