package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class CameraDeviceSetupImpl extends android.hardware.camera2.CameraDevice.CameraDeviceSetup {
    private final java.lang.String mCameraId;
    private final android.hardware.camera2.CameraManager mCameraManager;
    private final android.content.Context mContext;
    private final java.lang.Object mInterfaceLock = new java.lang.Object();
    private final int mTargetSdkVersion;

    public CameraDeviceSetupImpl(java.lang.String str, android.hardware.camera2.CameraManager cameraManager, android.content.Context context) {
        this.mCameraId = str;
        this.mCameraManager = cameraManager;
        this.mContext = context;
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
    }

    @Override // android.hardware.camera2.CameraDevice.CameraDeviceSetup
    public android.hardware.camera2.CaptureRequest.Builder createCaptureRequest(int i) throws android.hardware.camera2.CameraAccessException {
        android.hardware.camera2.CaptureRequest.Builder builder;
        synchronized (this.mInterfaceLock) {
            if (this.mCameraManager.isCameraServiceDisabled()) {
                throw new java.lang.IllegalArgumentException("No cameras available on device");
            }
            android.hardware.ICameraService cameraService = this.mCameraManager.getCameraService();
            if (cameraService == null) {
                throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable.");
            }
            try {
                android.hardware.camera2.impl.CameraMetadataNative createDefaultRequest = cameraService.createDefaultRequest(this.mCameraId, i);
                android.hardware.camera2.impl.CameraDeviceImpl.disableZslIfNeeded(createDefaultRequest, this.mTargetSdkVersion, i);
                builder = new android.hardware.camera2.CaptureRequest.Builder(createDefaultRequest, false, -1, this.mCameraId, null);
            } catch (android.os.RemoteException e) {
                throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
            } catch (android.os.ServiceSpecificException e2) {
                throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
            }
        }
        return builder;
    }

    @Override // android.hardware.camera2.CameraDevice.CameraDeviceSetup
    public boolean isSessionConfigurationSupported(android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.hardware.camera2.CameraAccessException {
        boolean isSessionConfigurationWithParametersSupported;
        synchronized (this.mInterfaceLock) {
            if (this.mCameraManager.isCameraServiceDisabled()) {
                throw new java.lang.IllegalArgumentException("No cameras available on device");
            }
            android.hardware.ICameraService cameraService = this.mCameraManager.getCameraService();
            if (cameraService == null) {
                throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable.");
            }
            try {
                isSessionConfigurationWithParametersSupported = cameraService.isSessionConfigurationWithParametersSupported(this.mCameraId, sessionConfiguration);
            } catch (android.os.RemoteException e) {
                throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
            } catch (android.os.ServiceSpecificException e2) {
                throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
            }
        }
        return isSessionConfigurationWithParametersSupported;
    }

    @Override // android.hardware.camera2.CameraDevice.CameraDeviceSetup
    public android.hardware.camera2.CameraCharacteristics getSessionCharacteristics(android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.hardware.camera2.CameraAccessException {
        android.hardware.camera2.CameraCharacteristics cameraCharacteristics;
        synchronized (this.mInterfaceLock) {
            if (this.mCameraManager.isCameraServiceDisabled()) {
                throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently disabled");
            }
            android.hardware.ICameraService cameraService = this.mCameraManager.getCameraService();
            if (cameraService == null) {
                throw new android.hardware.camera2.CameraAccessException(2, "Camera service is currently unavailable");
            }
            try {
                cameraCharacteristics = new android.hardware.camera2.CameraCharacteristics(cameraService.getSessionCharacteristics(this.mCameraId, this.mTargetSdkVersion, android.hardware.camera2.CameraManager.shouldOverrideToPortrait(this.mContext), sessionConfiguration));
            } catch (android.os.RemoteException e) {
                throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
            } catch (android.os.ServiceSpecificException e2) {
                switch (e2.errorCode) {
                    case 3:
                        throw new java.lang.IllegalArgumentException("Invalid Session Configuration");
                    case 10:
                        throw new java.lang.UnsupportedOperationException("Session Characteristics Query not supported by device.");
                    default:
                        throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
                }
            }
        }
        return cameraCharacteristics;
    }

    @Override // android.hardware.camera2.CameraDevice.CameraDeviceSetup
    public void openCamera(java.util.concurrent.Executor executor, android.hardware.camera2.CameraDevice.StateCallback stateCallback) throws android.hardware.camera2.CameraAccessException {
        this.mCameraManager.openCamera(this.mCameraId, executor, stateCallback);
    }

    @Override // android.hardware.camera2.CameraDevice.CameraDeviceSetup
    public java.lang.String getId() {
        return this.mCameraId;
    }

    public int hashCode() {
        return this.mCameraId.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj instanceof android.hardware.camera2.impl.CameraDeviceSetupImpl) {
            return this.mCameraId.equals(((android.hardware.camera2.impl.CameraDeviceSetupImpl) obj).mCameraId);
        }
        return false;
    }

    public java.lang.String toString() {
        return "CameraDeviceSetup(cameraId='" + this.mCameraId + "')";
    }

    public static boolean isCameraDeviceSetupSupported(android.hardware.camera2.CameraCharacteristics cameraCharacteristics) {
        java.lang.Integer num;
        return com.android.internal.camera.flags.Flags.featureCombinationQuery() && (num = (java.lang.Integer) cameraCharacteristics.get(android.hardware.camera2.CameraCharacteristics.INFO_SESSION_CONFIGURATION_QUERY_VERSION)) != null && num.intValue() > 34;
    }
}
