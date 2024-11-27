package android.crashrecovery.flags;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements android.crashrecovery.flags.FeatureFlags {
    @Override // android.crashrecovery.flags.FeatureFlags
    public boolean enableCrashrecovery() {
        return false;
    }

    @Override // android.crashrecovery.flags.FeatureFlags
    public boolean recoverabilityDetection() {
        return false;
    }
}
