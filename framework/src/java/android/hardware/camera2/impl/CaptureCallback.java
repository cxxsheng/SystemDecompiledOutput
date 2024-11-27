package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public abstract class CaptureCallback {
    private android.hardware.camera2.CameraCaptureSession.CaptureCallback mCallback;
    private java.util.concurrent.Executor mExecutor;

    public abstract void onCaptureBufferLost(android.hardware.camera2.CameraDevice cameraDevice, android.hardware.camera2.CaptureRequest captureRequest, android.view.Surface surface, long j);

    public abstract void onCaptureCompleted(android.hardware.camera2.CameraDevice cameraDevice, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.TotalCaptureResult totalCaptureResult);

    public abstract void onCaptureFailed(android.hardware.camera2.CameraDevice cameraDevice, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureFailure captureFailure);

    public abstract void onCapturePartial(android.hardware.camera2.CameraDevice cameraDevice, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureResult captureResult);

    public abstract void onCaptureProgressed(android.hardware.camera2.CameraDevice cameraDevice, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureResult captureResult);

    public abstract void onCaptureSequenceAborted(android.hardware.camera2.CameraDevice cameraDevice, int i);

    public abstract void onCaptureSequenceCompleted(android.hardware.camera2.CameraDevice cameraDevice, int i, long j);

    public abstract void onCaptureStarted(android.hardware.camera2.CameraDevice cameraDevice, android.hardware.camera2.CaptureRequest captureRequest, long j, long j2);

    public abstract void onReadoutStarted(android.hardware.camera2.CameraDevice cameraDevice, android.hardware.camera2.CaptureRequest captureRequest, long j, long j2);

    public CaptureCallback(java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) {
        this.mExecutor = executor;
        this.mCallback = captureCallback;
    }

    public java.util.concurrent.Executor getExecutor() {
        return this.mExecutor;
    }

    public android.hardware.camera2.CameraCaptureSession.CaptureCallback getSessionCallback() {
        return this.mCallback;
    }
}
