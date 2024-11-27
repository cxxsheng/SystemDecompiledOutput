package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class VirtualMouse extends android.hardware.input.VirtualInputDevice {
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

    public VirtualMouse(android.hardware.input.VirtualMouseConfig virtualMouseConfig, android.companion.virtual.IVirtualDevice iVirtualDevice, android.os.IBinder iBinder) {
        super(virtualMouseConfig, iVirtualDevice, iBinder);
    }

    public void sendButtonEvent(android.hardware.input.VirtualMouseButtonEvent virtualMouseButtonEvent) {
        try {
            if (!this.mVirtualDevice.sendButtonEvent(this.mToken, virtualMouseButtonEvent)) {
                android.util.Log.w("VirtualInputDevice", "Failed to send button event to virtual mouse " + this.mConfig.getInputDeviceName());
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendScrollEvent(android.hardware.input.VirtualMouseScrollEvent virtualMouseScrollEvent) {
        try {
            if (!this.mVirtualDevice.sendScrollEvent(this.mToken, virtualMouseScrollEvent)) {
                android.util.Log.w("VirtualInputDevice", "Failed to send scroll event to virtual mouse " + this.mConfig.getInputDeviceName());
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendRelativeEvent(android.hardware.input.VirtualMouseRelativeEvent virtualMouseRelativeEvent) {
        try {
            if (!this.mVirtualDevice.sendRelativeEvent(this.mToken, virtualMouseRelativeEvent)) {
                android.util.Log.w("VirtualInputDevice", "Failed to send relative event to virtual mouse " + this.mConfig.getInputDeviceName());
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.graphics.PointF getCursorPosition() {
        try {
            return this.mVirtualDevice.getCursorPosition(this.mToken);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
