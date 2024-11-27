package android.hardware.camera2;

/* loaded from: classes.dex */
public abstract class CameraCaptureSession implements java.lang.AutoCloseable {
    public static final int SESSION_ID_NONE = -1;

    public abstract void abortCaptures() throws android.hardware.camera2.CameraAccessException;

    public abstract int capture(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;

    public abstract int captureBurst(java.util.List<android.hardware.camera2.CaptureRequest> list, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;

    @Override // java.lang.AutoCloseable
    public abstract void close();

    public abstract void finalizeOutputConfigurations(java.util.List<android.hardware.camera2.params.OutputConfiguration> list) throws android.hardware.camera2.CameraAccessException;

    public abstract android.hardware.camera2.CameraDevice getDevice();

    public abstract android.view.Surface getInputSurface();

    public abstract boolean isReprocessable();

    public abstract void prepare(int i, android.view.Surface surface) throws android.hardware.camera2.CameraAccessException;

    public abstract void prepare(android.view.Surface surface) throws android.hardware.camera2.CameraAccessException;

    public abstract int setRepeatingBurst(java.util.List<android.hardware.camera2.CaptureRequest> list, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;

    public abstract int setRepeatingRequest(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;

    public abstract void stopRepeating() throws android.hardware.camera2.CameraAccessException;

    public abstract void tearDown(android.view.Surface surface) throws android.hardware.camera2.CameraAccessException;

    public int captureSingleRequest(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    public int captureBurstRequests(java.util.List<android.hardware.camera2.CaptureRequest> list, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    public int setSingleRepeatingRequest(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    public int setRepeatingBurstRequests(java.util.List<android.hardware.camera2.CaptureRequest> list, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    public void updateOutputConfiguration(android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    public android.hardware.camera2.CameraOfflineSession switchToOffline(java.util.Collection<android.view.Surface> collection, java.util.concurrent.Executor executor, android.hardware.camera2.CameraOfflineSession.CameraOfflineSessionCallback cameraOfflineSessionCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    public boolean supportsOfflineProcessing(android.view.Surface surface) {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    public static abstract class StateCallback {
        public abstract void onConfigureFailed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession);

        public abstract void onConfigured(android.hardware.camera2.CameraCaptureSession cameraCaptureSession);

        public void onReady(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
        }

        public void onActive(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
        }

        public void onCaptureQueueEmpty(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
        }

        public void onClosed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
        }

        public void onSurfacePrepared(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.view.Surface surface) {
        }
    }

    public static abstract class CaptureCallback {
        public static final int NO_FRAMES_CAPTURED = -1;

        public void onCaptureStarted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, long j, long j2) {
        }

        public void onReadoutStarted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, long j, long j2) {
        }

        public void onCapturePartial(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureResult captureResult) {
        }

        public void onCaptureProgressed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureResult captureResult) {
        }

        public void onCaptureCompleted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.TotalCaptureResult totalCaptureResult) {
        }

        public void onCaptureFailed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureFailure captureFailure) {
        }

        public void onCaptureSequenceCompleted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, int i, long j) {
        }

        public void onCaptureSequenceAborted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, int i) {
        }

        public void onCaptureBufferLost(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.view.Surface surface, long j) {
        }
    }
}
