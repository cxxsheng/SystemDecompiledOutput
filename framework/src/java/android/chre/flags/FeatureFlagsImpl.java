package android.chre.flags;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements android.chre.flags.FeatureFlags {
    @Override // android.chre.flags.FeatureFlags
    public boolean contextHubCallbackUuidEnabled() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean flagLogNanoappLoadMetrics() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean metricsReporterInTheDaemon() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessage() {
        return false;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageImplementation() {
        return false;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean removeApWakeupMetricReportLimit() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean waitForPreloadedNanoappStart() {
        return false;
    }
}
