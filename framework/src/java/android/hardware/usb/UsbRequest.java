package android.hardware.usb;

/* loaded from: classes2.dex */
public class UsbRequest {
    static final int MAX_USBFS_BUFFER_SIZE = 16384;
    private static final java.lang.String TAG = "UsbRequest";
    private java.nio.ByteBuffer mBuffer;
    private java.lang.Object mClientData;
    private android.hardware.usb.UsbDeviceConnection mConnection;
    private android.hardware.usb.UsbEndpoint mEndpoint;
    private boolean mIsUsingNewQueue;
    private int mLength;
    private long mNativeContext;
    private java.nio.ByteBuffer mTempBuffer;
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private final java.lang.Object mLock = new java.lang.Object();

    private native boolean native_cancel();

    private native void native_close();

    private native int native_dequeue_array(byte[] bArr, int i, boolean z);

    private native int native_dequeue_direct();

    private native boolean native_init(android.hardware.usb.UsbDeviceConnection usbDeviceConnection, int i, int i2, int i3, int i4);

    private native boolean native_queue(java.nio.ByteBuffer byteBuffer, int i, int i2);

    private native boolean native_queue_array(byte[] bArr, int i, boolean z);

    private native boolean native_queue_direct(java.nio.ByteBuffer byteBuffer, int i, boolean z);

    public boolean initialize(android.hardware.usb.UsbDeviceConnection usbDeviceConnection, android.hardware.usb.UsbEndpoint usbEndpoint) {
        this.mEndpoint = usbEndpoint;
        this.mConnection = (android.hardware.usb.UsbDeviceConnection) java.util.Objects.requireNonNull(usbDeviceConnection, "connection");
        boolean native_init = native_init(usbDeviceConnection, usbEndpoint.getAddress(), usbEndpoint.getAttributes(), usbEndpoint.getMaxPacketSize(), usbEndpoint.getInterval());
        if (native_init) {
            this.mCloseGuard.open("UsbRequest.close");
        }
        return native_init;
    }

    public void close() {
        synchronized (this.mLock) {
            if (this.mNativeContext != 0) {
                this.mEndpoint = null;
                this.mConnection = null;
                native_close();
                this.mCloseGuard.close();
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

    public android.hardware.usb.UsbEndpoint getEndpoint() {
        return this.mEndpoint;
    }

    public java.lang.Object getClientData() {
        return this.mClientData;
    }

    public void setClientData(java.lang.Object obj) {
        this.mClientData = obj;
    }

    @java.lang.Deprecated
    public boolean queue(java.nio.ByteBuffer byteBuffer, int i) {
        android.hardware.usb.UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (usbDeviceConnection == null) {
            throw new java.lang.NullPointerException("invalid connection");
        }
        return usbDeviceConnection.queueRequest(this, byteBuffer, i);
    }

    boolean queueIfConnectionOpen(java.nio.ByteBuffer byteBuffer, int i) {
        boolean native_queue_array;
        android.hardware.usb.UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (usbDeviceConnection == null || !usbDeviceConnection.isOpen()) {
            throw new java.lang.NullPointerException("invalid connection");
        }
        boolean z = this.mEndpoint.getDirection() == 0;
        if (usbDeviceConnection.getContext().getApplicationInfo().targetSdkVersion < 28 && i > 16384) {
            i = 16384;
        }
        synchronized (this.mLock) {
            this.mBuffer = byteBuffer;
            this.mLength = i;
            if (byteBuffer.isDirect()) {
                native_queue_array = native_queue_direct(byteBuffer, i, z);
            } else if (byteBuffer.hasArray()) {
                native_queue_array = native_queue_array(byteBuffer.array(), i, z);
            } else {
                throw new java.lang.IllegalArgumentException("buffer is not direct and has no array");
            }
            if (!native_queue_array) {
                this.mBuffer = null;
                this.mLength = 0;
            }
        }
        return native_queue_array;
    }

    public boolean queue(java.nio.ByteBuffer byteBuffer) {
        android.hardware.usb.UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (usbDeviceConnection == null) {
            throw new java.lang.IllegalStateException("invalid connection");
        }
        return usbDeviceConnection.queueRequest(this, byteBuffer);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x006e A[Catch: all -> 0x00ac, TryCatch #0 {, blocks: (B:14:0x0032, B:16:0x0037, B:17:0x00a2, B:23:0x003e, B:25:0x004c, B:26:0x0057, B:31:0x0063, B:33:0x006e, B:35:0x007c, B:36:0x0092, B:37:0x0094), top: B:13:0x0032 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean queueIfConnectionOpen(java.nio.ByteBuffer byteBuffer) {
        boolean z;
        boolean native_queue;
        android.hardware.usb.UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (usbDeviceConnection == null || !usbDeviceConnection.isOpen()) {
            throw new java.lang.IllegalStateException("invalid connection");
        }
        com.android.internal.util.Preconditions.checkState(this.mNativeContext != 0, "request is not initialized");
        com.android.internal.util.Preconditions.checkState(!this.mIsUsingNewQueue, "this request is currently queued");
        boolean z2 = this.mEndpoint.getDirection() == 0;
        synchronized (this.mLock) {
            this.mBuffer = byteBuffer;
            if (byteBuffer == null) {
                this.mIsUsingNewQueue = true;
                native_queue = native_queue(null, 0, 0);
            } else {
                if (usbDeviceConnection.getContext().getApplicationInfo().targetSdkVersion < 28) {
                    com.android.internal.util.Preconditions.checkArgumentInRange(byteBuffer.remaining(), 0, 16384, "number of remaining bytes");
                }
                if (byteBuffer.isReadOnly() && !z2) {
                    z = false;
                    com.android.internal.util.Preconditions.checkArgument(z, "buffer can not be read-only when receiving data");
                    if (!byteBuffer.isDirect()) {
                        this.mTempBuffer = java.nio.ByteBuffer.allocateDirect(this.mBuffer.remaining());
                        if (z2) {
                            this.mBuffer.mark();
                            this.mTempBuffer.put(this.mBuffer);
                            this.mTempBuffer.flip();
                            this.mBuffer.reset();
                        }
                        byteBuffer = this.mTempBuffer;
                    }
                    this.mIsUsingNewQueue = true;
                    native_queue = native_queue(byteBuffer, byteBuffer.position(), byteBuffer.remaining());
                }
                z = true;
                com.android.internal.util.Preconditions.checkArgument(z, "buffer can not be read-only when receiving data");
                if (!byteBuffer.isDirect()) {
                }
                this.mIsUsingNewQueue = true;
                native_queue = native_queue(byteBuffer, byteBuffer.position(), byteBuffer.remaining());
            }
        }
        if (!native_queue) {
            this.mIsUsingNewQueue = false;
            this.mTempBuffer = null;
            this.mBuffer = null;
        }
        return native_queue;
    }

    void dequeue(boolean z) {
        int native_dequeue_array;
        boolean z2 = this.mEndpoint.getDirection() == 0;
        synchronized (this.mLock) {
            if (this.mIsUsingNewQueue) {
                int native_dequeue_direct = native_dequeue_direct();
                this.mIsUsingNewQueue = false;
                if (this.mBuffer != null) {
                    if (this.mTempBuffer == null) {
                        this.mBuffer.position(this.mBuffer.position() + native_dequeue_direct);
                    } else {
                        this.mTempBuffer.limit(native_dequeue_direct);
                        try {
                            if (z2) {
                                this.mBuffer.position(this.mBuffer.position() + native_dequeue_direct);
                            } else {
                                this.mBuffer.put(this.mTempBuffer);
                            }
                            this.mTempBuffer = null;
                        } catch (java.lang.Throwable th) {
                            this.mTempBuffer = null;
                            throw th;
                        }
                    }
                }
                this.mBuffer = null;
                this.mLength = 0;
            } else {
                if (this.mBuffer.isDirect()) {
                    native_dequeue_array = native_dequeue_direct();
                } else {
                    native_dequeue_array = native_dequeue_array(this.mBuffer.array(), this.mLength, z2);
                }
                if (native_dequeue_array >= 0) {
                    int min = java.lang.Math.min(native_dequeue_array, this.mLength);
                    try {
                        this.mBuffer.position(min);
                    } catch (java.lang.IllegalArgumentException e) {
                        if (z) {
                            android.util.Log.e(TAG, "Buffer " + this.mBuffer + " does not have enough space to read " + min + " bytes", e);
                            throw new java.nio.BufferOverflowException();
                        }
                        throw e;
                    }
                }
                this.mBuffer = null;
                this.mLength = 0;
            }
        }
    }

    public boolean cancel() {
        android.hardware.usb.UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (usbDeviceConnection == null) {
            return false;
        }
        return usbDeviceConnection.cancelRequest(this);
    }

    boolean cancelIfOpen() {
        android.hardware.usb.UsbDeviceConnection usbDeviceConnection = this.mConnection;
        if (this.mNativeContext == 0 || (usbDeviceConnection != null && !usbDeviceConnection.isOpen())) {
            android.util.Log.w(TAG, "Detected attempt to cancel a request on a connection which isn't open");
            return false;
        }
        return native_cancel();
    }
}
