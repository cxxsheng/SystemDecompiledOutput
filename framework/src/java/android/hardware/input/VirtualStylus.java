package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class VirtualStylus extends android.hardware.input.VirtualInputDevice {
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

    public VirtualStylus(android.hardware.input.VirtualStylusConfig virtualStylusConfig, android.companion.virtual.IVirtualDevice iVirtualDevice, android.os.IBinder iBinder) {
        super(virtualStylusConfig, iVirtualDevice, iBinder);
    }

    public void sendMotionEvent(android.hardware.input.VirtualStylusMotionEvent virtualStylusMotionEvent) {
        try {
            if (!this.mVirtualDevice.sendStylusMotionEvent(this.mToken, virtualStylusMotionEvent)) {
                android.util.Log.w("VirtualInputDevice", "Failed to send motion event from virtual stylus " + this.mConfig.getInputDeviceName());
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendButtonEvent(android.hardware.input.VirtualStylusButtonEvent virtualStylusButtonEvent) {
        try {
            if (!this.mVirtualDevice.sendStylusButtonEvent(this.mToken, virtualStylusButtonEvent)) {
                android.util.Log.w("VirtualInputDevice", "Failed to send button event from virtual stylus " + this.mConfig.getInputDeviceName());
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
