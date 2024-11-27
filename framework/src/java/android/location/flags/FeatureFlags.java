package android.location.flags;

/* loaded from: classes2.dex */
public interface FeatureFlags {
    boolean fixServiceWatcher();

    boolean geoidHeightsViaAltitudeHal();

    boolean gnssApiMeasurementRequestWorkSource();

    boolean gnssApiNavicL1();

    boolean gnssCallStopBeforeSetPositionMode();

    boolean gnssConfigurationFromResource();

    boolean locationBypass();

    boolean locationValidation();

    boolean newGeocoder();

    boolean releaseSuplConnectionOnTimeout();

    boolean replaceFutureElapsedRealtimeJni();
}
