package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class ICameraDeviceUserWrapper {
    private final android.hardware.camera2.ICameraDeviceUser mRemoteDevice;

    public ICameraDeviceUserWrapper(android.hardware.camera2.ICameraDeviceUser iCameraDeviceUser) {
        if (iCameraDeviceUser == null) {
            throw new java.lang.NullPointerException("Remote device may not be null");
        }
        this.mRemoteDevice = iCameraDeviceUser;
    }

    public void unlinkToDeath(android.os.IBinder.DeathRecipient deathRecipient, int i) {
        if (this.mRemoteDevice.asBinder() != null) {
            this.mRemoteDevice.asBinder().unlinkToDeath(deathRecipient, i);
        }
    }

    public void disconnect() {
        try {
            this.mRemoteDevice.disconnect();
        } catch (android.os.RemoteException e) {
        }
    }

    public android.hardware.camera2.utils.SubmitInfo submitRequest(android.hardware.camera2.CaptureRequest captureRequest, boolean z) throws android.hardware.camera2.CameraAccessException {
        try {
            return this.mRemoteDevice.submitRequest(captureRequest, z);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public android.hardware.camera2.utils.SubmitInfo submitRequestList(android.hardware.camera2.CaptureRequest[] captureRequestArr, boolean z) throws android.hardware.camera2.CameraAccessException {
        try {
            return this.mRemoteDevice.submitRequestList(captureRequestArr, z);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public long cancelRequest(int i) throws android.hardware.camera2.CameraAccessException {
        try {
            return this.mRemoteDevice.cancelRequest(i);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void beginConfigure() throws android.hardware.camera2.CameraAccessException {
        try {
            this.mRemoteDevice.beginConfigure();
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public int[] endConfigure(int i, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, long j) throws android.hardware.camera2.CameraAccessException {
        try {
            android.hardware.camera2.ICameraDeviceUser iCameraDeviceUser = this.mRemoteDevice;
            if (cameraMetadataNative == null) {
                cameraMetadataNative = new android.hardware.camera2.impl.CameraMetadataNative();
            }
            return iCameraDeviceUser.endConfigure(i, cameraMetadataNative, j);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void deleteStream(int i) throws android.hardware.camera2.CameraAccessException {
        try {
            this.mRemoteDevice.deleteStream(i);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public int createStream(android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.hardware.camera2.CameraAccessException {
        try {
            return this.mRemoteDevice.createStream(outputConfiguration);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public int createInputStream(int i, int i2, int i3, boolean z) throws android.hardware.camera2.CameraAccessException {
        try {
            return this.mRemoteDevice.createInputStream(i, i2, i3, z);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public android.view.Surface getInputSurface() throws android.hardware.camera2.CameraAccessException {
        try {
            return this.mRemoteDevice.getInputSurface();
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public android.hardware.camera2.impl.CameraMetadataNative createDefaultRequest(int i) throws android.hardware.camera2.CameraAccessException {
        try {
            return this.mRemoteDevice.createDefaultRequest(i);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public android.hardware.camera2.impl.CameraMetadataNative getCameraInfo() throws android.hardware.camera2.CameraAccessException {
        try {
            return this.mRemoteDevice.getCameraInfo();
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void waitUntilIdle() throws android.hardware.camera2.CameraAccessException {
        try {
            this.mRemoteDevice.waitUntilIdle();
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public boolean isSessionConfigurationSupported(android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.hardware.camera2.CameraAccessException {
        try {
            return this.mRemoteDevice.isSessionConfigurationSupported(sessionConfiguration);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 10) {
                throw new java.lang.UnsupportedOperationException("Session configuration query not supported");
            }
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException("Invalid session configuration");
            }
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public long flush() throws android.hardware.camera2.CameraAccessException {
        try {
            return this.mRemoteDevice.flush();
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void prepare(int i) throws android.hardware.camera2.CameraAccessException {
        try {
            this.mRemoteDevice.prepare(i);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void tearDown(int i) throws android.hardware.camera2.CameraAccessException {
        try {
            this.mRemoteDevice.tearDown(i);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void prepare2(int i, int i2) throws android.hardware.camera2.CameraAccessException {
        try {
            this.mRemoteDevice.prepare2(i, i2);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void updateOutputConfiguration(int i, android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.hardware.camera2.CameraAccessException {
        try {
            this.mRemoteDevice.updateOutputConfiguration(i, outputConfiguration);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public android.hardware.camera2.ICameraOfflineSession switchToOffline(android.hardware.camera2.ICameraDeviceCallbacks iCameraDeviceCallbacks, int[] iArr) throws android.hardware.camera2.CameraAccessException {
        try {
            return this.mRemoteDevice.switchToOffline(iCameraDeviceCallbacks, iArr);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void finalizeOutputConfigurations(int i, android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.hardware.camera2.CameraAccessException {
        try {
            this.mRemoteDevice.finalizeOutputConfigurations(i, outputConfiguration);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public void setCameraAudioRestriction(int i) throws android.hardware.camera2.CameraAccessException {
        try {
            this.mRemoteDevice.setCameraAudioRestriction(i);
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }

    public int getGlobalAudioRestriction() throws android.hardware.camera2.CameraAccessException {
        try {
            return this.mRemoteDevice.getGlobalAudioRestriction();
        } catch (android.os.RemoteException e) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e);
        } catch (android.os.ServiceSpecificException e2) {
            throw android.hardware.camera2.utils.ExceptionUtils.throwAsPublicException(e2);
        }
    }
}
