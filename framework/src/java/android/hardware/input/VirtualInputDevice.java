package android.hardware.input;

/* loaded from: classes2.dex */
abstract class VirtualInputDevice implements java.io.Closeable {
    protected static final java.lang.String TAG = "VirtualInputDevice";
    protected final android.hardware.input.VirtualInputDeviceConfig mConfig;
    protected final android.os.IBinder mToken;
    protected final android.companion.virtual.IVirtualDevice mVirtualDevice;

    VirtualInputDevice(android.hardware.input.VirtualInputDeviceConfig virtualInputDeviceConfig, android.companion.virtual.IVirtualDevice iVirtualDevice, android.os.IBinder iBinder) {
        this.mConfig = virtualInputDeviceConfig;
        this.mVirtualDevice = iVirtualDevice;
        this.mToken = iBinder;
    }

    public int getInputDeviceId() {
        try {
            return this.mVirtualDevice.getInputDeviceId(this.mToken);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        android.util.Log.d(TAG, "Closing virtual input device " + this.mConfig.getInputDeviceName());
        try {
            this.mVirtualDevice.unregisterInputDevice(this.mToken);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String toString() {
        return this.mConfig.toString();
    }
}
