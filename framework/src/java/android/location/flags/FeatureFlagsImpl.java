package android.location.flags;

/* loaded from: classes2.dex */
public final class FeatureFlagsImpl implements android.location.flags.FeatureFlags {
    @Override // android.location.flags.FeatureFlags
    public boolean fixServiceWatcher() {
        return false;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean geoidHeightsViaAltitudeHal() {
        return false;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean gnssApiMeasurementRequestWorkSource() {
        return false;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean gnssApiNavicL1() {
        return false;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean gnssCallStopBeforeSetPositionMode() {
        return true;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean gnssConfigurationFromResource() {
        return true;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean locationBypass() {
        return false;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean locationValidation() {
        return false;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean newGeocoder() {
        return false;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean releaseSuplConnectionOnTimeout() {
        return false;
    }

    @Override // android.location.flags.FeatureFlags
    public boolean replaceFutureElapsedRealtimeJni() {
        return true;
    }
}
