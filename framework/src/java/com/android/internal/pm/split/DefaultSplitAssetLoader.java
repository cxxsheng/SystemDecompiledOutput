package com.android.internal.pm.split;

/* loaded from: classes5.dex */
public class DefaultSplitAssetLoader implements com.android.internal.pm.split.SplitAssetLoader {
    private android.content.res.ApkAssets mBaseApkAssets;
    private final java.lang.String mBaseApkPath;
    private android.content.res.AssetManager mCachedAssetManager;
    private final int mFlags;
    private final java.lang.String[] mSplitApkPaths;

    public DefaultSplitAssetLoader(android.content.pm.parsing.PackageLite packageLite, int i) {
        this.mBaseApkPath = packageLite.getBaseApkPath();
        this.mSplitApkPaths = packageLite.getSplitApkPaths();
        this.mFlags = i;
    }

    private static android.content.res.ApkAssets loadApkAssets(java.lang.String str, int i) throws java.lang.IllegalArgumentException {
        if ((i & 1) != 0 && !android.content.pm.parsing.ApkLiteParseUtils.isApkPath(str)) {
            throw new java.lang.IllegalArgumentException("Invalid package file: " + str);
        }
        try {
            return android.content.res.ApkAssets.loadFromPath(str);
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("Failed to load APK at path " + str, e);
        }
    }

    @Override // com.android.internal.pm.split.SplitAssetLoader
    public android.content.res.AssetManager getBaseAssetManager() throws java.lang.IllegalArgumentException {
        if (this.mCachedAssetManager == null) {
            int i = 1;
            android.content.res.ApkAssets[] apkAssetsArr = new android.content.res.ApkAssets[(this.mSplitApkPaths != null ? this.mSplitApkPaths.length : 0) + 1];
            android.content.res.ApkAssets loadApkAssets = loadApkAssets(this.mBaseApkPath, this.mFlags);
            this.mBaseApkAssets = loadApkAssets;
            apkAssetsArr[0] = loadApkAssets;
            if (!com.android.internal.util.ArrayUtils.isEmpty(this.mSplitApkPaths)) {
                java.lang.String[] strArr = this.mSplitApkPaths;
                int length = strArr.length;
                int i2 = 0;
                while (i2 < length) {
                    apkAssetsArr[i] = loadApkAssets(strArr[i2], this.mFlags);
                    i2++;
                    i++;
                }
            }
            android.content.res.AssetManager assetManager = new android.content.res.AssetManager();
            assetManager.setConfiguration(0, 0, null, new java.lang.String[0], 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, android.os.Build.VERSION.RESOURCES_SDK_INT);
            assetManager.setApkAssets(apkAssetsArr, false);
            this.mCachedAssetManager = assetManager;
            return this.mCachedAssetManager;
        }
        return this.mCachedAssetManager;
    }

    @Override // com.android.internal.pm.split.SplitAssetLoader
    public android.content.res.AssetManager getSplitAssetManager(int i) throws java.lang.IllegalArgumentException {
        return getBaseAssetManager();
    }

    @Override // com.android.internal.pm.split.SplitAssetLoader
    public android.content.res.ApkAssets getBaseApkAssets() {
        return this.mBaseApkAssets;
    }

    @Override // java.lang.AutoCloseable
    public void close() throws java.lang.Exception {
        libcore.io.IoUtils.closeQuietly(this.mCachedAssetManager);
    }
}
