package android.content.res;

/* loaded from: classes.dex */
public final class ApkAssets {
    private static final int FORMAT_APK = 0;
    private static final int FORMAT_ARSC = 2;
    private static final int FORMAT_DIR = 3;
    private static final int FORMAT_IDMAP = 1;
    public static final int PROPERTY_DISABLE_INCREMENTAL_HARDENING = 16;
    public static final int PROPERTY_DYNAMIC = 2;
    public static final int PROPERTY_LOADER = 4;
    public static final int PROPERTY_ONLY_OVERLAYABLES = 32;
    private static final int PROPERTY_OVERLAY = 8;
    public static final int PROPERTY_SYSTEM = 1;
    private final android.content.res.loader.AssetsProvider mAssets;
    private final int mFlags;
    private long mNativePtr;
    private final android.content.res.StringBlock mStringBlock;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FormatType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PropertyFlags {
    }

    private static native boolean nativeDefinesOverlayable(long j) throws java.io.IOException;

    private static native void nativeDestroy(long j);

    private static native java.lang.String nativeGetAssetPath(long j);

    private static native java.lang.String nativeGetDebugName(long j);

    private static native android.content.om.OverlayableInfo nativeGetOverlayableInfo(long j, java.lang.String str) throws java.io.IOException;

    private static native long nativeGetStringBlock(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nativeIsUpToDate(long j);

    private static native long nativeLoad(int i, java.lang.String str, int i2, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException;

    private static native long nativeLoadEmpty(int i, android.content.res.loader.AssetsProvider assetsProvider);

    private static native long nativeLoadFd(int i, java.io.FileDescriptor fileDescriptor, java.lang.String str, int i2, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException;

    private static native long nativeLoadFdOffsets(int i, java.io.FileDescriptor fileDescriptor, java.lang.String str, long j, long j2, int i2, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException;

    private static native long nativeOpenXml(long j, java.lang.String str) throws java.io.IOException;

    public static android.content.res.ApkAssets loadFromPath(java.lang.String str) throws java.io.IOException {
        return loadFromPath(str, 0);
    }

    public static android.content.res.ApkAssets loadFromPath(java.lang.String str, int i) throws java.io.IOException {
        return new android.content.res.ApkAssets(0, str, i, null);
    }

    public static android.content.res.ApkAssets loadFromPath(java.lang.String str, int i, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException {
        return new android.content.res.ApkAssets(0, str, i, assetsProvider);
    }

    public static android.content.res.ApkAssets loadFromFd(java.io.FileDescriptor fileDescriptor, java.lang.String str, int i, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException {
        return new android.content.res.ApkAssets(0, fileDescriptor, str, i, assetsProvider);
    }

    public static android.content.res.ApkAssets loadFromFd(java.io.FileDescriptor fileDescriptor, java.lang.String str, long j, long j2, int i, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException {
        return new android.content.res.ApkAssets(0, fileDescriptor, str, j, j2, i, assetsProvider);
    }

    public static android.content.res.ApkAssets loadOverlayFromPath(java.lang.String str, int i) throws java.io.IOException {
        return new android.content.res.ApkAssets(1, str, i, null);
    }

    public static android.content.res.ApkAssets loadTableFromFd(java.io.FileDescriptor fileDescriptor, java.lang.String str, int i, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException {
        return new android.content.res.ApkAssets(2, fileDescriptor, str, i, assetsProvider);
    }

    public static android.content.res.ApkAssets loadTableFromFd(java.io.FileDescriptor fileDescriptor, java.lang.String str, long j, long j2, int i, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException {
        return new android.content.res.ApkAssets(2, fileDescriptor, str, j, j2, i, assetsProvider);
    }

    public static android.content.res.ApkAssets loadFromDir(java.lang.String str, int i, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException {
        return new android.content.res.ApkAssets(3, str, i, assetsProvider);
    }

    public static android.content.res.ApkAssets loadEmptyForLoader(int i, android.content.res.loader.AssetsProvider assetsProvider) {
        return new android.content.res.ApkAssets(i, assetsProvider);
    }

    private ApkAssets(int i, java.lang.String str, int i2, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException {
        java.util.Objects.requireNonNull(str, "path");
        this.mFlags = i2;
        this.mNativePtr = nativeLoad(i, str, i2, assetsProvider);
        this.mStringBlock = new android.content.res.StringBlock(nativeGetStringBlock(this.mNativePtr), true);
        this.mAssets = assetsProvider;
    }

    private ApkAssets(int i, java.io.FileDescriptor fileDescriptor, java.lang.String str, int i2, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException {
        java.util.Objects.requireNonNull(fileDescriptor, "fd");
        java.util.Objects.requireNonNull(str, "friendlyName");
        this.mFlags = i2;
        this.mNativePtr = nativeLoadFd(i, fileDescriptor, str, i2, assetsProvider);
        this.mStringBlock = new android.content.res.StringBlock(nativeGetStringBlock(this.mNativePtr), true);
        this.mAssets = assetsProvider;
    }

    private ApkAssets(int i, java.io.FileDescriptor fileDescriptor, java.lang.String str, long j, long j2, int i2, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException {
        java.util.Objects.requireNonNull(fileDescriptor, "fd");
        java.util.Objects.requireNonNull(str, "friendlyName");
        this.mFlags = i2;
        this.mNativePtr = nativeLoadFdOffsets(i, fileDescriptor, str, j, j2, i2, assetsProvider);
        this.mStringBlock = new android.content.res.StringBlock(nativeGetStringBlock(this.mNativePtr), true);
        this.mAssets = assetsProvider;
    }

    private ApkAssets(int i, android.content.res.loader.AssetsProvider assetsProvider) {
        this.mFlags = i;
        this.mNativePtr = nativeLoadEmpty(i, assetsProvider);
        this.mStringBlock = null;
        this.mAssets = assetsProvider;
    }

    public java.lang.String getAssetPath() {
        java.lang.String emptyIfNull;
        synchronized (this) {
            emptyIfNull = android.text.TextUtils.emptyIfNull(nativeGetAssetPath(this.mNativePtr));
        }
        return emptyIfNull;
    }

    public java.lang.String getDebugName() {
        java.lang.String nativeGetDebugName;
        synchronized (this) {
            nativeGetDebugName = nativeGetDebugName(this.mNativePtr);
        }
        return nativeGetDebugName;
    }

    java.lang.CharSequence getStringFromPool(int i) {
        java.lang.CharSequence sequence;
        if (this.mStringBlock == null) {
            return null;
        }
        synchronized (this) {
            sequence = this.mStringBlock.getSequence(i);
        }
        return sequence;
    }

    public boolean isForLoader() {
        return (this.mFlags & 4) != 0;
    }

    public android.content.res.loader.AssetsProvider getAssetsProvider() {
        return this.mAssets;
    }

    public android.content.res.XmlResourceParser openXml(java.lang.String str) throws java.io.IOException {
        android.content.res.XmlResourceParser newParser;
        java.util.Objects.requireNonNull(str, "fileName");
        synchronized (this) {
            android.content.res.XmlBlock xmlBlock = new android.content.res.XmlBlock(null, nativeOpenXml(this.mNativePtr, str));
            try {
                newParser = xmlBlock.newParser();
                if (newParser == null) {
                    throw new java.lang.AssertionError("block.newParser() returned a null parser");
                }
                xmlBlock.close();
            } finally {
            }
        }
        return newParser;
    }

    public android.content.om.OverlayableInfo getOverlayableInfo(java.lang.String str) throws java.io.IOException {
        android.content.om.OverlayableInfo nativeGetOverlayableInfo;
        synchronized (this) {
            nativeGetOverlayableInfo = nativeGetOverlayableInfo(this.mNativePtr, str);
        }
        return nativeGetOverlayableInfo;
    }

    public boolean definesOverlayable() throws java.io.IOException {
        boolean nativeDefinesOverlayable;
        synchronized (this) {
            nativeDefinesOverlayable = nativeDefinesOverlayable(this.mNativePtr);
        }
        return nativeDefinesOverlayable;
    }

    public boolean isUpToDate() {
        boolean nativeIsUpToDate;
        synchronized (this) {
            nativeIsUpToDate = nativeIsUpToDate(this.mNativePtr);
        }
        return nativeIsUpToDate;
    }

    public java.lang.String toString() {
        return "ApkAssets{path=" + getDebugName() + "}";
    }

    protected void finalize() throws java.lang.Throwable {
        close();
    }

    public void close() {
        synchronized (this) {
            if (this.mNativePtr != 0) {
                if (this.mStringBlock != null) {
                    this.mStringBlock.close();
                }
                nativeDestroy(this.mNativePtr);
                this.mNativePtr = 0L;
            }
        }
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "class=" + getClass());
        printWriter.println(str + "debugName=" + getDebugName());
        printWriter.println(str + "assetPath=" + getAssetPath());
    }
}
