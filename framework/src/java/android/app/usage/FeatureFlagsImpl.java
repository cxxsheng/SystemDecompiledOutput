package android.app.usage;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements android.app.usage.FeatureFlags {
    @Override // android.app.usage.FeatureFlags
    public boolean filterBasedEventQueryApi() {
        return false;
    }

    @Override // android.app.usage.FeatureFlags
    public boolean getAppBytesByDataTypeApi() {
        return false;
    }

    @Override // android.app.usage.FeatureFlags
    public boolean reportUsageStatsPermission() {
        return false;
    }

    @Override // android.app.usage.FeatureFlags
    public boolean useDedicatedHandlerThread() {
        return false;
    }

    @Override // android.app.usage.FeatureFlags
    public boolean useParceledList() {
        return true;
    }

    @Override // android.app.usage.FeatureFlags
    public boolean userInteractionTypeApi() {
        return false;
    }
}
