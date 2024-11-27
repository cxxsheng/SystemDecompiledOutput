package android.hardware.camera2;

/* loaded from: classes.dex */
public abstract class CameraOfflineSession extends android.hardware.camera2.CameraCaptureSession {

    public static abstract class CameraOfflineSessionCallback {
        public static final int STATUS_INTERNAL_ERROR = 0;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface StatusCode {
        }

        public abstract void onClosed(android.hardware.camera2.CameraOfflineSession cameraOfflineSession);

        public abstract void onError(android.hardware.camera2.CameraOfflineSession cameraOfflineSession, int i);

        public abstract void onIdle(android.hardware.camera2.CameraOfflineSession cameraOfflineSession);

        public abstract void onReady(android.hardware.camera2.CameraOfflineSession cameraOfflineSession);

        public abstract void onSwitchFailed(android.hardware.camera2.CameraOfflineSession cameraOfflineSession);
    }

    @Override // android.hardware.camera2.CameraCaptureSession, java.lang.AutoCloseable
    public abstract void close();
}
