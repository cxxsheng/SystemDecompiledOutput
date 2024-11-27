package android.hardware.camera2;

/* loaded from: classes.dex */
public abstract class CameraExtensionSession implements java.lang.AutoCloseable {

    public static abstract class ExtensionCaptureCallback {
        public void onCaptureStarted(android.hardware.camera2.CameraExtensionSession cameraExtensionSession, android.hardware.camera2.CaptureRequest captureRequest, long j) {
        }

        public void onCaptureProcessStarted(android.hardware.camera2.CameraExtensionSession cameraExtensionSession, android.hardware.camera2.CaptureRequest captureRequest) {
        }

        public void onCaptureFailed(android.hardware.camera2.CameraExtensionSession cameraExtensionSession, android.hardware.camera2.CaptureRequest captureRequest) {
        }

        public void onCaptureFailed(android.hardware.camera2.CameraExtensionSession cameraExtensionSession, android.hardware.camera2.CaptureRequest captureRequest, int i) {
        }

        public void onCaptureSequenceCompleted(android.hardware.camera2.CameraExtensionSession cameraExtensionSession, int i) {
        }

        public void onCaptureSequenceAborted(android.hardware.camera2.CameraExtensionSession cameraExtensionSession, int i) {
        }

        public void onCaptureResultAvailable(android.hardware.camera2.CameraExtensionSession cameraExtensionSession, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.TotalCaptureResult totalCaptureResult) {
        }

        public void onCaptureProcessProgressed(android.hardware.camera2.CameraExtensionSession cameraExtensionSession, android.hardware.camera2.CaptureRequest captureRequest, int i) {
        }
    }

    public static abstract class StateCallback {
        public abstract void onConfigureFailed(android.hardware.camera2.CameraExtensionSession cameraExtensionSession);

        public abstract void onConfigured(android.hardware.camera2.CameraExtensionSession cameraExtensionSession);

        public void onClosed(android.hardware.camera2.CameraExtensionSession cameraExtensionSession) {
        }
    }

    public android.hardware.camera2.CameraDevice getDevice() {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    public int capture(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    public int setRepeatingRequest(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    public void stopRepeating() throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    public static final class StillCaptureLatency {
        private final long mCaptureLatency;
        private final long mProcessingLatency;

        public StillCaptureLatency(long j, long j2) {
            this.mCaptureLatency = j;
            this.mProcessingLatency = j2;
        }

        public long getCaptureLatency() {
            return this.mCaptureLatency;
        }

        public long getProcessingLatency() {
            return this.mProcessingLatency;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.hardware.camera2.CameraExtensionSession.StillCaptureLatency stillCaptureLatency = (android.hardware.camera2.CameraExtensionSession.StillCaptureLatency) obj;
            if (this.mCaptureLatency == stillCaptureLatency.mCaptureLatency && this.mProcessingLatency == stillCaptureLatency.mProcessingLatency) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mCaptureLatency, this.mProcessingLatency);
        }

        public java.lang.String toString() {
            return "StillCaptureLatency(processingLatency:" + this.mProcessingLatency + ", captureLatency: " + this.mCaptureLatency + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public android.hardware.camera2.CameraExtensionSession.StillCaptureLatency getRealtimeStillCaptureLatency() throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }

    @Override // java.lang.AutoCloseable
    public void close() throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Subclasses must override this method");
    }
}
