package android.view;

/* loaded from: classes4.dex */
public final class InputWindowHandle {
    public float alpha;
    public boolean canOccludePresentation;
    public android.util.Size contentSize;
    public long dispatchingTimeoutMillis;
    public int displayId;
    public android.os.IBinder focusTransferTarget;
    public final android.graphics.Rect frame;
    public android.view.InputApplicationHandle inputApplicationHandle;
    public int inputConfig;
    public int layoutParamsFlags;
    public int layoutParamsType;
    public java.lang.String name;
    public int ownerPid;
    public int ownerUid;
    public java.lang.String packageName;
    private long ptr;
    public boolean replaceTouchableRegionWithCrop;
    public float scaleFactor;
    public int surfaceInset;
    public android.os.IBinder token;
    public int touchOcclusionMode;
    public final android.graphics.Region touchableRegion;
    public java.lang.ref.WeakReference<android.view.SurfaceControl> touchableRegionSurfaceControl;
    public android.graphics.Matrix transform;
    private android.os.IBinder windowToken;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InputConfigFlags {
    }

    private native void nativeDispose();

    public InputWindowHandle(android.view.InputApplicationHandle inputApplicationHandle, int i) {
        this.frame = new android.graphics.Rect();
        this.contentSize = new android.util.Size(0, 0);
        this.touchableRegion = new android.graphics.Region();
        this.touchOcclusionMode = 0;
        this.touchableRegionSurfaceControl = new java.lang.ref.WeakReference<>(null);
        this.inputApplicationHandle = inputApplicationHandle;
        this.displayId = i;
    }

    public InputWindowHandle(android.view.InputWindowHandle inputWindowHandle) {
        this.frame = new android.graphics.Rect();
        this.contentSize = new android.util.Size(0, 0);
        this.touchableRegion = new android.graphics.Region();
        this.touchOcclusionMode = 0;
        this.touchableRegionSurfaceControl = new java.lang.ref.WeakReference<>(null);
        this.ptr = 0L;
        this.inputApplicationHandle = new android.view.InputApplicationHandle(inputWindowHandle.inputApplicationHandle);
        this.token = inputWindowHandle.token;
        this.windowToken = inputWindowHandle.windowToken;
        this.name = inputWindowHandle.name;
        this.layoutParamsFlags = inputWindowHandle.layoutParamsFlags;
        this.layoutParamsType = inputWindowHandle.layoutParamsType;
        this.dispatchingTimeoutMillis = inputWindowHandle.dispatchingTimeoutMillis;
        this.frame.set(inputWindowHandle.frame);
        this.surfaceInset = inputWindowHandle.surfaceInset;
        this.scaleFactor = inputWindowHandle.scaleFactor;
        this.touchableRegion.set(inputWindowHandle.touchableRegion);
        this.inputConfig = inputWindowHandle.inputConfig;
        this.touchOcclusionMode = inputWindowHandle.touchOcclusionMode;
        this.ownerPid = inputWindowHandle.ownerPid;
        this.ownerUid = inputWindowHandle.ownerUid;
        this.packageName = inputWindowHandle.packageName;
        this.displayId = inputWindowHandle.displayId;
        this.touchableRegionSurfaceControl = inputWindowHandle.touchableRegionSurfaceControl;
        this.replaceTouchableRegionWithCrop = inputWindowHandle.replaceTouchableRegionWithCrop;
        if (inputWindowHandle.transform != null) {
            this.transform = new android.graphics.Matrix();
            this.transform.set(inputWindowHandle.transform);
        }
        this.focusTransferTarget = inputWindowHandle.focusTransferTarget;
        this.contentSize = new android.util.Size(inputWindowHandle.contentSize.getWidth(), inputWindowHandle.contentSize.getHeight());
        this.alpha = inputWindowHandle.alpha;
        this.canOccludePresentation = inputWindowHandle.canOccludePresentation;
    }

    public java.lang.String toString() {
        return (this.name != null ? this.name : "") + ", frame=[" + this.frame + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END + ", touchableRegion=" + this.touchableRegion + ", scaleFactor=" + this.scaleFactor + ", transform=" + this.transform + ", windowToken=" + this.windowToken + ", displayId=" + this.displayId + ", isClone=" + ((this.inputConfig & 65536) != 0) + ", contentSize=" + this.contentSize + ", alpha=" + this.alpha + ", canOccludePresentation=" + this.canOccludePresentation;
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            nativeDispose();
        } finally {
            super.finalize();
        }
    }

    public void replaceTouchableRegionWithCrop(android.view.SurfaceControl surfaceControl) {
        setTouchableRegionCrop(surfaceControl);
        this.replaceTouchableRegionWithCrop = true;
    }

    public void setTouchableRegionCrop(android.view.SurfaceControl surfaceControl) {
        this.touchableRegionSurfaceControl = new java.lang.ref.WeakReference<>(surfaceControl);
    }

    public void setWindowToken(android.os.IBinder iBinder) {
        this.windowToken = iBinder;
    }

    public android.os.IBinder getWindowToken() {
        return this.windowToken;
    }

    public void setInputConfig(int i, boolean z) {
        if (z) {
            this.inputConfig = i | this.inputConfig;
        } else {
            this.inputConfig = (~i) & this.inputConfig;
        }
    }

    public void setTrustedOverlay(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, boolean z) {
        if (com.android.window.flags.Flags.surfaceTrustedOverlay()) {
            transaction.setTrustedOverlay(surfaceControl, z);
        } else if (z) {
            this.inputConfig |= 256;
        }
    }
}
