package android.content.res.loader;

/* loaded from: classes.dex */
public class ResourcesProvider implements java.lang.AutoCloseable, java.io.Closeable {
    private static final java.lang.String TAG = "ResourcesProvider";
    private final android.content.res.ApkAssets mApkAssets;
    private final java.lang.Object mLock = new java.lang.Object();
    private boolean mOpen = true;
    private int mOpenCount = 0;

    public static android.content.res.loader.ResourcesProvider empty(android.content.res.loader.AssetsProvider assetsProvider) {
        return new android.content.res.loader.ResourcesProvider(android.content.res.ApkAssets.loadEmptyForLoader(4, assetsProvider));
    }

    public static android.content.res.loader.ResourcesProvider loadOverlay(android.content.om.OverlayInfo overlayInfo) throws java.io.IOException {
        java.util.Objects.requireNonNull(overlayInfo);
        com.android.internal.util.Preconditions.checkArgument(overlayInfo.isFabricated(), "Not accepted overlay");
        com.android.internal.util.Preconditions.checkStringNotEmpty(overlayInfo.getTargetOverlayableName(), "Without overlayable name");
        java.lang.String checkOverlayNameValid = com.android.internal.content.om.OverlayManagerImpl.checkOverlayNameValid(overlayInfo.getOverlayName());
        java.nio.file.Path of = java.nio.file.Path.of((java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(overlayInfo.getBaseCodePath(), "Invalid base path"), new java.lang.String[0]);
        if (!java.nio.file.Files.isRegularFile(of, new java.nio.file.LinkOption[0])) {
            throw new java.io.FileNotFoundException("The frro file not found");
        }
        java.nio.file.Path resolve = of.getParent().resolve(checkOverlayNameValid + ".idmap");
        if (!java.nio.file.Files.isRegularFile(resolve, new java.nio.file.LinkOption[0])) {
            throw new java.io.FileNotFoundException("The idmap file not found");
        }
        return new android.content.res.loader.ResourcesProvider(android.content.res.ApkAssets.loadOverlayFromPath(resolve.toString(), 0));
    }

    public static android.content.res.loader.ResourcesProvider loadFromApk(android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        return loadFromApk(parcelFileDescriptor, null);
    }

    public static android.content.res.loader.ResourcesProvider loadFromApk(android.os.ParcelFileDescriptor parcelFileDescriptor, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException {
        return new android.content.res.loader.ResourcesProvider(android.content.res.ApkAssets.loadFromFd(parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor.toString(), 4, assetsProvider));
    }

    public static android.content.res.loader.ResourcesProvider loadFromApk(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, long j2, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException {
        return new android.content.res.loader.ResourcesProvider(android.content.res.ApkAssets.loadFromFd(parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor.toString(), j, j2, 4, assetsProvider));
    }

    public static android.content.res.loader.ResourcesProvider loadFromTable(android.os.ParcelFileDescriptor parcelFileDescriptor, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException {
        return new android.content.res.loader.ResourcesProvider(android.content.res.ApkAssets.loadTableFromFd(parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor.toString(), 4, assetsProvider));
    }

    public static android.content.res.loader.ResourcesProvider loadFromTable(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, long j2, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException {
        return new android.content.res.loader.ResourcesProvider(android.content.res.ApkAssets.loadTableFromFd(parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor.toString(), j, j2, 4, assetsProvider));
    }

    public static android.content.res.loader.ResourcesProvider loadFromSplit(android.content.Context context, java.lang.String str) throws java.io.IOException {
        android.content.pm.ApplicationInfo applicationInfo = context.getApplicationInfo();
        int indexOf = com.android.internal.util.ArrayUtils.indexOf(applicationInfo.splitNames, str);
        if (indexOf < 0) {
            throw new java.lang.IllegalArgumentException("Split " + str + " not found");
        }
        return new android.content.res.loader.ResourcesProvider(android.content.res.ApkAssets.loadFromPath(applicationInfo.getSplitCodePaths()[indexOf], 4, null));
    }

    public static android.content.res.loader.ResourcesProvider loadFromDirectory(java.lang.String str, android.content.res.loader.AssetsProvider assetsProvider) throws java.io.IOException {
        return new android.content.res.loader.ResourcesProvider(android.content.res.ApkAssets.loadFromDir(str, 4, assetsProvider));
    }

    private ResourcesProvider(android.content.res.ApkAssets apkAssets) {
        this.mApkAssets = apkAssets;
    }

    public android.content.res.ApkAssets getApkAssets() {
        return this.mApkAssets;
    }

    final void incrementRefCount() {
        synchronized (this.mLock) {
            if (!this.mOpen) {
                throw new java.lang.IllegalStateException("Operation failed: resources provider is closed");
            }
            this.mOpenCount++;
        }
    }

    final void decrementRefCount() {
        synchronized (this.mLock) {
            this.mOpenCount--;
        }
    }

    @Override // java.lang.AutoCloseable, java.io.Closeable
    public void close() {
        synchronized (this.mLock) {
            if (this.mOpen) {
                if (this.mOpenCount != 0) {
                    throw new java.lang.IllegalStateException("Failed to close provider used by " + this.mOpenCount + " ResourcesLoader instances");
                }
                this.mOpen = false;
                try {
                    this.mApkAssets.close();
                } catch (java.lang.Throwable th) {
                }
            }
        }
    }

    protected void finalize() throws java.lang.Throwable {
        synchronized (this.mLock) {
            if (this.mOpenCount != 0) {
                android.util.Log.w(TAG, "ResourcesProvider " + this + " finalized with non-zero refs: " + this.mOpenCount);
            }
        }
    }
}
