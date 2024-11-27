package android.os;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean adpfGpuReportActualWorkDuration();

    boolean adpfPreferPowerEfficiency();

    boolean adpfUseFmqChannel();

    boolean allowPrivateProfile();

    boolean allowThermalHeadroomThresholds();

    boolean androidOsBuildVanillaIceCream();

    boolean batteryPartStatusApi();

    boolean batterySaverSupportedCheckApi();

    boolean batteryServiceSupportCurrentAdbCommand();

    boolean bugreportModeMaxValue();

    boolean disallowCellularNullCiphersRestriction();

    boolean messageQueueTailTracking();

    boolean removeAppProfilerPssCollection();

    boolean securityStateService();

    boolean stateOfHealthPublic();

    boolean storageLifetimeApi();

    boolean strictModeRestrictedNetwork();

    boolean telemetryApisFrameworkInitialization();
}
