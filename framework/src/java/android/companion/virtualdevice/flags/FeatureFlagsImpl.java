package android.companion.virtualdevice.flags;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements android.companion.virtualdevice.flags.FeatureFlags {
    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean cameraDeviceAwareness() {
        return false;
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean deviceAwareRecordAudioPermission() {
        return false;
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean metricsCollection() {
        return false;
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean virtualCameraServiceDiscovery() {
        return false;
    }
}
