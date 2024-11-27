package com.android.server.usb;

/* loaded from: classes2.dex */
public final class UsbAlsaJackDetector implements java.lang.Runnable {
    private static final java.lang.String TAG = "UsbAlsaJackDetector";
    private com.android.server.usb.UsbAlsaDevice mAlsaDevice;
    private boolean mStopJackDetect = false;

    private static native boolean nativeHasJackDetect(int i);

    private native boolean nativeInputJackConnected(int i);

    private native boolean nativeJackDetect(int i);

    private native boolean nativeOutputJackConnected(int i);

    private UsbAlsaJackDetector(com.android.server.usb.UsbAlsaDevice usbAlsaDevice) {
        this.mAlsaDevice = usbAlsaDevice;
    }

    public static com.android.server.usb.UsbAlsaJackDetector startJackDetect(com.android.server.usb.UsbAlsaDevice usbAlsaDevice) {
        if (!nativeHasJackDetect(usbAlsaDevice.getCardNum())) {
            return null;
        }
        com.android.server.usb.UsbAlsaJackDetector usbAlsaJackDetector = new com.android.server.usb.UsbAlsaJackDetector(usbAlsaDevice);
        new java.lang.Thread(usbAlsaJackDetector, "USB jack detect thread").start();
        return usbAlsaJackDetector;
    }

    public boolean isInputJackConnected() {
        return nativeInputJackConnected(this.mAlsaDevice.getCardNum());
    }

    public boolean isOutputJackConnected() {
        return nativeOutputJackConnected(this.mAlsaDevice.getCardNum());
    }

    public void pleaseStop() {
        synchronized (this) {
            this.mStopJackDetect = true;
        }
    }

    public boolean jackDetectCallback() {
        synchronized (this) {
            try {
                if (this.mStopJackDetect) {
                    return false;
                }
                this.mAlsaDevice.updateOutputWiredDeviceConnectionState(true);
                this.mAlsaDevice.updateInputWiredDeviceConnectionState(true);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        nativeJackDetect(this.mAlsaDevice.getCardNum());
    }
}
