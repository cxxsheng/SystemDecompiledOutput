package android.chre.flags;

/* loaded from: classes.dex */
public interface FeatureFlags {
    boolean contextHubCallbackUuidEnabled();

    boolean flagLogNanoappLoadMetrics();

    boolean metricsReporterInTheDaemon();

    boolean reliableMessage();

    boolean reliableMessageImplementation();

    boolean removeApWakeupMetricReportLimit();

    boolean waitForPreloadedNanoappStart();
}
