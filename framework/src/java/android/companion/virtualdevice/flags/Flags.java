package android.companion.virtualdevice.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static android.companion.virtualdevice.flags.FeatureFlags FEATURE_FLAGS = new android.companion.virtualdevice.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_CAMERA_DEVICE_AWARENESS = "android.companion.virtualdevice.flags.camera_device_awareness";
    public static final java.lang.String FLAG_DEVICE_AWARE_RECORD_AUDIO_PERMISSION = "android.companion.virtualdevice.flags.device_aware_record_audio_permission";
    public static final java.lang.String FLAG_METRICS_COLLECTION = "android.companion.virtualdevice.flags.metrics_collection";
    public static final java.lang.String FLAG_VIRTUAL_CAMERA_SERVICE_DISCOVERY = "android.companion.virtualdevice.flags.virtual_camera_service_discovery";

    public static boolean cameraDeviceAwareness() {
        return FEATURE_FLAGS.cameraDeviceAwareness();
    }

    public static boolean deviceAwareRecordAudioPermission() {
        return FEATURE_FLAGS.deviceAwareRecordAudioPermission();
    }

    public static boolean metricsCollection() {
        return FEATURE_FLAGS.metricsCollection();
    }

    public static boolean virtualCameraServiceDiscovery() {
        return FEATURE_FLAGS.virtualCameraServiceDiscovery();
    }
}
