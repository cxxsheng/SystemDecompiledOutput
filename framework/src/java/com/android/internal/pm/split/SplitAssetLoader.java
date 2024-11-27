package com.android.internal.pm.split;

/* loaded from: classes5.dex */
public interface SplitAssetLoader extends java.lang.AutoCloseable {
    android.content.res.ApkAssets getBaseApkAssets();

    android.content.res.AssetManager getBaseAssetManager() throws java.lang.IllegalArgumentException;

    android.content.res.AssetManager getSplitAssetManager(int i) throws java.lang.IllegalArgumentException;
}
