package android.companion.virtual.camera;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class VirtualCamera implements java.io.Closeable {
    private final java.lang.String mCameraId;
    private final android.companion.virtual.camera.VirtualCameraConfig mConfig;
    private final android.companion.virtual.IVirtualDevice mVirtualDevice;

    public VirtualCamera(android.companion.virtual.IVirtualDevice iVirtualDevice, java.lang.String str, android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) {
        this.mVirtualDevice = (android.companion.virtual.IVirtualDevice) java.util.Objects.requireNonNull(iVirtualDevice);
        this.mCameraId = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mConfig = (android.companion.virtual.camera.VirtualCameraConfig) java.util.Objects.requireNonNull(virtualCameraConfig);
    }

    public android.companion.virtual.camera.VirtualCameraConfig getConfig() {
        return this.mConfig;
    }

    public java.lang.String getId() {
        return this.mCameraId;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        try {
            this.mVirtualDevice.unregisterVirtualCamera(this.mConfig);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
