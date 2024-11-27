package android.graphics;

/* loaded from: classes.dex */
public final class ImageDecoder implements java.lang.AutoCloseable {
    public static final int ALLOCATOR_DEFAULT = 0;
    public static final int ALLOCATOR_HARDWARE = 3;
    public static final int ALLOCATOR_SHARED_MEMORY = 2;
    public static final int ALLOCATOR_SOFTWARE = 1;
    public static final int MEMORY_POLICY_DEFAULT = 1;
    public static final int MEMORY_POLICY_LOW_RAM = 0;
    private final boolean mAnimated;
    private android.content.res.AssetFileDescriptor mAssetFd;
    private android.graphics.Rect mCropRect;
    private int mDesiredHeight;
    private int mDesiredWidth;
    private final int mHeight;
    private java.io.InputStream mInputStream;
    private final boolean mIsNinePatch;
    private long mNativePtr;
    private android.graphics.ImageDecoder.OnPartialImageListener mOnPartialImageListener;
    private android.graphics.Rect mOutPaddingRect;
    private boolean mOwnsInputStream;
    private android.graphics.PostProcessor mPostProcessor;
    private android.graphics.ImageDecoder.Source mSource;
    private byte[] mTempStorage;
    private final int mWidth;
    private static boolean sIsHevcDecoderSupported = false;
    private static boolean sIsHevcDecoderSupportedInitialized = false;
    private static final java.lang.Object sIsHevcDecoderSupportedLock = new java.lang.Object();
    private static boolean sIsP010SupportedForAV1 = false;
    private static boolean sIsP010SupportedForHEVC = false;
    private static boolean sIsP010SupportedFlagsInitialized = false;
    private static final java.lang.Object sIsP010SupportedLock = new java.lang.Object();
    private int mAllocator = 0;
    private boolean mUnpremultipliedRequired = false;
    private boolean mMutable = false;
    private boolean mConserveMemory = false;
    private boolean mDecodeAsAlphaMask = false;
    private android.graphics.ColorSpace mDesiredColorSpace = null;
    private final java.util.concurrent.atomic.AtomicBoolean mClosed = new java.util.concurrent.atomic.AtomicBoolean();
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Allocator {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MemoryPolicy {
    }

    public interface OnHeaderDecodedListener {
        void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source);
    }

    public interface OnPartialImageListener {
        boolean onPartialImage(android.graphics.ImageDecoder.DecodeException decodeException);
    }

    private static native void nClose(long j);

    private static native android.graphics.ImageDecoder nCreate(long j, boolean z, android.graphics.ImageDecoder.Source source) throws java.io.IOException;

    private static native android.graphics.ImageDecoder nCreate(java.io.FileDescriptor fileDescriptor, long j, boolean z, android.graphics.ImageDecoder.Source source) throws java.io.IOException;

    private static native android.graphics.ImageDecoder nCreate(java.io.InputStream inputStream, byte[] bArr, boolean z, android.graphics.ImageDecoder.Source source) throws java.io.IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public static native android.graphics.ImageDecoder nCreate(java.nio.ByteBuffer byteBuffer, int i, int i2, boolean z, android.graphics.ImageDecoder.Source source) throws java.io.IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public static native android.graphics.ImageDecoder nCreate(byte[] bArr, int i, int i2, boolean z, android.graphics.ImageDecoder.Source source) throws java.io.IOException;

    private static native android.graphics.Bitmap nDecodeBitmap(long j, android.graphics.ImageDecoder imageDecoder, boolean z, int i, int i2, android.graphics.Rect rect, boolean z2, int i3, boolean z3, boolean z4, boolean z5, long j2, boolean z6) throws java.io.IOException;

    private static native android.graphics.ColorSpace nGetColorSpace(long j);

    private static native java.lang.String nGetMimeType(long j);

    private static native void nGetPadding(long j, android.graphics.Rect rect);

    private static native android.util.Size nGetSampledSize(long j, int i);

    public static abstract class Source {
        abstract android.graphics.ImageDecoder createImageDecoder(boolean z) throws java.io.IOException;

        private Source() {
        }

        android.content.res.Resources getResources() {
            return null;
        }

        int getDensity() {
            return 0;
        }

        final int computeDstDensity() {
            android.content.res.Resources resources = getResources();
            if (resources == null) {
                return android.graphics.Bitmap.getDefaultDensity();
            }
            return resources.getDisplayMetrics().densityDpi;
        }
    }

    private static class ByteArraySource extends android.graphics.ImageDecoder.Source {
        private final byte[] mData;
        private final int mLength;
        private final int mOffset;

        ByteArraySource(byte[] bArr, int i, int i2) {
            super();
            this.mData = bArr;
            this.mOffset = i;
            this.mLength = i2;
        }

        @Override // android.graphics.ImageDecoder.Source
        public android.graphics.ImageDecoder createImageDecoder(boolean z) throws java.io.IOException {
            return android.graphics.ImageDecoder.nCreate(this.mData, this.mOffset, this.mLength, z, this);
        }

        public java.lang.String toString() {
            return "ByteArraySource{len=" + this.mLength + "}";
        }
    }

    private static class ByteBufferSource extends android.graphics.ImageDecoder.Source {
        private final java.nio.ByteBuffer mBuffer;
        private final int mLength;

        ByteBufferSource(java.nio.ByteBuffer byteBuffer) {
            super();
            this.mBuffer = byteBuffer;
            this.mLength = this.mBuffer.limit() - this.mBuffer.position();
        }

        @Override // android.graphics.ImageDecoder.Source
        public android.graphics.ImageDecoder createImageDecoder(boolean z) throws java.io.IOException {
            if (!this.mBuffer.isDirect() && this.mBuffer.hasArray()) {
                return android.graphics.ImageDecoder.nCreate(this.mBuffer.array(), this.mBuffer.arrayOffset() + this.mBuffer.position(), this.mBuffer.limit() - this.mBuffer.position(), z, this);
            }
            java.nio.ByteBuffer slice = this.mBuffer.slice();
            return android.graphics.ImageDecoder.nCreate(slice, slice.position(), slice.limit(), z, this);
        }

        public java.lang.String toString() {
            return "ByteBufferSource{len=" + this.mLength + "}";
        }
    }

    private static class ContentResolverSource extends android.graphics.ImageDecoder.Source {
        private final android.content.ContentResolver mResolver;
        private final android.content.res.Resources mResources;
        private final android.net.Uri mUri;

        ContentResolverSource(android.content.ContentResolver contentResolver, android.net.Uri uri, android.content.res.Resources resources) {
            super();
            this.mResolver = contentResolver;
            this.mUri = uri;
            this.mResources = resources;
        }

        @Override // android.graphics.ImageDecoder.Source
        android.content.res.Resources getResources() {
            return this.mResources;
        }

        @Override // android.graphics.ImageDecoder.Source
        public android.graphics.ImageDecoder createImageDecoder(boolean z) throws java.io.IOException {
            android.content.res.AssetFileDescriptor assetFileDescriptor = null;
            try {
                if ("content".equals(this.mUri.getScheme())) {
                    assetFileDescriptor = this.mResolver.openTypedAssetFileDescriptor(this.mUri, com.google.android.mms.ContentType.IMAGE_UNSPECIFIED, null);
                } else {
                    assetFileDescriptor = this.mResolver.openAssetFileDescriptor(this.mUri, "r");
                }
            } catch (java.io.FileNotFoundException e) {
            }
            if (assetFileDescriptor == null) {
                java.io.InputStream openInputStream = this.mResolver.openInputStream(this.mUri);
                if (openInputStream == null) {
                    throw new java.io.FileNotFoundException(this.mUri.toString());
                }
                return android.graphics.ImageDecoder.createFromStream(openInputStream, true, z, this);
            }
            return android.graphics.ImageDecoder.createFromAssetFileDescriptor(assetFileDescriptor, z, this);
        }

        public java.lang.String toString() {
            java.lang.String uri = this.mUri.toString();
            if (uri.length() > 90) {
                uri = uri.substring(0, 80) + ".." + uri.substring(uri.length() - 10);
            }
            return "ContentResolverSource{uri=" + uri + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.graphics.ImageDecoder createFromFile(java.io.File file, boolean z, android.graphics.ImageDecoder.Source source) throws java.io.IOException {
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
        java.io.FileDescriptor fd = fileInputStream.getFD();
        try {
            android.system.Os.lseek(fd, 0L, android.system.OsConstants.SEEK_CUR);
            try {
                android.graphics.ImageDecoder nCreate = nCreate(fd, -1L, z, source);
                if (nCreate != null) {
                    nCreate.mInputStream = fileInputStream;
                    nCreate.mOwnsInputStream = true;
                }
                return nCreate;
            } finally {
                libcore.io.IoUtils.closeQuietly(fileInputStream);
            }
        } catch (android.system.ErrnoException e) {
            return createFromStream(fileInputStream, true, z, source);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.graphics.ImageDecoder createFromStream(java.io.InputStream inputStream, boolean z, boolean z2, android.graphics.ImageDecoder.Source source) throws java.io.IOException {
        byte[] bArr = new byte[16384];
        try {
            android.graphics.ImageDecoder nCreate = nCreate(inputStream, bArr, z2, source);
            if (nCreate != null) {
                nCreate.mInputStream = inputStream;
                nCreate.mOwnsInputStream = z;
                nCreate.mTempStorage = bArr;
            }
            return nCreate;
        } finally {
            if (z) {
                libcore.io.IoUtils.closeQuietly(inputStream);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.graphics.ImageDecoder createFromAssetFileDescriptor(android.content.res.AssetFileDescriptor assetFileDescriptor, boolean z, android.graphics.ImageDecoder.Source source) throws java.io.IOException {
        android.graphics.ImageDecoder createFromStream;
        if (assetFileDescriptor == null) {
            throw new java.io.FileNotFoundException();
        }
        java.io.FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
        try {
            try {
                android.system.Os.lseek(fileDescriptor, assetFileDescriptor.getStartOffset(), android.system.OsConstants.SEEK_SET);
                createFromStream = nCreate(fileDescriptor, assetFileDescriptor.getDeclaredLength(), z, source);
            } catch (android.system.ErrnoException e) {
                createFromStream = createFromStream(new java.io.FileInputStream(fileDescriptor), true, z, source);
            }
            if (createFromStream != null) {
                createFromStream.mAssetFd = assetFileDescriptor;
            }
            return createFromStream;
        } finally {
            libcore.io.IoUtils.closeQuietly(assetFileDescriptor);
        }
    }

    private static class InputStreamSource extends android.graphics.ImageDecoder.Source {
        final int mInputDensity;
        java.io.InputStream mInputStream;
        final android.content.res.Resources mResources;

        InputStreamSource(android.content.res.Resources resources, java.io.InputStream inputStream, int i) {
            super();
            if (inputStream == null) {
                throw new java.lang.IllegalArgumentException("The InputStream cannot be null");
            }
            this.mResources = resources;
            this.mInputStream = inputStream;
            this.mInputDensity = i;
        }

        @Override // android.graphics.ImageDecoder.Source
        public android.content.res.Resources getResources() {
            return this.mResources;
        }

        @Override // android.graphics.ImageDecoder.Source
        public int getDensity() {
            return this.mInputDensity;
        }

        @Override // android.graphics.ImageDecoder.Source
        public android.graphics.ImageDecoder createImageDecoder(boolean z) throws java.io.IOException {
            android.graphics.ImageDecoder createFromStream;
            synchronized (this) {
                if (this.mInputStream == null) {
                    throw new java.io.IOException("Cannot reuse InputStreamSource");
                }
                java.io.InputStream inputStream = this.mInputStream;
                this.mInputStream = null;
                createFromStream = android.graphics.ImageDecoder.createFromStream(inputStream, false, z, this);
            }
            return createFromStream;
        }

        public java.lang.String toString() {
            return "InputStream{s=" + this.mInputStream + "}";
        }
    }

    public static class AssetInputStreamSource extends android.graphics.ImageDecoder.Source {
        private android.content.res.AssetManager.AssetInputStream mAssetInputStream;
        private final int mDensity;
        private final android.content.res.Resources mResources;

        public AssetInputStreamSource(android.content.res.AssetManager.AssetInputStream assetInputStream, android.content.res.Resources resources, android.util.TypedValue typedValue) {
            super();
            this.mAssetInputStream = assetInputStream;
            this.mResources = resources;
            if (typedValue.density == 0) {
                this.mDensity = 160;
            } else if (typedValue.density != 65535) {
                this.mDensity = typedValue.density;
            } else {
                this.mDensity = 0;
            }
        }

        @Override // android.graphics.ImageDecoder.Source
        public android.content.res.Resources getResources() {
            return this.mResources;
        }

        @Override // android.graphics.ImageDecoder.Source
        public int getDensity() {
            return this.mDensity;
        }

        @Override // android.graphics.ImageDecoder.Source
        public android.graphics.ImageDecoder createImageDecoder(boolean z) throws java.io.IOException {
            android.graphics.ImageDecoder createFromAsset;
            synchronized (this) {
                if (this.mAssetInputStream == null) {
                    throw new java.io.IOException("Cannot reuse AssetInputStreamSource");
                }
                android.content.res.AssetManager.AssetInputStream assetInputStream = this.mAssetInputStream;
                this.mAssetInputStream = null;
                createFromAsset = android.graphics.ImageDecoder.createFromAsset(assetInputStream, z, this);
            }
            return createFromAsset;
        }

        public java.lang.String toString() {
            return "AssetInputStream{s=" + this.mAssetInputStream + "}";
        }
    }

    private static class ResourceSource extends android.graphics.ImageDecoder.Source {
        private java.lang.Object mLock;
        int mResDensity;
        final int mResId;
        final android.content.res.Resources mResources;

        ResourceSource(android.content.res.Resources resources, int i) {
            super();
            this.mLock = new java.lang.Object();
            this.mResources = resources;
            this.mResId = i;
            this.mResDensity = 0;
        }

        @Override // android.graphics.ImageDecoder.Source
        public android.content.res.Resources getResources() {
            return this.mResources;
        }

        @Override // android.graphics.ImageDecoder.Source
        public int getDensity() {
            int i;
            synchronized (this.mLock) {
                i = this.mResDensity;
            }
            return i;
        }

        @Override // android.graphics.ImageDecoder.Source
        public android.graphics.ImageDecoder createImageDecoder(boolean z) throws java.io.IOException {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            java.io.InputStream openRawResource = this.mResources.openRawResource(this.mResId, typedValue);
            synchronized (this.mLock) {
                if (typedValue.density == 0) {
                    this.mResDensity = 160;
                } else if (typedValue.density != 65535) {
                    this.mResDensity = typedValue.density;
                }
            }
            return android.graphics.ImageDecoder.createFromAsset((android.content.res.AssetManager.AssetInputStream) openRawResource, z, this);
        }

        public java.lang.String toString() {
            try {
                return "Resource{name=" + this.mResources.getResourceName(this.mResId) + "}";
            } catch (android.content.res.Resources.NotFoundException e) {
                return "Resource{id=" + this.mResId + "}";
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.graphics.ImageDecoder createFromAsset(android.content.res.AssetManager.AssetInputStream assetInputStream, boolean z, android.graphics.ImageDecoder.Source source) throws java.io.IOException {
        try {
            android.graphics.ImageDecoder nCreate = nCreate(assetInputStream.getNativeAsset(), z, source);
            if (nCreate != null) {
                nCreate.mInputStream = assetInputStream;
                nCreate.mOwnsInputStream = true;
            }
            return nCreate;
        } finally {
            libcore.io.IoUtils.closeQuietly(assetInputStream);
        }
    }

    private static class AssetSource extends android.graphics.ImageDecoder.Source {
        private final android.content.res.AssetManager mAssets;
        private final java.lang.String mFileName;

        AssetSource(android.content.res.AssetManager assetManager, java.lang.String str) {
            super();
            this.mAssets = assetManager;
            this.mFileName = str;
        }

        @Override // android.graphics.ImageDecoder.Source
        public android.graphics.ImageDecoder createImageDecoder(boolean z) throws java.io.IOException {
            return android.graphics.ImageDecoder.createFromAsset((android.content.res.AssetManager.AssetInputStream) this.mAssets.open(this.mFileName), z, this);
        }

        public java.lang.String toString() {
            return "AssetSource{file=" + this.mFileName + "}";
        }
    }

    private static class FileSource extends android.graphics.ImageDecoder.Source {
        private final java.io.File mFile;

        FileSource(java.io.File file) {
            super();
            this.mFile = file;
        }

        @Override // android.graphics.ImageDecoder.Source
        public android.graphics.ImageDecoder createImageDecoder(boolean z) throws java.io.IOException {
            return android.graphics.ImageDecoder.createFromFile(this.mFile, z, this);
        }

        public java.lang.String toString() {
            return "FileSource{file=" + this.mFile + "}";
        }
    }

    private static class CallableSource extends android.graphics.ImageDecoder.Source {
        private final java.util.concurrent.Callable<android.content.res.AssetFileDescriptor> mCallable;

        CallableSource(java.util.concurrent.Callable<android.content.res.AssetFileDescriptor> callable) {
            super();
            this.mCallable = callable;
        }

        @Override // android.graphics.ImageDecoder.Source
        public android.graphics.ImageDecoder createImageDecoder(boolean z) throws java.io.IOException {
            try {
                return android.graphics.ImageDecoder.createFromAssetFileDescriptor(this.mCallable.call(), z, this);
            } catch (java.lang.Exception e) {
                if (e instanceof java.io.IOException) {
                    throw ((java.io.IOException) e);
                }
                throw new java.io.IOException(e);
            }
        }

        public java.lang.String toString() {
            return "CallableSource{obj=" + this.mCallable.toString() + "}";
        }
    }

    public static class ImageInfo {
        private final android.graphics.ColorSpace mColorSpace;
        private final boolean mIsAnimated;
        private final java.lang.String mMimeType;
        private final android.util.Size mSize;

        private ImageInfo(android.util.Size size, boolean z, java.lang.String str, android.graphics.ColorSpace colorSpace) {
            this.mSize = size;
            this.mIsAnimated = z;
            this.mMimeType = str;
            this.mColorSpace = colorSpace;
        }

        public android.util.Size getSize() {
            return this.mSize;
        }

        public java.lang.String getMimeType() {
            return this.mMimeType;
        }

        public boolean isAnimated() {
            return this.mIsAnimated;
        }

        public android.graphics.ColorSpace getColorSpace() {
            return this.mColorSpace;
        }
    }

    public static final class DecodeException extends java.io.IOException {
        public static final int SOURCE_EXCEPTION = 1;
        public static final int SOURCE_INCOMPLETE = 2;
        public static final int SOURCE_MALFORMED_DATA = 3;
        final int mError;
        final android.graphics.ImageDecoder.Source mSource;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Error {
        }

        DecodeException(int i, java.lang.Throwable th, android.graphics.ImageDecoder.Source source) {
            super(errorMessage(i, th), th);
            this.mError = i;
            this.mSource = source;
        }

        DecodeException(int i, java.lang.String str, java.lang.Throwable th, android.graphics.ImageDecoder.Source source) {
            super(str + errorMessage(i, th), th);
            this.mError = i;
            this.mSource = source;
        }

        public int getError() {
            return this.mError;
        }

        public android.graphics.ImageDecoder.Source getSource() {
            return this.mSource;
        }

        private static java.lang.String errorMessage(int i, java.lang.Throwable th) {
            switch (i) {
                case 1:
                    return "Exception in input: " + th;
                case 2:
                    return "Input was incomplete.";
                case 3:
                    return "Input contained an error.";
                default:
                    return "";
            }
        }
    }

    private ImageDecoder(long j, int i, int i2, boolean z, boolean z2) {
        this.mNativePtr = j;
        this.mWidth = i;
        this.mHeight = i2;
        this.mDesiredWidth = i;
        this.mDesiredHeight = i2;
        this.mAnimated = z;
        this.mIsNinePatch = z2;
        this.mCloseGuard.open("close");
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            this.mInputStream = null;
            this.mAssetFd = null;
            close();
        } finally {
            super.finalize();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isMimeTypeSupported(java.lang.String str) {
        char c;
        java.util.Objects.requireNonNull(str);
        java.lang.String lowerCase = str.toLowerCase(java.util.Locale.US);
        switch (lowerCase.hashCode()) {
            case -1875291391:
                if (lowerCase.equals("image/x-fuji-raf")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -1635437028:
                if (lowerCase.equals("image/x-samsung-srw")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -1594371159:
                if (lowerCase.equals("image/x-sony-arw")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1487656890:
                if (lowerCase.equals(android.media.MediaFormat.MIMETYPE_IMAGE_AVIF)) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -1487464693:
                if (lowerCase.equals("image/heic")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -1487464690:
                if (lowerCase.equals("image/heif")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -1487394660:
                if (lowerCase.equals(com.google.android.mms.ContentType.IMAGE_JPEG)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1487018032:
                if (lowerCase.equals("image/webp")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1423313290:
                if (lowerCase.equals("image/x-adobe-dng")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -985160897:
                if (lowerCase.equals("image/x-panasonic-rw2")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -879272239:
                if (lowerCase.equals("image/bmp")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -879267568:
                if (lowerCase.equals(com.google.android.mms.ContentType.IMAGE_GIF)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -879258763:
                if (lowerCase.equals(com.google.android.mms.ContentType.IMAGE_PNG)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -332763809:
                if (lowerCase.equals("image/x-pentax-pef")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 741270252:
                if (lowerCase.equals(com.google.android.mms.ContentType.IMAGE_WBMP)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1146342924:
                if (lowerCase.equals("image/x-ico")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1378106698:
                if (lowerCase.equals("image/x-olympus-orf")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 2099152104:
                if (lowerCase.equals("image/x-nikon-nef")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 2099152524:
                if (lowerCase.equals("image/x-nikon-nrw")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 2111234748:
                if (lowerCase.equals("image/x-canon-cr2")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
                return true;
            case 17:
            case 18:
                return isHevcDecoderSupported();
            case 19:
                return isP010SupportedForAV1();
            default:
                return false;
        }
    }

    public static android.graphics.ImageDecoder.Source createSource(android.content.res.Resources resources, int i) {
        return new android.graphics.ImageDecoder.ResourceSource(resources, i);
    }

    public static android.graphics.ImageDecoder.Source createSource(android.content.ContentResolver contentResolver, android.net.Uri uri) {
        return new android.graphics.ImageDecoder.ContentResolverSource(contentResolver, uri, null);
    }

    public static android.graphics.ImageDecoder.Source createSource(android.content.ContentResolver contentResolver, android.net.Uri uri, android.content.res.Resources resources) {
        return new android.graphics.ImageDecoder.ContentResolverSource(contentResolver, uri, resources);
    }

    public static android.graphics.ImageDecoder.Source createSource(android.content.res.AssetManager assetManager, java.lang.String str) {
        return new android.graphics.ImageDecoder.AssetSource(assetManager, str);
    }

    public static android.graphics.ImageDecoder.Source createSource(byte[] bArr, int i, int i2) throws java.lang.ArrayIndexOutOfBoundsException {
        if (bArr == null) {
            throw new java.lang.NullPointerException("null byte[] in createSource!");
        }
        if (i < 0 || i2 < 0 || i >= bArr.length || i + i2 > bArr.length) {
            throw new java.lang.ArrayIndexOutOfBoundsException("invalid offset/length!");
        }
        return new android.graphics.ImageDecoder.ByteArraySource(bArr, i, i2);
    }

    public static android.graphics.ImageDecoder.Source createSource(byte[] bArr) {
        return createSource(bArr, 0, bArr.length);
    }

    public static android.graphics.ImageDecoder.Source createSource(java.nio.ByteBuffer byteBuffer) {
        return new android.graphics.ImageDecoder.ByteBufferSource(byteBuffer);
    }

    public static android.graphics.ImageDecoder.Source createSource(android.content.res.Resources resources, java.io.InputStream inputStream) {
        return new android.graphics.ImageDecoder.InputStreamSource(resources, inputStream, android.graphics.Bitmap.getDefaultDensity());
    }

    public static android.graphics.ImageDecoder.Source createSource(android.content.res.Resources resources, java.io.InputStream inputStream, int i) {
        return new android.graphics.ImageDecoder.InputStreamSource(resources, inputStream, i);
    }

    public static android.graphics.ImageDecoder.Source createSource(java.io.File file) {
        return new android.graphics.ImageDecoder.FileSource(file);
    }

    public static android.graphics.ImageDecoder.Source createSource(java.util.concurrent.Callable<android.content.res.AssetFileDescriptor> callable) {
        return new android.graphics.ImageDecoder.CallableSource(callable);
    }

    private android.util.Size getSampledSize(int i) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("sampleSize must be positive! provided " + i);
        }
        if (this.mNativePtr == 0) {
            throw new java.lang.IllegalStateException("ImageDecoder is closed!");
        }
        return nGetSampledSize(this.mNativePtr, i);
    }

    public void setTargetSize(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            throw new java.lang.IllegalArgumentException("Dimensions must be positive! provided (" + i + ", " + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mDesiredWidth = i;
        this.mDesiredHeight = i2;
    }

    private int getTargetDimension(int i, int i2, int i3) {
        if (i2 >= i) {
            return 1;
        }
        int i4 = i / i2;
        if (i3 == i4) {
            return i3;
        }
        if (java.lang.Math.abs((i3 * i2) - i) < i2) {
            return i3;
        }
        return i4;
    }

    public void setTargetSampleSize(int i) {
        android.util.Size sampledSize = getSampledSize(i);
        setTargetSize(getTargetDimension(this.mWidth, i, sampledSize.getWidth()), getTargetDimension(this.mHeight, i, sampledSize.getHeight()));
    }

    private boolean requestedResize() {
        return (this.mWidth == this.mDesiredWidth && this.mHeight == this.mDesiredHeight) ? false : true;
    }

    public void setAllocator(int i) {
        if (i < 0 || i > 3) {
            throw new java.lang.IllegalArgumentException("invalid allocator " + i);
        }
        this.mAllocator = i;
    }

    public int getAllocator() {
        return this.mAllocator;
    }

    public void setUnpremultipliedRequired(boolean z) {
        this.mUnpremultipliedRequired = z;
    }

    public boolean isUnpremultipliedRequired() {
        return this.mUnpremultipliedRequired;
    }

    public void setPostProcessor(android.graphics.PostProcessor postProcessor) {
        this.mPostProcessor = postProcessor;
    }

    public android.graphics.PostProcessor getPostProcessor() {
        return this.mPostProcessor;
    }

    public void setOnPartialImageListener(android.graphics.ImageDecoder.OnPartialImageListener onPartialImageListener) {
        this.mOnPartialImageListener = onPartialImageListener;
    }

    public android.graphics.ImageDecoder.OnPartialImageListener getOnPartialImageListener() {
        return this.mOnPartialImageListener;
    }

    public void setCrop(android.graphics.Rect rect) {
        this.mCropRect = rect;
    }

    public android.graphics.Rect getCrop() {
        return this.mCropRect;
    }

    public void setOutPaddingRect(android.graphics.Rect rect) {
        this.mOutPaddingRect = rect;
    }

    public void setMutableRequired(boolean z) {
        this.mMutable = z;
    }

    public boolean isMutableRequired() {
        return this.mMutable;
    }

    public void setMemorySizePolicy(int i) {
        this.mConserveMemory = i == 0;
    }

    public int getMemorySizePolicy() {
        return !this.mConserveMemory ? 1 : 0;
    }

    public void setDecodeAsAlphaMaskEnabled(boolean z) {
        this.mDecodeAsAlphaMask = z;
    }

    public boolean isDecodeAsAlphaMaskEnabled() {
        return this.mDecodeAsAlphaMask;
    }

    public void setTargetColorSpace(android.graphics.ColorSpace colorSpace) {
        this.mDesiredColorSpace = colorSpace;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mCloseGuard.close();
        if (!this.mClosed.compareAndSet(false, true)) {
            return;
        }
        nClose(this.mNativePtr);
        this.mNativePtr = 0L;
        if (this.mOwnsInputStream) {
            libcore.io.IoUtils.closeQuietly(this.mInputStream);
        }
        libcore.io.IoUtils.closeQuietly(this.mAssetFd);
        this.mInputStream = null;
        this.mAssetFd = null;
        this.mTempStorage = null;
    }

    private void checkState(boolean z) {
        if (this.mNativePtr == 0) {
            throw new java.lang.IllegalStateException("Cannot use closed ImageDecoder!");
        }
        checkSubset(this.mDesiredWidth, this.mDesiredHeight, this.mCropRect);
        if (!z && this.mAllocator == 3) {
            if (this.mMutable) {
                throw new java.lang.IllegalStateException("Cannot make mutable HARDWARE Bitmap!");
            }
            if (this.mDecodeAsAlphaMask) {
                throw new java.lang.IllegalStateException("Cannot make HARDWARE Alpha mask Bitmap!");
            }
        }
        if (this.mPostProcessor != null && this.mUnpremultipliedRequired) {
            throw new java.lang.IllegalStateException("Cannot draw to unpremultiplied pixels!");
        }
    }

    private static void checkSubset(int i, int i2, android.graphics.Rect rect) {
        if (rect == null) {
            return;
        }
        if (rect.width() <= 0 || rect.height() <= 0) {
            throw new java.lang.IllegalStateException("Subset " + rect + " is empty/unsorted");
        }
        if (rect.left < 0 || rect.top < 0 || rect.right > i || rect.bottom > i2) {
            throw new java.lang.IllegalStateException("Subset " + rect + " not contained by scaled image bounds: (" + i + " x " + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    private boolean checkForExtended() {
        if (this.mDesiredColorSpace == null) {
            return false;
        }
        return this.mDesiredColorSpace == android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.EXTENDED_SRGB) || this.mDesiredColorSpace == android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.LINEAR_EXTENDED_SRGB);
    }

    private long getColorSpacePtr() {
        if (this.mDesiredColorSpace == null) {
            return 0L;
        }
        return this.mDesiredColorSpace.getNativeInstance();
    }

    private android.graphics.Bitmap decodeBitmapInternal() throws java.io.IOException {
        checkState(false);
        return nDecodeBitmap(this.mNativePtr, this, this.mPostProcessor != null, this.mDesiredWidth, this.mDesiredHeight, this.mCropRect, this.mMutable, this.mAllocator, this.mUnpremultipliedRequired, this.mConserveMemory, this.mDecodeAsAlphaMask, getColorSpacePtr(), checkForExtended());
    }

    private void callHeaderDecoded(android.graphics.ImageDecoder.OnHeaderDecodedListener onHeaderDecodedListener, android.graphics.ImageDecoder.Source source) {
        if (onHeaderDecodedListener != null) {
            onHeaderDecodedListener.onHeaderDecoded(this, new android.graphics.ImageDecoder.ImageInfo(new android.util.Size(this.mWidth, this.mHeight), this.mAnimated, getMimeType(), getColorSpace()), source);
        }
    }

    public static android.graphics.ImageDecoder.ImageInfo decodeHeader(android.graphics.ImageDecoder.Source source) throws java.io.IOException {
        android.os.Trace.traceBegin(8192L, "ImageDecoder#decodeHeader");
        try {
            android.graphics.ImageDecoder createImageDecoder = source.createImageDecoder(true);
            try {
                android.graphics.ImageDecoder.ImageInfo imageInfo = new android.graphics.ImageDecoder.ImageInfo(new android.util.Size(createImageDecoder.mWidth, createImageDecoder.mHeight), createImageDecoder.mAnimated, createImageDecoder.getMimeType(), createImageDecoder.getColorSpace());
                if (createImageDecoder != null) {
                    createImageDecoder.close();
                }
                return imageInfo;
            } finally {
            }
        } finally {
            android.os.Trace.traceEnd(8192L);
        }
    }

    public static android.graphics.drawable.Drawable decodeDrawable(android.graphics.ImageDecoder.Source source, android.graphics.ImageDecoder.OnHeaderDecodedListener onHeaderDecodedListener) throws java.io.IOException {
        if (onHeaderDecodedListener == null) {
            throw new java.lang.IllegalArgumentException("listener cannot be null! Use decodeDrawable(Source) to not have a listener");
        }
        return decodeDrawableImpl(source, onHeaderDecodedListener);
    }

    private static android.graphics.drawable.Drawable decodeDrawableImpl(android.graphics.ImageDecoder.Source source, android.graphics.ImageDecoder.OnHeaderDecodedListener onHeaderDecodedListener) throws java.io.IOException {
        android.os.Trace.traceBegin(8192L, "ImageDecoder#decodeDrawable");
        try {
            android.graphics.ImageDecoder createImageDecoder = source.createImageDecoder(true);
            try {
                createImageDecoder.mSource = source;
                createImageDecoder.callHeaderDecoded(onHeaderDecodedListener, source);
                android.graphics.ImageDecoder.ImageDecoderSourceTrace imageDecoderSourceTrace = new android.graphics.ImageDecoder.ImageDecoderSourceTrace(createImageDecoder);
                try {
                    if (createImageDecoder.mUnpremultipliedRequired) {
                        throw new java.lang.IllegalStateException("Cannot decode a Drawable with unpremultiplied pixels!");
                    }
                    if (createImageDecoder.mMutable) {
                        throw new java.lang.IllegalStateException("Cannot decode a mutable Drawable!");
                    }
                    int computeDensity = createImageDecoder.computeDensity(source);
                    if (createImageDecoder.mAnimated) {
                        android.graphics.ImageDecoder imageDecoder = createImageDecoder.mPostProcessor == null ? null : createImageDecoder;
                        createImageDecoder.checkState(true);
                        android.graphics.drawable.AnimatedImageDrawable animatedImageDrawable = new android.graphics.drawable.AnimatedImageDrawable(createImageDecoder.mNativePtr, imageDecoder, createImageDecoder.mDesiredWidth, createImageDecoder.mDesiredHeight, createImageDecoder.getColorSpacePtr(), createImageDecoder.checkForExtended(), computeDensity, source.computeDstDensity(), createImageDecoder.mCropRect, createImageDecoder.mInputStream, createImageDecoder.mAssetFd);
                        createImageDecoder.mInputStream = null;
                        createImageDecoder.mAssetFd = null;
                        imageDecoderSourceTrace.close();
                        if (createImageDecoder != null) {
                            createImageDecoder.close();
                        }
                        return animatedImageDrawable;
                    }
                    android.graphics.Bitmap decodeBitmapInternal = createImageDecoder.decodeBitmapInternal();
                    decodeBitmapInternal.setDensity(computeDensity);
                    android.content.res.Resources resources = source.getResources();
                    byte[] ninePatchChunk = decodeBitmapInternal.getNinePatchChunk();
                    if (ninePatchChunk == null || !android.graphics.NinePatch.isNinePatchChunk(ninePatchChunk)) {
                        android.graphics.drawable.BitmapDrawable bitmapDrawable = new android.graphics.drawable.BitmapDrawable(resources, decodeBitmapInternal);
                        imageDecoderSourceTrace.close();
                        if (createImageDecoder != null) {
                            createImageDecoder.close();
                        }
                        return bitmapDrawable;
                    }
                    android.graphics.Rect rect = new android.graphics.Rect();
                    decodeBitmapInternal.getOpticalInsets(rect);
                    android.graphics.Rect rect2 = createImageDecoder.mOutPaddingRect;
                    android.graphics.Rect rect3 = rect2 == null ? new android.graphics.Rect() : rect2;
                    nGetPadding(createImageDecoder.mNativePtr, rect3);
                    android.graphics.drawable.NinePatchDrawable ninePatchDrawable = new android.graphics.drawable.NinePatchDrawable(resources, decodeBitmapInternal, ninePatchChunk, rect3, rect, null);
                    imageDecoderSourceTrace.close();
                    if (createImageDecoder != null) {
                        createImageDecoder.close();
                    }
                    return ninePatchDrawable;
                } finally {
                }
            } finally {
            }
        } finally {
            android.os.Trace.traceEnd(8192L);
        }
    }

    public static android.graphics.drawable.Drawable decodeDrawable(android.graphics.ImageDecoder.Source source) throws java.io.IOException {
        return decodeDrawableImpl(source, null);
    }

    public static android.graphics.Bitmap decodeBitmap(android.graphics.ImageDecoder.Source source, android.graphics.ImageDecoder.OnHeaderDecodedListener onHeaderDecodedListener) throws java.io.IOException {
        if (onHeaderDecodedListener == null) {
            throw new java.lang.IllegalArgumentException("listener cannot be null! Use decodeBitmap(Source) to not have a listener");
        }
        return decodeBitmapImpl(source, onHeaderDecodedListener);
    }

    private static android.graphics.Bitmap decodeBitmapImpl(android.graphics.ImageDecoder.Source source, android.graphics.ImageDecoder.OnHeaderDecodedListener onHeaderDecodedListener) throws java.io.IOException {
        byte[] ninePatchChunk;
        android.os.Trace.traceBegin(8192L, "ImageDecoder#decodeBitmap");
        try {
            android.graphics.ImageDecoder createImageDecoder = source.createImageDecoder(false);
            try {
                createImageDecoder.mSource = source;
                createImageDecoder.callHeaderDecoded(onHeaderDecodedListener, source);
                android.graphics.ImageDecoder.ImageDecoderSourceTrace imageDecoderSourceTrace = new android.graphics.ImageDecoder.ImageDecoderSourceTrace(createImageDecoder);
                try {
                    int computeDensity = createImageDecoder.computeDensity(source);
                    android.graphics.Bitmap decodeBitmapInternal = createImageDecoder.decodeBitmapInternal();
                    decodeBitmapInternal.setDensity(computeDensity);
                    android.graphics.Rect rect = createImageDecoder.mOutPaddingRect;
                    if (rect != null && (ninePatchChunk = decodeBitmapInternal.getNinePatchChunk()) != null && android.graphics.NinePatch.isNinePatchChunk(ninePatchChunk)) {
                        nGetPadding(createImageDecoder.mNativePtr, rect);
                    }
                    imageDecoderSourceTrace.close();
                    if (createImageDecoder != null) {
                        createImageDecoder.close();
                    }
                    return decodeBitmapInternal;
                } finally {
                }
            } finally {
            }
        } finally {
            android.os.Trace.traceEnd(8192L);
        }
    }

    private static java.lang.AutoCloseable traceDecoderSource(android.graphics.ImageDecoder imageDecoder) {
        final boolean isTagEnabled = android.os.Trace.isTagEnabled(8192L);
        if (isTagEnabled) {
            android.os.Trace.traceBegin(8192L, describeDecoderForTrace(imageDecoder));
        }
        return new java.lang.AutoCloseable() { // from class: android.graphics.ImageDecoder.1
            @Override // java.lang.AutoCloseable
            public void close() throws java.lang.Exception {
                if (isTagEnabled) {
                    android.os.Trace.traceEnd(8192L);
                }
            }
        };
    }

    private int computeDensity(android.graphics.ImageDecoder.Source source) {
        if (requestedResize()) {
            return 0;
        }
        int density = source.getDensity();
        if (density == 0) {
            return density;
        }
        if (this.mIsNinePatch && this.mPostProcessor == null) {
            return density;
        }
        android.content.res.Resources resources = source.getResources();
        if (resources != null && resources.getDisplayMetrics().noncompatDensityDpi == density) {
            return density;
        }
        int computeDstDensity = source.computeDstDensity();
        if (density == computeDstDensity) {
            return density;
        }
        if (density < computeDstDensity && android.graphics.Compatibility.getTargetSdkVersion() >= 28) {
            return density;
        }
        float f = computeDstDensity / density;
        setTargetSize(java.lang.Math.max((int) ((this.mWidth * f) + 0.5f), 1), java.lang.Math.max((int) ((this.mHeight * f) + 0.5f), 1));
        return computeDstDensity;
    }

    private java.lang.String getMimeType() {
        return nGetMimeType(this.mNativePtr);
    }

    private android.graphics.ColorSpace getColorSpace() {
        return nGetColorSpace(this.mNativePtr);
    }

    public static android.graphics.Bitmap decodeBitmap(android.graphics.ImageDecoder.Source source) throws java.io.IOException {
        return decodeBitmapImpl(source, null);
    }

    private static boolean isHevcDecoderSupported() {
        synchronized (sIsHevcDecoderSupportedLock) {
            if (sIsHevcDecoderSupportedInitialized) {
                return sIsHevcDecoderSupported;
            }
            android.media.MediaFormat mediaFormat = new android.media.MediaFormat();
            mediaFormat.setString(android.media.MediaFormat.KEY_MIME, android.media.MediaFormat.MIMETYPE_VIDEO_HEVC);
            sIsHevcDecoderSupported = new android.media.MediaCodecList(0).findDecoderForFormat(mediaFormat) != null;
            sIsHevcDecoderSupportedInitialized = true;
            return sIsHevcDecoderSupported;
        }
    }

    private static boolean isP010SupportedForAV1() {
        synchronized (sIsP010SupportedLock) {
            if (sIsP010SupportedFlagsInitialized) {
                return sIsP010SupportedForAV1;
            }
            checkP010SupportforAV1HEVC();
            return sIsP010SupportedForAV1;
        }
    }

    private static boolean isP010SupportedForHEVC() {
        synchronized (sIsP010SupportedLock) {
            if (sIsP010SupportedFlagsInitialized) {
                return sIsP010SupportedForHEVC;
            }
            checkP010SupportforAV1HEVC();
            return sIsP010SupportedForHEVC;
        }
    }

    private static void checkP010SupportforAV1HEVC() {
        for (android.media.MediaCodecInfo mediaCodecInfo : new android.media.MediaCodecList(0).getCodecInfos()) {
            if (!mediaCodecInfo.isEncoder()) {
                for (java.lang.String str : mediaCodecInfo.getSupportedTypes()) {
                    if (str.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_VIDEO_AV1) || str.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_VIDEO_HEVC)) {
                        android.media.MediaCodecInfo.CodecCapabilities capabilitiesForType = mediaCodecInfo.getCapabilitiesForType(str);
                        for (int i = 0; i < capabilitiesForType.colorFormats.length; i++) {
                            if (capabilitiesForType.colorFormats[i] == 54) {
                                if (str.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_VIDEO_AV1)) {
                                    sIsP010SupportedForAV1 = true;
                                } else {
                                    sIsP010SupportedForHEVC = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        sIsP010SupportedFlagsInitialized = true;
    }

    private int postProcessAndRelease(android.graphics.Canvas canvas) {
        try {
            return this.mPostProcessor.onPostProcess(canvas);
        } finally {
            canvas.release();
        }
    }

    private void onPartialImage(int i, java.lang.Throwable th) throws android.graphics.ImageDecoder.DecodeException {
        android.graphics.ImageDecoder.DecodeException decodeException = new android.graphics.ImageDecoder.DecodeException(i, th, this.mSource);
        if (this.mOnPartialImageListener != null) {
            if (!this.mOnPartialImageListener.onPartialImage(decodeException)) {
                throw decodeException;
            }
            return;
        }
        throw decodeException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String describeDecoderForTrace(android.graphics.ImageDecoder imageDecoder) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("ID#w=");
        sb.append(imageDecoder.mWidth);
        sb.append(";h=");
        sb.append(imageDecoder.mHeight);
        if (imageDecoder.mDesiredWidth != imageDecoder.mWidth || imageDecoder.mDesiredHeight != imageDecoder.mHeight) {
            sb.append(";dw=");
            sb.append(imageDecoder.mDesiredWidth);
            sb.append(";dh=");
            sb.append(imageDecoder.mDesiredHeight);
        }
        sb.append(";src=");
        sb.append(imageDecoder.mSource);
        return sb.toString();
    }

    private static final class ImageDecoderSourceTrace implements java.lang.AutoCloseable {
        private final boolean mResourceTracingEnabled = android.os.Trace.isTagEnabled(8192);

        ImageDecoderSourceTrace(android.graphics.ImageDecoder imageDecoder) {
            if (this.mResourceTracingEnabled) {
                android.os.Trace.traceBegin(8192L, android.graphics.ImageDecoder.describeDecoderForTrace(imageDecoder));
            }
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            if (this.mResourceTracingEnabled) {
                android.os.Trace.traceEnd(8192L);
            }
        }
    }
}
