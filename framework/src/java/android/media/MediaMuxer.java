package android.media;

/* loaded from: classes2.dex */
public final class MediaMuxer {
    private static final int MUXER_STATE_INITIALIZED = 0;
    private static final int MUXER_STATE_STARTED = 1;
    private static final int MUXER_STATE_STOPPED = 2;
    private static final int MUXER_STATE_UNINITIALIZED = -1;
    private long mNativeObject;
    private int mState = -1;
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private int mLastTrackIndex = -1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Format {
    }

    private static native int nativeAddTrack(long j, java.lang.String[] strArr, java.lang.Object[] objArr);

    private static native void nativeRelease(long j);

    private static native void nativeSetLocation(long j, int i, int i2);

    private static native void nativeSetOrientationHint(long j, int i);

    private static native long nativeSetup(java.io.FileDescriptor fileDescriptor, int i) throws java.lang.IllegalArgumentException, java.io.IOException;

    private static native void nativeStart(long j);

    private static native void nativeStop(long j);

    private static native void nativeWriteSampleData(long j, int i, java.nio.ByteBuffer byteBuffer, int i2, int i3, long j2, int i4);

    static {
        java.lang.System.loadLibrary("media_jni");
    }

    public static final class OutputFormat {
        public static final int MUXER_OUTPUT_3GPP = 2;
        public static final int MUXER_OUTPUT_FIRST = 0;
        public static final int MUXER_OUTPUT_HEIF = 3;
        public static final int MUXER_OUTPUT_LAST = 4;
        public static final int MUXER_OUTPUT_MPEG_4 = 0;
        public static final int MUXER_OUTPUT_OGG = 4;
        public static final int MUXER_OUTPUT_WEBM = 1;

        private OutputFormat() {
        }
    }

    private java.lang.String convertMuxerStateCodeToString(int i) {
        switch (i) {
            case -1:
                return "UNINITIALIZED";
            case 0:
                return "INITIALIZED";
            case 1:
                return "STARTED";
            case 2:
                return "STOPPED";
            default:
                return "UNKNOWN";
        }
    }

    public MediaMuxer(java.lang.String str, int i) throws java.io.IOException {
        java.io.RandomAccessFile randomAccessFile;
        if (str == null) {
            throw new java.lang.IllegalArgumentException("path must not be null");
        }
        java.io.RandomAccessFile randomAccessFile2 = null;
        try {
            randomAccessFile = new java.io.RandomAccessFile(str, "rws");
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            randomAccessFile.setLength(0L);
            setUpMediaMuxer(randomAccessFile.getFD(), i);
            randomAccessFile.close();
        } catch (java.lang.Throwable th2) {
            th = th2;
            randomAccessFile2 = randomAccessFile;
            if (randomAccessFile2 != null) {
                randomAccessFile2.close();
            }
            throw th;
        }
    }

    public MediaMuxer(java.io.FileDescriptor fileDescriptor, int i) throws java.io.IOException {
        setUpMediaMuxer(fileDescriptor, i);
    }

    private void setUpMediaMuxer(java.io.FileDescriptor fileDescriptor, int i) throws java.io.IOException {
        if (i < 0 || i > 4) {
            throw new java.lang.IllegalArgumentException("format: " + i + " is invalid");
        }
        this.mNativeObject = nativeSetup(fileDescriptor, i);
        this.mState = 0;
        this.mCloseGuard.open("release");
    }

    public void setOrientationHint(int i) {
        if (i != 0 && i != 90 && i != 180 && i != 270) {
            throw new java.lang.IllegalArgumentException("Unsupported angle: " + i);
        }
        if (this.mState == 0) {
            nativeSetOrientationHint(this.mNativeObject, i);
            return;
        }
        throw new java.lang.IllegalStateException("Can't set rotation degrees due to wrong state(" + convertMuxerStateCodeToString(this.mState) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }

    public void setLocation(float f, float f2) {
        int round = java.lang.Math.round(f * 10000.0f);
        int round2 = java.lang.Math.round(10000.0f * f2);
        if (round > 900000 || round < -900000) {
            throw new java.lang.IllegalArgumentException("Latitude: " + f + " out of range.");
        }
        if (round2 > 1800000 || round2 < -1800000) {
            throw new java.lang.IllegalArgumentException("Longitude: " + f2 + " out of range");
        }
        if (this.mState == 0 && this.mNativeObject != 0) {
            nativeSetLocation(this.mNativeObject, round, round2);
            return;
        }
        throw new java.lang.IllegalStateException("Can't set location due to wrong state(" + convertMuxerStateCodeToString(this.mState) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }

    public void start() {
        if (this.mNativeObject == 0) {
            throw new java.lang.IllegalStateException("Muxer has been released!");
        }
        if (this.mState == 0) {
            nativeStart(this.mNativeObject);
            this.mState = 1;
            return;
        }
        throw new java.lang.IllegalStateException("Can't start due to wrong state(" + convertMuxerStateCodeToString(this.mState) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }

    public void stop() {
        if (this.mState == 1) {
            try {
                try {
                    nativeStop(this.mNativeObject);
                    return;
                } catch (java.lang.Exception e) {
                    throw e;
                }
            } finally {
                this.mState = 2;
            }
        }
        throw new java.lang.IllegalStateException("Can't stop due to wrong state(" + convertMuxerStateCodeToString(this.mState) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            if (this.mNativeObject != 0) {
                nativeRelease(this.mNativeObject);
                this.mNativeObject = 0L;
            }
        } finally {
            super.finalize();
        }
    }

    public int addTrack(android.media.MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            throw new java.lang.IllegalArgumentException("format must not be null.");
        }
        if (this.mState != 0) {
            throw new java.lang.IllegalStateException("Muxer is not initialized.");
        }
        if (this.mNativeObject == 0) {
            throw new java.lang.IllegalStateException("Muxer has been released!");
        }
        java.util.Map<java.lang.String, java.lang.Object> map = mediaFormat.getMap();
        int size = map.size();
        if (size > 0) {
            java.lang.String[] strArr = new java.lang.String[size];
            java.lang.Object[] objArr = new java.lang.Object[size];
            int i = 0;
            for (java.util.Map.Entry<java.lang.String, java.lang.Object> entry : map.entrySet()) {
                strArr[i] = entry.getKey();
                objArr[i] = entry.getValue();
                i++;
            }
            int nativeAddTrack = nativeAddTrack(this.mNativeObject, strArr, objArr);
            if (this.mLastTrackIndex >= nativeAddTrack) {
                throw new java.lang.IllegalArgumentException("Invalid format.");
            }
            this.mLastTrackIndex = nativeAddTrack;
            return nativeAddTrack;
        }
        throw new java.lang.IllegalArgumentException("format must not be empty.");
    }

    public void writeSampleData(int i, java.nio.ByteBuffer byteBuffer, android.media.MediaCodec.BufferInfo bufferInfo) {
        if (i < 0 || i > this.mLastTrackIndex) {
            throw new java.lang.IllegalArgumentException("trackIndex is invalid");
        }
        if (byteBuffer == null) {
            throw new java.lang.IllegalArgumentException("byteBuffer must not be null");
        }
        if (bufferInfo == null) {
            throw new java.lang.IllegalArgumentException("bufferInfo must not be null");
        }
        if (bufferInfo.size < 0 || bufferInfo.offset < 0 || bufferInfo.offset + bufferInfo.size > byteBuffer.capacity()) {
            throw new java.lang.IllegalArgumentException("bufferInfo must specify a valid buffer offset and size");
        }
        if (this.mNativeObject == 0) {
            throw new java.lang.IllegalStateException("Muxer has been released!");
        }
        if (this.mState != 1) {
            throw new java.lang.IllegalStateException("Can't write, muxer is not started");
        }
        nativeWriteSampleData(this.mNativeObject, i, byteBuffer, bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
    }

    public void release() {
        if (this.mState == 1) {
            stop();
        }
        if (this.mNativeObject != 0) {
            nativeRelease(this.mNativeObject);
            this.mNativeObject = 0L;
            this.mCloseGuard.close();
        }
        this.mState = -1;
    }
}
