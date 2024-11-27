package android.graphics;

/* loaded from: classes.dex */
public final class Bitmap implements android.os.Parcelable {
    public static final int DENSITY_NONE = 0;
    private static final long NATIVE_ALLOCATION_SIZE = 32;
    private static final java.lang.String TAG = "Bitmap";
    private static final int WORKING_COMPRESS_STORAGE = 4096;
    private android.graphics.ColorSpace mColorSpace;
    int mDensity;
    private android.graphics.Gainmap mGainmap;
    private java.lang.ref.WeakReference<android.hardware.HardwareBuffer> mHardwareBuffer;
    private int mHeight;
    private final long mNativePtr;
    private byte[] mNinePatchChunk;
    private android.graphics.NinePatch.InsetStruct mNinePatchInsets;
    private boolean mRecycled;
    private boolean mRequestPremultiplied;
    private int mWidth;
    private static volatile int sDefaultDensity = -1;
    public static final android.os.Parcelable.Creator<android.graphics.Bitmap> CREATOR = new android.os.Parcelable.Creator<android.graphics.Bitmap>() { // from class: android.graphics.Bitmap.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.Bitmap createFromParcel(android.os.Parcel parcel) {
            android.graphics.Bitmap nativeCreateFromParcel = android.graphics.Bitmap.nativeCreateFromParcel(parcel);
            if (nativeCreateFromParcel == null) {
                throw new java.lang.RuntimeException("Failed to unparcel Bitmap");
            }
            if (parcel.readBoolean()) {
                nativeCreateFromParcel.setGainmap((android.graphics.Gainmap) parcel.readTypedObject(android.graphics.Gainmap.CREATOR));
            }
            return nativeCreateFromParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.Bitmap[] newArray(int i) {
            return new android.graphics.Bitmap[i];
        }
    };

    private static native boolean nativeCompress(long j, int i, int i2, java.io.OutputStream outputStream, byte[] bArr);

    private static native android.graphics.ColorSpace nativeComputeColorSpace(long j);

    private static native int nativeConfig(long j);

    private static native android.graphics.Bitmap nativeCopy(long j, int i, boolean z);

    private static native android.graphics.Bitmap nativeCopyAshmem(long j);

    private static native android.graphics.Bitmap nativeCopyAshmemConfig(long j, int i);

    private static native void nativeCopyPixelsFromBuffer(long j, java.nio.Buffer buffer);

    private static native void nativeCopyPixelsToBuffer(long j, java.nio.Buffer buffer);

    private static native android.graphics.Bitmap nativeCopyPreserveInternalConfig(long j);

    private static native android.graphics.Bitmap nativeCreate(int[] iArr, int i, int i2, int i3, int i4, int i5, boolean z, long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native android.graphics.Bitmap nativeCreateFromParcel(android.os.Parcel parcel);

    private static native void nativeErase(long j, int i);

    private static native void nativeErase(long j, long j2, long j3);

    private static native android.graphics.Bitmap nativeExtractAlpha(long j, long j2, int[] iArr);

    private static native android.graphics.Gainmap nativeExtractGainmap(long j);

    private static native int nativeGenerationId(long j);

    private static native int nativeGetAllocationByteCount(long j);

    private static native int nativeGetAshmemFD(long j);

    private static native long nativeGetColor(long j, int i, int i2);

    private static native android.hardware.HardwareBuffer nativeGetHardwareBuffer(long j);

    private static native long nativeGetNativeFinalizer();

    private static native int nativeGetPixel(long j, int i, int i2);

    private static native void nativeGetPixels(long j, int[] iArr, int i, int i2, int i3, int i4, int i5, int i6);

    private static native boolean nativeHasAlpha(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nativeHasGainmap(long j);

    private static native boolean nativeHasMipMap(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nativeIsBackedByAshmem(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nativeIsImmutable(long j);

    private static native boolean nativeIsPremultiplied(long j);

    private static native boolean nativeIsSRGB(long j);

    private static native boolean nativeIsSRGBLinear(long j);

    private static native void nativePrepareToDraw(long j);

    private static native void nativeReconfigure(long j, int i, int i2, int i3, boolean z);

    private static native void nativeRecycle(long j);

    private static native int nativeRowBytes(long j);

    private static native boolean nativeSameAs(long j, long j2);

    private static native void nativeSetColorSpace(long j, long j2);

    private static native void nativeSetGainmap(long j, long j2);

    private static native void nativeSetHasAlpha(long j, boolean z, boolean z2);

    private static native void nativeSetHasMipMap(long j, boolean z);

    private static native void nativeSetImmutable(long j);

    private static native void nativeSetPixel(long j, int i, int i2, int i3);

    private static native void nativeSetPixels(long j, int[] iArr, int i, int i2, int i3, int i4, int i5, int i6);

    private static native void nativeSetPremultiplied(long j, boolean z);

    private static native android.graphics.Bitmap nativeWrapHardwareBufferBitmap(android.hardware.HardwareBuffer hardwareBuffer, long j);

    private static native boolean nativeWriteToParcel(long j, int i, android.os.Parcel parcel);

    public static void setDefaultDensity(int i) {
        sDefaultDensity = i;
    }

    static int getDefaultDensity() {
        if (sDefaultDensity >= 0) {
            return sDefaultDensity;
        }
        sDefaultDensity = android.util.DisplayMetrics.DENSITY_DEVICE;
        return sDefaultDensity;
    }

    Bitmap(long j, int i, int i2, int i3, boolean z, byte[] bArr, android.graphics.NinePatch.InsetStruct insetStruct) {
        this(j, i, i2, i3, z, bArr, insetStruct, true);
    }

    Bitmap(long j, int i, int i2, int i3, boolean z, byte[] bArr, android.graphics.NinePatch.InsetStruct insetStruct, boolean z2) {
        libcore.util.NativeAllocationRegistry createNonmalloced;
        this.mDensity = getDefaultDensity();
        if (j == 0) {
            throw new java.lang.RuntimeException("internal error: native bitmap is 0");
        }
        this.mWidth = i;
        this.mHeight = i2;
        this.mRequestPremultiplied = z;
        this.mNinePatchChunk = bArr;
        this.mNinePatchInsets = insetStruct;
        if (i3 >= 0) {
            this.mDensity = i3;
        }
        this.mNativePtr = j;
        int allocationByteCount = getAllocationByteCount();
        if (z2) {
            createNonmalloced = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.Bitmap.class.getClassLoader(), nativeGetNativeFinalizer(), allocationByteCount);
        } else {
            createNonmalloced = libcore.util.NativeAllocationRegistry.createNonmalloced(android.graphics.Bitmap.class.getClassLoader(), nativeGetNativeFinalizer(), allocationByteCount);
        }
        createNonmalloced.registerNativeAllocation(this, j);
    }

    public long getNativeInstance() {
        return this.mNativePtr;
    }

    void reinit(int i, int i2, boolean z) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mRequestPremultiplied = z;
        this.mColorSpace = null;
    }

    public int getDensity() {
        if (this.mRecycled) {
            android.util.Log.w(TAG, "Called getDensity() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return this.mDensity;
    }

    public void setDensity(int i) {
        this.mDensity = i;
    }

    public void reconfigure(int i, int i2, android.graphics.Bitmap.Config config) {
        checkRecycled("Can't call reconfigure() on a recycled bitmap");
        if (i <= 0 || i2 <= 0) {
            throw new java.lang.IllegalArgumentException("width and height must be > 0");
        }
        if (!isMutable()) {
            throw new java.lang.IllegalStateException("only mutable bitmaps may be reconfigured");
        }
        nativeReconfigure(this.mNativePtr, i, i2, config.nativeInt, this.mRequestPremultiplied);
        this.mWidth = i;
        this.mHeight = i2;
        this.mColorSpace = null;
    }

    public void setWidth(int i) {
        reconfigure(i, getHeight(), getConfig());
    }

    public void setHeight(int i) {
        reconfigure(getWidth(), i, getConfig());
    }

    public void setConfig(android.graphics.Bitmap.Config config) {
        reconfigure(getWidth(), getHeight(), config);
    }

    private void setNinePatchChunk(byte[] bArr) {
        this.mNinePatchChunk = bArr;
    }

    public void recycle() {
        if (!this.mRecycled) {
            nativeRecycle(this.mNativePtr);
            this.mNinePatchChunk = null;
            this.mRecycled = true;
            this.mHardwareBuffer = null;
        }
    }

    public final boolean isRecycled() {
        return this.mRecycled;
    }

    public int getGenerationId() {
        if (this.mRecycled) {
            android.util.Log.w(TAG, "Called getGenerationId() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeGenerationId(this.mNativePtr);
    }

    void checkRecycled(java.lang.String str) {
        if (this.mRecycled) {
            throw new java.lang.IllegalStateException(str);
        }
    }

    private void checkHardware(java.lang.String str) {
        if (getConfig() == android.graphics.Bitmap.Config.HARDWARE) {
            throw new java.lang.IllegalStateException(str);
        }
    }

    private static void checkXYSign(int i, int i2) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("x must be >= 0");
        }
        if (i2 < 0) {
            throw new java.lang.IllegalArgumentException("y must be >= 0");
        }
    }

    private static void checkWidthHeight(int i, int i2) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("width must be > 0");
        }
        if (i2 <= 0) {
            throw new java.lang.IllegalArgumentException("height must be > 0");
        }
    }

    public enum Config {
        ALPHA_8(1),
        RGB_565(3),
        ARGB_4444(4),
        ARGB_8888(5),
        RGBA_F16(6),
        HARDWARE(7),
        RGBA_1010102(8);

        final int nativeInt;
        private static android.graphics.Bitmap.Config[] sConfigs = {null, ALPHA_8, null, RGB_565, ARGB_4444, ARGB_8888, RGBA_F16, HARDWARE, RGBA_1010102};

        Config(int i) {
            this.nativeInt = i;
        }

        static android.graphics.Bitmap.Config nativeToConfig(int i) {
            return sConfigs[i];
        }
    }

    public void copyPixelsToBuffer(java.nio.Buffer buffer) {
        char c;
        checkHardware("unable to copyPixelsToBuffer, pixel access is not supported on Config#HARDWARE bitmaps");
        int remaining = buffer.remaining();
        if (buffer instanceof java.nio.ByteBuffer) {
            c = 0;
        } else if (buffer instanceof java.nio.ShortBuffer) {
            c = 1;
        } else if (buffer instanceof java.nio.IntBuffer) {
            c = 2;
        } else {
            throw new java.lang.RuntimeException("unsupported Buffer subclass");
        }
        long j = remaining << c;
        long byteCount = getByteCount();
        if (j < byteCount) {
            throw new java.lang.RuntimeException("Buffer not large enough for pixels");
        }
        nativeCopyPixelsToBuffer(this.mNativePtr, buffer);
        buffer.position((int) (buffer.position() + (byteCount >> c)));
    }

    public void copyPixelsFromBuffer(java.nio.Buffer buffer) {
        char c;
        checkRecycled("copyPixelsFromBuffer called on recycled bitmap");
        checkHardware("unable to copyPixelsFromBuffer, Config#HARDWARE bitmaps are immutable");
        int remaining = buffer.remaining();
        if (buffer instanceof java.nio.ByteBuffer) {
            c = 0;
        } else if (buffer instanceof java.nio.ShortBuffer) {
            c = 1;
        } else if (buffer instanceof java.nio.IntBuffer) {
            c = 2;
        } else {
            throw new java.lang.RuntimeException("unsupported Buffer subclass");
        }
        long j = remaining << c;
        long byteCount = getByteCount();
        if (j < byteCount) {
            throw new java.lang.RuntimeException("Buffer not large enough for pixels");
        }
        nativeCopyPixelsFromBuffer(this.mNativePtr, buffer);
        buffer.position((int) (buffer.position() + (byteCount >> c)));
    }

    private void noteHardwareBitmapSlowCall() {
        if (getConfig() == android.graphics.Bitmap.Config.HARDWARE) {
            android.os.StrictMode.noteSlowCall("Warning: attempt to read pixels from hardware bitmap, which is very slow operation");
        }
    }

    public android.graphics.Bitmap copy(android.graphics.Bitmap.Config config, boolean z) {
        checkRecycled("Can't copy a recycled bitmap");
        if (config == android.graphics.Bitmap.Config.HARDWARE && z) {
            throw new java.lang.IllegalArgumentException("Hardware bitmaps are always immutable");
        }
        noteHardwareBitmapSlowCall();
        android.graphics.Bitmap nativeCopy = nativeCopy(this.mNativePtr, config.nativeInt, z);
        if (nativeCopy != null) {
            nativeCopy.setPremultiplied(this.mRequestPremultiplied);
            nativeCopy.mDensity = this.mDensity;
        }
        return nativeCopy;
    }

    public android.graphics.Bitmap createAshmemBitmap() {
        checkRecycled("Can't copy a recycled bitmap");
        noteHardwareBitmapSlowCall();
        android.graphics.Bitmap nativeCopyAshmem = nativeCopyAshmem(this.mNativePtr);
        if (nativeCopyAshmem != null) {
            nativeCopyAshmem.setPremultiplied(this.mRequestPremultiplied);
            nativeCopyAshmem.mDensity = this.mDensity;
        }
        return nativeCopyAshmem;
    }

    public android.graphics.Bitmap asShared() {
        if (nativeIsBackedByAshmem(this.mNativePtr) && nativeIsImmutable(this.mNativePtr)) {
            return this;
        }
        android.graphics.Bitmap createAshmemBitmap = createAshmemBitmap();
        if (createAshmemBitmap == null) {
            throw new java.lang.RuntimeException("Failed to create shared Bitmap!");
        }
        return createAshmemBitmap;
    }

    public android.os.SharedMemory getSharedMemory() {
        checkRecycled("Cannot access shared memory of a recycled bitmap");
        if (nativeIsBackedByAshmem(this.mNativePtr)) {
            try {
                return android.os.SharedMemory.fromFileDescriptor(android.os.ParcelFileDescriptor.fromFd(nativeGetAshmemFD(this.mNativePtr)));
            } catch (java.io.IOException e) {
                android.util.Log.e(TAG, "Unable to create dup'd file descriptor for shared bitmap memory");
                return null;
            }
        }
        return null;
    }

    public static android.graphics.Bitmap wrapHardwareBuffer(android.hardware.HardwareBuffer hardwareBuffer, android.graphics.ColorSpace colorSpace) {
        long usage = hardwareBuffer.getUsage();
        if ((256 & usage) == 0) {
            throw new java.lang.IllegalArgumentException("usage flags must contain USAGE_GPU_SAMPLED_IMAGE.");
        }
        if ((usage & 16384) != 0) {
            throw new java.lang.IllegalArgumentException("Bitmap is not compatible with protected buffers");
        }
        if (colorSpace == null) {
            colorSpace = android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB);
        }
        android.graphics.Bitmap nativeWrapHardwareBufferBitmap = nativeWrapHardwareBufferBitmap(hardwareBuffer, colorSpace.getNativeInstance());
        if (nativeWrapHardwareBufferBitmap != null) {
            nativeWrapHardwareBufferBitmap.mHardwareBuffer = new java.lang.ref.WeakReference<>(hardwareBuffer);
        }
        return nativeWrapHardwareBufferBitmap;
    }

    public static android.graphics.Bitmap createScaledBitmap(android.graphics.Bitmap bitmap, int i, int i2, boolean z) {
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width != i || height != i2) {
            matrix.setScale(i / width, i2 / height);
        }
        return createBitmap(bitmap, 0, 0, width, height, matrix, z);
    }

    public static android.graphics.Bitmap createBitmap(android.graphics.Bitmap bitmap) {
        return createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    public static android.graphics.Bitmap createBitmap(android.graphics.Bitmap bitmap, int i, int i2, int i3, int i4) {
        return createBitmap(bitmap, i, i2, i3, i4, (android.graphics.Matrix) null, false);
    }

    public static android.graphics.Bitmap createBitmap(android.graphics.Bitmap bitmap, int i, int i2, int i3, int i4, android.graphics.Matrix matrix, boolean z) {
        android.graphics.Bitmap bitmap2;
        android.graphics.Bitmap.Config config;
        android.graphics.Bitmap bitmap3;
        android.graphics.Bitmap createBitmap;
        int i5;
        int i6;
        android.graphics.Paint paint;
        android.graphics.Bitmap transformGainmap;
        android.graphics.Bitmap.Config config2;
        android.graphics.ColorSpace colorSpace;
        checkXYSign(i, i2);
        checkWidthHeight(i3, i4);
        int i7 = i + i3;
        if (i7 > bitmap.getWidth()) {
            throw new java.lang.IllegalArgumentException("x + width must be <= bitmap.width()");
        }
        int i8 = i2 + i4;
        if (i8 > bitmap.getHeight()) {
            throw new java.lang.IllegalArgumentException("y + height must be <= bitmap.height()");
        }
        if (bitmap.isRecycled()) {
            throw new java.lang.IllegalArgumentException("cannot use a recycled source in createBitmap");
        }
        if (!bitmap.isMutable() && i == 0 && i2 == 0 && i3 == bitmap.getWidth() && i4 == bitmap.getHeight() && (matrix == null || matrix.isIdentity())) {
            return bitmap;
        }
        boolean z2 = bitmap.getConfig() == android.graphics.Bitmap.Config.HARDWARE;
        if (!z2) {
            bitmap2 = bitmap;
        } else {
            bitmap.noteHardwareBitmapSlowCall();
            bitmap2 = nativeCopyPreserveInternalConfig(bitmap.mNativePtr);
        }
        android.graphics.Rect rect = new android.graphics.Rect(i, i2, i7, i8);
        android.graphics.RectF rectF = new android.graphics.RectF(0.0f, 0.0f, i3, i4);
        android.graphics.RectF rectF2 = new android.graphics.RectF();
        android.graphics.Bitmap.Config config3 = android.graphics.Bitmap.Config.ARGB_8888;
        android.graphics.Bitmap.Config config4 = bitmap2.getConfig();
        if (config4 == null) {
            config = config3;
        } else {
            switch (config4) {
                case RGB_565:
                    config = android.graphics.Bitmap.Config.RGB_565;
                    break;
                case ALPHA_8:
                    config = android.graphics.Bitmap.Config.ALPHA_8;
                    break;
                case RGBA_F16:
                    config = android.graphics.Bitmap.Config.RGBA_F16;
                    break;
                default:
                    config = android.graphics.Bitmap.Config.ARGB_8888;
                    break;
            }
        }
        android.graphics.ColorSpace colorSpace2 = bitmap2.getColorSpace();
        if (matrix == null || matrix.isIdentity()) {
            bitmap3 = null;
            createBitmap = createBitmap((android.util.DisplayMetrics) null, i3, i4, config, bitmap2.hasAlpha(), colorSpace2);
            i5 = i3;
            i6 = i4;
            paint = null;
        } else {
            boolean z3 = !matrix.rectStaysRect();
            matrix.mapRect(rectF2, rectF);
            int round = java.lang.Math.round(rectF2.width());
            int round2 = java.lang.Math.round(rectF2.height());
            if (z3 && config != android.graphics.Bitmap.Config.ARGB_8888 && config != android.graphics.Bitmap.Config.RGBA_F16) {
                android.graphics.Bitmap.Config config5 = android.graphics.Bitmap.Config.ARGB_8888;
                if (colorSpace2 != null) {
                    config2 = config5;
                    colorSpace = colorSpace2;
                } else {
                    config2 = config5;
                    colorSpace = android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB);
                }
            } else {
                config2 = config;
                colorSpace = colorSpace2;
            }
            android.graphics.Bitmap createBitmap2 = createBitmap((android.util.DisplayMetrics) null, round, round2, config2, z3 || bitmap2.hasAlpha(), colorSpace);
            android.graphics.Paint paint2 = new android.graphics.Paint();
            paint2.setFilterBitmap(z);
            if (z3) {
                paint2.setAntiAlias(true);
            }
            createBitmap = createBitmap2;
            bitmap3 = null;
            paint = paint2;
            i6 = round2;
            i5 = round;
        }
        createBitmap.mDensity = bitmap2.mDensity;
        createBitmap.setHasAlpha(bitmap2.hasAlpha());
        createBitmap.setPremultiplied(bitmap2.mRequestPremultiplied);
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        canvas.translate(-rectF2.left, -rectF2.top);
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap2, rect, rectF, paint);
        canvas.setBitmap(bitmap3);
        if (bitmap2.hasGainmap() && (transformGainmap = transformGainmap(bitmap2, matrix, i5, i6, paint, rect, rectF, rectF2)) != null) {
            createBitmap.setGainmap(new android.graphics.Gainmap(bitmap2.getGainmap(), transformGainmap));
        }
        if (z2) {
            return createBitmap.copy(android.graphics.Bitmap.Config.HARDWARE, false);
        }
        return createBitmap;
    }

    private static android.graphics.Bitmap transformGainmap(android.graphics.Bitmap bitmap, android.graphics.Matrix matrix, int i, int i2, android.graphics.Paint paint, android.graphics.Rect rect, android.graphics.RectF rectF, android.graphics.RectF rectF2) {
        android.graphics.Bitmap gainmapContents = bitmap.getGainmap().getGainmapContents();
        float width = gainmapContents.getWidth() / bitmap.getWidth();
        float height = gainmapContents.getHeight() / bitmap.getHeight();
        int round = java.lang.Math.round(i * width);
        int round2 = java.lang.Math.round(i2 * height);
        if (round == 0 || round2 == 0) {
            return null;
        }
        android.graphics.Rect rect2 = new android.graphics.Rect((int) (rect.left * width), (int) (rect.top * height), (int) (rect.right * width), (int) (rect.bottom * height));
        android.graphics.Bitmap nativeCreate = nativeCreate(null, 0, round, round, round2, gainmapContents.getConfig().nativeInt, true, 0L);
        nativeCreate.eraseColor(0);
        android.graphics.Canvas canvas = new android.graphics.Canvas(nativeCreate);
        canvas.scale(width, height);
        canvas.translate(-rectF2.left, -rectF2.top);
        canvas.concat(matrix);
        canvas.drawBitmap(gainmapContents, rect2, rectF, paint);
        canvas.setBitmap(null);
        return nativeCreate;
    }

    public static android.graphics.Bitmap createBitmap(int i, int i2, android.graphics.Bitmap.Config config) {
        return createBitmap(i, i2, config, true);
    }

    public static android.graphics.Bitmap createBitmap(android.util.DisplayMetrics displayMetrics, int i, int i2, android.graphics.Bitmap.Config config) {
        return createBitmap(displayMetrics, i, i2, config, true);
    }

    public static android.graphics.Bitmap createBitmap(int i, int i2, android.graphics.Bitmap.Config config, boolean z) {
        return createBitmap((android.util.DisplayMetrics) null, i, i2, config, z);
    }

    public static android.graphics.Bitmap createBitmap(int i, int i2, android.graphics.Bitmap.Config config, boolean z, android.graphics.ColorSpace colorSpace) {
        return createBitmap((android.util.DisplayMetrics) null, i, i2, config, z, colorSpace);
    }

    public static android.graphics.Bitmap createBitmap(android.util.DisplayMetrics displayMetrics, int i, int i2, android.graphics.Bitmap.Config config, boolean z) {
        return createBitmap(displayMetrics, i, i2, config, z, android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB));
    }

    public static android.graphics.Bitmap createBitmap(android.util.DisplayMetrics displayMetrics, int i, int i2, android.graphics.Bitmap.Config config, boolean z, android.graphics.ColorSpace colorSpace) {
        if (i <= 0 || i2 <= 0) {
            throw new java.lang.IllegalArgumentException("width and height must be > 0");
        }
        if (config == android.graphics.Bitmap.Config.HARDWARE) {
            throw new java.lang.IllegalArgumentException("can't create mutable bitmap with Config.HARDWARE");
        }
        if (colorSpace == null && config != android.graphics.Bitmap.Config.ALPHA_8) {
            throw new java.lang.IllegalArgumentException("can't create bitmap without a color space");
        }
        android.graphics.Bitmap nativeCreate = nativeCreate(null, 0, i, i, i2, config.nativeInt, true, colorSpace == null ? 0L : colorSpace.getNativeInstance());
        if (displayMetrics != null) {
            nativeCreate.mDensity = displayMetrics.densityDpi;
        }
        nativeCreate.setHasAlpha(z);
        if ((config == android.graphics.Bitmap.Config.ARGB_8888 || config == android.graphics.Bitmap.Config.RGBA_F16) && !z) {
            nativeErase(nativeCreate.mNativePtr, -16777216);
        }
        return nativeCreate;
    }

    public static android.graphics.Bitmap createBitmap(int[] iArr, int i, int i2, int i3, int i4, android.graphics.Bitmap.Config config) {
        return createBitmap((android.util.DisplayMetrics) null, iArr, i, i2, i3, i4, config);
    }

    public static android.graphics.Bitmap createBitmap(android.util.DisplayMetrics displayMetrics, int[] iArr, int i, int i2, int i3, int i4, android.graphics.Bitmap.Config config) {
        checkWidthHeight(i3, i4);
        if (java.lang.Math.abs(i2) < i3) {
            throw new java.lang.IllegalArgumentException("abs(stride) must be >= width");
        }
        int i5 = ((i4 - 1) * i2) + i;
        int length = iArr.length;
        if (i < 0 || i + i3 > length || i5 < 0 || i5 + i3 > length) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        if (i3 <= 0 || i4 <= 0) {
            throw new java.lang.IllegalArgumentException("width and height must be > 0");
        }
        android.graphics.Bitmap nativeCreate = nativeCreate(iArr, i, i2, i3, i4, config.nativeInt, false, android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB).getNativeInstance());
        if (displayMetrics != null) {
            nativeCreate.mDensity = displayMetrics.densityDpi;
        }
        return nativeCreate;
    }

    public static android.graphics.Bitmap createBitmap(int[] iArr, int i, int i2, android.graphics.Bitmap.Config config) {
        return createBitmap((android.util.DisplayMetrics) null, iArr, 0, i, i, i2, config);
    }

    public static android.graphics.Bitmap createBitmap(android.util.DisplayMetrics displayMetrics, int[] iArr, int i, int i2, android.graphics.Bitmap.Config config) {
        return createBitmap(displayMetrics, iArr, 0, i, i, i2, config);
    }

    public static android.graphics.Bitmap createBitmap(android.graphics.Picture picture) {
        return createBitmap(picture, picture.getWidth(), picture.getHeight(), android.graphics.Bitmap.Config.HARDWARE);
    }

    public static android.graphics.Bitmap createBitmap(android.graphics.Picture picture, int i, int i2, android.graphics.Bitmap.Config config) {
        if (i <= 0 || i2 <= 0) {
            throw new java.lang.IllegalArgumentException("width & height must be > 0");
        }
        if (config == null) {
            throw new java.lang.IllegalArgumentException("Config must not be null");
        }
        picture.endRecording();
        if (picture.requiresHardwareAcceleration() && config != android.graphics.Bitmap.Config.HARDWARE) {
            android.os.StrictMode.noteSlowCall("GPU readback");
        }
        if (config == android.graphics.Bitmap.Config.HARDWARE || picture.requiresHardwareAcceleration()) {
            android.graphics.RenderNode create = android.graphics.RenderNode.create("BitmapTemporary", null);
            create.setLeftTopRightBottom(0, 0, i, i2);
            create.setClipToBounds(false);
            create.setForceDarkAllowed(false);
            android.graphics.RecordingCanvas beginRecording = create.beginRecording(i, i2);
            if (picture.getWidth() != i || picture.getHeight() != i2) {
                beginRecording.scale(i / picture.getWidth(), i2 / picture.getHeight());
            }
            beginRecording.drawPicture(picture);
            create.endRecording();
            android.graphics.Bitmap createHardwareBitmap = android.view.ThreadedRenderer.createHardwareBitmap(create, i, i2);
            if (config != android.graphics.Bitmap.Config.HARDWARE) {
                return createHardwareBitmap.copy(config, false);
            }
            return createHardwareBitmap;
        }
        android.graphics.Bitmap createBitmap = createBitmap(i, i2, config);
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        if (picture.getWidth() != i || picture.getHeight() != i2) {
            canvas.scale(i / picture.getWidth(), i2 / picture.getHeight());
        }
        canvas.drawPicture(picture);
        canvas.setBitmap(null);
        createBitmap.setImmutable();
        return createBitmap;
    }

    public byte[] getNinePatchChunk() {
        return this.mNinePatchChunk;
    }

    public void getOpticalInsets(android.graphics.Rect rect) {
        if (this.mNinePatchInsets == null) {
            rect.setEmpty();
        } else {
            rect.set(this.mNinePatchInsets.opticalRect);
        }
    }

    public android.graphics.NinePatch.InsetStruct getNinePatchInsets() {
        return this.mNinePatchInsets;
    }

    public enum CompressFormat {
        JPEG(0),
        PNG(1),
        WEBP(2),
        WEBP_LOSSY(3),
        WEBP_LOSSLESS(4);

        final int nativeInt;

        CompressFormat(int i) {
            this.nativeInt = i;
        }
    }

    public boolean compress(android.graphics.Bitmap.CompressFormat compressFormat, int i, java.io.OutputStream outputStream) {
        checkRecycled("Can't compress a recycled bitmap");
        if (outputStream == null) {
            throw new java.lang.NullPointerException();
        }
        if (i < 0 || i > 100) {
            throw new java.lang.IllegalArgumentException("quality must be 0..100");
        }
        android.os.StrictMode.noteSlowCall("Compression of a bitmap is slow");
        android.os.Trace.traceBegin(8192L, "Bitmap.compress");
        boolean nativeCompress = nativeCompress(this.mNativePtr, compressFormat.nativeInt, i, outputStream, new byte[4096]);
        android.os.Trace.traceEnd(8192L);
        return nativeCompress;
    }

    public final boolean isMutable() {
        return !nativeIsImmutable(this.mNativePtr);
    }

    private void setImmutable() {
        if (isMutable()) {
            nativeSetImmutable(this.mNativePtr);
        }
    }

    public final boolean isPremultiplied() {
        if (this.mRecycled) {
            android.util.Log.w(TAG, "Called isPremultiplied() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeIsPremultiplied(this.mNativePtr);
    }

    public final void setPremultiplied(boolean z) {
        checkRecycled("setPremultiplied called on a recycled bitmap");
        this.mRequestPremultiplied = z;
        nativeSetPremultiplied(this.mNativePtr, z);
    }

    public final int getWidth() {
        if (this.mRecycled) {
            android.util.Log.w(TAG, "Called getWidth() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return this.mWidth;
    }

    public final int getHeight() {
        if (this.mRecycled) {
            android.util.Log.w(TAG, "Called getHeight() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return this.mHeight;
    }

    public int getScaledWidth(android.graphics.Canvas canvas) {
        return scaleFromDensity(getWidth(), this.mDensity, canvas.mDensity);
    }

    public int getScaledHeight(android.graphics.Canvas canvas) {
        return scaleFromDensity(getHeight(), this.mDensity, canvas.mDensity);
    }

    public int getScaledWidth(android.util.DisplayMetrics displayMetrics) {
        return scaleFromDensity(getWidth(), this.mDensity, displayMetrics.densityDpi);
    }

    public int getScaledHeight(android.util.DisplayMetrics displayMetrics) {
        return scaleFromDensity(getHeight(), this.mDensity, displayMetrics.densityDpi);
    }

    public int getScaledWidth(int i) {
        return scaleFromDensity(getWidth(), this.mDensity, i);
    }

    public int getScaledHeight(int i) {
        return scaleFromDensity(getHeight(), this.mDensity, i);
    }

    public static int scaleFromDensity(int i, int i2, int i3) {
        if (i2 == 0 || i3 == 0 || i2 == i3) {
            return i;
        }
        return ((i * i3) + (i2 >> 1)) / i2;
    }

    public final int getRowBytes() {
        if (this.mRecycled) {
            android.util.Log.w(TAG, "Called getRowBytes() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeRowBytes(this.mNativePtr);
    }

    public final int getByteCount() {
        if (this.mRecycled) {
            android.util.Log.w(TAG, "Called getByteCount() on a recycle()'d bitmap! This is undefined behavior!");
            return 0;
        }
        return getRowBytes() * getHeight();
    }

    public final int getAllocationByteCount() {
        if (this.mRecycled) {
            android.util.Log.w(TAG, "Called getAllocationByteCount() on a recycle()'d bitmap! This is undefined behavior!");
            return 0;
        }
        return nativeGetAllocationByteCount(this.mNativePtr);
    }

    public final android.graphics.Bitmap.Config getConfig() {
        if (this.mRecycled) {
            android.util.Log.w(TAG, "Called getConfig() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return android.graphics.Bitmap.Config.nativeToConfig(nativeConfig(this.mNativePtr));
    }

    public final boolean hasAlpha() {
        if (this.mRecycled) {
            android.util.Log.w(TAG, "Called hasAlpha() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeHasAlpha(this.mNativePtr);
    }

    public void setHasAlpha(boolean z) {
        checkRecycled("setHasAlpha called on a recycled bitmap");
        nativeSetHasAlpha(this.mNativePtr, z, this.mRequestPremultiplied);
    }

    public final boolean hasMipMap() {
        if (this.mRecycled) {
            android.util.Log.w(TAG, "Called hasMipMap() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeHasMipMap(this.mNativePtr);
    }

    public final void setHasMipMap(boolean z) {
        checkRecycled("setHasMipMap called on a recycled bitmap");
        nativeSetHasMipMap(this.mNativePtr, z);
    }

    public final android.graphics.ColorSpace getColorSpace() {
        checkRecycled("getColorSpace called on a recycled bitmap");
        if (this.mColorSpace == null) {
            this.mColorSpace = nativeComputeColorSpace(this.mNativePtr);
        }
        return this.mColorSpace;
    }

    public void setColorSpace(android.graphics.ColorSpace colorSpace) {
        checkRecycled("setColorSpace called on a recycled bitmap");
        if (colorSpace == null) {
            throw new java.lang.IllegalArgumentException("The colorSpace cannot be set to null");
        }
        if (getConfig() == android.graphics.Bitmap.Config.ALPHA_8) {
            throw new java.lang.IllegalArgumentException("Cannot set a ColorSpace on ALPHA_8");
        }
        android.graphics.ColorSpace colorSpace2 = getColorSpace();
        nativeSetColorSpace(this.mNativePtr, colorSpace.getNativeInstance());
        this.mColorSpace = null;
        android.graphics.ColorSpace colorSpace3 = getColorSpace();
        try {
            if (colorSpace2.getComponentCount() != colorSpace3.getComponentCount()) {
                throw new java.lang.IllegalArgumentException("The new ColorSpace must have the same component count as the current ColorSpace");
            }
            for (int i = 0; i < colorSpace2.getComponentCount(); i++) {
                if (colorSpace2.getMinValue(i) < colorSpace3.getMinValue(i)) {
                    throw new java.lang.IllegalArgumentException("The new ColorSpace cannot increase the minimum value for any of the components compared to the current ColorSpace. To perform this type of conversion create a new Bitmap in the desired ColorSpace and draw this Bitmap into it.");
                }
                if (colorSpace2.getMaxValue(i) > colorSpace3.getMaxValue(i)) {
                    throw new java.lang.IllegalArgumentException("The new ColorSpace cannot decrease the maximum value for any of the components compared to the current ColorSpace/ To perform this type of conversion create a new Bitmap in the desired ColorSpace and draw this Bitmap into it.");
                }
            }
        } catch (java.lang.IllegalArgumentException e) {
            this.mColorSpace = colorSpace2;
            nativeSetColorSpace(this.mNativePtr, this.mColorSpace.getNativeInstance());
            throw e;
        }
    }

    public boolean hasGainmap() {
        checkRecycled("Bitmap is recycled");
        return nativeHasGainmap(this.mNativePtr);
    }

    public android.graphics.Gainmap getGainmap() {
        checkRecycled("Bitmap is recycled");
        if (this.mGainmap == null) {
            this.mGainmap = nativeExtractGainmap(this.mNativePtr);
        }
        return this.mGainmap;
    }

    public void setGainmap(android.graphics.Gainmap gainmap) {
        checkRecycled("Bitmap is recycled");
        this.mGainmap = null;
        nativeSetGainmap(this.mNativePtr, gainmap == null ? 0L : gainmap.mNativePtr);
    }

    public void eraseColor(int i) {
        checkRecycled("Can't erase a recycled bitmap");
        if (!isMutable()) {
            throw new java.lang.IllegalStateException("cannot erase immutable bitmaps");
        }
        nativeErase(this.mNativePtr, i);
    }

    public void eraseColor(long j) {
        checkRecycled("Can't erase a recycled bitmap");
        if (!isMutable()) {
            throw new java.lang.IllegalStateException("cannot erase immutable bitmaps");
        }
        nativeErase(this.mNativePtr, android.graphics.Color.colorSpace(j).getNativeInstance(), j);
    }

    public int getPixel(int i, int i2) {
        checkRecycled("Can't call getPixel() on a recycled bitmap");
        checkHardware("unable to getPixel(), pixel access is not supported on Config#HARDWARE bitmaps");
        checkPixelAccess(i, i2);
        return nativeGetPixel(this.mNativePtr, i, i2);
    }

    private static float clamp(float f, android.graphics.ColorSpace colorSpace, int i) {
        return java.lang.Math.max(java.lang.Math.min(f, colorSpace.getMaxValue(i)), colorSpace.getMinValue(i));
    }

    public android.graphics.Color getColor(int i, int i2) {
        checkRecycled("Can't call getColor() on a recycled bitmap");
        checkHardware("unable to getColor(), pixel access is not supported on Config#HARDWARE bitmaps");
        checkPixelAccess(i, i2);
        android.graphics.ColorSpace colorSpace = getColorSpace();
        if (colorSpace == null || colorSpace.equals(android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB))) {
            return android.graphics.Color.valueOf(nativeGetPixel(this.mNativePtr, i, i2));
        }
        long nativeGetColor = nativeGetColor(this.mNativePtr, i, i2);
        return android.graphics.Color.valueOf(clamp(android.util.Half.toFloat((short) ((nativeGetColor >> 0) & 65535)), colorSpace, 0), clamp(android.util.Half.toFloat((short) ((nativeGetColor >> 16) & 65535)), colorSpace, 1), clamp(android.util.Half.toFloat((short) ((nativeGetColor >> 32) & 65535)), colorSpace, 2), android.util.Half.toFloat((short) ((nativeGetColor >> 48) & 65535)), colorSpace);
    }

    public void getPixels(int[] iArr, int i, int i2, int i3, int i4, int i5, int i6) {
        checkRecycled("Can't call getPixels() on a recycled bitmap");
        checkHardware("unable to getPixels(), pixel access is not supported on Config#HARDWARE bitmaps");
        if (i5 == 0 || i6 == 0) {
            return;
        }
        checkPixelsAccess(i3, i4, i5, i6, i, i2, iArr);
        nativeGetPixels(this.mNativePtr, iArr, i, i2, i3, i4, i5, i6);
    }

    private void checkPixelAccess(int i, int i2) {
        checkXYSign(i, i2);
        if (i >= getWidth()) {
            throw new java.lang.IllegalArgumentException("x must be < bitmap.width()");
        }
        if (i2 >= getHeight()) {
            throw new java.lang.IllegalArgumentException("y must be < bitmap.height()");
        }
    }

    private void checkPixelsAccess(int i, int i2, int i3, int i4, int i5, int i6, int[] iArr) {
        checkXYSign(i, i2);
        if (i3 < 0) {
            throw new java.lang.IllegalArgumentException("width must be >= 0");
        }
        if (i4 < 0) {
            throw new java.lang.IllegalArgumentException("height must be >= 0");
        }
        if (i + i3 > getWidth()) {
            throw new java.lang.IllegalArgumentException("x + width must be <= bitmap.width()");
        }
        if (i2 + i4 > getHeight()) {
            throw new java.lang.IllegalArgumentException("y + height must be <= bitmap.height()");
        }
        if (java.lang.Math.abs(i6) < i3) {
            throw new java.lang.IllegalArgumentException("abs(stride) must be >= width");
        }
        int i7 = ((i4 - 1) * i6) + i5;
        int length = iArr.length;
        if (i5 < 0 || i5 + i3 > length || i7 < 0 || i7 + i3 > length) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
    }

    public void setPixel(int i, int i2, int i3) {
        checkRecycled("Can't call setPixel() on a recycled bitmap");
        if (!isMutable()) {
            throw new java.lang.IllegalStateException();
        }
        checkPixelAccess(i, i2);
        nativeSetPixel(this.mNativePtr, i, i2, i3);
    }

    public void setPixels(int[] iArr, int i, int i2, int i3, int i4, int i5, int i6) {
        checkRecycled("Can't call setPixels() on a recycled bitmap");
        if (!isMutable()) {
            throw new java.lang.IllegalStateException();
        }
        if (i5 == 0 || i6 == 0) {
            return;
        }
        checkPixelsAccess(i3, i4, i5, i6, i, i2, iArr);
        nativeSetPixels(this.mNativePtr, iArr, i, i2, i3, i4, i5, i6);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        checkRecycled("Can't parcel a recycled bitmap");
        noteHardwareBitmapSlowCall();
        if (!nativeWriteToParcel(this.mNativePtr, this.mDensity, parcel)) {
            throw new java.lang.RuntimeException("native writeToParcel failed");
        }
        if (hasGainmap()) {
            parcel.writeBoolean(true);
            parcel.writeTypedObject(this.mGainmap, i);
        } else {
            parcel.writeBoolean(false);
        }
    }

    public android.graphics.Bitmap extractAlpha() {
        return extractAlpha(null, null);
    }

    public android.graphics.Bitmap extractAlpha(android.graphics.Paint paint, int[] iArr) {
        checkRecycled("Can't extractAlpha on a recycled bitmap");
        long nativeInstance = paint != null ? paint.getNativeInstance() : 0L;
        noteHardwareBitmapSlowCall();
        android.graphics.Bitmap nativeExtractAlpha = nativeExtractAlpha(this.mNativePtr, nativeInstance, iArr);
        if (nativeExtractAlpha == null) {
            throw new java.lang.RuntimeException("Failed to extractAlpha on Bitmap");
        }
        nativeExtractAlpha.mDensity = this.mDensity;
        return nativeExtractAlpha;
    }

    public boolean sameAs(android.graphics.Bitmap bitmap) {
        android.os.StrictMode.noteSlowCall("sameAs compares pixel data, not expected to be fast");
        checkRecycled("Can't call sameAs on a recycled bitmap!");
        if (this == bitmap) {
            return true;
        }
        if (bitmap == null) {
            return false;
        }
        if (bitmap.isRecycled()) {
            throw new java.lang.IllegalArgumentException("Can't compare to a recycled bitmap!");
        }
        return nativeSameAs(this.mNativePtr, bitmap.mNativePtr);
    }

    public void prepareToDraw() {
        checkRecycled("Can't prepareToDraw on a recycled bitmap!");
        nativePrepareToDraw(this.mNativePtr);
    }

    public android.hardware.HardwareBuffer getHardwareBuffer() {
        checkRecycled("Can't getHardwareBuffer from a recycled bitmap");
        android.hardware.HardwareBuffer hardwareBuffer = this.mHardwareBuffer == null ? null : this.mHardwareBuffer.get();
        if (hardwareBuffer == null || hardwareBuffer.isClosed()) {
            android.hardware.HardwareBuffer nativeGetHardwareBuffer = nativeGetHardwareBuffer(this.mNativePtr);
            this.mHardwareBuffer = new java.lang.ref.WeakReference<>(nativeGetHardwareBuffer);
            return nativeGetHardwareBuffer;
        }
        return hardwareBuffer;
    }
}
