package android.accessibilityservice;

/* loaded from: classes.dex */
public interface BrailleDisplayController {
    public static final java.lang.String TEST_BRAILLE_DISPLAY_BUS_BLUETOOTH = "BUS_BLUETOOTH";
    public static final java.lang.String TEST_BRAILLE_DISPLAY_DESCRIPTOR = "DESCRIPTOR";
    public static final java.lang.String TEST_BRAILLE_DISPLAY_HIDRAW_PATH = "HIDRAW_PATH";
    public static final java.lang.String TEST_BRAILLE_DISPLAY_UNIQUE_ID = "UNIQUE_ID";

    public interface BrailleDisplayCallback {
        public static final int FLAG_ERROR_BRAILLE_DISPLAY_NOT_FOUND = 2;
        public static final int FLAG_ERROR_CANNOT_ACCESS = 1;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ErrorCode {
        }

        void onConnected(byte[] bArr);

        void onConnectionFailed(int i);

        void onDisconnected();

        void onInput(byte[] bArr);
    }

    void connect(android.bluetooth.BluetoothDevice bluetoothDevice, android.accessibilityservice.BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback);

    void connect(android.bluetooth.BluetoothDevice bluetoothDevice, java.util.concurrent.Executor executor, android.accessibilityservice.BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback);

    void connect(android.hardware.usb.UsbDevice usbDevice, android.accessibilityservice.BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback);

    void connect(android.hardware.usb.UsbDevice usbDevice, java.util.concurrent.Executor executor, android.accessibilityservice.BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback);

    void disconnect();

    boolean isConnected();

    void write(byte[] bArr) throws java.io.IOException;

    static void checkApiFlagIsEnabled() {
        if (!android.view.accessibility.Flags.brailleDisplayHid()) {
            throw new java.lang.IllegalStateException("Flag BRAILLE_DISPLAY_HID not enabled");
        }
    }

    static void setTestBrailleDisplayData(android.accessibilityservice.AccessibilityService accessibilityService, java.util.List<android.os.Bundle> list) {
        checkApiFlagIsEnabled();
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(accessibilityService.getConnectionId());
        if (connection != null) {
            try {
                connection.setTestBrailleDisplayData(list);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
