package android.database.sqlite;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements android.database.sqlite.FeatureFlags {
    @Override // android.database.sqlite.FeatureFlags
    public boolean sqliteAllowTempTables() {
        return false;
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean sqliteApis35() {
        return false;
    }
}
