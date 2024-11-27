package android.media;

/* loaded from: classes2.dex */
public class ImageReader implements java.lang.AutoCloseable {
    private static final int ACQUIRE_MAX_IMAGES = 2;
    private static final int ACQUIRE_NO_BUFS = 1;
    private static final int ACQUIRE_SUCCESS = 0;
    private static final long DETACH_THROWS_ISE_ONLY = 236825255;
    private java.util.List<android.media.Image> mAcquiredImages;
    private final java.lang.Object mCloseLock;
    private final int mDataSpace;
    private final boolean mDetachThrowsIseOnly;
    private int mEstimatedNativeAllocBytes;
    private final int mFormat;
    private final int mHardwareBufferFormat;
    private final int mHeight;
    private boolean mIsReaderValid;
    private android.media.ImageReader.OnImageAvailableListener mListener;
    private java.util.concurrent.Executor mListenerExecutor;
    private android.media.ImageReader.ListenerHandler mListenerHandler;
    private final java.lang.Object mListenerLock;
    private final int mMaxImages;
    private long mNativeContext;
    private final int mNumPlanes;
    private final android.hardware.camera2.MultiResolutionImageReader mParent;
    private android.view.Surface mSurface;
    private final long mUsage;
    private final int mWidth;

    public interface OnImageAvailableListener {
        void onImageAvailable(android.media.ImageReader imageReader);
    }

    private static native void nativeClassInit();

    private native synchronized void nativeClose();

    private static native synchronized android.media.ImageReader.ImagePlane[] nativeCreateImagePlanes(int i, android.graphics.GraphicBuffer graphicBuffer, int i2, int i3, int i4, int i5, int i6, int i7);

    private native synchronized int nativeDetachImage(android.media.Image image, boolean z);

    private native synchronized void nativeDiscardFreeBuffers();

    private native synchronized android.view.Surface nativeGetSurface();

    private native synchronized int nativeImageSetup(android.media.Image image);

    private native synchronized void nativeInit(java.lang.Object obj, int i, int i2, int i3, long j, int i4, int i5);

    private native synchronized void nativeReleaseImage(android.media.Image image);

    private static native synchronized void nativeUnlockGraphicBuffer(android.graphics.GraphicBuffer graphicBuffer);

    public static android.media.ImageReader newInstance(int i, int i2, int i3, int i4) {
        return new android.media.ImageReader(i, i2, i3, i4, i3 == 34 ? 0L : 3L, null);
    }

    public static android.media.ImageReader newInstance(int i, int i2, int i3, int i4, long j) {
        return new android.media.ImageReader(i, i2, i3, i4, j, null);
    }

    public static android.media.ImageReader newInstance(int i, int i2, int i3, int i4, android.hardware.camera2.MultiResolutionImageReader multiResolutionImageReader) {
        return new android.media.ImageReader(i, i2, i3, i4, i3 == 34 ? 0L : 3L, multiResolutionImageReader);
    }

    private void initializeImageReader(int i, int i2, int i3, int i4, long j, int i5, int i6) {
        if (i < 1 || i2 < 1) {
            throw new java.lang.IllegalArgumentException("The image dimensions must be positive");
        }
        if (i4 < 1) {
            throw new java.lang.IllegalArgumentException("Maximum outstanding image count must be at least 1");
        }
        if (i3 == 17) {
            throw new java.lang.IllegalArgumentException("NV21 format is not supported");
        }
        nativeInit(new java.lang.ref.WeakReference(this), i, i2, i4, j, i5, i6);
        this.mIsReaderValid = true;
        this.mSurface = nativeGetSurface();
        this.mEstimatedNativeAllocBytes = android.media.ImageUtils.getEstimatedNativeAllocBytes(i, i2, i3, 1);
        dalvik.system.VMRuntime.getRuntime().registerNativeAllocation(this.mEstimatedNativeAllocBytes);
    }

    private ImageReader(int i, int i2, int i3, int i4, long j, android.hardware.camera2.MultiResolutionImageReader multiResolutionImageReader) {
        this.mDetachThrowsIseOnly = android.app.compat.CompatChanges.isChangeEnabled(DETACH_THROWS_ISE_ONLY);
        this.mListenerLock = new java.lang.Object();
        this.mCloseLock = new java.lang.Object();
        this.mIsReaderValid = false;
        this.mAcquiredImages = new java.util.concurrent.CopyOnWriteArrayList();
        this.mWidth = i;
        this.mHeight = i2;
        this.mFormat = i3;
        this.mUsage = j;
        this.mMaxImages = i4;
        this.mParent = multiResolutionImageReader;
        this.mHardwareBufferFormat = android.media.PublicFormatUtils.getHalFormat(this.mFormat);
        this.mDataSpace = android.media.PublicFormatUtils.getHalDataspace(this.mFormat);
        this.mNumPlanes = android.media.ImageUtils.getNumPlanesForFormat(this.mFormat);
        initializeImageReader(i, i2, i3, i4, j, this.mHardwareBufferFormat, this.mDataSpace);
    }

    private ImageReader(int i, int i2, int i3, long j, android.hardware.camera2.MultiResolutionImageReader multiResolutionImageReader, int i4, int i5) {
        this.mDetachThrowsIseOnly = android.app.compat.CompatChanges.isChangeEnabled(DETACH_THROWS_ISE_ONLY);
        this.mListenerLock = new java.lang.Object();
        this.mCloseLock = new java.lang.Object();
        this.mIsReaderValid = false;
        this.mAcquiredImages = new java.util.concurrent.CopyOnWriteArrayList();
        this.mWidth = i;
        this.mHeight = i2;
        this.mUsage = j;
        this.mMaxImages = i3;
        this.mParent = multiResolutionImageReader;
        this.mHardwareBufferFormat = i4;
        this.mDataSpace = i5;
        this.mNumPlanes = android.media.ImageUtils.getNumPlanesForHardwareBufferFormat(this.mHardwareBufferFormat);
        this.mFormat = android.media.PublicFormatUtils.getPublicFormat(i4, i5);
        initializeImageReader(i, i2, this.mFormat, i3, j, i4, i5);
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getImageFormat() {
        return this.mFormat;
    }

    public int getHardwareBufferFormat() {
        return this.mHardwareBufferFormat;
    }

    public int getDataSpace() {
        return this.mDataSpace;
    }

    public int getMaxImages() {
        return this.mMaxImages;
    }

    public long getUsage() {
        return this.mUsage;
    }

    public android.view.Surface getSurface() {
        return this.mSurface;
    }

    public android.media.Image acquireLatestImage() {
        android.media.Image acquireNextImage = acquireNextImage();
        if (acquireNextImage == null) {
            return null;
        }
        while (true) {
            try {
                android.media.Image acquireNextImageNoThrowISE = acquireNextImageNoThrowISE();
                if (acquireNextImageNoThrowISE == null) {
                    break;
                }
                acquireNextImage.close();
                acquireNextImage = acquireNextImageNoThrowISE;
            } catch (java.lang.Throwable th) {
                if (acquireNextImage != null) {
                    acquireNextImage.close();
                }
                if (this.mParent != null) {
                    this.mParent.flushOther(this);
                }
                throw th;
            }
        }
        if (this.mParent != null) {
            this.mParent.flushOther(this);
        }
        return acquireNextImage;
    }

    public android.media.Image acquireNextImageNoThrowISE() {
        android.media.ImageReader.SurfaceImage surfaceImage = new android.media.ImageReader.SurfaceImage(this.mFormat);
        if (acquireNextSurfaceImage(surfaceImage) == 0) {
            return surfaceImage;
        }
        return null;
    }

    private int acquireNextSurfaceImage(android.media.ImageReader.SurfaceImage surfaceImage) {
        int i;
        synchronized (this.mCloseLock) {
            if (!this.mIsReaderValid) {
                i = 1;
            } else {
                i = nativeImageSetup(surfaceImage);
            }
            switch (i) {
                case 0:
                    surfaceImage.mIsImageValid = true;
                    break;
                case 1:
                case 2:
                    break;
                default:
                    throw new java.lang.AssertionError("Unknown nativeImageSetup return code " + i);
            }
            if (i == 0) {
                this.mAcquiredImages.add(surfaceImage);
            }
        }
        return i;
    }

    public android.media.Image acquireNextImage() {
        android.media.ImageReader.SurfaceImage surfaceImage = new android.media.ImageReader.SurfaceImage(this.mFormat);
        int acquireNextSurfaceImage = acquireNextSurfaceImage(surfaceImage);
        switch (acquireNextSurfaceImage) {
            case 0:
                return surfaceImage;
            case 1:
                return null;
            case 2:
                throw new java.lang.IllegalStateException(java.lang.String.format("maxImages (%d) has already been acquired, call #close before acquiring more.", java.lang.Integer.valueOf(this.mMaxImages)));
            default:
                throw new java.lang.AssertionError("Unknown nativeImageSetup return code " + acquireNextSurfaceImage);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseImage(android.media.Image image) {
        if (!(image instanceof android.media.ImageReader.SurfaceImage)) {
            throw new java.lang.IllegalArgumentException("This image was not produced by an ImageReader");
        }
        android.media.ImageReader.SurfaceImage surfaceImage = (android.media.ImageReader.SurfaceImage) image;
        if (!surfaceImage.mIsImageValid) {
            return;
        }
        if (surfaceImage.getReader() != this || !this.mAcquiredImages.contains(image)) {
            throw new java.lang.IllegalArgumentException("This image was not produced by this ImageReader");
        }
        surfaceImage.clearSurfacePlanes();
        nativeReleaseImage(image);
        surfaceImage.mIsImageValid = false;
        this.mAcquiredImages.remove(image);
    }

    public void setOnImageAvailableListener(android.media.ImageReader.OnImageAvailableListener onImageAvailableListener, android.os.Handler handler) {
        synchronized (this.mListenerLock) {
            if (onImageAvailableListener != null) {
                android.os.Looper looper = handler != null ? handler.getLooper() : android.os.Looper.myLooper();
                if (looper == null) {
                    throw new java.lang.IllegalArgumentException("handler is null but the current thread is not a looper");
                }
                if (this.mListenerHandler == null || this.mListenerHandler.getLooper() != looper) {
                    this.mListenerHandler = new android.media.ImageReader.ListenerHandler(looper);
                    this.mListenerExecutor = new android.media.ImageReader.HandlerExecutor(this.mListenerHandler);
                }
            } else {
                this.mListenerHandler = null;
                this.mListenerExecutor = null;
            }
            this.mListener = onImageAvailableListener;
        }
    }

    public void setOnImageAvailableListenerWithExecutor(android.media.ImageReader.OnImageAvailableListener onImageAvailableListener, java.util.concurrent.Executor executor) {
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("executor must not be null");
        }
        synchronized (this.mListenerLock) {
            this.mListenerExecutor = executor;
            this.mListener = onImageAvailableListener;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        setOnImageAvailableListener(null, null);
        if (this.mSurface != null) {
            this.mSurface.release();
        }
        synchronized (this.mCloseLock) {
            this.mIsReaderValid = false;
            java.util.Iterator<android.media.Image> it = this.mAcquiredImages.iterator();
            while (it.hasNext()) {
                it.next().close();
            }
            this.mAcquiredImages.clear();
            nativeClose();
            if (this.mEstimatedNativeAllocBytes > 0) {
                dalvik.system.VMRuntime.getRuntime().registerNativeFree(this.mEstimatedNativeAllocBytes);
                this.mEstimatedNativeAllocBytes = 0;
            }
        }
    }

    public void discardFreeBuffers() {
        synchronized (this.mCloseLock) {
            nativeDiscardFreeBuffers();
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    public void detachImage(android.media.Image image) {
        if (image == null) {
            throw new java.lang.IllegalArgumentException("input image must not be null");
        }
        if (!isImageOwnedbyMe(image)) {
            throw new java.lang.IllegalArgumentException("Trying to detach an image that is not owned by this ImageReader");
        }
        android.media.ImageReader.SurfaceImage surfaceImage = (android.media.ImageReader.SurfaceImage) image;
        surfaceImage.throwISEIfImageIsInvalid();
        if (surfaceImage.isAttachable()) {
            throw new java.lang.IllegalStateException("Image was already detached from this ImageReader");
        }
        nativeDetachImage(image, this.mDetachThrowsIseOnly);
        surfaceImage.clearSurfacePlanes();
        surfaceImage.mPlanes = null;
        surfaceImage.setDetached(true);
    }

    private boolean isImageOwnedbyMe(android.media.Image image) {
        return (image instanceof android.media.ImageReader.SurfaceImage) && ((android.media.ImageReader.SurfaceImage) image).getReader() == this;
    }

    private static void postEventFromNative(java.lang.Object obj) {
        java.util.concurrent.Executor executor;
        final android.media.ImageReader.OnImageAvailableListener onImageAvailableListener;
        boolean z;
        final android.media.ImageReader imageReader = (android.media.ImageReader) ((java.lang.ref.WeakReference) obj).get();
        if (imageReader == null) {
            return;
        }
        synchronized (imageReader.mListenerLock) {
            executor = imageReader.mListenerExecutor;
            onImageAvailableListener = imageReader.mListener;
        }
        synchronized (imageReader.mCloseLock) {
            z = imageReader.mIsReaderValid;
        }
        if (executor != null && onImageAvailableListener != null && z) {
            executor.execute(new java.lang.Runnable() { // from class: android.media.ImageReader.1
                @Override // java.lang.Runnable
                public void run() {
                    android.media.ImageReader.OnImageAvailableListener.this.onImageAvailable(imageReader);
                }
            });
        }
    }

    public static final class Builder {
        private int mHeight;
        private int mWidth;
        private int mMaxImages = 1;
        private int mImageFormat = 0;
        private int mHardwareBufferFormat = 1;
        private int mDataSpace = 0;
        private long mUsage = 3;
        private boolean mUseLegacyImageFormat = false;

        public Builder(int i, int i2) {
            this.mWidth = i;
            this.mHeight = i2;
        }

        public android.media.ImageReader.Builder setMaxImages(int i) {
            this.mMaxImages = i;
            return this;
        }

        public android.media.ImageReader.Builder setUsage(long j) {
            this.mUsage = j;
            return this;
        }

        public android.media.ImageReader.Builder setImageFormat(int i) {
            this.mImageFormat = i;
            this.mUseLegacyImageFormat = true;
            this.mHardwareBufferFormat = 1;
            this.mDataSpace = 0;
            return this;
        }

        public android.media.ImageReader.Builder setDefaultHardwareBufferFormat(int i) {
            this.mHardwareBufferFormat = i;
            this.mUseLegacyImageFormat = false;
            this.mImageFormat = 0;
            return this;
        }

        public android.media.ImageReader.Builder setDefaultDataSpace(int i) {
            this.mDataSpace = i;
            this.mUseLegacyImageFormat = false;
            this.mImageFormat = 0;
            return this;
        }

        public android.media.ImageReader build() {
            if (this.mUseLegacyImageFormat) {
                return new android.media.ImageReader(this.mWidth, this.mHeight, this.mImageFormat, this.mMaxImages, this.mUsage, (android.hardware.camera2.MultiResolutionImageReader) null);
            }
            return new android.media.ImageReader(this.mWidth, this.mHeight, this.mMaxImages, this.mUsage, null, this.mHardwareBufferFormat, this.mDataSpace);
        }
    }

    private final class ListenerHandler extends android.os.Handler {
        public ListenerHandler(android.os.Looper looper) {
            super(looper, null, true);
        }
    }

    private final class HandlerExecutor implements java.util.concurrent.Executor {
        private final android.os.Handler mHandler;

        public HandlerExecutor(android.os.Handler handler) {
            this.mHandler = (android.os.Handler) java.util.Objects.requireNonNull(handler);
        }

        @Override // java.util.concurrent.Executor
        public void execute(java.lang.Runnable runnable) {
            this.mHandler.post(runnable);
        }
    }

    private class SurfaceImage extends android.media.Image {
        private int mDataSpace;
        private int mFormat;
        private int mHardwareBufferFormat;
        private long mNativeBuffer;
        private android.media.ImageReader.SurfaceImage.SurfacePlane[] mPlanes;
        private int mScalingMode;
        private long mTimestamp;
        private int mTransform;
        private final java.lang.Object mCloseLock = new java.lang.Object();
        private java.util.concurrent.atomic.AtomicBoolean mIsDetached = new java.util.concurrent.atomic.AtomicBoolean(false);

        private native synchronized android.media.ImageReader.SurfaceImage.SurfacePlane[] nativeCreatePlanes(int i, int i2, long j);

        private native synchronized int nativeGetFenceFd();

        private native synchronized int nativeGetFormat(int i);

        private native synchronized android.hardware.HardwareBuffer nativeGetHardwareBuffer();

        private native synchronized int nativeGetHeight();

        private native synchronized int nativeGetWidth();

        public SurfaceImage(int i) {
            this.mFormat = 0;
            this.mHardwareBufferFormat = 1;
            this.mDataSpace = 0;
            this.mFormat = i;
            this.mHardwareBufferFormat = android.media.ImageReader.this.mHardwareBufferFormat;
            this.mDataSpace = android.media.ImageReader.this.mDataSpace;
        }

        @Override // android.media.Image, java.lang.AutoCloseable
        public void close() {
            synchronized (this.mCloseLock) {
                android.media.ImageReader.this.releaseImage(this);
            }
        }

        public android.media.ImageReader getReader() {
            return android.media.ImageReader.this;
        }

        @Override // android.media.Image
        public int getFormat() {
            throwISEIfImageIsInvalid();
            int imageFormat = android.media.ImageReader.this.getImageFormat();
            if (imageFormat != 34) {
                imageFormat = nativeGetFormat(imageFormat);
            }
            this.mFormat = imageFormat;
            return this.mFormat;
        }

        @Override // android.media.Image
        public int getWidth() {
            throwISEIfImageIsInvalid();
            switch (getFormat()) {
                case 36:
                case 256:
                case 257:
                case 4101:
                case android.graphics.ImageFormat.HEIC /* 1212500294 */:
                case android.graphics.ImageFormat.DEPTH_JPEG /* 1768253795 */:
                    return android.media.ImageReader.this.getWidth();
                default:
                    return nativeGetWidth();
            }
        }

        @Override // android.media.Image
        public int getHeight() {
            throwISEIfImageIsInvalid();
            switch (getFormat()) {
                case 36:
                case 256:
                case 257:
                case 4101:
                case android.graphics.ImageFormat.HEIC /* 1212500294 */:
                case android.graphics.ImageFormat.DEPTH_JPEG /* 1768253795 */:
                    return android.media.ImageReader.this.getHeight();
                default:
                    return nativeGetHeight();
            }
        }

        @Override // android.media.Image
        public long getTimestamp() {
            throwISEIfImageIsInvalid();
            return this.mTimestamp;
        }

        @Override // android.media.Image
        public int getTransform() {
            throwISEIfImageIsInvalid();
            return this.mTransform;
        }

        @Override // android.media.Image
        public int getScalingMode() {
            throwISEIfImageIsInvalid();
            return this.mScalingMode;
        }

        @Override // android.media.Image
        public int getPlaneCount() {
            throwISEIfImageIsInvalid();
            return android.media.ImageReader.this.mNumPlanes;
        }

        @Override // android.media.Image
        public android.hardware.SyncFence getFence() throws java.io.IOException {
            throwISEIfImageIsInvalid();
            if (nativeGetFenceFd() != -1) {
                return android.hardware.SyncFence.create(android.os.ParcelFileDescriptor.fromFd(nativeGetFenceFd()));
            }
            return android.hardware.SyncFence.createEmpty();
        }

        @Override // android.media.Image
        public android.hardware.HardwareBuffer getHardwareBuffer() {
            throwISEIfImageIsInvalid();
            return nativeGetHardwareBuffer();
        }

        @Override // android.media.Image
        public int getDataSpace() {
            throwISEIfImageIsInvalid();
            return this.mDataSpace;
        }

        @Override // android.media.Image
        public void setTimestamp(long j) {
            throwISEIfImageIsInvalid();
            this.mTimestamp = j;
        }

        @Override // android.media.Image
        public android.media.Image.Plane[] getPlanes() {
            throwISEIfImageIsInvalid();
            if (this.mPlanes == null) {
                this.mPlanes = nativeCreatePlanes(android.media.ImageReader.this.mNumPlanes, android.media.ImageReader.this.mHardwareBufferFormat, android.media.ImageReader.this.mUsage);
            }
            return (android.media.Image.Plane[]) this.mPlanes.clone();
        }

        protected final void finalize() throws java.lang.Throwable {
            try {
                close();
            } finally {
                super.finalize();
            }
        }

        @Override // android.media.Image
        public boolean isAttachable() {
            throwISEIfImageIsInvalid();
            return this.mIsDetached.get();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // android.media.Image
        public android.media.ImageReader getOwner() {
            throwISEIfImageIsInvalid();
            return android.media.ImageReader.this;
        }

        @Override // android.media.Image
        long getNativeContext() {
            throwISEIfImageIsInvalid();
            return this.mNativeBuffer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDetached(boolean z) {
            throwISEIfImageIsInvalid();
            this.mIsDetached.getAndSet(z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSurfacePlanes() {
            if (this.mIsImageValid && this.mPlanes != null) {
                for (int i = 0; i < this.mPlanes.length; i++) {
                    if (this.mPlanes[i] != null) {
                        this.mPlanes[i].clearBuffer();
                        this.mPlanes[i] = null;
                    }
                }
            }
        }

        private class SurfacePlane extends android.media.Image.Plane {
            private java.nio.ByteBuffer mBuffer;
            private final int mPixelStride;
            private final int mRowStride;

            private SurfacePlane(int i, int i2, java.nio.ByteBuffer byteBuffer) {
                this.mRowStride = i;
                this.mPixelStride = i2;
                this.mBuffer = byteBuffer;
                this.mBuffer.order(java.nio.ByteOrder.nativeOrder());
            }

            @Override // android.media.Image.Plane
            public java.nio.ByteBuffer getBuffer() {
                android.media.ImageReader.SurfaceImage.this.throwISEIfImageIsInvalid();
                return this.mBuffer;
            }

            @Override // android.media.Image.Plane
            public int getPixelStride() {
                android.media.ImageReader.SurfaceImage.this.throwISEIfImageIsInvalid();
                if (android.media.ImageReader.this.mFormat == 36) {
                    throw new java.lang.UnsupportedOperationException("getPixelStride is not supported for RAW_PRIVATE plane");
                }
                return this.mPixelStride;
            }

            @Override // android.media.Image.Plane
            public int getRowStride() {
                android.media.ImageReader.SurfaceImage.this.throwISEIfImageIsInvalid();
                if (android.media.ImageReader.this.mFormat == 36) {
                    throw new java.lang.UnsupportedOperationException("getRowStride is not supported for RAW_PRIVATE plane");
                }
                return this.mRowStride;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void clearBuffer() {
                if (this.mBuffer == null) {
                    return;
                }
                if (this.mBuffer.isDirect()) {
                    java.nio.NioUtils.freeDirectBuffer(this.mBuffer);
                }
                this.mBuffer = null;
            }
        }
    }

    public static class ImagePlane extends android.media.Image.Plane {
        private java.nio.ByteBuffer mBuffer;
        private final int mPixelStride;
        private final int mRowStride;

        private ImagePlane(int i, int i2, java.nio.ByteBuffer byteBuffer) {
            this.mRowStride = i;
            this.mPixelStride = i2;
            this.mBuffer = byteBuffer;
            this.mBuffer.order(java.nio.ByteOrder.nativeOrder());
        }

        @Override // android.media.Image.Plane
        public java.nio.ByteBuffer getBuffer() {
            return this.mBuffer;
        }

        @Override // android.media.Image.Plane
        public int getPixelStride() {
            return this.mPixelStride;
        }

        @Override // android.media.Image.Plane
        public int getRowStride() {
            return this.mRowStride;
        }
    }

    public static android.media.ImageReader.ImagePlane[] initializeImagePlanes(int i, android.graphics.GraphicBuffer graphicBuffer, int i2, int i3, long j, int i4, int i5, android.graphics.Rect rect) {
        return nativeCreateImagePlanes(i, graphicBuffer, i2, i3, rect.left, rect.top, rect.right, rect.bottom);
    }

    public static void unlockGraphicBuffer(android.graphics.GraphicBuffer graphicBuffer) {
        nativeUnlockGraphicBuffer(graphicBuffer);
    }

    static {
        java.lang.System.loadLibrary("media_jni");
        nativeClassInit();
    }
}
