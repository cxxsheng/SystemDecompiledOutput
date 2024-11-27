package android.graphics.fonts;

/* loaded from: classes.dex */
public final class Font {
    private static final libcore.util.NativeAllocationRegistry BUFFER_REGISTRY = libcore.util.NativeAllocationRegistry.createMalloced(java.nio.ByteBuffer.class.getClassLoader(), nGetReleaseNativeFont());
    private static final libcore.util.NativeAllocationRegistry FONT_REGISTRY = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.fonts.Font.class.getClassLoader(), nGetReleaseNativeFont());
    private static final int NOT_SPECIFIED = -1;
    private static final int STYLE_ITALIC = 1;
    private static final int STYLE_NORMAL = 0;
    private static final java.lang.String TAG = "Font";
    private final long mNativePtr;
    private final java.lang.Object mLock = new java.lang.Object();
    private java.nio.ByteBuffer mBuffer = null;
    private boolean mIsFileInitialized = false;
    private java.io.File mFile = null;
    private android.graphics.fonts.FontStyle mFontStyle = null;
    private android.graphics.fonts.FontVariationAxis[] mAxes = null;
    private android.os.LocaleList mLocaleList = null;

    @dalvik.annotation.optimization.CriticalNative
    private static native long nCloneFont(long j);

    @dalvik.annotation.optimization.FastNative
    private static native long[] nGetAvailableFontSet();

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetAxisCount(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nGetAxisInfo(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nGetBufferAddress(long j);

    @dalvik.annotation.optimization.FastNative
    private static native float nGetFontMetrics(long j, long j2, android.graphics.Paint.FontMetrics fontMetrics);

    @dalvik.annotation.optimization.FastNative
    private static native java.lang.String nGetFontPath(long j);

    @dalvik.annotation.optimization.FastNative
    private static native float nGetGlyphBounds(long j, int i, long j2, android.graphics.RectF rectF);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetIndex(long j);

    @dalvik.annotation.optimization.FastNative
    private static native java.lang.String nGetLocaleList(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nGetMinikinFontPtr(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetPackedStyle(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nGetReleaseNativeFont();

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetSourceId(long j);

    @dalvik.annotation.optimization.FastNative
    private static native java.nio.ByteBuffer nNewByteBuffer(long j);

    public static final class Builder {
        private android.graphics.fonts.FontVariationAxis[] mAxes;
        private java.nio.ByteBuffer mBuffer;
        private java.io.IOException mException;
        private java.io.File mFile;
        private android.graphics.fonts.Font mFont;
        private int mItalic;
        private java.lang.String mLocaleList;
        private int mTtcIndex;
        private int mWeight;

        @dalvik.annotation.optimization.CriticalNative
        private static native void nAddAxis(long j, int i, float f);

        private static native long nBuild(long j, java.nio.ByteBuffer byteBuffer, java.lang.String str, java.lang.String str2, int i, boolean z, int i2);

        @dalvik.annotation.optimization.FastNative
        private static native long nClone(long j, long j2, int i, boolean z, int i2);

        private static native long nInitBuilder();

        public Builder(java.nio.ByteBuffer byteBuffer) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            com.android.internal.util.Preconditions.checkNotNull(byteBuffer, "buffer can not be null");
            if (!byteBuffer.isDirect()) {
                throw new java.lang.IllegalArgumentException("Only direct buffer can be used as the source of font data.");
            }
            this.mBuffer = byteBuffer;
        }

        public Builder(java.nio.ByteBuffer byteBuffer, java.io.File file, java.lang.String str) {
            this(byteBuffer);
            this.mFile = file;
            this.mLocaleList = str;
        }

        public Builder(java.io.File file, java.lang.String str) {
            this(file);
            this.mLocaleList = str;
        }

        public Builder(java.io.File file) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            com.android.internal.util.Preconditions.checkNotNull(file, "path can not be null");
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
                try {
                    java.nio.channels.FileChannel channel = fileInputStream.getChannel();
                    this.mBuffer = channel.map(java.nio.channels.FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                    fileInputStream.close();
                } finally {
                }
            } catch (java.io.IOException e) {
                this.mException = e;
            }
            this.mFile = file;
        }

        public Builder(android.os.ParcelFileDescriptor parcelFileDescriptor) {
            this(parcelFileDescriptor, 0L, -1L);
        }

        public Builder(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(parcelFileDescriptor.getFileDescriptor());
                try {
                    java.nio.channels.FileChannel channel = fileInputStream.getChannel();
                    this.mBuffer = channel.map(java.nio.channels.FileChannel.MapMode.READ_ONLY, j, j2 == -1 ? channel.size() - j : j2);
                    fileInputStream.close();
                } finally {
                }
            } catch (java.io.IOException e) {
                this.mException = e;
            }
        }

        public Builder(android.content.res.AssetManager assetManager, java.lang.String str) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            try {
                this.mBuffer = createBuffer(assetManager, str, true, -1);
            } catch (java.io.IOException e) {
                this.mException = e;
            }
        }

        public Builder(android.content.res.AssetManager assetManager, java.lang.String str, boolean z, int i) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            try {
                this.mBuffer = createBuffer(assetManager, str, z, i);
            } catch (java.io.IOException e) {
                this.mException = e;
            }
        }

        public Builder(android.content.res.Resources resources, int i) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            android.util.TypedValue typedValue = new android.util.TypedValue();
            resources.getValue(i, typedValue, true);
            if (typedValue.string == null) {
                this.mException = new java.io.FileNotFoundException(i + " not found");
                return;
            }
            java.lang.String charSequence = typedValue.string.toString();
            if (!charSequence.toLowerCase().endsWith(".xml")) {
                try {
                    this.mBuffer = createBuffer(resources.getAssets(), charSequence, false, typedValue.assetCookie);
                    return;
                } catch (java.io.IOException e) {
                    this.mException = e;
                    return;
                }
            }
            this.mException = new java.io.FileNotFoundException(i + " must be font file.");
        }

        public Builder(android.graphics.fonts.Font font) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            this.mFont = font;
            this.mBuffer = font.getBuffer();
            this.mWeight = font.getStyle().getWeight();
            this.mItalic = font.getStyle().getSlant();
            this.mAxes = font.getAxes();
            this.mFile = font.getFile();
            this.mTtcIndex = font.getTtcIndex();
        }

        public static java.nio.ByteBuffer createBuffer(android.content.res.AssetManager assetManager, java.lang.String str, boolean z, int i) throws java.io.IOException {
            java.io.InputStream openNonAsset;
            android.content.res.AssetFileDescriptor openNonAssetFd;
            com.android.internal.util.Preconditions.checkNotNull(assetManager, "assetManager can not be null");
            com.android.internal.util.Preconditions.checkNotNull(str, "path can not be null");
            try {
                if (z) {
                    openNonAssetFd = assetManager.openFd(str);
                } else if (i > 0) {
                    openNonAssetFd = assetManager.openNonAssetFd(i, str);
                } else {
                    openNonAssetFd = assetManager.openNonAssetFd(str);
                }
                java.io.FileInputStream createInputStream = openNonAssetFd.createInputStream();
                try {
                    java.nio.MappedByteBuffer map = createInputStream.getChannel().map(java.nio.channels.FileChannel.MapMode.READ_ONLY, openNonAssetFd.getStartOffset(), openNonAssetFd.getDeclaredLength());
                    if (createInputStream != null) {
                        createInputStream.close();
                    }
                    return map;
                } finally {
                }
            } catch (java.io.IOException e) {
                if (z) {
                    openNonAsset = assetManager.open(str, 3);
                } else {
                    openNonAsset = assetManager.openNonAsset(i, str, 3);
                }
                try {
                    java.nio.ByteBuffer allocateDirect = java.nio.ByteBuffer.allocateDirect(openNonAsset.available());
                    allocateDirect.order(java.nio.ByteOrder.nativeOrder());
                    openNonAsset.read(allocateDirect.array(), allocateDirect.arrayOffset(), openNonAsset.available());
                    if (openNonAsset.read() != -1) {
                        throw new java.io.IOException("Unable to access full contents of " + str);
                    }
                    if (openNonAsset != null) {
                        openNonAsset.close();
                    }
                    return allocateDirect;
                } catch (java.lang.Throwable th) {
                    if (openNonAsset != null) {
                        try {
                            openNonAsset.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
        }

        public android.graphics.fonts.Font.Builder setWeight(int i) {
            com.android.internal.util.Preconditions.checkArgument(1 <= i && i <= 1000);
            this.mWeight = i;
            return this;
        }

        public android.graphics.fonts.Font.Builder setSlant(int i) {
            this.mItalic = i == 0 ? 0 : 1;
            return this;
        }

        public android.graphics.fonts.Font.Builder setTtcIndex(int i) {
            this.mTtcIndex = i;
            return this;
        }

        public android.graphics.fonts.Font.Builder setFontVariationSettings(java.lang.String str) {
            this.mAxes = android.graphics.fonts.FontVariationAxis.fromFontVariationSettings(str);
            return this;
        }

        public android.graphics.fonts.Font.Builder setFontVariationSettings(android.graphics.fonts.FontVariationAxis[] fontVariationAxisArr) {
            this.mAxes = fontVariationAxisArr == null ? null : (android.graphics.fonts.FontVariationAxis[]) fontVariationAxisArr.clone();
            return this;
        }

        public android.graphics.fonts.Font build() throws java.io.IOException {
            if (this.mException != null) {
                throw new java.io.IOException("Failed to read font contents", this.mException);
            }
            if (this.mWeight == -1 || this.mItalic == -1) {
                int analyzeStyle = android.graphics.fonts.FontFileUtil.analyzeStyle(this.mBuffer, this.mTtcIndex, this.mAxes);
                if (android.graphics.fonts.FontFileUtil.isSuccess(analyzeStyle)) {
                    if (this.mWeight == -1) {
                        this.mWeight = android.graphics.fonts.FontFileUtil.unpackWeight(analyzeStyle);
                    }
                    if (this.mItalic == -1) {
                        this.mItalic = android.graphics.fonts.FontFileUtil.unpackItalic(analyzeStyle) ? 1 : 0;
                    }
                } else {
                    this.mWeight = 400;
                    this.mItalic = 0;
                }
            }
            this.mWeight = java.lang.Math.max(1, java.lang.Math.min(1000, this.mWeight));
            boolean z = this.mItalic == 1;
            long nInitBuilder = nInitBuilder();
            if (this.mAxes != null) {
                for (android.graphics.fonts.FontVariationAxis fontVariationAxis : this.mAxes) {
                    nAddAxis(nInitBuilder, fontVariationAxis.getOpenTypeTagValue(), fontVariationAxis.getStyleValue());
                }
            }
            java.nio.ByteBuffer asReadOnlyBuffer = this.mBuffer.asReadOnlyBuffer();
            java.lang.String absolutePath = this.mFile == null ? "" : this.mFile.getAbsolutePath();
            if (this.mFont == null) {
                return new android.graphics.fonts.Font(nBuild(nInitBuilder, asReadOnlyBuffer, absolutePath, this.mLocaleList, this.mWeight, z, this.mTtcIndex));
            }
            return new android.graphics.fonts.Font(nClone(this.mFont.getNativePtr(), nInitBuilder, this.mWeight, z, this.mTtcIndex));
        }
    }

    public Font(long j) {
        this.mNativePtr = j;
        FONT_REGISTRY.registerNativeAllocation(this, this.mNativePtr);
    }

    public java.nio.ByteBuffer getBuffer() {
        java.nio.ByteBuffer byteBuffer;
        synchronized (this.mLock) {
            if (this.mBuffer == null) {
                long nCloneFont = nCloneFont(this.mNativePtr);
                java.nio.ByteBuffer nNewByteBuffer = nNewByteBuffer(this.mNativePtr);
                BUFFER_REGISTRY.registerNativeAllocation(nNewByteBuffer, nCloneFont);
                this.mBuffer = nNewByteBuffer.asReadOnlyBuffer();
            }
            byteBuffer = this.mBuffer;
        }
        return byteBuffer;
    }

    public java.io.File getFile() {
        java.io.File file;
        synchronized (this.mLock) {
            if (!this.mIsFileInitialized) {
                java.lang.String nGetFontPath = nGetFontPath(this.mNativePtr);
                if (!android.text.TextUtils.isEmpty(nGetFontPath)) {
                    this.mFile = new java.io.File(nGetFontPath);
                }
                this.mIsFileInitialized = true;
            }
            file = this.mFile;
        }
        return file;
    }

    public android.graphics.fonts.FontStyle getStyle() {
        android.graphics.fonts.FontStyle fontStyle;
        synchronized (this.mLock) {
            if (this.mFontStyle == null) {
                int nGetPackedStyle = nGetPackedStyle(this.mNativePtr);
                this.mFontStyle = new android.graphics.fonts.FontStyle(android.graphics.fonts.FontFileUtil.unpackWeight(nGetPackedStyle), android.graphics.fonts.FontFileUtil.unpackItalic(nGetPackedStyle) ? 1 : 0);
            }
            fontStyle = this.mFontStyle;
        }
        return fontStyle;
    }

    public int getTtcIndex() {
        return nGetIndex(this.mNativePtr);
    }

    public android.graphics.fonts.FontVariationAxis[] getAxes() {
        synchronized (this.mLock) {
            if (this.mAxes == null) {
                int nGetAxisCount = nGetAxisCount(this.mNativePtr);
                this.mAxes = new android.graphics.fonts.FontVariationAxis[nGetAxisCount];
                for (int i = 0; i < nGetAxisCount; i++) {
                    this.mAxes[i] = new android.graphics.fonts.FontVariationAxis(new java.lang.String(new char[]{(char) ((android.os.BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK & r4) >>> 56), (char) ((android.os.BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK & r4) >>> 48), (char) ((android.os.BatteryStats.STEP_LEVEL_LEVEL_MASK & r4) >>> 40), (char) ((r4 & android.util.proto.ProtoStream.FIELD_TYPE_MASK) >>> 32)}), java.lang.Float.intBitsToFloat((int) (4294967295L & nGetAxisInfo(this.mNativePtr, i))));
                }
            }
        }
        return this.mAxes;
    }

    public android.os.LocaleList getLocaleList() {
        android.os.LocaleList localeList;
        synchronized (this.mLock) {
            if (this.mLocaleList == null) {
                java.lang.String nGetLocaleList = nGetLocaleList(this.mNativePtr);
                if (android.text.TextUtils.isEmpty(nGetLocaleList)) {
                    this.mLocaleList = android.os.LocaleList.getEmptyLocaleList();
                } else {
                    this.mLocaleList = android.os.LocaleList.forLanguageTags(nGetLocaleList);
                }
            }
            localeList = this.mLocaleList;
        }
        return localeList;
    }

    public float getGlyphBounds(int i, android.graphics.Paint paint, android.graphics.RectF rectF) {
        return nGetGlyphBounds(this.mNativePtr, i, paint.getNativeInstance(), rectF);
    }

    public void getMetrics(android.graphics.Paint paint, android.graphics.Paint.FontMetrics fontMetrics) {
        nGetFontMetrics(this.mNativePtr, paint.getNativeInstance(), fontMetrics);
    }

    public long getNativePtr() {
        return this.mNativePtr;
    }

    public int getSourceIdentifier() {
        return nGetSourceId(this.mNativePtr);
    }

    private boolean isSameSource(android.graphics.fonts.Font font) {
        java.util.Objects.requireNonNull(font);
        java.nio.ByteBuffer buffer = getBuffer();
        java.nio.ByteBuffer buffer2 = font.getBuffer();
        if (buffer == buffer2) {
            return true;
        }
        if (buffer.capacity() != buffer2.capacity()) {
            return false;
        }
        if (getSourceIdentifier() == font.getSourceIdentifier() && buffer.position() == buffer2.position()) {
            return true;
        }
        return buffer.equals(buffer2);
    }

    public boolean paramEquals(android.graphics.fonts.Font font) {
        return font.getStyle().equals(getStyle()) && font.getTtcIndex() == getTtcIndex() && java.util.Arrays.equals(font.getAxes(), getAxes()) && java.util.Objects.equals(font.getLocaleList(), getLocaleList()) && java.util.Objects.equals(getFile(), font.getFile());
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.graphics.fonts.Font)) {
            return false;
        }
        android.graphics.fonts.Font font = (android.graphics.fonts.Font) obj;
        if (nGetMinikinFontPtr(this.mNativePtr) == nGetMinikinFontPtr(font.mNativePtr)) {
            return true;
        }
        if (paramEquals(font)) {
            return isSameSource(font);
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(getStyle(), java.lang.Integer.valueOf(getTtcIndex()), java.lang.Integer.valueOf(java.util.Arrays.hashCode(getAxes())), getLocaleList());
    }

    public java.lang.String toString() {
        return "Font {path=" + getFile() + ", style=" + getStyle() + ", ttcIndex=" + getTtcIndex() + ", axes=" + android.graphics.fonts.FontVariationAxis.toFontVariationSettings(getAxes()) + ", localeList=" + getLocaleList() + ", buffer=" + getBuffer() + "}";
    }

    public static java.util.Set<android.graphics.fonts.Font> getAvailableFonts() {
        java.util.IdentityHashMap identityHashMap = new java.util.IdentityHashMap();
        for (long j : nGetAvailableFontSet()) {
            android.graphics.fonts.Font font = new android.graphics.fonts.Font(j);
            identityHashMap.put(font, font);
        }
        return java.util.Collections.unmodifiableSet(identityHashMap.keySet());
    }
}
