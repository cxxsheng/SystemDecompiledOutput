package android.hardware.camera2.extension;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class CameraOutputSurface {
    private final android.hardware.camera2.extension.OutputSurface mOutputSurface;

    CameraOutputSurface(android.hardware.camera2.extension.OutputSurface outputSurface) {
        this.mOutputSurface = outputSurface;
    }

    public CameraOutputSurface(android.view.Surface surface, android.util.Size size) {
        this.mOutputSurface = new android.hardware.camera2.extension.OutputSurface();
        this.mOutputSurface.surface = surface;
        this.mOutputSurface.imageFormat = android.hardware.camera2.utils.SurfaceUtils.getSurfaceFormat(surface);
        this.mOutputSurface.size = new android.hardware.camera2.extension.Size();
        this.mOutputSurface.size.width = size.getWidth();
        this.mOutputSurface.size.height = size.getHeight();
        this.mOutputSurface.dynamicRangeProfile = 1L;
        this.mOutputSurface.colorSpace = -1;
    }

    public android.view.Surface getSurface() {
        return this.mOutputSurface.surface;
    }

    public android.util.Size getSize() {
        if (this.mOutputSurface.size != null) {
            return new android.util.Size(this.mOutputSurface.size.width, this.mOutputSurface.size.height);
        }
        return null;
    }

    public int getImageFormat() {
        return this.mOutputSurface.imageFormat;
    }

    public long getDynamicRangeProfile() {
        return this.mOutputSurface.dynamicRangeProfile;
    }

    public int getColorSpace() {
        return this.mOutputSurface.colorSpace;
    }

    public void setDynamicRangeProfile(long j) {
        this.mOutputSurface.dynamicRangeProfile = j;
    }

    public void setColorSpace(int i) {
        this.mOutputSurface.colorSpace = i;
    }
}
