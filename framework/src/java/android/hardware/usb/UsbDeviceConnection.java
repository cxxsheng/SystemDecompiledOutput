package android.hardware.usb;

/* loaded from: classes2.dex */
public class UsbDeviceConnection {
    private static final java.lang.String TAG = "UsbDeviceConnection";
    private android.content.Context mContext;
    private final android.hardware.usb.UsbDevice mDevice;
    private long mNativeContext;
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private final java.lang.Object mLock = new java.lang.Object();

    private native int native_bulk_request(int i, byte[] bArr, int i2, int i3, int i4);

    private native boolean native_claim_interface(int i, boolean z);

    private native void native_close();

    private native int native_control_request(int i, int i2, int i3, int i4, byte[] bArr, int i5, int i6, int i7);

    private native byte[] native_get_desc();

    private native int native_get_fd();

    private native java.lang.String native_get_serial();

    private native boolean native_open(java.lang.String str, java.io.FileDescriptor fileDescriptor);

    private native boolean native_release_interface(int i);

    private native android.hardware.usb.UsbRequest native_request_wait(long j) throws java.util.concurrent.TimeoutException;

    private native boolean native_reset_device();

    private native boolean native_set_configuration(int i);

    private native boolean native_set_interface(int i, int i2);

    public UsbDeviceConnection(android.hardware.usb.UsbDevice usbDevice) {
        this.mDevice = usbDevice;
    }

    boolean open(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.content.Context context) {
        boolean native_open;
        this.mContext = context.getApplicationContext();
        synchronized (this.mLock) {
            native_open = native_open(str, parcelFileDescriptor.getFileDescriptor());
            if (native_open) {
                this.mCloseGuard.open("UsbDeviceConnection.close");
            }
        }
        return native_open;
    }

    boolean isOpen() {
        return this.mNativeContext != 0;
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    boolean cancelRequest(android.hardware.usb.UsbRequest usbRequest) {
        synchronized (this.mLock) {
            if (!isOpen()) {
                return false;
            }
            return usbRequest.cancelIfOpen();
        }
    }

    boolean queueRequest(android.hardware.usb.UsbRequest usbRequest, java.nio.ByteBuffer byteBuffer, int i) {
        synchronized (this.mLock) {
            if (!isOpen()) {
                return false;
            }
            return usbRequest.queueIfConnectionOpen(byteBuffer, i);
        }
    }

    boolean queueRequest(android.hardware.usb.UsbRequest usbRequest, java.nio.ByteBuffer byteBuffer) {
        synchronized (this.mLock) {
            if (!isOpen()) {
                return false;
            }
            return usbRequest.queueIfConnectionOpen(byteBuffer);
        }
    }

    public void close() {
        synchronized (this.mLock) {
            if (isOpen()) {
                native_close();
                this.mCloseGuard.close();
            }
        }
    }

    public int getFileDescriptor() {
        return native_get_fd();
    }

    public byte[] getRawDescriptors() {
        return native_get_desc();
    }

    public boolean claimInterface(android.hardware.usb.UsbInterface usbInterface, boolean z) {
        return native_claim_interface(usbInterface.getId(), z);
    }

    public boolean releaseInterface(android.hardware.usb.UsbInterface usbInterface) {
        return native_release_interface(usbInterface.getId());
    }

    public boolean setInterface(android.hardware.usb.UsbInterface usbInterface) {
        return native_set_interface(usbInterface.getId(), usbInterface.getAlternateSetting());
    }

    public boolean setConfiguration(android.hardware.usb.UsbConfiguration usbConfiguration) {
        return native_set_configuration(usbConfiguration.getId());
    }

    public int controlTransfer(int i, int i2, int i3, int i4, byte[] bArr, int i5, int i6) {
        return controlTransfer(i, i2, i3, i4, bArr, 0, i5, i6);
    }

    public int controlTransfer(int i, int i2, int i3, int i4, byte[] bArr, int i5, int i6, int i7) {
        checkBounds(bArr, i5, i6);
        return native_control_request(i, i2, i3, i4, bArr, i5, i6, i7);
    }

    public int bulkTransfer(android.hardware.usb.UsbEndpoint usbEndpoint, byte[] bArr, int i, int i2) {
        return bulkTransfer(usbEndpoint, bArr, 0, i, i2);
    }

    public int bulkTransfer(android.hardware.usb.UsbEndpoint usbEndpoint, byte[] bArr, int i, int i2, int i3) {
        int i4;
        checkBounds(bArr, i, i2);
        if (this.mContext.getApplicationInfo().targetSdkVersion < 28 && i2 > 16384) {
            i4 = 16384;
        } else {
            i4 = i2;
        }
        return native_bulk_request(usbEndpoint.getAddress(), bArr, i, i4, i3);
    }

    @android.annotation.SystemApi
    public boolean resetDevice() {
        return native_reset_device();
    }

    public android.hardware.usb.UsbRequest requestWait() {
        android.hardware.usb.UsbRequest usbRequest;
        try {
            usbRequest = native_request_wait(-1L);
        } catch (java.util.concurrent.TimeoutException e) {
            usbRequest = null;
        }
        if (usbRequest != null) {
            usbRequest.dequeue(this.mContext.getApplicationInfo().targetSdkVersion >= 26);
        }
        return usbRequest;
    }

    public android.hardware.usb.UsbRequest requestWait(long j) throws java.util.concurrent.TimeoutException {
        android.hardware.usb.UsbRequest native_request_wait = native_request_wait(com.android.internal.util.Preconditions.checkArgumentNonnegative(j, "timeout"));
        if (native_request_wait != null) {
            native_request_wait.dequeue(true);
        }
        return native_request_wait;
    }

    public java.lang.String getSerial() {
        return native_get_serial();
    }

    private static void checkBounds(byte[] bArr, int i, int i2) {
        int length = bArr != null ? bArr.length : 0;
        if (i2 < 0 || i < 0 || i + i2 > length) {
            throw new java.lang.IllegalArgumentException("Buffer start or length out of bounds.");
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
        } finally {
            super.finalize();
        }
    }
}
