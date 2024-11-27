package com.android.internal.pm.split;

/* loaded from: classes5.dex */
public class SplitAssetDependencyLoader extends android.content.pm.split.SplitDependencyLoader<java.lang.IllegalArgumentException> implements com.android.internal.pm.split.SplitAssetLoader {
    private final android.content.res.AssetManager[] mCachedAssetManagers;
    private final android.content.res.ApkAssets[][] mCachedSplitApks;
    private final int mFlags;
    private final java.lang.String[] mSplitPaths;

    public SplitAssetDependencyLoader(android.content.pm.parsing.PackageLite packageLite, android.util.SparseArray<int[]> sparseArray, int i) {
        super(sparseArray);
        this.mSplitPaths = new java.lang.String[packageLite.getSplitApkPaths().length + 1];
        this.mSplitPaths[0] = packageLite.getBaseApkPath();
        java.lang.System.arraycopy(packageLite.getSplitApkPaths(), 0, this.mSplitPaths, 1, packageLite.getSplitApkPaths().length);
        this.mFlags = i;
        this.mCachedSplitApks = new android.content.res.ApkAssets[this.mSplitPaths.length][];
        this.mCachedAssetManagers = new android.content.res.AssetManager[this.mSplitPaths.length];
    }

    @Override // android.content.pm.split.SplitDependencyLoader
    protected boolean isSplitCached(int i) {
        return this.mCachedAssetManagers[i] != null;
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

    private static android.content.res.AssetManager createAssetManagerWithAssets(android.content.res.ApkAssets[] apkAssetsArr) {
        android.content.res.AssetManager assetManager = new android.content.res.AssetManager();
        assetManager.setConfiguration(0, 0, null, new java.lang.String[0], 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, android.os.Build.VERSION.RESOURCES_SDK_INT);
        assetManager.setApkAssets(apkAssetsArr, false);
        return assetManager;
    }

    @Override // android.content.pm.split.SplitDependencyLoader
    protected void constructSplit(int i, int[] iArr, int i2) throws java.lang.IllegalArgumentException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (i2 >= 0) {
            java.util.Collections.addAll(arrayList, this.mCachedSplitApks[i2]);
        }
        arrayList.add(loadApkAssets(this.mSplitPaths[i], this.mFlags));
        for (int i3 : iArr) {
            arrayList.add(loadApkAssets(this.mSplitPaths[i3], this.mFlags));
        }
        this.mCachedSplitApks[i] = (android.content.res.ApkAssets[]) arrayList.toArray(new android.content.res.ApkAssets[arrayList.size()]);
        this.mCachedAssetManagers[i] = createAssetManagerWithAssets(this.mCachedSplitApks[i]);
    }

    @Override // com.android.internal.pm.split.SplitAssetLoader
    public android.content.res.AssetManager getBaseAssetManager() throws java.lang.IllegalArgumentException {
        loadDependenciesForSplit(0);
        return this.mCachedAssetManagers[0];
    }

    @Override // com.android.internal.pm.split.SplitAssetLoader
    public android.content.res.AssetManager getSplitAssetManager(int i) throws java.lang.IllegalArgumentException {
        int i2 = i + 1;
        loadDependenciesForSplit(i2);
        return this.mCachedAssetManagers[i2];
    }

    @Override // com.android.internal.pm.split.SplitAssetLoader
    public android.content.res.ApkAssets getBaseApkAssets() {
        return this.mCachedSplitApks[0][0];
    }

    @Override // java.lang.AutoCloseable
    public void close() throws java.lang.Exception {
        for (android.content.res.AssetManager assetManager : this.mCachedAssetManagers) {
            libcore.io.IoUtils.closeQuietly(assetManager);
        }
    }
}
