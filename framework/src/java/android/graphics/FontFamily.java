package android.graphics;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class FontFamily {
    private static java.lang.String TAG = "FontFamily";
    private static final libcore.util.NativeAllocationRegistry sBuilderRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.FontFamily.class.getClassLoader(), nGetBuilderReleaseFunc());
    private static final libcore.util.NativeAllocationRegistry sFamilyRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.FontFamily.class.getClassLoader(), nGetFamilyReleaseFunc());
    private long mBuilderPtr;
    private java.lang.Runnable mNativeBuilderCleaner;
    public long mNativePtr;

    @dalvik.annotation.optimization.CriticalNative
    private static native void nAddAxisValue(long j, int i, float f);

    private static native boolean nAddFont(long j, java.nio.ByteBuffer byteBuffer, int i, int i2, int i3);

    private static native boolean nAddFontWeightStyle(long j, java.nio.ByteBuffer byteBuffer, int i, int i2, int i3);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nCreateFamily(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nGetBuilderReleaseFunc();

    @dalvik.annotation.optimization.CriticalNative
    private static native long nGetFamilyReleaseFunc();

    private static native long nInitBuilder(java.lang.String str, int i);

    public FontFamily() {
        this.mBuilderPtr = nInitBuilder(null, 0);
        this.mNativeBuilderCleaner = sBuilderRegistry.registerNativeAllocation(this, this.mBuilderPtr);
    }

    public FontFamily(java.lang.String[] strArr, int i) {
        java.lang.String str;
        if (strArr == null || strArr.length == 0) {
            str = null;
        } else if (strArr.length == 1) {
            str = strArr[0];
        } else {
            str = android.text.TextUtils.join(",", strArr);
        }
        this.mBuilderPtr = nInitBuilder(str, i);
        this.mNativeBuilderCleaner = sBuilderRegistry.registerNativeAllocation(this, this.mBuilderPtr);
    }

    public boolean freeze() {
        if (this.mBuilderPtr == 0) {
            throw new java.lang.IllegalStateException("This FontFamily is already frozen");
        }
        this.mNativePtr = nCreateFamily(this.mBuilderPtr);
        this.mNativeBuilderCleaner.run();
        this.mBuilderPtr = 0L;
        if (this.mNativePtr != 0) {
            sFamilyRegistry.registerNativeAllocation(this, this.mNativePtr);
        }
        return this.mNativePtr != 0;
    }

    public void abortCreation() {
        if (this.mBuilderPtr == 0) {
            throw new java.lang.IllegalStateException("This FontFamily is already frozen or abandoned");
        }
        this.mNativeBuilderCleaner.run();
        this.mBuilderPtr = 0L;
    }

    public boolean addFont(java.lang.String str, int i, android.graphics.fonts.FontVariationAxis[] fontVariationAxisArr, int i2, int i3) {
        if (this.mBuilderPtr == 0) {
            throw new java.lang.IllegalStateException("Unable to call addFont after freezing.");
        }
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(str);
            try {
                java.nio.channels.FileChannel channel = fileInputStream.getChannel();
                java.nio.MappedByteBuffer map = channel.map(java.nio.channels.FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                if (fontVariationAxisArr != null) {
                    for (android.graphics.fonts.FontVariationAxis fontVariationAxis : fontVariationAxisArr) {
                        nAddAxisValue(this.mBuilderPtr, fontVariationAxis.getOpenTypeTagValue(), fontVariationAxis.getStyleValue());
                    }
                }
                boolean nAddFont = nAddFont(this.mBuilderPtr, map, i, i2, i3);
                fileInputStream.close();
                return nAddFont;
            } finally {
            }
        } catch (java.io.IOException e) {
            return false;
        }
    }

    public boolean addFontFromBuffer(java.nio.ByteBuffer byteBuffer, int i, android.graphics.fonts.FontVariationAxis[] fontVariationAxisArr, int i2, int i3) {
        if (this.mBuilderPtr == 0) {
            throw new java.lang.IllegalStateException("Unable to call addFontWeightStyle after freezing.");
        }
        if (fontVariationAxisArr != null) {
            for (android.graphics.fonts.FontVariationAxis fontVariationAxis : fontVariationAxisArr) {
                nAddAxisValue(this.mBuilderPtr, fontVariationAxis.getOpenTypeTagValue(), fontVariationAxis.getStyleValue());
            }
        }
        return nAddFontWeightStyle(this.mBuilderPtr, byteBuffer, i, i2, i3);
    }

    public boolean addFontFromAssetManager(android.content.res.AssetManager assetManager, java.lang.String str, int i, boolean z, int i2, int i3, int i4, android.graphics.fonts.FontVariationAxis[] fontVariationAxisArr) {
        if (this.mBuilderPtr == 0) {
            throw new java.lang.IllegalStateException("Unable to call addFontFromAsset after freezing.");
        }
        try {
            return addFontFromBuffer(android.graphics.fonts.Font.Builder.createBuffer(assetManager, str, z, i), i2, fontVariationAxisArr, i3, i4);
        } catch (java.io.IOException e) {
            return false;
        }
    }
}
