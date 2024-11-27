package android.hardware;

/* loaded from: classes.dex */
public final class SensorDirectChannel implements java.nio.channels.Channel {
    public static final int RATE_FAST = 2;
    public static final int RATE_NORMAL = 1;
    public static final int RATE_STOP = 0;
    public static final int RATE_VERY_FAST = 3;
    public static final int TYPE_HARDWARE_BUFFER = 2;
    public static final int TYPE_MEMORY_FILE = 1;
    private final android.hardware.SensorManager mManager;
    private final int mNativeHandle;
    private final long mSize;
    private final int mType;
    private final java.util.concurrent.atomic.AtomicBoolean mClosed = new java.util.concurrent.atomic.AtomicBoolean();
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MemoryType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RateLevel {
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return !this.mClosed.get();
    }

    @java.lang.Deprecated
    public boolean isValid() {
        return isOpen();
    }

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.mClosed.compareAndSet(false, true)) {
            this.mCloseGuard.close();
            this.mManager.destroyDirectChannel(this);
        }
    }

    public int configure(android.hardware.Sensor sensor, int i) {
        return this.mManager.configureDirectChannelImpl(this, sensor, i);
    }

    SensorDirectChannel(android.hardware.SensorManager sensorManager, int i, int i2, long j) {
        this.mManager = sensorManager;
        this.mNativeHandle = i;
        this.mType = i2;
        this.mSize = j;
        this.mCloseGuard.open("SensorDirectChannel");
    }

    int getNativeHandle() {
        return this.mNativeHandle;
    }

    static long[] encodeData(android.os.MemoryFile memoryFile) {
        int i;
        try {
            i = memoryFile.getFileDescriptor().getInt$();
        } catch (java.io.IOException e) {
            i = -1;
        }
        return new long[]{1, 0, i};
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
}
