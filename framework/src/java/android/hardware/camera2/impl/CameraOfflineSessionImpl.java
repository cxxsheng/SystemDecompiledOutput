package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class CameraOfflineSessionImpl extends android.hardware.camera2.CameraOfflineSession implements android.os.IBinder.DeathRecipient {
    private static final long NANO_PER_SECOND = 1000000000;
    private static final int REQUEST_ID_NONE = -1;
    private static final java.lang.String TAG = "CameraOfflineSessionImpl";
    private final java.lang.String mCameraId;
    private android.util.SparseArray<android.hardware.camera2.impl.CaptureCallbackHolder> mCaptureCallbackMap;
    private final android.hardware.camera2.CameraCharacteristics mCharacteristics;
    private android.util.SparseArray<android.hardware.camera2.params.OutputConfiguration> mConfiguredOutputs;
    private android.hardware.camera2.impl.FrameNumberTracker mFrameNumberTracker;
    private final android.hardware.camera2.CameraOfflineSession.CameraOfflineSessionCallback mOfflineCallback;
    private final java.util.concurrent.Executor mOfflineExecutor;
    private java.util.AbstractMap.SimpleEntry<java.lang.Integer, android.hardware.camera2.params.InputConfiguration> mOfflineInput;
    private android.util.SparseArray<android.hardware.camera2.params.OutputConfiguration> mOfflineOutputs;
    private android.hardware.camera2.ICameraOfflineSession mRemoteSession;
    private final int mTotalPartialCount;
    private final boolean DEBUG = false;
    private final java.util.concurrent.atomic.AtomicBoolean mClosing = new java.util.concurrent.atomic.AtomicBoolean();
    final java.lang.Object mInterfaceLock = new java.lang.Object();
    private final android.hardware.camera2.impl.CameraOfflineSessionImpl.CameraDeviceCallbacks mCallbacks = new android.hardware.camera2.impl.CameraOfflineSessionImpl.CameraDeviceCallbacks();
    private java.util.List<android.hardware.camera2.impl.RequestLastFrameNumbersHolder> mOfflineRequestLastFrameNumbersList = new java.util.ArrayList();

    public CameraOfflineSessionImpl(java.lang.String str, android.hardware.camera2.CameraCharacteristics cameraCharacteristics, java.util.concurrent.Executor executor, android.hardware.camera2.CameraOfflineSession.CameraOfflineSessionCallback cameraOfflineSessionCallback, android.util.SparseArray<android.hardware.camera2.params.OutputConfiguration> sparseArray, java.util.AbstractMap.SimpleEntry<java.lang.Integer, android.hardware.camera2.params.InputConfiguration> simpleEntry, android.util.SparseArray<android.hardware.camera2.params.OutputConfiguration> sparseArray2, android.hardware.camera2.impl.FrameNumberTracker frameNumberTracker, android.util.SparseArray<android.hardware.camera2.impl.CaptureCallbackHolder> sparseArray3, java.util.List<android.hardware.camera2.impl.RequestLastFrameNumbersHolder> list) {
        this.mOfflineInput = new java.util.AbstractMap.SimpleEntry<>(-1, null);
        this.mOfflineOutputs = new android.util.SparseArray<>();
        this.mConfiguredOutputs = new android.util.SparseArray<>();
        this.mFrameNumberTracker = new android.hardware.camera2.impl.FrameNumberTracker();
        this.mCaptureCallbackMap = new android.util.SparseArray<>();
        if (str == null || cameraCharacteristics == null) {
            throw new java.lang.IllegalArgumentException("Null argument given");
        }
        this.mCameraId = str;
        this.mCharacteristics = cameraCharacteristics;
        java.lang.Integer num = (java.lang.Integer) this.mCharacteristics.get(android.hardware.camera2.CameraCharacteristics.REQUEST_PARTIAL_RESULT_COUNT);
        if (num == null) {
            this.mTotalPartialCount = 1;
        } else {
            this.mTotalPartialCount = num.intValue();
        }
        this.mOfflineRequestLastFrameNumbersList.addAll(list);
        this.mFrameNumberTracker = frameNumberTracker;
        this.mCaptureCallbackMap = sparseArray3;
        this.mConfiguredOutputs = sparseArray2;
        this.mOfflineOutputs = sparseArray;
        this.mOfflineInput = simpleEntry;
        this.mOfflineExecutor = (java.util.concurrent.Executor) com.android.internal.util.Preconditions.checkNotNull(executor, "offline executor must not be null");
        this.mOfflineCallback = (android.hardware.camera2.CameraOfflineSession.CameraOfflineSessionCallback) com.android.internal.util.Preconditions.checkNotNull(cameraOfflineSessionCallback, "offline callback must not be null");
    }

    public android.hardware.camera2.impl.CameraOfflineSessionImpl.CameraDeviceCallbacks getCallbacks() {
        return this.mCallbacks;
    }

    public class CameraDeviceCallbacks extends android.hardware.camera2.ICameraDeviceCallbacks.Stub {
        public CameraDeviceCallbacks() {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks.Stub, android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onDeviceError(int i, android.hardware.camera2.impl.CaptureResultExtras captureResultExtras) {
            synchronized (android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mInterfaceLock) {
                try {
                    switch (i) {
                        case 3:
                        case 4:
                        case 5:
                            onCaptureErrorLocked(i, captureResultExtras);
                            break;
                        default:
                            java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraOfflineSessionImpl.CameraDeviceCallbacks.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (!android.hardware.camera2.impl.CameraOfflineSessionImpl.this.isClosed()) {
                                        android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mOfflineCallback.onError(android.hardware.camera2.impl.CameraOfflineSessionImpl.this, 0);
                                    }
                                }
                            };
                            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mOfflineExecutor.execute(runnable);
                                break;
                            } finally {
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
                throw th;
            }
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onRepeatingRequestError(long j, int i) {
            android.util.Log.e(android.hardware.camera2.impl.CameraOfflineSessionImpl.TAG, "Unexpected repeating request error received. Last frame number is " + j);
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onDeviceIdle() {
            synchronized (android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mInterfaceLock) {
                if (android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mRemoteSession == null) {
                    android.util.Log.v(android.hardware.camera2.impl.CameraOfflineSessionImpl.TAG, "Ignoring idle state notifications during offline switches");
                    return;
                }
                android.hardware.camera2.impl.CameraOfflineSessionImpl.this.removeCompletedCallbackHolderLocked(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE);
                java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraOfflineSessionImpl.CameraDeviceCallbacks.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!android.hardware.camera2.impl.CameraOfflineSessionImpl.this.isClosed()) {
                            android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mOfflineCallback.onIdle(android.hardware.camera2.impl.CameraOfflineSessionImpl.this);
                        }
                    }
                };
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mOfflineExecutor.execute(runnable);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onCaptureStarted(final android.hardware.camera2.impl.CaptureResultExtras captureResultExtras, final long j) {
            java.lang.Object obj;
            int requestId = captureResultExtras.getRequestId();
            final long frameNumber = captureResultExtras.getFrameNumber();
            long lastCompletedRegularFrameNumber = captureResultExtras.getLastCompletedRegularFrameNumber();
            long lastCompletedReprocessFrameNumber = captureResultExtras.getLastCompletedReprocessFrameNumber();
            long lastCompletedZslFrameNumber = captureResultExtras.getLastCompletedZslFrameNumber();
            java.lang.Object obj2 = android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mInterfaceLock;
            synchronized (obj2) {
                try {
                    try {
                        android.hardware.camera2.impl.CameraOfflineSessionImpl.this.removeCompletedCallbackHolderLocked(lastCompletedRegularFrameNumber, lastCompletedReprocessFrameNumber, lastCompletedZslFrameNumber);
                        final android.hardware.camera2.impl.CaptureCallbackHolder captureCallbackHolder = (android.hardware.camera2.impl.CaptureCallbackHolder) android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mCaptureCallbackMap.get(requestId);
                        if (captureCallbackHolder == null) {
                            return;
                        }
                        java.util.concurrent.Executor executor = captureCallbackHolder.getCallback().getExecutor();
                        if (android.hardware.camera2.impl.CameraOfflineSessionImpl.this.isClosed()) {
                            obj = obj2;
                        } else {
                            if (executor != null) {
                                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                                try {
                                } catch (java.lang.Throwable th) {
                                    th = th;
                                }
                                try {
                                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraOfflineSessionImpl.CameraDeviceCallbacks.3
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            android.hardware.camera2.CameraCaptureSession.CaptureCallback sessionCallback = captureCallbackHolder.getCallback().getSessionCallback();
                                            if (!android.hardware.camera2.impl.CameraOfflineSessionImpl.this.isClosed() && sessionCallback != null) {
                                                int subsequenceId = captureResultExtras.getSubsequenceId();
                                                android.hardware.camera2.CaptureRequest request = captureCallbackHolder.getRequest(subsequenceId);
                                                if (captureCallbackHolder.hasBatchedOutputs()) {
                                                    android.util.Range range = (android.util.Range) request.get(android.hardware.camera2.CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
                                                    for (int i = 0; i < captureCallbackHolder.getRequestCount(); i++) {
                                                        long j2 = subsequenceId - i;
                                                        sessionCallback.onCaptureStarted(android.hardware.camera2.impl.CameraOfflineSessionImpl.this, captureCallbackHolder.getRequest(i), j - ((1000000000 * j2) / ((java.lang.Integer) range.getUpper()).intValue()), frameNumber - j2);
                                                    }
                                                    return;
                                                }
                                                sessionCallback.onCaptureStarted(android.hardware.camera2.impl.CameraOfflineSessionImpl.this, captureCallbackHolder.getRequest(captureResultExtras.getSubsequenceId()), j, frameNumber);
                                            }
                                        }
                                    });
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return;
                                } catch (java.lang.Throwable th2) {
                                    th = th2;
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    throw th;
                                }
                            }
                            obj = obj2;
                        }
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        throw th;
                    }
                } catch (java.lang.Throwable th4) {
                    th = th4;
                    throw th;
                }
            }
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onResultReceived(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, final android.hardware.camera2.impl.CaptureResultExtras captureResultExtras, android.hardware.camera2.impl.PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) throws android.os.RemoteException {
            android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative2;
            long j;
            java.util.concurrent.Executor executor;
            android.hardware.camera2.CaptureResult captureResult;
            java.lang.Runnable runnable;
            int requestId = captureResultExtras.getRequestId();
            long frameNumber = captureResultExtras.getFrameNumber();
            synchronized (android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mInterfaceLock) {
                cameraMetadataNative.set((android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.CameraCharacteristics.Key<android.util.Size>>) android.hardware.camera2.CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE, (android.hardware.camera2.CameraCharacteristics.Key<android.util.Size>) android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mCharacteristics.get(android.hardware.camera2.CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE));
                final android.hardware.camera2.impl.CaptureCallbackHolder captureCallbackHolder = (android.hardware.camera2.impl.CaptureCallbackHolder) android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mCaptureCallbackMap.get(requestId);
                final android.hardware.camera2.CaptureRequest request = captureCallbackHolder.getRequest(captureResultExtras.getSubsequenceId());
                boolean z = captureResultExtras.getPartialResultCount() < android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mTotalPartialCount;
                int requestType = request.getRequestType();
                if (captureCallbackHolder == null) {
                    android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mFrameNumberTracker.updateTracker(frameNumber, null, z, requestType);
                    return;
                }
                if (android.hardware.camera2.impl.CameraOfflineSessionImpl.this.isClosed()) {
                    android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mFrameNumberTracker.updateTracker(frameNumber, null, z, requestType);
                    return;
                }
                if (captureCallbackHolder.hasBatchedOutputs()) {
                    cameraMetadataNative2 = new android.hardware.camera2.impl.CameraMetadataNative(cameraMetadataNative);
                } else {
                    cameraMetadataNative2 = null;
                }
                java.util.concurrent.Executor executor2 = captureCallbackHolder.getCallback().getExecutor();
                if (z) {
                    final android.hardware.camera2.CaptureResult captureResult2 = new android.hardware.camera2.CaptureResult(android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mCameraId, cameraMetadataNative, request, captureResultExtras);
                    final android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative3 = cameraMetadataNative2;
                    captureResult = captureResult2;
                    runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraOfflineSessionImpl.CameraDeviceCallbacks.4
                        @Override // java.lang.Runnable
                        public void run() {
                            android.hardware.camera2.CameraCaptureSession.CaptureCallback sessionCallback = captureCallbackHolder.getCallback().getSessionCallback();
                            if (!android.hardware.camera2.impl.CameraOfflineSessionImpl.this.isClosed() && sessionCallback != null) {
                                if (captureCallbackHolder.hasBatchedOutputs()) {
                                    for (int i = 0; i < captureCallbackHolder.getRequestCount(); i++) {
                                        android.hardware.camera2.CaptureResult captureResult3 = new android.hardware.camera2.CaptureResult(android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mCameraId, new android.hardware.camera2.impl.CameraMetadataNative(cameraMetadataNative3), captureCallbackHolder.getRequest(i), captureResultExtras);
                                        sessionCallback.onCaptureProgressed(android.hardware.camera2.impl.CameraOfflineSessionImpl.this, captureCallbackHolder.getRequest(i), captureResult3);
                                    }
                                    return;
                                }
                                sessionCallback.onCaptureProgressed(android.hardware.camera2.impl.CameraOfflineSessionImpl.this, request, captureResult2);
                            }
                        }
                    };
                    j = frameNumber;
                    executor = executor2;
                } else {
                    final java.util.List<android.hardware.camera2.CaptureResult> popPartialResults = android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mFrameNumberTracker.popPartialResults(frameNumber);
                    final long longValue = ((java.lang.Long) cameraMetadataNative.get(android.hardware.camera2.CaptureResult.SENSOR_TIMESTAMP)).longValue();
                    final android.util.Range range = (android.util.Range) request.get(android.hardware.camera2.CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
                    final int subsequenceId = captureResultExtras.getSubsequenceId();
                    final android.hardware.camera2.TotalCaptureResult totalCaptureResult = new android.hardware.camera2.TotalCaptureResult(android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mCameraId, cameraMetadataNative, request, captureResultExtras, popPartialResults, captureCallbackHolder.getSessionId(), physicalCaptureResultInfoArr);
                    final android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative4 = cameraMetadataNative2;
                    j = frameNumber;
                    executor = executor2;
                    captureResult = totalCaptureResult;
                    runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraOfflineSessionImpl.CameraDeviceCallbacks.5
                        @Override // java.lang.Runnable
                        public void run() {
                            android.hardware.camera2.CameraCaptureSession.CaptureCallback sessionCallback = captureCallbackHolder.getCallback().getSessionCallback();
                            if (!android.hardware.camera2.impl.CameraOfflineSessionImpl.this.isClosed() && sessionCallback != null) {
                                if (captureCallbackHolder.hasBatchedOutputs()) {
                                    for (int i = 0; i < captureCallbackHolder.getRequestCount(); i++) {
                                        cameraMetadataNative4.set((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key<java.lang.Long>>) android.hardware.camera2.CaptureResult.SENSOR_TIMESTAMP, (android.hardware.camera2.CaptureResult.Key<java.lang.Long>) java.lang.Long.valueOf(longValue - (((subsequenceId - i) * 1000000000) / ((java.lang.Integer) range.getUpper()).intValue())));
                                        sessionCallback.onCaptureCompleted(android.hardware.camera2.impl.CameraOfflineSessionImpl.this, captureCallbackHolder.getRequest(i), new android.hardware.camera2.TotalCaptureResult(android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mCameraId, new android.hardware.camera2.impl.CameraMetadataNative(cameraMetadataNative4), captureCallbackHolder.getRequest(i), captureResultExtras, popPartialResults, captureCallbackHolder.getSessionId(), new android.hardware.camera2.impl.PhysicalCaptureResultInfo[0]));
                                    }
                                    return;
                                }
                                sessionCallback.onCaptureCompleted(android.hardware.camera2.impl.CameraOfflineSessionImpl.this, request, totalCaptureResult);
                            }
                        }
                    };
                }
                if (executor != null) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        executor.execute(runnable);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mFrameNumberTracker.updateTracker(j, captureResult, z, requestType);
                if (!z) {
                    android.hardware.camera2.impl.CameraOfflineSessionImpl.this.checkAndFireSequenceComplete();
                }
            }
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onPrepared(int i) {
            android.util.Log.e(android.hardware.camera2.impl.CameraOfflineSessionImpl.TAG, "Unexpected stream " + i + " is prepared");
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onRequestQueueEmpty() {
            android.util.Log.v(android.hardware.camera2.impl.CameraOfflineSessionImpl.TAG, "onRequestQueueEmpty");
        }

        private void onCaptureErrorLocked(int i, android.hardware.camera2.impl.CaptureResultExtras captureResultExtras) {
            long clearCallingIdentity;
            android.hardware.camera2.params.OutputConfiguration outputConfiguration;
            int requestId = captureResultExtras.getRequestId();
            int subsequenceId = captureResultExtras.getSubsequenceId();
            final long frameNumber = captureResultExtras.getFrameNumber();
            java.lang.String errorPhysicalCameraId = captureResultExtras.getErrorPhysicalCameraId();
            final android.hardware.camera2.impl.CaptureCallbackHolder captureCallbackHolder = (android.hardware.camera2.impl.CaptureCallbackHolder) android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mCaptureCallbackMap.get(requestId);
            if (captureCallbackHolder == null) {
                android.util.Log.e(android.hardware.camera2.impl.CameraOfflineSessionImpl.TAG, java.lang.String.format("Receive capture error on unknown request ID %d", java.lang.Integer.valueOf(requestId)));
                return;
            }
            final android.hardware.camera2.CaptureRequest request = captureCallbackHolder.getRequest(subsequenceId);
            if (i == 5) {
                if (android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mRemoteSession == null && !android.hardware.camera2.impl.CameraOfflineSessionImpl.this.isClosed()) {
                    outputConfiguration = (android.hardware.camera2.params.OutputConfiguration) android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mConfiguredOutputs.get(captureResultExtras.getErrorStreamId());
                } else {
                    outputConfiguration = (android.hardware.camera2.params.OutputConfiguration) android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mOfflineOutputs.get(captureResultExtras.getErrorStreamId());
                }
                if (outputConfiguration == null) {
                    android.util.Log.v(android.hardware.camera2.impl.CameraOfflineSessionImpl.TAG, java.lang.String.format("Stream %d has been removed. Skipping buffer lost callback", java.lang.Integer.valueOf(captureResultExtras.getErrorStreamId())));
                    return;
                }
                for (final android.view.Surface surface : outputConfiguration.getSurfaces()) {
                    if (request.containsTarget(surface)) {
                        java.util.concurrent.Executor executor = captureCallbackHolder.getCallback().getExecutor();
                        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraOfflineSessionImpl.CameraDeviceCallbacks.6
                            @Override // java.lang.Runnable
                            public void run() {
                                android.hardware.camera2.CameraCaptureSession.CaptureCallback sessionCallback = captureCallbackHolder.getCallback().getSessionCallback();
                                if (!android.hardware.camera2.impl.CameraOfflineSessionImpl.this.isClosed() && sessionCallback != null) {
                                    sessionCallback.onCaptureBufferLost(android.hardware.camera2.impl.CameraOfflineSessionImpl.this, request, surface, frameNumber);
                                }
                            }
                        };
                        if (executor != null) {
                            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                executor.execute(runnable);
                            } finally {
                            }
                        } else {
                            continue;
                        }
                    }
                }
                return;
            }
            final android.hardware.camera2.CaptureFailure captureFailure = new android.hardware.camera2.CaptureFailure(request, 0, i == 4, requestId, frameNumber, errorPhysicalCameraId);
            java.util.concurrent.Executor executor2 = captureCallbackHolder.getCallback().getExecutor();
            java.lang.Runnable runnable2 = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraOfflineSessionImpl.CameraDeviceCallbacks.7
                @Override // java.lang.Runnable
                public void run() {
                    android.hardware.camera2.CameraCaptureSession.CaptureCallback sessionCallback = captureCallbackHolder.getCallback().getSessionCallback();
                    if (!android.hardware.camera2.impl.CameraOfflineSessionImpl.this.isClosed() && sessionCallback != null) {
                        sessionCallback.onCaptureFailed(android.hardware.camera2.impl.CameraOfflineSessionImpl.this, request, captureFailure);
                    }
                }
            };
            android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mFrameNumberTracker.updateTracker(frameNumber, true, request.getRequestType());
            android.hardware.camera2.impl.CameraOfflineSessionImpl.this.checkAndFireSequenceComplete();
            if (executor2 != null) {
                clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    executor2.execute(runnable2);
                } finally {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAndFireSequenceComplete() {
        final android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback;
        android.hardware.camera2.impl.CaptureCallbackHolder captureCallbackHolder;
        boolean z;
        java.util.concurrent.Executor executor;
        long completedFrameNumber = this.mFrameNumberTracker.getCompletedFrameNumber();
        long completedReprocessFrameNumber = this.mFrameNumberTracker.getCompletedReprocessFrameNumber();
        long completedZslStillFrameNumber = this.mFrameNumberTracker.getCompletedZslStillFrameNumber();
        java.util.Iterator<android.hardware.camera2.impl.RequestLastFrameNumbersHolder> it = this.mOfflineRequestLastFrameNumbersList.iterator();
        while (it.hasNext()) {
            final android.hardware.camera2.impl.RequestLastFrameNumbersHolder next = it.next();
            final int requestId = next.getRequestId();
            synchronized (this.mInterfaceLock) {
                int indexOfKey = this.mCaptureCallbackMap.indexOfKey(requestId);
                captureCallback = null;
                if (indexOfKey < 0) {
                    captureCallbackHolder = null;
                } else {
                    captureCallbackHolder = this.mCaptureCallbackMap.valueAt(indexOfKey);
                }
                z = false;
                if (captureCallbackHolder != null) {
                    long lastRegularFrameNumber = next.getLastRegularFrameNumber();
                    long lastReprocessFrameNumber = next.getLastReprocessFrameNumber();
                    long lastZslStillFrameNumber = next.getLastZslStillFrameNumber();
                    java.util.concurrent.Executor executor2 = captureCallbackHolder.getCallback().getExecutor();
                    android.hardware.camera2.CameraCaptureSession.CaptureCallback sessionCallback = captureCallbackHolder.getCallback().getSessionCallback();
                    if (lastRegularFrameNumber <= completedFrameNumber && lastReprocessFrameNumber <= completedReprocessFrameNumber && lastZslStillFrameNumber <= completedZslStillFrameNumber) {
                        this.mCaptureCallbackMap.removeAt(indexOfKey);
                        z = true;
                    }
                    executor = executor2;
                    captureCallback = sessionCallback;
                } else {
                    executor = null;
                }
            }
            if (captureCallbackHolder == null || z) {
                it.remove();
            }
            if (z && captureCallback != null && executor != null) {
                java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraOfflineSessionImpl.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!android.hardware.camera2.impl.CameraOfflineSessionImpl.this.isClosed()) {
                            captureCallback.onCaptureSequenceCompleted(android.hardware.camera2.impl.CameraOfflineSessionImpl.this, requestId, next.getLastFrameNumber());
                        }
                    }
                };
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    executor.execute(runnable);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (this.mCaptureCallbackMap.size() == 0) {
                        getCallbacks().onDeviceIdle();
                    }
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeCompletedCallbackHolderLocked(long j, long j2, long j3) {
        java.util.Iterator<android.hardware.camera2.impl.RequestLastFrameNumbersHolder> it = this.mOfflineRequestLastFrameNumbersList.iterator();
        while (it.hasNext()) {
            android.hardware.camera2.impl.RequestLastFrameNumbersHolder next = it.next();
            int requestId = next.getRequestId();
            int indexOfKey = this.mCaptureCallbackMap.indexOfKey(requestId);
            if ((indexOfKey >= 0 ? this.mCaptureCallbackMap.valueAt(indexOfKey) : null) != null) {
                long lastRegularFrameNumber = next.getLastRegularFrameNumber();
                long lastReprocessFrameNumber = next.getLastReprocessFrameNumber();
                long lastZslStillFrameNumber = next.getLastZslStillFrameNumber();
                if (lastRegularFrameNumber <= j && lastReprocessFrameNumber <= j2 && lastZslStillFrameNumber <= j3) {
                    if (next.isSequenceCompleted()) {
                        this.mCaptureCallbackMap.removeAt(indexOfKey);
                        it.remove();
                    } else {
                        android.util.Log.e(TAG, "Sequence not yet completed for request id " + requestId);
                    }
                }
            }
        }
    }

    public void notifyFailedSwitch() {
        synchronized (this.mInterfaceLock) {
            java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraOfflineSessionImpl.2
                @Override // java.lang.Runnable
                public void run() {
                    android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mOfflineCallback.onSwitchFailed(android.hardware.camera2.impl.CameraOfflineSessionImpl.this);
                }
            };
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mOfflineExecutor.execute(runnable);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void setRemoteSession(android.hardware.camera2.ICameraOfflineSession iCameraOfflineSession) throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (iCameraOfflineSession == null) {
                notifyFailedSwitch();
                return;
            }
            this.mRemoteSession = iCameraOfflineSession;
            android.os.IBinder asBinder = iCameraOfflineSession.asBinder();
            if (asBinder == null) {
                throw new android.hardware.camera2.CameraAccessException(2, "The camera offline session has encountered a serious error");
            }
            try {
                asBinder.linkToDeath(this, 0);
                java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraOfflineSessionImpl.3
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!android.hardware.camera2.impl.CameraOfflineSessionImpl.this.isClosed()) {
                            android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mOfflineCallback.onReady(android.hardware.camera2.impl.CameraOfflineSessionImpl.this);
                        }
                    }
                };
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mOfflineExecutor.execute(runnable);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (android.os.RemoteException e) {
                throw new android.hardware.camera2.CameraAccessException(2, "The camera offline session has encountered a serious error");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isClosed() {
        return this.mClosing.get();
    }

    private void disconnect() {
        synchronized (this.mInterfaceLock) {
            if (this.mClosing.getAndSet(true)) {
                return;
            }
            if (this.mRemoteSession != null) {
                this.mRemoteSession.asBinder().unlinkToDeath(this, 0);
                try {
                    this.mRemoteSession.disconnect();
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "Exception while disconnecting from offline session: ", e);
                }
                this.mRemoteSession = null;
                java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraOfflineSessionImpl.4
                    @Override // java.lang.Runnable
                    public void run() {
                        android.hardware.camera2.impl.CameraOfflineSessionImpl.this.mOfflineCallback.onClosed(android.hardware.camera2.impl.CameraOfflineSessionImpl.this);
                    }
                };
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mOfflineExecutor.execute(runnable);
                    return;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            throw new java.lang.IllegalStateException("Offline session is not yet ready");
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            disconnect();
        } finally {
            super.finalize();
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        android.util.Log.w(TAG, "CameraOfflineSession on device " + this.mCameraId + " died unexpectedly");
        disconnect();
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public android.hardware.camera2.CameraDevice getDevice() {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void prepare(android.view.Surface surface) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void prepare(int i, android.view.Surface surface) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void tearDown(android.view.Surface surface) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void finalizeOutputConfigurations(java.util.List<android.hardware.camera2.params.OutputConfiguration> list) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int capture(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureSingleRequest(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureBurst(java.util.List<android.hardware.camera2.CaptureRequest> list, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureBurstRequests(java.util.List<android.hardware.camera2.CaptureRequest> list, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingRequest(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setSingleRepeatingRequest(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingBurst(java.util.List<android.hardware.camera2.CaptureRequest> list, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingBurstRequests(java.util.List<android.hardware.camera2.CaptureRequest> list, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void stopRepeating() throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void abortCaptures() throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void updateOutputConfiguration(android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public boolean isReprocessable() {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public android.view.Surface getInputSurface() {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public android.hardware.camera2.CameraOfflineSession switchToOffline(java.util.Collection<android.view.Surface> collection, java.util.concurrent.Executor executor, android.hardware.camera2.CameraOfflineSession.CameraOfflineSessionCallback cameraOfflineSessionCallback) throws android.hardware.camera2.CameraAccessException {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public boolean supportsOfflineProcessing(android.view.Surface surface) {
        throw new java.lang.UnsupportedOperationException("Operation not supported in offline mode");
    }

    @Override // android.hardware.camera2.CameraOfflineSession, android.hardware.camera2.CameraCaptureSession, java.lang.AutoCloseable
    public void close() {
        disconnect();
    }
}
