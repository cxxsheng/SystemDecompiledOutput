package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class VirtualKeyboard extends android.hardware.input.VirtualInputDevice {
    private final int mUnsupportedKeyCode;

    @Override // android.hardware.input.VirtualInputDevice, java.io.Closeable, java.lang.AutoCloseable
    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    @Override // android.hardware.input.VirtualInputDevice
    public /* bridge */ /* synthetic */ java.lang.String toString() {
        return super.toString();
    }

    public VirtualKeyboard(android.hardware.input.VirtualKeyboardConfig virtualKeyboardConfig, android.companion.virtual.IVirtualDevice iVirtualDevice, android.os.IBinder iBinder) {
        super(virtualKeyboardConfig, iVirtualDevice, iBinder);
        this.mUnsupportedKeyCode = 23;
    }

    public void sendKeyEvent(android.hardware.input.VirtualKeyEvent virtualKeyEvent) {
        try {
            if (23 == virtualKeyEvent.getKeyCode()) {
                throw new java.lang.IllegalArgumentException("Unsupported key code " + virtualKeyEvent.getKeyCode() + " sent to a VirtualKeyboard input device.");
            }
            if (!this.mVirtualDevice.sendKeyEvent(this.mToken, virtualKeyEvent)) {
                android.util.Log.w("VirtualInputDevice", "Failed to send key event to virtual keyboard " + this.mConfig.getInputDeviceName());
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.hardware.input.VirtualInputDevice
    public int getInputDeviceId() {
        return super.getInputDeviceId();
    }
}
