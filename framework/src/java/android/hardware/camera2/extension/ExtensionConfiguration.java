package android.hardware.camera2.extension;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class ExtensionConfiguration {
    private final java.util.List<android.hardware.camera2.extension.ExtensionOutputConfiguration> mOutputs;
    private final android.hardware.camera2.CaptureRequest mSessionParameters;
    private final int mSessionTemplateId;
    private final int mSessionType;

    public ExtensionConfiguration(int i, int i2, java.util.List<android.hardware.camera2.extension.ExtensionOutputConfiguration> list, android.hardware.camera2.CaptureRequest captureRequest) {
        this.mSessionType = i;
        this.mSessionTemplateId = i2;
        this.mOutputs = list;
        this.mSessionParameters = captureRequest;
    }

    android.hardware.camera2.extension.CameraSessionConfig getCameraSessionConfig() {
        if (this.mOutputs.isEmpty()) {
            return null;
        }
        android.hardware.camera2.extension.CameraSessionConfig cameraSessionConfig = new android.hardware.camera2.extension.CameraSessionConfig();
        cameraSessionConfig.sessionTemplateId = this.mSessionTemplateId;
        cameraSessionConfig.sessionType = this.mSessionType;
        cameraSessionConfig.outputConfigs = new java.util.ArrayList(this.mOutputs.size());
        java.util.Iterator<android.hardware.camera2.extension.ExtensionOutputConfiguration> it = this.mOutputs.iterator();
        while (it.hasNext()) {
            cameraSessionConfig.outputConfigs.add(it.next().getOutputConfig());
        }
        if (this.mSessionParameters != null) {
            cameraSessionConfig.sessionParameter = this.mSessionParameters.getNativeCopy();
        }
        return cameraSessionConfig;
    }
}
