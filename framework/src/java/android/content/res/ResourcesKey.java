package android.content.res;

/* loaded from: classes.dex */
public final class ResourcesKey {
    public final android.content.res.CompatibilityInfo mCompatInfo;
    public int mDisplayId;
    private final int mHash;
    public final java.lang.String[] mLibDirs;
    public final android.content.res.loader.ResourcesLoader[] mLoaders;
    public final java.lang.String[] mOverlayPaths;
    public final android.content.res.Configuration mOverrideConfiguration;
    public final java.lang.String mResDir;
    public final java.lang.String[] mSplitResDirs;

    public ResourcesKey(java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, int i, android.content.res.Configuration configuration, android.content.res.CompatibilityInfo compatibilityInfo, android.content.res.loader.ResourcesLoader[] resourcesLoaderArr) {
        this.mResDir = str;
        this.mSplitResDirs = strArr;
        this.mOverlayPaths = strArr2;
        this.mLibDirs = strArr3;
        if (resourcesLoaderArr != null && resourcesLoaderArr.length == 0) {
            resourcesLoaderArr = null;
        }
        this.mLoaders = resourcesLoaderArr;
        this.mDisplayId = i;
        this.mOverrideConfiguration = new android.content.res.Configuration(configuration == null ? android.content.res.Configuration.EMPTY : configuration);
        this.mCompatInfo = compatibilityInfo == null ? android.content.res.CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO : compatibilityInfo;
        this.mHash = ((((((((((((((527 + java.util.Objects.hashCode(this.mResDir)) * 31) + java.util.Arrays.hashCode(this.mSplitResDirs)) * 31) + java.util.Arrays.hashCode(this.mOverlayPaths)) * 31) + java.util.Arrays.hashCode(this.mLibDirs)) * 31) + java.util.Objects.hashCode(java.lang.Integer.valueOf(this.mDisplayId))) * 31) + java.util.Objects.hashCode(this.mOverrideConfiguration)) * 31) + java.util.Objects.hashCode(this.mCompatInfo)) * 31) + java.util.Arrays.hashCode(this.mLoaders);
    }

    public ResourcesKey(java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, int i, android.content.res.Configuration configuration, android.content.res.CompatibilityInfo compatibilityInfo) {
        this(str, strArr, strArr2, strArr3, i, configuration, compatibilityInfo, null);
    }

    public boolean hasOverrideConfiguration() {
        return !android.content.res.Configuration.EMPTY.equals(this.mOverrideConfiguration);
    }

    public boolean isPathReferenced(java.lang.String str) {
        return (this.mResDir != null && this.mResDir.startsWith(str)) || anyStartsWith(this.mSplitResDirs, str) || anyStartsWith(this.mOverlayPaths, str) || anyStartsWith(this.mLibDirs, str);
    }

    private static boolean anyStartsWith(java.lang.String[] strArr, java.lang.String str) {
        if (strArr != null) {
            for (java.lang.String str2 : strArr) {
                if (str2 != null && str2.startsWith(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        return this.mHash;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.content.res.ResourcesKey)) {
            return false;
        }
        android.content.res.ResourcesKey resourcesKey = (android.content.res.ResourcesKey) obj;
        return this.mHash == resourcesKey.mHash && java.util.Objects.equals(this.mResDir, resourcesKey.mResDir) && java.util.Arrays.equals(this.mSplitResDirs, resourcesKey.mSplitResDirs) && java.util.Arrays.equals(this.mOverlayPaths, resourcesKey.mOverlayPaths) && java.util.Arrays.equals(this.mLibDirs, resourcesKey.mLibDirs) && this.mDisplayId == resourcesKey.mDisplayId && java.util.Objects.equals(this.mOverrideConfiguration, resourcesKey.mOverrideConfiguration) && java.util.Objects.equals(this.mCompatInfo, resourcesKey.mCompatInfo) && java.util.Arrays.equals(this.mLoaders, resourcesKey.mLoaders);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder().append("ResourcesKey{");
        append.append(" mHash=").append(java.lang.Integer.toHexString(this.mHash));
        append.append(" mResDir=").append(this.mResDir);
        append.append(" mSplitDirs=[");
        if (this.mSplitResDirs != null) {
            append.append(android.text.TextUtils.join(",", this.mSplitResDirs));
        }
        append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        append.append(" mOverlayDirs=[");
        if (this.mOverlayPaths != null) {
            append.append(android.text.TextUtils.join(",", this.mOverlayPaths));
        }
        append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        append.append(" mLibDirs=[");
        if (this.mLibDirs != null) {
            append.append(android.text.TextUtils.join(",", this.mLibDirs));
        }
        append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        append.append(" mDisplayId=").append(this.mDisplayId);
        append.append(" mOverrideConfig=").append(android.content.res.Configuration.resourceQualifierString(this.mOverrideConfiguration));
        append.append(" mCompatInfo=").append(this.mCompatInfo);
        append.append(" mLoaders=[");
        if (this.mLoaders != null) {
            append.append(android.text.TextUtils.join(",", this.mLoaders));
        }
        append.append("]}");
        return append.toString();
    }
}
