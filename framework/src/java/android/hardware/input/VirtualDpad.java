package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class VirtualDpad extends android.hardware.input.VirtualInputDevice {
    private final java.util.Set<java.lang.Integer> mSupportedKeyCodes;

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

    public VirtualDpad(android.hardware.input.VirtualDpadConfig virtualDpadConfig, android.companion.virtual.IVirtualDevice iVirtualDevice, android.os.IBinder iBinder) {
        super(virtualDpadConfig, iVirtualDevice, iBinder);
        this.mSupportedKeyCodes = java.util.Collections.unmodifiableSet(new java.util.HashSet(java.util.Arrays.asList(4, 19, 20, 21, 22, 23)));
    }

    public void sendKeyEvent(android.hardware.input.VirtualKeyEvent virtualKeyEvent) {
        try {
            if (!this.mSupportedKeyCodes.contains(java.lang.Integer.valueOf(virtualKeyEvent.getKeyCode()))) {
                throw new java.lang.IllegalArgumentException("Unsupported key code " + virtualKeyEvent.getKeyCode() + " sent to a VirtualDpad input device.");
            }
            if (!this.mVirtualDevice.sendDpadKeyEvent(this.mToken, virtualKeyEvent)) {
                android.util.Log.w("VirtualInputDevice", "Failed to send key event to virtual dpad " + this.mConfig.getInputDeviceName());
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
