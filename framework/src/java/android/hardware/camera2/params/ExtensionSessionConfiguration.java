package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class ExtensionSessionConfiguration {
    private static final java.lang.String TAG = "ExtensionSessionConfiguration";
    private android.hardware.camera2.CameraExtensionSession.StateCallback mCallback;
    private int mColorSpace;
    private java.util.concurrent.Executor mExecutor;
    private int mExtensionType;
    private java.util.List<android.hardware.camera2.params.OutputConfiguration> mOutputs;
    private android.hardware.camera2.params.OutputConfiguration mPostviewOutput = null;

    public ExtensionSessionConfiguration(int i, java.util.List<android.hardware.camera2.params.OutputConfiguration> list, java.util.concurrent.Executor executor, android.hardware.camera2.CameraExtensionSession.StateCallback stateCallback) {
        this.mExecutor = null;
        this.mCallback = null;
        this.mExtensionType = i;
        this.mOutputs = list;
        this.mExecutor = executor;
        this.mCallback = stateCallback;
    }

    public int getExtension() {
        return this.mExtensionType;
    }

    public void setPostviewOutputConfiguration(android.hardware.camera2.params.OutputConfiguration outputConfiguration) {
        this.mPostviewOutput = outputConfiguration;
    }

    public android.hardware.camera2.params.OutputConfiguration getPostviewOutputConfiguration() {
        return this.mPostviewOutput;
    }

    public java.util.List<android.hardware.camera2.params.OutputConfiguration> getOutputConfigurations() {
        return this.mOutputs;
    }

    public android.hardware.camera2.CameraExtensionSession.StateCallback getStateCallback() {
        return this.mCallback;
    }

    public java.util.concurrent.Executor getExecutor() {
        return this.mExecutor;
    }

    public void setColorSpace(android.graphics.ColorSpace.Named named) {
        this.mColorSpace = named.ordinal();
        java.util.Iterator<android.hardware.camera2.params.OutputConfiguration> it = this.mOutputs.iterator();
        while (it.hasNext()) {
            it.next().setColorSpace(named);
        }
        if (this.mPostviewOutput != null) {
            this.mPostviewOutput.setColorSpace(named);
        }
    }

    public void clearColorSpace() {
        this.mColorSpace = -1;
        java.util.Iterator<android.hardware.camera2.params.OutputConfiguration> it = this.mOutputs.iterator();
        while (it.hasNext()) {
            it.next().clearColorSpace();
        }
        if (this.mPostviewOutput != null) {
            this.mPostviewOutput.clearColorSpace();
        }
    }

    public android.graphics.ColorSpace getColorSpace() {
        if (this.mColorSpace != -1) {
            return android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.values()[this.mColorSpace]);
        }
        return null;
    }
}
