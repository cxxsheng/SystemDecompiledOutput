package android.content.res;

/* loaded from: classes.dex */
public final class AssetManager implements java.lang.AutoCloseable {
    public static final int ACCESS_BUFFER = 3;
    public static final int ACCESS_RANDOM = 1;
    public static final int ACCESS_STREAMING = 2;
    public static final int ACCESS_UNKNOWN = 0;
    public static final int COOKIE_UNKNOWN = -1;
    private static final boolean DEBUG_REFS = false;
    private static final java.lang.String FRAMEWORK_APK_PATH = "/system/framework/framework-res.apk";
    private static final java.lang.String LINEAGE_APK_PATH = "/system/framework/org.lineageos.platform-res.apk";
    private static final java.lang.String TAG = "AssetManager";
    private static android.util.ArraySet<android.content.res.ApkAssets> sSystemApkAssetsSet;
    private android.content.res.ApkAssets[] mApkAssets;
    private android.content.res.loader.ResourcesLoader[] mLoaders;
    private int mNumRefs;
    private long mObject;
    private final long[] mOffsets;
    private boolean mOpen;
    private java.util.HashMap<java.lang.Long, java.lang.RuntimeException> mRefStacks;
    private final android.util.TypedValue mValue;
    private static final java.lang.Object sSync = new java.lang.Object();
    private static final android.content.res.ApkAssets[] sEmptyApkAssets = new android.content.res.ApkAssets[0];
    static android.content.res.AssetManager sSystem = null;
    private static android.content.res.ApkAssets[] sSystemApkAssets = new android.content.res.ApkAssets[0];

    public static native java.lang.String getAssetAllocations();

    public static native int getGlobalAssetCount();

    public static native int getGlobalAssetManagerCount();

    private static native void nativeApplyStyle(long j, long j2, int i, int i2, long j3, int[] iArr, long j4, long j5);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAssetDestroy(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeAssetGetLength(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeAssetGetRemainingLength(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeAssetRead(long j, byte[] bArr, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeAssetReadChar(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeAssetSeek(long j, long j2, int i);

    private static native int[] nativeAttributeResolutionStack(long j, long j2, int i, int i2, int i3);

    private static native boolean nativeContainsAllocatedTable(long j);

    private static native long nativeCreate();

    private static native void nativeDestroy(long j);

    private static native android.util.SparseArray<java.lang.String> nativeGetAssignedPackageIdentifiers(long j, boolean z, boolean z2);

    private static native java.lang.String nativeGetLastResourceResolution(long j);

    private static native java.lang.String[] nativeGetLocales(long j, boolean z);

    private static native java.util.Map nativeGetOverlayableMap(long j, java.lang.String str);

    private static native java.lang.String nativeGetOverlayablesToString(long j, java.lang.String str);

    private static native int nativeGetParentThemeIdentifier(long j, int i);

    private static native int nativeGetResourceArray(long j, int i, int[] iArr);

    private static native int nativeGetResourceArraySize(long j, int i);

    private static native int nativeGetResourceBagValue(long j, int i, int i2, android.util.TypedValue typedValue);

    private static native java.lang.String nativeGetResourceEntryName(long j, int i);

    private static native int nativeGetResourceIdentifier(long j, java.lang.String str, java.lang.String str2, java.lang.String str3);

    private static native int[] nativeGetResourceIntArray(long j, int i);

    private static native java.lang.String nativeGetResourceName(long j, int i);

    private static native java.lang.String nativeGetResourcePackageName(long j, int i);

    private static native java.lang.String[] nativeGetResourceStringArray(long j, int i);

    private static native int[] nativeGetResourceStringArrayInfo(long j, int i);

    private static native java.lang.String nativeGetResourceTypeName(long j, int i);

    private static native int nativeGetResourceValue(long j, int i, short s, android.util.TypedValue typedValue, boolean z);

    private static native android.content.res.Configuration[] nativeGetSizeAndUiModeConfigurations(long j);

    private static native android.content.res.Configuration[] nativeGetSizeConfigurations(long j);

    private static native int[] nativeGetStyleAttributes(long j, int i);

    private static native long nativeGetThemeFreeFunction();

    private static native java.lang.String[] nativeList(long j, java.lang.String str) throws java.io.IOException;

    private static native long nativeOpenAsset(long j, java.lang.String str, int i);

    private static native android.os.ParcelFileDescriptor nativeOpenAssetFd(long j, java.lang.String str, long[] jArr) throws java.io.IOException;

    private static native long nativeOpenNonAsset(long j, int i, java.lang.String str, int i2);

    private static native android.os.ParcelFileDescriptor nativeOpenNonAssetFd(long j, int i, java.lang.String str, long[] jArr) throws java.io.IOException;

    private static native long nativeOpenXmlAsset(long j, int i, java.lang.String str);

    private static native long nativeOpenXmlAssetFd(long j, int i, java.io.FileDescriptor fileDescriptor);

    private static native boolean nativeResolveAttrs(long j, long j2, int i, int i2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4);

    private static native boolean nativeRetrieveAttributes(long j, long j2, int[] iArr, int[] iArr2, int[] iArr3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetApkAssets(long j, android.content.res.ApkAssets[] apkAssetsArr, boolean z, boolean z2);

    private static native void nativeSetConfiguration(long j, int i, int i2, java.lang.String str, java.lang.String[] strArr, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, boolean z);

    private static native void nativeSetResourceResolutionLoggingEnabled(long j, boolean z);

    private static native void nativeThemeApplyStyle(long j, long j2, int i, boolean z);

    private static native void nativeThemeCopy(long j, long j2, long j3, long j4);

    private static native long nativeThemeCreate(long j);

    private static native void nativeThemeDump(long j, long j2, int i, java.lang.String str, java.lang.String str2);

    private static native int nativeThemeGetAttributeValue(long j, long j2, int i, android.util.TypedValue typedValue, boolean z);

    static native int nativeThemeGetChangingConfigurations(long j);

    private static native void nativeThemeRebase(long j, long j2, int[] iArr, boolean[] zArr, int i);

    public static class Builder {
        private java.util.ArrayList<android.content.res.ApkAssets> mUserApkAssets = new java.util.ArrayList<>();
        private java.util.ArrayList<android.content.res.loader.ResourcesLoader> mLoaders = new java.util.ArrayList<>();
        private boolean mNoInit = false;

        public android.content.res.AssetManager.Builder addApkAssets(android.content.res.ApkAssets apkAssets) {
            this.mUserApkAssets.add(apkAssets);
            return this;
        }

        public android.content.res.AssetManager.Builder addLoader(android.content.res.loader.ResourcesLoader resourcesLoader) {
            this.mLoaders.add(resourcesLoader);
            return this;
        }

        public android.content.res.AssetManager.Builder setNoInit() {
            this.mNoInit = true;
            return this;
        }

        public android.content.res.AssetManager build() {
            boolean z;
            android.content.res.ApkAssets[] apkAssets = android.content.res.AssetManager.getSystem().getApkAssets();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            android.util.ArraySet arraySet = new android.util.ArraySet();
            int size = this.mLoaders.size();
            while (true) {
                size--;
                z = false;
                if (size < 0) {
                    break;
                }
                java.util.List<android.content.res.ApkAssets> apkAssets2 = this.mLoaders.get(size).getApkAssets();
                for (int size2 = apkAssets2.size() - 1; size2 >= 0; size2--) {
                    android.content.res.ApkAssets apkAssets3 = apkAssets2.get(size2);
                    if (arraySet.add(apkAssets3)) {
                        arrayList.add(0, apkAssets3);
                    }
                }
            }
            android.content.res.ApkAssets[] apkAssetsArr = new android.content.res.ApkAssets[apkAssets.length + this.mUserApkAssets.size() + arrayList.size()];
            java.lang.System.arraycopy(apkAssets, 0, apkAssetsArr, 0, apkAssets.length);
            int size3 = this.mUserApkAssets.size();
            for (int i = 0; i < size3; i++) {
                apkAssetsArr[apkAssets.length + i] = this.mUserApkAssets.get(i);
            }
            int size4 = arrayList.size();
            for (int i2 = 0; i2 < size4; i2++) {
                apkAssetsArr[apkAssets.length + i2 + this.mUserApkAssets.size()] = (android.content.res.ApkAssets) arrayList.get(i2);
            }
            android.content.res.AssetManager assetManager = new android.content.res.AssetManager(z);
            assetManager.mApkAssets = apkAssetsArr;
            android.content.res.AssetManager.nativeSetApkAssets(assetManager.mObject, apkAssetsArr, false, this.mNoInit);
            assetManager.mLoaders = this.mLoaders.isEmpty() ? null : (android.content.res.loader.ResourcesLoader[]) this.mLoaders.toArray(new android.content.res.loader.ResourcesLoader[0]);
            return assetManager;
        }
    }

    public AssetManager() {
        android.content.res.ApkAssets[] apkAssetsArr;
        this.mValue = new android.util.TypedValue();
        this.mOffsets = new long[2];
        this.mOpen = true;
        this.mNumRefs = 1;
        synchronized (sSync) {
            createSystemAssetsInZygoteLocked(false, FRAMEWORK_APK_PATH);
            apkAssetsArr = sSystemApkAssets;
        }
        this.mObject = nativeCreate();
        setApkAssets(apkAssetsArr, false);
    }

    private AssetManager(boolean z) {
        this.mValue = new android.util.TypedValue();
        this.mOffsets = new long[2];
        this.mOpen = true;
        this.mNumRefs = 1;
        this.mObject = nativeCreate();
    }

    public static void createSystemAssetsInZygoteLocked(boolean z, java.lang.String str) {
        if (sSystem != null && !z) {
            return;
        }
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(android.content.res.ApkAssets.loadFromPath(str, 1));
            for (java.lang.String str2 : com.android.internal.content.om.OverlayConfig.getZygoteInstance().createImmutableFrameworkIdmapsInZygote()) {
                arrayList.add(android.content.res.ApkAssets.loadOverlayFromPath(str2, 1));
            }
            arrayList.add(android.content.res.ApkAssets.loadFromPath(LINEAGE_APK_PATH, 1));
            sSystemApkAssetsSet = new android.util.ArraySet<>(arrayList);
            sSystemApkAssets = (android.content.res.ApkAssets[]) arrayList.toArray(new android.content.res.ApkAssets[arrayList.size()]);
            if (sSystem == null) {
                sSystem = new android.content.res.AssetManager(true);
            }
            sSystem.setApkAssets(sSystemApkAssets, false);
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("Failed to create system AssetManager", e);
        }
    }

    public static android.content.res.AssetManager getSystem() {
        android.content.res.AssetManager assetManager;
        synchronized (sSync) {
            createSystemAssetsInZygoteLocked(false, FRAMEWORK_APK_PATH);
            assetManager = sSystem;
        }
        return assetManager;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this) {
            if (this.mOpen) {
                this.mOpen = false;
                decRefsLocked(hashCode());
            }
        }
    }

    public void setApkAssets(android.content.res.ApkAssets[] apkAssetsArr, boolean z) {
        java.util.Objects.requireNonNull(apkAssetsArr, "apkAssets");
        int length = sSystemApkAssets.length + apkAssetsArr.length;
        android.content.res.ApkAssets[] apkAssetsArr2 = new android.content.res.ApkAssets[length];
        java.lang.System.arraycopy(sSystemApkAssets, 0, apkAssetsArr2, 0, sSystemApkAssets.length);
        int length2 = sSystemApkAssets.length;
        for (android.content.res.ApkAssets apkAssets : apkAssetsArr) {
            if (!sSystemApkAssetsSet.contains(apkAssets)) {
                apkAssetsArr2[length2] = apkAssets;
                length2++;
            }
        }
        if (length2 != length) {
            apkAssetsArr2 = (android.content.res.ApkAssets[]) java.util.Arrays.copyOf(apkAssetsArr2, length2);
        }
        synchronized (this) {
            ensureOpenLocked();
            this.mApkAssets = apkAssetsArr2;
            nativeSetApkAssets(this.mObject, this.mApkAssets, z, false);
            if (z) {
                invalidateCachesLocked(-1);
            }
        }
    }

    void setLoaders(java.util.List<android.content.res.loader.ResourcesLoader> list) {
        java.util.Objects.requireNonNull(list, "newLoaders");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < this.mApkAssets.length; i++) {
            if (!this.mApkAssets[i].isForLoader()) {
                arrayList.add(this.mApkAssets[i]);
            }
        }
        if (!list.isEmpty()) {
            int size = arrayList.size();
            android.util.ArraySet arraySet = new android.util.ArraySet();
            for (int size2 = list.size() - 1; size2 >= 0; size2--) {
                java.util.List<android.content.res.ApkAssets> apkAssets = list.get(size2).getApkAssets();
                for (int size3 = apkAssets.size() - 1; size3 >= 0; size3--) {
                    android.content.res.ApkAssets apkAssets2 = apkAssets.get(size3);
                    if (arraySet.add(apkAssets2)) {
                        arrayList.add(size, apkAssets2);
                    }
                }
            }
        }
        this.mLoaders = (android.content.res.loader.ResourcesLoader[]) list.toArray(new android.content.res.loader.ResourcesLoader[0]);
        setApkAssets((android.content.res.ApkAssets[]) arrayList.toArray(new android.content.res.ApkAssets[0]), true);
    }

    private void invalidateCachesLocked(int i) {
    }

    public android.content.res.ApkAssets[] getApkAssets() {
        synchronized (this) {
            if (this.mOpen) {
                return this.mApkAssets;
            }
            return sEmptyApkAssets;
        }
    }

    public java.lang.String[] getApkPaths() {
        synchronized (this) {
            if (this.mOpen) {
                java.lang.String[] strArr = new java.lang.String[this.mApkAssets.length];
                int length = this.mApkAssets.length;
                for (int i = 0; i < length; i++) {
                    strArr[i] = this.mApkAssets[i].getAssetPath();
                }
                return strArr;
            }
            return new java.lang.String[0];
        }
    }

    public int findCookieForPath(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "path");
        synchronized (this) {
            ensureValidLocked();
            int length = this.mApkAssets.length;
            for (int i = 0; i < length; i++) {
                if (str.equals(this.mApkAssets[i].getAssetPath())) {
                    return i + 1;
                }
            }
            return 0;
        }
    }

    @java.lang.Deprecated
    public int addAssetPath(java.lang.String str) {
        return addAssetPathInternal(str, false, false);
    }

    @java.lang.Deprecated
    public int addAssetPathAsSharedLibrary(java.lang.String str) {
        return addAssetPathInternal(str, false, true);
    }

    @java.lang.Deprecated
    public int addOverlayPath(java.lang.String str) {
        return addAssetPathInternal(str, true, false);
    }

    private int addAssetPathInternal(java.lang.String str, boolean z, boolean z2) {
        android.content.res.ApkAssets loadFromPath;
        java.util.Objects.requireNonNull(str, "path");
        synchronized (this) {
            ensureOpenLocked();
            int length = this.mApkAssets.length;
            for (int i = 0; i < length; i++) {
                if (this.mApkAssets[i].getAssetPath().equals(str)) {
                    return i + 1;
                }
            }
            try {
                if (z) {
                    loadFromPath = android.content.res.ApkAssets.loadOverlayFromPath("/data/resource-cache/" + str.substring(1).replace('/', '@') + "@idmap", 0);
                } else {
                    loadFromPath = android.content.res.ApkAssets.loadFromPath(str, z2 ? 2 : 0);
                }
                int i2 = length + 1;
                this.mApkAssets = (android.content.res.ApkAssets[]) java.util.Arrays.copyOf(this.mApkAssets, i2);
                this.mApkAssets[length] = loadFromPath;
                nativeSetApkAssets(this.mObject, this.mApkAssets, true, false);
                invalidateCachesLocked(-1);
                return i2;
            } catch (java.io.IOException e) {
                return 0;
            }
        }
    }

    public java.util.List<android.content.res.loader.ResourcesLoader> getLoaders() {
        return this.mLoaders == null ? java.util.Collections.emptyList() : java.util.Arrays.asList(this.mLoaders);
    }

    private void ensureValidLocked() {
        if (this.mObject == 0) {
            throw new java.lang.RuntimeException("AssetManager has been destroyed");
        }
    }

    private void ensureOpenLocked() {
        if (!this.mOpen) {
            throw new java.lang.RuntimeException("AssetManager has been closed");
        }
        if (this.mObject == 0) {
            throw new java.lang.RuntimeException("AssetManager is open but the native object is gone");
        }
    }

    boolean getResourceValue(int i, int i2, android.util.TypedValue typedValue, boolean z) {
        java.util.Objects.requireNonNull(typedValue, "outValue");
        synchronized (this) {
            ensureValidLocked();
            int nativeGetResourceValue = nativeGetResourceValue(this.mObject, i, (short) i2, typedValue, z);
            if (nativeGetResourceValue <= 0) {
                return false;
            }
            typedValue.changingConfigurations = android.content.pm.ActivityInfo.activityInfoConfigNativeToJava(typedValue.changingConfigurations);
            if (typedValue.type == 3) {
                java.lang.CharSequence pooledStringForCookie = getPooledStringForCookie(nativeGetResourceValue, typedValue.data);
                typedValue.string = pooledStringForCookie;
                if (pooledStringForCookie == null) {
                    return false;
                }
            }
            return true;
        }
    }

    java.lang.CharSequence getResourceText(int i) {
        synchronized (this) {
            android.util.TypedValue typedValue = this.mValue;
            if (!getResourceValue(i, 0, typedValue, true)) {
                return null;
            }
            return typedValue.coerceToString();
        }
    }

    java.lang.CharSequence getResourceBagText(int i, int i2) {
        synchronized (this) {
            ensureValidLocked();
            android.util.TypedValue typedValue = this.mValue;
            int nativeGetResourceBagValue = nativeGetResourceBagValue(this.mObject, i, i2, typedValue);
            if (nativeGetResourceBagValue <= 0) {
                return null;
            }
            typedValue.changingConfigurations = android.content.pm.ActivityInfo.activityInfoConfigNativeToJava(typedValue.changingConfigurations);
            if (typedValue.type == 3) {
                return getPooledStringForCookie(nativeGetResourceBagValue, typedValue.data);
            }
            return typedValue.coerceToString();
        }
    }

    int getResourceArraySize(int i) {
        int nativeGetResourceArraySize;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceArraySize = nativeGetResourceArraySize(this.mObject, i);
        }
        return nativeGetResourceArraySize;
    }

    int getResourceArray(int i, int[] iArr) {
        int nativeGetResourceArray;
        java.util.Objects.requireNonNull(iArr, "outData");
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceArray = nativeGetResourceArray(this.mObject, i, iArr);
        }
        return nativeGetResourceArray;
    }

    java.lang.String[] getResourceStringArray(int i) {
        java.lang.String[] nativeGetResourceStringArray;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceStringArray = nativeGetResourceStringArray(this.mObject, i);
        }
        return nativeGetResourceStringArray;
    }

    java.lang.CharSequence[] getResourceTextArray(int i) {
        java.lang.CharSequence charSequence;
        synchronized (this) {
            ensureValidLocked();
            int[] nativeGetResourceStringArrayInfo = nativeGetResourceStringArrayInfo(this.mObject, i);
            if (nativeGetResourceStringArrayInfo == null) {
                return null;
            }
            int length = nativeGetResourceStringArrayInfo.length;
            java.lang.CharSequence[] charSequenceArr = new java.lang.CharSequence[length / 2];
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                int i4 = nativeGetResourceStringArrayInfo[i2];
                int i5 = nativeGetResourceStringArrayInfo[i2 + 1];
                if (i5 < 0 || i4 <= 0) {
                    charSequence = null;
                } else {
                    charSequence = getPooledStringForCookie(i4, i5);
                }
                charSequenceArr[i3] = charSequence;
                i2 += 2;
                i3++;
            }
            return charSequenceArr;
        }
    }

    int[] getResourceIntArray(int i) {
        int[] nativeGetResourceIntArray;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceIntArray = nativeGetResourceIntArray(this.mObject, i);
        }
        return nativeGetResourceIntArray;
    }

    int[] getStyleAttributes(int i) {
        int[] nativeGetStyleAttributes;
        synchronized (this) {
            ensureValidLocked();
            nativeGetStyleAttributes = nativeGetStyleAttributes(this.mObject, i);
        }
        return nativeGetStyleAttributes;
    }

    boolean getThemeValue(long j, int i, android.util.TypedValue typedValue, boolean z) {
        java.util.Objects.requireNonNull(typedValue, "outValue");
        synchronized (this) {
            ensureValidLocked();
            int nativeThemeGetAttributeValue = nativeThemeGetAttributeValue(this.mObject, j, i, typedValue, z);
            if (nativeThemeGetAttributeValue <= 0) {
                return false;
            }
            typedValue.changingConfigurations = android.content.pm.ActivityInfo.activityInfoConfigNativeToJava(typedValue.changingConfigurations);
            if (typedValue.type == 3) {
                java.lang.CharSequence pooledStringForCookie = getPooledStringForCookie(nativeThemeGetAttributeValue, typedValue.data);
                typedValue.string = pooledStringForCookie;
                if (pooledStringForCookie == null) {
                    return false;
                }
            }
            return true;
        }
    }

    void dumpTheme(long j, int i, java.lang.String str, java.lang.String str2) {
        synchronized (this) {
            ensureValidLocked();
            nativeThemeDump(this.mObject, j, i, str, str2);
        }
    }

    java.lang.String getResourceName(int i) {
        java.lang.String nativeGetResourceName;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceName = nativeGetResourceName(this.mObject, i);
        }
        return nativeGetResourceName;
    }

    java.lang.String getResourcePackageName(int i) {
        java.lang.String nativeGetResourcePackageName;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourcePackageName = nativeGetResourcePackageName(this.mObject, i);
        }
        return nativeGetResourcePackageName;
    }

    java.lang.String getResourceTypeName(int i) {
        java.lang.String nativeGetResourceTypeName;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceTypeName = nativeGetResourceTypeName(this.mObject, i);
        }
        return nativeGetResourceTypeName;
    }

    java.lang.String getResourceEntryName(int i) {
        java.lang.String nativeGetResourceEntryName;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceEntryName = nativeGetResourceEntryName(this.mObject, i);
        }
        return nativeGetResourceEntryName;
    }

    int getResourceIdentifier(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        int nativeGetResourceIdentifier;
        synchronized (this) {
            ensureValidLocked();
            nativeGetResourceIdentifier = nativeGetResourceIdentifier(this.mObject, str, str2, str3);
        }
        return nativeGetResourceIdentifier;
    }

    int getParentThemeIdentifier(int i) {
        int nativeGetParentThemeIdentifier;
        synchronized (this) {
            ensureValidLocked();
            nativeGetParentThemeIdentifier = nativeGetParentThemeIdentifier(this.mObject, i);
        }
        return nativeGetParentThemeIdentifier;
    }

    public void setResourceResolutionLoggingEnabled(boolean z) {
        synchronized (this) {
            ensureValidLocked();
            nativeSetResourceResolutionLoggingEnabled(this.mObject, z);
        }
    }

    public java.lang.String getLastResourceResolution() {
        java.lang.String nativeGetLastResourceResolution;
        synchronized (this) {
            ensureValidLocked();
            nativeGetLastResourceResolution = nativeGetLastResourceResolution(this.mObject);
        }
        return nativeGetLastResourceResolution;
    }

    public boolean containsAllocatedTable() {
        boolean nativeContainsAllocatedTable;
        synchronized (this) {
            ensureValidLocked();
            nativeContainsAllocatedTable = nativeContainsAllocatedTable(this.mObject);
        }
        return nativeContainsAllocatedTable;
    }

    java.lang.CharSequence getPooledStringForCookie(int i, int i2) {
        return getApkAssets()[i - 1].getStringFromPool(i2);
    }

    public java.io.InputStream open(java.lang.String str) throws java.io.IOException {
        return open(str, 2);
    }

    public java.io.InputStream open(java.lang.String str, int i) throws java.io.IOException {
        android.content.res.AssetManager.AssetInputStream assetInputStream;
        java.util.Objects.requireNonNull(str, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            long nativeOpenAsset = nativeOpenAsset(this.mObject, str, i);
            if (nativeOpenAsset == 0) {
                throw new java.io.FileNotFoundException("Asset file: " + str);
            }
            assetInputStream = new android.content.res.AssetManager.AssetInputStream(nativeOpenAsset);
            incRefsLocked(assetInputStream.hashCode());
        }
        return assetInputStream;
    }

    public android.content.res.AssetFileDescriptor openFd(java.lang.String str) throws java.io.IOException {
        android.content.res.AssetFileDescriptor assetFileDescriptor;
        java.util.Objects.requireNonNull(str, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            android.os.ParcelFileDescriptor nativeOpenAssetFd = nativeOpenAssetFd(this.mObject, str, this.mOffsets);
            if (nativeOpenAssetFd == null) {
                throw new java.io.FileNotFoundException("Asset file: " + str);
            }
            assetFileDescriptor = new android.content.res.AssetFileDescriptor(nativeOpenAssetFd, this.mOffsets[0], this.mOffsets[1]);
        }
        return assetFileDescriptor;
    }

    public java.lang.String[] list(java.lang.String str) throws java.io.IOException {
        java.lang.String[] nativeList;
        java.util.Objects.requireNonNull(str, "path");
        synchronized (this) {
            ensureValidLocked();
            nativeList = nativeList(this.mObject, str);
        }
        return nativeList;
    }

    public java.io.InputStream openNonAsset(java.lang.String str) throws java.io.IOException {
        return openNonAsset(0, str, 2);
    }

    public java.io.InputStream openNonAsset(java.lang.String str, int i) throws java.io.IOException {
        return openNonAsset(0, str, i);
    }

    public java.io.InputStream openNonAsset(int i, java.lang.String str) throws java.io.IOException {
        return openNonAsset(i, str, 2);
    }

    public java.io.InputStream openNonAsset(int i, java.lang.String str, int i2) throws java.io.IOException {
        android.content.res.AssetManager.AssetInputStream assetInputStream;
        java.util.Objects.requireNonNull(str, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            long nativeOpenNonAsset = nativeOpenNonAsset(this.mObject, i, str, i2);
            if (nativeOpenNonAsset == 0) {
                throw new java.io.FileNotFoundException("Asset absolute file: " + str);
            }
            assetInputStream = new android.content.res.AssetManager.AssetInputStream(nativeOpenNonAsset);
            incRefsLocked(assetInputStream.hashCode());
        }
        return assetInputStream;
    }

    public android.content.res.AssetFileDescriptor openNonAssetFd(java.lang.String str) throws java.io.IOException {
        return openNonAssetFd(0, str);
    }

    public android.content.res.AssetFileDescriptor openNonAssetFd(int i, java.lang.String str) throws java.io.IOException {
        android.content.res.AssetFileDescriptor assetFileDescriptor;
        java.util.Objects.requireNonNull(str, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            android.os.ParcelFileDescriptor nativeOpenNonAssetFd = nativeOpenNonAssetFd(this.mObject, i, str, this.mOffsets);
            if (nativeOpenNonAssetFd == null) {
                throw new java.io.FileNotFoundException("Asset absolute file: " + str);
            }
            assetFileDescriptor = new android.content.res.AssetFileDescriptor(nativeOpenNonAssetFd, this.mOffsets[0], this.mOffsets[1]);
        }
        return assetFileDescriptor;
    }

    public android.content.res.XmlResourceParser openXmlResourceParser(java.lang.String str) throws java.io.IOException {
        return openXmlResourceParser(0, str);
    }

    public android.content.res.XmlResourceParser openXmlResourceParser(int i, java.lang.String str) throws java.io.IOException {
        android.content.res.XmlBlock openXmlBlockAsset = openXmlBlockAsset(i, str);
        try {
            android.content.res.XmlResourceParser newParser = openXmlBlockAsset.newParser(0, new android.content.res.Validator());
            if (newParser == null) {
                throw new java.lang.AssertionError("block.newParser() returned a null parser");
            }
            if (openXmlBlockAsset != null) {
                openXmlBlockAsset.close();
            }
            return newParser;
        } catch (java.lang.Throwable th) {
            if (openXmlBlockAsset != null) {
                try {
                    openXmlBlockAsset.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    android.content.res.XmlBlock openXmlBlockAsset(java.lang.String str) throws java.io.IOException {
        return openXmlBlockAsset(0, str);
    }

    android.content.res.XmlBlock openXmlBlockAsset(int i, java.lang.String str) throws java.io.IOException {
        android.content.res.XmlBlock xmlBlock;
        java.util.Objects.requireNonNull(str, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            long nativeOpenXmlAsset = nativeOpenXmlAsset(this.mObject, i, str);
            if (nativeOpenXmlAsset == 0) {
                throw new java.io.FileNotFoundException("Asset XML file: " + str);
            }
            xmlBlock = new android.content.res.XmlBlock(this, nativeOpenXmlAsset);
            incRefsLocked(xmlBlock.hashCode());
        }
        return xmlBlock;
    }

    void xmlBlockGone(int i) {
        synchronized (this) {
            decRefsLocked(i);
        }
    }

    void applyStyle(long j, int i, int i2, android.content.res.XmlBlock.Parser parser, int[] iArr, long j2, long j3) {
        java.util.Objects.requireNonNull(iArr, "inAttrs");
        synchronized (this) {
            ensureValidLocked();
            nativeApplyStyle(this.mObject, j, i, i2, parser != null ? parser.mParseState : 0L, iArr, j2, j3);
        }
    }

    int[] getAttributeResolutionStack(long j, int i, int i2, int i3) {
        int[] nativeAttributeResolutionStack;
        synchronized (this) {
            ensureValidLocked();
            nativeAttributeResolutionStack = nativeAttributeResolutionStack(this.mObject, j, i3, i, i2);
        }
        return nativeAttributeResolutionStack;
    }

    boolean resolveAttrs(long j, int i, int i2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
        boolean nativeResolveAttrs;
        java.util.Objects.requireNonNull(iArr2, "inAttrs");
        java.util.Objects.requireNonNull(iArr3, "outValues");
        java.util.Objects.requireNonNull(iArr4, "outIndices");
        synchronized (this) {
            ensureValidLocked();
            nativeResolveAttrs = nativeResolveAttrs(this.mObject, j, i, i2, iArr, iArr2, iArr3, iArr4);
        }
        return nativeResolveAttrs;
    }

    boolean retrieveAttributes(android.content.res.XmlBlock.Parser parser, int[] iArr, int[] iArr2, int[] iArr3) {
        boolean nativeRetrieveAttributes;
        java.util.Objects.requireNonNull(parser, "parser");
        java.util.Objects.requireNonNull(iArr, "inAttrs");
        java.util.Objects.requireNonNull(iArr2, "outValues");
        java.util.Objects.requireNonNull(iArr3, "outIndices");
        synchronized (this) {
            ensureValidLocked();
            nativeRetrieveAttributes = nativeRetrieveAttributes(this.mObject, parser.mParseState, iArr, iArr2, iArr3);
        }
        return nativeRetrieveAttributes;
    }

    long createTheme() {
        long nativeThemeCreate;
        synchronized (this) {
            ensureValidLocked();
            nativeThemeCreate = nativeThemeCreate(this.mObject);
            incRefsLocked(nativeThemeCreate);
        }
        return nativeThemeCreate;
    }

    void releaseTheme(long j) {
        synchronized (this) {
            decRefsLocked(j);
        }
    }

    static long getThemeFreeFunction() {
        return nativeGetThemeFreeFunction();
    }

    void applyStyleToTheme(long j, int i, boolean z) {
        synchronized (this) {
            ensureValidLocked();
            nativeThemeApplyStyle(this.mObject, j, i, z);
        }
    }

    android.content.res.AssetManager rebaseTheme(long j, android.content.res.AssetManager assetManager, int[] iArr, boolean[] zArr, int i) {
        if (this != assetManager) {
            synchronized (this) {
                ensureValidLocked();
                decRefsLocked(j);
            }
            synchronized (assetManager) {
                assetManager.ensureValidLocked();
                assetManager.incRefsLocked(j);
            }
        }
        try {
            synchronized (assetManager) {
                assetManager.ensureValidLocked();
                nativeThemeRebase(assetManager.mObject, j, iArr, zArr, i);
            }
            return assetManager;
        } finally {
            java.lang.ref.Reference.reachabilityFence(assetManager);
        }
    }

    void setThemeTo(long j, android.content.res.AssetManager assetManager, long j2) {
        synchronized (this) {
            ensureValidLocked();
            synchronized (assetManager) {
                assetManager.ensureValidLocked();
                nativeThemeCopy(this.mObject, j, assetManager.mObject, j2);
            }
        }
    }

    protected void finalize() throws java.lang.Throwable {
        synchronized (this) {
            if (this.mObject != 0) {
                nativeDestroy(this.mObject);
                this.mObject = 0L;
            }
        }
    }

    public final class AssetInputStream extends java.io.InputStream {
        private long mAssetNativePtr;
        private long mLength;
        private long mMarkPos;

        public final int getAssetInt() {
            throw new java.lang.UnsupportedOperationException();
        }

        public final long getNativeAsset() {
            return this.mAssetNativePtr;
        }

        private AssetInputStream(long j) {
            this.mAssetNativePtr = j;
            this.mLength = android.content.res.AssetManager.nativeAssetGetLength(j);
        }

        @Override // java.io.InputStream
        public final int read() throws java.io.IOException {
            ensureOpen();
            return android.content.res.AssetManager.nativeAssetReadChar(this.mAssetNativePtr);
        }

        @Override // java.io.InputStream
        public final int read(byte[] bArr) throws java.io.IOException {
            ensureOpen();
            java.util.Objects.requireNonNull(bArr, android.app.blob.XmlTags.TAG_BLOB);
            return android.content.res.AssetManager.nativeAssetRead(this.mAssetNativePtr, bArr, 0, bArr.length);
        }

        @Override // java.io.InputStream
        public final int read(byte[] bArr, int i, int i2) throws java.io.IOException {
            ensureOpen();
            java.util.Objects.requireNonNull(bArr, android.app.blob.XmlTags.TAG_BLOB);
            return android.content.res.AssetManager.nativeAssetRead(this.mAssetNativePtr, bArr, i, i2);
        }

        @Override // java.io.InputStream
        public final long skip(long j) throws java.io.IOException {
            ensureOpen();
            long nativeAssetSeek = android.content.res.AssetManager.nativeAssetSeek(this.mAssetNativePtr, 0L, 0);
            if (nativeAssetSeek + j > this.mLength) {
                j = this.mLength - nativeAssetSeek;
            }
            if (j > 0) {
                android.content.res.AssetManager.nativeAssetSeek(this.mAssetNativePtr, j, 0);
            }
            return j;
        }

        @Override // java.io.InputStream
        public final int available() throws java.io.IOException {
            ensureOpen();
            long nativeAssetGetRemainingLength = android.content.res.AssetManager.nativeAssetGetRemainingLength(this.mAssetNativePtr);
            if (nativeAssetGetRemainingLength > 2147483647L) {
                return Integer.MAX_VALUE;
            }
            return (int) nativeAssetGetRemainingLength;
        }

        @Override // java.io.InputStream
        public final boolean markSupported() {
            return true;
        }

        @Override // java.io.InputStream
        public final void mark(int i) {
            ensureOpen();
            this.mMarkPos = android.content.res.AssetManager.nativeAssetSeek(this.mAssetNativePtr, 0L, 0);
        }

        @Override // java.io.InputStream
        public final void reset() throws java.io.IOException {
            ensureOpen();
            android.content.res.AssetManager.nativeAssetSeek(this.mAssetNativePtr, this.mMarkPos, -1);
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public final void close() throws java.io.IOException {
            if (this.mAssetNativePtr != 0) {
                android.content.res.AssetManager.nativeAssetDestroy(this.mAssetNativePtr);
                this.mAssetNativePtr = 0L;
                synchronized (android.content.res.AssetManager.this) {
                    android.content.res.AssetManager.this.decRefsLocked(hashCode());
                }
            }
        }

        protected void finalize() throws java.lang.Throwable {
            close();
        }

        private void ensureOpen() {
            if (this.mAssetNativePtr == 0) {
                throw new java.lang.IllegalStateException("AssetInputStream is closed");
            }
        }
    }

    public boolean isUpToDate() {
        synchronized (this) {
            if (!this.mOpen) {
                return false;
            }
            for (android.content.res.ApkAssets apkAssets : this.mApkAssets) {
                if (!apkAssets.isUpToDate()) {
                    return false;
                }
            }
            return true;
        }
    }

    public java.lang.String[] getLocales() {
        java.lang.String[] nativeGetLocales;
        synchronized (this) {
            ensureValidLocked();
            nativeGetLocales = nativeGetLocales(this.mObject, false);
        }
        return nativeGetLocales;
    }

    public java.lang.String[] getNonSystemLocales() {
        java.lang.String[] nativeGetLocales;
        synchronized (this) {
            ensureValidLocked();
            nativeGetLocales = nativeGetLocales(this.mObject, true);
        }
        return nativeGetLocales;
    }

    android.content.res.Configuration[] getSizeConfigurations() {
        android.content.res.Configuration[] nativeGetSizeConfigurations;
        synchronized (this) {
            ensureValidLocked();
            nativeGetSizeConfigurations = nativeGetSizeConfigurations(this.mObject);
        }
        return nativeGetSizeConfigurations;
    }

    android.content.res.Configuration[] getSizeAndUiModeConfigurations() {
        android.content.res.Configuration[] nativeGetSizeAndUiModeConfigurations;
        synchronized (this) {
            ensureValidLocked();
            nativeGetSizeAndUiModeConfigurations = nativeGetSizeAndUiModeConfigurations(this.mObject);
        }
        return nativeGetSizeAndUiModeConfigurations;
    }

    public void setConfiguration(int i, int i2, java.lang.String str, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18) {
        if (str != null) {
            setConfiguration(i, i2, null, new java.lang.String[]{str}, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18);
        } else {
            setConfiguration(i, i2, null, null, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18);
        }
    }

    public void setConfiguration(int i, int i2, java.lang.String str, java.lang.String[] strArr, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18) {
        setConfigurationInternal(i, i2, str, strArr, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, false);
    }

    void setConfigurationInternal(int i, int i2, java.lang.String str, java.lang.String[] strArr, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, boolean z) {
        synchronized (this) {
            ensureValidLocked();
            nativeSetConfiguration(this.mObject, i, i2, str, strArr, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, z);
        }
    }

    public android.util.SparseArray<java.lang.String> getAssignedPackageIdentifiers() {
        return getAssignedPackageIdentifiers(true, true);
    }

    public android.util.SparseArray<java.lang.String> getAssignedPackageIdentifiers(boolean z, boolean z2) {
        android.util.SparseArray<java.lang.String> nativeGetAssignedPackageIdentifiers;
        synchronized (this) {
            ensureValidLocked();
            nativeGetAssignedPackageIdentifiers = nativeGetAssignedPackageIdentifiers(this.mObject, z, z2);
        }
        return nativeGetAssignedPackageIdentifiers;
    }

    public java.util.Map<java.lang.String, java.lang.String> getOverlayableMap(java.lang.String str) {
        java.util.Map<java.lang.String, java.lang.String> nativeGetOverlayableMap;
        synchronized (this) {
            ensureValidLocked();
            nativeGetOverlayableMap = nativeGetOverlayableMap(this.mObject, str);
        }
        return nativeGetOverlayableMap;
    }

    public java.lang.String getOverlayablesToString(java.lang.String str) {
        java.lang.String nativeGetOverlayablesToString;
        synchronized (this) {
            ensureValidLocked();
            nativeGetOverlayablesToString = nativeGetOverlayablesToString(this.mObject, str);
        }
        return nativeGetOverlayablesToString;
    }

    private void incRefsLocked(long j) {
        this.mNumRefs++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void decRefsLocked(long j) {
        this.mNumRefs--;
        if (this.mNumRefs == 0 && this.mObject != 0) {
            nativeDestroy(this.mObject);
            this.mObject = 0L;
            this.mApkAssets = sEmptyApkAssets;
        }
    }

    synchronized void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "class=" + getClass());
        printWriter.println(str + "apkAssets=");
        for (int i = 0; i < this.mApkAssets.length; i++) {
            printWriter.println(str + i);
            this.mApkAssets[i].dump(printWriter, str + "  ");
        }
    }
}
