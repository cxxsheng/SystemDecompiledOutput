package com.android.server.biometrics;

/* loaded from: classes.dex */
public class BiometricCameraManagerImpl implements com.android.server.biometrics.BiometricCameraManager {
    private final android.hardware.camera2.CameraManager mCameraManager;
    private final android.hardware.SensorPrivacyManager mSensorPrivacyManager;
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Boolean> mIsCameraAvailable = new java.util.concurrent.ConcurrentHashMap<>();
    private final android.hardware.camera2.CameraManager.AvailabilityCallback mCameraAvailabilityCallback = new android.hardware.camera2.CameraManager.AvailabilityCallback() { // from class: com.android.server.biometrics.BiometricCameraManagerImpl.1
        @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
        public void onCameraAvailable(@android.annotation.NonNull java.lang.String str) {
            com.android.server.biometrics.BiometricCameraManagerImpl.this.mIsCameraAvailable.put(str, true);
        }

        @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
        public void onCameraUnavailable(@android.annotation.NonNull java.lang.String str) {
            com.android.server.biometrics.BiometricCameraManagerImpl.this.mIsCameraAvailable.put(str, false);
        }
    };

    public BiometricCameraManagerImpl(@android.annotation.NonNull android.hardware.camera2.CameraManager cameraManager, @android.annotation.NonNull android.hardware.SensorPrivacyManager sensorPrivacyManager) {
        this.mCameraManager = cameraManager;
        this.mSensorPrivacyManager = sensorPrivacyManager;
        this.mCameraManager.registerAvailabilityCallback(this.mCameraAvailabilityCallback, (android.os.Handler) null);
    }

    @Override // com.android.server.biometrics.BiometricCameraManager
    public boolean isAnyCameraUnavailable() {
        java.util.Iterator<java.lang.String> it = this.mIsCameraAvailable.keySet().iterator();
        while (it.hasNext()) {
            if (!this.mIsCameraAvailable.get(it.next()).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.biometrics.BiometricCameraManager
    public boolean isCameraPrivacyEnabled() {
        return this.mSensorPrivacyManager != null && this.mSensorPrivacyManager.isSensorPrivacyEnabled(1, 2);
    }
}
