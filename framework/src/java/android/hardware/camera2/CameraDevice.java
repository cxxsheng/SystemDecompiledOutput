package android.hardware.camera2;

/* loaded from: classes.dex */
public abstract class CameraDevice implements java.lang.AutoCloseable {
    public static final int AUDIO_RESTRICTION_NONE = 0;
    public static final int AUDIO_RESTRICTION_VIBRATION = 1;
    public static final int AUDIO_RESTRICTION_VIBRATION_SOUND = 3;

    @android.annotation.SystemApi
    public static final int SESSION_OPERATION_MODE_CONSTRAINED_HIGH_SPEED = 1;

    @android.annotation.SystemApi
    public static final int SESSION_OPERATION_MODE_NORMAL = 0;

    @android.annotation.SystemApi
    public static final int SESSION_OPERATION_MODE_VENDOR_START = 32768;
    public static final int TEMPLATE_MANUAL = 6;
    public static final int TEMPLATE_PREVIEW = 1;
    public static final int TEMPLATE_RECORD = 3;
    public static final int TEMPLATE_STILL_CAPTURE = 2;
    public static final int TEMPLATE_VIDEO_SNAPSHOT = 4;
    public static final int TEMPLATE_ZERO_SHUTTER_LAG = 5;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CAMERA_AUDIO_RESTRICTION {
    }

    public static abstract class CameraDeviceSetup {
        public abstract android.hardware.camera2.CaptureRequest.Builder createCaptureRequest(int i) throws android.hardware.camera2.CameraAccessException;

        public abstract java.lang.String getId();

        public abstract android.hardware.camera2.CameraCharacteristics getSessionCharacteristics(android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.hardware.camera2.CameraAccessException;

        public abstract boolean isSessionConfigurationSupported(android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.hardware.camera2.CameraAccessException;

        public abstract void openCamera(java.util.concurrent.Executor executor, android.hardware.camera2.CameraDevice.StateCallback stateCallback) throws android.hardware.camera2.CameraAccessException;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RequestTemplate {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SessionOperatingMode {
    }

    @Override // java.lang.AutoCloseable
    public abstract void close();

    public abstract android.hardware.camera2.CaptureRequest.Builder createCaptureRequest(int i) throws android.hardware.camera2.CameraAccessException;

    @java.lang.Deprecated
    public abstract void createCaptureSession(java.util.List<android.view.Surface> list, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;

    @java.lang.Deprecated
    public abstract void createCaptureSessionByOutputConfigurations(java.util.List<android.hardware.camera2.params.OutputConfiguration> list, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;

    @java.lang.Deprecated
    public abstract void createConstrainedHighSpeedCaptureSession(java.util.List<android.view.Surface> list, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract void createCustomCaptureSession(android.hardware.camera2.params.InputConfiguration inputConfiguration, java.util.List<android.hardware.camera2.params.OutputConfiguration> list, int i, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;

    public abstract android.hardware.camera2.CaptureRequest.Builder createReprocessCaptureRequest(android.hardware.camera2.TotalCaptureResult totalCaptureResult) throws android.hardware.camera2.CameraAccessException;

    @java.lang.Deprecated
    public abstract void createReprocessableCaptureSession(android.hardware.camera2.params.InputConfiguration inputConfiguration, java.util.List<android.view.Surface> list, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;

    @java.lang.Deprecated
    public abstract void createReprocessableCaptureSessionByConfigurations(android.hardware.camera2.params.InputConfiguration inputConfiguration, java.util.List<android.hardware.camera2.params.OutputConfiguration> list, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;

    public abstract java.lang.String getId();

    public void createExtensionSession(android.hardware.camera2.params.ExtensionSessionConfiguration extensionSessionConfiguration) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("No default implementation");
    }

    public void createCaptureSession(android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("No default implementation");
    }

    public android.hardware.camera2.CaptureRequest.Builder createCaptureRequest(int i, java.util.Set<java.lang.String> set) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    public boolean isSessionConfigurationSupported(android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    public static abstract class StateCallback {
        public static final int ERROR_CAMERA_DEVICE = 4;
        public static final int ERROR_CAMERA_DISABLED = 3;
        public static final int ERROR_CAMERA_IN_USE = 1;
        public static final int ERROR_CAMERA_SERVICE = 5;
        public static final int ERROR_MAX_CAMERAS_IN_USE = 2;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ErrorCode {
        }

        public abstract void onDisconnected(android.hardware.camera2.CameraDevice cameraDevice);

        public abstract void onError(android.hardware.camera2.CameraDevice cameraDevice, int i);

        public abstract void onOpened(android.hardware.camera2.CameraDevice cameraDevice);

        public void onClosed(android.hardware.camera2.CameraDevice cameraDevice) {
        }
    }

    public void setCameraAudioRestriction(int i) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    public int getCameraAudioRestriction() throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }
}
