package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public final class CameraAdvancedExtensionSessionImpl extends android.hardware.camera2.CameraExtensionSession {
    private static final java.lang.String TAG = "CameraAdvancedExtensionSessionImpl";
    private android.hardware.camera2.extension.IAdvancedExtenderImpl mAdvancedExtender;
    private final android.hardware.camera2.CameraExtensionSession.StateCallback mCallbacks;
    private android.hardware.camera2.CameraDevice mCameraDevice;
    private final java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> mCharacteristicsMap;
    private android.view.Surface mClientCaptureSurface;
    private android.view.Surface mClientPostviewSurface;
    private android.view.Surface mClientRepeatingRequestSurface;
    private final android.content.Context mContext;
    private final java.util.concurrent.Executor mExecutor;
    private int mExtensionType;
    private final android.os.Handler mHandler;
    private final android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.InitializeSessionHandler mInitializeHandler;
    private boolean mInitialized;
    final java.lang.Object mInterfaceLock;
    private boolean mSessionClosed;
    private final int mSessionId;
    private final android.hardware.camera2.utils.ExtensionSessionStatsAggregator mStatsAggregator;
    private android.os.IBinder mToken;
    private final java.util.HashMap<android.view.Surface, android.hardware.camera2.extension.CameraOutputConfig> mCameraConfigMap = new java.util.HashMap<>();
    private final java.util.HashMap<java.lang.Integer, android.media.ImageReader> mReaderMap = new java.util.HashMap<>();
    private android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.RequestProcessor mRequestProcessor = new android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.RequestProcessor();
    private android.hardware.camera2.CameraCaptureSession mCaptureSession = null;
    private android.hardware.camera2.extension.ISessionProcessorImpl mSessionProcessor = null;
    private final android.os.HandlerThread mHandlerThread = new android.os.HandlerThread(TAG);

    public static android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl createCameraAdvancedExtensionSession(android.hardware.camera2.impl.CameraDeviceImpl cameraDeviceImpl, java.util.Map<java.lang.String, android.hardware.camera2.CameraCharacteristics> map, android.content.Context context, android.hardware.camera2.params.ExtensionSessionConfiguration extensionSessionConfiguration, int i, android.os.IBinder iBinder) throws android.hardware.camera2.CameraAccessException, android.os.RemoteException {
        int i2;
        android.view.Surface surface;
        java.lang.String id = cameraDeviceImpl.getId();
        android.hardware.camera2.CameraExtensionCharacteristics cameraExtensionCharacteristics = new android.hardware.camera2.CameraExtensionCharacteristics(context, id, map);
        java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> characteristicsMapNative = android.hardware.camera2.impl.CameraExtensionUtils.getCharacteristicsMapNative(map);
        if (!android.hardware.camera2.CameraExtensionCharacteristics.isExtensionSupported(cameraDeviceImpl.getId(), extensionSessionConfiguration.getExtension(), characteristicsMapNative)) {
            throw new java.lang.UnsupportedOperationException("Unsupported extension type: " + extensionSessionConfiguration.getExtension());
        }
        if (extensionSessionConfiguration.getOutputConfigurations().isEmpty() || extensionSessionConfiguration.getOutputConfigurations().size() > 2) {
            throw new java.lang.IllegalArgumentException("Unexpected amount of output surfaces, received: " + extensionSessionConfiguration.getOutputConfigurations().size() + " expected <= 2");
        }
        for (android.hardware.camera2.params.OutputConfiguration outputConfiguration : extensionSessionConfiguration.getOutputConfigurations()) {
            if (outputConfiguration.getDynamicRangeProfile() != 1) {
                throw new java.lang.IllegalArgumentException("Unsupported dynamic range profile: " + outputConfiguration.getDynamicRangeProfile());
            }
            if (outputConfiguration.getStreamUseCase() != 0) {
                throw new java.lang.IllegalArgumentException("Unsupported stream use case: " + outputConfiguration.getStreamUseCase());
            }
        }
        android.view.Surface repeatingRequestSurface = android.hardware.camera2.impl.CameraExtensionUtils.getRepeatingRequestSurface(extensionSessionConfiguration.getOutputConfigurations(), cameraExtensionCharacteristics.getExtensionSupportedSizes(extensionSessionConfiguration.getExtension(), android.graphics.SurfaceTexture.class));
        if (repeatingRequestSurface == null) {
            i2 = 0;
        } else {
            i2 = 1;
        }
        java.util.HashMap hashMap = new java.util.HashMap();
        for (int i3 : android.hardware.camera2.impl.CameraExtensionUtils.SUPPORTED_CAPTURE_OUTPUT_FORMATS) {
            java.util.List<android.util.Size> extensionSupportedSizes = cameraExtensionCharacteristics.getExtensionSupportedSizes(extensionSessionConfiguration.getExtension(), i3);
            if (extensionSupportedSizes != null) {
                hashMap.put(java.lang.Integer.valueOf(i3), extensionSupportedSizes);
            }
        }
        android.view.Surface burstCaptureSurface = android.hardware.camera2.impl.CameraExtensionUtils.getBurstCaptureSurface(extensionSessionConfiguration.getOutputConfigurations(), hashMap);
        if (burstCaptureSurface != null) {
            i2++;
        }
        if (i2 != extensionSessionConfiguration.getOutputConfigurations().size()) {
            throw new java.lang.IllegalArgumentException("One or more unsupported output surfaces found!");
        }
        if (burstCaptureSurface != null && extensionSessionConfiguration.getPostviewOutputConfiguration() != null) {
            android.hardware.camera2.impl.CameraExtensionUtils.SurfaceInfo querySurface = android.hardware.camera2.impl.CameraExtensionUtils.querySurface(burstCaptureSurface);
            android.util.Size size = new android.util.Size(querySurface.mWidth, querySurface.mHeight);
            java.util.HashMap hashMap2 = new java.util.HashMap();
            for (int i4 : android.hardware.camera2.impl.CameraExtensionUtils.SUPPORTED_CAPTURE_OUTPUT_FORMATS) {
                java.util.List<android.util.Size> postviewSupportedSizes = cameraExtensionCharacteristics.getPostviewSupportedSizes(extensionSessionConfiguration.getExtension(), size, i4);
                if (postviewSupportedSizes != null) {
                    hashMap2.put(java.lang.Integer.valueOf(i4), postviewSupportedSizes);
                }
            }
            android.view.Surface postviewSurface = android.hardware.camera2.impl.CameraExtensionUtils.getPostviewSurface(extensionSessionConfiguration.getPostviewOutputConfiguration(), hashMap2, querySurface.mFormat);
            if (postviewSurface == null) {
                throw new java.lang.IllegalArgumentException("Unsupported output surface for postview!");
            }
            surface = postviewSurface;
        } else {
            surface = null;
        }
        android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension = android.hardware.camera2.CameraExtensionCharacteristics.initializeAdvancedExtension(extensionSessionConfiguration.getExtension());
        initializeAdvancedExtension.init(id, characteristicsMapNative);
        android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl cameraAdvancedExtensionSessionImpl = new android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl(context, initializeAdvancedExtension, cameraDeviceImpl, characteristicsMapNative, repeatingRequestSurface, burstCaptureSurface, surface, extensionSessionConfiguration.getStateCallback(), extensionSessionConfiguration.getExecutor(), i, iBinder, extensionSessionConfiguration.getExtension());
        cameraAdvancedExtensionSessionImpl.mStatsAggregator.setClientName(context.getOpPackageName());
        cameraAdvancedExtensionSessionImpl.mStatsAggregator.setExtensionType(extensionSessionConfiguration.getExtension());
        cameraAdvancedExtensionSessionImpl.initialize();
        return cameraAdvancedExtensionSessionImpl;
    }

    private CameraAdvancedExtensionSessionImpl(android.content.Context context, android.hardware.camera2.extension.IAdvancedExtenderImpl iAdvancedExtenderImpl, android.hardware.camera2.impl.CameraDeviceImpl cameraDeviceImpl, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map, android.view.Surface surface, android.view.Surface surface2, android.view.Surface surface3, android.hardware.camera2.CameraExtensionSession.StateCallback stateCallback, java.util.concurrent.Executor executor, int i, android.os.IBinder iBinder, int i2) {
        this.mToken = null;
        this.mContext = context;
        this.mAdvancedExtender = iAdvancedExtenderImpl;
        this.mCameraDevice = cameraDeviceImpl;
        this.mCharacteristicsMap = map;
        this.mCallbacks = stateCallback;
        this.mExecutor = executor;
        this.mClientRepeatingRequestSurface = surface;
        this.mClientCaptureSurface = surface2;
        this.mClientPostviewSurface = surface3;
        this.mHandlerThread.start();
        this.mHandler = new android.os.Handler(this.mHandlerThread.getLooper());
        this.mInitialized = false;
        this.mSessionClosed = false;
        this.mInitializeHandler = new android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.InitializeSessionHandler();
        this.mSessionId = i;
        this.mToken = iBinder;
        this.mInterfaceLock = cameraDeviceImpl.mInterfaceLock;
        this.mExtensionType = i2;
        this.mStatsAggregator = new android.hardware.camera2.utils.ExtensionSessionStatsAggregator(this.mCameraDevice.getId(), true);
    }

    public synchronized void initialize() throws android.hardware.camera2.CameraAccessException, android.os.RemoteException {
        int i;
        if (this.mInitialized) {
            android.util.Log.d(TAG, "Session already initialized");
            return;
        }
        android.hardware.camera2.extension.OutputSurface initializeParcelable = initializeParcelable(this.mClientRepeatingRequestSurface);
        android.hardware.camera2.extension.OutputSurface initializeParcelable2 = initializeParcelable(this.mClientCaptureSurface);
        android.hardware.camera2.extension.OutputSurface initializeParcelable3 = initializeParcelable(this.mClientPostviewSurface);
        this.mSessionProcessor = this.mAdvancedExtender.getSessionProcessor();
        android.hardware.camera2.extension.CameraSessionConfig initSession = this.mSessionProcessor.initSession(this.mToken, this.mCameraDevice.getId(), this.mCharacteristicsMap, initializeParcelable, initializeParcelable2, initializeParcelable3);
        java.util.List<android.hardware.camera2.extension.CameraOutputConfig> list = initSession.outputConfigs;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.hardware.camera2.extension.CameraOutputConfig> it = list.iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            android.hardware.camera2.extension.CameraOutputConfig next = it.next();
            android.view.Surface initializeSurface = initializeSurface(next);
            if (initializeSurface != null) {
                android.hardware.camera2.params.OutputConfiguration outputConfiguration = new android.hardware.camera2.params.OutputConfiguration(next.surfaceGroupId, initializeSurface);
                if (next.isMultiResolutionOutput) {
                    outputConfiguration.setMultiResolutionOutput();
                }
                if (next.sharedSurfaceConfigs != null && !next.sharedSurfaceConfigs.isEmpty()) {
                    outputConfiguration.enableSurfaceSharing();
                    for (android.hardware.camera2.extension.CameraOutputConfig cameraOutputConfig : next.sharedSurfaceConfigs) {
                        android.view.Surface initializeSurface2 = initializeSurface(cameraOutputConfig);
                        if (initializeSurface2 != null) {
                            outputConfiguration.addSurface(initializeSurface2);
                            this.mCameraConfigMap.put(initializeSurface2, cameraOutputConfig);
                        }
                    }
                }
                outputConfiguration.setTimestampBase(1);
                outputConfiguration.setReadoutTimestampEnabled(false);
                outputConfiguration.setPhysicalCameraId(next.physicalCameraId);
                arrayList.add(outputConfiguration);
                this.mCameraConfigMap.put(outputConfiguration.getSurface(), next);
            }
        }
        if (initSession.sessionType != -1 && initSession.sessionType != 1) {
            i = initSession.sessionType;
            android.util.Log.v(TAG, "Using session type: " + i);
        }
        android.hardware.camera2.params.SessionConfiguration sessionConfiguration = new android.hardware.camera2.params.SessionConfiguration(i, arrayList, new android.hardware.camera2.impl.CameraExtensionUtils.HandlerExecutor(this.mHandler), new android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.SessionStateHandler());
        if (initSession.sessionParameter != null && !initSession.sessionParameter.isEmpty()) {
            android.hardware.camera2.CaptureRequest build = this.mCameraDevice.createCaptureRequest(initSession.sessionTemplateId).build();
            android.hardware.camera2.impl.CameraMetadataNative.update(build.getNativeMetadata(), initSession.sessionParameter);
            sessionConfiguration.setSessionParameters(build);
        }
        this.mCameraDevice.createCaptureSession(sessionConfiguration);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.hardware.camera2.extension.ParcelCaptureResult initializeParcelable(android.hardware.camera2.CaptureResult captureResult) {
        android.hardware.camera2.extension.ParcelCaptureResult parcelCaptureResult = new android.hardware.camera2.extension.ParcelCaptureResult();
        parcelCaptureResult.cameraId = captureResult.getCameraId();
        parcelCaptureResult.results = captureResult.getNativeMetadata();
        parcelCaptureResult.parent = captureResult.getRequest();
        parcelCaptureResult.sequenceId = captureResult.getSequenceId();
        parcelCaptureResult.frameNumber = captureResult.getFrameNumber();
        return parcelCaptureResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.hardware.camera2.extension.ParcelTotalCaptureResult initializeParcelable(android.hardware.camera2.TotalCaptureResult totalCaptureResult) {
        android.hardware.camera2.extension.ParcelTotalCaptureResult parcelTotalCaptureResult = new android.hardware.camera2.extension.ParcelTotalCaptureResult();
        parcelTotalCaptureResult.logicalCameraId = totalCaptureResult.getCameraId();
        parcelTotalCaptureResult.results = totalCaptureResult.getNativeMetadata();
        parcelTotalCaptureResult.parent = totalCaptureResult.getRequest();
        parcelTotalCaptureResult.sequenceId = totalCaptureResult.getSequenceId();
        parcelTotalCaptureResult.frameNumber = totalCaptureResult.getFrameNumber();
        parcelTotalCaptureResult.sessionId = totalCaptureResult.getSessionId();
        parcelTotalCaptureResult.partials = new java.util.ArrayList(totalCaptureResult.getPartialResults().size());
        java.util.Iterator<android.hardware.camera2.CaptureResult> it = totalCaptureResult.getPartialResults().iterator();
        while (it.hasNext()) {
            parcelTotalCaptureResult.partials.add(initializeParcelable(it.next()));
        }
        java.util.Map<java.lang.String, android.hardware.camera2.TotalCaptureResult> physicalCameraTotalResults = totalCaptureResult.getPhysicalCameraTotalResults();
        parcelTotalCaptureResult.physicalResult = new java.util.ArrayList(physicalCameraTotalResults.size());
        for (android.hardware.camera2.TotalCaptureResult totalCaptureResult2 : physicalCameraTotalResults.values()) {
            parcelTotalCaptureResult.physicalResult.add(new android.hardware.camera2.impl.PhysicalCaptureResultInfo(totalCaptureResult2.getCameraId(), totalCaptureResult2.getNativeMetadata()));
        }
        return parcelTotalCaptureResult;
    }

    private static android.hardware.camera2.extension.OutputSurface initializeParcelable(android.view.Surface surface) {
        android.hardware.camera2.extension.OutputSurface outputSurface = new android.hardware.camera2.extension.OutputSurface();
        if (surface != null) {
            outputSurface.surface = surface;
            outputSurface.size = new android.hardware.camera2.extension.Size();
            android.util.Size surfaceSize = android.hardware.camera2.utils.SurfaceUtils.getSurfaceSize(surface);
            outputSurface.size.width = surfaceSize.getWidth();
            outputSurface.size.height = surfaceSize.getHeight();
            outputSurface.imageFormat = android.hardware.camera2.utils.SurfaceUtils.getSurfaceFormat(surface);
        } else {
            outputSurface.surface = null;
            outputSurface.size = new android.hardware.camera2.extension.Size();
            outputSurface.size.width = -1;
            outputSurface.size.height = -1;
            outputSurface.imageFormat = 0;
        }
        return outputSurface;
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public android.hardware.camera2.CameraDevice getDevice() {
        android.hardware.camera2.CameraDevice cameraDevice;
        synchronized (this.mInterfaceLock) {
            cameraDevice = this.mCameraDevice;
        }
        return cameraDevice;
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public android.hardware.camera2.CameraExtensionSession.StillCaptureLatency getRealtimeStillCaptureLatency() throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (!this.mInitialized) {
                throw new java.lang.IllegalStateException("Uninitialized component");
            }
            try {
                android.hardware.camera2.extension.LatencyPair realtimeCaptureLatency = this.mSessionProcessor.getRealtimeCaptureLatency();
                if (realtimeCaptureLatency == null) {
                    return null;
                }
                return new android.hardware.camera2.CameraExtensionSession.StillCaptureLatency(realtimeCaptureLatency.first, realtimeCaptureLatency.second);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to query realtime latency! Extension service does not respond");
                throw new android.hardware.camera2.CameraAccessException(3);
            }
        }
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public int setRepeatingRequest(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback) throws android.hardware.camera2.CameraAccessException {
        int startRepeating;
        synchronized (this.mInterfaceLock) {
            if (!this.mInitialized) {
                throw new java.lang.IllegalStateException("Uninitialized component");
            }
            if (this.mClientRepeatingRequestSurface == null) {
                throw new java.lang.IllegalArgumentException("No registered preview surface");
            }
            if (!captureRequest.containsTarget(this.mClientRepeatingRequestSurface) || captureRequest.getTargets().size() != 1) {
                throw new java.lang.IllegalArgumentException("Invalid repeating request output target!");
            }
            try {
                this.mSessionProcessor.setParameters(captureRequest);
                startRepeating = this.mSessionProcessor.startRepeating(new android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.RequestCallbackHandler(captureRequest, executor, extensionCaptureCallback, this.mCameraDevice.getId()));
            } catch (android.os.RemoteException e) {
                throw new android.hardware.camera2.CameraAccessException(3, "Failed to enable repeating request, extension service failed to respond!");
            }
        }
        return startRepeating;
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public int capture(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback) throws android.hardware.camera2.CameraAccessException {
        int startTrigger;
        synchronized (this.mInterfaceLock) {
            if (!this.mInitialized) {
                throw new java.lang.IllegalStateException("Uninitialized component");
            }
            validateCaptureRequestTargets(captureRequest);
            if (this.mClientCaptureSurface != null && captureRequest.containsTarget(this.mClientCaptureSurface)) {
                try {
                    boolean containsTarget = captureRequest.containsTarget(this.mClientPostviewSurface);
                    this.mSessionProcessor.setParameters(captureRequest);
                    startTrigger = this.mSessionProcessor.startCapture(new android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.RequestCallbackHandler(captureRequest, executor, extensionCaptureCallback, this.mCameraDevice.getId()), containsTarget);
                } catch (android.os.RemoteException e) {
                    throw new android.hardware.camera2.CameraAccessException(3, "Failed  to submit capture request, extension service failed to respond!");
                }
            } else if (this.mClientRepeatingRequestSurface != null && captureRequest.containsTarget(this.mClientRepeatingRequestSurface)) {
                try {
                    startTrigger = this.mSessionProcessor.startTrigger(captureRequest, new android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.RequestCallbackHandler(captureRequest, executor, extensionCaptureCallback, this.mCameraDevice.getId()));
                } catch (android.os.RemoteException e2) {
                    throw new android.hardware.camera2.CameraAccessException(3, "Failed  to submit trigger request, extension service failed to respond!");
                }
            } else {
                throw new java.lang.IllegalArgumentException("Invalid single capture output target!");
            }
        }
        return startTrigger;
    }

    private void validateCaptureRequestTargets(android.hardware.camera2.CaptureRequest captureRequest) {
        if (captureRequest.getTargets().size() == 1) {
            boolean z = this.mClientCaptureSurface != null && captureRequest.containsTarget(this.mClientCaptureSurface);
            boolean z2 = this.mClientRepeatingRequestSurface != null && captureRequest.containsTarget(this.mClientRepeatingRequestSurface);
            if (!z && !z2) {
                throw new java.lang.IllegalArgumentException("Target output combination requested is not supported!");
            }
        }
        if (captureRequest.getTargets().size() == 2 && !captureRequest.getTargets().containsAll(java.util.Arrays.asList(this.mClientCaptureSurface, this.mClientPostviewSurface))) {
            throw new java.lang.IllegalArgumentException("Target output combination requested is not supported!");
        }
        if (captureRequest.getTargets().size() > 2) {
            throw new java.lang.IllegalArgumentException("Target output combination requested is not supported!");
        }
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public void stopRepeating() throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (!this.mInitialized) {
                throw new java.lang.IllegalStateException("Uninitialized component");
            }
            this.mCaptureSession.stopRepeating();
            try {
                this.mSessionProcessor.stopRepeating();
            } catch (android.os.RemoteException e) {
                throw new android.hardware.camera2.CameraAccessException(3, "Failed to notify about the end of repeating request, extension service failed to respond!");
            }
        }
    }

    @Override // android.hardware.camera2.CameraExtensionSession, java.lang.AutoCloseable
    public void close() throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (this.mInitialized) {
                try {
                    try {
                        this.mCaptureSession.stopRepeating();
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(TAG, "Failed to stop the repeating request or end the session, , extension service does not respond!");
                    }
                } catch (java.lang.IllegalStateException e2) {
                }
                this.mSessionProcessor.stopRepeating();
                this.mSessionProcessor.onCaptureSessionEnd();
                this.mSessionClosed = true;
                this.mStatsAggregator.commit(true);
                this.mCaptureSession.close();
            }
        }
    }

    public void commitStats() {
        synchronized (this.mInterfaceLock) {
            if (this.mInitialized) {
                this.mStatsAggregator.commit(true);
            }
        }
    }

    public void release(boolean z) {
        boolean z2;
        synchronized (this.mInterfaceLock) {
            this.mHandlerThread.quitSafely();
            if (this.mSessionProcessor != null) {
                try {
                    if (!this.mSessionClosed) {
                        this.mSessionProcessor.onCaptureSessionEnd();
                    }
                    this.mSessionProcessor.deInitSession(this.mToken);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "Failed to de-initialize session processor, extension service does not respond!");
                }
                this.mSessionProcessor = null;
            }
            if (this.mToken == null) {
                z2 = false;
            } else {
                if (!this.mInitialized && this.mCaptureSession == null) {
                    z2 = false;
                    android.hardware.camera2.CameraExtensionCharacteristics.unregisterClient(this.mContext, this.mToken, this.mExtensionType);
                }
                android.hardware.camera2.CameraExtensionCharacteristics.releaseSession(this.mExtensionType);
                z2 = true;
                android.hardware.camera2.CameraExtensionCharacteristics.unregisterClient(this.mContext, this.mToken, this.mExtensionType);
            }
            this.mInitialized = false;
            this.mToken = null;
            java.util.Iterator<android.media.ImageReader> it = this.mReaderMap.values().iterator();
            while (it.hasNext()) {
                it.next().close();
            }
            this.mReaderMap.clear();
            this.mClientRepeatingRequestSurface = null;
            this.mClientCaptureSurface = null;
            this.mCaptureSession = null;
            this.mRequestProcessor = null;
            this.mCameraDevice = null;
            this.mAdvancedExtender = null;
        }
        if (z2 && !z) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.lambda$release$0();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$release$0() {
        this.mCallbacks.onClosed(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyConfigurationFailure() {
        synchronized (this.mInterfaceLock) {
            if (this.mInitialized) {
                return;
            }
            release(true);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.lambda$notifyConfigurationFailure$1();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyConfigurationFailure$1() {
        this.mCallbacks.onConfigureFailed(this);
    }

    private class SessionStateHandler extends android.hardware.camera2.CameraCaptureSession.StateCallback {
        private SessionStateHandler() {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onClosed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.release(false);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            synchronized (android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mCaptureSession = cameraCaptureSession;
                android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mStatsAggregator.commit(false);
            }
            try {
                android.hardware.camera2.CameraExtensionCharacteristics.initializeSession(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mInitializeHandler, android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mExtensionType);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to initialize session! Extension service does not respond!");
                android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.notifyConfigurationFailure();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class InitializeSessionHandler extends android.hardware.camera2.extension.IInitializeSessionCallback.Stub {
        private InitializeSessionHandler() {
        }

        /* renamed from: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$InitializeSessionHandler$1, reason: invalid class name */
        class AnonymousClass1 implements java.lang.Runnable {
            AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                synchronized (android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                    z = false;
                    try {
                        if (android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mSessionProcessor != null) {
                            android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mInitialized = true;
                            android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mSessionProcessor.onCaptureSessionStart(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mRequestProcessor, android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mStatsAggregator.getStatsKey());
                            z = true;
                        } else {
                            android.util.Log.v(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to start capture session, session  released before extension start!");
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to start capture session, extension service does not respond!");
                        android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mInitialized = z;
                    }
                }
                if (z) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$InitializeSessionHandler$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.InitializeSessionHandler.AnonymousClass1.this.lambda$run$0();
                            }
                        });
                        return;
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.InitializeSessionHandler.this.onFailure();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$run$0() {
                android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mCallbacks.onConfigured(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this);
            }
        }

        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onSuccess() {
            android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mHandler.post(new android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.InitializeSessionHandler.AnonymousClass1());
        }

        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onFailure() {
            android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.InitializeSessionHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mCaptureSession.close();
                    android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to initialize proxy service session! This can happen when trying to configure multiple concurrent extension sessions!");
                    android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.notifyConfigurationFailure();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class RequestCallbackHandler extends android.hardware.camera2.extension.ICaptureCallback.Stub {
        private final java.lang.String mCameraId;
        private final android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback mClientCallbacks;
        private final java.util.concurrent.Executor mClientExecutor;
        private final android.hardware.camera2.CaptureRequest mClientRequest;

        private RequestCallbackHandler(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback, java.lang.String str) {
            this.mClientRequest = captureRequest;
            this.mClientExecutor = executor;
            this.mClientCallbacks = extensionCaptureCallback;
            this.mCameraId = str;
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureStarted(int i, final long j) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureStarted$0(j);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureStarted$0(long j) {
            this.mClientCallbacks.onCaptureStarted(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this, this.mClientRequest, j);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureProcessStarted(int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureProcessStarted$1();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProcessStarted$1() {
            this.mClientCallbacks.onCaptureProcessStarted(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this, this.mClientRequest);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureFailed(int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureFailed$2();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureFailed$2() {
            this.mClientCallbacks.onCaptureFailed(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this, this.mClientRequest);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureProcessFailed(int i, final int i2) {
            if (com.android.internal.camera.flags.Flags.concertMode()) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mClientExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureProcessFailed$3(i2);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProcessFailed$3(int i) {
            this.mClientCallbacks.onCaptureFailed(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this, this.mClientRequest, i);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureSequenceCompleted(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureSequenceCompleted$4(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$4(int i) {
            this.mClientCallbacks.onCaptureSequenceCompleted(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this, i);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureSequenceAborted(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureSequenceAborted$5(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceAborted$5(int i) {
            this.mClientCallbacks.onCaptureSequenceAborted(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this, i);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureCompleted(long j, int i, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) {
            if (cameraMetadataNative == null) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture result!");
                return;
            }
            cameraMetadataNative.set((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key<java.lang.Long>>) android.hardware.camera2.CaptureResult.SENSOR_TIMESTAMP, (android.hardware.camera2.CaptureResult.Key<java.lang.Long>) java.lang.Long.valueOf(j));
            final android.hardware.camera2.TotalCaptureResult totalCaptureResult = new android.hardware.camera2.TotalCaptureResult(this.mCameraId, cameraMetadataNative, this.mClientRequest, i, j, new java.util.ArrayList(), android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mSessionId, new android.hardware.camera2.impl.PhysicalCaptureResultInfo[0]);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureCompleted$6(totalCaptureResult);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$6(android.hardware.camera2.TotalCaptureResult totalCaptureResult) {
            this.mClientCallbacks.onCaptureResultAvailable(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this, this.mClientRequest, totalCaptureResult);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureProcessProgressed(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureProcessProgressed$7(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProcessProgressed$7(int i) {
            this.mClientCallbacks.onCaptureProcessProgressed(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this, this.mClientRequest, i);
        }
    }

    private final class CaptureCallbackHandler extends android.hardware.camera2.CameraCaptureSession.CaptureCallback {
        private final android.hardware.camera2.extension.IRequestCallback mCallback;

        public CaptureCallbackHandler(android.hardware.camera2.extension.IRequestCallback iRequestCallback) {
            this.mCallback = iRequestCallback;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureBufferLost(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.view.Surface surface, long j) {
            try {
                if (!(captureRequest.getTag() instanceof java.lang.Integer)) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                } else {
                    this.mCallback.onCaptureBufferLost(((java.lang.Integer) captureRequest.getTag()).intValue(), j, ((android.hardware.camera2.extension.CameraOutputConfig) android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap.get(surface)).outputId.id);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify lost capture buffer, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureCompleted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.TotalCaptureResult totalCaptureResult) {
            try {
                if (!(captureRequest.getTag() instanceof java.lang.Integer)) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                } else {
                    this.mCallback.onCaptureCompleted(((java.lang.Integer) captureRequest.getTag()).intValue(), android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.initializeParcelable(totalCaptureResult));
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify capture result, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureFailure captureFailure) {
            try {
                if (!(captureRequest.getTag() instanceof java.lang.Integer)) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                } else {
                    java.lang.Integer num = (java.lang.Integer) captureRequest.getTag();
                    android.hardware.camera2.extension.CaptureFailure captureFailure2 = new android.hardware.camera2.extension.CaptureFailure();
                    captureFailure2.request = captureRequest;
                    captureFailure2.reason = captureFailure.getReason();
                    captureFailure2.errorPhysicalCameraId = captureFailure.getPhysicalCameraId();
                    captureFailure2.frameNumber = captureFailure.getFrameNumber();
                    captureFailure2.sequenceId = captureFailure.getSequenceId();
                    captureFailure2.dropped = !captureFailure.wasImageCaptured();
                    this.mCallback.onCaptureFailed(num.intValue(), captureFailure2);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify capture failure, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureProgressed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureResult captureResult) {
            try {
                if (!(captureRequest.getTag() instanceof java.lang.Integer)) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                } else {
                    this.mCallback.onCaptureProgressed(((java.lang.Integer) captureRequest.getTag()).intValue(), android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.initializeParcelable(captureResult));
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify capture partial result, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, int i) {
            try {
                this.mCallback.onCaptureSequenceAborted(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify aborted sequence, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, int i, long j) {
            try {
                this.mCallback.onCaptureSequenceCompleted(i, j);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify sequence complete, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, long j, long j2) {
            try {
                if (!(captureRequest.getTag() instanceof java.lang.Integer)) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                } else {
                    this.mCallback.onCaptureStarted(((java.lang.Integer) captureRequest.getTag()).intValue(), j2, j);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify capture started, extension service doesn't respond!");
            }
        }
    }

    private static final class ImageReaderHandler implements android.media.ImageReader.OnImageAvailableListener {
        private final android.hardware.camera2.extension.IImageProcessorImpl mIImageProcessor;
        private final android.hardware.camera2.extension.OutputConfigId mOutputConfigId;
        private final java.lang.String mPhysicalCameraId;

        private ImageReaderHandler(int i, android.hardware.camera2.extension.IImageProcessorImpl iImageProcessorImpl, java.lang.String str) {
            this.mOutputConfigId = new android.hardware.camera2.extension.OutputConfigId();
            this.mOutputConfigId.id = i;
            this.mIImageProcessor = iImageProcessorImpl;
            this.mPhysicalCameraId = str;
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(android.media.ImageReader imageReader) {
            if (this.mIImageProcessor == null) {
                return;
            }
            try {
                android.media.Image acquireNextImage = imageReader.acquireNextImage();
                if (acquireNextImage == null) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Invalid image!");
                    return;
                }
                try {
                    imageReader.detachImage(acquireNextImage);
                    android.hardware.camera2.extension.ParcelImage parcelImage = new android.hardware.camera2.extension.ParcelImage();
                    parcelImage.buffer = acquireNextImage.getHardwareBuffer();
                    try {
                        android.hardware.SyncFence fence = acquireNextImage.getFence();
                        if (fence.isValid()) {
                            parcelImage.fence = fence.getFdDup();
                        }
                    } catch (java.io.IOException e) {
                        android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to parcel buffer fence!");
                    }
                    parcelImage.width = acquireNextImage.getWidth();
                    parcelImage.height = acquireNextImage.getHeight();
                    parcelImage.format = acquireNextImage.getFormat();
                    parcelImage.timestamp = acquireNextImage.getTimestamp();
                    parcelImage.transform = acquireNextImage.getTransform();
                    parcelImage.scalingMode = acquireNextImage.getScalingMode();
                    parcelImage.planeCount = acquireNextImage.getPlaneCount();
                    parcelImage.crop = acquireNextImage.getCropRect();
                    try {
                        try {
                            this.mIImageProcessor.onNextImageAvailable(this.mOutputConfigId, parcelImage, this.mPhysicalCameraId);
                        } finally {
                            parcelImage.buffer.close();
                            acquireNextImage.close();
                        }
                    } catch (android.os.RemoteException e2) {
                        android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to propagate image buffer on output surface id: " + this.mOutputConfigId + " extension service does not respond!");
                    }
                } catch (java.lang.Exception e3) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to detach image");
                    acquireNextImage.close();
                }
            } catch (java.lang.IllegalStateException e4) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to acquire image, too many images pending!");
            }
        }
    }

    private final class RequestProcessor extends android.hardware.camera2.extension.IRequestProcessorImpl.Stub {
        private RequestProcessor() {
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void setImageProcessor(android.hardware.camera2.extension.OutputConfigId outputConfigId, android.hardware.camera2.extension.IImageProcessorImpl iImageProcessorImpl) {
            synchronized (android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                if (android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mReaderMap.containsKey(java.lang.Integer.valueOf(outputConfigId.id))) {
                    android.media.ImageReader imageReader = (android.media.ImageReader) android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mReaderMap.get(java.lang.Integer.valueOf(outputConfigId.id));
                    if (android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap.containsKey(imageReader.getSurface())) {
                        imageReader.setOnImageAvailableListener(new android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.ImageReaderHandler(outputConfigId.id, iImageProcessorImpl, ((android.hardware.camera2.extension.CameraOutputConfig) android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap.get(imageReader.getSurface())).physicalCameraId), android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mHandler);
                    } else {
                        android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Camera output configuration for ImageReader with  config Id " + outputConfigId.id + " not found!");
                    }
                } else {
                    android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "ImageReader with output config id: " + outputConfigId.id + " not found!");
                }
            }
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int submit(android.hardware.camera2.extension.Request request, android.hardware.camera2.extension.IRequestCallback iRequestCallback) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(request);
            return submitBurst(arrayList, iRequestCallback);
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int submitBurst(java.util.List<android.hardware.camera2.extension.Request> list, android.hardware.camera2.extension.IRequestCallback iRequestCallback) {
            int i = -1;
            try {
            } catch (android.hardware.camera2.CameraAccessException e) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to submit capture requests!");
            } catch (java.lang.IllegalStateException e2) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Capture session closed!");
            }
            synchronized (android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                if (!android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mInitialized) {
                    return -1;
                }
                android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.CaptureCallbackHandler captureCallbackHandler = android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.new CaptureCallbackHandler(iRequestCallback);
                java.util.ArrayList arrayList = new java.util.ArrayList();
                java.util.Iterator<android.hardware.camera2.extension.Request> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.initializeCaptureRequest(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mCameraDevice, it.next(), android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap));
                }
                i = android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mCaptureSession.captureBurstRequests(arrayList, new android.hardware.camera2.impl.CameraExtensionUtils.HandlerExecutor(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mHandler), captureCallbackHandler);
                return i;
            }
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int setRepeating(android.hardware.camera2.extension.Request request, android.hardware.camera2.extension.IRequestCallback iRequestCallback) {
            int i = -1;
            try {
            } catch (android.hardware.camera2.CameraAccessException e) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed to enable repeating request!");
            } catch (java.lang.IllegalStateException e2) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Capture session closed!");
            }
            synchronized (android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                if (!android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mInitialized) {
                    return -1;
                }
                i = android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mCaptureSession.setSingleRepeatingRequest(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.initializeCaptureRequest(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mCameraDevice, request, android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap), new android.hardware.camera2.impl.CameraExtensionUtils.HandlerExecutor(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mHandler), android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.new CaptureCallbackHandler(iRequestCallback));
                return i;
            }
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void abortCaptures() {
            try {
                synchronized (android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                    if (android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mInitialized) {
                        android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mCaptureSession.abortCaptures();
                    }
                }
            } catch (android.hardware.camera2.CameraAccessException e) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed during capture abort!");
            } catch (java.lang.IllegalStateException e2) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Capture session closed!");
            }
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void stopRepeating() {
            try {
                synchronized (android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                    if (android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mInitialized) {
                        android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.this.mCaptureSession.stopRepeating();
                    }
                }
            } catch (android.hardware.camera2.CameraAccessException e) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Failed during repeating capture stop!");
            } catch (java.lang.IllegalStateException e2) {
                android.util.Log.e(android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.TAG, "Capture session closed!");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.hardware.camera2.CaptureRequest initializeCaptureRequest(android.hardware.camera2.CameraDevice cameraDevice, android.hardware.camera2.extension.Request request, java.util.HashMap<android.view.Surface, android.hardware.camera2.extension.CameraOutputConfig> hashMap) throws android.hardware.camera2.CameraAccessException {
        boolean z;
        android.hardware.camera2.CaptureRequest.Builder createCaptureRequest = cameraDevice.createCaptureRequest(request.templateId);
        for (android.hardware.camera2.extension.OutputConfigId outputConfigId : request.targetOutputConfigIds) {
            java.util.Iterator<java.util.Map.Entry<android.view.Surface, android.hardware.camera2.extension.CameraOutputConfig>> it = hashMap.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                java.util.Map.Entry<android.view.Surface, android.hardware.camera2.extension.CameraOutputConfig> next = it.next();
                if (next.getValue().outputId.id == outputConfigId.id) {
                    createCaptureRequest.addTarget(next.getKey());
                    z = true;
                    break;
                }
            }
            if (!z) {
                android.util.Log.e(TAG, "Surface with output id: " + outputConfigId.id + " not found among registered camera outputs!");
            }
        }
        createCaptureRequest.setTag(java.lang.Integer.valueOf(request.requestId));
        android.hardware.camera2.CaptureRequest build = createCaptureRequest.build();
        android.hardware.camera2.impl.CameraMetadataNative.update(build.getNativeMetadata(), request.parameters);
        return build;
    }

    private android.view.Surface initializeSurface(android.hardware.camera2.extension.CameraOutputConfig cameraOutputConfig) {
        switch (cameraOutputConfig.type) {
            case 0:
                if (cameraOutputConfig.surface == null) {
                    android.util.Log.w(TAG, "Unsupported client output id: " + cameraOutputConfig.outputId.id + ", skipping!");
                    return null;
                }
                return cameraOutputConfig.surface;
            case 1:
                if (cameraOutputConfig.imageFormat == 0 || cameraOutputConfig.size.width <= 0 || cameraOutputConfig.size.height <= 0) {
                    android.util.Log.w(TAG, "Unsupported client output id: " + cameraOutputConfig.outputId.id + ", skipping!");
                    return null;
                }
                android.media.ImageReader newInstance = android.media.ImageReader.newInstance(cameraOutputConfig.size.width, cameraOutputConfig.size.height, cameraOutputConfig.imageFormat, cameraOutputConfig.capacity, cameraOutputConfig.usage);
                this.mReaderMap.put(java.lang.Integer.valueOf(cameraOutputConfig.outputId.id), newInstance);
                return newInstance.getSurface();
            default:
                throw new java.lang.IllegalArgumentException("Unsupported output config type: " + cameraOutputConfig.type);
        }
    }
}
