package android.hardware.camera2;

/* loaded from: classes.dex */
public final class ExtensionCaptureRequest {
    public static final int EFV_STABILIZATION_MODE_GIMBAL = 1;
    public static final int EFV_STABILIZATION_MODE_LOCKED = 2;
    public static final int EFV_STABILIZATION_MODE_OFF = 0;

    @android.hardware.camera2.impl.ExtensionKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Float> EFV_PADDING_ZOOM_FACTOR = android.hardware.camera2.CaptureRequest.EFV_PADDING_ZOOM_FACTOR;

    @android.hardware.camera2.impl.ExtensionKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean> EFV_AUTO_ZOOM = android.hardware.camera2.CaptureRequest.EFV_AUTO_ZOOM;

    @android.hardware.camera2.impl.ExtensionKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Float> EFV_MAX_PADDING_ZOOM_FACTOR = android.hardware.camera2.CaptureRequest.EFV_MAX_PADDING_ZOOM_FACTOR;

    @android.hardware.camera2.impl.ExtensionKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> EFV_STABILIZATION_MODE = android.hardware.camera2.CaptureRequest.EFV_STABILIZATION_MODE;

    @android.hardware.camera2.impl.ExtensionKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<android.util.Pair<java.lang.Integer, java.lang.Integer>> EFV_TRANSLATE_VIEWPORT = android.hardware.camera2.CaptureRequest.EFV_TRANSLATE_VIEWPORT;

    @android.hardware.camera2.impl.ExtensionKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Float> EFV_ROTATE_VIEWPORT = android.hardware.camera2.CaptureRequest.EFV_ROTATE_VIEWPORT;
}
