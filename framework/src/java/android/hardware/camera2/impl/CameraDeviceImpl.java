package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class CameraDeviceImpl extends android.hardware.camera2.CameraDevice implements android.os.IBinder.DeathRecipient {
    static final long CHECK_PARAMS_IN_IS_SESSION_CONFIGURATION_SUPPORTED = 320741775;
    private static final long NANO_PER_SECOND = 1000000000;
    private static final int REQUEST_ID_NONE = -1;
    private final java.lang.String TAG;
    private final int mAppTargetSdkVersion;
    private final android.hardware.camera2.CameraDevice.CameraDeviceSetup mCameraDeviceSetup;
    private final java.lang.String mCameraId;
    private final android.hardware.camera2.CameraCharacteristics mCharacteristics;
    private final android.content.Context mContext;
    private android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl mCurrentAdvancedExtensionSession;
    private android.hardware.camera2.impl.CameraExtensionSessionImpl mCurrentExtensionSession;
    private android.hardware.camera2.impl.CameraCaptureSessionCore mCurrentSession;
    private final android.hardware.camera2.CameraDevice.StateCallback mDeviceCallback;
    private final java.util.concurrent.Executor mDeviceExecutor;
    private int[] mFailedRepeatingRequestTypes;
    private boolean mIsPrivilegedApp;
    private android.hardware.camera2.impl.CameraOfflineSessionImpl mOfflineSessionImpl;
    private java.util.concurrent.ExecutorService mOfflineSwitchService;
    private final java.util.Map<java.lang.String, android.hardware.camera2.CameraCharacteristics> mPhysicalIdsToChars;
    private android.hardware.camera2.impl.ICameraDeviceUserWrapper mRemoteDevice;
    private int[] mRepeatingRequestTypes;
    private volatile android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK mSessionStateCallback;
    private final int mTotalPartialCount;
    private final boolean DEBUG = false;
    private boolean mRemoteDeviceInit = false;
    final java.lang.Object mInterfaceLock = new java.lang.Object();
    private final android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks mCallbacks = new android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks();
    private final java.util.concurrent.atomic.AtomicBoolean mClosing = new java.util.concurrent.atomic.AtomicBoolean();
    private boolean mInError = false;
    private boolean mIdle = true;
    private android.util.SparseArray<android.hardware.camera2.impl.CaptureCallbackHolder> mCaptureCallbackMap = new android.util.SparseArray<>();
    private java.util.HashMap<java.lang.Integer, java.lang.Integer> mBatchOutputMap = new java.util.HashMap<>();
    private int mRepeatingRequestId = -1;
    private int mFailedRepeatingRequestId = -1;
    private java.util.AbstractMap.SimpleEntry<java.lang.Integer, android.hardware.camera2.params.InputConfiguration> mConfiguredInput = new java.util.AbstractMap.SimpleEntry<>(-1, null);
    private final android.util.SparseArray<android.hardware.camera2.params.OutputConfiguration> mConfiguredOutputs = new android.util.SparseArray<>();
    private final java.util.HashSet<java.lang.Integer> mOfflineSupport = new java.util.HashSet<>();
    private final java.util.List<android.hardware.camera2.impl.RequestLastFrameNumbersHolder> mRequestLastFrameNumbersList = new java.util.ArrayList();
    private android.hardware.camera2.impl.FrameNumberTracker mFrameNumberTracker = new android.hardware.camera2.impl.FrameNumberTracker();
    private int mNextSessionId = 0;
    private final java.lang.Runnable mCallOnOpened = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.1
        @Override // java.lang.Runnable
        public void run() {
            synchronized (android.hardware.camera2.impl.CameraDeviceImpl.this.mInterfaceLock) {
                if (android.hardware.camera2.impl.CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK stateCallbackKK = android.hardware.camera2.impl.CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK != null) {
                    stateCallbackKK.onOpened(android.hardware.camera2.impl.CameraDeviceImpl.this);
                }
                android.hardware.camera2.impl.CameraDeviceImpl.this.mDeviceCallback.onOpened(android.hardware.camera2.impl.CameraDeviceImpl.this);
            }
        }
    };
    private final java.lang.Runnable mCallOnUnconfigured = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.2
        @Override // java.lang.Runnable
        public void run() {
            synchronized (android.hardware.camera2.impl.CameraDeviceImpl.this.mInterfaceLock) {
                if (android.hardware.camera2.impl.CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK stateCallbackKK = android.hardware.camera2.impl.CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK != null) {
                    stateCallbackKK.onUnconfigured(android.hardware.camera2.impl.CameraDeviceImpl.this);
                }
            }
        }
    };
    private final java.lang.Runnable mCallOnActive = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.3
        @Override // java.lang.Runnable
        public void run() {
            synchronized (android.hardware.camera2.impl.CameraDeviceImpl.this.mInterfaceLock) {
                if (android.hardware.camera2.impl.CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK stateCallbackKK = android.hardware.camera2.impl.CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK != null) {
                    stateCallbackKK.onActive(android.hardware.camera2.impl.CameraDeviceImpl.this);
                }
            }
        }
    };
    private final java.lang.Runnable mCallOnBusy = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.4
        @Override // java.lang.Runnable
        public void run() {
            synchronized (android.hardware.camera2.impl.CameraDeviceImpl.this.mInterfaceLock) {
                if (android.hardware.camera2.impl.CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK stateCallbackKK = android.hardware.camera2.impl.CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK != null) {
                    stateCallbackKK.onBusy(android.hardware.camera2.impl.CameraDeviceImpl.this);
                }
            }
        }
    };
    private final java.lang.Runnable mCallOnClosed = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.5
        private boolean mClosedOnce = false;

        @Override // java.lang.Runnable
        public void run() {
            android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK stateCallbackKK;
            if (this.mClosedOnce) {
                throw new java.lang.AssertionError("Don't post #onClosed more than once");
            }
            synchronized (android.hardware.camera2.impl.CameraDeviceImpl.this.mInterfaceLock) {
                stateCallbackKK = android.hardware.camera2.impl.CameraDeviceImpl.this.mSessionStateCallback;
            }
            if (stateCallbackKK != null) {
                stateCallbackKK.onClosed(android.hardware.camera2.impl.CameraDeviceImpl.this);
            }
            android.hardware.camera2.impl.CameraDeviceImpl.this.mDeviceCallback.onClosed(android.hardware.camera2.impl.CameraDeviceImpl.this);
            this.mClosedOnce = true;
        }
    };
    private final java.lang.Runnable mCallOnIdle = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.6
        @Override // java.lang.Runnable
        public void run() {
            synchronized (android.hardware.camera2.impl.CameraDeviceImpl.this.mInterfaceLock) {
                if (android.hardware.camera2.impl.CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK stateCallbackKK = android.hardware.camera2.impl.CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK != null) {
                    stateCallbackKK.onIdle(android.hardware.camera2.impl.CameraDeviceImpl.this);
                }
            }
        }
    };
    private final java.lang.Runnable mCallOnDisconnected = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.7
        @Override // java.lang.Runnable
        public void run() {
            synchronized (android.hardware.camera2.impl.CameraDeviceImpl.this.mInterfaceLock) {
                if (android.hardware.camera2.impl.CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK stateCallbackKK = android.hardware.camera2.impl.CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK != null) {
                    stateCallbackKK.onDisconnected(android.hardware.camera2.impl.CameraDeviceImpl.this);
                }
                android.hardware.camera2.impl.CameraDeviceImpl.this.mDeviceCallback.onDisconnected(android.hardware.camera2.impl.CameraDeviceImpl.this);
            }
        }
    };

    public CameraDeviceImpl(java.lang.String str, android.hardware.camera2.CameraDevice.StateCallback stateCallback, java.util.concurrent.Executor executor, android.hardware.camera2.CameraCharacteristics cameraCharacteristics, java.util.Map<java.lang.String, android.hardware.camera2.CameraCharacteristics> map, int i, android.content.Context context, android.hardware.camera2.CameraDevice.CameraDeviceSetup cameraDeviceSetup) {
        this.mIsPrivilegedApp = false;
        if (str == null || stateCallback == null || executor == null || cameraCharacteristics == null) {
            throw new java.lang.IllegalArgumentException("Null argument given");
        }
        this.mCameraId = str;
        this.mDeviceCallback = stateCallback;
        this.mDeviceExecutor = executor;
        this.mCharacteristics = cameraCharacteristics;
        this.mPhysicalIdsToChars = map;
        this.mAppTargetSdkVersion = i;
        this.mContext = context;
        this.mCameraDeviceSetup = cameraDeviceSetup;
        java.lang.String format = java.lang.String.format("CameraDevice-JV-%s", this.mCameraId);
        this.TAG = format.length() > 23 ? format.substring(0, 23) : format;
        java.lang.Integer num = (java.lang.Integer) this.mCharacteristics.get(android.hardware.camera2.CameraCharacteristics.REQUEST_PARTIAL_RESULT_COUNT);
        if (num == null) {
            this.mTotalPartialCount = 1;
        } else {
            this.mTotalPartialCount = num.intValue();
        }
        this.mIsPrivilegedApp = checkPrivilegedAppList();
    }

    public android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks getCallbacks() {
        return this.mCallbacks;
    }

    public void setRemoteDevice(android.hardware.camera2.ICameraDeviceUser iCameraDeviceUser) throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (this.mInError) {
                return;
            }
            this.mRemoteDevice = new android.hardware.camera2.impl.ICameraDeviceUserWrapper(iCameraDeviceUser);
            android.os.IBinder asBinder = iCameraDeviceUser.asBinder();
            if (asBinder != null) {
                try {
                    asBinder.linkToDeath(this, 0);
                } catch (android.os.RemoteException e) {
                    this.mDeviceExecutor.execute(this.mCallOnDisconnected);
                    throw new android.hardware.camera2.CameraAccessException(2, "The camera device has encountered a serious error");
                }
            }
            this.mDeviceExecutor.execute(this.mCallOnOpened);
            this.mDeviceExecutor.execute(this.mCallOnUnconfigured);
            this.mRemoteDeviceInit = true;
        }
    }

    public void setRemoteFailure(android.os.ServiceSpecificException serviceSpecificException) {
        final boolean z;
        final int i = 4;
        switch (serviceSpecificException.errorCode) {
            case 4:
                z = false;
                break;
            case 5:
            case 9:
            default:
                android.util.Log.e(this.TAG, "Unexpected failure in opening camera device: " + serviceSpecificException.errorCode + serviceSpecificException.getMessage());
                z = true;
                break;
            case 6:
                i = 3;
                z = true;
                break;
            case 7:
                z = true;
                i = 1;
                break;
            case 8:
                i = 2;
                z = true;
                break;
            case 10:
                z = true;
                break;
        }
        synchronized (this.mInterfaceLock) {
            this.mInError = true;
            this.mDeviceExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.8
                @Override // java.lang.Runnable
                public void run() {
                    if (z) {
                        android.hardware.camera2.impl.CameraDeviceImpl.this.mDeviceCallback.onError(android.hardware.camera2.impl.CameraDeviceImpl.this, i);
                    } else {
                        android.hardware.camera2.impl.CameraDeviceImpl.this.mDeviceCallback.onDisconnected(android.hardware.camera2.impl.CameraDeviceImpl.this);
                    }
                }
            });
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public java.lang.String getId() {
        return this.mCameraId;
    }

    public void configureOutputs(java.util.List<android.view.Surface> list) throws android.hardware.camera2.CameraAccessException {
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        java.util.Iterator<android.view.Surface> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new android.hardware.camera2.params.OutputConfiguration(it.next()));
        }
        configureStreamsChecked(null, arrayList, 0, null, android.os.SystemClock.uptimeMillis());
    }

    public boolean configureStreamsChecked(android.hardware.camera2.params.InputConfiguration inputConfiguration, java.util.List<android.hardware.camera2.params.OutputConfiguration> list, int i, android.hardware.camera2.CaptureRequest captureRequest, long j) throws android.hardware.camera2.CameraAccessException {
        java.util.List<android.hardware.camera2.params.OutputConfiguration> list2;
        int[] endConfigure;
        if (list != null) {
            list2 = list;
        } else {
            list2 = new java.util.ArrayList<>();
        }
        if (list2.size() == 0 && inputConfiguration != null) {
            throw new java.lang.IllegalArgumentException("cannot configure an input stream without any output streams");
        }
        checkInputConfiguration(inputConfiguration);
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            java.util.HashSet hashSet = new java.util.HashSet(list2);
            java.util.ArrayList<java.lang.Integer> arrayList = new java.util.ArrayList();
            for (int i2 = 0; i2 < this.mConfiguredOutputs.size(); i2++) {
                int keyAt = this.mConfiguredOutputs.keyAt(i2);
                android.hardware.camera2.params.OutputConfiguration valueAt = this.mConfiguredOutputs.valueAt(i2);
                if (list2.contains(valueAt) && !valueAt.isDeferredConfiguration()) {
                    hashSet.remove(valueAt);
                }
                arrayList.add(java.lang.Integer.valueOf(keyAt));
            }
            this.mDeviceExecutor.execute(this.mCallOnBusy);
            stopRepeating();
            try {
                try {
                    waitUntilIdle();
                    this.mRemoteDevice.beginConfigure();
                    android.hardware.camera2.params.InputConfiguration value = this.mConfiguredInput.getValue();
                    if (inputConfiguration != value && (inputConfiguration == null || !inputConfiguration.equals(value))) {
                        if (value != null) {
                            this.mRemoteDevice.deleteStream(this.mConfiguredInput.getKey().intValue());
                            this.mConfiguredInput = new java.util.AbstractMap.SimpleEntry<>(-1, null);
                        }
                        if (inputConfiguration != null) {
                            this.mConfiguredInput = new java.util.AbstractMap.SimpleEntry<>(java.lang.Integer.valueOf(this.mRemoteDevice.createInputStream(inputConfiguration.getWidth(), inputConfiguration.getHeight(), inputConfiguration.getFormat(), inputConfiguration.isMultiResolution())), inputConfiguration);
                        }
                    }
                    for (java.lang.Integer num : arrayList) {
                        this.mRemoteDevice.deleteStream(num.intValue());
                        this.mConfiguredOutputs.delete(num.intValue());
                    }
                    for (android.hardware.camera2.params.OutputConfiguration outputConfiguration : list2) {
                        if (hashSet.contains(outputConfiguration)) {
                            this.mConfiguredOutputs.put(this.mRemoteDevice.createStream(outputConfiguration), outputConfiguration);
                        }
                    }
                    if (captureRequest == null) {
                        endConfigure = this.mRemoteDevice.endConfigure(i, null, j);
                    } else {
                        endConfigure = this.mRemoteDevice.endConfigure(i, captureRequest.getNativeCopy(), j);
                    }
                    this.mOfflineSupport.clear();
                    if (endConfigure != null && endConfigure.length > 0) {
                        for (int i3 : endConfigure) {
                            this.mOfflineSupport.add(java.lang.Integer.valueOf(i3));
                        }
                    }
                    if (list2.size() > 0) {
                        this.mDeviceExecutor.execute(this.mCallOnIdle);
                    }
                } catch (android.hardware.camera2.CameraAccessException e) {
                    if (e.getReason() == 4) {
                        throw new java.lang.IllegalStateException("The camera is currently busy. You must wait until the previous operation completes.", e);
                    }
                    throw e;
                } catch (java.lang.IllegalArgumentException e2) {
                    android.util.Log.w(this.TAG, "Stream configuration failed due to: " + e2.getMessage());
                    return false;
                }
            } finally {
                this.mDeviceExecutor.execute(this.mCallOnUnconfigured);
            }
        }
        return true;
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createCaptureSession(java.util.List<android.view.Surface> list, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        java.util.Iterator<android.view.Surface> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new android.hardware.camera2.params.OutputConfiguration(it.next()));
        }
        createCaptureSessionInternal(null, arrayList, stateCallback, checkAndWrapHandler(handler), 0, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createCaptureSessionByOutputConfigurations(java.util.List<android.hardware.camera2.params.OutputConfiguration> list, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        createCaptureSessionInternal(null, new java.util.ArrayList(list), stateCallback, checkAndWrapHandler(handler), 0, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createReprocessableCaptureSession(android.hardware.camera2.params.InputConfiguration inputConfiguration, java.util.List<android.view.Surface> list, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        if (inputConfiguration == null) {
            throw new java.lang.IllegalArgumentException("inputConfig cannot be null when creating a reprocessable capture session");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        java.util.Iterator<android.view.Surface> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new android.hardware.camera2.params.OutputConfiguration(it.next()));
        }
        createCaptureSessionInternal(inputConfiguration, arrayList, stateCallback, checkAndWrapHandler(handler), 0, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createReprocessableCaptureSessionByConfigurations(android.hardware.camera2.params.InputConfiguration inputConfiguration, java.util.List<android.hardware.camera2.params.OutputConfiguration> list, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        if (inputConfiguration == null) {
            throw new java.lang.IllegalArgumentException("inputConfig cannot be null when creating a reprocessable capture session");
        }
        if (list == null) {
            throw new java.lang.IllegalArgumentException("Output configurations cannot be null when creating a reprocessable capture session");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.hardware.camera2.params.OutputConfiguration> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new android.hardware.camera2.params.OutputConfiguration(it.next()));
        }
        createCaptureSessionInternal(inputConfiguration, arrayList, stateCallback, checkAndWrapHandler(handler), 0, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createConstrainedHighSpeedCaptureSession(java.util.List<android.view.Surface> list, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        if (list == null || list.size() == 0 || list.size() > 2) {
            throw new java.lang.IllegalArgumentException("Output surface list must not be null and the size must be no more than 2");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        java.util.Iterator<android.view.Surface> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new android.hardware.camera2.params.OutputConfiguration(it.next()));
        }
        createCaptureSessionInternal(null, arrayList, stateCallback, checkAndWrapHandler(handler), 1, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createCustomCaptureSession(android.hardware.camera2.params.InputConfiguration inputConfiguration, java.util.List<android.hardware.camera2.params.OutputConfiguration> list, int i, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.hardware.camera2.params.OutputConfiguration> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new android.hardware.camera2.params.OutputConfiguration(it.next()));
        }
        createCaptureSessionInternal(inputConfiguration, arrayList, stateCallback, checkAndWrapHandler(handler), i, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createCaptureSession(android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.hardware.camera2.CameraAccessException {
        if (sessionConfiguration == null) {
            throw new java.lang.IllegalArgumentException("Invalid session configuration");
        }
        java.util.List<android.hardware.camera2.params.OutputConfiguration> outputConfigurations = sessionConfiguration.getOutputConfigurations();
        if (outputConfigurations == null) {
            throw new java.lang.IllegalArgumentException("Invalid output configurations");
        }
        if (sessionConfiguration.getExecutor() == null) {
            throw new java.lang.IllegalArgumentException("Invalid executor");
        }
        createCaptureSessionInternal(sessionConfiguration.getInputConfiguration(), outputConfigurations, sessionConfiguration.getStateCallback(), sessionConfiguration.getExecutor(), sessionConfiguration.getSessionType(), sessionConfiguration.getSessionParameters());
    }

    private void createCaptureSessionInternal(android.hardware.camera2.params.InputConfiguration inputConfiguration, java.util.List<android.hardware.camera2.params.OutputConfiguration> list, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback, java.util.concurrent.Executor executor, int i, android.hardware.camera2.CaptureRequest captureRequest) throws android.hardware.camera2.CameraAccessException {
        android.view.Surface surface;
        android.hardware.camera2.impl.CameraCaptureSessionCore cameraCaptureSessionImpl;
        android.view.Surface surface2;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            boolean z = false;
            boolean z2 = i == 1;
            if (z2 && inputConfiguration != null) {
                throw new java.lang.IllegalArgumentException("Constrained high speed session doesn't support input configuration yet.");
            }
            if (this.mCurrentExtensionSession != null) {
                this.mCurrentExtensionSession.commitStats();
            }
            if (this.mCurrentAdvancedExtensionSession != null) {
                this.mCurrentAdvancedExtensionSession.commitStats();
            }
            if (this.mCurrentSession != null) {
                this.mCurrentSession.replaceSessionClose();
            }
            if (this.mCurrentExtensionSession != null) {
                this.mCurrentExtensionSession.release(false);
                this.mCurrentExtensionSession = null;
            }
            if (this.mCurrentAdvancedExtensionSession != null) {
                this.mCurrentAdvancedExtensionSession.release(false);
                this.mCurrentAdvancedExtensionSession = null;
            }
            try {
                boolean configureStreamsChecked = configureStreamsChecked(inputConfiguration, list, i, captureRequest, uptimeMillis);
                if (configureStreamsChecked && inputConfiguration != null) {
                    surface2 = this.mRemoteDevice.getInputSurface();
                } else {
                    surface2 = null;
                }
                surface = surface2;
                z = configureStreamsChecked;
                e = null;
            } catch (android.hardware.camera2.CameraAccessException e) {
                e = e;
                surface = null;
            }
            if (!z2) {
                int i2 = this.mNextSessionId;
                this.mNextSessionId = i2 + 1;
                cameraCaptureSessionImpl = new android.hardware.camera2.impl.CameraCaptureSessionImpl(i2, surface, stateCallback, executor, this, this.mDeviceExecutor, z);
            } else {
                java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
                java.util.Iterator<android.hardware.camera2.params.OutputConfiguration> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().getSurface());
                }
                android.hardware.camera2.utils.SurfaceUtils.checkConstrainedHighSpeedSurfaces(arrayList, null, (android.hardware.camera2.params.StreamConfigurationMap) getCharacteristics().get(android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP));
                int i3 = this.mNextSessionId;
                this.mNextSessionId = i3 + 1;
                cameraCaptureSessionImpl = new android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl(i3, stateCallback, executor, this, this.mDeviceExecutor, z, this.mCharacteristics);
            }
            this.mCurrentSession = cameraCaptureSessionImpl;
            if (e == null) {
                this.mSessionStateCallback = this.mCurrentSession.getDeviceStateCallback();
            } else {
                throw e;
            }
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public boolean isSessionConfigurationSupported(android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.hardware.camera2.CameraAccessException, java.lang.UnsupportedOperationException, java.lang.IllegalArgumentException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (android.app.compat.CompatChanges.isChangeEnabled(CHECK_PARAMS_IN_IS_SESSION_CONFIGURATION_SUPPORTED) && com.android.internal.camera.flags.Flags.cameraDeviceSetup() && this.mCameraDeviceSetup != null) {
                return this.mCameraDeviceSetup.isSessionConfigurationSupported(sessionConfiguration);
            }
            return this.mRemoteDevice.isSessionConfigurationSupported(sessionConfiguration);
        }
    }

    public void setSessionListener(android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK stateCallbackKK) {
        synchronized (this.mInterfaceLock) {
            this.mSessionStateCallback = stateCallbackKK;
        }
    }

    public static void disableZslIfNeeded(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, int i, int i2) {
        if ((i >= 26 && i2 == 2) || ((java.lang.Boolean) cameraMetadataNative.get(android.hardware.camera2.CaptureRequest.CONTROL_ENABLE_ZSL)) == null) {
            return;
        }
        cameraMetadataNative.set((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean>>) android.hardware.camera2.CaptureRequest.CONTROL_ENABLE_ZSL, (android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean>) false);
    }

    @Override // android.hardware.camera2.CameraDevice
    public android.hardware.camera2.CaptureRequest.Builder createCaptureRequest(int i, java.util.Set<java.lang.String> set) throws android.hardware.camera2.CameraAccessException {
        android.hardware.camera2.CaptureRequest.Builder builder;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            java.util.Iterator<java.lang.String> it = set.iterator();
            while (it.hasNext()) {
                if (it.next() == getId()) {
                    throw new java.lang.IllegalStateException("Physical id matches the logical id!");
                }
            }
            android.hardware.camera2.impl.CameraMetadataNative createDefaultRequest = this.mRemoteDevice.createDefaultRequest(i);
            disableZslIfNeeded(createDefaultRequest, this.mAppTargetSdkVersion, i);
            builder = new android.hardware.camera2.CaptureRequest.Builder(createDefaultRequest, false, -1, getId(), set);
        }
        return builder;
    }

    @Override // android.hardware.camera2.CameraDevice
    public android.hardware.camera2.CaptureRequest.Builder createCaptureRequest(int i) throws android.hardware.camera2.CameraAccessException {
        android.hardware.camera2.CaptureRequest.Builder builder;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            android.hardware.camera2.impl.CameraMetadataNative createDefaultRequest = this.mRemoteDevice.createDefaultRequest(i);
            disableZslIfNeeded(createDefaultRequest, this.mAppTargetSdkVersion, i);
            builder = new android.hardware.camera2.CaptureRequest.Builder(createDefaultRequest, false, -1, getId(), null);
        }
        return builder;
    }

    @Override // android.hardware.camera2.CameraDevice
    public android.hardware.camera2.CaptureRequest.Builder createReprocessCaptureRequest(android.hardware.camera2.TotalCaptureResult totalCaptureResult) throws android.hardware.camera2.CameraAccessException {
        android.hardware.camera2.CaptureRequest.Builder builder;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            builder = new android.hardware.camera2.CaptureRequest.Builder(new android.hardware.camera2.impl.CameraMetadataNative(totalCaptureResult.getNativeCopy()), true, totalCaptureResult.getSessionId(), getId(), null);
            builder.set(android.hardware.camera2.CaptureRequest.CONTROL_CAPTURE_INTENT, 2);
        }
        return builder;
    }

    public void prepare(android.view.Surface surface) throws android.hardware.camera2.CameraAccessException {
        int i;
        if (surface == null) {
            throw new java.lang.IllegalArgumentException("Surface is null");
        }
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            int i2 = 0;
            while (true) {
                if (i2 >= this.mConfiguredOutputs.size()) {
                    i = -1;
                    break;
                } else if (!this.mConfiguredOutputs.valueAt(i2).getSurfaces().contains(surface)) {
                    i2++;
                } else {
                    i = this.mConfiguredOutputs.keyAt(i2);
                    break;
                }
            }
            if (i == -1) {
                throw new java.lang.IllegalArgumentException("Surface is not part of this session");
            }
            this.mRemoteDevice.prepare(i);
        }
    }

    public void prepare(int i, android.view.Surface surface) throws android.hardware.camera2.CameraAccessException {
        int i2;
        if (surface == null) {
            throw new java.lang.IllegalArgumentException("Surface is null");
        }
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid maxCount given: " + i);
        }
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            int i3 = 0;
            while (true) {
                if (i3 >= this.mConfiguredOutputs.size()) {
                    i2 = -1;
                    break;
                } else if (surface != this.mConfiguredOutputs.valueAt(i3).getSurface()) {
                    i3++;
                } else {
                    i2 = this.mConfiguredOutputs.keyAt(i3);
                    break;
                }
            }
            if (i2 == -1) {
                throw new java.lang.IllegalArgumentException("Surface is not part of this session");
            }
            this.mRemoteDevice.prepare2(i, i2);
        }
    }

    public void updateOutputConfiguration(android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.hardware.camera2.CameraAccessException {
        int i;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            int i2 = 0;
            while (true) {
                if (i2 >= this.mConfiguredOutputs.size()) {
                    i = -1;
                    break;
                } else if (outputConfiguration.getSurface() != this.mConfiguredOutputs.valueAt(i2).getSurface()) {
                    i2++;
                } else {
                    i = this.mConfiguredOutputs.keyAt(i2);
                    break;
                }
            }
            if (i == -1) {
                throw new java.lang.IllegalArgumentException("Invalid output configuration");
            }
            this.mRemoteDevice.updateOutputConfiguration(i, outputConfiguration);
            this.mConfiguredOutputs.put(i, outputConfiguration);
        }
    }

    public android.hardware.camera2.CameraOfflineSession switchToOffline(java.util.Collection<android.view.Surface> collection, java.util.concurrent.Executor executor, android.hardware.camera2.CameraOfflineSession.CameraOfflineSessionCallback cameraOfflineSessionCallback) throws android.hardware.camera2.CameraAccessException {
        android.hardware.camera2.impl.CameraOfflineSessionImpl cameraOfflineSessionImpl;
        int i;
        if (collection.isEmpty()) {
            throw new java.lang.IllegalArgumentException("Invalid offline surfaces!");
        }
        final java.util.HashSet hashSet = new java.util.HashSet();
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (this.mOfflineSessionImpl != null) {
                throw new java.lang.IllegalStateException("Switch to offline mode already in progress");
            }
            for (android.view.Surface surface : collection) {
                int i2 = 0;
                while (true) {
                    if (i2 >= this.mConfiguredOutputs.size()) {
                        i = -1;
                        break;
                    }
                    if (surface != this.mConfiguredOutputs.valueAt(i2).getSurface()) {
                        i2++;
                    } else {
                        i = this.mConfiguredOutputs.keyAt(i2);
                        sparseArray.append(i, this.mConfiguredOutputs.valueAt(i2));
                        break;
                    }
                }
                if (i == -1) {
                    throw new java.lang.IllegalArgumentException("Offline surface is not part of this session");
                }
                if (!this.mOfflineSupport.contains(java.lang.Integer.valueOf(i))) {
                    throw new java.lang.IllegalArgumentException("Surface: " + surface + " does not  support offline mode");
                }
                hashSet.add(java.lang.Integer.valueOf(i));
            }
            stopRepeating();
            this.mOfflineSessionImpl = new android.hardware.camera2.impl.CameraOfflineSessionImpl(this.mCameraId, this.mCharacteristics, executor, cameraOfflineSessionCallback, sparseArray, this.mConfiguredInput, this.mConfiguredOutputs, this.mFrameNumberTracker, this.mCaptureCallbackMap, this.mRequestLastFrameNumbersList);
            cameraOfflineSessionImpl = this.mOfflineSessionImpl;
            this.mOfflineSwitchService = java.util.concurrent.Executors.newSingleThreadExecutor();
            this.mConfiguredOutputs.clear();
            this.mConfiguredInput = new java.util.AbstractMap.SimpleEntry<>(-1, null);
            this.mIdle = true;
            this.mCaptureCallbackMap = new android.util.SparseArray<>();
            this.mBatchOutputMap = new java.util.HashMap<>();
            this.mFrameNumberTracker = new android.hardware.camera2.impl.FrameNumberTracker();
            this.mCurrentSession.closeWithoutDraining();
            this.mCurrentSession = null;
        }
        this.mOfflineSwitchService.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.9
            @Override // java.lang.Runnable
            public void run() {
                try {
                    try {
                        android.hardware.camera2.impl.CameraDeviceImpl.this.mOfflineSessionImpl.setRemoteSession(android.hardware.camera2.impl.CameraDeviceImpl.this.mRemoteDevice.switchToOffline(android.hardware.camera2.impl.CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks(), java.util.Arrays.stream((java.lang.Integer[]) hashSet.toArray(new java.lang.Integer[hashSet.size()])).mapToInt(new android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray()));
                    } catch (android.hardware.camera2.CameraAccessException e) {
                        android.hardware.camera2.impl.CameraDeviceImpl.this.mOfflineSessionImpl.notifyFailedSwitch();
                    }
                } finally {
                    android.hardware.camera2.impl.CameraDeviceImpl.this.mOfflineSessionImpl = null;
                }
            }
        });
        return cameraOfflineSessionImpl;
    }

    public boolean supportsOfflineProcessing(android.view.Surface surface) {
        int i;
        boolean contains;
        if (surface == null) {
            throw new java.lang.IllegalArgumentException("Surface is null");
        }
        synchronized (this.mInterfaceLock) {
            int i2 = 0;
            while (true) {
                if (i2 >= this.mConfiguredOutputs.size()) {
                    i = -1;
                    break;
                }
                if (surface != this.mConfiguredOutputs.valueAt(i2).getSurface()) {
                    i2++;
                } else {
                    i = this.mConfiguredOutputs.keyAt(i2);
                    break;
                }
            }
            if (i == -1) {
                throw new java.lang.IllegalArgumentException("Surface is not part of this session");
            }
            contains = this.mOfflineSupport.contains(java.lang.Integer.valueOf(i));
        }
        return contains;
    }

    public void tearDown(android.view.Surface surface) throws android.hardware.camera2.CameraAccessException {
        int i;
        if (surface == null) {
            throw new java.lang.IllegalArgumentException("Surface is null");
        }
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            int i2 = 0;
            while (true) {
                if (i2 >= this.mConfiguredOutputs.size()) {
                    i = -1;
                    break;
                } else if (surface != this.mConfiguredOutputs.valueAt(i2).getSurface()) {
                    i2++;
                } else {
                    i = this.mConfiguredOutputs.keyAt(i2);
                    break;
                }
            }
            if (i == -1) {
                throw new java.lang.IllegalArgumentException("Surface is not part of this session");
            }
            this.mRemoteDevice.tearDown(i);
        }
    }

    public void finalizeOutputConfigs(java.util.List<android.hardware.camera2.params.OutputConfiguration> list) throws android.hardware.camera2.CameraAccessException {
        int i;
        if (list == null || list.size() == 0) {
            throw new java.lang.IllegalArgumentException("deferred config is null or empty");
        }
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            for (android.hardware.camera2.params.OutputConfiguration outputConfiguration : list) {
                int i2 = 0;
                while (true) {
                    if (i2 < this.mConfiguredOutputs.size()) {
                        if (!outputConfiguration.equals(this.mConfiguredOutputs.valueAt(i2))) {
                            i2++;
                        } else {
                            i = this.mConfiguredOutputs.keyAt(i2);
                            break;
                        }
                    } else {
                        i = -1;
                        break;
                    }
                }
                if (i == -1) {
                    throw new java.lang.IllegalArgumentException("Deferred config is not part of this session");
                }
                if (outputConfiguration.getSurfaces().size() == 0) {
                    throw new java.lang.IllegalArgumentException("The final config for stream " + i + " must have at least 1 surface");
                }
                this.mRemoteDevice.finalizeOutputConfigurations(i, outputConfiguration);
                this.mConfiguredOutputs.put(i, outputConfiguration);
            }
        }
    }

    public int capture(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.impl.CaptureCallback captureCallback, java.util.concurrent.Executor executor) throws android.hardware.camera2.CameraAccessException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(captureRequest);
        return submitCaptureRequest(arrayList, captureCallback, executor, false);
    }

    public int captureBurst(java.util.List<android.hardware.camera2.CaptureRequest> list, android.hardware.camera2.impl.CaptureCallback captureCallback, java.util.concurrent.Executor executor) throws android.hardware.camera2.CameraAccessException {
        if (list == null || list.isEmpty()) {
            throw new java.lang.IllegalArgumentException("At least one request must be given");
        }
        return submitCaptureRequest(list, captureCallback, executor, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkEarlyTriggerSequenceCompleteLocked(final int i, long j, int[] iArr) {
        if (j == -1) {
            int indexOfKey = this.mCaptureCallbackMap.indexOfKey(i);
            final android.hardware.camera2.impl.CaptureCallbackHolder valueAt = indexOfKey >= 0 ? this.mCaptureCallbackMap.valueAt(indexOfKey) : null;
            if (valueAt != null) {
                this.mCaptureCallbackMap.removeAt(indexOfKey);
                java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.10
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!android.hardware.camera2.impl.CameraDeviceImpl.this.isClosed()) {
                            valueAt.getCallback().onCaptureSequenceAborted(android.hardware.camera2.impl.CameraDeviceImpl.this, i);
                        }
                    }
                };
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    valueAt.getExecutor().execute(runnable);
                    return;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            android.util.Log.w(this.TAG, java.lang.String.format("did not register callback to request %d", java.lang.Integer.valueOf(i)));
            return;
        }
        this.mRequestLastFrameNumbersList.add(new android.hardware.camera2.impl.RequestLastFrameNumbersHolder(i, j, iArr));
        checkAndFireSequenceComplete();
    }

    private int[] getRequestTypes(android.hardware.camera2.CaptureRequest[] captureRequestArr) {
        int[] iArr = new int[captureRequestArr.length];
        for (int i = 0; i < captureRequestArr.length; i++) {
            iArr[i] = captureRequestArr[i].getRequestType();
        }
        return iArr;
    }

    private boolean hasBatchedOutputs(java.util.List<android.hardware.camera2.CaptureRequest> list) {
        for (int i = 0; i < list.size(); i++) {
            android.hardware.camera2.CaptureRequest captureRequest = list.get(i);
            if (!captureRequest.isPartOfCRequestList()) {
                return false;
            }
            if (i == 0 && captureRequest.getTargets().size() != 2) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTracker(int i, long j, int i2, android.hardware.camera2.CaptureResult captureResult, boolean z) {
        if (this.mBatchOutputMap.containsKey(java.lang.Integer.valueOf(i))) {
            int intValue = this.mBatchOutputMap.get(java.lang.Integer.valueOf(i)).intValue();
            for (int i3 = 0; i3 < intValue; i3++) {
                this.mFrameNumberTracker.updateTracker(j - ((intValue - 1) - i3), captureResult, z, i2);
            }
            return;
        }
        this.mFrameNumberTracker.updateTracker(j, captureResult, z, i2);
    }

    private int submitCaptureRequest(java.util.List<android.hardware.camera2.CaptureRequest> list, android.hardware.camera2.impl.CaptureCallback captureCallback, java.util.concurrent.Executor executor, boolean z) throws android.hardware.camera2.CameraAccessException {
        int requestId;
        java.util.concurrent.Executor checkExecutor = checkExecutor(executor, captureCallback);
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            for (android.hardware.camera2.CaptureRequest captureRequest : list) {
                if (captureRequest.getTargets().isEmpty()) {
                    throw new java.lang.IllegalArgumentException("Each request must have at least one Surface target");
                }
                java.util.Iterator<android.view.Surface> it = captureRequest.getTargets().iterator();
                while (it.hasNext()) {
                    if (it.next() == null) {
                        throw new java.lang.IllegalArgumentException("Null Surface targets are not allowed");
                    }
                }
            }
            if (z) {
                stopRepeating();
            }
            android.hardware.camera2.CaptureRequest[] captureRequestArr = (android.hardware.camera2.CaptureRequest[]) list.toArray(new android.hardware.camera2.CaptureRequest[list.size()]);
            for (android.hardware.camera2.CaptureRequest captureRequest2 : captureRequestArr) {
                captureRequest2.convertSurfaceToStreamId(this.mConfiguredOutputs);
            }
            android.hardware.camera2.utils.SubmitInfo submitRequestList = this.mRemoteDevice.submitRequestList(captureRequestArr, z);
            for (android.hardware.camera2.CaptureRequest captureRequest3 : captureRequestArr) {
                captureRequest3.recoverStreamIdToSurface();
            }
            if (hasBatchedOutputs(list)) {
                this.mBatchOutputMap.put(java.lang.Integer.valueOf(submitRequestList.getRequestId()), java.lang.Integer.valueOf(list.size()));
            }
            if (captureCallback != null) {
                this.mCaptureCallbackMap.put(submitRequestList.getRequestId(), new android.hardware.camera2.impl.CaptureCallbackHolder(captureCallback, list, checkExecutor, z, this.mNextSessionId - 1));
            }
            if (z) {
                if (this.mRepeatingRequestId != -1) {
                    checkEarlyTriggerSequenceCompleteLocked(this.mRepeatingRequestId, submitRequestList.getLastFrameNumber(), this.mRepeatingRequestTypes);
                }
                this.mRepeatingRequestId = submitRequestList.getRequestId();
                this.mRepeatingRequestTypes = getRequestTypes(captureRequestArr);
            } else {
                this.mRequestLastFrameNumbersList.add(new android.hardware.camera2.impl.RequestLastFrameNumbersHolder(list, submitRequestList));
            }
            if (this.mIdle) {
                this.mDeviceExecutor.execute(this.mCallOnActive);
            }
            this.mIdle = false;
            requestId = submitRequestList.getRequestId();
        }
        return requestId;
    }

    public int setRepeatingRequest(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.impl.CaptureCallback captureCallback, java.util.concurrent.Executor executor) throws android.hardware.camera2.CameraAccessException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(captureRequest);
        return submitCaptureRequest(arrayList, captureCallback, executor, true);
    }

    public int setRepeatingBurst(java.util.List<android.hardware.camera2.CaptureRequest> list, android.hardware.camera2.impl.CaptureCallback captureCallback, java.util.concurrent.Executor executor) throws android.hardware.camera2.CameraAccessException {
        if (list == null || list.isEmpty()) {
            throw new java.lang.IllegalArgumentException("At least one request must be given");
        }
        return submitCaptureRequest(list, captureCallback, executor, true);
    }

    public void stopRepeating() throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (this.mRepeatingRequestId != -1) {
                int i = this.mRepeatingRequestId;
                this.mRepeatingRequestId = -1;
                this.mFailedRepeatingRequestId = -1;
                int[] iArr = this.mRepeatingRequestTypes;
                this.mRepeatingRequestTypes = null;
                this.mFailedRepeatingRequestTypes = null;
                try {
                    checkEarlyTriggerSequenceCompleteLocked(i, this.mRemoteDevice.cancelRequest(i), iArr);
                } catch (java.lang.IllegalArgumentException e) {
                    this.mFailedRepeatingRequestId = i;
                    this.mFailedRepeatingRequestTypes = iArr;
                }
            }
        }
    }

    private void waitUntilIdle() throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (this.mRepeatingRequestId != -1) {
                throw new java.lang.IllegalStateException("Active repeating request ongoing");
            }
            this.mRemoteDevice.waitUntilIdle();
        }
    }

    public void flush() throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            this.mDeviceExecutor.execute(this.mCallOnBusy);
            if (this.mIdle) {
                this.mDeviceExecutor.execute(this.mCallOnIdle);
                return;
            }
            long flush = this.mRemoteDevice.flush();
            if (this.mRepeatingRequestId != -1) {
                checkEarlyTriggerSequenceCompleteLocked(this.mRepeatingRequestId, flush, this.mRepeatingRequestTypes);
                this.mRepeatingRequestId = -1;
                this.mRepeatingRequestTypes = null;
            }
        }
    }

    @Override // android.hardware.camera2.CameraDevice, java.lang.AutoCloseable
    public void close() {
        synchronized (this.mInterfaceLock) {
            if (this.mClosing.getAndSet(true)) {
                return;
            }
            if (this.mOfflineSwitchService != null) {
                this.mOfflineSwitchService.shutdownNow();
                this.mOfflineSwitchService = null;
            }
            if (this.mCurrentExtensionSession != null) {
                this.mCurrentExtensionSession.commitStats();
            }
            if (this.mCurrentAdvancedExtensionSession != null) {
                this.mCurrentAdvancedExtensionSession.commitStats();
            }
            if (this.mRemoteDevice != null) {
                this.mRemoteDevice.disconnect();
                this.mRemoteDevice.unlinkToDeath(this, 0);
            }
            if (this.mCurrentExtensionSession != null) {
                this.mCurrentExtensionSession.release(true);
                this.mCurrentExtensionSession = null;
            }
            if (this.mCurrentAdvancedExtensionSession != null) {
                this.mCurrentAdvancedExtensionSession.release(true);
                this.mCurrentAdvancedExtensionSession = null;
            }
            if (this.mRemoteDevice != null || this.mInError) {
                this.mDeviceExecutor.execute(this.mCallOnClosed);
            }
            this.mRemoteDevice = null;
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private boolean checkInputConfigurationWithStreamConfigurationsAs(android.hardware.camera2.params.InputConfiguration inputConfiguration, android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap) {
        int[] inputFormats = streamConfigurationMap.getInputFormats();
        int format = inputConfiguration.getFormat();
        boolean z = false;
        for (int i : inputFormats) {
            if (i == format) {
                z = true;
            }
        }
        if (format == 36 || format == 37 || format == 38 || format == 32) {
            return true;
        }
        if (!z) {
            return false;
        }
        android.util.Size[] inputSizes = streamConfigurationMap.getInputSizes(format);
        boolean z2 = false;
        for (android.util.Size size : inputSizes) {
            if (inputConfiguration.getWidth() == size.getWidth() && inputConfiguration.getHeight() == size.getHeight()) {
                z2 = true;
            }
        }
        return z2;
    }

    private boolean checkInputConfigurationWithStreamConfigurations(android.hardware.camera2.params.InputConfiguration inputConfiguration, boolean z) {
        android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationMap> key = android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP;
        if (z) {
            key = android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP_MAXIMUM_RESOLUTION;
        }
        android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap = (android.hardware.camera2.params.StreamConfigurationMap) this.mCharacteristics.get(key);
        if (streamConfigurationMap != null && checkInputConfigurationWithStreamConfigurationsAs(inputConfiguration, streamConfigurationMap)) {
            return true;
        }
        java.util.Iterator<java.util.Map.Entry<java.lang.String, android.hardware.camera2.CameraCharacteristics>> it = this.mPhysicalIdsToChars.entrySet().iterator();
        while (it.hasNext()) {
            android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap2 = (android.hardware.camera2.params.StreamConfigurationMap) it.next().getValue().get(key);
            if (streamConfigurationMap2 != null && checkInputConfigurationWithStreamConfigurationsAs(inputConfiguration, streamConfigurationMap2)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPrivilegedAppList() {
        java.lang.String currentOpPackageName = android.app.ActivityThread.currentOpPackageName();
        java.lang.String str = android.os.SystemProperties.get("persist.vendor.camera.privapp.list");
        if (str.length() > 0) {
            android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(',');
            simpleStringSplitter.setString(str);
            java.util.Iterator<java.lang.String> it = simpleStringSplitter.iterator();
            while (it.hasNext()) {
                if (currentOpPackageName.equals(it.next())) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean isPrivilegedApp() {
        return this.mIsPrivilegedApp;
    }

    private void checkInputConfiguration(android.hardware.camera2.params.InputConfiguration inputConfiguration) {
        if (inputConfiguration == null) {
            return;
        }
        int format = inputConfiguration.getFormat();
        boolean z = false;
        if (inputConfiguration.isMultiResolution()) {
            android.hardware.camera2.params.MultiResolutionStreamConfigurationMap multiResolutionStreamConfigurationMap = (android.hardware.camera2.params.MultiResolutionStreamConfigurationMap) this.mCharacteristics.get(android.hardware.camera2.CameraCharacteristics.SCALER_MULTI_RESOLUTION_STREAM_CONFIGURATION_MAP);
            boolean z2 = false;
            for (int i : multiResolutionStreamConfigurationMap.getInputFormats()) {
                if (i == format) {
                    z2 = true;
                }
            }
            if (!z2) {
                throw new java.lang.IllegalArgumentException("multi-resolution input format " + format + " is not valid");
            }
            for (android.hardware.camera2.params.MultiResolutionStreamInfo multiResolutionStreamInfo : multiResolutionStreamConfigurationMap.getInputInfo(format)) {
                if (inputConfiguration.getWidth() == multiResolutionStreamInfo.getWidth() && inputConfiguration.getHeight() == multiResolutionStreamInfo.getHeight()) {
                    z = true;
                }
            }
            if (!z) {
                throw new java.lang.IllegalArgumentException("Multi-resolution input size " + inputConfiguration.getWidth() + "x" + inputConfiguration.getHeight() + " is not valid");
            }
            return;
        }
        if (isPrivilegedApp()) {
            android.util.Log.w(this.TAG, "ignore input format/size check for white listed app");
        } else if (!checkInputConfigurationWithStreamConfigurations(inputConfiguration, false) && !checkInputConfigurationWithStreamConfigurations(inputConfiguration, true)) {
            throw new java.lang.IllegalArgumentException("Input config with format " + format + " and size " + inputConfiguration.getWidth() + "x" + inputConfiguration.getHeight() + " not supported by camera id " + this.mCameraId);
        }
    }

    public static abstract class StateCallbackKK extends android.hardware.camera2.CameraDevice.StateCallback {
        public void onUnconfigured(android.hardware.camera2.CameraDevice cameraDevice) {
        }

        public void onActive(android.hardware.camera2.CameraDevice cameraDevice) {
        }

        public void onBusy(android.hardware.camera2.CameraDevice cameraDevice) {
        }

        public void onIdle(android.hardware.camera2.CameraDevice cameraDevice) {
        }

        public void onRequestQueueEmpty() {
        }

        public void onSurfacePrepared(android.view.Surface surface) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAndFireSequenceComplete() {
        long completedFrameNumber = this.mFrameNumberTracker.getCompletedFrameNumber();
        long completedReprocessFrameNumber = this.mFrameNumberTracker.getCompletedReprocessFrameNumber();
        long completedZslStillFrameNumber = this.mFrameNumberTracker.getCompletedZslStillFrameNumber();
        java.util.Iterator<android.hardware.camera2.impl.RequestLastFrameNumbersHolder> it = this.mRequestLastFrameNumbersList.iterator();
        while (it.hasNext()) {
            final android.hardware.camera2.impl.RequestLastFrameNumbersHolder next = it.next();
            final int requestId = next.getRequestId();
            if (this.mRemoteDevice == null) {
                android.util.Log.w(this.TAG, "Camera closed while checking sequences");
                return;
            }
            if (!next.isSequenceCompleted()) {
                long lastRegularFrameNumber = next.getLastRegularFrameNumber();
                long lastReprocessFrameNumber = next.getLastReprocessFrameNumber();
                long lastZslStillFrameNumber = next.getLastZslStillFrameNumber();
                if (lastRegularFrameNumber <= completedFrameNumber && lastReprocessFrameNumber <= completedReprocessFrameNumber && lastZslStillFrameNumber <= completedZslStillFrameNumber) {
                    next.markSequenceCompleted();
                }
                int indexOfKey = this.mCaptureCallbackMap.indexOfKey(requestId);
                final android.hardware.camera2.impl.CaptureCallbackHolder valueAt = indexOfKey >= 0 ? this.mCaptureCallbackMap.valueAt(indexOfKey) : null;
                if (valueAt != null && next.isSequenceCompleted()) {
                    java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.11
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!android.hardware.camera2.impl.CameraDeviceImpl.this.isClosed()) {
                                valueAt.getCallback().onCaptureSequenceCompleted(android.hardware.camera2.impl.CameraDeviceImpl.this, requestId, next.getLastFrameNumber());
                            }
                        }
                    };
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        valueAt.getExecutor().execute(runnable);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
            if (next.isSequenceCompleted() && next.isInflightCompleted()) {
                int indexOfKey2 = this.mCaptureCallbackMap.indexOfKey(requestId);
                if (indexOfKey2 >= 0) {
                    this.mCaptureCallbackMap.removeAt(indexOfKey2);
                }
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeCompletedCallbackHolderLocked(long j, long j2, long j3) {
        java.util.Iterator<android.hardware.camera2.impl.RequestLastFrameNumbersHolder> it = this.mRequestLastFrameNumbersList.iterator();
        while (it.hasNext()) {
            android.hardware.camera2.impl.RequestLastFrameNumbersHolder next = it.next();
            int requestId = next.getRequestId();
            if (this.mRemoteDevice == null) {
                android.util.Log.w(this.TAG, "Camera closed while removing completed callback holders");
                return;
            }
            long lastRegularFrameNumber = next.getLastRegularFrameNumber();
            long lastReprocessFrameNumber = next.getLastReprocessFrameNumber();
            long lastZslStillFrameNumber = next.getLastZslStillFrameNumber();
            if (lastRegularFrameNumber <= j && lastReprocessFrameNumber <= j2 && lastZslStillFrameNumber <= j3) {
                if (next.isSequenceCompleted()) {
                    int indexOfKey = this.mCaptureCallbackMap.indexOfKey(requestId);
                    if (indexOfKey >= 0) {
                        this.mCaptureCallbackMap.removeAt(indexOfKey);
                    }
                    it.remove();
                } else {
                    next.markInflightCompleted();
                }
            }
        }
    }

    public void onDeviceError(int i, android.hardware.camera2.impl.CaptureResultExtras captureResultExtras) {
        synchronized (this.mInterfaceLock) {
            if (this.mRemoteDevice == null && this.mRemoteDeviceInit) {
                return;
            }
            if (this.mOfflineSessionImpl != null) {
                this.mOfflineSessionImpl.getCallbacks().onDeviceError(i, captureResultExtras);
                return;
            }
            switch (i) {
                case 0:
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mDeviceExecutor.execute(this.mCallOnDisconnected);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                case 1:
                    scheduleNotifyError(4);
                    return;
                case 2:
                default:
                    android.util.Log.e(this.TAG, "Unknown error from camera device: " + i);
                    scheduleNotifyError(5);
                    return;
                case 3:
                case 4:
                case 5:
                    onCaptureErrorLocked(i, captureResultExtras);
                    return;
                case 6:
                    scheduleNotifyError(3);
                    return;
            }
        }
    }

    private void scheduleNotifyError(int i) {
        this.mInError = true;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mDeviceExecutor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.BiConsumer() { // from class: android.hardware.camera2.impl.CameraDeviceImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.hardware.camera2.impl.CameraDeviceImpl) obj).notifyError(((java.lang.Integer) obj2).intValue());
                }
            }, this, java.lang.Integer.valueOf(i)).recycleOnUse());
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyError(int i) {
        if (!isClosed()) {
            this.mDeviceCallback.onError(this, i);
        }
    }

    private void onCaptureErrorLocked(int i, android.hardware.camera2.impl.CaptureResultExtras captureResultExtras) {
        int i2;
        long clearCallingIdentity;
        int requestId = captureResultExtras.getRequestId();
        int subsequenceId = captureResultExtras.getSubsequenceId();
        final long frameNumber = captureResultExtras.getFrameNumber();
        java.lang.String errorPhysicalCameraId = captureResultExtras.getErrorPhysicalCameraId();
        final android.hardware.camera2.impl.CaptureCallbackHolder captureCallbackHolder = this.mCaptureCallbackMap.get(requestId);
        if (captureCallbackHolder == null) {
            android.util.Log.e(this.TAG, java.lang.String.format("Receive capture error on unknown request ID %d", java.lang.Integer.valueOf(requestId)));
            return;
        }
        final android.hardware.camera2.CaptureRequest request = captureCallbackHolder.getRequest(subsequenceId);
        if (i == 5) {
            android.hardware.camera2.params.OutputConfiguration outputConfiguration = this.mConfiguredOutputs.get(captureResultExtras.getErrorStreamId());
            if (outputConfiguration == null) {
                android.util.Log.v(this.TAG, java.lang.String.format("Stream %d has been removed. Skipping buffer lost callback", java.lang.Integer.valueOf(captureResultExtras.getErrorStreamId())));
                return;
            }
            for (final android.view.Surface surface : outputConfiguration.getSurfaces()) {
                if (request.containsTarget(surface)) {
                    java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.12
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!android.hardware.camera2.impl.CameraDeviceImpl.this.isClosed()) {
                                captureCallbackHolder.getCallback().onCaptureBufferLost(android.hardware.camera2.impl.CameraDeviceImpl.this, request, surface, frameNumber);
                            }
                        }
                    };
                    clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        captureCallbackHolder.getExecutor().execute(runnable);
                    } finally {
                    }
                }
            }
            return;
        }
        boolean z = i == 4;
        if (this.mCurrentSession != null && this.mCurrentSession.isAborting()) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        final android.hardware.camera2.CaptureFailure captureFailure = new android.hardware.camera2.CaptureFailure(request, i2, z, requestId, frameNumber, errorPhysicalCameraId);
        java.lang.Runnable runnable2 = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.13
            @Override // java.lang.Runnable
            public void run() {
                if (!android.hardware.camera2.impl.CameraDeviceImpl.this.isClosed()) {
                    captureCallbackHolder.getCallback().onCaptureFailed(android.hardware.camera2.impl.CameraDeviceImpl.this, request, captureFailure);
                }
            }
        };
        if (errorPhysicalCameraId == null) {
            if (this.mBatchOutputMap.containsKey(java.lang.Integer.valueOf(requestId))) {
                for (int i3 = 0; i3 < this.mBatchOutputMap.get(java.lang.Integer.valueOf(requestId)).intValue(); i3++) {
                    this.mFrameNumberTracker.updateTracker(frameNumber - (subsequenceId - i3), true, request.getRequestType());
                }
            } else {
                this.mFrameNumberTracker.updateTracker(frameNumber, true, request.getRequestType());
            }
            checkAndFireSequenceComplete();
        }
        clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            captureCallbackHolder.getExecutor().execute(runnable2);
        } finally {
        }
    }

    public void onDeviceIdle() {
        synchronized (this.mInterfaceLock) {
            if (this.mRemoteDevice == null) {
                return;
            }
            if (this.mOfflineSessionImpl != null) {
                this.mOfflineSessionImpl.getCallbacks().onDeviceIdle();
                return;
            }
            removeCompletedCallbackHolderLocked(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE);
            if (!this.mIdle) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mDeviceExecutor.execute(this.mCallOnIdle);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            this.mIdle = true;
        }
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
            android.hardware.camera2.impl.CameraDeviceImpl.this.onDeviceError(i, captureResultExtras);
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onRepeatingRequestError(long j, int i) {
            synchronized (android.hardware.camera2.impl.CameraDeviceImpl.this.mInterfaceLock) {
                if (android.hardware.camera2.impl.CameraDeviceImpl.this.mRemoteDevice != null && android.hardware.camera2.impl.CameraDeviceImpl.this.mRepeatingRequestId != -1) {
                    if (android.hardware.camera2.impl.CameraDeviceImpl.this.mOfflineSessionImpl != null) {
                        android.hardware.camera2.impl.CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onRepeatingRequestError(j, i);
                        return;
                    }
                    android.hardware.camera2.impl.CameraDeviceImpl.this.checkEarlyTriggerSequenceCompleteLocked(android.hardware.camera2.impl.CameraDeviceImpl.this.mRepeatingRequestId, j, android.hardware.camera2.impl.CameraDeviceImpl.this.mRepeatingRequestTypes);
                    if (android.hardware.camera2.impl.CameraDeviceImpl.this.mRepeatingRequestId == i) {
                        android.hardware.camera2.impl.CameraDeviceImpl.this.mRepeatingRequestId = -1;
                        android.hardware.camera2.impl.CameraDeviceImpl.this.mRepeatingRequestTypes = null;
                    }
                    return;
                }
                if (android.hardware.camera2.impl.CameraDeviceImpl.this.mFailedRepeatingRequestId == i && android.hardware.camera2.impl.CameraDeviceImpl.this.mFailedRepeatingRequestTypes != null && android.hardware.camera2.impl.CameraDeviceImpl.this.mRemoteDevice != null) {
                    android.util.Log.v(android.hardware.camera2.impl.CameraDeviceImpl.this.TAG, "Resuming stop of failed repeating request with id: " + android.hardware.camera2.impl.CameraDeviceImpl.this.mFailedRepeatingRequestId);
                    android.hardware.camera2.impl.CameraDeviceImpl.this.checkEarlyTriggerSequenceCompleteLocked(android.hardware.camera2.impl.CameraDeviceImpl.this.mFailedRepeatingRequestId, j, android.hardware.camera2.impl.CameraDeviceImpl.this.mFailedRepeatingRequestTypes);
                    android.hardware.camera2.impl.CameraDeviceImpl.this.mFailedRepeatingRequestId = -1;
                    android.hardware.camera2.impl.CameraDeviceImpl.this.mFailedRepeatingRequestTypes = null;
                }
            }
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onDeviceIdle() {
            android.hardware.camera2.impl.CameraDeviceImpl.this.onDeviceIdle();
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onCaptureStarted(final android.hardware.camera2.impl.CaptureResultExtras captureResultExtras, final long j) {
            int requestId = captureResultExtras.getRequestId();
            final long frameNumber = captureResultExtras.getFrameNumber();
            long lastCompletedRegularFrameNumber = captureResultExtras.getLastCompletedRegularFrameNumber();
            long lastCompletedReprocessFrameNumber = captureResultExtras.getLastCompletedReprocessFrameNumber();
            long lastCompletedZslFrameNumber = captureResultExtras.getLastCompletedZslFrameNumber();
            final boolean hasReadoutTimestamp = captureResultExtras.hasReadoutTimestamp();
            final long readoutTimestamp = captureResultExtras.getReadoutTimestamp();
            synchronized (android.hardware.camera2.impl.CameraDeviceImpl.this.mInterfaceLock) {
                try {
                } catch (java.lang.Throwable th) {
                    th = th;
                }
                try {
                    if (android.hardware.camera2.impl.CameraDeviceImpl.this.mRemoteDevice == null) {
                        return;
                    }
                    if (android.hardware.camera2.impl.CameraDeviceImpl.this.mOfflineSessionImpl == null) {
                        android.hardware.camera2.impl.CameraDeviceImpl.this.removeCompletedCallbackHolderLocked(lastCompletedRegularFrameNumber, lastCompletedReprocessFrameNumber, lastCompletedZslFrameNumber);
                        final android.hardware.camera2.impl.CaptureCallbackHolder captureCallbackHolder = (android.hardware.camera2.impl.CaptureCallbackHolder) android.hardware.camera2.impl.CameraDeviceImpl.this.mCaptureCallbackMap.get(requestId);
                        if (captureCallbackHolder == null) {
                            return;
                        }
                        if (android.hardware.camera2.impl.CameraDeviceImpl.this.isClosed()) {
                            return;
                        }
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                        }
                        try {
                            captureCallbackHolder.getExecutor().execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (!android.hardware.camera2.impl.CameraDeviceImpl.this.isClosed()) {
                                        int subsequenceId = captureResultExtras.getSubsequenceId();
                                        android.hardware.camera2.CaptureRequest request = captureCallbackHolder.getRequest(subsequenceId);
                                        if (captureCallbackHolder.hasBatchedOutputs()) {
                                            android.util.Range range = (android.util.Range) request.get(android.hardware.camera2.CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
                                            for (int i = 0; i < captureCallbackHolder.getRequestCount(); i++) {
                                                long j2 = subsequenceId - i;
                                                long j3 = j2 * 1000000000;
                                                captureCallbackHolder.getCallback().onCaptureStarted(android.hardware.camera2.impl.CameraDeviceImpl.this, captureCallbackHolder.getRequest(i), j - (j3 / ((java.lang.Integer) range.getUpper()).intValue()), frameNumber - j2);
                                                if (hasReadoutTimestamp) {
                                                    captureCallbackHolder.getCallback().onReadoutStarted(android.hardware.camera2.impl.CameraDeviceImpl.this, captureCallbackHolder.getRequest(i), readoutTimestamp - (j3 / ((java.lang.Integer) range.getUpper()).intValue()), frameNumber - j2);
                                                }
                                            }
                                            return;
                                        }
                                        captureCallbackHolder.getCallback().onCaptureStarted(android.hardware.camera2.impl.CameraDeviceImpl.this, request, j, frameNumber);
                                        if (hasReadoutTimestamp) {
                                            captureCallbackHolder.getCallback().onReadoutStarted(android.hardware.camera2.impl.CameraDeviceImpl.this, request, readoutTimestamp, frameNumber);
                                        }
                                    }
                                }
                            });
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            return;
                        } catch (java.lang.Throwable th3) {
                            th = th3;
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    android.hardware.camera2.impl.CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onCaptureStarted(captureResultExtras, j);
                } catch (java.lang.Throwable th4) {
                    th = th4;
                    throw th;
                }
            }
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onResultReceived(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, final android.hardware.camera2.impl.CaptureResultExtras captureResultExtras, android.hardware.camera2.impl.PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) throws android.os.RemoteException {
            java.lang.Object obj;
            android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative2;
            android.hardware.camera2.impl.CaptureCallbackHolder captureCallbackHolder;
            android.hardware.camera2.CaptureResult captureResult;
            java.lang.Runnable runnable;
            int requestId = captureResultExtras.getRequestId();
            long frameNumber = captureResultExtras.getFrameNumber();
            java.lang.Object obj2 = android.hardware.camera2.impl.CameraDeviceImpl.this.mInterfaceLock;
            synchronized (obj2) {
                try {
                    try {
                        if (android.hardware.camera2.impl.CameraDeviceImpl.this.mRemoteDevice == null) {
                            return;
                        }
                        if (android.hardware.camera2.impl.CameraDeviceImpl.this.mOfflineSessionImpl != null) {
                            android.hardware.camera2.impl.CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onResultReceived(cameraMetadataNative, captureResultExtras, physicalCaptureResultInfoArr);
                            return;
                        }
                        cameraMetadataNative.set((android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.CameraCharacteristics.Key<android.util.Size>>) android.hardware.camera2.CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE, (android.hardware.camera2.CameraCharacteristics.Key<android.util.Size>) android.hardware.camera2.impl.CameraDeviceImpl.this.getCharacteristics().get(android.hardware.camera2.CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE));
                        final android.hardware.camera2.impl.CaptureCallbackHolder captureCallbackHolder2 = (android.hardware.camera2.impl.CaptureCallbackHolder) android.hardware.camera2.impl.CameraDeviceImpl.this.mCaptureCallbackMap.get(requestId);
                        boolean z = captureResultExtras.getPartialResultCount() < android.hardware.camera2.impl.CameraDeviceImpl.this.mTotalPartialCount;
                        if (captureCallbackHolder2 == null) {
                            return;
                        }
                        final android.hardware.camera2.CaptureRequest request = captureCallbackHolder2.getRequest(captureResultExtras.getSubsequenceId());
                        int requestType = request.getRequestType();
                        if (android.hardware.camera2.impl.CameraDeviceImpl.this.isClosed()) {
                            android.hardware.camera2.impl.CameraDeviceImpl.this.updateTracker(requestId, frameNumber, requestType, null, z);
                            return;
                        }
                        if (captureCallbackHolder2.hasBatchedOutputs()) {
                            cameraMetadataNative2 = new android.hardware.camera2.impl.CameraMetadataNative(cameraMetadataNative);
                        } else {
                            cameraMetadataNative2 = null;
                        }
                        if (z) {
                            final android.hardware.camera2.CaptureResult captureResult2 = new android.hardware.camera2.CaptureResult(android.hardware.camera2.impl.CameraDeviceImpl.this.getId(), cameraMetadataNative, request, captureResultExtras);
                            final android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative3 = cameraMetadataNative2;
                            captureResult = captureResult2;
                            obj = obj2;
                            runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (!android.hardware.camera2.impl.CameraDeviceImpl.this.isClosed()) {
                                        if (captureCallbackHolder2.hasBatchedOutputs()) {
                                            for (int i = 0; i < captureCallbackHolder2.getRequestCount(); i++) {
                                                captureCallbackHolder2.getCallback().onCaptureProgressed(android.hardware.camera2.impl.CameraDeviceImpl.this, captureCallbackHolder2.getRequest(i), new android.hardware.camera2.CaptureResult(android.hardware.camera2.impl.CameraDeviceImpl.this.getId(), new android.hardware.camera2.impl.CameraMetadataNative(cameraMetadataNative3), captureCallbackHolder2.getRequest(i), captureResultExtras));
                                            }
                                            return;
                                        }
                                        captureCallbackHolder2.getCallback().onCaptureProgressed(android.hardware.camera2.impl.CameraDeviceImpl.this, request, captureResult2);
                                    }
                                }
                            };
                            captureCallbackHolder = captureCallbackHolder2;
                        } else {
                            final java.util.List<android.hardware.camera2.CaptureResult> popPartialResults = android.hardware.camera2.impl.CameraDeviceImpl.this.mFrameNumberTracker.popPartialResults(frameNumber);
                            if (android.hardware.camera2.impl.CameraDeviceImpl.this.mBatchOutputMap.containsKey(java.lang.Integer.valueOf(requestId))) {
                                int intValue = ((java.lang.Integer) android.hardware.camera2.impl.CameraDeviceImpl.this.mBatchOutputMap.get(java.lang.Integer.valueOf(requestId))).intValue();
                                for (int i = 1; i < intValue; i++) {
                                    android.hardware.camera2.impl.CameraDeviceImpl.this.mFrameNumberTracker.popPartialResults(frameNumber - (intValue - i));
                                }
                            }
                            final long longValue = ((java.lang.Long) cameraMetadataNative.get(android.hardware.camera2.CaptureResult.SENSOR_TIMESTAMP)).longValue();
                            final android.util.Range range = (android.util.Range) request.get(android.hardware.camera2.CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
                            final int subsequenceId = captureResultExtras.getSubsequenceId();
                            final android.hardware.camera2.TotalCaptureResult totalCaptureResult = new android.hardware.camera2.TotalCaptureResult(android.hardware.camera2.impl.CameraDeviceImpl.this.getId(), cameraMetadataNative, request, captureResultExtras, popPartialResults, captureCallbackHolder2.getSessionId(), physicalCaptureResultInfoArr);
                            final android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative4 = cameraMetadataNative2;
                            captureCallbackHolder = captureCallbackHolder2;
                            obj = obj2;
                            captureResult = totalCaptureResult;
                            runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks.3
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (!android.hardware.camera2.impl.CameraDeviceImpl.this.isClosed()) {
                                        if (captureCallbackHolder2.hasBatchedOutputs()) {
                                            for (int i2 = 0; i2 < captureCallbackHolder2.getRequestCount(); i2++) {
                                                cameraMetadataNative4.set((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key<java.lang.Long>>) android.hardware.camera2.CaptureResult.SENSOR_TIMESTAMP, (android.hardware.camera2.CaptureResult.Key<java.lang.Long>) java.lang.Long.valueOf(longValue - (((subsequenceId - i2) * 1000000000) / ((java.lang.Integer) range.getUpper()).intValue())));
                                                captureCallbackHolder2.getCallback().onCaptureCompleted(android.hardware.camera2.impl.CameraDeviceImpl.this, captureCallbackHolder2.getRequest(i2), new android.hardware.camera2.TotalCaptureResult(android.hardware.camera2.impl.CameraDeviceImpl.this.getId(), new android.hardware.camera2.impl.CameraMetadataNative(cameraMetadataNative4), captureCallbackHolder2.getRequest(i2), captureResultExtras, popPartialResults, captureCallbackHolder2.getSessionId(), new android.hardware.camera2.impl.PhysicalCaptureResultInfo[0]));
                                            }
                                            return;
                                        }
                                        captureCallbackHolder2.getCallback().onCaptureCompleted(android.hardware.camera2.impl.CameraDeviceImpl.this, request, totalCaptureResult);
                                    }
                                }
                            };
                        }
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            captureCallbackHolder.getExecutor().execute(runnable);
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            android.hardware.camera2.impl.CameraDeviceImpl.this.updateTracker(requestId, frameNumber, requestType, captureResult, z);
                            if (!z) {
                                android.hardware.camera2.impl.CameraDeviceImpl.this.checkAndFireSequenceComplete();
                            }
                        } catch (java.lang.Throwable th) {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        obj = obj2;
                        throw th;
                    }
                } catch (java.lang.Throwable th3) {
                    th = th3;
                    throw th;
                }
            }
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onPrepared(int i) {
            synchronized (android.hardware.camera2.impl.CameraDeviceImpl.this.mInterfaceLock) {
                if (android.hardware.camera2.impl.CameraDeviceImpl.this.mOfflineSessionImpl != null) {
                    android.hardware.camera2.impl.CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onPrepared(i);
                    return;
                }
                android.hardware.camera2.params.OutputConfiguration outputConfiguration = (android.hardware.camera2.params.OutputConfiguration) android.hardware.camera2.impl.CameraDeviceImpl.this.mConfiguredOutputs.get(i);
                android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK stateCallbackKK = android.hardware.camera2.impl.CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK == null) {
                    return;
                }
                if (outputConfiguration == null) {
                    android.util.Log.w(android.hardware.camera2.impl.CameraDeviceImpl.this.TAG, "onPrepared invoked for unknown output Surface");
                    return;
                }
                java.util.Iterator<android.view.Surface> it = outputConfiguration.getSurfaces().iterator();
                while (it.hasNext()) {
                    stateCallbackKK.onSurfacePrepared(it.next());
                }
            }
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onRequestQueueEmpty() {
            synchronized (android.hardware.camera2.impl.CameraDeviceImpl.this.mInterfaceLock) {
                if (android.hardware.camera2.impl.CameraDeviceImpl.this.mOfflineSessionImpl != null) {
                    android.hardware.camera2.impl.CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onRequestQueueEmpty();
                    return;
                }
                android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK stateCallbackKK = android.hardware.camera2.impl.CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK == null) {
                    return;
                }
                stateCallbackKK.onRequestQueueEmpty();
            }
        }
    }

    private static class CameraHandlerExecutor implements java.util.concurrent.Executor {
        private final android.os.Handler mHandler;

        public CameraHandlerExecutor(android.os.Handler handler) {
            this.mHandler = (android.os.Handler) java.util.Objects.requireNonNull(handler);
        }

        @Override // java.util.concurrent.Executor
        public void execute(java.lang.Runnable runnable) {
            this.mHandler.post(runnable);
        }
    }

    static java.util.concurrent.Executor checkExecutor(java.util.concurrent.Executor executor) {
        return executor == null ? checkAndWrapHandler(null) : executor;
    }

    public static <T> java.util.concurrent.Executor checkExecutor(java.util.concurrent.Executor executor, T t) {
        return t != null ? checkExecutor(executor) : executor;
    }

    public static java.util.concurrent.Executor checkAndWrapHandler(android.os.Handler handler) {
        return new android.hardware.camera2.impl.CameraDeviceImpl.CameraHandlerExecutor(checkHandler(handler));
    }

    static android.os.Handler checkHandler(android.os.Handler handler) {
        if (handler == null) {
            android.os.Looper myLooper = android.os.Looper.myLooper();
            if (myLooper == null) {
                throw new java.lang.IllegalArgumentException("No handler given, and current thread has no looper!");
            }
            return new android.os.Handler(myLooper);
        }
        return handler;
    }

    static <T> android.os.Handler checkHandler(android.os.Handler handler, T t) {
        if (t != null) {
            return checkHandler(handler);
        }
        return handler;
    }

    private void checkIfCameraClosedOrInError() throws android.hardware.camera2.CameraAccessException {
        if (this.mRemoteDevice == null) {
            throw new java.lang.IllegalStateException("CameraDevice was already closed");
        }
        if (this.mInError) {
            throw new android.hardware.camera2.CameraAccessException(3, "The camera device has encountered a serious error");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isClosed() {
        return this.mClosing.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.CameraCharacteristics getCharacteristics() {
        return this.mCharacteristics;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        android.util.Log.w(this.TAG, "CameraDevice " + this.mCameraId + " died unexpectedly");
        if (this.mRemoteDevice == null) {
            return;
        }
        this.mInError = true;
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.14
            @Override // java.lang.Runnable
            public void run() {
                if (!android.hardware.camera2.impl.CameraDeviceImpl.this.isClosed()) {
                    android.hardware.camera2.impl.CameraDeviceImpl.this.mDeviceCallback.onError(android.hardware.camera2.impl.CameraDeviceImpl.this, 5);
                }
            }
        };
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mDeviceExecutor.execute(runnable);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public void setCameraAudioRestriction(int i) throws android.hardware.camera2.CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            this.mRemoteDevice.setCameraAudioRestriction(i);
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public int getCameraAudioRestriction() throws android.hardware.camera2.CameraAccessException {
        int globalAudioRestriction;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            globalAudioRestriction = this.mRemoteDevice.getGlobalAudioRestriction();
        }
        return globalAudioRestriction;
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createExtensionSession(android.hardware.camera2.params.ExtensionSessionConfiguration extensionSessionConfiguration) throws android.hardware.camera2.CameraAccessException {
        java.util.HashMap hashMap = new java.util.HashMap(this.mPhysicalIdsToChars);
        hashMap.put(this.mCameraId, this.mCharacteristics);
        java.lang.StringBuilder append = new java.lang.StringBuilder().append(this.TAG).append(" : ");
        int i = this.mNextSessionId;
        this.mNextSessionId = i + 1;
        android.os.Binder binder = new android.os.Binder(append.append(i).toString());
        try {
            try {
                if (!android.hardware.camera2.CameraExtensionCharacteristics.registerClient(this.mContext, binder, extensionSessionConfiguration.getExtension(), this.mCameraId, android.hardware.camera2.impl.CameraExtensionUtils.getCharacteristicsMapNative(hashMap))) {
                    throw new java.lang.UnsupportedOperationException("Unsupported extension!");
                }
                if (android.hardware.camera2.CameraExtensionCharacteristics.areAdvancedExtensionsSupported(extensionSessionConfiguration.getExtension())) {
                    this.mCurrentAdvancedExtensionSession = android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.createCameraAdvancedExtensionSession(this, hashMap, this.mContext, extensionSessionConfiguration, this.mNextSessionId, binder);
                } else {
                    this.mCurrentExtensionSession = android.hardware.camera2.impl.CameraExtensionSessionImpl.createCameraExtensionSession(this, hashMap, this.mContext, extensionSessionConfiguration, this.mNextSessionId, binder);
                }
            } catch (android.os.RemoteException e) {
                throw new android.hardware.camera2.CameraAccessException(3);
            }
        } catch (java.lang.Throwable th) {
            if (binder != null) {
                android.hardware.camera2.CameraExtensionCharacteristics.unregisterClient(this.mContext, binder, extensionSessionConfiguration.getExtension());
            }
            throw th;
        }
    }
}
