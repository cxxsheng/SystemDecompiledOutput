package android.hardware.camera2.extension;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class ExtensionOutputConfiguration {
    private final int mOutputConfigId;
    private final java.lang.String mPhysicalCameraId;
    private final int mSurfaceGroupId;
    private final java.util.List<android.hardware.camera2.extension.CameraOutputSurface> mSurfaces;

    public ExtensionOutputConfiguration(java.util.List<android.hardware.camera2.extension.CameraOutputSurface> list, int i, java.lang.String str, int i2) {
        this.mSurfaces = list;
        this.mPhysicalCameraId = str;
        this.mOutputConfigId = i;
        this.mSurfaceGroupId = i2;
    }

    private void initializeOutputConfig(android.hardware.camera2.extension.CameraOutputConfig cameraOutputConfig, android.hardware.camera2.extension.CameraOutputSurface cameraOutputSurface) {
        cameraOutputConfig.surface = cameraOutputSurface.getSurface();
        if (cameraOutputSurface.getSize() != null) {
            cameraOutputConfig.size = new android.hardware.camera2.extension.Size();
            cameraOutputConfig.size.width = cameraOutputSurface.getSize().getWidth();
            cameraOutputConfig.size.height = cameraOutputSurface.getSize().getHeight();
        }
        cameraOutputConfig.imageFormat = cameraOutputSurface.getImageFormat();
        cameraOutputConfig.type = 0;
        cameraOutputConfig.physicalCameraId = this.mPhysicalCameraId;
        cameraOutputConfig.outputId = new android.hardware.camera2.extension.OutputConfigId();
        cameraOutputConfig.outputId.id = this.mOutputConfigId;
        cameraOutputConfig.surfaceGroupId = this.mSurfaceGroupId;
    }

    android.hardware.camera2.extension.CameraOutputConfig getOutputConfig() {
        if (this.mSurfaces.isEmpty()) {
            return null;
        }
        android.hardware.camera2.extension.CameraOutputConfig cameraOutputConfig = new android.hardware.camera2.extension.CameraOutputConfig();
        initializeOutputConfig(cameraOutputConfig, this.mSurfaces.get(0));
        if (this.mSurfaces.size() > 1) {
            cameraOutputConfig.sharedSurfaceConfigs = new java.util.ArrayList(this.mSurfaces.size() - 1);
            for (int i = 1; i < this.mSurfaces.size(); i++) {
                android.hardware.camera2.extension.CameraOutputConfig cameraOutputConfig2 = new android.hardware.camera2.extension.CameraOutputConfig();
                initializeOutputConfig(cameraOutputConfig2, this.mSurfaces.get(i));
                cameraOutputConfig.sharedSurfaceConfigs.add(cameraOutputConfig2);
            }
        }
        return cameraOutputConfig;
    }
}
