package android.hardware;

/* loaded from: classes.dex */
public final class HardwareBuffer implements android.os.Parcelable, java.lang.AutoCloseable {
    public static final int BLOB = 33;
    public static final android.os.Parcelable.Creator<android.hardware.HardwareBuffer> CREATOR = new android.os.Parcelable.Creator<android.hardware.HardwareBuffer>() { // from class: android.hardware.HardwareBuffer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.HardwareBuffer createFromParcel(android.os.Parcel parcel) {
            if (parcel == null) {
                throw new java.lang.NullPointerException("null passed to createFromParcel");
            }
            long nReadHardwareBufferFromParcel = android.hardware.HardwareBuffer.nReadHardwareBufferFromParcel(parcel);
            if (nReadHardwareBufferFromParcel != 0) {
                return new android.hardware.HardwareBuffer(nReadHardwareBufferFromParcel);
            }
            throw new android.os.BadParcelableException("Failed to read hardware buffer");
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.HardwareBuffer[] newArray(int i) {
            return new android.hardware.HardwareBuffer[i];
        }
    };
    public static final int DS_24UI8 = 50;
    public static final int DS_FP32UI8 = 52;
    public static final int D_16 = 48;
    public static final int D_24 = 49;
    public static final int D_FP32 = 51;
    public static final int RGBA_10101010 = 59;
    public static final int RGBA_1010102 = 43;
    public static final int RGBA_8888 = 1;
    public static final int RGBA_FP16 = 22;
    public static final int RGBX_8888 = 2;
    public static final int RGB_565 = 4;
    public static final int RGB_888 = 3;
    public static final int RG_1616 = 58;
    public static final int R_16 = 57;
    public static final int R_8 = 56;
    public static final int S_UI8 = 53;
    public static final long USAGE_COMPOSER_OVERLAY = 2048;
    public static final long USAGE_CPU_READ_OFTEN = 3;
    public static final long USAGE_CPU_READ_RARELY = 2;
    public static final long USAGE_CPU_WRITE_OFTEN = 48;
    public static final long USAGE_CPU_WRITE_RARELY = 32;
    public static final long USAGE_FRONT_BUFFER = 4294967296L;
    public static final long USAGE_GPU_COLOR_OUTPUT = 512;
    public static final long USAGE_GPU_CUBE_MAP = 33554432;
    public static final long USAGE_GPU_DATA_BUFFER = 16777216;
    public static final long USAGE_GPU_MIPMAP_COMPLETE = 67108864;
    public static final long USAGE_GPU_SAMPLED_IMAGE = 256;
    public static final long USAGE_PROTECTED_CONTENT = 16384;
    public static final long USAGE_SENSOR_DIRECT_DATA = 8388608;
    public static final long USAGE_VIDEO_ENCODE = 65536;
    public static final int YCBCR_420_888 = 35;
    public static final int YCBCR_P010 = 54;
    private java.lang.Runnable mCleaner;
    private final dalvik.system.CloseGuard mCloseGuard;
    private long mNativeObject;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Format {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Usage {
    }

    private static native long nCreateFromGraphicBuffer(android.graphics.GraphicBuffer graphicBuffer);

    private static native long nCreateHardwareBuffer(int i, int i2, int i3, int i4, long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nEstimateSize(long j);

    @dalvik.annotation.optimization.FastNative
    private static native int nGetFormat(long j);

    @dalvik.annotation.optimization.FastNative
    private static native int nGetHeight(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nGetId(long j);

    @dalvik.annotation.optimization.FastNative
    private static native int nGetLayers(long j);

    private static native long nGetNativeFinalizer();

    @dalvik.annotation.optimization.FastNative
    private static native long nGetUsage(long j);

    @dalvik.annotation.optimization.FastNative
    private static native int nGetWidth(long j);

    private static native boolean nIsSupported(int i, int i2, int i3, int i4, long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nReadHardwareBufferFromParcel(android.os.Parcel parcel);

    private static native void nWriteHardwareBufferToParcel(long j, android.os.Parcel parcel);

    public static android.hardware.HardwareBuffer create(int i, int i2, int i3, int i4, long j) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid width " + i);
        }
        if (i2 <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid height " + i2);
        }
        if (i4 <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid layer count " + i4);
        }
        if (i3 == 33 && i2 != 1) {
            throw new java.lang.IllegalArgumentException("Height must be 1 when using the BLOB format");
        }
        long nCreateHardwareBuffer = nCreateHardwareBuffer(i, i2, i3, i4, j);
        if (nCreateHardwareBuffer == 0) {
            throw new java.lang.IllegalArgumentException("Unable to create a HardwareBuffer, either the dimensions passed were too large, too many image layers were requested, or an invalid set of usage flags or invalid format was passed");
        }
        return new android.hardware.HardwareBuffer(nCreateHardwareBuffer);
    }

    public static boolean isSupported(int i, int i2, int i3, int i4, long j) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid width " + i);
        }
        if (i2 <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid height " + i2);
        }
        if (i4 <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid layer count " + i4);
        }
        if (i3 == 33 && i2 != 1) {
            throw new java.lang.IllegalArgumentException("Height must be 1 when using the BLOB format");
        }
        return nIsSupported(i, i2, i3, i4, j);
    }

    public static android.hardware.HardwareBuffer createFromGraphicBuffer(android.graphics.GraphicBuffer graphicBuffer) {
        return new android.hardware.HardwareBuffer(nCreateFromGraphicBuffer(graphicBuffer));
    }

    private HardwareBuffer(long j) {
        this.mCloseGuard = dalvik.system.CloseGuard.get();
        this.mNativeObject = j;
        this.mCleaner = new libcore.util.NativeAllocationRegistry(android.hardware.HardwareBuffer.class.getClassLoader(), nGetNativeFinalizer(), nEstimateSize(j)).registerNativeAllocation(this, this.mNativeObject);
        this.mCloseGuard.open("HardwareBuffer.close");
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            this.mCloseGuard.warnIfOpen();
            close();
        } finally {
            super.finalize();
        }
    }

    public int getWidth() {
        checkClosed("width");
        return nGetWidth(this.mNativeObject);
    }

    public int getHeight() {
        checkClosed("height");
        return nGetHeight(this.mNativeObject);
    }

    public int getFormat() {
        checkClosed(android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT);
        return nGetFormat(this.mNativeObject);
    }

    public int getLayers() {
        checkClosed("layer count");
        return nGetLayers(this.mNativeObject);
    }

    public long getUsage() {
        checkClosed("usage");
        return nGetUsage(this.mNativeObject);
    }

    public long getId() {
        checkClosed("id");
        return nGetId(this.mNativeObject);
    }

    private void checkClosed(java.lang.String str) {
        if (isClosed()) {
            throw new java.lang.IllegalStateException("This HardwareBuffer has been closed and its " + str + " cannot be obtained.");
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (!isClosed()) {
            this.mCloseGuard.close();
            this.mNativeObject = 0L;
            this.mCleaner.run();
            this.mCleaner = null;
        }
    }

    public boolean isClosed() {
        return this.mNativeObject == 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (isClosed()) {
            throw new java.lang.IllegalStateException("This HardwareBuffer has been closed and cannot be written to a parcel.");
        }
        nWriteHardwareBufferToParcel(this.mNativeObject, parcel);
    }
}
