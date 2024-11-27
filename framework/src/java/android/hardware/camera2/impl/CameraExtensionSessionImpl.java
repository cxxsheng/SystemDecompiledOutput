package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public final class CameraExtensionSessionImpl extends android.hardware.camera2.CameraExtensionSession {
    private static final int PREVIEW_QUEUE_SIZE = 10;
    private static final java.lang.String TAG = "CameraExtensionSessionImpl";
    private final android.hardware.camera2.CameraExtensionSession.StateCallback mCallbacks;
    private android.view.Surface mCameraBurstSurface;
    private final android.hardware.camera2.CameraDevice mCameraDevice;
    private android.view.Surface mCameraRepeatingSurface;
    private boolean mCaptureResultsSupported;
    private android.view.Surface mClientCaptureSurface;
    private android.view.Surface mClientPostviewSurface;
    private android.view.Surface mClientRepeatingRequestSurface;
    private final android.content.Context mContext;
    private final java.util.concurrent.Executor mExecutor;
    private int mExtensionType;
    private final android.os.Handler mHandler;
    private final android.hardware.camera2.extension.IImageCaptureExtenderImpl mImageExtender;
    private final android.hardware.camera2.impl.CameraExtensionSessionImpl.InitializeSessionHandler mInitializeHandler;
    private boolean mInitialized;
    final java.lang.Object mInterfaceLock;
    private final android.hardware.camera2.extension.IPreviewExtenderImpl mPreviewExtender;
    private boolean mSessionClosed;
    private final int mSessionId;
    private final android.hardware.camera2.utils.ExtensionSessionStatsAggregator mStatsAggregator;
    private final java.util.List<android.util.Size> mSupportedPreviewSizes;
    private final java.util.Set<android.hardware.camera2.CaptureRequest.Key> mSupportedRequestKeys;
    private final java.util.Set<android.hardware.camera2.CaptureResult.Key> mSupportedResultKeys;
    private android.os.IBinder mToken;
    private android.hardware.camera2.CameraCaptureSession mCaptureSession = null;
    private android.media.ImageReader mRepeatingRequestImageReader = null;
    private android.media.ImageReader mBurstCaptureImageReader = null;
    private android.media.ImageReader mStubCaptureImageReader = null;
    private android.media.ImageWriter mRepeatingRequestImageWriter = null;
    private android.hardware.camera2.impl.CameraExtensionSessionImpl.CameraOutputImageCallback mRepeatingRequestImageCallback = null;
    private android.hardware.camera2.impl.CameraExtensionSessionImpl.CameraOutputImageCallback mBurstCaptureImageCallback = null;
    private android.hardware.camera2.impl.CameraExtensionJpegProcessor mImageJpegProcessor = null;
    private android.hardware.camera2.extension.ICaptureProcessorImpl mImageProcessor = null;
    private android.hardware.camera2.impl.CameraExtensionForwardProcessor mPreviewImageProcessor = null;
    private android.hardware.camera2.extension.IRequestUpdateProcessorImpl mPreviewRequestUpdateProcessor = null;
    private int mPreviewProcessorType = 2;
    private boolean mInternalRepeatingRequestEnabled = true;
    private final android.os.HandlerThread mHandlerThread = new android.os.HandlerThread(TAG);

    private interface OnImageAvailableListener {
        void onImageAvailable(android.media.ImageReader imageReader, android.media.Image image);

        void onImageDropped(long j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int nativeGetSurfaceFormat(android.view.Surface surface) {
        return android.hardware.camera2.utils.SurfaceUtils.getSurfaceFormat(surface);
    }

    public static android.hardware.camera2.impl.CameraExtensionSessionImpl createCameraExtensionSession(android.hardware.camera2.impl.CameraDeviceImpl cameraDeviceImpl, java.util.Map<java.lang.String, android.hardware.camera2.CameraCharacteristics> map, android.content.Context context, android.hardware.camera2.params.ExtensionSessionConfiguration extensionSessionConfiguration, int i, android.os.IBinder iBinder) throws android.hardware.camera2.CameraAccessException, android.os.RemoteException {
        int i2;
        android.view.Surface surface;
        java.lang.String id = cameraDeviceImpl.getId();
        android.hardware.camera2.CameraExtensionCharacteristics cameraExtensionCharacteristics = new android.hardware.camera2.CameraExtensionCharacteristics(context, id, map);
        if (!android.hardware.camera2.CameraExtensionCharacteristics.isExtensionSupported(cameraDeviceImpl.getId(), extensionSessionConfiguration.getExtension(), android.hardware.camera2.impl.CameraExtensionUtils.getCharacteristicsMapNative(map))) {
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
        android.util.Pair<android.hardware.camera2.extension.IPreviewExtenderImpl, android.hardware.camera2.extension.IImageCaptureExtenderImpl> initializeExtension = android.hardware.camera2.CameraExtensionCharacteristics.initializeExtension(extensionSessionConfiguration.getExtension());
        java.util.List<android.util.Size> extensionSupportedSizes = cameraExtensionCharacteristics.getExtensionSupportedSizes(extensionSessionConfiguration.getExtension(), android.graphics.SurfaceTexture.class);
        android.view.Surface repeatingRequestSurface = android.hardware.camera2.impl.CameraExtensionUtils.getRepeatingRequestSurface(extensionSessionConfiguration.getOutputConfigurations(), extensionSupportedSizes);
        int i3 = 0;
        if (repeatingRequestSurface == null) {
            i2 = 0;
        } else {
            i2 = 1;
        }
        java.util.HashMap hashMap = new java.util.HashMap();
        for (int i4 : android.hardware.camera2.impl.CameraExtensionUtils.SUPPORTED_CAPTURE_OUTPUT_FORMATS) {
            java.util.List<android.util.Size> extensionSupportedSizes2 = cameraExtensionCharacteristics.getExtensionSupportedSizes(extensionSessionConfiguration.getExtension(), i4);
            if (extensionSupportedSizes2 != null) {
                hashMap.put(java.lang.Integer.valueOf(i4), extensionSupportedSizes2);
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
            int[] iArr = android.hardware.camera2.impl.CameraExtensionUtils.SUPPORTED_CAPTURE_OUTPUT_FORMATS;
            int length = iArr.length;
            while (i3 < length) {
                int i5 = iArr[i3];
                int[] iArr2 = iArr;
                java.util.List<android.util.Size> postviewSupportedSizes = cameraExtensionCharacteristics.getPostviewSupportedSizes(extensionSessionConfiguration.getExtension(), size, i5);
                if (postviewSupportedSizes != null) {
                    hashMap2.put(java.lang.Integer.valueOf(i5), postviewSupportedSizes);
                }
                i3++;
                iArr = iArr2;
            }
            android.view.Surface postviewSurface = android.hardware.camera2.impl.CameraExtensionUtils.getPostviewSurface(extensionSessionConfiguration.getPostviewOutputConfiguration(), hashMap2, querySurface.mFormat);
            if (postviewSurface == null) {
                throw new java.lang.IllegalArgumentException("Unsupported output surface for postview!");
            }
            surface = postviewSurface;
        } else {
            surface = null;
        }
        initializeExtension.first.init(id, map.get(id).getNativeMetadata());
        initializeExtension.first.onInit(iBinder, id, map.get(id).getNativeMetadata());
        initializeExtension.second.init(id, map.get(id).getNativeMetadata());
        initializeExtension.second.onInit(iBinder, id, map.get(id).getNativeMetadata());
        android.hardware.camera2.impl.CameraExtensionSessionImpl cameraExtensionSessionImpl = new android.hardware.camera2.impl.CameraExtensionSessionImpl(context, initializeExtension.second, initializeExtension.first, extensionSupportedSizes, cameraDeviceImpl, repeatingRequestSurface, burstCaptureSurface, surface, extensionSessionConfiguration.getStateCallback(), extensionSessionConfiguration.getExecutor(), i, iBinder, cameraExtensionCharacteristics.getAvailableCaptureRequestKeys(extensionSessionConfiguration.getExtension()), cameraExtensionCharacteristics.getAvailableCaptureResultKeys(extensionSessionConfiguration.getExtension()), extensionSessionConfiguration.getExtension());
        cameraExtensionSessionImpl.mStatsAggregator.setClientName(context.getOpPackageName());
        cameraExtensionSessionImpl.mStatsAggregator.setExtensionType(extensionSessionConfiguration.getExtension());
        cameraExtensionSessionImpl.initialize();
        return cameraExtensionSessionImpl;
    }

    public CameraExtensionSessionImpl(android.content.Context context, android.hardware.camera2.extension.IImageCaptureExtenderImpl iImageCaptureExtenderImpl, android.hardware.camera2.extension.IPreviewExtenderImpl iPreviewExtenderImpl, java.util.List<android.util.Size> list, android.hardware.camera2.impl.CameraDeviceImpl cameraDeviceImpl, android.view.Surface surface, android.view.Surface surface2, android.view.Surface surface3, android.hardware.camera2.CameraExtensionSession.StateCallback stateCallback, java.util.concurrent.Executor executor, int i, android.os.IBinder iBinder, java.util.Set<android.hardware.camera2.CaptureRequest.Key> set, java.util.Set<android.hardware.camera2.CaptureResult.Key> set2, int i2) {
        this.mToken = null;
        this.mContext = context;
        this.mImageExtender = iImageCaptureExtenderImpl;
        this.mPreviewExtender = iPreviewExtenderImpl;
        this.mCameraDevice = cameraDeviceImpl;
        this.mCallbacks = stateCallback;
        this.mExecutor = executor;
        this.mClientRepeatingRequestSurface = surface;
        this.mClientCaptureSurface = surface2;
        this.mClientPostviewSurface = surface3;
        this.mSupportedPreviewSizes = list;
        this.mHandlerThread.start();
        this.mHandler = new android.os.Handler(this.mHandlerThread.getLooper());
        this.mInitialized = false;
        this.mSessionClosed = false;
        this.mInitializeHandler = new android.hardware.camera2.impl.CameraExtensionSessionImpl.InitializeSessionHandler();
        this.mSessionId = i;
        this.mToken = iBinder;
        this.mSupportedRequestKeys = set;
        this.mSupportedResultKeys = set2;
        this.mCaptureResultsSupported = !set2.isEmpty();
        this.mInterfaceLock = cameraDeviceImpl.mInterfaceLock;
        this.mExtensionType = i2;
        this.mStatsAggregator = new android.hardware.camera2.utils.ExtensionSessionStatsAggregator(this.mCameraDevice.getId(), false);
    }

    private void initializeRepeatingRequestPipeline() throws android.os.RemoteException {
        android.hardware.camera2.impl.CameraExtensionUtils.SurfaceInfo surfaceInfo = new android.hardware.camera2.impl.CameraExtensionUtils.SurfaceInfo();
        this.mPreviewProcessorType = this.mPreviewExtender.getProcessorType();
        if (this.mClientRepeatingRequestSurface != null) {
            surfaceInfo = android.hardware.camera2.impl.CameraExtensionUtils.querySurface(this.mClientRepeatingRequestSurface);
        } else {
            android.hardware.camera2.impl.CameraExtensionUtils.SurfaceInfo querySurface = android.hardware.camera2.impl.CameraExtensionUtils.querySurface(this.mClientCaptureSurface);
            android.util.Size findSmallestAspectMatchedSize = findSmallestAspectMatchedSize(this.mSupportedPreviewSizes, new android.util.Size(querySurface.mWidth, querySurface.mHeight));
            surfaceInfo.mWidth = findSmallestAspectMatchedSize.getWidth();
            surfaceInfo.mHeight = findSmallestAspectMatchedSize.getHeight();
            surfaceInfo.mUsage = 256L;
        }
        if (this.mPreviewProcessorType == 1) {
            try {
                this.mPreviewImageProcessor = new android.hardware.camera2.impl.CameraExtensionForwardProcessor(this.mPreviewExtender.getPreviewImageProcessor(), surfaceInfo.mFormat, surfaceInfo.mUsage, this.mHandler);
                this.mPreviewImageProcessor.onImageFormatUpdate(35);
                this.mPreviewImageProcessor.onResolutionUpdate(new android.util.Size(surfaceInfo.mWidth, surfaceInfo.mHeight));
                this.mPreviewImageProcessor.onOutputSurface(null, -1);
                this.mRepeatingRequestImageReader = android.media.ImageReader.newInstance(surfaceInfo.mWidth, surfaceInfo.mHeight, 35, 10, surfaceInfo.mUsage);
                this.mCameraRepeatingSurface = this.mRepeatingRequestImageReader.getSurface();
            } catch (java.lang.ClassCastException e) {
                throw new java.lang.UnsupportedOperationException("Failed casting preview processor!");
            }
        } else if (this.mPreviewProcessorType == 0) {
            try {
                this.mPreviewRequestUpdateProcessor = this.mPreviewExtender.getRequestUpdateProcessor();
                this.mRepeatingRequestImageReader = android.media.ImageReader.newInstance(surfaceInfo.mWidth, surfaceInfo.mHeight, 34, 10, surfaceInfo.mUsage);
                this.mCameraRepeatingSurface = this.mRepeatingRequestImageReader.getSurface();
                android.hardware.camera2.extension.Size size = new android.hardware.camera2.extension.Size();
                size.width = surfaceInfo.mWidth;
                size.height = surfaceInfo.mHeight;
                this.mPreviewRequestUpdateProcessor.onResolutionUpdate(size);
                this.mPreviewRequestUpdateProcessor.onImageFormatUpdate(34);
            } catch (java.lang.ClassCastException e2) {
                throw new java.lang.UnsupportedOperationException("Failed casting preview processor!");
            }
        } else {
            this.mRepeatingRequestImageReader = android.media.ImageReader.newInstance(surfaceInfo.mWidth, surfaceInfo.mHeight, 34, 10, surfaceInfo.mUsage);
            this.mCameraRepeatingSurface = this.mRepeatingRequestImageReader.getSurface();
        }
        this.mRepeatingRequestImageCallback = new android.hardware.camera2.impl.CameraExtensionSessionImpl.CameraOutputImageCallback(this.mRepeatingRequestImageReader, true);
        this.mRepeatingRequestImageReader.setOnImageAvailableListener(this.mRepeatingRequestImageCallback, this.mHandler);
    }

    private void initializeBurstCapturePipeline() throws android.os.RemoteException {
        this.mImageProcessor = this.mImageExtender.getCaptureProcessor();
        if (this.mImageProcessor == null && this.mImageExtender.getMaxCaptureStage() != 1) {
            throw new java.lang.UnsupportedOperationException("Multiple stages expected without a valid capture processor!");
        }
        if (this.mImageProcessor != null) {
            if (this.mClientCaptureSurface != null) {
                android.hardware.camera2.impl.CameraExtensionUtils.SurfaceInfo querySurface = android.hardware.camera2.impl.CameraExtensionUtils.querySurface(this.mClientCaptureSurface);
                if (querySurface.mFormat == 256) {
                    this.mImageJpegProcessor = new android.hardware.camera2.impl.CameraExtensionJpegProcessor(this.mImageProcessor);
                    this.mImageProcessor = this.mImageJpegProcessor;
                }
                this.mBurstCaptureImageReader = android.media.ImageReader.newInstance(querySurface.mWidth, querySurface.mHeight, 35, this.mImageExtender.getMaxCaptureStage());
            } else {
                this.mBurstCaptureImageReader = android.media.ImageReader.newInstance(this.mRepeatingRequestImageReader.getWidth(), this.mRepeatingRequestImageReader.getHeight(), 35, 1);
                this.mStubCaptureImageReader = android.media.ImageReader.newInstance(this.mRepeatingRequestImageReader.getWidth(), this.mRepeatingRequestImageReader.getHeight(), 35, 1);
                this.mImageProcessor.onOutputSurface(this.mStubCaptureImageReader.getSurface(), 35);
            }
            this.mBurstCaptureImageCallback = new android.hardware.camera2.impl.CameraExtensionSessionImpl.CameraOutputImageCallback(this.mBurstCaptureImageReader, false);
            this.mBurstCaptureImageReader.setOnImageAvailableListener(this.mBurstCaptureImageCallback, this.mHandler);
            this.mCameraBurstSurface = this.mBurstCaptureImageReader.getSurface();
            android.hardware.camera2.extension.Size size = new android.hardware.camera2.extension.Size();
            size.width = this.mBurstCaptureImageReader.getWidth();
            size.height = this.mBurstCaptureImageReader.getHeight();
            if (this.mClientPostviewSurface != null) {
                android.hardware.camera2.impl.CameraExtensionUtils.SurfaceInfo querySurface2 = android.hardware.camera2.impl.CameraExtensionUtils.querySurface(this.mClientPostviewSurface);
                android.hardware.camera2.extension.Size size2 = new android.hardware.camera2.extension.Size();
                size2.width = querySurface2.mWidth;
                size2.height = querySurface2.mHeight;
                this.mImageProcessor.onResolutionUpdate(size, size2);
            } else {
                this.mImageProcessor.onResolutionUpdate(size, null);
            }
            this.mImageProcessor.onImageFormatUpdate(this.mBurstCaptureImageReader.getImageFormat());
            return;
        }
        if (this.mClientCaptureSurface != null) {
            this.mCameraBurstSurface = this.mClientCaptureSurface;
        } else {
            this.mBurstCaptureImageReader = android.media.ImageReader.newInstance(this.mRepeatingRequestImageReader.getWidth(), this.mRepeatingRequestImageReader.getHeight(), 256, 1);
            this.mCameraBurstSurface = this.mBurstCaptureImageReader.getSurface();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishPipelineInitialization() throws android.os.RemoteException {
        if (this.mClientRepeatingRequestSurface != null) {
            if (this.mPreviewProcessorType == 0) {
                this.mPreviewRequestUpdateProcessor.onOutputSurface(this.mClientRepeatingRequestSurface, nativeGetSurfaceFormat(this.mClientRepeatingRequestSurface));
                this.mRepeatingRequestImageWriter = android.media.ImageWriter.newInstance(this.mClientRepeatingRequestSurface, 10, 34);
            } else if (this.mPreviewProcessorType == 2) {
                this.mRepeatingRequestImageWriter = android.media.ImageWriter.newInstance(this.mClientRepeatingRequestSurface, 10, 34);
            }
        }
        if (this.mImageProcessor != null && this.mClientCaptureSurface != null) {
            if (this.mClientPostviewSurface != null) {
                this.mImageProcessor.onPostviewOutputSurface(this.mClientPostviewSurface);
            }
            this.mImageProcessor.onOutputSurface(this.mClientCaptureSurface, android.hardware.camera2.impl.CameraExtensionUtils.querySurface(this.mClientCaptureSurface).mFormat);
        }
    }

    public synchronized void initialize() throws android.hardware.camera2.CameraAccessException, android.os.RemoteException {
        if (this.mInitialized) {
            android.util.Log.d(TAG, "Session already initialized");
            return;
        }
        int sessionType = this.mPreviewExtender.getSessionType();
        int sessionType2 = this.mImageExtender.getSessionType();
        if (sessionType != sessionType2) {
            throw new java.lang.IllegalStateException("Preview extender session type: " + sessionType + "and image extender session type: " + sessionType2 + " mismatch!");
        }
        if (sessionType != -1 && sessionType != 1) {
            android.util.Log.v(TAG, "Using session type: " + sessionType);
        } else {
            sessionType = 0;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        initializeRepeatingRequestPipeline();
        android.hardware.camera2.params.OutputConfiguration outputConfiguration = new android.hardware.camera2.params.OutputConfiguration(this.mCameraRepeatingSurface);
        outputConfiguration.setTimestampBase(1);
        outputConfiguration.setReadoutTimestampEnabled(false);
        arrayList2.add(outputConfiguration);
        android.hardware.camera2.extension.CaptureStageImpl onPresetSession = this.mPreviewExtender.onPresetSession();
        if (onPresetSession != null) {
            arrayList.add(onPresetSession);
        }
        initializeBurstCapturePipeline();
        android.hardware.camera2.params.OutputConfiguration outputConfiguration2 = new android.hardware.camera2.params.OutputConfiguration(this.mCameraBurstSurface);
        outputConfiguration2.setTimestampBase(1);
        outputConfiguration2.setReadoutTimestampEnabled(false);
        arrayList2.add(outputConfiguration2);
        android.hardware.camera2.extension.CaptureStageImpl onPresetSession2 = this.mImageExtender.onPresetSession();
        if (onPresetSession2 != null) {
            arrayList.add(onPresetSession2);
        }
        android.hardware.camera2.params.SessionConfiguration sessionConfiguration = new android.hardware.camera2.params.SessionConfiguration(sessionType, arrayList2, new android.hardware.camera2.impl.CameraExtensionUtils.HandlerExecutor(this.mHandler), new android.hardware.camera2.impl.CameraExtensionSessionImpl.SessionStateHandler());
        if (!arrayList.isEmpty()) {
            sessionConfiguration.setSessionParameters(createRequest(this.mCameraDevice, arrayList, null, 1));
        }
        this.mCameraDevice.createCaptureSession(sessionConfiguration);
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
                android.hardware.camera2.extension.LatencyPair realtimeCaptureLatency = this.mImageExtender.getRealtimeCaptureLatency();
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
        int repeatingRequest;
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
            this.mInternalRepeatingRequestEnabled = false;
            try {
                repeatingRequest = setRepeatingRequest(this.mPreviewExtender.getCaptureStage(), new android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler(this, captureRequest, executor, extensionCaptureCallback, this.mRepeatingRequestImageCallback), captureRequest);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to set repeating request! Extension service does not respond");
                throw new android.hardware.camera2.CameraAccessException(3);
            }
        }
        return repeatingRequest;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.ArrayList<android.hardware.camera2.extension.CaptureStageImpl> compileInitialRequestList() {
        java.util.ArrayList<android.hardware.camera2.extension.CaptureStageImpl> arrayList = new java.util.ArrayList<>();
        try {
            android.hardware.camera2.extension.CaptureStageImpl onEnableSession = this.mPreviewExtender.onEnableSession();
            if (onEnableSession != null) {
                arrayList.add(onEnableSession);
            }
            android.hardware.camera2.extension.CaptureStageImpl onEnableSession2 = this.mImageExtender.onEnableSession();
            if (onEnableSession2 != null) {
                arrayList.add(onEnableSession2);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to initialize session parameters! Extension service does not respond!");
        }
        return arrayList;
    }

    private java.util.List<android.hardware.camera2.CaptureRequest> createBurstRequest(android.hardware.camera2.CameraDevice cameraDevice, java.util.List<android.hardware.camera2.extension.CaptureStageImpl> list, android.hardware.camera2.CaptureRequest captureRequest, android.view.Surface surface, int i, java.util.Map<android.hardware.camera2.CaptureRequest, java.lang.Integer> map) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.hardware.camera2.extension.CaptureStageImpl captureStageImpl : list) {
            try {
                android.hardware.camera2.CaptureRequest.Builder createCaptureRequest = cameraDevice.createCaptureRequest(i);
                for (android.hardware.camera2.CaptureRequest.Key key : this.mSupportedRequestKeys) {
                    java.lang.Object obj = captureRequest.get(key);
                    if (obj != null) {
                        captureStageImpl.parameters.set((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key>) key, (android.hardware.camera2.CaptureRequest.Key) obj);
                    }
                }
                createCaptureRequest.addTarget(surface);
                android.hardware.camera2.CaptureRequest build = createCaptureRequest.build();
                android.hardware.camera2.impl.CameraMetadataNative.update(build.getNativeMetadata(), captureStageImpl.parameters);
                arrayList.add(build);
                if (map != null) {
                    map.put(build, java.lang.Integer.valueOf(captureStageImpl.id));
                }
            } catch (android.hardware.camera2.CameraAccessException e) {
                return null;
            }
        }
        return arrayList;
    }

    private android.hardware.camera2.CaptureRequest createRequest(android.hardware.camera2.CameraDevice cameraDevice, java.util.List<android.hardware.camera2.extension.CaptureStageImpl> list, android.view.Surface surface, int i, android.hardware.camera2.CaptureRequest captureRequest) throws android.hardware.camera2.CameraAccessException {
        android.hardware.camera2.CaptureRequest.Builder createCaptureRequest = cameraDevice.createCaptureRequest(i);
        if (surface != null) {
            createCaptureRequest.addTarget(surface);
        }
        android.hardware.camera2.CaptureRequest build = createCaptureRequest.build();
        android.hardware.camera2.impl.CameraMetadataNative nativeMetadata = build.getNativeMetadata();
        for (android.hardware.camera2.extension.CaptureStageImpl captureStageImpl : list) {
            if (captureStageImpl != null) {
                android.hardware.camera2.impl.CameraMetadataNative.update(nativeMetadata, captureStageImpl.parameters);
            }
        }
        if (captureRequest != null) {
            for (android.hardware.camera2.CaptureRequest.Key key : this.mSupportedRequestKeys) {
                java.lang.Object obj = captureRequest.get(key);
                if (obj != null) {
                    nativeMetadata.set((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key>) key, (android.hardware.camera2.CaptureRequest.Key) obj);
                }
            }
        }
        return build;
    }

    private android.hardware.camera2.CaptureRequest createRequest(android.hardware.camera2.CameraDevice cameraDevice, java.util.List<android.hardware.camera2.extension.CaptureStageImpl> list, android.view.Surface surface, int i) throws android.hardware.camera2.CameraAccessException {
        return createRequest(cameraDevice, list, surface, i, null);
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public int capture(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback) throws android.hardware.camera2.CameraAccessException {
        if (!this.mInitialized) {
            throw new java.lang.IllegalStateException("Uninitialized component");
        }
        validateCaptureRequestTargets(captureRequest);
        if (this.mClientCaptureSurface != null && captureRequest.containsTarget(this.mClientCaptureSurface)) {
            java.util.HashMap hashMap = new java.util.HashMap();
            try {
                java.util.List<android.hardware.camera2.CaptureRequest> createBurstRequest = createBurstRequest(this.mCameraDevice, this.mImageExtender.getCaptureStages(), captureRequest, this.mCameraBurstSurface, 2, hashMap);
                if (createBurstRequest == null) {
                    throw new java.lang.UnsupportedOperationException("Failed to create still capture burst request");
                }
                return this.mCaptureSession.captureBurstRequests(createBurstRequest, new android.hardware.camera2.impl.CameraExtensionUtils.HandlerExecutor(this.mHandler), new android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler(captureRequest, executor, extensionCaptureCallback, hashMap, this.mBurstCaptureImageCallback));
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to initialize internal burst request! Extension service does not respond!");
                throw new android.hardware.camera2.CameraAccessException(3);
            }
        }
        if (this.mClientRepeatingRequestSurface != null && captureRequest.containsTarget(this.mClientRepeatingRequestSurface)) {
            try {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                arrayList.add(this.mPreviewExtender.getCaptureStage());
                return this.mCaptureSession.capture(createRequest(this.mCameraDevice, arrayList, this.mCameraRepeatingSurface, 1, captureRequest), new android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler(captureRequest, executor, extensionCaptureCallback, this.mRepeatingRequestImageCallback, true), this.mHandler);
            } catch (android.os.RemoteException e2) {
                android.util.Log.e(TAG, "Failed to initialize capture request! Extension service does not respond!");
                throw new android.hardware.camera2.CameraAccessException(3);
            }
        }
        throw new java.lang.IllegalArgumentException("Capture request to unknown output surface!");
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
            this.mInternalRepeatingRequestEnabled = true;
            this.mCaptureSession.stopRepeating();
        }
    }

    @Override // android.hardware.camera2.CameraExtensionSession, java.lang.AutoCloseable
    public void close() throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (this.mInitialized) {
                this.mInternalRepeatingRequestEnabled = false;
                try {
                    this.mCaptureSession.stopRepeating();
                } catch (java.lang.IllegalStateException e) {
                    this.mSessionClosed = true;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                try {
                    android.hardware.camera2.extension.CaptureStageImpl onDisableSession = this.mPreviewExtender.onDisableSession();
                    if (onDisableSession != null) {
                        arrayList.add(onDisableSession);
                    }
                    android.hardware.camera2.extension.CaptureStageImpl onDisableSession2 = this.mImageExtender.onDisableSession();
                    if (onDisableSession2 != null) {
                        arrayList.add(onDisableSession2);
                    }
                } catch (android.os.RemoteException e2) {
                    android.util.Log.e(TAG, "Failed to disable extension! Extension service does not respond!");
                }
                if (!arrayList.isEmpty() && !this.mSessionClosed) {
                    this.mCaptureSession.capture(createRequest(this.mCameraDevice, arrayList, this.mCameraRepeatingSurface, 1), new android.hardware.camera2.impl.CameraExtensionSessionImpl.CloseRequestHandler(this.mRepeatingRequestImageCallback), this.mHandler);
                }
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

    /* JADX INFO: Access modifiers changed from: private */
    public void setInitialCaptureRequest(java.util.List<android.hardware.camera2.extension.CaptureStageImpl> list, android.hardware.camera2.impl.CameraExtensionSessionImpl.InitialRequestHandler initialRequestHandler) throws android.hardware.camera2.CameraAccessException {
        this.mCaptureSession.capture(createRequest(this.mCameraDevice, list, this.mCameraRepeatingSurface, 1), initialRequestHandler, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int setRepeatingRequest(android.hardware.camera2.extension.CaptureStageImpl captureStageImpl, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback) throws android.hardware.camera2.CameraAccessException {
        return setRepeatingRequest(captureStageImpl, captureCallback, (android.hardware.camera2.CaptureRequest) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int setRepeatingRequest(android.hardware.camera2.extension.CaptureStageImpl captureStageImpl, android.hardware.camera2.CameraCaptureSession.CaptureCallback captureCallback, android.hardware.camera2.CaptureRequest captureRequest) throws android.hardware.camera2.CameraAccessException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(captureStageImpl);
        return this.mCaptureSession.setSingleRepeatingRequest(createRequest(this.mCameraDevice, arrayList, this.mCameraRepeatingSurface, 1, captureRequest), new android.hardware.camera2.impl.CameraExtensionUtils.HandlerExecutor(this.mHandler), captureCallback);
    }

    public void release(boolean z) {
        boolean z2;
        synchronized (this.mInterfaceLock) {
            this.mInternalRepeatingRequestEnabled = false;
            this.mHandlerThread.quit();
            try {
                if (!this.mSessionClosed) {
                    this.mPreviewExtender.onDisableSession();
                    this.mImageExtender.onDisableSession();
                }
                this.mPreviewExtender.onDeInit(this.mToken);
                this.mImageExtender.onDeInit(this.mToken);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to release extensions! Extension service does not respond!");
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
            if (this.mRepeatingRequestImageCallback != null) {
                this.mRepeatingRequestImageCallback.close();
                this.mRepeatingRequestImageCallback = null;
            }
            if (this.mRepeatingRequestImageReader != null) {
                this.mRepeatingRequestImageReader.close();
                this.mRepeatingRequestImageReader = null;
            }
            if (this.mBurstCaptureImageCallback != null) {
                this.mBurstCaptureImageCallback.close();
                this.mBurstCaptureImageCallback = null;
            }
            if (this.mBurstCaptureImageReader != null) {
                this.mBurstCaptureImageReader.close();
                this.mBurstCaptureImageReader = null;
            }
            if (this.mStubCaptureImageReader != null) {
                this.mStubCaptureImageReader.close();
                this.mStubCaptureImageReader = null;
            }
            if (this.mRepeatingRequestImageWriter != null) {
                this.mRepeatingRequestImageWriter.close();
                this.mRepeatingRequestImageWriter = null;
            }
            if (this.mPreviewImageProcessor != null) {
                this.mPreviewImageProcessor.close();
                this.mPreviewImageProcessor = null;
            }
            if (this.mImageJpegProcessor != null) {
                this.mImageJpegProcessor.close();
                this.mImageJpegProcessor = null;
            }
            this.mCaptureSession = null;
            this.mImageProcessor = null;
            this.mClientRepeatingRequestSurface = null;
            this.mCameraRepeatingSurface = null;
            this.mClientCaptureSurface = null;
            this.mCameraBurstSurface = null;
            this.mClientPostviewSurface = null;
        }
        if (z2 && !z) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraExtensionSessionImpl.this.lambda$release$0();
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
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraExtensionSessionImpl.this.lambda$notifyConfigurationFailure$1();
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

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyConfigurationSuccess() {
        synchronized (this.mInterfaceLock) {
            if (this.mInitialized) {
                return;
            }
            this.mInitialized = true;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraExtensionSessionImpl.this.lambda$notifyConfigurationSuccess$2();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyConfigurationSuccess$2() {
        this.mCallbacks.onConfigured(this);
    }

    private class SessionStateHandler extends android.hardware.camera2.CameraCaptureSession.StateCallback {
        private SessionStateHandler() {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onClosed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            android.hardware.camera2.impl.CameraExtensionSessionImpl.this.release(false);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            android.hardware.camera2.impl.CameraExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            synchronized (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mInterfaceLock) {
                android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mCaptureSession = cameraCaptureSession;
                android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mStatsAggregator.commit(false);
                try {
                    android.hardware.camera2.impl.CameraExtensionSessionImpl.this.finishPipelineInitialization();
                    android.hardware.camera2.CameraExtensionCharacteristics.initializeSession(android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mInitializeHandler, android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mExtensionType);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Failed to initialize session! Extension service does not respond!");
                    android.hardware.camera2.impl.CameraExtensionSessionImpl.this.notifyConfigurationFailure();
                }
            }
        }
    }

    private class InitializeSessionHandler extends android.hardware.camera2.extension.IInitializeSessionCallback.Stub {
        private InitializeSessionHandler() {
        }

        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onSuccess() {
            android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl.InitializeSessionHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    java.util.ArrayList compileInitialRequestList = android.hardware.camera2.impl.CameraExtensionSessionImpl.this.compileInitialRequestList();
                    boolean z = false;
                    if (!compileInitialRequestList.isEmpty()) {
                        try {
                            android.hardware.camera2.impl.CameraExtensionSessionImpl.this.setInitialCaptureRequest(compileInitialRequestList, android.hardware.camera2.impl.CameraExtensionSessionImpl.this.new InitialRequestHandler(android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mRepeatingRequestImageCallback));
                        } catch (android.hardware.camera2.CameraAccessException e) {
                            android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Failed to initialize the initial capture request!");
                        }
                    } else {
                        try {
                            android.hardware.camera2.impl.CameraExtensionSessionImpl.this.setRepeatingRequest(android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mPreviewExtender.getCaptureStage(), new android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, null, null, null, android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mRepeatingRequestImageCallback));
                        } catch (android.hardware.camera2.CameraAccessException | android.os.RemoteException e2) {
                            android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Failed to initialize internal repeating request!");
                        }
                    }
                    z = true;
                    if (!z) {
                        android.hardware.camera2.impl.CameraExtensionSessionImpl.this.notifyConfigurationFailure();
                    }
                }
            });
        }

        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onFailure() {
            android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl.InitializeSessionHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mCaptureSession.close();
                    android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Failed to initialize proxy service session! This can happen when trying to configure multiple concurrent extension sessions!");
                    android.hardware.camera2.impl.CameraExtensionSessionImpl.this.notifyConfigurationFailure();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class BurstRequestHandler extends android.hardware.camera2.CameraCaptureSession.CaptureCallback {
        private final android.hardware.camera2.impl.CameraExtensionSessionImpl.CameraOutputImageCallback mBurstImageCallback;
        private final android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback mCallbacks;
        private final java.util.HashMap<android.hardware.camera2.CaptureRequest, java.lang.Integer> mCaptureRequestMap;
        private final android.hardware.camera2.CaptureRequest mClientRequest;
        private final java.util.concurrent.Executor mExecutor;
        private java.util.HashMap<java.lang.Integer, android.util.Pair<android.media.Image, android.hardware.camera2.TotalCaptureResult>> mCaptureStageMap = new java.util.HashMap<>();
        private android.util.LongSparseArray<android.util.Pair<android.media.Image, java.lang.Integer>> mCapturePendingMap = new android.util.LongSparseArray<>();
        private android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.ImageCallback mImageCallback = null;
        private boolean mCaptureFailed = false;
        private android.hardware.camera2.impl.CameraExtensionSessionImpl.CaptureResultHandler mCaptureResultHandler = null;

        public BurstRequestHandler(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback, java.util.HashMap<android.hardware.camera2.CaptureRequest, java.lang.Integer> hashMap, android.hardware.camera2.impl.CameraExtensionSessionImpl.CameraOutputImageCallback cameraOutputImageCallback) {
            this.mClientRequest = captureRequest;
            this.mExecutor = executor;
            this.mCallbacks = extensionCaptureCallback;
            this.mCaptureRequestMap = hashMap;
            this.mBurstImageCallback = cameraOutputImageCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyCaptureFailed() {
            if (!this.mCaptureFailed) {
                this.mCaptureFailed = true;
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.lambda$notifyCaptureFailed$0();
                        }
                    });
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    for (android.util.Pair<android.media.Image, android.hardware.camera2.TotalCaptureResult> pair : this.mCaptureStageMap.values()) {
                        if (pair.first != null) {
                            pair.first.close();
                        }
                    }
                    this.mCaptureStageMap.clear();
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyCaptureFailed$0() {
            this.mCallbacks.onCaptureFailed(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, final long j, long j2) {
            boolean z;
            synchronized (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mInterfaceLock) {
                z = true;
                if (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mImageProcessor != null && this.mImageCallback == null) {
                    this.mImageCallback = new android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.ImageCallback();
                } else if (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mImageProcessor != null) {
                    z = false;
                }
            }
            if (z) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.lambda$onCaptureStarted$1(j);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            if (this.mBurstImageCallback != null && this.mImageCallback != null) {
                this.mBurstImageCallback.registerListener(java.lang.Long.valueOf(j), this.mImageCallback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureStarted$1(long j) {
            this.mCallbacks.onCaptureStarted(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, this.mClientRequest, j);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureBufferLost(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.view.Surface surface, long j) {
            notifyCaptureFailed();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureFailure captureFailure) {
            notifyCaptureFailed();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.lambda$onCaptureSequenceAborted$2(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceAborted$2(int i) {
            this.mCallbacks.onCaptureSequenceAborted(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, i);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, final int i, long j) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.lambda$onCaptureSequenceCompleted$3(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$3(int i) {
            this.mCallbacks.onCaptureSequenceCompleted(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, i);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureCompleted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.TotalCaptureResult totalCaptureResult) {
            if (!this.mCaptureRequestMap.containsKey(captureRequest)) {
                android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Unexpected still capture request received!");
                return;
            }
            java.lang.Integer num = this.mCaptureRequestMap.get(captureRequest);
            java.lang.Long l = (java.lang.Long) totalCaptureResult.get(android.hardware.camera2.CaptureResult.SENSOR_TIMESTAMP);
            if (l != null) {
                if (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mCaptureResultsSupported && this.mCaptureResultHandler == null) {
                    this.mCaptureResultHandler = android.hardware.camera2.impl.CameraExtensionSessionImpl.this.new CaptureResultHandler(this.mClientRequest, this.mExecutor, this.mCallbacks, totalCaptureResult.getSequenceId());
                }
                if (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mImageProcessor != null) {
                    if (this.mCapturePendingMap.indexOfKey(l.longValue()) >= 0) {
                        android.media.Image image = this.mCapturePendingMap.get(l.longValue()).first;
                        this.mCapturePendingMap.remove(l.longValue());
                        this.mCaptureStageMap.put(num, new android.util.Pair<>(image, totalCaptureResult));
                        checkAndFireBurstProcessing();
                        return;
                    }
                    this.mCapturePendingMap.put(l.longValue(), new android.util.Pair<>(null, num));
                    this.mCaptureStageMap.put(num, new android.util.Pair<>(null, totalCaptureResult));
                    return;
                }
                this.mCaptureRequestMap.clear();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.lambda$onCaptureCompleted$4();
                        }
                    });
                    if (this.mCaptureResultHandler != null) {
                        this.mCaptureResultHandler.onCaptureCompleted(l.longValue(), android.hardware.camera2.impl.CameraExtensionSessionImpl.this.initializeFilteredResults(totalCaptureResult));
                    }
                    return;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Capture result without valid sensor timestamp!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$4() {
            this.mCallbacks.onCaptureProcessStarted(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void checkAndFireBurstProcessing() {
            boolean z;
            if (this.mCaptureRequestMap.size() == this.mCaptureStageMap.size()) {
                for (android.util.Pair<android.media.Image, android.hardware.camera2.TotalCaptureResult> pair : this.mCaptureStageMap.values()) {
                    if (pair.first == null || pair.second == null) {
                        return;
                    }
                }
                this.mCaptureRequestMap.clear();
                this.mCapturePendingMap.clear();
                java.lang.Byte b = (java.lang.Byte) this.mClientRequest.get(android.hardware.camera2.CaptureRequest.JPEG_QUALITY);
                java.util.List<android.hardware.camera2.extension.CaptureBundle> initializeParcelable = android.hardware.camera2.impl.CameraExtensionSessionImpl.initializeParcelable(this.mCaptureStageMap, (java.lang.Integer) this.mClientRequest.get(android.hardware.camera2.CaptureRequest.JPEG_ORIENTATION), b);
                try {
                    android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mImageProcessor.process(initializeParcelable, this.mCaptureResultHandler, this.mClientRequest.containsTarget(android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mClientPostviewSurface));
                    z = true;
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Failed to process multi-frame request! Extension service does not respond!");
                    z = false;
                }
                java.util.Iterator<android.hardware.camera2.extension.CaptureBundle> it = initializeParcelable.iterator();
                while (it.hasNext()) {
                    it.next().captureImage.buffer.close();
                }
                initializeParcelable.clear();
                java.util.Iterator<android.util.Pair<android.media.Image, android.hardware.camera2.TotalCaptureResult>> it2 = this.mCaptureStageMap.values().iterator();
                while (it2.hasNext()) {
                    it2.next().first.close();
                }
                this.mCaptureStageMap.clear();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (z) {
                        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.lambda$checkAndFireBurstProcessing$5();
                            }
                        });
                    } else {
                        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.lambda$checkAndFireBurstProcessing$6();
                            }
                        });
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$checkAndFireBurstProcessing$5() {
            this.mCallbacks.onCaptureProcessStarted(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$checkAndFireBurstProcessing$6() {
            this.mCallbacks.onCaptureFailed(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        private class ImageCallback implements android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener {
            private ImageCallback() {
            }

            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageDropped(long j) {
                android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.notifyCaptureFailed();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageAvailable(android.media.ImageReader imageReader, android.media.Image image) {
                if (android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.mCaptureFailed) {
                    image.close();
                }
                long timestamp = image.getTimestamp();
                imageReader.detachImage(image);
                if (android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.mCapturePendingMap.indexOfKey(timestamp) >= 0) {
                    java.lang.Integer num = (java.lang.Integer) ((android.util.Pair) android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.mCapturePendingMap.get(timestamp)).second;
                    android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.mCapturePendingMap.remove(timestamp);
                    android.util.Pair pair = (android.util.Pair) android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.mCaptureStageMap.get(num);
                    if (pair != null) {
                        android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.mCaptureStageMap.put(num, new android.util.Pair(image, (android.hardware.camera2.TotalCaptureResult) pair.second));
                        android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.checkAndFireBurstProcessing();
                        return;
                    } else {
                        android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Capture stage: " + ((android.util.Pair) android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.mCapturePendingMap.get(timestamp)).second + " is absent!");
                        return;
                    }
                }
                android.hardware.camera2.impl.CameraExtensionSessionImpl.BurstRequestHandler.this.mCapturePendingMap.put(timestamp, new android.util.Pair(image, -1));
            }
        }
    }

    private class ImageLoopbackCallback implements android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener {
        private ImageLoopbackCallback() {
        }

        @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
        public void onImageDropped(long j) {
        }

        @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
        public void onImageAvailable(android.media.ImageReader imageReader, android.media.Image image) {
            image.close();
        }
    }

    private class InitialRequestHandler extends android.hardware.camera2.CameraCaptureSession.CaptureCallback {
        private final android.hardware.camera2.impl.CameraExtensionSessionImpl.CameraOutputImageCallback mImageCallback;

        public InitialRequestHandler(android.hardware.camera2.impl.CameraExtensionSessionImpl.CameraOutputImageCallback cameraOutputImageCallback) {
            this.mImageCallback = cameraOutputImageCallback;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, long j, long j2) {
            this.mImageCallback.registerListener(java.lang.Long.valueOf(j), new android.hardware.camera2.impl.CameraExtensionSessionImpl.ImageLoopbackCallback());
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, int i) {
            android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Initial capture request aborted!");
            android.hardware.camera2.impl.CameraExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureFailure captureFailure) {
            android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Initial capture request failed!");
            android.hardware.camera2.impl.CameraExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, int i, long j) {
            boolean z;
            synchronized (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mInterfaceLock) {
                try {
                    android.hardware.camera2.impl.CameraExtensionSessionImpl.this.setRepeatingRequest(android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mPreviewExtender.getCaptureStage(), new android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, null, null, null, this.mImageCallback));
                    z = true;
                } catch (android.hardware.camera2.CameraAccessException | android.os.RemoteException e) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Failed to start the internal repeating request!");
                    z = false;
                }
            }
            if (!z) {
                android.hardware.camera2.impl.CameraExtensionSessionImpl.this.notifyConfigurationFailure();
            }
        }
    }

    private class CameraOutputImageCallback implements android.media.ImageReader.OnImageAvailableListener, java.io.Closeable {
        private final android.media.ImageReader mImageReader;
        private final boolean mPruneOlderBuffers;
        private java.util.HashMap<java.lang.Long, android.util.Pair<android.media.Image, android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener>> mImageListenerMap = new java.util.HashMap<>();
        private boolean mOutOfBuffers = false;

        CameraOutputImageCallback(android.media.ImageReader imageReader, boolean z) {
            this.mImageReader = imageReader;
            this.mPruneOlderBuffers = z;
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(android.media.ImageReader imageReader) {
            synchronized (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mInterfaceLock) {
                try {
                    try {
                        android.media.Image acquireNextImage = imageReader.acquireNextImage();
                        if (acquireNextImage == null) {
                            android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Invalid image!");
                            return;
                        }
                        java.lang.Long valueOf = java.lang.Long.valueOf(acquireNextImage.getTimestamp());
                        if (this.mImageListenerMap.containsKey(valueOf)) {
                            android.util.Pair<android.media.Image, android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener> remove = this.mImageListenerMap.remove(valueOf);
                            if (remove.second != null) {
                                remove.second.onImageAvailable(imageReader, acquireNextImage);
                            } else {
                                android.util.Log.w(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Invalid image listener, dropping frame!");
                                acquireNextImage.close();
                            }
                        } else {
                            this.mImageListenerMap.put(valueOf, new android.util.Pair<>(acquireNextImage, null));
                        }
                        notifyDroppedImages(valueOf.longValue());
                    } catch (java.lang.IllegalStateException e) {
                        android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Failed to acquire image, too many images pending!");
                        this.mOutOfBuffers = true;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void notifyDroppedImages(long j) {
            synchronized (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mInterfaceLock) {
                java.util.Set<java.lang.Long> keySet = this.mImageListenerMap.keySet();
                java.util.ArrayList arrayList = new java.util.ArrayList();
                java.util.Iterator<java.lang.Long> it = keySet.iterator();
                while (it.hasNext()) {
                    long longValue = it.next().longValue();
                    if (longValue < j) {
                        if (!this.mPruneOlderBuffers) {
                            android.util.Log.w(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Unexpected older image with ts: " + longValue);
                        } else {
                            android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Dropped image with ts: " + longValue);
                            android.util.Pair<android.media.Image, android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener> pair = this.mImageListenerMap.get(java.lang.Long.valueOf(longValue));
                            if (pair.second != null) {
                                pair.second.onImageDropped(longValue);
                            }
                            if (pair.first != null) {
                                pair.first.close();
                            }
                            arrayList.add(java.lang.Long.valueOf(longValue));
                        }
                    }
                }
                java.util.Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    this.mImageListenerMap.remove(java.lang.Long.valueOf(((java.lang.Long) it2.next()).longValue()));
                }
            }
        }

        public void registerListener(java.lang.Long l, android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener onImageAvailableListener) {
            synchronized (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mInterfaceLock) {
                if (this.mImageListenerMap.containsKey(l)) {
                    android.util.Pair<android.media.Image, android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener> remove = this.mImageListenerMap.remove(l);
                    if (remove.first != null) {
                        onImageAvailableListener.onImageAvailable(this.mImageReader, remove.first);
                        if (this.mOutOfBuffers) {
                            this.mOutOfBuffers = false;
                            android.util.Log.w(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Out of buffers, retry!");
                            onImageAvailable(this.mImageReader);
                        }
                    } else {
                        android.util.Log.w(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "No valid image for listener with ts: " + l.longValue());
                    }
                } else {
                    this.mImageListenerMap.put(l, new android.util.Pair<>(null, onImageAvailableListener));
                }
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            synchronized (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mInterfaceLock) {
                for (android.util.Pair<android.media.Image, android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener> pair : this.mImageListenerMap.values()) {
                    if (pair.first != null) {
                        pair.first.close();
                    }
                }
                java.util.Iterator<java.lang.Long> it = this.mImageListenerMap.keySet().iterator();
                while (it.hasNext()) {
                    long longValue = it.next().longValue();
                    android.util.Pair<android.media.Image, android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener> pair2 = this.mImageListenerMap.get(java.lang.Long.valueOf(longValue));
                    if (pair2.second != null) {
                        pair2.second.onImageDropped(longValue);
                    }
                }
                this.mImageListenerMap.clear();
            }
        }
    }

    private class CloseRequestHandler extends android.hardware.camera2.CameraCaptureSession.CaptureCallback {
        private final android.hardware.camera2.impl.CameraExtensionSessionImpl.CameraOutputImageCallback mImageCallback;

        public CloseRequestHandler(android.hardware.camera2.impl.CameraExtensionSessionImpl.CameraOutputImageCallback cameraOutputImageCallback) {
            this.mImageCallback = cameraOutputImageCallback;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, long j, long j2) {
            this.mImageCallback.registerListener(java.lang.Long.valueOf(j), new android.hardware.camera2.impl.CameraExtensionSessionImpl.ImageLoopbackCallback());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CaptureResultHandler extends android.hardware.camera2.extension.IProcessResultImpl.Stub {
        private final android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback mCallbacks;
        private final android.hardware.camera2.CaptureRequest mClientRequest;
        private final java.util.concurrent.Executor mExecutor;
        private final int mRequestId;

        public CaptureResultHandler(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback, int i) {
            this.mClientRequest = captureRequest;
            this.mExecutor = executor;
            this.mCallbacks = extensionCaptureCallback;
            this.mRequestId = i;
        }

        @Override // android.hardware.camera2.extension.IProcessResultImpl
        public void onCaptureCompleted(long j, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) {
            if (cameraMetadataNative == null) {
                android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Invalid capture result!");
                return;
            }
            cameraMetadataNative.set((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key<java.lang.Long>>) android.hardware.camera2.CaptureResult.SENSOR_TIMESTAMP, (android.hardware.camera2.CaptureResult.Key<java.lang.Long>) java.lang.Long.valueOf(j));
            final android.hardware.camera2.TotalCaptureResult totalCaptureResult = new android.hardware.camera2.TotalCaptureResult(android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mCameraDevice.getId(), cameraMetadataNative, this.mClientRequest, this.mRequestId, j, new java.util.ArrayList(), android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mSessionId, new android.hardware.camera2.impl.PhysicalCaptureResultInfo[0]);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$CaptureResultHandler$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraExtensionSessionImpl.CaptureResultHandler.this.lambda$onCaptureCompleted$0(totalCaptureResult);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$0(android.hardware.camera2.TotalCaptureResult totalCaptureResult) {
            this.mCallbacks.onCaptureResultAvailable(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, this.mClientRequest, totalCaptureResult);
        }

        @Override // android.hardware.camera2.extension.IProcessResultImpl
        public void onCaptureProcessProgressed(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$CaptureResultHandler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CameraExtensionSessionImpl.CaptureResultHandler.this.lambda$onCaptureProcessProgressed$1(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProcessProgressed$1(int i) {
            this.mCallbacks.onCaptureProcessProgressed(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, this.mClientRequest, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class PreviewRequestHandler extends android.hardware.camera2.CameraCaptureSession.CaptureCallback {
        private final android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback mCallbacks;
        private android.hardware.camera2.impl.CameraExtensionSessionImpl.CaptureResultHandler mCaptureResultHandler;
        private final boolean mClientNotificationsEnabled;
        private final android.hardware.camera2.CaptureRequest mClientRequest;
        private final java.util.concurrent.Executor mExecutor;
        private android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener mImageCallback;
        private android.util.LongSparseArray<android.util.Pair<android.media.Image, android.hardware.camera2.TotalCaptureResult>> mPendingResultMap;
        private final android.hardware.camera2.impl.CameraExtensionSessionImpl.CameraOutputImageCallback mRepeatingImageCallback;
        private boolean mRequestUpdatedNeeded;
        private final boolean mSingleCapture;

        public PreviewRequestHandler(android.hardware.camera2.impl.CameraExtensionSessionImpl cameraExtensionSessionImpl, android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback, android.hardware.camera2.impl.CameraExtensionSessionImpl.CameraOutputImageCallback cameraOutputImageCallback) {
            this(captureRequest, executor, extensionCaptureCallback, cameraOutputImageCallback, false);
        }

        public PreviewRequestHandler(android.hardware.camera2.CaptureRequest captureRequest, java.util.concurrent.Executor executor, android.hardware.camera2.CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback, android.hardware.camera2.impl.CameraExtensionSessionImpl.CameraOutputImageCallback cameraOutputImageCallback, boolean z) {
            this.mImageCallback = null;
            this.mPendingResultMap = new android.util.LongSparseArray<>();
            this.mCaptureResultHandler = null;
            boolean z2 = false;
            this.mRequestUpdatedNeeded = false;
            this.mClientRequest = captureRequest;
            this.mExecutor = executor;
            this.mCallbacks = extensionCaptureCallback;
            if (this.mClientRequest != null && this.mExecutor != null && this.mCallbacks != null) {
                z2 = true;
            }
            this.mClientNotificationsEnabled = z2;
            this.mRepeatingImageCallback = cameraOutputImageCallback;
            this.mSingleCapture = z;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, final long j, long j2) {
            android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener imageLoopbackCallback;
            synchronized (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mInterfaceLock) {
                if (this.mImageCallback == null) {
                    byte b = 0;
                    if (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mPreviewProcessorType == 1) {
                        if (this.mClientNotificationsEnabled) {
                            android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mPreviewImageProcessor.onOutputSurface(android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mClientRepeatingRequestSurface, android.hardware.camera2.impl.CameraExtensionSessionImpl.nativeGetSurfaceFormat(android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mClientRepeatingRequestSurface));
                        } else {
                            android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mPreviewImageProcessor.onOutputSurface(null, -1);
                        }
                        this.mImageCallback = new android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.ImageProcessCallback();
                    } else {
                        if (this.mClientNotificationsEnabled) {
                            imageLoopbackCallback = new android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.ImageForwardCallback(android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mRepeatingRequestImageWriter);
                        } else {
                            imageLoopbackCallback = new android.hardware.camera2.impl.CameraExtensionSessionImpl.ImageLoopbackCallback();
                        }
                        this.mImageCallback = imageLoopbackCallback;
                    }
                }
            }
            if (this.mClientNotificationsEnabled) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.lambda$onCaptureStarted$0(j);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            this.mRepeatingImageCallback.registerListener(java.lang.Long.valueOf(j), this.mImageCallback);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureStarted$0(long j) {
            this.mCallbacks.onCaptureStarted(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, this.mClientRequest, j);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, final int i) {
            synchronized (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mInterfaceLock) {
                if (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mInternalRepeatingRequestEnabled && !this.mSingleCapture) {
                    resumeInternalRepeatingRequest(true);
                }
            }
            if (this.mClientNotificationsEnabled) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.lambda$onCaptureSequenceAborted$1(i);
                        }
                    });
                    return;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            android.hardware.camera2.impl.CameraExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceAborted$1(int i) {
            this.mCallbacks.onCaptureSequenceAborted(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, i);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, final int i, long j) {
            synchronized (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mInterfaceLock) {
                if (this.mRequestUpdatedNeeded && !this.mSingleCapture) {
                    this.mRequestUpdatedNeeded = false;
                    resumeInternalRepeatingRequest(false);
                } else if (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mInternalRepeatingRequestEnabled && !this.mSingleCapture) {
                    resumeInternalRepeatingRequest(true);
                }
            }
            if (this.mClientNotificationsEnabled) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.lambda$onCaptureSequenceCompleted$2(i);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$2(int i) {
            this.mCallbacks.onCaptureSequenceCompleted(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, i);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureFailure captureFailure) {
            if (this.mClientNotificationsEnabled) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.lambda$onCaptureFailed$3();
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureFailed$3() {
            this.mCallbacks.onCaptureFailed(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x016c A[Catch: all -> 0x01bc, TRY_LEAVE, TryCatch #7 {, blocks: (B:4:0x0008, B:6:0x0012, B:8:0x001a, B:10:0x001e, B:12:0x0022, B:13:0x0036, B:15:0x003d, B:18:0x0046, B:44:0x0063, B:23:0x016c, B:31:0x01a2, B:35:0x01a8, B:36:0x01ab, B:38:0x01b3, B:20:0x0077, B:48:0x006c, B:52:0x005a, B:53:0x007b, B:55:0x0083, B:57:0x008f, B:59:0x009f, B:61:0x00a3, B:62:0x00b2, B:64:0x00bb, B:67:0x00da, B:69:0x0137, B:72:0x013d, B:73:0x0155, B:80:0x00ff, B:77:0x0133, B:76:0x011e, B:81:0x0156, B:82:0x01ac, B:66:0x00cf, B:79:0x00f8, B:75:0x0117, B:26:0x0172, B:28:0x0180, B:30:0x0188, B:33:0x0198), top: B:3:0x0008, inners: #1, #3, #4, #7 }] */
        /* JADX WARN: Removed duplicated region for block: B:40:0x01b6  */
        /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onCaptureCompleted(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.TotalCaptureResult totalCaptureResult) {
            boolean z;
            android.media.Image image;
            boolean z2 = this.mClientNotificationsEnabled;
            synchronized (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mInterfaceLock) {
                java.lang.Long l = (java.lang.Long) totalCaptureResult.get(android.hardware.camera2.CaptureResult.SENSOR_TIMESTAMP);
                if (l != null) {
                    if (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mCaptureResultsSupported && this.mClientNotificationsEnabled && this.mCaptureResultHandler == null) {
                        this.mCaptureResultHandler = android.hardware.camera2.impl.CameraExtensionSessionImpl.this.new CaptureResultHandler(this.mClientRequest, this.mExecutor, this.mCallbacks, totalCaptureResult.getSequenceId());
                    }
                    android.hardware.camera2.extension.CaptureStageImpl captureStageImpl = null;
                    if (this.mSingleCapture || android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mPreviewProcessorType != 0) {
                        if (android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mPreviewProcessorType == 1) {
                            int indexOfKey = this.mPendingResultMap.indexOfKey(l.longValue());
                            if (indexOfKey >= 0 && this.mPendingResultMap.get(l.longValue()).first == null) {
                                if (this.mCaptureResultHandler != null) {
                                    this.mCaptureResultHandler.onCaptureCompleted(l.longValue(), android.hardware.camera2.impl.CameraExtensionSessionImpl.this.initializeFilteredResults(totalCaptureResult));
                                }
                                discardPendingRepeatingResults(indexOfKey, this.mPendingResultMap, false);
                            } else if (indexOfKey >= 0) {
                                android.hardware.camera2.extension.ParcelImage initializeParcelImage = android.hardware.camera2.impl.CameraExtensionSessionImpl.initializeParcelImage(this.mPendingResultMap.get(l.longValue()).first);
                                try {
                                    try {
                                        android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mPreviewImageProcessor.process(initializeParcelImage, totalCaptureResult, this.mCaptureResultHandler);
                                        z = true;
                                    } catch (android.os.RemoteException e) {
                                        android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Extension service does not respond during processing, dropping frame!");
                                        initializeParcelImage.buffer.close();
                                        image = this.mPendingResultMap.get(l.longValue()).first;
                                        image.close();
                                        z = false;
                                        discardPendingRepeatingResults(indexOfKey, this.mPendingResultMap, false);
                                        if (z2) {
                                        }
                                        if (z2) {
                                        }
                                    } catch (java.lang.RuntimeException e2) {
                                        android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Runtime exception encountered during buffer processing, dropping frame!");
                                        initializeParcelImage.buffer.close();
                                        image = this.mPendingResultMap.get(l.longValue()).first;
                                        image.close();
                                        z = false;
                                        discardPendingRepeatingResults(indexOfKey, this.mPendingResultMap, false);
                                        if (z2) {
                                        }
                                        if (z2) {
                                        }
                                    }
                                    discardPendingRepeatingResults(indexOfKey, this.mPendingResultMap, false);
                                } finally {
                                    initializeParcelImage.buffer.close();
                                    this.mPendingResultMap.get(l.longValue()).first.close();
                                }
                            } else {
                                this.mPendingResultMap.put(l.longValue(), new android.util.Pair<>(null, totalCaptureResult));
                                z2 = false;
                                z = true;
                            }
                        }
                        z = true;
                    } else {
                        try {
                            captureStageImpl = android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mPreviewRequestUpdateProcessor.process(totalCaptureResult.getNativeMetadata(), totalCaptureResult.getSequenceId());
                        } catch (android.os.RemoteException e3) {
                            android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Extension service does not respond during processing!");
                        }
                        if (captureStageImpl != null) {
                            try {
                                android.hardware.camera2.impl.CameraExtensionSessionImpl.this.setRepeatingRequest(captureStageImpl, this, captureRequest);
                                this.mRequestUpdatedNeeded = true;
                            } catch (android.hardware.camera2.CameraAccessException e4) {
                                android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Failed to update repeating request settings!");
                            } catch (java.lang.IllegalStateException e5) {
                            }
                        } else {
                            this.mRequestUpdatedNeeded = false;
                        }
                        z = true;
                    }
                    if (z2) {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            if (z) {
                                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda4
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.lambda$onCaptureCompleted$4();
                                    }
                                });
                                if (this.mCaptureResultHandler != null && android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mPreviewProcessorType != 1) {
                                    this.mCaptureResultHandler.onCaptureCompleted(l.longValue(), android.hardware.camera2.impl.CameraExtensionSessionImpl.this.initializeFilteredResults(totalCaptureResult));
                                }
                            } else {
                                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda5
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.lambda$onCaptureCompleted$5();
                                    }
                                });
                            }
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (java.lang.Throwable th) {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                } else {
                    android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Result without valid sensor timestamp!");
                }
            }
            if (z2) {
                return;
            }
            android.hardware.camera2.impl.CameraExtensionSessionImpl.this.notifyConfigurationSuccess();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$4() {
            this.mCallbacks.onCaptureProcessStarted(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$5() {
            this.mCallbacks.onCaptureFailed(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        private void resumeInternalRepeatingRequest(boolean z) {
            try {
                if (z) {
                    android.hardware.camera2.impl.CameraExtensionSessionImpl.this.setRepeatingRequest(android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mPreviewExtender.getCaptureStage(), new android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, null, null, null, this.mRepeatingImageCallback));
                } else {
                    android.hardware.camera2.impl.CameraExtensionSessionImpl.this.setRepeatingRequest(android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mPreviewExtender.getCaptureStage(), this, this.mClientRequest);
                }
            } catch (android.hardware.camera2.CameraAccessException e) {
                android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Failed to resume internal repeating request!");
            } catch (android.os.RemoteException e2) {
                android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Failed to resume internal repeating request, extension service fails to respond!");
            } catch (java.lang.IllegalStateException e3) {
                android.util.Log.w(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Failed to resume internal repeating request!");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.Long calculatePruneThreshold(android.util.LongSparseArray<android.util.Pair<android.media.Image, android.hardware.camera2.TotalCaptureResult>> longSparseArray) {
            long j = Long.MAX_VALUE;
            for (int i = 0; i < longSparseArray.size(); i++) {
                android.util.Pair<android.media.Image, android.hardware.camera2.TotalCaptureResult> valueAt = longSparseArray.valueAt(i);
                long keyAt = longSparseArray.keyAt(i);
                if (valueAt.first != null && keyAt < j) {
                    j = keyAt;
                }
            }
            if (j == Long.MAX_VALUE) {
                j = 0;
            }
            return java.lang.Long.valueOf(j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void discardPendingRepeatingResults(int i, android.util.LongSparseArray<android.util.Pair<android.media.Image, android.hardware.camera2.TotalCaptureResult>> longSparseArray, boolean z) {
            if (i < 0) {
                return;
            }
            for (int i2 = i; i2 >= 0; i2--) {
                if (longSparseArray.valueAt(i2).first != null) {
                    longSparseArray.valueAt(i2).first.close();
                } else if (this.mClientNotificationsEnabled && longSparseArray.valueAt(i2).second != null && (i2 != i || z)) {
                    android.hardware.camera2.TotalCaptureResult totalCaptureResult = longSparseArray.valueAt(i2).second;
                    java.lang.Long l = (java.lang.Long) totalCaptureResult.get(android.hardware.camera2.CaptureResult.SENSOR_TIMESTAMP);
                    if (this.mCaptureResultHandler != null) {
                        this.mCaptureResultHandler.onCaptureCompleted(l.longValue(), android.hardware.camera2.impl.CameraExtensionSessionImpl.this.initializeFilteredResults(totalCaptureResult));
                    }
                    android.util.Log.w(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Preview frame drop with timestamp: " + longSparseArray.keyAt(i2));
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.lambda$discardPendingRepeatingResults$6();
                            }
                        });
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                longSparseArray.removeAt(i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$discardPendingRepeatingResults$6() {
            this.mCallbacks.onCaptureFailed(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        private class ImageForwardCallback implements android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener {
            private final android.media.ImageWriter mOutputWriter;

            public ImageForwardCallback(android.media.ImageWriter imageWriter) {
                this.mOutputWriter = imageWriter;
            }

            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageDropped(long j) {
                android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.discardPendingRepeatingResults(android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mPendingResultMap.indexOfKey(j), android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mPendingResultMap, true);
            }

            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageAvailable(android.media.ImageReader imageReader, android.media.Image image) {
                if (image == null) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Invalid image!");
                    return;
                }
                try {
                    this.mOutputWriter.queueInputImage(image);
                } catch (java.lang.IllegalStateException e) {
                    android.util.Log.w(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Output surface likely abandoned, dropping buffer!");
                    image.close();
                } catch (java.lang.RuntimeException e2) {
                    if (!e2.getClass().equals(java.lang.RuntimeException.class)) {
                        throw e2;
                    }
                    android.util.Log.w(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Output surface likely abandoned, dropping buffer!");
                    image.close();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        class ImageProcessCallback implements android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener {
            private ImageProcessCallback() {
            }

            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageDropped(long j) {
                android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.discardPendingRepeatingResults(android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mPendingResultMap.indexOfKey(j), android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mPendingResultMap, true);
                android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mPendingResultMap.put(j, new android.util.Pair(null, null));
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v2, types: [android.hardware.camera2.extension.ParcelImage] */
            /* JADX WARN: Type inference failed for: r0v4, types: [android.hardware.camera2.extension.ParcelImage] */
            /* JADX WARN: Type inference failed for: r0v7, types: [android.util.LongSparseArray] */
            /* JADX WARN: Type inference failed for: r10v0, types: [android.media.Image, java.lang.Object] */
            /* JADX WARN: Type inference failed for: r10v1, types: [android.media.Image] */
            /* JADX WARN: Type inference failed for: r10v2, types: [android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler] */
            /* JADX WARN: Type inference failed for: r6v2, types: [android.hardware.camera2.impl.CameraExtensionForwardProcessor] */
            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageAvailable(android.media.ImageReader imageReader, android.media.Image image) {
                boolean z = true;
                if (android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mPendingResultMap.size() + 1 >= 10) {
                    android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.discardPendingRepeatingResults(android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mPendingResultMap.indexOfKey(android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.calculatePruneThreshold(android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mPendingResultMap).longValue()), android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mPendingResultMap, true);
                }
                if (image == 0) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Invalid preview buffer!");
                    return;
                }
                try {
                    imageReader.detachImage(image);
                    long timestamp = image.getTimestamp();
                    int indexOfKey = android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mPendingResultMap.indexOfKey(timestamp);
                    if (indexOfKey < 0) {
                        android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mPendingResultMap.put(timestamp, new android.util.Pair(image, null));
                        return;
                    }
                    ?? initializeParcelImage = android.hardware.camera2.impl.CameraExtensionSessionImpl.initializeParcelImage(image);
                    try {
                        try {
                            android.hardware.camera2.impl.CameraExtensionSessionImpl.this.mPreviewImageProcessor.process(initializeParcelImage, (android.hardware.camera2.TotalCaptureResult) ((android.util.Pair) android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mPendingResultMap.get(timestamp)).second, android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mCaptureResultHandler);
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Extension service does not respond during processing, dropping frame!");
                            initializeParcelImage.buffer.close();
                            image.close();
                            z = false;
                        }
                        image = android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this;
                        initializeParcelImage = android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mPendingResultMap;
                        image.discardPendingRepeatingResults(indexOfKey, initializeParcelImage, false);
                        if (android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mClientNotificationsEnabled) {
                            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                if (z) {
                                    android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$ImageProcessCallback$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.ImageProcessCallback.this.lambda$onImageAvailable$0();
                                        }
                                    });
                                } else {
                                    android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$ImageProcessCallback$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.ImageProcessCallback.this.lambda$onImageAvailable$1();
                                        }
                                    });
                                }
                            } finally {
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                    } finally {
                        initializeParcelImage.buffer.close();
                        image.close();
                    }
                } catch (java.lang.IllegalStateException e2) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Failed to detach image!");
                    image.close();
                } catch (java.lang.RuntimeException e3) {
                    if (!e3.getClass().equals(java.lang.RuntimeException.class)) {
                        throw e3;
                    }
                    android.util.Log.e(android.hardware.camera2.impl.CameraExtensionSessionImpl.TAG, "Failed to detach image!");
                    image.close();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onImageAvailable$0() {
                android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mCallbacks.onCaptureProcessStarted(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mClientRequest);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onImageAvailable$1() {
                android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mCallbacks.onCaptureFailed(android.hardware.camera2.impl.CameraExtensionSessionImpl.this, android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.this.mClientRequest);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.impl.CameraMetadataNative initializeFilteredResults(android.hardware.camera2.TotalCaptureResult totalCaptureResult) {
        android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative = new android.hardware.camera2.impl.CameraMetadataNative();
        for (android.hardware.camera2.CaptureResult.Key key : this.mSupportedResultKeys) {
            java.lang.Object obj = totalCaptureResult.get(key);
            if (obj != null) {
                cameraMetadataNative.set((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key>) key, (android.hardware.camera2.CaptureResult.Key) obj);
            }
        }
        return cameraMetadataNative;
    }

    private static android.util.Size findSmallestAspectMatchedSize(java.util.List<android.util.Size> list, android.util.Size size) {
        if (size.getHeight() == 0) {
            throw new java.lang.IllegalArgumentException("Invalid input aspect ratio");
        }
        float width = size.getWidth() / size.getHeight();
        android.util.Size size2 = null;
        android.util.Size size3 = null;
        for (android.util.Size size4 : list) {
            if (size3 == null) {
                size3 = size4;
            }
            if (size4.getHeight() > 0 && (size2 == null || size2.getWidth() * size2.getHeight() < size4.getWidth() * size4.getHeight())) {
                if (java.lang.Math.abs((size4.getWidth() / size4.getHeight()) - width) <= 0.01f) {
                    size2 = size4;
                }
            }
        }
        if (size2 == null) {
            android.util.Log.e(TAG, "AR matched size not found returning first size in list");
            return size3;
        }
        return size2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.hardware.camera2.extension.ParcelImage initializeParcelImage(android.media.Image image) {
        android.hardware.camera2.extension.ParcelImage parcelImage = new android.hardware.camera2.extension.ParcelImage();
        parcelImage.buffer = image.getHardwareBuffer();
        try {
            android.hardware.SyncFence fence = image.getFence();
            if (fence.isValid()) {
                parcelImage.fence = fence.getFdDup();
            }
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "Failed to parcel buffer fence!");
        }
        parcelImage.width = image.getWidth();
        parcelImage.height = image.getHeight();
        parcelImage.format = image.getFormat();
        parcelImage.timestamp = image.getTimestamp();
        parcelImage.transform = image.getTransform();
        parcelImage.scalingMode = image.getScalingMode();
        parcelImage.planeCount = image.getPlaneCount();
        parcelImage.crop = image.getCropRect();
        return parcelImage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.List<android.hardware.camera2.extension.CaptureBundle> initializeParcelable(java.util.HashMap<java.lang.Integer, android.util.Pair<android.media.Image, android.hardware.camera2.TotalCaptureResult>> hashMap, java.lang.Integer num, java.lang.Byte b) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.Integer num2 : hashMap.keySet()) {
            android.util.Pair<android.media.Image, android.hardware.camera2.TotalCaptureResult> pair = hashMap.get(num2);
            android.hardware.camera2.extension.CaptureBundle captureBundle = new android.hardware.camera2.extension.CaptureBundle();
            captureBundle.stage = num2.intValue();
            captureBundle.captureImage = initializeParcelImage(pair.first);
            captureBundle.sequenceId = pair.second.getSequenceId();
            captureBundle.captureResult = pair.second.getNativeMetadata();
            if (num != null) {
                captureBundle.captureResult.set((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key<java.lang.Integer>>) android.hardware.camera2.CaptureResult.JPEG_ORIENTATION, (android.hardware.camera2.CaptureResult.Key<java.lang.Integer>) num);
            }
            if (b != null) {
                captureBundle.captureResult.set((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key<java.lang.Byte>>) android.hardware.camera2.CaptureResult.JPEG_QUALITY, (android.hardware.camera2.CaptureResult.Key<java.lang.Byte>) b);
            }
            arrayList.add(captureBundle);
        }
        return arrayList;
    }
}
