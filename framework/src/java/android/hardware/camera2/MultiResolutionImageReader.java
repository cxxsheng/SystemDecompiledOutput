package android.hardware.camera2;

/* loaded from: classes.dex */
public class MultiResolutionImageReader implements java.lang.AutoCloseable {
    private static final java.lang.String TAG = "MultiResolutionImageReader";
    private final int mFormat;
    private final int mMaxImages;
    private final android.media.ImageReader[] mReaders;
    private final android.hardware.camera2.params.MultiResolutionStreamInfo[] mStreamInfo;

    public MultiResolutionImageReader(java.util.Collection<android.hardware.camera2.params.MultiResolutionStreamInfo> collection, int i, int i2) {
        this.mFormat = i;
        this.mMaxImages = i2;
        if (collection == null || collection.size() <= 1) {
            throw new java.lang.IllegalArgumentException("The streams info collection must contain at least 2 entries");
        }
        if (this.mMaxImages < 1) {
            throw new java.lang.IllegalArgumentException("Maximum outstanding image count must be at least 1");
        }
        if (i == 17) {
            throw new java.lang.IllegalArgumentException("NV21 format is not supported");
        }
        int size = collection.size();
        this.mReaders = new android.media.ImageReader[size];
        this.mStreamInfo = new android.hardware.camera2.params.MultiResolutionStreamInfo[size];
        int i3 = 0;
        for (android.hardware.camera2.params.MultiResolutionStreamInfo multiResolutionStreamInfo : collection) {
            this.mReaders[i3] = android.media.ImageReader.newInstance(multiResolutionStreamInfo.getWidth(), multiResolutionStreamInfo.getHeight(), i, i2);
            this.mStreamInfo[i3] = multiResolutionStreamInfo;
            i3++;
        }
    }

    public MultiResolutionImageReader(java.util.Collection<android.hardware.camera2.params.MultiResolutionStreamInfo> collection, int i, int i2, long j) {
        this.mFormat = i;
        this.mMaxImages = i2;
        if (collection == null || collection.size() <= 1) {
            throw new java.lang.IllegalArgumentException("The streams info collection must contain at least 2 entries");
        }
        if (this.mMaxImages < 1) {
            throw new java.lang.IllegalArgumentException("Maximum outstanding image count must be at least 1");
        }
        if (i == 17) {
            throw new java.lang.IllegalArgumentException("NV21 format is not supported");
        }
        int size = collection.size();
        this.mReaders = new android.media.ImageReader[size];
        this.mStreamInfo = new android.hardware.camera2.params.MultiResolutionStreamInfo[size];
        int i3 = 0;
        for (android.hardware.camera2.params.MultiResolutionStreamInfo multiResolutionStreamInfo : collection) {
            this.mReaders[i3] = android.media.ImageReader.newInstance(multiResolutionStreamInfo.getWidth(), multiResolutionStreamInfo.getHeight(), i, i2, j);
            this.mStreamInfo[i3] = multiResolutionStreamInfo;
            i3++;
        }
    }

    public void setOnImageAvailableListener(android.media.ImageReader.OnImageAvailableListener onImageAvailableListener, java.util.concurrent.Executor executor) {
        for (int i = 0; i < this.mReaders.length; i++) {
            this.mReaders[i].setOnImageAvailableListenerWithExecutor(onImageAvailableListener, executor);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        flush();
        for (int i = 0; i < this.mReaders.length; i++) {
            this.mReaders[i].close();
        }
    }

    protected void finalize() {
        close();
    }

    public void flush() {
        flushOther(null);
    }

    public void flushOther(android.media.ImageReader imageReader) {
        for (int i = 0; i < this.mReaders.length; i++) {
            if (imageReader == null || imageReader != this.mReaders[i]) {
                while (true) {
                    android.media.Image acquireNextImageNoThrowISE = this.mReaders[i].acquireNextImageNoThrowISE();
                    if (acquireNextImageNoThrowISE == null) {
                        break;
                    } else {
                        acquireNextImageNoThrowISE.close();
                    }
                }
            }
        }
    }

    public android.media.ImageReader[] getReaders() {
        return this.mReaders;
    }

    public android.view.Surface getSurface() {
        int width = this.mReaders[0].getWidth() * this.mReaders[0].getHeight();
        android.view.Surface surface = this.mReaders[0].getSurface();
        for (int i = 1; i < this.mReaders.length; i++) {
            int width2 = this.mReaders[i].getWidth() * this.mReaders[i].getHeight();
            if (width2 < width) {
                surface = this.mReaders[i].getSurface();
                width = width2;
            }
        }
        return surface;
    }

    public android.hardware.camera2.params.MultiResolutionStreamInfo getStreamInfoForImageReader(android.media.ImageReader imageReader) {
        for (int i = 0; i < this.mReaders.length; i++) {
            if (imageReader == this.mReaders[i]) {
                return this.mStreamInfo[i];
            }
        }
        throw new java.lang.IllegalArgumentException("ImageReader doesn't belong to this multi-resolution imagereader");
    }
}
