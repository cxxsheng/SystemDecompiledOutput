package android.hardware.camera2;

/* loaded from: classes.dex */
public final class CameraManager {
    private static final int API_VERSION_1 = 1;
    private static final int API_VERSION_2 = 2;
    private static final java.lang.String CAMERA_OPEN_CLOSE_LISTENER_PERMISSION = "android.permission.CAMERA_OPEN_CLOSE_LISTENER";
    private static final int CAMERA_TYPE_ALL = 1;
    private static final int CAMERA_TYPE_BACKWARD_COMPATIBLE = 0;
    private static final long ENABLE_PHYSICAL_CAMERA_CALLBACK_FOR_UNAVAILABLE_LOGICAL_CAMERA = 244358506;
    public static final java.lang.String LANDSCAPE_TO_PORTRAIT_PROP = "camera.enable_landscape_to_portrait";
    public static final long OVERRIDE_CAMERA_LANDSCAPE_TO_PORTRAIT = 250678880;
    private static final java.lang.String TAG = "CameraManager";
    private static final int USE_CALLING_UID = -1;
    private final android.content.Context mContext;
    private java.util.ArrayList<java.lang.String> mDeviceIdList;
    private final boolean mHasOpenCloseListenerPermission;
    private final boolean DEBUG = false;
    private final java.lang.Object mLock = new java.lang.Object();

    public interface DeviceStateListener {
        void onDeviceStateChanged(boolean z);
    }

    public CameraManager(android.content.Context context) {
        synchronized (this.mLock) {
            this.mContext = context;
            this.mHasOpenCloseListenerPermission = this.mContext.checkSelfPermission("android.permission.CAMERA_OPEN_CLOSE_LISTENER") == 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class FoldStateListener implements android.hardware.devicestate.DeviceStateManager.DeviceStateCallback {
        private java.util.ArrayList<java.lang.ref.WeakReference<android.hardware.camera2.CameraManager.DeviceStateListener>> mDeviceStateListeners = new java.util.ArrayList<>();
        private boolean mFoldedDeviceState;
        private final int[] mFoldedDeviceStates;

        public FoldStateListener(android.content.Context context) {
            this.mFoldedDeviceStates = context.getResources().getIntArray(com.android.internal.R.array.config_foldedDeviceStates);
        }

        private synchronized void handleStateChange(int i) {
            boolean contains = com.android.internal.util.ArrayUtils.contains(this.mFoldedDeviceStates, i);
            this.mFoldedDeviceState = contains;
            java.util.Iterator<java.lang.ref.WeakReference<android.hardware.camera2.CameraManager.DeviceStateListener>> it = this.mDeviceStateListeners.iterator();
            while (it.hasNext()) {
                android.hardware.camera2.CameraManager.DeviceStateListener deviceStateListener = it.next().get();
                if (deviceStateListener != null) {
                    deviceStateListener.onDeviceStateChanged(contains);
                } else {
                    it.remove();
                }
            }
        }

        public synchronized void addDeviceStateListener(android.hardware.camera2.CameraManager.DeviceStateListener deviceStateListener) {
            deviceStateListener.onDeviceStateChanged(this.mFoldedDeviceState);
            this.mDeviceStateListeners.removeIf(new java.util.function.Predicate() { // from class: android.hardware.camera2.CameraManager$FoldStateListener$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return android.hardware.camera2.CameraManager.FoldStateListener.lambda$addDeviceStateListener$0((java.lang.ref.WeakReference) obj);
                }
            });
            this.mDeviceStateListeners.add(new java.lang.ref.WeakReference<>(deviceStateListener));
        }

        static /* synthetic */ boolean lambda$addDeviceStateListener$0(java.lang.ref.WeakReference weakReference) {
            return weakReference.get() == null;
        }

        @Override // android.hardware.devicestate.DeviceStateManager.DeviceStateCallback
        public final void onBaseStateChanged(int i) {
            handleStateChange(i);
        }

        @Override // android.hardware.devicestate.DeviceStateManager.DeviceStateCallback
        public final void onStateChanged(int i) {
            handleStateChange(i);
        }
    }

    public void registerDeviceStateListener(android.hardware.camera2.CameraCharacteristics cameraCharacteristics) {
        android.hardware.camera2.CameraManager.CameraManagerGlobal.get().registerDeviceStateListener(cameraCharacteristics, this.mContext);
    }

    public java.lang.String[] getCameraIdList() throws android.hardware.camera2.CameraAccessException {
        return android.hardware.camera2.CameraManager.CameraManagerGlobal.get().getCameraIdList();
    }

    public java.lang.String[] getCameraIdListNoLazy() throws android.hardware.camera2.CameraAccessException {
        return android.hardware.camera2.CameraManager.CameraManagerGlobal.get().getCameraIdListNoLazy();
    }

    public java.util.Set<java.util.Set<java.lang.String>> getConcurrentCameraIds() throws android.hardware.camera2.CameraAccessException {
        return android.hardware.camera2.CameraManager.CameraManagerGlobal.get().getConcurrentCameraIds();
    }

    public boolean isConcurrentSessionConfigurationSupported(java.util.Map<java.lang.String, android.hardware.camera2.params.SessionConfiguration> map) throws android.hardware.camera2.CameraAccessException {
        return android.hardware.camera2.CameraManager.CameraManagerGlobal.get().isConcurrentSessionConfigurationSupported(map, this.mContext.getApplicationInfo().targetSdkVersion);
    }

    public void registerAvailabilityCallback(android.hardware.camera2.CameraManager.AvailabilityCallback availabilityCallback, android.os.Handler handler) {
        android.hardware.camera2.CameraManager.CameraManagerGlobal.get().registerAvailabilityCallback(availabilityCallback, android.hardware.camera2.impl.CameraDeviceImpl.checkAndWrapHandler(handler), this.mHasOpenCloseListenerPermission);
    }

    public void registerAvailabilityCallback(java.util.concurrent.Executor executor, android.hardware.camera2.CameraManager.AvailabilityCallback availabilityCallback) {
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("executor was null");
        }
        android.hardware.camera2.CameraManager.CameraManagerGlobal.get().registerAvailabilityCallback(availabilityCallback, executor, this.mHasOpenCloseListenerPermission);
    }

    public void unregisterAvailabilityCallback(android.hardware.camera2.CameraManager.AvailabilityCallback availabilityCallback) {
        android.hardware.camera2.CameraManager.CameraManagerGlobal.get().unregisterAvailabilityCallback(availabilityCallback);
    }

    public void registerTorchCallback(android.hardware.camera2.CameraManager.TorchCallback torchCallback, android.os.Handler handler) {
        android.hardware.camera2.CameraManager.CameraManagerGlobal.get().registerTorchCallback(torchCallback, android.hardware.camera2.impl.CameraDeviceImpl.checkAndWrapHandler(handler));
    }

    public void registerTorchCallback(java.util.concurrent.Executor executor, android.hardware.camera2.CameraManager.TorchCallback torchCallback) {
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("executor was null");
        }
        android.hardware.camera2.CameraManager.CameraManagerGlobal.get().registerTorchCallback(torchCallback, executor);
    }

    public void unregisterTorchCallback(android.hardware.camera2.CameraManager.TorchCallback torchCallback) {
        android.hardware.camera2.CameraManager.CameraManagerGlobal.get().unregisterTorchCallback(torchCallback);
    }

    private android.util.Size getDisplaySize() {
        android.util.Size size = new android.util.Size(0, 0);
        try {
            android.view.Display display = ((android.hardware.display.DisplayManager) this.mContext.getSystemService(android.content.Context.DISPLAY_SERVICE)).getDisplay(0);
            if (display == null) {
                android.util.Log.e(TAG, "Invalid default display!");
                return size;
            }
            android.graphics.Point point = new android.graphics.Point();
            display.getRealSize(point);
            int i = point.x;
            int i2 = point.y;
            if (i2 > i) {
                i2 = i;
                i = point.y;
            }
            return new android.util.Size(i, i2);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "getDisplaySize Failed. " + e);
            return size;
        }
    }

    private java.util.Map<java.lang.String, android.hardware.camera2.params.StreamConfiguration[]> getPhysicalCameraMultiResolutionConfigs(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.ICameraService iCameraService) throws android.hardware.camera2.CameraAccessException {
        java.util.HashMap hashMap = new java.util.HashMap();
        java.lang.Boolean bool = (java.lang.Boolean) cameraMetadataNative.get(android.hardware.camera2.CameraCharacteristics.SCALER_MULTI_RESOLUTION_STREAM_SUPPORTED);
        if (bool == null || !bool.booleanValue()) {
            return hashMap;
        }
        java.util.Set<java.lang.String> physicalCameraIds = cameraMetadataNative.getPhysicalCameraIds();
        if (physicalCameraIds.size() == 0 && cameraMetadataNative.isUltraHighResolutionSensor()) {
            android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr = (android.hardware.camera2.params.StreamConfiguration[]) cameraMetadataNative.get(android.hardware.camera2.CameraCharacteristics.SCALER_PHYSICAL_CAMERA_MULTI_RESOLUTION_STREAM_CONFIGURATIONS);
            if (streamConfigurationArr != null) {
                hashMap.put(str, streamConfigurationArr);
            }
            return hashMap;
        }
        try {
            for (java.lang.String str2 : physicalCameraIds) {
                android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr2 = (android.hardware.camera2.params.StreamConfiguration[]) iCameraService.getCameraCharacteristics(str2, this.mContext.getApplicationInfo().targetSdkVersion, false).get(android.hardware.camera2.CameraCharacteristics.SCALER_PHYSICAL_CAMERA_MULTI_RESOLUTION_STREAM_CONFIGURATIONS);
                if (streamConfigurationArr2 != null) {
                    hashMap.put(str2, streamConfigurationArr2);
                }
            }
            return hashMap;
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(new android.os.ServiceSpecificException(4, "Camera service is currently unavailable"));
        }
    }

    public android.hardware.camera2.CameraCharacteristics getCameraCharacteristics(java.lang.String str) throws android.hardware.camera2.CameraAccessException {
        return getCameraCharacteristics(str, shouldOverrideToPortrait(this.mContext));
    }

    public android.hardware.camera2.CameraCharacteristics getCameraCharacteristics(java.lang.String str, boolean z) throws android.hardware.camera2.CameraAccessException {
        android.hardware.camera2.CameraCharacteristics cameraCharacteristics;
        if (android.hardware.camera2.CameraManager.CameraManagerGlobal.sCameraServiceDisabled) {
            throw new java.lang.IllegalArgumentException("No cameras available on device");
        }
        synchronized (this.mLock) {
            android.hardware.ICameraService cameraService = android.hardware.camera2.CameraManager.CameraManagerGlobal.get().getCameraService();
            if (cameraService == null) {
                throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable");
            }
            try {
                android.util.Size displaySize = getDisplaySize();
                android.hardware.camera2.impl.CameraMetadataNative cameraCharacteristics2 = cameraService.getCameraCharacteristics(str, this.mContext.getApplicationInfo().targetSdkVersion, z);
                try {
                    cameraCharacteristics2.setCameraId(java.lang.Integer.parseInt(str));
                } catch (java.lang.NumberFormatException e) {
                    android.util.Log.v(TAG, "Failed to parse camera Id " + str + " to integer");
                }
                cameraCharacteristics2.setHasMandatoryConcurrentStreams(android.hardware.camera2.CameraManager.CameraManagerGlobal.get().cameraIdHasConcurrentStreamsLocked(str));
                cameraCharacteristics2.setDisplaySize(displaySize);
                java.util.Map<java.lang.String, android.hardware.camera2.params.StreamConfiguration[]> physicalCameraMultiResolutionConfigs = getPhysicalCameraMultiResolutionConfigs(str, cameraCharacteristics2, cameraService);
                if (physicalCameraMultiResolutionConfigs.size() > 0) {
                    cameraCharacteristics2.setMultiResolutionStreamConfigurationMap(physicalCameraMultiResolutionConfigs);
                }
                cameraCharacteristics = new android.hardware.camera2.CameraCharacteristics(cameraCharacteristics2);
            } catch (android.os.RemoteException e2) {
                throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable", e2);
            } catch (android.os.ServiceSpecificException e3) {
                throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e3);
            }
        }
        registerDeviceStateListener(cameraCharacteristics);
        return cameraCharacteristics;
    }

    public android.hardware.camera2.CameraExtensionCharacteristics getCameraExtensionCharacteristics(java.lang.String str) throws android.hardware.camera2.CameraAccessException {
        android.hardware.camera2.CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(str);
        java.util.Map<java.lang.String, android.hardware.camera2.CameraCharacteristics> physicalIdToCharsMap = getPhysicalIdToCharsMap(cameraCharacteristics);
        physicalIdToCharsMap.put(str, cameraCharacteristics);
        return new android.hardware.camera2.CameraExtensionCharacteristics(this.mContext, str, physicalIdToCharsMap);
    }

    private java.util.Map<java.lang.String, android.hardware.camera2.CameraCharacteristics> getPhysicalIdToCharsMap(android.hardware.camera2.CameraCharacteristics cameraCharacteristics) throws android.hardware.camera2.CameraAccessException {
        java.util.HashMap hashMap = new java.util.HashMap();
        for (java.lang.String str : cameraCharacteristics.getPhysicalCameraIds()) {
            hashMap.put(str, getCameraCharacteristics(str));
        }
        return hashMap;
    }

    public android.hardware.camera2.CameraDevice.CameraDeviceSetup getCameraDeviceSetup(java.lang.String str) throws android.hardware.camera2.CameraAccessException {
        if (!isCameraDeviceSetupSupported(str)) {
            throw new java.lang.UnsupportedOperationException("CameraDeviceSetup is not supported for Camera ID: " + str);
        }
        return getCameraDeviceSetupUnsafe(str);
    }

    private android.hardware.camera2.CameraDevice.CameraDeviceSetup getCameraDeviceSetupUnsafe(java.lang.String str) {
        return new android.hardware.camera2.impl.CameraDeviceSetupImpl(str, this, this.mContext);
    }

    public boolean isCameraDeviceSetupSupported(java.lang.String str) throws android.hardware.camera2.CameraAccessException {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("Camera ID was null");
        }
        if (android.hardware.camera2.CameraManager.CameraManagerGlobal.sCameraServiceDisabled || !java.util.Arrays.asList(android.hardware.camera2.CameraManager.CameraManagerGlobal.get().getCameraIdList()).contains(str)) {
            throw new java.lang.IllegalArgumentException("Camera ID '" + str + "' not available on device.");
        }
        return android.hardware.camera2.impl.CameraDeviceSetupImpl.isCameraDeviceSetupSupported(getCameraCharacteristics(str));
    }

    private android.hardware.camera2.CameraDevice openCameraDeviceUserAsync(java.lang.String str, android.hardware.camera2.CameraDevice.StateCallback stateCallback, java.util.concurrent.Executor executor, int i, int i2, boolean z) throws android.hardware.camera2.CameraAccessException {
        android.hardware.camera2.CameraDevice.CameraDeviceSetup cameraDeviceSetup;
        android.hardware.camera2.impl.CameraDeviceImpl cameraDeviceImpl;
        android.hardware.ICameraService cameraService;
        android.hardware.camera2.CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(str);
        java.util.Map<java.lang.String, android.hardware.camera2.CameraCharacteristics> physicalIdToCharsMap = getPhysicalIdToCharsMap(cameraCharacteristics);
        synchronized (this.mLock) {
            android.hardware.camera2.ICameraDeviceUser iCameraDeviceUser = null;
            if (com.android.internal.camera.flags.Flags.cameraDeviceSetup() && android.hardware.camera2.impl.CameraDeviceSetupImpl.isCameraDeviceSetupSupported(cameraCharacteristics)) {
                cameraDeviceSetup = getCameraDeviceSetupUnsafe(str);
            } else {
                cameraDeviceSetup = null;
            }
            cameraDeviceImpl = new android.hardware.camera2.impl.CameraDeviceImpl(str, stateCallback, executor, cameraCharacteristics, physicalIdToCharsMap, this.mContext.getApplicationInfo().targetSdkVersion, this.mContext, cameraDeviceSetup);
            android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks callbacks = cameraDeviceImpl.getCallbacks();
            try {
                cameraService = android.hardware.camera2.CameraManager.CameraManagerGlobal.get().getCameraService();
            } catch (android.os.RemoteException e) {
                android.os.ServiceSpecificException serviceSpecificException = new android.os.ServiceSpecificException(4, "Camera service is currently unavailable");
                cameraDeviceImpl.setRemoteFailure(serviceSpecificException);
                throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(serviceSpecificException);
            } catch (android.os.ServiceSpecificException e2) {
                if (e2.errorCode == 9) {
                    throw new java.lang.AssertionError("Should've gone down the shim path");
                }
                if (e2.errorCode != 7 && e2.errorCode != 8 && e2.errorCode != 6 && e2.errorCode != 4 && e2.errorCode != 10) {
                    throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
                }
                cameraDeviceImpl.setRemoteFailure(e2);
                if (e2.errorCode == 6 || e2.errorCode == 4 || e2.errorCode == 7) {
                    throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
                }
            }
            if (cameraService == null) {
                throw new android.os.ServiceSpecificException(4, "Camera service is currently unavailable");
            }
            iCameraDeviceUser = cameraService.connectDevice(callbacks, str, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), i, i2, this.mContext.getApplicationInfo().targetSdkVersion, z);
            cameraDeviceImpl.setRemoteDevice(iCameraDeviceUser);
        }
        return cameraDeviceImpl;
    }

    public void openCamera(java.lang.String str, android.hardware.camera2.CameraDevice.StateCallback stateCallback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException {
        openCameraForUid(str, stateCallback, android.hardware.camera2.impl.CameraDeviceImpl.checkAndWrapHandler(handler), -1);
    }

    public void openCamera(java.lang.String str, boolean z, android.os.Handler handler, android.hardware.camera2.CameraDevice.StateCallback stateCallback) throws android.hardware.camera2.CameraAccessException {
        openCameraForUid(str, stateCallback, android.hardware.camera2.impl.CameraDeviceImpl.checkAndWrapHandler(handler), -1, 0, z);
    }

    public void openCamera(java.lang.String str, java.util.concurrent.Executor executor, android.hardware.camera2.CameraDevice.StateCallback stateCallback) throws android.hardware.camera2.CameraAccessException {
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("executor was null");
        }
        openCameraForUid(str, stateCallback, executor, -1);
    }

    @android.annotation.SystemApi
    public void openCamera(java.lang.String str, int i, java.util.concurrent.Executor executor, android.hardware.camera2.CameraDevice.StateCallback stateCallback) throws android.hardware.camera2.CameraAccessException {
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("executor was null");
        }
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("oomScoreOffset < 0, cannot increase priority of camera client");
        }
        openCameraForUid(str, stateCallback, executor, -1, i, shouldOverrideToPortrait(this.mContext));
    }

    public void openCameraForUid(java.lang.String str, android.hardware.camera2.CameraDevice.StateCallback stateCallback, java.util.concurrent.Executor executor, int i, int i2, boolean z) throws android.hardware.camera2.CameraAccessException {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("cameraId was null");
        }
        if (stateCallback == null) {
            throw new java.lang.IllegalArgumentException("callback was null");
        }
        if (android.hardware.camera2.CameraManager.CameraManagerGlobal.sCameraServiceDisabled) {
            throw new java.lang.IllegalArgumentException("No cameras available on device");
        }
        openCameraDeviceUserAsync(str, stateCallback, executor, i, i2, z);
    }

    public void openCameraForUid(java.lang.String str, android.hardware.camera2.CameraDevice.StateCallback stateCallback, java.util.concurrent.Executor executor, int i) throws android.hardware.camera2.CameraAccessException {
        openCameraForUid(str, stateCallback, executor, i, 0, shouldOverrideToPortrait(this.mContext));
    }

    public void setTorchMode(java.lang.String str, boolean z) throws android.hardware.camera2.CameraAccessException {
        if (android.hardware.camera2.CameraManager.CameraManagerGlobal.sCameraServiceDisabled) {
            throw new java.lang.IllegalArgumentException("No cameras available on device");
        }
        android.hardware.camera2.CameraManager.CameraManagerGlobal.get().setTorchMode(str, z);
    }

    public void turnOnTorchWithStrengthLevel(java.lang.String str, int i) throws android.hardware.camera2.CameraAccessException {
        if (android.hardware.camera2.CameraManager.CameraManagerGlobal.sCameraServiceDisabled) {
            throw new java.lang.IllegalArgumentException("No camera available on device");
        }
        android.hardware.camera2.CameraManager.CameraManagerGlobal.get().turnOnTorchWithStrengthLevel(str, i);
    }

    public int getTorchStrengthLevel(java.lang.String str) throws android.hardware.camera2.CameraAccessException {
        if (android.hardware.camera2.CameraManager.CameraManagerGlobal.sCameraServiceDisabled) {
            throw new java.lang.IllegalArgumentException("No camera available on device.");
        }
        return android.hardware.camera2.CameraManager.CameraManagerGlobal.get().getTorchStrengthLevel(str);
    }

    public static boolean shouldOverrideToPortrait(android.content.Context context) {
        android.content.pm.PackageManager packageManager;
        java.lang.String str;
        if (context == null) {
            packageManager = null;
            str = null;
        } else {
            packageManager = context.getPackageManager();
            str = context.getOpPackageName();
        }
        return shouldOverrideToPortrait(packageManager, str);
    }

    public static boolean shouldOverrideToPortrait(android.content.pm.PackageManager packageManager, java.lang.String str) {
        if (!android.hardware.camera2.CameraManager.CameraManagerGlobal.sLandscapeToPortrait) {
            return false;
        }
        if (packageManager != null && str != null) {
            try {
                return packageManager.getProperty(android.content.pm.PackageManager.PROPERTY_COMPAT_OVERRIDE_LANDSCAPE_TO_PORTRAIT, str).getBoolean();
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        }
        return android.app.compat.CompatChanges.isChangeEnabled(OVERRIDE_CAMERA_LANDSCAPE_TO_PORTRAIT);
    }

    public static boolean physicalCallbacksAreEnabledForUnavailableCamera() {
        return android.app.compat.CompatChanges.isChangeEnabled(ENABLE_PHYSICAL_CAMERA_CALLBACK_FOR_UNAVAILABLE_LOGICAL_CAMERA);
    }

    public static abstract class AvailabilityCallback {
        public void onCameraAvailable(java.lang.String str) {
        }

        public void onCameraUnavailable(java.lang.String str) {
        }

        public void onCameraAccessPrioritiesChanged() {
        }

        public void onPhysicalCameraAvailable(java.lang.String str, java.lang.String str2) {
        }

        public void onPhysicalCameraUnavailable(java.lang.String str, java.lang.String str2) {
        }

        @android.annotation.SystemApi
        public void onCameraOpened(java.lang.String str, java.lang.String str2) {
        }

        @android.annotation.SystemApi
        public void onCameraClosed(java.lang.String str) {
        }
    }

    public static abstract class TorchCallback {
        public void onTorchModeUnavailable(java.lang.String str) {
        }

        public void onTorchModeChanged(java.lang.String str, boolean z) {
        }

        public void onTorchStrengthLevelChanged(java.lang.String str, int i) {
        }
    }

    public static boolean isHiddenPhysicalCamera(java.lang.String str) {
        try {
            android.hardware.ICameraService cameraService = android.hardware.camera2.CameraManager.CameraManagerGlobal.get().getCameraService();
            if (cameraService == null) {
                return false;
            }
            return cameraService.isHiddenPhysicalCamera(str);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public void injectCamera(java.lang.String str, java.lang.String str2, java.lang.String str3, java.util.concurrent.Executor executor, android.hardware.camera2.CameraInjectionSession.InjectionStatusCallback injectionStatusCallback) throws android.hardware.camera2.CameraAccessException, java.lang.SecurityException, java.lang.IllegalArgumentException {
        if (android.hardware.camera2.CameraManager.CameraManagerGlobal.sCameraServiceDisabled) {
            throw new java.lang.IllegalArgumentException("No cameras available on device");
        }
        android.hardware.ICameraService cameraService = android.hardware.camera2.CameraManager.CameraManagerGlobal.get().getCameraService();
        if (cameraService == null) {
            throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable");
        }
        synchronized (this.mLock) {
            try {
                try {
                    android.hardware.camera2.impl.CameraInjectionSessionImpl cameraInjectionSessionImpl = new android.hardware.camera2.impl.CameraInjectionSessionImpl(injectionStatusCallback, executor);
                    cameraInjectionSessionImpl.setRemoteInjectionSession(cameraService.injectCamera(str, str2, str3, cameraInjectionSessionImpl.getCallback()));
                } catch (android.os.RemoteException e) {
                    throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(new android.os.ServiceSpecificException(4, "Camera service is currently unavailable"));
                } catch (android.os.ServiceSpecificException e2) {
                    throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void remapCameraIds(android.hardware.CameraIdRemapping cameraIdRemapping) throws android.hardware.camera2.CameraAccessException, java.lang.SecurityException, java.lang.IllegalArgumentException {
        android.hardware.camera2.CameraManager.CameraManagerGlobal.get().remapCameraIds(cameraIdRemapping);
    }

    public void injectSessionParams(java.lang.String str, android.hardware.camera2.CaptureRequest captureRequest) throws android.hardware.camera2.CameraAccessException, java.lang.SecurityException {
        android.hardware.camera2.CameraManager.CameraManagerGlobal.get().injectSessionParams(str, captureRequest);
    }

    public android.hardware.ICameraService getCameraService() {
        return android.hardware.camera2.CameraManager.CameraManagerGlobal.get().getCameraService();
    }

    public boolean isCameraServiceDisabled() {
        return android.hardware.camera2.CameraManager.CameraManagerGlobal.sCameraServiceDisabled;
    }

    public static java.lang.String reportExtensionSessionStats(android.hardware.CameraExtensionSessionStats cameraExtensionSessionStats) {
        android.hardware.ICameraService cameraService = android.hardware.camera2.CameraManager.CameraManagerGlobal.get().getCameraService();
        if (cameraService == null) {
            android.util.Log.e(TAG, "CameraService not available. Not reporting extension stats.");
            return "";
        }
        try {
            return cameraService.reportExtensionSessionStats(cameraExtensionSessionStats);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to report extension session stats to cameraservice.", e);
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CameraManagerGlobal extends android.hardware.ICameraServiceListener.Stub implements android.os.IBinder.DeathRecipient {
        private static final java.lang.String CAMERA_SERVICE_BINDER_NAME = "media.camera";
        private static final java.lang.String TAG = "CameraManagerGlobal";
        private static final android.hardware.camera2.CameraManager.CameraManagerGlobal gCameraManager = new android.hardware.camera2.CameraManager.CameraManagerGlobal();
        public static final boolean sCameraServiceDisabled = android.os.SystemProperties.getBoolean("config.disable_cameraservice", false);
        public static final boolean sLandscapeToPortrait = android.os.SystemProperties.getBoolean(android.hardware.camera2.CameraManager.LANDSCAPE_TO_PORTRAIT_PROP, false);
        private android.hardware.CameraIdRemapping mActiveCameraIdRemapping;
        private android.hardware.ICameraService mCameraService;
        private android.os.Handler mDeviceStateHandler;
        private android.os.HandlerThread mDeviceStateHandlerThread;
        private android.hardware.camera2.CameraManager.FoldStateListener mFoldStateListener;
        private final boolean DEBUG = false;
        private final int CAMERA_SERVICE_RECONNECT_DELAY_MS = 1000;
        private final java.util.concurrent.ScheduledExecutorService mScheduler = java.util.concurrent.Executors.newScheduledThreadPool(1);
        private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mDeviceStatus = new android.util.ArrayMap<>();
        private final android.util.ArrayMap<java.lang.String, java.util.ArrayList<java.lang.String>> mUnavailablePhysicalDevices = new android.util.ArrayMap<>();
        private final android.util.ArrayMap<java.lang.String, java.lang.String> mOpenedDevices = new android.util.ArrayMap<>();
        private final java.util.Set<java.util.Set<java.lang.String>> mConcurrentCameraIdCombinations = new android.util.ArraySet();
        private final android.util.ArrayMap<android.hardware.camera2.CameraManager.AvailabilityCallback, java.util.concurrent.Executor> mCallbackMap = new android.util.ArrayMap<>();
        private android.os.Binder mTorchClientBinder = new android.os.Binder();
        private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mTorchStatus = new android.util.ArrayMap<>();
        private final android.util.ArrayMap<android.hardware.camera2.CameraManager.TorchCallback, java.util.concurrent.Executor> mTorchCallbackMap = new android.util.ArrayMap<>();
        private final java.lang.Object mLock = new java.lang.Object();
        private boolean mHasOpenCloseListenerPermission = false;

        private CameraManagerGlobal() {
        }

        public static android.hardware.camera2.CameraManager.CameraManagerGlobal get() {
            return gCameraManager;
        }

        public void registerDeviceStateListener(android.hardware.camera2.CameraCharacteristics cameraCharacteristics, android.content.Context context) {
            synchronized (this.mLock) {
                if (this.mDeviceStateHandlerThread == null) {
                    this.mDeviceStateHandlerThread = new android.os.HandlerThread(TAG);
                    this.mDeviceStateHandlerThread.start();
                    this.mDeviceStateHandler = new android.os.Handler(this.mDeviceStateHandlerThread.getLooper());
                }
                if (this.mFoldStateListener == null) {
                    this.mFoldStateListener = new android.hardware.camera2.CameraManager.FoldStateListener(context);
                    try {
                        ((android.hardware.devicestate.DeviceStateManager) context.getSystemService(android.hardware.devicestate.DeviceStateManager.class)).registerCallback(new android.os.HandlerExecutor(this.mDeviceStateHandler), this.mFoldStateListener);
                    } catch (java.lang.IllegalStateException e) {
                        this.mFoldStateListener = null;
                        android.util.Log.v(TAG, "Failed to register device state listener!");
                        android.util.Log.v(TAG, "Device state dependent characteristics updates will not befunctional!");
                        return;
                    }
                }
                this.mFoldStateListener.addDeviceStateListener(cameraCharacteristics.getDeviceStateListener());
            }
        }

        @Override // android.hardware.ICameraServiceListener.Stub, android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public android.hardware.ICameraService getCameraService() {
            android.hardware.ICameraService iCameraService;
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                if (this.mCameraService == null && !sCameraServiceDisabled) {
                    android.util.Log.e(TAG, "Camera service is unavailable");
                }
                iCameraService = this.mCameraService;
            }
            return iCameraService;
        }

        private void connectCameraServiceLocked() {
            if (this.mCameraService != null || sCameraServiceDisabled) {
                return;
            }
            android.util.Log.i(TAG, "Connecting to camera service");
            android.os.IBinder service = android.os.ServiceManager.getService(CAMERA_SERVICE_BINDER_NAME);
            if (service == null) {
                return;
            }
            try {
                service.linkToDeath(this, 0);
                android.hardware.ICameraService asInterface = android.hardware.ICameraService.Stub.asInterface(service);
                try {
                    android.hardware.camera2.impl.CameraMetadataNative.setupGlobalVendorTagDescriptor();
                } catch (android.os.ServiceSpecificException e) {
                    handleRecoverableSetupErrors(e);
                }
                try {
                    for (android.hardware.CameraStatus cameraStatus : asInterface.addListener(this)) {
                        onStatusChangedLocked(cameraStatus.status, cameraStatus.cameraId);
                        if (cameraStatus.unavailablePhysicalCameras != null) {
                            for (java.lang.String str : cameraStatus.unavailablePhysicalCameras) {
                                onPhysicalCameraStatusChangedLocked(0, cameraStatus.cameraId, str);
                            }
                        }
                        if (this.mHasOpenCloseListenerPermission && cameraStatus.status == -2 && !cameraStatus.clientPackage.isEmpty()) {
                            onCameraOpenedLocked(cameraStatus.cameraId, cameraStatus.clientPackage);
                        }
                    }
                    this.mCameraService = asInterface;
                } catch (android.os.RemoteException e2) {
                } catch (android.os.ServiceSpecificException e3) {
                    throw new java.lang.IllegalStateException("Failed to register a camera service listener", e3);
                }
                try {
                    for (android.hardware.camera2.utils.ConcurrentCameraIdCombination concurrentCameraIdCombination : asInterface.getConcurrentCameraIds()) {
                        this.mConcurrentCameraIdCombinations.add(concurrentCameraIdCombination.getConcurrentCameraIdCombination());
                    }
                } catch (android.os.RemoteException e4) {
                } catch (android.os.ServiceSpecificException e5) {
                    throw new java.lang.IllegalStateException("Failed to get concurrent camera id combinations", e5);
                }
                if (this.mActiveCameraIdRemapping != null) {
                    try {
                        asInterface.remapCameraIds(this.mActiveCameraIdRemapping);
                    } catch (android.os.RemoteException e6) {
                    } catch (android.os.ServiceSpecificException e7) {
                        android.util.Log.e(TAG, "Unable to remap camera Ids in the camera service");
                    }
                }
            } catch (android.os.RemoteException e8) {
            }
        }

        public void remapCameraIds(android.hardware.CameraIdRemapping cameraIdRemapping) throws android.hardware.camera2.CameraAccessException, java.lang.SecurityException {
            synchronized (this.mLock) {
                android.hardware.ICameraService cameraService = getCameraService();
                if (cameraService == null) {
                    throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable.");
                }
                try {
                    cameraService.remapCameraIds(cameraIdRemapping);
                    this.mActiveCameraIdRemapping = cameraIdRemapping;
                } catch (android.os.RemoteException e) {
                    throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable.");
                } catch (android.os.ServiceSpecificException e2) {
                    throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
                }
            }
        }

        public void injectSessionParams(java.lang.String str, android.hardware.camera2.CaptureRequest captureRequest) throws android.hardware.camera2.CameraAccessException, java.lang.SecurityException {
            synchronized (this.mLock) {
                android.hardware.ICameraService cameraService = getCameraService();
                if (cameraService == null) {
                    throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable.");
                }
                try {
                    cameraService.injectSessionParams(str, captureRequest.getNativeMetadata());
                } catch (android.os.RemoteException e) {
                    throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable.");
                } catch (android.os.ServiceSpecificException e2) {
                    throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
                }
            }
        }

        private java.lang.String[] extractCameraIdListLocked() {
            boolean shouldExposeAuxCamera = android.hardware.Camera.shouldExposeAuxCamera();
            int i = 0;
            for (int i2 = 0; i2 < this.mDeviceStatus.size() && (shouldExposeAuxCamera || i2 != 2); i2++) {
                int intValue = this.mDeviceStatus.valueAt(i2).intValue();
                if (intValue != 0 && intValue != 2) {
                    i++;
                }
            }
            java.lang.String[] strArr = new java.lang.String[i];
            int i3 = 0;
            for (int i4 = 0; i4 < this.mDeviceStatus.size() && (shouldExposeAuxCamera || i4 != 2); i4++) {
                int intValue2 = this.mDeviceStatus.valueAt(i4).intValue();
                if (intValue2 != 0 && intValue2 != 2) {
                    strArr[i3] = this.mDeviceStatus.keyAt(i4);
                    i3++;
                }
            }
            return strArr;
        }

        private java.util.Set<java.util.Set<java.lang.String>> extractConcurrentCameraIdListLocked() {
            android.util.ArraySet arraySet = new android.util.ArraySet();
            for (java.util.Set<java.lang.String> set : this.mConcurrentCameraIdCombinations) {
                android.util.ArraySet arraySet2 = new android.util.ArraySet();
                for (java.lang.String str : set) {
                    java.lang.Integer num = this.mDeviceStatus.get(str);
                    if (num != null && num.intValue() != 2 && num.intValue() != 0) {
                        arraySet2.add(str);
                    }
                }
                arraySet.add(arraySet2);
            }
            return arraySet;
        }

        private static void sortCameraIds(java.lang.String[] strArr) {
            java.util.Arrays.sort(strArr, new java.util.Comparator<java.lang.String>() { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.1
                @Override // java.util.Comparator
                public int compare(java.lang.String str, java.lang.String str2) {
                    int i;
                    int i2;
                    try {
                        i = java.lang.Integer.parseInt(str);
                    } catch (java.lang.NumberFormatException e) {
                        i = -1;
                    }
                    try {
                        i2 = java.lang.Integer.parseInt(str2);
                    } catch (java.lang.NumberFormatException e2) {
                        i2 = -1;
                    }
                    if (i >= 0 && i2 >= 0) {
                        return i - i2;
                    }
                    if (i >= 0) {
                        return -1;
                    }
                    if (i2 >= 0) {
                        return 1;
                    }
                    return str.compareTo(str2);
                }
            });
        }

        public static boolean cameraStatusesContains(android.hardware.CameraStatus[] cameraStatusArr, java.lang.String str) {
            for (android.hardware.CameraStatus cameraStatus : cameraStatusArr) {
                if (cameraStatus.cameraId.equals(str)) {
                    return true;
                }
            }
            return false;
        }

        public java.lang.String[] getCameraIdListNoLazy() {
            java.lang.String[] extractCameraIdListLocked;
            if (sCameraServiceDisabled) {
                return new java.lang.String[0];
            }
            android.hardware.ICameraServiceListener.Stub stub = new android.hardware.ICameraServiceListener.Stub() { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.2
                @Override // android.hardware.ICameraServiceListener
                public void onStatusChanged(int i, java.lang.String str) throws android.os.RemoteException {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onPhysicalCameraStatusChanged(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onTorchStatusChanged(int i, java.lang.String str) throws android.os.RemoteException {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onTorchStrengthLevelChanged(java.lang.String str, int i) throws android.os.RemoteException {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onCameraAccessPrioritiesChanged() {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onCameraOpened(java.lang.String str, java.lang.String str2) {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onCameraClosed(java.lang.String str) {
                }
            };
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                try {
                    android.hardware.CameraStatus[] addListener = this.mCameraService.addListener(stub);
                    this.mCameraService.removeListener(stub);
                    for (android.hardware.CameraStatus cameraStatus : addListener) {
                        onStatusChangedLocked(cameraStatus.status, cameraStatus.cameraId);
                    }
                    java.util.Set<java.lang.String> keySet = this.mDeviceStatus.keySet();
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    for (java.lang.String str : keySet) {
                        if (!cameraStatusesContains(addListener, str)) {
                            arrayList.add(str);
                        }
                    }
                    java.util.Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        java.lang.String str2 = (java.lang.String) it.next();
                        onStatusChangedLocked(0, str2);
                        this.mTorchStatus.remove(str2);
                    }
                } catch (android.os.RemoteException e) {
                } catch (android.os.ServiceSpecificException e2) {
                    throw new java.lang.IllegalStateException("Failed to register a camera service listener", e2);
                }
                extractCameraIdListLocked = extractCameraIdListLocked();
            }
            sortCameraIds(extractCameraIdListLocked);
            return extractCameraIdListLocked;
        }

        public java.lang.String[] getCameraIdList() {
            java.lang.String[] extractCameraIdListLocked;
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                extractCameraIdListLocked = extractCameraIdListLocked();
            }
            sortCameraIds(extractCameraIdListLocked);
            return extractCameraIdListLocked;
        }

        public java.util.Set<java.util.Set<java.lang.String>> getConcurrentCameraIds() {
            java.util.Set<java.util.Set<java.lang.String>> extractConcurrentCameraIdListLocked;
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                extractConcurrentCameraIdListLocked = extractConcurrentCameraIdListLocked();
            }
            return extractConcurrentCameraIdListLocked;
        }

        public boolean isConcurrentSessionConfigurationSupported(java.util.Map<java.lang.String, android.hardware.camera2.params.SessionConfiguration> map, int i) throws android.hardware.camera2.CameraAccessException {
            if (map == null) {
                throw new java.lang.IllegalArgumentException("cameraIdsAndSessionConfigurations was null");
            }
            int size = map.size();
            if (size == 0) {
                throw new java.lang.IllegalArgumentException("camera id and session combination is empty");
            }
            synchronized (this.mLock) {
                java.util.Iterator<java.util.Set<java.lang.String>> it = this.mConcurrentCameraIdCombinations.iterator();
                int i2 = 0;
                boolean z = false;
                while (it.hasNext()) {
                    if (it.next().containsAll(map.keySet())) {
                        z = true;
                    }
                }
                if (!z) {
                    android.util.Log.v(TAG, "isConcurrentSessionConfigurationSupported called with a subset ofcamera ids not returned by getConcurrentCameraIds");
                    return false;
                }
                android.hardware.camera2.utils.CameraIdAndSessionConfiguration[] cameraIdAndSessionConfigurationArr = new android.hardware.camera2.utils.CameraIdAndSessionConfiguration[size];
                for (java.util.Map.Entry<java.lang.String, android.hardware.camera2.params.SessionConfiguration> entry : map.entrySet()) {
                    cameraIdAndSessionConfigurationArr[i2] = new android.hardware.camera2.utils.CameraIdAndSessionConfiguration(entry.getKey(), entry.getValue());
                    i2++;
                }
                try {
                    return this.mCameraService.isConcurrentSessionConfigurationSupported(cameraIdAndSessionConfigurationArr, i);
                } catch (android.os.RemoteException e) {
                    throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable", e);
                } catch (android.os.ServiceSpecificException e2) {
                    throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
                }
            }
        }

        public boolean cameraIdHasConcurrentStreamsLocked(java.lang.String str) {
            if (!this.mDeviceStatus.containsKey(str)) {
                return false;
            }
            java.util.Iterator<java.util.Set<java.lang.String>> it = this.mConcurrentCameraIdCombinations.iterator();
            while (it.hasNext()) {
                if (it.next().contains(str)) {
                    return true;
                }
            }
            return false;
        }

        public void setTorchMode(java.lang.String str, boolean z) throws android.hardware.camera2.CameraAccessException {
            synchronized (this.mLock) {
                try {
                    if (str == null) {
                        throw new java.lang.IllegalArgumentException("cameraId was null");
                    }
                    android.hardware.ICameraService cameraService = getCameraService();
                    if (cameraService == null) {
                        throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable");
                    }
                    try {
                        cameraService.setTorchMode(str, z, this.mTorchClientBinder);
                    } catch (android.os.RemoteException e) {
                        throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable");
                    } catch (android.os.ServiceSpecificException e2) {
                        throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void turnOnTorchWithStrengthLevel(java.lang.String str, int i) throws android.hardware.camera2.CameraAccessException {
            synchronized (this.mLock) {
                try {
                    if (str == null) {
                        throw new java.lang.IllegalArgumentException("cameraId was null");
                    }
                    android.hardware.ICameraService cameraService = getCameraService();
                    if (cameraService == null) {
                        throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable.");
                    }
                    try {
                        cameraService.turnOnTorchWithStrengthLevel(str, i, this.mTorchClientBinder);
                    } catch (android.os.RemoteException e) {
                        throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable.");
                    } catch (android.os.ServiceSpecificException e2) {
                        throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public int getTorchStrengthLevel(java.lang.String str) throws android.hardware.camera2.CameraAccessException {
            int torchStrengthLevel;
            synchronized (this.mLock) {
                try {
                    if (str == null) {
                        throw new java.lang.IllegalArgumentException("cameraId was null");
                    }
                    android.hardware.ICameraService cameraService = getCameraService();
                    if (cameraService == null) {
                        throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable.");
                    }
                    try {
                        torchStrengthLevel = cameraService.getTorchStrengthLevel(str);
                    } catch (android.os.RemoteException e) {
                        throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable.");
                    } catch (android.os.ServiceSpecificException e2) {
                        throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return torchStrengthLevel;
        }

        private void handleRecoverableSetupErrors(android.os.ServiceSpecificException serviceSpecificException) {
            switch (serviceSpecificException.errorCode) {
                case 4:
                    android.util.Log.w(TAG, serviceSpecificException.getMessage());
                    return;
                default:
                    throw new java.lang.IllegalStateException(serviceSpecificException);
            }
        }

        private boolean isAvailable(int i) {
            switch (i) {
                case 1:
                    return true;
                default:
                    return false;
            }
        }

        private boolean validStatus(int i) {
            switch (i) {
                case -2:
                case 0:
                case 1:
                case 2:
                    return true;
                case -1:
                default:
                    return false;
            }
        }

        private boolean validTorchStatus(int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                    return true;
                default:
                    return false;
            }
        }

        private void postSingleAccessPriorityChangeUpdate(final android.hardware.camera2.CameraManager.AvailabilityCallback availabilityCallback, java.util.concurrent.Executor executor) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.3
                    @Override // java.lang.Runnable
                    public void run() {
                        availabilityCallback.onCameraAccessPrioritiesChanged();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private void postSingleCameraOpenedUpdate(final android.hardware.camera2.CameraManager.AvailabilityCallback availabilityCallback, java.util.concurrent.Executor executor, final java.lang.String str, final java.lang.String str2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.4
                    @Override // java.lang.Runnable
                    public void run() {
                        availabilityCallback.onCameraOpened(str, str2);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private void postSingleCameraClosedUpdate(final android.hardware.camera2.CameraManager.AvailabilityCallback availabilityCallback, java.util.concurrent.Executor executor, final java.lang.String str) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.5
                    @Override // java.lang.Runnable
                    public void run() {
                        availabilityCallback.onCameraClosed(str);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private void postSingleUpdate(final android.hardware.camera2.CameraManager.AvailabilityCallback availabilityCallback, java.util.concurrent.Executor executor, final java.lang.String str, final java.lang.String str2, int i) {
            long clearCallingIdentity;
            if (isAvailable(i)) {
                clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.6
                        @Override // java.lang.Runnable
                        public void run() {
                            if (str2 == null) {
                                availabilityCallback.onCameraAvailable(str);
                            } else {
                                availabilityCallback.onPhysicalCameraAvailable(str, str2);
                            }
                        }
                    });
                } finally {
                }
            } else {
                clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.7
                        @Override // java.lang.Runnable
                        public void run() {
                            if (str2 == null) {
                                availabilityCallback.onCameraUnavailable(str);
                            } else {
                                availabilityCallback.onPhysicalCameraUnavailable(str, str2);
                            }
                        }
                    });
                } finally {
                }
            }
        }

        private void postSingleTorchUpdate(final android.hardware.camera2.CameraManager.TorchCallback torchCallback, java.util.concurrent.Executor executor, final java.lang.String str, final int i) {
            long clearCallingIdentity;
            switch (i) {
                case 1:
                case 2:
                    clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.hardware.camera2.CameraManager.TorchCallback torchCallback2 = android.hardware.camera2.CameraManager.TorchCallback.this;
                                java.lang.String str2 = str;
                                int i2 = i;
                                torchCallback2.onTorchModeChanged(str2, r3 == 2);
                            }
                        });
                        return;
                    } finally {
                    }
                default:
                    clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.hardware.camera2.CameraManager.TorchCallback.this.onTorchModeUnavailable(str);
                            }
                        });
                        return;
                    } finally {
                    }
            }
        }

        private void postSingleTorchStrengthLevelUpdate(final android.hardware.camera2.CameraManager.TorchCallback torchCallback, java.util.concurrent.Executor executor, final java.lang.String str, final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                executor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.CameraManager.TorchCallback.this.onTorchStrengthLevelChanged(str, i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private void updateCallbackLocked(android.hardware.camera2.CameraManager.AvailabilityCallback availabilityCallback, java.util.concurrent.Executor executor) {
            for (int i = 0; i < this.mDeviceStatus.size(); i++) {
                java.lang.String keyAt = this.mDeviceStatus.keyAt(i);
                java.lang.Integer valueAt = this.mDeviceStatus.valueAt(i);
                postSingleUpdate(availabilityCallback, executor, keyAt, null, valueAt.intValue());
                if ((isAvailable(valueAt.intValue()) || android.hardware.camera2.CameraManager.physicalCallbacksAreEnabledForUnavailableCamera()) && this.mUnavailablePhysicalDevices.containsKey(keyAt)) {
                    java.util.Iterator<java.lang.String> it = this.mUnavailablePhysicalDevices.get(keyAt).iterator();
                    while (it.hasNext()) {
                        postSingleUpdate(availabilityCallback, executor, keyAt, it.next(), 0);
                    }
                }
            }
            for (int i2 = 0; i2 < this.mOpenedDevices.size(); i2++) {
                postSingleCameraOpenedUpdate(availabilityCallback, executor, this.mOpenedDevices.keyAt(i2), this.mOpenedDevices.valueAt(i2));
            }
        }

        private void onStatusChangedLocked(int i, java.lang.String str) {
            java.lang.Integer put;
            if (!android.hardware.Camera.shouldExposeAuxCamera() && java.lang.Integer.parseInt(str) >= 2) {
                android.util.Log.w(TAG, "[soar.cts] ignore the status update of camera: " + str);
                return;
            }
            if (!validStatus(i)) {
                android.util.Log.e(TAG, java.lang.String.format("Ignoring invalid device %s status 0x%x", str, java.lang.Integer.valueOf(i)));
                return;
            }
            if (i == 0) {
                put = this.mDeviceStatus.remove(str);
                this.mUnavailablePhysicalDevices.remove(str);
            } else {
                put = this.mDeviceStatus.put(str, java.lang.Integer.valueOf(i));
                if (put == null) {
                    this.mUnavailablePhysicalDevices.put(str, new java.util.ArrayList<>());
                }
            }
            if (put != null && put.intValue() == i) {
                return;
            }
            if (put != null && isAvailable(i) == isAvailable(put.intValue())) {
                return;
            }
            int size = this.mCallbackMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                java.util.concurrent.Executor valueAt = this.mCallbackMap.valueAt(i2);
                android.hardware.camera2.CameraManager.AvailabilityCallback keyAt = this.mCallbackMap.keyAt(i2);
                postSingleUpdate(keyAt, valueAt, str, null, i);
                if (isAvailable(i) && this.mUnavailablePhysicalDevices.containsKey(str)) {
                    java.util.Iterator<java.lang.String> it = this.mUnavailablePhysicalDevices.get(str).iterator();
                    while (it.hasNext()) {
                        postSingleUpdate(keyAt, valueAt, str, it.next(), 0);
                    }
                }
            }
        }

        private void onPhysicalCameraStatusChangedLocked(int i, java.lang.String str, java.lang.String str2) {
            if (!validStatus(i)) {
                android.util.Log.e(TAG, java.lang.String.format("Ignoring invalid device %s physical device %s status 0x%x", str, str2, java.lang.Integer.valueOf(i)));
                return;
            }
            if (!this.mDeviceStatus.containsKey(str) || !this.mUnavailablePhysicalDevices.containsKey(str)) {
                android.util.Log.e(TAG, java.lang.String.format("Camera %s is not present. Ignore physical camera status change", str));
                return;
            }
            java.util.ArrayList<java.lang.String> arrayList = this.mUnavailablePhysicalDevices.get(str);
            if (!isAvailable(i) && !arrayList.contains(str2)) {
                arrayList.add(str2);
            } else if (isAvailable(i) && arrayList.contains(str2)) {
                arrayList.remove(str2);
            } else {
                return;
            }
            if (!android.hardware.camera2.CameraManager.physicalCallbacksAreEnabledForUnavailableCamera() && !isAvailable(this.mDeviceStatus.get(str).intValue())) {
                android.util.Log.i(TAG, java.lang.String.format("Camera %s is not available. Ignore physical camera status change callback(s)", str));
                return;
            }
            int size = this.mCallbackMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                postSingleUpdate(this.mCallbackMap.keyAt(i2), this.mCallbackMap.valueAt(i2), str, str2, i);
            }
        }

        private void updateTorchCallbackLocked(android.hardware.camera2.CameraManager.TorchCallback torchCallback, java.util.concurrent.Executor executor) {
            for (int i = 0; i < this.mTorchStatus.size(); i++) {
                postSingleTorchUpdate(torchCallback, executor, this.mTorchStatus.keyAt(i), this.mTorchStatus.valueAt(i).intValue());
            }
        }

        private void onTorchStatusChangedLocked(int i, java.lang.String str) {
            if (!validTorchStatus(i)) {
                android.util.Log.e(TAG, java.lang.String.format("Ignoring invalid device %s torch status 0x%x", str, java.lang.Integer.valueOf(i)));
                return;
            }
            java.lang.Integer put = this.mTorchStatus.put(str, java.lang.Integer.valueOf(i));
            if (put != null && put.intValue() == i) {
                return;
            }
            int size = this.mTorchCallbackMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                postSingleTorchUpdate(this.mTorchCallbackMap.keyAt(i2), this.mTorchCallbackMap.valueAt(i2), str, i);
            }
        }

        private void onTorchStrengthLevelChangedLocked(java.lang.String str, int i) {
            int size = this.mTorchCallbackMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                postSingleTorchStrengthLevelUpdate(this.mTorchCallbackMap.keyAt(i2), this.mTorchCallbackMap.valueAt(i2), str, i);
            }
        }

        public void registerAvailabilityCallback(android.hardware.camera2.CameraManager.AvailabilityCallback availabilityCallback, java.util.concurrent.Executor executor, boolean z) {
            synchronized (this.mLock) {
                this.mHasOpenCloseListenerPermission = z;
                connectCameraServiceLocked();
                if (this.mCallbackMap.put(availabilityCallback, executor) == null) {
                    updateCallbackLocked(availabilityCallback, executor);
                }
                if (this.mCameraService == null) {
                    scheduleCameraServiceReconnectionLocked();
                }
            }
        }

        public void unregisterAvailabilityCallback(android.hardware.camera2.CameraManager.AvailabilityCallback availabilityCallback) {
            synchronized (this.mLock) {
                this.mCallbackMap.remove(availabilityCallback);
            }
        }

        public void registerTorchCallback(android.hardware.camera2.CameraManager.TorchCallback torchCallback, java.util.concurrent.Executor executor) {
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                if (this.mTorchCallbackMap.put(torchCallback, executor) == null) {
                    updateTorchCallbackLocked(torchCallback, executor);
                }
                if (this.mCameraService == null) {
                    scheduleCameraServiceReconnectionLocked();
                }
            }
        }

        public void unregisterTorchCallback(android.hardware.camera2.CameraManager.TorchCallback torchCallback) {
            synchronized (this.mLock) {
                this.mTorchCallbackMap.remove(torchCallback);
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onStatusChanged(int i, java.lang.String str) throws android.os.RemoteException {
            synchronized (this.mLock) {
                onStatusChangedLocked(i, str);
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onPhysicalCameraStatusChanged(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            synchronized (this.mLock) {
                onPhysicalCameraStatusChangedLocked(i, str, str2);
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onTorchStatusChanged(int i, java.lang.String str) throws android.os.RemoteException {
            synchronized (this.mLock) {
                onTorchStatusChangedLocked(i, str);
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onTorchStrengthLevelChanged(java.lang.String str, int i) throws android.os.RemoteException {
            synchronized (this.mLock) {
                onTorchStrengthLevelChangedLocked(str, i);
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraAccessPrioritiesChanged() {
            synchronized (this.mLock) {
                int size = this.mCallbackMap.size();
                for (int i = 0; i < size; i++) {
                    postSingleAccessPriorityChangeUpdate(this.mCallbackMap.keyAt(i), this.mCallbackMap.valueAt(i));
                }
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraOpened(java.lang.String str, java.lang.String str2) {
            synchronized (this.mLock) {
                onCameraOpenedLocked(str, str2);
            }
        }

        private void onCameraOpenedLocked(java.lang.String str, java.lang.String str2) {
            java.lang.String put = this.mOpenedDevices.put(str, str2);
            if (put != null) {
                if (put.equals(str2)) {
                    android.util.Log.w(TAG, "onCameraOpened was previously called for " + put + " and is now again called for the same package name, so no new client visible update will be sent");
                    return;
                }
                android.util.Log.w(TAG, "onCameraOpened was previously called for " + put + " and is now called for " + str2 + " without onCameraClosed being called first");
            }
            int size = this.mCallbackMap.size();
            for (int i = 0; i < size; i++) {
                postSingleCameraOpenedUpdate(this.mCallbackMap.keyAt(i), this.mCallbackMap.valueAt(i), str, str2);
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraClosed(java.lang.String str) {
            synchronized (this.mLock) {
                onCameraClosedLocked(str);
            }
        }

        private void onCameraClosedLocked(java.lang.String str) {
            this.mOpenedDevices.remove(str);
            int size = this.mCallbackMap.size();
            for (int i = 0; i < size; i++) {
                postSingleCameraClosedUpdate(this.mCallbackMap.keyAt(i), this.mCallbackMap.valueAt(i), str);
            }
        }

        private void scheduleCameraServiceReconnectionLocked() {
            if (this.mCallbackMap.isEmpty() && this.mTorchCallbackMap.isEmpty()) {
                return;
            }
            try {
                this.mScheduler.schedule(new java.lang.Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.CameraManager.CameraManagerGlobal.this.lambda$scheduleCameraServiceReconnectionLocked$3();
                    }
                }, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (java.util.concurrent.RejectedExecutionException e) {
                android.util.Log.e(TAG, "Failed to schedule camera service re-connect: " + e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$scheduleCameraServiceReconnectionLocked$3() {
            if (getCameraService() == null) {
                synchronized (this.mLock) {
                    scheduleCameraServiceReconnectionLocked();
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (this.mLock) {
                if (this.mCameraService == null) {
                    return;
                }
                this.mCameraService = null;
                for (int size = this.mDeviceStatus.size() - 1; size >= 0; size--) {
                    java.lang.String keyAt = this.mDeviceStatus.keyAt(size);
                    onStatusChangedLocked(0, keyAt);
                    if (this.mHasOpenCloseListenerPermission) {
                        onCameraClosedLocked(keyAt);
                    }
                }
                for (int i = 0; i < this.mTorchStatus.size(); i++) {
                    onTorchStatusChangedLocked(0, this.mTorchStatus.keyAt(i));
                }
                this.mConcurrentCameraIdCombinations.clear();
                scheduleCameraServiceReconnectionLocked();
            }
        }
    }
}
