package android.media;

/* loaded from: classes2.dex */
public class ImageWriter implements java.lang.AutoCloseable {
    private final java.lang.Object mCloseLock;
    private int mDataSpace;
    private java.util.List<android.media.Image> mDequeuedImages;
    private int mEstimatedNativeAllocBytes;
    private int mHardwareBufferFormat;
    private int mHeight;
    private boolean mIsWriterValid;
    private android.media.ImageWriter.OnImageReleasedListener mListener;
    private android.media.ImageWriter.ListenerHandler mListenerHandler;
    private final java.lang.Object mListenerLock;
    private final int mMaxImages;
    private long mNativeContext;
    private long mUsage;
    private int mWidth;
    private int mWriterFormat;

    public interface OnImageReleasedListener {
        void onImageReleased(android.media.ImageWriter imageWriter);
    }

    private native synchronized void cancelImage(long j, android.media.Image image);

    private native synchronized int nativeAttachAndQueueGraphicBuffer(long j, android.graphics.GraphicBuffer graphicBuffer, int i, long j2, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    private native synchronized int nativeAttachAndQueueImage(long j, long j2, int i, long j3, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    private static native void nativeClassInit();

    private native synchronized void nativeClose(long j);

    private native synchronized void nativeDequeueInputImage(long j, android.media.Image image);

    private native synchronized long nativeInit(java.lang.Object obj, android.view.Surface surface, int i, int i2, int i3, boolean z, int i4, int i5, long j);

    private native synchronized void nativeQueueInputImage(long j, android.media.Image image, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7);

    public static android.media.ImageWriter newInstance(android.view.Surface surface, int i) {
        return new android.media.ImageWriter(surface, i, true, 0, -1, -1);
    }

    public static android.media.ImageWriter newInstance(android.view.Surface surface, int i, int i2, int i3, int i4) {
        if (!android.graphics.ImageFormat.isPublicFormat(i2) && !android.graphics.PixelFormat.isPublicFormat(i2)) {
            throw new java.lang.IllegalArgumentException("Invalid format is specified: " + i2);
        }
        return new android.media.ImageWriter(surface, i, false, i2, i3, i4);
    }

    public static android.media.ImageWriter newInstance(android.view.Surface surface, int i, int i2) {
        if (!android.graphics.ImageFormat.isPublicFormat(i2) && !android.graphics.PixelFormat.isPublicFormat(i2)) {
            throw new java.lang.IllegalArgumentException("Invalid format is specified: " + i2);
        }
        return new android.media.ImageWriter(surface, i, false, i2, -1, -1);
    }

    private void initializeImageWriter(android.view.Surface surface, int i, boolean z, int i2, int i3, int i4, int i5, int i6, long j) {
        int i7;
        if (surface != null && i >= 1) {
            this.mNativeContext = nativeInit(new java.lang.ref.WeakReference(this), surface, i, i5, i6, z, i3, i4, j);
            if (!z) {
                i7 = i2;
            } else {
                int surfaceFormat = android.hardware.camera2.utils.SurfaceUtils.getSurfaceFormat(surface);
                this.mHardwareBufferFormat = surfaceFormat;
                int surfaceDataspace = android.hardware.camera2.utils.SurfaceUtils.getSurfaceDataspace(surface);
                this.mDataSpace = surfaceDataspace;
                i7 = android.media.PublicFormatUtils.getPublicFormat(surfaceFormat, surfaceDataspace);
            }
            android.util.Size surfaceSize = android.hardware.camera2.utils.SurfaceUtils.getSurfaceSize(surface);
            int i8 = i5;
            if (i8 == -1) {
                i8 = surfaceSize.getWidth();
            }
            this.mWidth = i8;
            this.mHeight = i6 == -1 ? surfaceSize.getHeight() : i6;
            this.mEstimatedNativeAllocBytes = android.media.ImageUtils.getEstimatedNativeAllocBytes(this.mWidth, this.mHeight, i7, 1);
            dalvik.system.VMRuntime.getRuntime().registerNativeAllocation(this.mEstimatedNativeAllocBytes);
            this.mIsWriterValid = true;
            return;
        }
        throw new java.lang.IllegalArgumentException("Illegal input argument: surface " + surface + ", maxImages: " + i);
    }

    private ImageWriter(android.view.Surface surface, int i, boolean z, int i2, int i3, int i4) {
        this.mListenerLock = new java.lang.Object();
        this.mCloseLock = new java.lang.Object();
        this.mIsWriterValid = false;
        this.mUsage = 48L;
        this.mDequeuedImages = new java.util.concurrent.CopyOnWriteArrayList();
        this.mMaxImages = i;
        if (!z) {
            this.mHardwareBufferFormat = android.media.PublicFormatUtils.getHalFormat(i2);
            this.mDataSpace = android.media.PublicFormatUtils.getHalDataspace(i2);
        }
        initializeImageWriter(surface, i, z, i2, this.mHardwareBufferFormat, this.mDataSpace, i3, i4, this.mUsage);
    }

    private ImageWriter(android.view.Surface surface, int i, boolean z, int i2, int i3, int i4, long j) {
        this.mListenerLock = new java.lang.Object();
        this.mCloseLock = new java.lang.Object();
        this.mIsWriterValid = false;
        this.mUsage = 48L;
        this.mDequeuedImages = new java.util.concurrent.CopyOnWriteArrayList();
        this.mMaxImages = i;
        this.mUsage = j;
        if (!z) {
            this.mHardwareBufferFormat = android.media.PublicFormatUtils.getHalFormat(i2);
            this.mDataSpace = android.media.PublicFormatUtils.getHalDataspace(i2);
        }
        initializeImageWriter(surface, i, z, i2, this.mHardwareBufferFormat, this.mDataSpace, i3, i4, j);
    }

    private ImageWriter(android.view.Surface surface, int i, boolean z, int i2, int i3, int i4, int i5, long j) {
        int i6;
        this.mListenerLock = new java.lang.Object();
        this.mCloseLock = new java.lang.Object();
        this.mIsWriterValid = false;
        this.mUsage = 48L;
        this.mDequeuedImages = new java.util.concurrent.CopyOnWriteArrayList();
        this.mMaxImages = i;
        this.mUsage = j;
        if (z) {
            i6 = 0;
        } else {
            int publicFormat = android.media.PublicFormatUtils.getPublicFormat(i2, i3);
            this.mHardwareBufferFormat = i2;
            this.mDataSpace = i3;
            i6 = publicFormat;
        }
        initializeImageWriter(surface, i, z, i6, i2, i3, i4, i5, j);
    }

    public int getMaxImages() {
        return this.mMaxImages;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public android.media.Image dequeueInputImage() {
        if (this.mDequeuedImages.size() >= this.mMaxImages) {
            throw new java.lang.IllegalStateException("Already dequeued max number of Images " + this.mMaxImages);
        }
        android.media.ImageWriter.WriterSurfaceImage writerSurfaceImage = new android.media.ImageWriter.WriterSurfaceImage(this);
        nativeDequeueInputImage(this.mNativeContext, writerSurfaceImage);
        this.mDequeuedImages.add(writerSurfaceImage);
        writerSurfaceImage.mIsImageValid = true;
        return writerSurfaceImage;
    }

    public void queueInputImage(android.media.Image image) {
        if (image == null) {
            throw new java.lang.IllegalArgumentException("image shouldn't be null");
        }
        boolean isImageOwnedByMe = isImageOwnedByMe(image);
        if (isImageOwnedByMe && !((android.media.ImageWriter.WriterSurfaceImage) image).mIsImageValid) {
            throw new java.lang.IllegalStateException("Image from ImageWriter is invalid");
        }
        if (!isImageOwnedByMe) {
            if (image.getOwner() instanceof android.media.ImageReader) {
                ((android.media.ImageReader) image.getOwner()).detachImage(image);
            } else if (image.getOwner() != null) {
                throw new java.lang.IllegalArgumentException("Only images from ImageReader can be queued to ImageWriter, other image source is not supported yet!");
            }
            attachAndQueueInputImage(image);
            image.close();
            return;
        }
        android.graphics.Rect cropRect = image.getCropRect();
        nativeQueueInputImage(this.mNativeContext, image, image.getTimestamp(), image.getDataSpace(), cropRect.left, cropRect.top, cropRect.right, cropRect.bottom, image.getTransform(), image.getScalingMode());
        if (isImageOwnedByMe) {
            this.mDequeuedImages.remove(image);
            android.media.ImageWriter.WriterSurfaceImage writerSurfaceImage = (android.media.ImageWriter.WriterSurfaceImage) image;
            writerSurfaceImage.clearSurfacePlanes();
            writerSurfaceImage.mIsImageValid = false;
        }
    }

    public int getFormat() {
        return this.mWriterFormat;
    }

    public long getUsage() {
        return this.mUsage;
    }

    public int getHardwareBufferFormat() {
        return this.mHardwareBufferFormat;
    }

    public int getDataSpace() {
        return this.mDataSpace;
    }

    public void setOnImageReleasedListener(android.media.ImageWriter.OnImageReleasedListener onImageReleasedListener, android.os.Handler handler) {
        synchronized (this.mListenerLock) {
            if (onImageReleasedListener != null) {
                android.os.Looper looper = handler != null ? handler.getLooper() : android.os.Looper.myLooper();
                if (looper == null) {
                    throw new java.lang.IllegalArgumentException("handler is null but the current thread is not a looper");
                }
                if (this.mListenerHandler == null || this.mListenerHandler.getLooper() != looper) {
                    this.mListenerHandler = new android.media.ImageWriter.ListenerHandler(looper);
                }
                this.mListener = onImageReleasedListener;
            } else {
                this.mListener = null;
                this.mListenerHandler = null;
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        setOnImageReleasedListener(null, null);
        synchronized (this.mCloseLock) {
            if (this.mIsWriterValid) {
                java.util.Iterator<android.media.Image> it = this.mDequeuedImages.iterator();
                while (it.hasNext()) {
                    it.next().close();
                }
                this.mDequeuedImages.clear();
                nativeClose(this.mNativeContext);
                this.mNativeContext = 0L;
                if (this.mEstimatedNativeAllocBytes > 0) {
                    dalvik.system.VMRuntime.getRuntime().registerNativeFree(this.mEstimatedNativeAllocBytes);
                    this.mEstimatedNativeAllocBytes = 0;
                }
                this.mIsWriterValid = false;
            }
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private void attachAndQueueInputImage(android.media.Image image) {
        if (image == null) {
            throw new java.lang.IllegalArgumentException("image shouldn't be null");
        }
        if (isImageOwnedByMe(image)) {
            throw new java.lang.IllegalArgumentException("Can not attach an image that is owned ImageWriter already");
        }
        if (!image.isAttachable()) {
            throw new java.lang.IllegalStateException("Image was not detached from last owner, or image  is not detachable");
        }
        android.graphics.Rect cropRect = image.getCropRect();
        int halFormat = android.media.PublicFormatUtils.getHalFormat(image.getFormat());
        if (image.getNativeContext() != 0) {
            nativeAttachAndQueueImage(this.mNativeContext, image.getNativeContext(), halFormat, image.getTimestamp(), image.getDataSpace(), cropRect.left, cropRect.top, cropRect.right, cropRect.bottom, image.getTransform(), image.getScalingMode());
            return;
        }
        android.graphics.GraphicBuffer createFromHardwareBuffer = android.graphics.GraphicBuffer.createFromHardwareBuffer(image.getHardwareBuffer());
        nativeAttachAndQueueGraphicBuffer(this.mNativeContext, createFromHardwareBuffer, halFormat, image.getTimestamp(), image.getDataSpace(), cropRect.left, cropRect.top, cropRect.right, cropRect.bottom, image.getTransform(), image.getScalingMode());
        createFromHardwareBuffer.destroy();
        image.close();
    }

    private final class ListenerHandler extends android.os.Handler {
        public ListenerHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.media.ImageWriter.OnImageReleasedListener onImageReleasedListener;
            boolean z;
            synchronized (android.media.ImageWriter.this.mListenerLock) {
                onImageReleasedListener = android.media.ImageWriter.this.mListener;
            }
            synchronized (android.media.ImageWriter.this.mCloseLock) {
                z = android.media.ImageWriter.this.mIsWriterValid;
            }
            if (onImageReleasedListener != null && z) {
                onImageReleasedListener.onImageReleased(android.media.ImageWriter.this);
            }
        }
    }

    private static void postEventFromNative(java.lang.Object obj) {
        android.media.ImageWriter.ListenerHandler listenerHandler;
        android.media.ImageWriter imageWriter = (android.media.ImageWriter) ((java.lang.ref.WeakReference) obj).get();
        if (imageWriter == null) {
            return;
        }
        synchronized (imageWriter.mListenerLock) {
            listenerHandler = imageWriter.mListenerHandler;
        }
        if (listenerHandler != null) {
            listenerHandler.sendEmptyMessage(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abortImage(android.media.Image image) {
        if (image == null) {
            throw new java.lang.IllegalArgumentException("image shouldn't be null");
        }
        if (!this.mDequeuedImages.contains(image)) {
            throw new java.lang.IllegalStateException("It is illegal to abort some image that is not dequeued yet");
        }
        android.media.ImageWriter.WriterSurfaceImage writerSurfaceImage = (android.media.ImageWriter.WriterSurfaceImage) image;
        if (!writerSurfaceImage.mIsImageValid) {
            return;
        }
        cancelImage(this.mNativeContext, image);
        this.mDequeuedImages.remove(image);
        writerSurfaceImage.clearSurfacePlanes();
        writerSurfaceImage.mIsImageValid = false;
    }

    private boolean isImageOwnedByMe(android.media.Image image) {
        return (image instanceof android.media.ImageWriter.WriterSurfaceImage) && ((android.media.ImageWriter.WriterSurfaceImage) image).getOwner() == this;
    }

    public static final class Builder {
        private android.view.Surface mSurface;
        private int mWidth = -1;
        private int mHeight = -1;
        private int mMaxImages = 1;
        private int mImageFormat = 0;
        private long mUsage = -1;
        private int mHardwareBufferFormat = 1;
        private int mDataSpace = 0;
        private boolean mUseSurfaceImageFormatInfo = true;
        private boolean mUseLegacyImageFormat = false;

        public Builder(android.view.Surface surface) {
            this.mSurface = surface;
        }

        public android.media.ImageWriter.Builder setWidthAndHeight(int i, int i2) {
            this.mWidth = i;
            this.mHeight = i2;
            return this;
        }

        public android.media.ImageWriter.Builder setMaxImages(int i) {
            this.mMaxImages = i;
            return this;
        }

        public android.media.ImageWriter.Builder setImageFormat(int i) {
            if (!android.graphics.ImageFormat.isPublicFormat(i) && !android.graphics.PixelFormat.isPublicFormat(i)) {
                throw new java.lang.IllegalArgumentException("Invalid imageFormat is specified: " + i);
            }
            this.mImageFormat = i;
            this.mUseLegacyImageFormat = true;
            this.mHardwareBufferFormat = 1;
            this.mDataSpace = 0;
            this.mUseSurfaceImageFormatInfo = false;
            return this;
        }

        public android.media.ImageWriter.Builder setHardwareBufferFormat(int i) {
            this.mHardwareBufferFormat = i;
            this.mImageFormat = 0;
            this.mUseLegacyImageFormat = false;
            this.mUseSurfaceImageFormatInfo = false;
            return this;
        }

        public android.media.ImageWriter.Builder setDataSpace(int i) {
            this.mDataSpace = i;
            this.mImageFormat = 0;
            this.mUseLegacyImageFormat = false;
            this.mUseSurfaceImageFormatInfo = false;
            return this;
        }

        public android.media.ImageWriter.Builder setUsage(long j) {
            this.mUsage = j;
            return this;
        }

        public android.media.ImageWriter build() {
            if (this.mUseLegacyImageFormat) {
                return new android.media.ImageWriter(this.mSurface, this.mMaxImages, this.mUseSurfaceImageFormatInfo, this.mImageFormat, this.mWidth, this.mHeight, this.mUsage);
            }
            return new android.media.ImageWriter(this.mSurface, this.mMaxImages, this.mUseSurfaceImageFormatInfo, this.mHardwareBufferFormat, this.mDataSpace, this.mWidth, this.mHeight, this.mUsage);
        }
    }

    private static class WriterSurfaceImage extends android.media.Image {
        private int mDataSpace;
        private int mHeight;
        private long mNativeBuffer;
        private android.media.ImageWriter mOwner;
        private android.media.ImageWriter.WriterSurfaceImage.SurfacePlane[] mPlanes;
        private int mWidth;
        private int mNativeFenceFd = -1;
        private int mFormat = -1;
        private final long DEFAULT_TIMESTAMP = Long.MIN_VALUE;
        private long mTimestamp = Long.MIN_VALUE;
        private int mTransform = 0;
        private int mScalingMode = 0;
        private final java.lang.Object mCloseLock = new java.lang.Object();

        private native synchronized android.media.ImageWriter.WriterSurfaceImage.SurfacePlane[] nativeCreatePlanes(int i, int i2);

        private native synchronized int nativeGetFormat(int i);

        private native synchronized android.hardware.HardwareBuffer nativeGetHardwareBuffer();

        private native synchronized int nativeGetHeight();

        private native synchronized int nativeGetWidth();

        private native synchronized void nativeSetFenceFd(int i);

        public WriterSurfaceImage(android.media.ImageWriter imageWriter) {
            this.mHeight = -1;
            this.mWidth = -1;
            this.mDataSpace = 0;
            this.mOwner = imageWriter;
            this.mWidth = imageWriter.mWidth;
            this.mHeight = imageWriter.mHeight;
            this.mDataSpace = imageWriter.mDataSpace;
        }

        @Override // android.media.Image
        public int getDataSpace() {
            throwISEIfImageIsInvalid();
            return this.mDataSpace;
        }

        @Override // android.media.Image
        public void setDataSpace(int i) {
            throwISEIfImageIsInvalid();
            this.mDataSpace = i;
        }

        @Override // android.media.Image
        public int getFormat() {
            throwISEIfImageIsInvalid();
            if (this.mFormat == -1) {
                this.mFormat = nativeGetFormat(this.mDataSpace);
            }
            return this.mFormat;
        }

        @Override // android.media.Image
        public int getWidth() {
            throwISEIfImageIsInvalid();
            if (this.mWidth == -1) {
                this.mWidth = nativeGetWidth();
            }
            return this.mWidth;
        }

        @Override // android.media.Image
        public int getHeight() {
            throwISEIfImageIsInvalid();
            if (this.mHeight == -1) {
                this.mHeight = nativeGetHeight();
            }
            return this.mHeight;
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
        public long getTimestamp() {
            throwISEIfImageIsInvalid();
            return this.mTimestamp;
        }

        @Override // android.media.Image
        public void setTimestamp(long j) {
            throwISEIfImageIsInvalid();
            this.mTimestamp = j;
        }

        @Override // android.media.Image
        public android.hardware.HardwareBuffer getHardwareBuffer() {
            throwISEIfImageIsInvalid();
            return nativeGetHardwareBuffer();
        }

        @Override // android.media.Image
        public android.hardware.SyncFence getFence() throws java.io.IOException {
            throwISEIfImageIsInvalid();
            if (this.mNativeFenceFd != -1) {
                return android.hardware.SyncFence.create(android.os.ParcelFileDescriptor.fromFd(this.mNativeFenceFd));
            }
            return android.hardware.SyncFence.createEmpty();
        }

        @Override // android.media.Image
        public void setFence(android.hardware.SyncFence syncFence) throws java.io.IOException {
            throwISEIfImageIsInvalid();
            nativeSetFenceFd(syncFence.getFdDup().detachFd());
        }

        @Override // android.media.Image
        public android.media.Image.Plane[] getPlanes() {
            throwISEIfImageIsInvalid();
            if (this.mPlanes == null) {
                this.mPlanes = nativeCreatePlanes(android.media.ImageUtils.getNumPlanesForFormat(getFormat()), getOwner().getFormat());
            }
            return (android.media.Image.Plane[]) this.mPlanes.clone();
        }

        @Override // android.media.Image
        public boolean isAttachable() {
            throwISEIfImageIsInvalid();
            return false;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // android.media.Image
        public android.media.ImageWriter getOwner() {
            throwISEIfImageIsInvalid();
            return this.mOwner;
        }

        @Override // android.media.Image
        long getNativeContext() {
            throwISEIfImageIsInvalid();
            return this.mNativeBuffer;
        }

        @Override // android.media.Image, java.lang.AutoCloseable
        public void close() {
            synchronized (this.mCloseLock) {
                if (this.mIsImageValid) {
                    getOwner().abortImage(this);
                }
            }
        }

        protected final void finalize() throws java.lang.Throwable {
            try {
                close();
            } finally {
                super.finalize();
            }
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
            public int getRowStride() {
                android.media.ImageWriter.WriterSurfaceImage.this.throwISEIfImageIsInvalid();
                return this.mRowStride;
            }

            @Override // android.media.Image.Plane
            public int getPixelStride() {
                android.media.ImageWriter.WriterSurfaceImage.this.throwISEIfImageIsInvalid();
                return this.mPixelStride;
            }

            @Override // android.media.Image.Plane
            public java.nio.ByteBuffer getBuffer() {
                android.media.ImageWriter.WriterSurfaceImage.this.throwISEIfImageIsInvalid();
                return this.mBuffer;
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

    static {
        java.lang.System.loadLibrary("media_jni");
        nativeClassInit();
    }
}
