package android.mtp;

/* loaded from: classes2.dex */
public final class MtpDevice {
    private static final java.lang.String TAG = "MtpDevice";
    private android.hardware.usb.UsbDeviceConnection mConnection;
    private final android.hardware.usb.UsbDevice mDevice;
    private long mNativeContext;
    private dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private final java.lang.Object mLock = new java.lang.Object();

    private native void native_close();

    private native boolean native_delete_object(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native void native_discard_event_request(int i);

    private native android.mtp.MtpDeviceInfo native_get_device_info();

    private native byte[] native_get_object(int i, long j);

    private native int[] native_get_object_handles(int i, int i2, int i3);

    private native android.mtp.MtpObjectInfo native_get_object_info(int i);

    private native long native_get_object_size_long(int i, int i2) throws java.io.IOException;

    private native int native_get_parent(int i);

    private native long native_get_partial_object(int i, long j, long j2, byte[] bArr) throws java.io.IOException;

    private native int native_get_partial_object_64(int i, long j, long j2, byte[] bArr) throws java.io.IOException;

    private native int native_get_storage_id(int i);

    private native int[] native_get_storage_ids();

    private native android.mtp.MtpStorageInfo native_get_storage_info(int i);

    private native byte[] native_get_thumbnail(int i);

    private native boolean native_import_file(int i, int i2);

    private native boolean native_import_file(int i, java.lang.String str);

    private native boolean native_open(java.lang.String str, int i);

    private native android.mtp.MtpEvent native_reap_event_request(int i) throws java.io.IOException;

    private native boolean native_send_object(int i, long j, int i2);

    private native android.mtp.MtpObjectInfo native_send_object_info(android.mtp.MtpObjectInfo mtpObjectInfo);

    private native int native_set_device_property_init_version(java.lang.String str);

    private native int native_submit_event_request() throws java.io.IOException;

    static {
        java.lang.System.loadLibrary("media_jni");
    }

    public MtpDevice(android.hardware.usb.UsbDevice usbDevice) {
        com.android.internal.util.Preconditions.checkNotNull(usbDevice);
        this.mDevice = usbDevice;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0032 A[Catch: all -> 0x0029, TryCatch #0 {all -> 0x0029, blocks: (B:17:0x000a, B:19:0x001a, B:6:0x002e, B:7:0x003b, B:15:0x0032), top: B:16:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:6:0x002e A[Catch: all -> 0x0029, TryCatch #0 {all -> 0x0029, blocks: (B:17:0x000a, B:19:0x001a, B:6:0x002e, B:7:0x003b, B:15:0x0032), top: B:16:0x000a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean open(android.hardware.usb.UsbDeviceConnection usbDeviceConnection) {
        boolean native_open;
        android.content.Context context = usbDeviceConnection.getContext();
        synchronized (this.mLock) {
            if (context != null) {
                try {
                    if (!((android.os.UserManager) context.getSystemService("user")).hasUserRestriction(android.os.UserManager.DISALLOW_USB_FILE_TRANSFER)) {
                        native_open = native_open(this.mDevice.getDeviceName(), usbDeviceConnection.getFileDescriptor());
                        if (native_open) {
                            usbDeviceConnection.close();
                        } else {
                            this.mConnection = usbDeviceConnection;
                            this.mCloseGuard.open("close");
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            native_open = false;
            if (native_open) {
            }
        }
        return native_open;
    }

    public void close() {
        synchronized (this.mLock) {
            if (this.mConnection != null) {
                this.mCloseGuard.close();
                native_close();
                this.mConnection.close();
                this.mConnection = null;
            }
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            close();
        } finally {
            super.finalize();
        }
    }

    public java.lang.String getDeviceName() {
        return this.mDevice.getDeviceName();
    }

    public int getDeviceId() {
        return this.mDevice.getDeviceId();
    }

    public java.lang.String toString() {
        return this.mDevice.getDeviceName();
    }

    public android.mtp.MtpDeviceInfo getDeviceInfo() {
        return native_get_device_info();
    }

    public int setDevicePropertyInitVersion(java.lang.String str) {
        return native_set_device_property_init_version(str);
    }

    public int[] getStorageIds() {
        return native_get_storage_ids();
    }

    public int[] getObjectHandles(int i, int i2, int i3) {
        return native_get_object_handles(i, i2, i3);
    }

    public byte[] getObject(int i, int i2) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i2, "objectSize should not be negative");
        return native_get_object(i, i2);
    }

    public long getPartialObject(int i, long j, long j2, byte[] bArr) throws java.io.IOException {
        return native_get_partial_object(i, j, j2, bArr);
    }

    public long getPartialObject64(int i, long j, long j2, byte[] bArr) throws java.io.IOException {
        return native_get_partial_object_64(i, j, j2, bArr);
    }

    public byte[] getThumbnail(int i) {
        return native_get_thumbnail(i);
    }

    public android.mtp.MtpStorageInfo getStorageInfo(int i) {
        return native_get_storage_info(i);
    }

    public android.mtp.MtpObjectInfo getObjectInfo(int i) {
        return native_get_object_info(i);
    }

    public boolean deleteObject(int i) {
        return native_delete_object(i);
    }

    public long getParent(int i) {
        return native_get_parent(i);
    }

    public long getStorageId(int i) {
        return native_get_storage_id(i);
    }

    public boolean importFile(int i, java.lang.String str) {
        return native_import_file(i, str);
    }

    public boolean importFile(int i, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        return native_import_file(i, parcelFileDescriptor.getFd());
    }

    public boolean sendObject(int i, long j, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        return native_send_object(i, j, parcelFileDescriptor.getFd());
    }

    public android.mtp.MtpObjectInfo sendObjectInfo(android.mtp.MtpObjectInfo mtpObjectInfo) {
        return native_send_object_info(mtpObjectInfo);
    }

    public android.mtp.MtpEvent readEvent(android.os.CancellationSignal cancellationSignal) throws java.io.IOException {
        final int native_submit_event_request = native_submit_event_request();
        com.android.internal.util.Preconditions.checkState(native_submit_event_request >= 0, "Other thread is reading an event.");
        if (cancellationSignal != null) {
            cancellationSignal.setOnCancelListener(new android.os.CancellationSignal.OnCancelListener() { // from class: android.mtp.MtpDevice.1
                @Override // android.os.CancellationSignal.OnCancelListener
                public void onCancel() {
                    android.mtp.MtpDevice.this.native_discard_event_request(native_submit_event_request);
                }
            });
        }
        try {
            return native_reap_event_request(native_submit_event_request);
        } finally {
            if (cancellationSignal != null) {
                cancellationSignal.setOnCancelListener(null);
            }
        }
    }

    public long getObjectSizeLong(int i, int i2) throws java.io.IOException {
        return native_get_object_size_long(i, i2);
    }
}
