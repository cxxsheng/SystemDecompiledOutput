package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class VirtualNavigationTouchpad extends android.hardware.input.VirtualInputDevice {
    @Override // android.hardware.input.VirtualInputDevice, java.io.Closeable, java.lang.AutoCloseable
    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    @Override // android.hardware.input.VirtualInputDevice
    public /* bridge */ /* synthetic */ int getInputDeviceId() {
        return super.getInputDeviceId();
    }

    @Override // android.hardware.input.VirtualInputDevice
    public /* bridge */ /* synthetic */ java.lang.String toString() {
        return super.toString();
    }

    public VirtualNavigationTouchpad(android.hardware.input.VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig, android.companion.virtual.IVirtualDevice iVirtualDevice, android.os.IBinder iBinder) {
        super(virtualNavigationTouchpadConfig, iVirtualDevice, iBinder);
    }

    public void sendTouchEvent(android.hardware.input.VirtualTouchEvent virtualTouchEvent) {
        try {
            if (!this.mVirtualDevice.sendTouchEvent(this.mToken, virtualTouchEvent)) {
                android.util.Log.w("VirtualInputDevice", "Failed to send touch event to virtual navigation touchpad " + this.mConfig.getInputDeviceName());
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
