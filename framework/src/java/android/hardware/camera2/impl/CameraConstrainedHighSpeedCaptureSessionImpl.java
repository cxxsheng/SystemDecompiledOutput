package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class CameraConstrainedHighSpeedCaptureSessionImpl extends android.hardware.camera2.CameraConstrainedHighSpeedCaptureSession implements android.hardware.camera2.impl.CameraCaptureSessionCore {
    private final android.hardware.camera2.CameraCharacteristics mCharacteristics;
    private final android.hardware.camera2.impl.CameraCaptureSessionImpl mSessionImpl;
    private final android.os.ConditionVariable mInitialized = new android.os.ConditionVariable();
    private final java.lang.String TAG = "CameraConstrainedHighSpeedCaptureSessionImpl";

    CameraConstrainedHighSpeedCaptureSessionImpl(int i, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, java.util.concurrent.Executor executor, android.hardware.camera2.impl.CameraDeviceImpl cameraDeviceImpl, java.util.concurrent.Executor executor2, boolean z, android.hardware.camera2.CameraCharacteristics cameraCharacteristics) {
        this.mCharacteristics = cameraCharacteristics;
        this.mSessionImpl = new android.hardware.camera2.impl.CameraCaptureSessionImpl(i, null, new android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl.WrapperCallback(stateCallback), executor, cameraDeviceImpl, executor2, z);
        this.mInitialized.open();
    }

    @Override // android.hardware.camera2.CameraConstrainedHighSpeedCaptureSession
    public java.util.List<android.hardware.camera2.CaptureRequest> createHighSpeedRequestList(android.hardware.camera2.CaptureRequest captureRequest) throws android.hardware.camera2.CameraAccessException {
        android.hardware.camera2.CaptureRequest.Builder builder;
        if (captureRequest == null) {
            throw new java.lang.IllegalArgumentException("Input capture request must not be null");
        }
        android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationMap> key = android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP;
        java.lang.Integer num = (java.lang.Integer) captureRequest.get(android.hardware.camera2.CaptureRequest.SENSOR_PIXEL_MODE);
        if (num != null && num.intValue() == 1) {
            key = android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP_MAXIMUM_RESOLUTION;
        }
        java.util.Collection<android.view.Surface> targets = captureRequest.getTargets();
        android.util.Range range = (android.util.Range) captureRequest.get(android.hardware.camera2.CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
        android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap = (android.hardware.camera2.params.StreamConfigurationMap) this.mCharacteristics.get(key);
        android.hardware.camera2.utils.SurfaceUtils.checkConstrainedHighSpeedSurfaces(targets, range, streamConfigurationMap);
        android.util.Range<java.lang.Integer>[] highSpeedVideoFpsRangesFor = streamConfigurationMap.getHighSpeedVideoFpsRangesFor(android.hardware.camera2.utils.SurfaceUtils.getSurfaceSize(targets.iterator().next()));
        android.util.Log.v("CameraConstrainedHighSpeedCaptureSessionImpl", "High speed fps ranges: " + java.util.Arrays.toString(highSpeedVideoFpsRangesFor));
        int i = Integer.MAX_VALUE;
        for (android.util.Range<java.lang.Integer> range2 : highSpeedVideoFpsRangesFor) {
            int intValue = range2.getLower().intValue();
            if (i > intValue) {
                i = intValue;
            }
        }
        if (i != 60 && i != 30) {
            android.util.Log.w("CameraConstrainedHighSpeedCaptureSessionImpl", "previewFps is neither 60 nor 30.");
            i = 30;
        }
        android.util.Log.v("CameraConstrainedHighSpeedCaptureSessionImpl", "previewFps: " + i);
        int intValue2 = ((java.lang.Integer) range.getUpper()).intValue() / i;
        if (((java.lang.Integer) range.getUpper()).intValue() > ((java.lang.Integer) range.getLower()).intValue()) {
            intValue2 = 1;
        }
        android.util.Log.v("CameraConstrainedHighSpeedCaptureSessionImpl", "Request list size is: " + intValue2);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.hardware.camera2.CaptureRequest.Builder builder2 = new android.hardware.camera2.CaptureRequest.Builder(new android.hardware.camera2.impl.CameraMetadataNative(captureRequest.getNativeCopy()), false, -1, captureRequest.getLogicalCameraId(), null);
        builder2.setTag(captureRequest.getTag());
        java.util.Iterator<android.view.Surface> it = targets.iterator();
        android.view.Surface next = it.next();
        if (targets.size() == 1 && android.hardware.camera2.utils.SurfaceUtils.isSurfaceForHwVideoEncoder(next)) {
            builder2.set(android.hardware.camera2.CaptureRequest.CONTROL_CAPTURE_INTENT, 1);
        } else {
            builder2.set(android.hardware.camera2.CaptureRequest.CONTROL_CAPTURE_INTENT, 3);
        }
        builder2.setPartOfCHSRequestList(true);
        if (targets.size() == 2) {
            builder = new android.hardware.camera2.CaptureRequest.Builder(new android.hardware.camera2.impl.CameraMetadataNative(captureRequest.getNativeCopy()), false, -1, captureRequest.getLogicalCameraId(), null);
            builder.setTag(captureRequest.getTag());
            builder.set(android.hardware.camera2.CaptureRequest.CONTROL_CAPTURE_INTENT, 3);
            builder.addTarget(next);
            android.view.Surface next2 = it.next();
            builder.addTarget(next2);
            builder.setPartOfCHSRequestList(true);
            if (!android.hardware.camera2.utils.SurfaceUtils.isSurfaceForHwVideoEncoder(next)) {
                next = next2;
            }
            builder2.addTarget(next);
        } else {
            builder2.addTarget(next);
            builder = null;
        }
        for (int i2 = 0; i2 < intValue2; i2++) {
            if (i2 == 0 && builder != null) {
                arrayList.add(builder.build());
            } else {
                arrayList.add(builder2.build());
            }
        }
        return java.util.Collections.unmodifiableList(arrayList);
    }

    private boolean isConstrainedHighSpeedRequestList(java.util.List<android.hardware.camera2.CaptureRequest> list) {
        com.android.internal.util.Preconditions.checkCollectionNotEmpty(list, "High speed request list");
        java.util.Iterator<android.hardware.camera2.CaptureRequest> it = list.iterator();
        while (it.hasNext()) {
            if (!it.next().isPartOfCRequestList()) {
                return false;
            }
        }
        return true;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public android.hardware.camera2.CameraDevice getDevice() {
        return this.mSessionImpl.getDevice();
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void prepare(android.view.Surface surface) throws android.hardware.camera2.CameraAccessException {
        this.mSessionImpl.prepare(surface);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void prepare(int i, android.view.Surface surface) throws android.hardware.camera2.CameraAccessException {
        this.mSessionImpl.prepare(i, surface);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void tearDown(android.view.Surface surface) throws android.hardware.camera2.CameraAccessException {
        this.mSessionImpl.tearDown(surface);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int capture(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureSingleRequest(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureBurst(java.util.List<android.hardware.camera2.CaptureRequest> list, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        if (!isConstrainedHighSpeedRequestList(list)) {
            throw new java.lang.IllegalArgumentException("Only request lists created by createHighSpeedRequestList() can be submitted to a constrained high speed capture session");
        }
        return this.mSessionImpl.captureBurst(list, captureCallback, handler);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureBurstRequests(java.util.List<android.hardware.camera2.CaptureRequest> list, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        if (!isConstrainedHighSpeedRequestList(list)) {
            throw new java.lang.IllegalArgumentException("Only request lists created by createHighSpeedRequestList() can be submitted to a constrained high speed capture session");
        }
        return this.mSessionImpl.captureBurstRequests(list, executor, captureCallback);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingRequest(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setSingleRepeatingRequest(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingBurst(java.util.List<android.hardware.camera2.CaptureRequest> list, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        if (!isConstrainedHighSpeedRequestList(list)) {
            throw new java.lang.IllegalArgumentException("Only request lists created by createHighSpeedRequestList() can be submitted to a constrained high speed capture session");
        }
        return this.mSessionImpl.setRepeatingBurst(list, captureCallback, handler);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingBurstRequests(java.util.List<android.hardware.camera2.CaptureRequest> list, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        if (!isConstrainedHighSpeedRequestList(list)) {
            throw new java.lang.IllegalArgumentException("Only request lists created by createHighSpeedRequestList() can be submitted to a constrained high speed capture session");
        }
        return this.mSessionImpl.setRepeatingBurstRequests(list, executor, captureCallback);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void stopRepeating() throws android.hardware.camera2.CameraAccessException {
        this.mSessionImpl.stopRepeating();
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void abortCaptures() throws android.hardware.camera2.CameraAccessException {
        this.mSessionImpl.abortCaptures();
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public android.view.Surface getInputSurface() {
        return null;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void updateOutputConfiguration(android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public android.hardware.camera2.CameraOfflineSession switchToOffline(java.util.Collection<android.view.Surface> collection, java.util.concurrent.Executor executor, android.hardware.camera2.CameraOfflineSession.CameraOfflineSessionCallback cameraOfflineSessionCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public boolean supportsOfflineProcessing(android.view.Surface surface) {
        throw new java.lang.UnsupportedOperationException("Constrained high speed session doesn't support offline mode");
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public void closeWithoutDraining() {
        throw new java.lang.UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override // android.hardware.camera2.CameraCaptureSession, java.lang.AutoCloseable
    public void close() {
        this.mSessionImpl.close();
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public boolean isReprocessable() {
        return false;
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public void replaceSessionClose() {
        this.mSessionImpl.replaceSessionClose();
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK getDeviceStateCallback() {
        return this.mSessionImpl.getDeviceStateCallback();
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public boolean isAborting() {
        return this.mSessionImpl.isAborting();
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void finalizeOutputConfigurations(java.util.List<android.hardware.camera2.params.OutputConfiguration> list) throws android.hardware.camera2.CameraAccessException {
        this.mSessionImpl.finalizeOutputConfigurations(list);
    }

    private class WrapperCallback extends android.hardware.camera2.CameraCaptureSession.StateCallback {
        private final android.hardware.camera2.CameraCaptureSession.StateCallback mCallback;

        public WrapperCallback(android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback) {
            this.mCallback = stateCallback;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl.this.mInitialized.block();
            this.mCallback.onConfigured(android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl.this.mInitialized.block();
            this.mCallback.onConfigureFailed(android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onReady(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onReady(android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onActive(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onActive(android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onCaptureQueueEmpty(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onCaptureQueueEmpty(android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onClosed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onClosed(android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onSurfacePrepared(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.view.Surface surface) {
            this.mCallback.onSurfacePrepared(android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl.this, surface);
        }
    }
}
