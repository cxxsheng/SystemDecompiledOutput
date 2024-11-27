package android.hardware.camera2.extension;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public abstract class SessionProcessor {
    private static final java.lang.String TAG = "SessionProcessor";
    private android.hardware.camera2.extension.CameraUsageTracker mCameraUsageTracker;

    @android.annotation.SystemApi
    public interface CaptureCallback {
        void onCaptureCompleted(long j, int i, java.util.Map<android.hardware.camera2.CaptureResult.Key, java.lang.Object> map);

        void onCaptureFailed(int i, int i2);

        void onCaptureProcessStarted(int i);

        void onCaptureSequenceAborted(int i);

        void onCaptureSequenceCompleted(int i);

        void onCaptureStarted(int i, long j);
    }

    public abstract void deInitSession(android.os.IBinder iBinder);

    public abstract android.hardware.camera2.extension.ExtensionConfiguration initSession(android.os.IBinder iBinder, java.lang.String str, android.hardware.camera2.extension.CharacteristicsMap characteristicsMap, android.hardware.camera2.extension.CameraOutputSurface cameraOutputSurface, android.hardware.camera2.extension.CameraOutputSurface cameraOutputSurface2);

    public abstract void onCaptureSessionEnd();

    public abstract void onCaptureSessionStart(android.hardware.camera2.extension.RequestProcessor requestProcessor, java.lang.String str);

    public abstract void setParameters(android.hardware.camera2.CaptureRequest captureRequest);

    public abstract int startMultiFrameCapture(java.util.concurrent.Executor executor, android.hardware.camera2.extension.SessionProcessor.CaptureCallback captureCallback);

    public abstract int startRepeating(java.util.concurrent.Executor executor, android.hardware.camera2.extension.SessionProcessor.CaptureCallback captureCallback);

    public abstract int startTrigger(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.extension.SessionProcessor.CaptureCallback captureCallback);

    public abstract void stopRepeating();

    void setCameraUsageTracker(android.hardware.camera2.extension.CameraUsageTracker cameraUsageTracker) {
        this.mCameraUsageTracker = cameraUsageTracker;
    }

    private final class SessionProcessorImpl extends android.hardware.camera2.extension.ISessionProcessorImpl.Stub {
        private long mVendorId;

        private SessionProcessorImpl() {
            this.mVendorId = -1L;
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public android.hardware.camera2.extension.CameraSessionConfig initSession(android.os.IBinder iBinder, java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map, android.hardware.camera2.extension.OutputSurface outputSurface, android.hardware.camera2.extension.OutputSurface outputSurface2, android.hardware.camera2.extension.OutputSurface outputSurface3) throws android.os.RemoteException {
            android.hardware.camera2.extension.ExtensionConfiguration initSession = android.hardware.camera2.extension.SessionProcessor.this.initSession(iBinder, str, new android.hardware.camera2.extension.CharacteristicsMap(map), new android.hardware.camera2.extension.CameraOutputSurface(outputSurface), new android.hardware.camera2.extension.CameraOutputSurface(outputSurface2));
            if (initSession == null) {
                throw new java.lang.IllegalArgumentException("Invalid extension configuration");
            }
            java.util.ArrayList allVendorKeys = map.get(str).getAllVendorKeys(android.hardware.camera2.CameraCharacteristics.Key.class);
            if (allVendorKeys != null && !allVendorKeys.isEmpty()) {
                this.mVendorId = ((android.hardware.camera2.CameraCharacteristics.Key) allVendorKeys.get(0)).getVendorId();
            }
            return initSession.getCameraSessionConfig();
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void deInitSession(android.os.IBinder iBinder) throws android.os.RemoteException {
            android.hardware.camera2.extension.SessionProcessor.this.deInitSession(iBinder);
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void onCaptureSessionStart(android.hardware.camera2.extension.IRequestProcessorImpl iRequestProcessorImpl, java.lang.String str) throws android.os.RemoteException {
            if (android.hardware.camera2.extension.SessionProcessor.this.mCameraUsageTracker != null) {
                android.hardware.camera2.extension.SessionProcessor.this.mCameraUsageTracker.startCameraOperation();
            }
            android.hardware.camera2.extension.SessionProcessor.this.onCaptureSessionStart(new android.hardware.camera2.extension.RequestProcessor(iRequestProcessorImpl, this.mVendorId), str);
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void onCaptureSessionEnd() throws android.os.RemoteException {
            if (android.hardware.camera2.extension.SessionProcessor.this.mCameraUsageTracker != null) {
                android.hardware.camera2.extension.SessionProcessor.this.mCameraUsageTracker.finishCameraOperation();
            }
            android.hardware.camera2.extension.SessionProcessor.this.onCaptureSessionEnd();
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startRepeating(android.hardware.camera2.extension.ICaptureCallback iCaptureCallback) throws android.os.RemoteException {
            return android.hardware.camera2.extension.SessionProcessor.this.startRepeating(new android.hardware.camera2.impl.CameraExtensionUtils.HandlerExecutor(new android.os.Handler(android.os.Looper.getMainLooper())), new android.hardware.camera2.extension.SessionProcessor.CaptureCallbackImpl(iCaptureCallback, this.mVendorId));
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void stopRepeating() throws android.os.RemoteException {
            android.hardware.camera2.extension.SessionProcessor.this.stopRepeating();
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startCapture(android.hardware.camera2.extension.ICaptureCallback iCaptureCallback, boolean z) throws android.os.RemoteException {
            return android.hardware.camera2.extension.SessionProcessor.this.startMultiFrameCapture(new android.hardware.camera2.impl.CameraExtensionUtils.HandlerExecutor(new android.os.Handler(android.os.Looper.getMainLooper())), new android.hardware.camera2.extension.SessionProcessor.CaptureCallbackImpl(iCaptureCallback, this.mVendorId));
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void setParameters(android.hardware.camera2.CaptureRequest captureRequest) throws android.os.RemoteException {
            android.hardware.camera2.extension.SessionProcessor.this.setParameters(captureRequest);
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startTrigger(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.extension.ICaptureCallback iCaptureCallback) throws android.os.RemoteException {
            return android.hardware.camera2.extension.SessionProcessor.this.startTrigger(captureRequest, new android.hardware.camera2.impl.CameraExtensionUtils.HandlerExecutor(new android.os.Handler(android.os.Looper.getMainLooper())), new android.hardware.camera2.extension.SessionProcessor.CaptureCallbackImpl(iCaptureCallback, this.mVendorId));
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public android.hardware.camera2.extension.LatencyPair getRealtimeCaptureLatency() throws android.os.RemoteException {
            return null;
        }
    }

    private static final class CaptureCallbackImpl implements android.hardware.camera2.extension.SessionProcessor.CaptureCallback {
        private final android.hardware.camera2.extension.ICaptureCallback mCaptureCallback;
        private long mVendorId;

        CaptureCallbackImpl(android.hardware.camera2.extension.ICaptureCallback iCaptureCallback, long j) {
            this.mVendorId = -1L;
            this.mCaptureCallback = iCaptureCallback;
            this.mVendorId = j;
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureStarted(int i, long j) {
            try {
                this.mCaptureCallback.onCaptureStarted(i, j);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.hardware.camera2.extension.SessionProcessor.TAG, "Failed to notify capture start due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureProcessStarted(int i) {
            try {
                this.mCaptureCallback.onCaptureProcessStarted(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.hardware.camera2.extension.SessionProcessor.TAG, "Failed to notify process start due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureFailed(int i, int i2) {
            try {
                this.mCaptureCallback.onCaptureProcessFailed(i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.hardware.camera2.extension.SessionProcessor.TAG, "Failed to notify capture failure start due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureSequenceCompleted(int i) {
            try {
                this.mCaptureCallback.onCaptureSequenceCompleted(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.hardware.camera2.extension.SessionProcessor.TAG, "Failed to notify capture sequence done due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureSequenceAborted(int i) {
            try {
                this.mCaptureCallback.onCaptureSequenceAborted(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.hardware.camera2.extension.SessionProcessor.TAG, "Failed to notify capture sequence abort due to remote exception!");
            }
        }

        @Override // android.hardware.camera2.extension.SessionProcessor.CaptureCallback
        public void onCaptureCompleted(long j, int i, java.util.Map<android.hardware.camera2.CaptureResult.Key, java.lang.Object> map) {
            android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative = new android.hardware.camera2.impl.CameraMetadataNative();
            cameraMetadataNative.setVendorId(this.mVendorId);
            for (java.util.Map.Entry<android.hardware.camera2.CaptureResult.Key, java.lang.Object> entry : map.entrySet()) {
                cameraMetadataNative.set((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key>) entry.getKey(), (android.hardware.camera2.CaptureResult.Key) entry.getValue());
            }
            try {
                this.mCaptureCallback.onCaptureCompleted(j, i, cameraMetadataNative);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.hardware.camera2.extension.SessionProcessor.TAG, "Failed to notify capture complete due to remote exception!");
            }
        }
    }

    android.hardware.camera2.extension.ISessionProcessorImpl getSessionProcessorBinder() {
        return new android.hardware.camera2.extension.SessionProcessor.SessionProcessorImpl();
    }
}
