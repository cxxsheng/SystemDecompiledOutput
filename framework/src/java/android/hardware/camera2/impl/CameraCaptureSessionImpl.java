package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class CameraCaptureSessionImpl extends android.hardware.camera2.CameraCaptureSession implements android.hardware.camera2.impl.CameraCaptureSessionCore {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "CameraCaptureSession";
    private final android.hardware.camera2.utils.TaskSingleDrainer mAbortDrainer;
    private volatile boolean mAborting;
    private boolean mClosed;
    private final boolean mConfigureSuccess;
    private final java.util.concurrent.Executor mDeviceExecutor;
    private final android.hardware.camera2.impl.CameraDeviceImpl mDeviceImpl;
    private final int mId;
    private final java.lang.String mIdString;
    private final android.hardware.camera2.utils.TaskSingleDrainer mIdleDrainer;
    private final android.view.Surface mInput;
    private final android.hardware.camera2.utils.TaskDrainer<java.lang.Integer> mSequenceDrainer;
    private boolean mSkipUnconfigure = false;
    private final android.hardware.camera2.CameraCaptureSession.StateCallback mStateCallback;
    private final java.util.concurrent.Executor mStateExecutor;

    CameraCaptureSessionImpl(int i, android.view.Surface surface, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, java.util.concurrent.Executor executor, android.hardware.camera2.impl.CameraDeviceImpl cameraDeviceImpl, java.util.concurrent.Executor executor2, boolean z) {
        this.mClosed = false;
        if (stateCallback == null) {
            throw new java.lang.IllegalArgumentException("callback must not be null");
        }
        this.mId = i;
        this.mIdString = java.lang.String.format("Session %d: ", java.lang.Integer.valueOf(this.mId));
        this.mInput = surface;
        this.mStateExecutor = (java.util.concurrent.Executor) com.android.internal.util.Preconditions.checkNotNull(executor, "stateExecutor must not be null");
        this.mStateCallback = createUserStateCallbackProxy(this.mStateExecutor, stateCallback);
        this.mDeviceExecutor = (java.util.concurrent.Executor) com.android.internal.util.Preconditions.checkNotNull(executor2, "deviceStateExecutor must not be null");
        this.mDeviceImpl = (android.hardware.camera2.impl.CameraDeviceImpl) com.android.internal.util.Preconditions.checkNotNull(cameraDeviceImpl, "deviceImpl must not be null");
        this.mSequenceDrainer = new android.hardware.camera2.utils.TaskDrainer<>(this.mDeviceExecutor, new android.hardware.camera2.impl.CameraCaptureSessionImpl.SequenceDrainListener(), "seq");
        this.mIdleDrainer = new android.hardware.camera2.utils.TaskSingleDrainer(this.mDeviceExecutor, new android.hardware.camera2.impl.CameraCaptureSessionImpl.IdleDrainListener(), "idle");
        this.mAbortDrainer = new android.hardware.camera2.utils.TaskSingleDrainer(this.mDeviceExecutor, new android.hardware.camera2.impl.CameraCaptureSessionImpl.AbortDrainListener(), "abort");
        if (z) {
            this.mStateCallback.onConfigured(this);
            this.mConfigureSuccess = true;
        } else {
            this.mStateCallback.onConfigureFailed(this);
            this.mClosed = true;
            android.util.Log.e(TAG, this.mIdString + "Failed to create capture session; configuration failed");
            this.mConfigureSuccess = false;
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public android.hardware.camera2.CameraDevice getDevice() {
        return this.mDeviceImpl;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void prepare(android.view.Surface surface) throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            this.mDeviceImpl.prepare(surface);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void prepare(int i, android.view.Surface surface) throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            this.mDeviceImpl.prepare(i, surface);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void tearDown(android.view.Surface surface) throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            this.mDeviceImpl.tearDown(surface);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void finalizeOutputConfigurations(java.util.List<android.hardware.camera2.params.OutputConfiguration> list) throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            this.mDeviceImpl.finalizeOutputConfigs(list);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int capture(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        int addPendingSequence;
        checkCaptureRequest(captureRequest);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            addPendingSequence = addPendingSequence(this.mDeviceImpl.capture(captureRequest, createCaptureCallbackProxy(android.hardware.camera2.impl.CameraDeviceImpl.checkHandler(handler, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return addPendingSequence;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureSingleRequest(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        int addPendingSequence;
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("executor must not be null");
        }
        if (captureCallback == null) {
            throw new java.lang.IllegalArgumentException("callback must not be null");
        }
        checkCaptureRequest(captureRequest);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            addPendingSequence = addPendingSequence(this.mDeviceImpl.capture(captureRequest, createCaptureCallbackProxyWithExecutor(android.hardware.camera2.impl.CameraDeviceImpl.checkExecutor(executor, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return addPendingSequence;
    }

    private void checkCaptureRequest(android.hardware.camera2.CaptureRequest captureRequest) {
        if (captureRequest == null) {
            throw new java.lang.IllegalArgumentException("request must not be null");
        }
        if (captureRequest.isReprocess() && !isReprocessable()) {
            throw new java.lang.IllegalArgumentException("this capture session cannot handle reprocess requests");
        }
        if (!this.mDeviceImpl.isPrivilegedApp() && captureRequest.isReprocess() && captureRequest.getReprocessableSessionId() != this.mId) {
            throw new java.lang.IllegalArgumentException("capture request was created for another session");
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureBurst(java.util.List<android.hardware.camera2.CaptureRequest> list, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        int addPendingSequence;
        checkCaptureRequests(list);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            addPendingSequence = addPendingSequence(this.mDeviceImpl.captureBurst(list, createCaptureCallbackProxy(android.hardware.camera2.impl.CameraDeviceImpl.checkHandler(handler, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return addPendingSequence;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int captureBurstRequests(java.util.List<android.hardware.camera2.CaptureRequest> list, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        int addPendingSequence;
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("executor must not be null");
        }
        if (captureCallback == null) {
            throw new java.lang.IllegalArgumentException("callback must not be null");
        }
        checkCaptureRequests(list);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            addPendingSequence = addPendingSequence(this.mDeviceImpl.captureBurst(list, createCaptureCallbackProxyWithExecutor(android.hardware.camera2.impl.CameraDeviceImpl.checkExecutor(executor, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return addPendingSequence;
    }

    private void checkCaptureRequests(java.util.List<android.hardware.camera2.CaptureRequest> list) {
        if (list == null) {
            throw new java.lang.IllegalArgumentException("Requests must not be null");
        }
        if (list.isEmpty()) {
            throw new java.lang.IllegalArgumentException("Requests must have at least one element");
        }
        for (android.hardware.camera2.CaptureRequest captureRequest : list) {
            if (captureRequest.isReprocess()) {
                if (!isReprocessable()) {
                    throw new java.lang.IllegalArgumentException("This capture session cannot handle reprocess requests");
                }
                if (captureRequest.getReprocessableSessionId() != this.mId) {
                    throw new java.lang.IllegalArgumentException("Capture request was created for another session");
                }
            }
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingRequest(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        int addPendingSequence;
        checkRepeatingRequest(captureRequest);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            addPendingSequence = addPendingSequence(this.mDeviceImpl.setRepeatingRequest(captureRequest, createCaptureCallbackProxy(android.hardware.camera2.impl.CameraDeviceImpl.checkHandler(handler, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return addPendingSequence;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setSingleRepeatingRequest(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        int addPendingSequence;
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("executor must not be null");
        }
        if (captureCallback == null) {
            throw new java.lang.IllegalArgumentException("callback must not be null");
        }
        checkRepeatingRequest(captureRequest);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            addPendingSequence = addPendingSequence(this.mDeviceImpl.setRepeatingRequest(captureRequest, createCaptureCallbackProxyWithExecutor(android.hardware.camera2.impl.CameraDeviceImpl.checkExecutor(executor, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return addPendingSequence;
    }

    private void checkRepeatingRequest(android.hardware.camera2.CaptureRequest captureRequest) {
        if (captureRequest == null) {
            throw new java.lang.IllegalArgumentException("request must not be null");
        }
        if (captureRequest.isReprocess()) {
            throw new java.lang.IllegalArgumentException("repeating reprocess requests are not supported");
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingBurst(java.util.List<android.hardware.camera2.CaptureRequest> list, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        int addPendingSequence;
        checkRepeatingRequests(list);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            addPendingSequence = addPendingSequence(this.mDeviceImpl.setRepeatingBurst(list, createCaptureCallbackProxy(android.hardware.camera2.impl.CameraDeviceImpl.checkHandler(handler, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return addPendingSequence;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public int setRepeatingBurstRequests(java.util.List<android.hardware.camera2.CaptureRequest> list, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        int addPendingSequence;
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("executor must not be null");
        }
        if (captureCallback == null) {
            throw new java.lang.IllegalArgumentException("callback must not be null");
        }
        checkRepeatingRequests(list);
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            addPendingSequence = addPendingSequence(this.mDeviceImpl.setRepeatingBurst(list, createCaptureCallbackProxyWithExecutor(android.hardware.camera2.impl.CameraDeviceImpl.checkExecutor(executor, captureCallback), captureCallback), this.mDeviceExecutor));
        }
        return addPendingSequence;
    }

    private void checkRepeatingRequests(java.util.List<android.hardware.camera2.CaptureRequest> list) {
        if (list == null) {
            throw new java.lang.IllegalArgumentException("requests must not be null");
        }
        if (list.isEmpty()) {
            throw new java.lang.IllegalArgumentException("requests must have at least one element");
        }
        java.util.Iterator<android.hardware.camera2.CaptureRequest> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().isReprocess()) {
                throw new java.lang.IllegalArgumentException("repeating reprocess burst requests are not supported");
            }
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void stopRepeating() throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            this.mDeviceImpl.stopRepeating();
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void abortCaptures() throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            if (this.mAborting) {
                android.util.Log.w(TAG, this.mIdString + "abortCaptures - Session is already aborting; doing nothing");
                return;
            }
            this.mAborting = true;
            this.mAbortDrainer.taskStarted();
            this.mDeviceImpl.flush();
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public void updateOutputConfiguration(android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
            this.mDeviceImpl.updateOutputConfiguration(outputConfiguration);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public android.hardware.camera2.CameraOfflineSession switchToOffline(java.util.Collection<android.view.Surface> collection, java.util.concurrent.Executor executor, android.hardware.camera2.CameraOfflineSession.CameraOfflineSessionCallback cameraOfflineSessionCallback) throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
        }
        return this.mDeviceImpl.switchToOffline(collection, executor, cameraOfflineSessionCallback);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public boolean supportsOfflineProcessing(android.view.Surface surface) {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            checkNotClosed();
        }
        return this.mDeviceImpl.supportsOfflineProcessing(surface);
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public boolean isReprocessable() {
        return this.mInput != null;
    }

    @Override // android.hardware.camera2.CameraCaptureSession
    public android.view.Surface getInputSurface() {
        return this.mInput;
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public void replaceSessionClose() {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            this.mSkipUnconfigure = true;
            close();
        }
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public void closeWithoutDraining() {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            if (this.mClosed) {
                return;
            }
            this.mClosed = true;
            this.mStateCallback.onClosed(this);
            if (this.mInput != null) {
                this.mInput.release();
            }
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession, java.lang.AutoCloseable
    public void close() {
        synchronized (this.mDeviceImpl.mInterfaceLock) {
            if (this.mClosed) {
                return;
            }
            this.mClosed = true;
            try {
                this.mDeviceImpl.stopRepeating();
            } catch (android.hardware.camera2.CameraAccessException e) {
                android.util.Log.e(TAG, this.mIdString + "Exception while stopping repeating: ", e);
            } catch (java.lang.IllegalStateException e2) {
                this.mStateCallback.onClosed(this);
                return;
            }
            this.mSequenceDrainer.beginDrain();
            if (this.mInput != null) {
                this.mInput.release();
            }
        }
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public boolean isAborting() {
        return this.mAborting;
    }

    private android.hardware.camera2.CameraCaptureSession.StateCallback createUserStateCallbackProxy(java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback) {
        return new android.hardware.camera2.impl.CallbackProxies.SessionStateCallbackProxy(executor, stateCallback);
    }

    private android.hardware.camera2.impl.CaptureCallback createCaptureCallbackProxy(android.os.Handler handler, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) {
        return createCaptureCallbackProxyWithExecutor(captureCallback != null ? android.hardware.camera2.impl.CameraDeviceImpl.checkAndWrapHandler(handler) : null, captureCallback);
    }

    /* renamed from: android.hardware.camera2.impl.CameraCaptureSessionImpl$1, reason: invalid class name */
    class AnonymousClass1 extends android.hardware.camera2.impl.CaptureCallback {
        final /* synthetic */ android.hardware.camera2.CameraCaptureSession.CaptureCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback2, java.util.concurrent.Executor executor2) {
            super(executor, captureCallback);
            this.val$callback = captureCallback2;
            this.val$executor = executor2;
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCaptureStarted(android.hardware.camera2.CameraDevice cameraDevice, final android.hardware.camera2.CaptureRequest captureRequest, final long j, final long j2) {
            if (this.val$callback != null && this.val$executor != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraCaptureSessionImpl.AnonymousClass1.this.lambda$onCaptureStarted$0(captureCallback, captureRequest, j, j2);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureStarted$0(android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.hardware.camera2.CaptureRequest captureRequest, long j, long j2) {
            captureCallback.onCaptureStarted(android.hardware.camera2.impl.CameraCaptureSessionImpl.this, captureRequest, j, j2);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onReadoutStarted(android.hardware.camera2.CameraDevice cameraDevice, final android.hardware.camera2.CaptureRequest captureRequest, final long j, final long j2) {
            if (this.val$callback != null && this.val$executor != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraCaptureSessionImpl.AnonymousClass1.this.lambda$onReadoutStarted$1(captureCallback, captureRequest, j, j2);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReadoutStarted$1(android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.hardware.camera2.CaptureRequest captureRequest, long j, long j2) {
            captureCallback.onReadoutStarted(android.hardware.camera2.impl.CameraCaptureSessionImpl.this, captureRequest, j, j2);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCapturePartial(android.hardware.camera2.CameraDevice cameraDevice, final android.hardware.camera2.CaptureRequest captureRequest, final android.hardware.camera2.CaptureResult captureResult) {
            if (this.val$callback != null && this.val$executor != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraCaptureSessionImpl.AnonymousClass1.this.lambda$onCapturePartial$2(captureCallback, captureRequest, captureResult);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCapturePartial$2(android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureResult captureResult) {
            captureCallback.onCapturePartial(android.hardware.camera2.impl.CameraCaptureSessionImpl.this, captureRequest, captureResult);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCaptureProgressed(android.hardware.camera2.CameraDevice cameraDevice, final android.hardware.camera2.CaptureRequest captureRequest, final android.hardware.camera2.CaptureResult captureResult) {
            if (this.val$callback != null && this.val$executor != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraCaptureSessionImpl.AnonymousClass1.this.lambda$onCaptureProgressed$3(captureCallback, captureRequest, captureResult);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProgressed$3(android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureResult captureResult) {
            captureCallback.onCaptureProgressed(android.hardware.camera2.impl.CameraCaptureSessionImpl.this, captureRequest, captureResult);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCaptureCompleted(android.hardware.camera2.CameraDevice cameraDevice, final android.hardware.camera2.CaptureRequest captureRequest, final android.hardware.camera2.TotalCaptureResult totalCaptureResult) {
            if (this.val$callback != null && this.val$executor != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraCaptureSessionImpl.AnonymousClass1.this.lambda$onCaptureCompleted$4(captureCallback, captureRequest, totalCaptureResult);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$4(android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.TotalCaptureResult totalCaptureResult) {
            captureCallback.onCaptureCompleted(android.hardware.camera2.impl.CameraCaptureSessionImpl.this, captureRequest, totalCaptureResult);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCaptureFailed(android.hardware.camera2.CameraDevice cameraDevice, final android.hardware.camera2.CaptureRequest captureRequest, final android.hardware.camera2.CaptureFailure captureFailure) {
            if (this.val$callback != null && this.val$executor != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraCaptureSessionImpl.AnonymousClass1.this.lambda$onCaptureFailed$5(captureCallback, captureRequest, captureFailure);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureFailed$5(android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureFailure captureFailure) {
            captureCallback.onCaptureFailed(android.hardware.camera2.impl.CameraCaptureSessionImpl.this, captureRequest, captureFailure);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCaptureSequenceCompleted(android.hardware.camera2.CameraDevice cameraDevice, final int i, final long j) {
            if (this.val$callback != null && this.val$executor != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraCaptureSessionImpl.AnonymousClass1.this.lambda$onCaptureSequenceCompleted$6(captureCallback, i, j);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            android.hardware.camera2.impl.CameraCaptureSessionImpl.this.finishPendingSequence(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$6(android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, int i, long j) {
            captureCallback.onCaptureSequenceCompleted(android.hardware.camera2.impl.CameraCaptureSessionImpl.this, i, j);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCaptureSequenceAborted(android.hardware.camera2.CameraDevice cameraDevice, final int i) {
            if (this.val$callback != null && this.val$executor != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraCaptureSessionImpl.AnonymousClass1.this.lambda$onCaptureSequenceAborted$7(captureCallback, i);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            android.hardware.camera2.impl.CameraCaptureSessionImpl.this.finishPendingSequence(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceAborted$7(android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, int i) {
            captureCallback.onCaptureSequenceAborted(android.hardware.camera2.impl.CameraCaptureSessionImpl.this, i);
        }

        @Override // android.hardware.camera2.impl.CaptureCallback
        public void onCaptureBufferLost(android.hardware.camera2.CameraDevice cameraDevice, final android.hardware.camera2.CaptureRequest captureRequest, final android.view.Surface surface, final long j) {
            if (this.val$callback != null && this.val$executor != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl$1$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraCaptureSessionImpl.AnonymousClass1.this.lambda$onCaptureBufferLost$8(captureCallback, captureRequest, surface, j);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureBufferLost$8(android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.hardware.camera2.CaptureRequest captureRequest, android.view.Surface surface, long j) {
            captureCallback.onCaptureBufferLost(android.hardware.camera2.impl.CameraCaptureSessionImpl.this, captureRequest, surface, j);
        }
    }

    private android.hardware.camera2.impl.CaptureCallback createCaptureCallbackProxyWithExecutor(java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) {
        return new android.hardware.camera2.impl.CameraCaptureSessionImpl.AnonymousClass1(executor, captureCallback, captureCallback, executor);
    }

    @Override // android.hardware.camera2.impl.CameraCaptureSessionCore
    public android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK getDeviceStateCallback() {
        final java.lang.Object obj = this.mDeviceImpl.mInterfaceLock;
        return new android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK() { // from class: android.hardware.camera2.impl.CameraCaptureSessionImpl.2
            private boolean mBusy = false;
            private boolean mActive = false;

            @Override // android.hardware.camera2.CameraDevice.StateCallback
            public void onOpened(android.hardware.camera2.CameraDevice cameraDevice) {
                throw new java.lang.AssertionError("Camera must already be open before creating a session");
            }

            @Override // android.hardware.camera2.CameraDevice.StateCallback
            public void onDisconnected(android.hardware.camera2.CameraDevice cameraDevice) {
                android.hardware.camera2.impl.CameraCaptureSessionImpl.this.close();
            }

            @Override // android.hardware.camera2.CameraDevice.StateCallback
            public void onError(android.hardware.camera2.CameraDevice cameraDevice, int i) {
                android.util.Log.wtf(android.hardware.camera2.impl.CameraCaptureSessionImpl.TAG, android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mIdString + "Got device error " + i);
            }

            @Override // android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK
            public void onActive(android.hardware.camera2.CameraDevice cameraDevice) {
                android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mIdleDrainer.taskStarted();
                this.mActive = true;
                android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mStateCallback.onActive(this);
            }

            @Override // android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK
            public void onIdle(android.hardware.camera2.CameraDevice cameraDevice) {
                boolean z;
                synchronized (obj) {
                    z = android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mAborting;
                }
                if (this.mBusy && z) {
                    android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mAbortDrainer.taskFinished();
                    synchronized (obj) {
                        android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mAborting = false;
                    }
                }
                if (this.mActive) {
                    android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mIdleDrainer.taskFinished();
                }
                this.mBusy = false;
                this.mActive = false;
                android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mStateCallback.onReady(this);
            }

            @Override // android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK
            public void onBusy(android.hardware.camera2.CameraDevice cameraDevice) {
                this.mBusy = true;
            }

            @Override // android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK
            public void onUnconfigured(android.hardware.camera2.CameraDevice cameraDevice) {
            }

            @Override // android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK
            public void onRequestQueueEmpty() {
                android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mStateCallback.onCaptureQueueEmpty(this);
            }

            @Override // android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK
            public void onSurfacePrepared(android.view.Surface surface) {
                android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mStateCallback.onSurfacePrepared(this, surface);
            }
        };
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private void checkNotClosed() {
        if (this.mClosed) {
            throw new java.lang.IllegalStateException("Session has been closed; further changes are illegal.");
        }
    }

    private int addPendingSequence(int i) {
        this.mSequenceDrainer.taskStarted(java.lang.Integer.valueOf(i));
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishPendingSequence(int i) {
        try {
            this.mSequenceDrainer.taskFinished(java.lang.Integer.valueOf(i));
        } catch (java.lang.IllegalStateException e) {
            android.util.Log.w(TAG, e.getMessage());
        }
    }

    private class SequenceDrainListener implements android.hardware.camera2.utils.TaskDrainer.DrainListener {
        private SequenceDrainListener() {
        }

        @Override // android.hardware.camera2.utils.TaskDrainer.DrainListener
        public void onDrained() {
            android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mStateCallback.onClosed(android.hardware.camera2.impl.CameraCaptureSessionImpl.this);
            if (android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mSkipUnconfigure) {
                return;
            }
            android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mAbortDrainer.beginDrain();
        }
    }

    private class AbortDrainListener implements android.hardware.camera2.utils.TaskDrainer.DrainListener {
        private AbortDrainListener() {
        }

        @Override // android.hardware.camera2.utils.TaskDrainer.DrainListener
        public void onDrained() {
            synchronized (android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mDeviceImpl.mInterfaceLock) {
                if (android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mSkipUnconfigure) {
                    return;
                }
                android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mIdleDrainer.beginDrain();
            }
        }
    }

    private class IdleDrainListener implements android.hardware.camera2.utils.TaskDrainer.DrainListener {
        private IdleDrainListener() {
        }

        @Override // android.hardware.camera2.utils.TaskDrainer.DrainListener
        public void onDrained() {
            synchronized (android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mDeviceImpl.mInterfaceLock) {
                if (android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mSkipUnconfigure) {
                    return;
                }
                try {
                    android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mDeviceImpl.configureStreamsChecked(null, null, 0, null, android.os.SystemClock.uptimeMillis());
                } catch (android.hardware.camera2.CameraAccessException e) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraCaptureSessionImpl.TAG, android.hardware.camera2.impl.CameraCaptureSessionImpl.this.mIdString + "Exception while unconfiguring outputs: ", e);
                } catch (java.lang.IllegalStateException e2) {
                }
            }
        }
    }
}
