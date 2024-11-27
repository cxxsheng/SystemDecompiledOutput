package android.app;

/* loaded from: classes.dex */
public class ResourcesManager {
    private static final boolean DEBUG = false;
    static final java.lang.String TAG = "ResourcesManager";
    private static android.app.ResourcesManager sResourcesManager;
    private java.util.ArrayList<android.util.Pair<java.lang.String[], android.content.pm.ApplicationInfo>> mPendingAppInfoUpdates;
    private android.content.res.CompatibilityInfo mResCompatibilityInfo;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.content.res.Configuration mResConfiguration = new android.content.res.Configuration();
    private int mResDisplayId = 0;
    private final android.util.ArrayMap<android.content.res.ResourcesKey, java.lang.ref.WeakReference<android.content.res.ResourcesImpl>> mResourceImpls = new android.util.ArrayMap<>();
    private final java.util.ArrayList<java.lang.ref.WeakReference<android.content.res.Resources>> mResourceReferences = new java.util.ArrayList<>();
    private final java.lang.ref.ReferenceQueue<android.content.res.Resources> mResourcesReferencesQueue = new java.lang.ref.ReferenceQueue<>();
    private android.app.LocaleConfig mLocaleConfig = new android.app.LocaleConfig(android.os.LocaleList.getEmptyLocaleList());
    private final android.util.ArrayMap<android.app.ResourcesManager.ApkKey, java.lang.ref.WeakReference<android.content.res.ApkAssets>> mCachedApkAssets = new android.util.ArrayMap<>();
    private final java.util.WeakHashMap<android.os.IBinder, android.app.ResourcesManager.ActivityResources> mActivityResourceReferences = new java.util.WeakHashMap<>();
    private final android.app.ResourcesManager.UpdateHandler mUpdateCallbacks = new android.app.ResourcesManager.UpdateHandler();
    private final android.util.ArraySet<java.lang.String> mApplicationOwnedApks = new android.util.ArraySet<>();

    private static class ApkKey {
        public final boolean overlay;
        public final java.lang.String path;
        public final boolean sharedLib;

        ApkKey(java.lang.String str, boolean z, boolean z2) {
            this.path = str;
            this.sharedLib = z;
            this.overlay = z2;
        }

        public int hashCode() {
            return ((((this.path.hashCode() + 31) * 31) + java.lang.Boolean.hashCode(this.sharedLib)) * 31) + java.lang.Boolean.hashCode(this.overlay);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.app.ResourcesManager.ApkKey)) {
                return false;
            }
            android.app.ResourcesManager.ApkKey apkKey = (android.app.ResourcesManager.ApkKey) obj;
            return this.path.equals(apkKey.path) && this.sharedLib == apkKey.sharedLib && this.overlay == apkKey.overlay;
        }
    }

    protected class ApkAssetsSupplier {
        final android.util.ArrayMap<android.app.ResourcesManager.ApkKey, android.content.res.ApkAssets> mLocalCache = new android.util.ArrayMap<>();

        protected ApkAssetsSupplier() {
        }

        android.content.res.ApkAssets load(android.app.ResourcesManager.ApkKey apkKey) throws java.io.IOException {
            android.content.res.ApkAssets apkAssets = this.mLocalCache.get(apkKey);
            if (apkAssets == null) {
                android.content.res.ApkAssets loadApkAssets = android.app.ResourcesManager.this.loadApkAssets(apkKey);
                this.mLocalCache.put(apkKey, loadApkAssets);
                return loadApkAssets;
            }
            return apkAssets;
        }
    }

    private static class ActivityResources {
        public final java.util.ArrayList<android.app.ResourcesManager.ActivityResource> activityResources;
        public final java.lang.ref.ReferenceQueue<android.content.res.Resources> activityResourcesQueue;
        public final android.content.res.Configuration overrideConfig;
        public int overrideDisplayId;

        private ActivityResources() {
            this.overrideConfig = new android.content.res.Configuration();
            this.activityResources = new java.util.ArrayList<>();
            this.activityResourcesQueue = new java.lang.ref.ReferenceQueue<>();
        }

        public int countLiveReferences() {
            int i = 0;
            for (int i2 = 0; i2 < this.activityResources.size(); i2++) {
                java.lang.ref.WeakReference<android.content.res.Resources> weakReference = this.activityResources.get(i2).resources;
                if (weakReference != null && weakReference.get() != null) {
                    i++;
                }
            }
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ActivityResource {
        public final android.content.res.Configuration overrideConfig;
        public java.lang.Integer overrideDisplayId;
        public java.lang.ref.WeakReference<android.content.res.Resources> resources;

        private ActivityResource() {
            this.overrideConfig = new android.content.res.Configuration();
        }
    }

    public static android.app.ResourcesManager getInstance() {
        android.app.ResourcesManager resourcesManager;
        synchronized (android.app.ResourcesManager.class) {
            if (sResourcesManager == null) {
                sResourcesManager = new android.app.ResourcesManager();
            }
            resourcesManager = sResourcesManager;
        }
        return resourcesManager;
    }

    public void invalidatePath(java.lang.String str) {
        android.content.res.ResourcesImpl resourcesImpl;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            for (int size = this.mResourceImpls.size() - 1; size >= 0; size--) {
                if (this.mResourceImpls.keyAt(size).isPathReferenced(str) && (resourcesImpl = this.mResourceImpls.removeAt(size).get()) != null) {
                    arrayList.add(resourcesImpl);
                }
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            ((android.content.res.ResourcesImpl) arrayList.get(i)).flushLayoutCache();
        }
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        synchronized (this.mCachedApkAssets) {
            for (int size2 = this.mCachedApkAssets.size() - 1; size2 >= 0; size2--) {
                if (this.mCachedApkAssets.keyAt(size2).path.equals(str)) {
                    java.lang.ref.WeakReference<android.content.res.ApkAssets> removeAt = this.mCachedApkAssets.removeAt(size2);
                    android.content.res.ApkAssets apkAssets = removeAt != null ? removeAt.get() : null;
                    if (apkAssets != null) {
                        arrayList2.add(apkAssets);
                    }
                }
            }
        }
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            ((android.content.res.ApkAssets) arrayList2.get(i2)).close();
        }
        android.util.Log.i(TAG, "Invalidated " + arrayList.size() + " asset managers that referenced " + str);
    }

    public android.content.res.Configuration getConfiguration() {
        return this.mResConfiguration;
    }

    public android.util.DisplayMetrics getDisplayMetrics() {
        return getDisplayMetrics(this.mResDisplayId, android.view.DisplayAdjustments.DEFAULT_DISPLAY_ADJUSTMENTS);
    }

    protected android.util.DisplayMetrics getDisplayMetrics(int i, android.view.DisplayAdjustments displayAdjustments) {
        android.hardware.display.DisplayManagerGlobal displayManagerGlobal = android.hardware.display.DisplayManagerGlobal.getInstance();
        android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
        android.view.DisplayInfo displayInfo = displayManagerGlobal != null ? displayManagerGlobal.getDisplayInfo(i) : null;
        if (displayInfo != null) {
            displayInfo.getAppMetrics(displayMetrics, displayAdjustments);
        } else {
            displayMetrics.setToDefaults();
        }
        return displayMetrics;
    }

    private android.util.DisplayMetrics getDisplayMetrics(android.content.res.Configuration configuration) {
        android.hardware.display.DisplayManagerGlobal displayManagerGlobal = android.hardware.display.DisplayManagerGlobal.getInstance();
        android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
        android.view.DisplayInfo displayInfo = displayManagerGlobal != null ? displayManagerGlobal.getDisplayInfo(this.mResDisplayId) : null;
        if (displayInfo != null) {
            displayInfo.getAppMetrics(displayMetrics, android.view.DisplayAdjustments.DEFAULT_DISPLAY_ADJUSTMENTS.getCompatibilityInfo(), configuration);
        } else {
            displayMetrics.setToDefaults();
        }
        return displayMetrics;
    }

    private static void applyDisplayMetricsToConfiguration(android.util.DisplayMetrics displayMetrics, android.content.res.Configuration configuration) {
        configuration.touchscreen = 1;
        configuration.densityDpi = displayMetrics.densityDpi;
        configuration.screenWidthDp = (int) ((displayMetrics.widthPixels / displayMetrics.density) + 0.5f);
        configuration.screenHeightDp = (int) ((displayMetrics.heightPixels / displayMetrics.density) + 0.5f);
        int resetScreenLayout = android.content.res.Configuration.resetScreenLayout(configuration.screenLayout);
        if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
            configuration.orientation = 2;
            configuration.screenLayout = android.content.res.Configuration.reduceScreenLayout(resetScreenLayout, configuration.screenWidthDp, configuration.screenHeightDp);
        } else {
            configuration.orientation = 1;
            configuration.screenLayout = android.content.res.Configuration.reduceScreenLayout(resetScreenLayout, configuration.screenHeightDp, configuration.screenWidthDp);
        }
        configuration.smallestScreenWidthDp = java.lang.Math.min(configuration.screenWidthDp, configuration.screenHeightDp);
        configuration.compatScreenWidthDp = configuration.screenWidthDp;
        configuration.compatScreenHeightDp = configuration.screenHeightDp;
        configuration.compatSmallestScreenWidthDp = configuration.smallestScreenWidthDp;
    }

    public boolean applyCompatConfiguration(int i, android.content.res.Configuration configuration) {
        synchronized (this.mLock) {
            if (this.mResCompatibilityInfo == null || this.mResCompatibilityInfo.supportsScreen()) {
                return false;
            }
            this.mResCompatibilityInfo.applyToConfiguration(i, configuration);
            return true;
        }
    }

    public android.view.Display getAdjustedDisplay(int i, android.content.res.Resources resources) {
        android.hardware.display.DisplayManagerGlobal displayManagerGlobal = android.hardware.display.DisplayManagerGlobal.getInstance();
        if (displayManagerGlobal == null) {
            return null;
        }
        return displayManagerGlobal.getCompatibleDisplay(i, resources);
    }

    public void initializeApplicationPaths(java.lang.String str, java.lang.String[] strArr) {
        synchronized (this.mLock) {
            if (this.mApplicationOwnedApks.isEmpty()) {
                addApplicationPathsLocked(str, strArr);
            }
        }
    }

    private void addApplicationPathsLocked(java.lang.String str, java.lang.String[] strArr) {
        this.mApplicationOwnedApks.add(str);
        if (strArr != null) {
            this.mApplicationOwnedApks.addAll(java.util.Arrays.asList(strArr));
        }
    }

    private static java.lang.String overlayPathToIdmapPath(java.lang.String str) {
        return "/data/resource-cache/" + str.substring(1).replace('/', '@') + "@idmap";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.res.ApkAssets loadApkAssets(android.app.ResourcesManager.ApkKey apkKey) throws java.io.IOException {
        java.lang.ref.WeakReference<android.content.res.ApkAssets> weakReference;
        int i;
        android.content.res.ApkAssets loadFromPath;
        android.content.res.ApkAssets apkAssets;
        synchronized (this.mCachedApkAssets) {
            weakReference = this.mCachedApkAssets.get(apkKey);
        }
        if (weakReference != null && (apkAssets = weakReference.get()) != null && apkAssets.isUpToDate()) {
            return apkAssets;
        }
        if (!apkKey.sharedLib) {
            i = 0;
        } else {
            i = 2;
        }
        if (this.mApplicationOwnedApks.contains(apkKey.path)) {
            i |= 16;
        }
        if (apkKey.overlay) {
            loadFromPath = android.content.res.ApkAssets.loadOverlayFromPath(overlayPathToIdmapPath(apkKey.path), i);
        } else {
            loadFromPath = android.content.res.ApkAssets.loadFromPath(apkKey.path, i);
        }
        synchronized (this.mCachedApkAssets) {
            this.mCachedApkAssets.put(apkKey, new java.lang.ref.WeakReference<>(loadFromPath));
        }
        return loadFromPath;
    }

    private static java.util.ArrayList<android.app.ResourcesManager.ApkKey> extractApkKeys(android.content.res.ResourcesKey resourcesKey) {
        java.util.ArrayList<android.app.ResourcesManager.ApkKey> arrayList = new java.util.ArrayList<>();
        if (resourcesKey.mResDir != null) {
            arrayList.add(new android.app.ResourcesManager.ApkKey(resourcesKey.mResDir, false, false));
        }
        if (resourcesKey.mSplitResDirs != null) {
            for (java.lang.String str : resourcesKey.mSplitResDirs) {
                arrayList.add(new android.app.ResourcesManager.ApkKey(str, false, false));
            }
        }
        if (resourcesKey.mLibDirs != null) {
            for (java.lang.String str2 : resourcesKey.mLibDirs) {
                if (str2.endsWith(".apk")) {
                    arrayList.add(new android.app.ResourcesManager.ApkKey(str2, true, false));
                }
            }
        }
        if (resourcesKey.mOverlayPaths != null) {
            for (java.lang.String str3 : resourcesKey.mOverlayPaths) {
                arrayList.add(new android.app.ResourcesManager.ApkKey(str3, false, true));
            }
        }
        return arrayList;
    }

    protected android.content.res.AssetManager createAssetManager(android.content.res.ResourcesKey resourcesKey) {
        return createAssetManager(resourcesKey, null);
    }

    protected android.content.res.AssetManager createAssetManager(android.content.res.ResourcesKey resourcesKey, android.app.ResourcesManager.ApkAssetsSupplier apkAssetsSupplier) {
        android.content.res.ApkAssets load;
        android.content.res.AssetManager.Builder noInit = new android.content.res.AssetManager.Builder().setNoInit();
        java.util.ArrayList<android.app.ResourcesManager.ApkKey> extractApkKeys = extractApkKeys(resourcesKey);
        int size = extractApkKeys.size();
        for (int i = 0; i < size; i++) {
            android.app.ResourcesManager.ApkKey apkKey = extractApkKeys.get(i);
            if (apkAssetsSupplier != null) {
                try {
                    load = apkAssetsSupplier.load(apkKey);
                } catch (java.io.IOException e) {
                    if (apkKey.overlay) {
                        android.util.Log.w(TAG, java.lang.String.format("failed to add overlay path '%s'", apkKey.path), e);
                    } else if (apkKey.sharedLib) {
                        android.util.Log.w(TAG, java.lang.String.format("asset path '%s' does not exist or contains no resources", apkKey.path), e);
                    } else {
                        android.util.Log.e(TAG, java.lang.String.format("failed to add asset path '%s'", apkKey.path), e);
                        return null;
                    }
                }
            } else {
                load = loadApkAssets(apkKey);
            }
            noInit.addApkAssets(load);
        }
        if (resourcesKey.mLoaders != null) {
            for (android.content.res.loader.ResourcesLoader resourcesLoader : resourcesKey.mLoaders) {
                noInit.addLoader(resourcesLoader);
            }
        }
        return noInit.build();
    }

    private static <T> int countLiveReferences(java.util.Collection<java.lang.ref.WeakReference<T>> collection) {
        java.util.Iterator<java.lang.ref.WeakReference<T>> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            java.lang.ref.WeakReference<T> next = it.next();
            if ((next != null ? next.get() : null) != null) {
                i++;
            }
        }
        return i;
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        int countLiveReferences;
        int countLiveReferences2;
        int countLiveReferences3;
        synchronized (this.mLock) {
            countLiveReferences = countLiveReferences(this.mResourceReferences);
            java.util.Iterator<android.app.ResourcesManager.ActivityResources> it = this.mActivityResourceReferences.values().iterator();
            while (it.hasNext()) {
                countLiveReferences += it.next().countLiveReferences();
            }
            countLiveReferences2 = countLiveReferences(this.mResourceImpls.values());
        }
        synchronized (this.mCachedApkAssets) {
            countLiveReferences3 = countLiveReferences(this.mCachedApkAssets.values());
        }
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "  ");
        for (int i = 0; i < str.length() / 2; i++) {
            indentingPrintWriter.increaseIndent();
        }
        indentingPrintWriter.println("ResourcesManager:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("total apks: ");
        indentingPrintWriter.println(countLiveReferences3);
        indentingPrintWriter.print("resources: ");
        indentingPrintWriter.println(countLiveReferences);
        indentingPrintWriter.print("resource impls: ");
        indentingPrintWriter.println(countLiveReferences2);
    }

    private android.content.res.Configuration generateConfig(android.content.res.ResourcesKey resourcesKey) {
        if (resourcesKey.hasOverrideConfiguration()) {
            android.content.res.Configuration configuration = new android.content.res.Configuration(getConfiguration());
            configuration.updateFrom(resourcesKey.mOverrideConfiguration);
            return configuration;
        }
        return getConfiguration();
    }

    private int generateDisplayId(android.content.res.ResourcesKey resourcesKey) {
        return resourcesKey.mDisplayId != -1 ? resourcesKey.mDisplayId : this.mResDisplayId;
    }

    private android.content.res.ResourcesImpl createResourcesImpl(android.content.res.ResourcesKey resourcesKey, android.app.ResourcesManager.ApkAssetsSupplier apkAssetsSupplier) {
        android.content.res.AssetManager createAssetManager = createAssetManager(resourcesKey, apkAssetsSupplier);
        if (createAssetManager == null) {
            return null;
        }
        android.view.DisplayAdjustments displayAdjustments = new android.view.DisplayAdjustments(resourcesKey.mOverrideConfiguration);
        displayAdjustments.setCompatibilityInfo(resourcesKey.mCompatInfo);
        return new android.content.res.ResourcesImpl(createAssetManager, getDisplayMetrics(generateDisplayId(resourcesKey), displayAdjustments), generateConfig(resourcesKey), displayAdjustments);
    }

    private android.content.res.ResourcesImpl findResourcesImplForKeyLocked(android.content.res.ResourcesKey resourcesKey) {
        java.lang.ref.WeakReference<android.content.res.ResourcesImpl> weakReference = this.mResourceImpls.get(resourcesKey);
        android.content.res.ResourcesImpl resourcesImpl = weakReference != null ? weakReference.get() : null;
        if (resourcesImpl == null || !resourcesImpl.getAssets().isUpToDate()) {
            return null;
        }
        return resourcesImpl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.res.ResourcesImpl findOrCreateResourcesImplForKeyLocked(android.content.res.ResourcesKey resourcesKey) {
        return findOrCreateResourcesImplForKeyLocked(resourcesKey, null);
    }

    private android.content.res.ResourcesImpl findOrCreateResourcesImplForKeyLocked(android.content.res.ResourcesKey resourcesKey, android.app.ResourcesManager.ApkAssetsSupplier apkAssetsSupplier) {
        android.content.res.ResourcesImpl findResourcesImplForKeyLocked = findResourcesImplForKeyLocked(resourcesKey);
        if (findResourcesImplForKeyLocked == null && (findResourcesImplForKeyLocked = createResourcesImpl(resourcesKey, apkAssetsSupplier)) != null) {
            this.mResourceImpls.put(resourcesKey, new java.lang.ref.WeakReference<>(findResourcesImplForKeyLocked));
        }
        return findResourcesImplForKeyLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.res.ResourcesKey findKeyForResourceImplLocked(android.content.res.ResourcesImpl resourcesImpl) {
        int size = this.mResourceImpls.size();
        for (int i = 0; i < size; i++) {
            java.lang.ref.WeakReference<android.content.res.ResourcesImpl> valueAt = this.mResourceImpls.valueAt(i);
            if (valueAt != null && valueAt.refersTo(resourcesImpl)) {
                return this.mResourceImpls.keyAt(i);
            }
        }
        return null;
    }

    public boolean isSameResourcesOverrideConfig(android.os.IBinder iBinder, android.content.res.Configuration configuration) {
        android.app.ResourcesManager.ActivityResources activityResources;
        synchronized (this.mLock) {
            if (iBinder == null) {
                activityResources = null;
            } else {
                try {
                    activityResources = this.mActivityResourceReferences.get(iBinder);
                } finally {
                }
            }
            boolean z = true;
            if (activityResources == null) {
                if (configuration != null) {
                    z = false;
                }
                return z;
            }
            if (!java.util.Objects.equals(activityResources.overrideConfig, configuration) && (configuration == null || activityResources.overrideConfig == null || configuration.diffPublicOnly(activityResources.overrideConfig) != 0)) {
                z = false;
            }
            return z;
        }
    }

    private android.app.ResourcesManager.ActivityResources getOrCreateActivityResourcesStructLocked(android.os.IBinder iBinder) {
        android.app.ResourcesManager.ActivityResources activityResources = this.mActivityResourceReferences.get(iBinder);
        if (activityResources == null) {
            android.app.ResourcesManager.ActivityResources activityResources2 = new android.app.ResourcesManager.ActivityResources();
            this.mActivityResourceReferences.put(iBinder, activityResources2);
            return activityResources2;
        }
        return activityResources;
    }

    private android.content.res.Resources findResourcesForActivityLocked(android.os.IBinder iBinder, android.content.res.ResourcesKey resourcesKey, java.lang.ClassLoader classLoader) {
        android.app.ResourcesManager.ActivityResources orCreateActivityResourcesStructLocked = getOrCreateActivityResourcesStructLocked(iBinder);
        int size = orCreateActivityResourcesStructLocked.activityResources.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                return null;
            }
            android.content.res.Resources resources = orCreateActivityResourcesStructLocked.activityResources.get(i).resources.get();
            android.content.res.ResourcesKey findKeyForResourceImplLocked = resources != null ? findKeyForResourceImplLocked(resources.getImpl()) : null;
            if (findKeyForResourceImplLocked == null || !java.util.Objects.equals(resources.getClassLoader(), classLoader) || !java.util.Objects.equals(findKeyForResourceImplLocked, resourcesKey)) {
                i++;
            } else {
                return resources;
            }
        }
    }

    private android.content.res.Resources createResourcesForActivityLocked(android.os.IBinder iBinder, android.content.res.Configuration configuration, java.lang.Integer num, java.lang.ClassLoader classLoader, android.content.res.ResourcesImpl resourcesImpl, android.content.res.CompatibilityInfo compatibilityInfo) {
        android.app.ResourcesManager.ActivityResources orCreateActivityResourcesStructLocked = getOrCreateActivityResourcesStructLocked(iBinder);
        cleanupReferences(orCreateActivityResourcesStructLocked.activityResources, orCreateActivityResourcesStructLocked.activityResourcesQueue, new java.util.function.Function() { // from class: android.app.ResourcesManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.ref.WeakReference weakReference;
                weakReference = ((android.app.ResourcesManager.ActivityResource) obj).resources;
                return weakReference;
            }
        });
        android.content.res.Resources compatResources = compatibilityInfo.needsCompatResources() ? new android.content.res.CompatResources(classLoader) : new android.content.res.Resources(classLoader);
        compatResources.setImpl(resourcesImpl);
        compatResources.setCallbacks(this.mUpdateCallbacks);
        android.app.ResourcesManager.ActivityResource activityResource = new android.app.ResourcesManager.ActivityResource();
        activityResource.resources = new java.lang.ref.WeakReference<>(compatResources, orCreateActivityResourcesStructLocked.activityResourcesQueue);
        activityResource.overrideConfig.setTo(configuration);
        activityResource.overrideDisplayId = num;
        orCreateActivityResourcesStructLocked.activityResources.add(activityResource);
        return compatResources;
    }

    private android.content.res.Resources createResourcesLocked(java.lang.ClassLoader classLoader, android.content.res.ResourcesImpl resourcesImpl, android.content.res.CompatibilityInfo compatibilityInfo) {
        cleanupReferences(this.mResourceReferences, this.mResourcesReferencesQueue);
        android.content.res.Resources compatResources = compatibilityInfo.needsCompatResources() ? new android.content.res.CompatResources(classLoader) : new android.content.res.Resources(classLoader);
        compatResources.setImpl(resourcesImpl);
        compatResources.setCallbacks(this.mUpdateCallbacks);
        this.mResourceReferences.add(new java.lang.ref.WeakReference<>(compatResources, this.mResourcesReferencesQueue));
        return compatResources;
    }

    public android.content.res.Resources createBaseTokenResources(android.os.IBinder iBinder, java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, java.lang.String[] strArr4, int i, android.content.res.Configuration configuration, android.content.res.CompatibilityInfo compatibilityInfo, java.lang.ClassLoader classLoader, java.util.List<android.content.res.loader.ResourcesLoader> list) {
        try {
            android.os.Trace.traceBegin(8192L, "ResourcesManager#createBaseActivityResources");
            android.content.res.ResourcesKey resourcesKey = new android.content.res.ResourcesKey(str, strArr, combinedOverlayPaths(strArr2, strArr3), strArr4, i, configuration, compatibilityInfo, list == null ? null : (android.content.res.loader.ResourcesLoader[]) list.toArray(new android.content.res.loader.ResourcesLoader[0]));
            java.lang.ClassLoader systemClassLoader = classLoader != null ? classLoader : java.lang.ClassLoader.getSystemClassLoader();
            synchronized (this.mLock) {
                getOrCreateActivityResourcesStructLocked(iBinder);
            }
            updateResourcesForActivity(iBinder, configuration, i);
            synchronized (this.mLock) {
                android.content.res.Resources findResourcesForActivityLocked = findResourcesForActivityLocked(iBinder, resourcesKey, systemClassLoader);
                if (findResourcesForActivityLocked != null) {
                    return findResourcesForActivityLocked;
                }
                return createResourcesForActivity(iBinder, resourcesKey, android.content.res.Configuration.EMPTY, null, systemClassLoader, null);
            }
        } finally {
            android.os.Trace.traceEnd(8192L);
        }
    }

    private void rebaseKeyForActivity(android.os.IBinder iBinder, android.content.res.ResourcesKey resourcesKey, boolean z) {
        android.content.res.Configuration configuration;
        synchronized (this.mLock) {
            android.app.ResourcesManager.ActivityResources orCreateActivityResourcesStructLocked = getOrCreateActivityResourcesStructLocked(iBinder);
            if (resourcesKey.mDisplayId == -1) {
                resourcesKey.mDisplayId = orCreateActivityResourcesStructLocked.overrideDisplayId;
            }
            if (resourcesKey.hasOverrideConfiguration()) {
                configuration = new android.content.res.Configuration(orCreateActivityResourcesStructLocked.overrideConfig);
                configuration.updateFrom(resourcesKey.mOverrideConfiguration);
            } else {
                configuration = orCreateActivityResourcesStructLocked.overrideConfig;
            }
            if (z && resourcesKey.mOverrideConfiguration.windowConfiguration.getAppBounds() == null) {
                if (!resourcesKey.hasOverrideConfiguration()) {
                    configuration = new android.content.res.Configuration(configuration);
                }
                configuration.windowConfiguration.setAppBounds(null);
            }
            resourcesKey.mOverrideConfiguration.setTo(configuration);
        }
    }

    private void rebaseKeyForDisplay(android.content.res.ResourcesKey resourcesKey, int i) {
        android.content.res.Configuration configuration = new android.content.res.Configuration();
        android.view.DisplayAdjustments displayAdjustments = new android.view.DisplayAdjustments(resourcesKey.mOverrideConfiguration);
        displayAdjustments.setCompatibilityInfo(resourcesKey.mCompatInfo);
        applyDisplayMetricsToConfiguration(getDisplayMetrics(i, displayAdjustments), configuration);
        if (resourcesKey.hasOverrideConfiguration()) {
            configuration.updateFrom(resourcesKey.mOverrideConfiguration);
        }
        resourcesKey.mOverrideConfiguration.setTo(configuration);
    }

    private static <T> void cleanupReferences(java.util.ArrayList<java.lang.ref.WeakReference<T>> arrayList, java.lang.ref.ReferenceQueue<T> referenceQueue) {
        cleanupReferences(arrayList, referenceQueue, java.util.function.Function.identity());
    }

    private static <C, T> void cleanupReferences(java.util.ArrayList<C> arrayList, java.lang.ref.ReferenceQueue<T> referenceQueue, final java.util.function.Function<C, java.lang.ref.WeakReference<T>> function) {
        java.lang.ref.Reference<? extends T> poll = referenceQueue.poll();
        if (poll == null) {
            return;
        }
        final java.util.HashSet hashSet = new java.util.HashSet();
        while (poll != null) {
            hashSet.add(poll);
            poll = referenceQueue.poll();
        }
        com.android.internal.util.ArrayUtils.unstableRemoveIf(arrayList, new java.util.function.Predicate() { // from class: android.app.ResourcesManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.app.ResourcesManager.lambda$cleanupReferences$1(function, hashSet, obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$cleanupReferences$1(java.util.function.Function function, java.util.HashSet hashSet, java.lang.Object obj) {
        java.lang.ref.WeakReference weakReference = (java.lang.ref.WeakReference) function.apply(obj);
        return weakReference == null || hashSet.contains(weakReference);
    }

    private android.app.ResourcesManager.ApkAssetsSupplier createApkAssetsSupplierNotLocked(android.content.res.ResourcesKey resourcesKey) {
        android.os.Trace.traceBegin(8192L, "ResourcesManager#createApkAssetsSupplierNotLocked");
        try {
            android.app.ResourcesManager.ApkAssetsSupplier apkAssetsSupplier = new android.app.ResourcesManager.ApkAssetsSupplier();
            java.util.ArrayList<android.app.ResourcesManager.ApkKey> extractApkKeys = extractApkKeys(resourcesKey);
            int size = extractApkKeys.size();
            for (int i = 0; i < size; i++) {
                android.app.ResourcesManager.ApkKey apkKey = extractApkKeys.get(i);
                try {
                    apkAssetsSupplier.load(apkKey);
                } catch (java.io.IOException e) {
                    android.util.Log.w(TAG, java.lang.String.format("failed to preload asset path '%s'", apkKey.path), e);
                }
            }
            return apkAssetsSupplier;
        } finally {
            android.os.Trace.traceEnd(8192L);
        }
    }

    private android.content.res.Resources createResources(android.content.res.ResourcesKey resourcesKey, java.lang.ClassLoader classLoader, android.app.ResourcesManager.ApkAssetsSupplier apkAssetsSupplier) {
        synchronized (this.mLock) {
            android.content.res.ResourcesImpl findOrCreateResourcesImplForKeyLocked = findOrCreateResourcesImplForKeyLocked(resourcesKey, apkAssetsSupplier);
            if (findOrCreateResourcesImplForKeyLocked == null) {
                return null;
            }
            return createResourcesLocked(classLoader, findOrCreateResourcesImplForKeyLocked, resourcesKey.mCompatInfo);
        }
    }

    private android.content.res.Resources createResourcesForActivity(android.os.IBinder iBinder, android.content.res.ResourcesKey resourcesKey, android.content.res.Configuration configuration, java.lang.Integer num, java.lang.ClassLoader classLoader, android.app.ResourcesManager.ApkAssetsSupplier apkAssetsSupplier) {
        synchronized (this.mLock) {
            android.content.res.ResourcesImpl findOrCreateResourcesImplForKeyLocked = findOrCreateResourcesImplForKeyLocked(resourcesKey, apkAssetsSupplier);
            if (findOrCreateResourcesImplForKeyLocked == null) {
                return null;
            }
            return createResourcesForActivityLocked(iBinder, configuration, num, classLoader, findOrCreateResourcesImplForKeyLocked, resourcesKey.mCompatInfo);
        }
    }

    public android.content.res.Resources getResources(android.os.IBinder iBinder, java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, java.lang.String[] strArr4, java.lang.Integer num, android.content.res.Configuration configuration, android.content.res.CompatibilityInfo compatibilityInfo, java.lang.ClassLoader classLoader, java.util.List<android.content.res.loader.ResourcesLoader> list) {
        android.content.res.Resources createResources;
        try {
            android.os.Trace.traceBegin(8192L, "ResourcesManager#getResources");
            android.content.res.ResourcesKey resourcesKey = new android.content.res.ResourcesKey(str, strArr, combinedOverlayPaths(strArr2, strArr3), strArr4, num != null ? num.intValue() : -1, configuration, compatibilityInfo, list == null ? null : (android.content.res.loader.ResourcesLoader[]) list.toArray(new android.content.res.loader.ResourcesLoader[0]));
            java.lang.ClassLoader systemClassLoader = classLoader != null ? classLoader : java.lang.ClassLoader.getSystemClassLoader();
            android.app.ResourcesManager.ApkAssetsSupplier createApkAssetsSupplierNotLocked = createApkAssetsSupplierNotLocked(resourcesKey);
            if (num != null) {
                rebaseKeyForDisplay(resourcesKey, num.intValue());
            }
            if (iBinder != null) {
                android.content.res.Configuration configuration2 = new android.content.res.Configuration(resourcesKey.mOverrideConfiguration);
                rebaseKeyForActivity(iBinder, resourcesKey, num != null);
                createResources = createResourcesForActivity(iBinder, resourcesKey, configuration2, num, systemClassLoader, createApkAssetsSupplierNotLocked);
            } else {
                createResources = createResources(resourcesKey, systemClassLoader, createApkAssetsSupplierNotLocked);
            }
            return createResources;
        } finally {
            android.os.Trace.traceEnd(8192L);
        }
    }

    public void updateResourcesForActivity(android.os.IBinder iBinder, android.content.res.Configuration configuration, int i) {
        android.content.res.ResourcesKey rebaseActivityOverrideConfig;
        android.content.res.ResourcesImpl findOrCreateResourcesImplForKeyLocked;
        try {
            android.os.Trace.traceBegin(8192L, "ResourcesManager#updateResourcesForActivity");
            if (i == -1) {
                throw new java.lang.IllegalArgumentException("displayId can not be INVALID_DISPLAY");
            }
            synchronized (this.mLock) {
                android.app.ResourcesManager.ActivityResources orCreateActivityResourcesStructLocked = getOrCreateActivityResourcesStructLocked(iBinder);
                boolean z = orCreateActivityResourcesStructLocked.overrideDisplayId != i;
                if (java.util.Objects.equals(orCreateActivityResourcesStructLocked.overrideConfig, configuration) && !z) {
                    return;
                }
                new android.content.res.Configuration(orCreateActivityResourcesStructLocked.overrideConfig);
                if (configuration != null) {
                    orCreateActivityResourcesStructLocked.overrideConfig.setTo(configuration);
                } else {
                    orCreateActivityResourcesStructLocked.overrideConfig.unset();
                }
                orCreateActivityResourcesStructLocked.overrideDisplayId = i;
                applyAllPendingAppInfoUpdates();
                int size = orCreateActivityResourcesStructLocked.activityResources.size();
                for (int i2 = 0; i2 < size; i2++) {
                    android.app.ResourcesManager.ActivityResource activityResource = orCreateActivityResourcesStructLocked.activityResources.get(i2);
                    android.content.res.Resources resources = activityResource.resources.get();
                    if (resources != null && (rebaseActivityOverrideConfig = rebaseActivityOverrideConfig(activityResource, configuration, i)) != null && (findOrCreateResourcesImplForKeyLocked = findOrCreateResourcesImplForKeyLocked(rebaseActivityOverrideConfig)) != null && findOrCreateResourcesImplForKeyLocked != resources.getImpl()) {
                        resources.setImpl(findOrCreateResourcesImplForKeyLocked);
                    }
                }
            }
        } finally {
            android.os.Trace.traceEnd(8192L);
        }
    }

    private android.content.res.ResourcesKey rebaseActivityOverrideConfig(android.app.ResourcesManager.ActivityResource activityResource, android.content.res.Configuration configuration, int i) {
        android.content.res.Resources resources = activityResource.resources.get();
        if (resources == null) {
            return null;
        }
        android.content.res.ResourcesKey findKeyForResourceImplLocked = findKeyForResourceImplLocked(resources.getImpl());
        if (findKeyForResourceImplLocked == null) {
            android.util.Slog.e(TAG, "can't find ResourcesKey for resources impl=" + resources.getImpl());
            return null;
        }
        android.content.res.Configuration configuration2 = new android.content.res.Configuration();
        if (configuration != null) {
            configuration2.setTo(configuration);
        }
        java.lang.Integer num = activityResource.overrideDisplayId;
        if (num != null) {
            android.view.DisplayAdjustments displayAdjustments = new android.view.DisplayAdjustments(configuration2);
            displayAdjustments.getConfiguration().setTo(activityResource.overrideConfig);
            displayAdjustments.setCompatibilityInfo(findKeyForResourceImplLocked.mCompatInfo);
            applyDisplayMetricsToConfiguration(getDisplayMetrics(num.intValue(), displayAdjustments), configuration2);
        }
        if (!activityResource.overrideConfig.equals(android.content.res.Configuration.EMPTY)) {
            configuration2.updateFrom(activityResource.overrideConfig);
        }
        if (activityResource.overrideDisplayId != null && activityResource.overrideConfig.windowConfiguration.getAppBounds() == null) {
            configuration2.windowConfiguration.setAppBounds(null);
        }
        if (num != null) {
            i = num.intValue();
        }
        return new android.content.res.ResourcesKey(findKeyForResourceImplLocked.mResDir, findKeyForResourceImplLocked.mSplitResDirs, findKeyForResourceImplLocked.mOverlayPaths, findKeyForResourceImplLocked.mLibDirs, i, configuration2, findKeyForResourceImplLocked.mCompatInfo, findKeyForResourceImplLocked.mLoaders);
    }

    public void appendPendingAppInfoUpdate(java.lang.String[] strArr, android.content.pm.ApplicationInfo applicationInfo) {
        synchronized (this.mLock) {
            if (this.mPendingAppInfoUpdates == null) {
                this.mPendingAppInfoUpdates = new java.util.ArrayList<>();
            }
            for (int size = this.mPendingAppInfoUpdates.size() - 1; size >= 0; size--) {
                if (com.android.internal.util.ArrayUtils.containsAll(strArr, this.mPendingAppInfoUpdates.get(size).first)) {
                    this.mPendingAppInfoUpdates.remove(size);
                }
            }
            this.mPendingAppInfoUpdates.add(new android.util.Pair<>(strArr, applicationInfo));
        }
    }

    public final void applyAllPendingAppInfoUpdates() {
        synchronized (this.mLock) {
            if (this.mPendingAppInfoUpdates != null) {
                int size = this.mPendingAppInfoUpdates.size();
                for (int i = 0; i < size; i++) {
                    android.util.Pair<java.lang.String[], android.content.pm.ApplicationInfo> pair = this.mPendingAppInfoUpdates.get(i);
                    applyNewResourceDirsLocked(pair.first, pair.second);
                }
                this.mPendingAppInfoUpdates = null;
            }
        }
    }

    public final boolean applyConfigurationToResources(android.content.res.Configuration configuration, android.content.res.CompatibilityInfo compatibilityInfo) {
        int i;
        int i2;
        synchronized (this.mLock) {
            try {
                android.os.Trace.traceBegin(8192L, "ResourcesManager#applyConfigurationToResources");
                if (!this.mResConfiguration.isOtherSeqNewer(configuration) && compatibilityInfo == null) {
                    return false;
                }
                int updateFrom = this.mResConfiguration.updateFrom(configuration);
                if (compatibilityInfo != null && (this.mResCompatibilityInfo == null || !this.mResCompatibilityInfo.equals(compatibilityInfo))) {
                    this.mResCompatibilityInfo = compatibilityInfo;
                    i = updateFrom | 3328;
                } else {
                    i = updateFrom;
                }
                if ((Integer.MIN_VALUE & i) != 0) {
                    applyAllPendingAppInfoUpdates();
                }
                android.content.res.Resources.updateSystemConfiguration(configuration, getDisplayMetrics(configuration), compatibilityInfo);
                android.app.ApplicationPackageManager.configurationChanged();
                android.content.res.Configuration configuration2 = new android.content.res.Configuration();
                int size = this.mResourceImpls.size() - 1;
                while (size >= 0) {
                    android.content.res.ResourcesKey keyAt = this.mResourceImpls.keyAt(size);
                    java.lang.ref.WeakReference<android.content.res.ResourcesImpl> valueAt = this.mResourceImpls.valueAt(size);
                    android.content.res.ResourcesImpl resourcesImpl = valueAt != null ? valueAt.get() : null;
                    if (resourcesImpl != null) {
                        i2 = size;
                        applyConfigurationToResourcesLocked(configuration, compatibilityInfo, configuration2, keyAt, resourcesImpl);
                    } else {
                        i2 = size;
                        this.mResourceImpls.removeAt(i2);
                    }
                    size = i2 - 1;
                }
                return i != 0;
            } finally {
                android.os.Trace.traceEnd(8192L);
            }
        }
    }

    private void applyConfigurationToResourcesLocked(android.content.res.Configuration configuration, android.content.res.CompatibilityInfo compatibilityInfo, android.content.res.Configuration configuration2, android.content.res.ResourcesKey resourcesKey, android.content.res.ResourcesImpl resourcesImpl) {
        configuration2.setTo(configuration);
        if (resourcesKey.hasOverrideConfiguration()) {
            configuration2.updateFrom(resourcesKey.mOverrideConfiguration);
        }
        android.view.DisplayAdjustments displayAdjustments = resourcesImpl.getDisplayAdjustments();
        if (compatibilityInfo != null) {
            android.view.DisplayAdjustments displayAdjustments2 = new android.view.DisplayAdjustments(displayAdjustments);
            displayAdjustments2.setCompatibilityInfo(compatibilityInfo);
            displayAdjustments = displayAdjustments2;
        }
        displayAdjustments.setConfiguration(configuration2);
        resourcesImpl.updateConfiguration(configuration2, getDisplayMetrics(generateDisplayId(resourcesKey), displayAdjustments), compatibilityInfo);
    }

    public void appendLibAssetForMainAssetPath(java.lang.String str, java.lang.String str2) {
        appendLibAssetsForMainAssetPath(str, new java.lang.String[]{str2});
    }

    public void appendLibAssetsForMainAssetPath(java.lang.String str, java.lang.String[] strArr) {
        java.lang.String[] strArr2 = strArr;
        synchronized (this.mLock) {
            android.util.ArrayMap<android.content.res.ResourcesImpl, android.content.res.ResourcesKey> arrayMap = new android.util.ArrayMap<>();
            int size = this.mResourceImpls.size();
            int i = 0;
            while (i < size) {
                android.content.res.ResourcesKey keyAt = this.mResourceImpls.keyAt(i);
                java.lang.ref.WeakReference<android.content.res.ResourcesImpl> valueAt = this.mResourceImpls.valueAt(i);
                android.content.res.ResourcesImpl resourcesImpl = valueAt != null ? valueAt.get() : null;
                if (resourcesImpl != null && java.util.Objects.equals(keyAt.mResDir, str)) {
                    java.lang.String[] strArr3 = keyAt.mLibDirs;
                    for (java.lang.String str2 : strArr2) {
                        strArr3 = (java.lang.String[]) com.android.internal.util.ArrayUtils.appendElement(java.lang.String.class, strArr3, str2);
                    }
                    if (!java.util.Arrays.equals(strArr3, keyAt.mLibDirs)) {
                        arrayMap.put(resourcesImpl, new android.content.res.ResourcesKey(keyAt.mResDir, keyAt.mSplitResDirs, keyAt.mOverlayPaths, strArr3, keyAt.mDisplayId, keyAt.mOverrideConfiguration, keyAt.mCompatInfo, keyAt.mLoaders));
                    }
                }
                i++;
                strArr2 = strArr;
            }
            redirectResourcesToNewImplLocked(arrayMap);
        }
    }

    private void applyNewResourceDirsLocked(java.lang.String[] strArr, android.content.pm.ApplicationInfo applicationInfo) {
        java.lang.String[] strArr2;
        java.lang.String str;
        java.lang.String[] strArr3;
        int i;
        int i2;
        try {
            android.os.Trace.traceBegin(8192L, "ResourcesManager#applyNewResourceDirsLocked");
            java.lang.String baseCodePath = applicationInfo.getBaseCodePath();
            int myUid = android.os.Process.myUid();
            if (applicationInfo.uid == myUid) {
                strArr2 = applicationInfo.splitSourceDirs;
            } else {
                strArr2 = applicationInfo.splitPublicSourceDirs;
            }
            java.lang.String[] strArr4 = (java.lang.String[]) com.android.internal.util.ArrayUtils.cloneOrNull(strArr2);
            java.lang.String[] combinedOverlayPaths = combinedOverlayPaths(applicationInfo.resourceDirs, applicationInfo.overlayPaths);
            if (applicationInfo.uid == myUid) {
                addApplicationPathsLocked(baseCodePath, strArr4);
            }
            android.util.ArrayMap<android.content.res.ResourcesImpl, android.content.res.ResourcesKey> arrayMap = new android.util.ArrayMap<>();
            int size = this.mResourceImpls.size();
            int i3 = 0;
            while (i3 < size) {
                android.content.res.ResourcesKey keyAt = this.mResourceImpls.keyAt(i3);
                java.lang.ref.WeakReference<android.content.res.ResourcesImpl> valueAt = this.mResourceImpls.valueAt(i3);
                android.content.res.ResourcesImpl resourcesImpl = valueAt != null ? valueAt.get() : null;
                if (resourcesImpl == null) {
                    str = baseCodePath;
                    i = i3;
                    i2 = size;
                    strArr3 = strArr4;
                } else {
                    if (keyAt.mResDir != null && !keyAt.mResDir.equals(baseCodePath) && !com.android.internal.util.ArrayUtils.contains(strArr, keyAt.mResDir)) {
                        str = baseCodePath;
                        i = i3;
                        i2 = size;
                        strArr3 = strArr4;
                    }
                    str = baseCodePath;
                    strArr3 = strArr4;
                    i = i3;
                    i2 = size;
                    arrayMap.put(resourcesImpl, new android.content.res.ResourcesKey(baseCodePath, strArr4, combinedOverlayPaths, keyAt.mLibDirs, keyAt.mDisplayId, keyAt.mOverrideConfiguration, keyAt.mCompatInfo, keyAt.mLoaders));
                }
                i3 = i + 1;
                size = i2;
                strArr4 = strArr3;
                baseCodePath = str;
            }
            redirectResourcesToNewImplLocked(arrayMap);
        } finally {
            android.os.Trace.traceEnd(8192L);
        }
    }

    private static java.lang.String[] combinedOverlayPaths(java.lang.String[] strArr, java.lang.String[] strArr2) {
        if (strArr == null) {
            return (java.lang.String[]) com.android.internal.util.ArrayUtils.cloneOrNull(strArr2);
        }
        if (strArr2 == null) {
            return (java.lang.String[]) com.android.internal.util.ArrayUtils.cloneOrNull(strArr);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(strArr2.length + strArr.length);
        for (java.lang.String str : strArr2) {
            arrayList.add(str);
        }
        for (java.lang.String str2 : strArr) {
            if (!arrayList.contains(str2)) {
                arrayList.add(str2);
            }
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void redirectResourcesToNewImplLocked(android.util.ArrayMap<android.content.res.ResourcesImpl, android.content.res.ResourcesKey> arrayMap) {
        android.content.res.ResourcesKey resourcesKey;
        android.content.res.ResourcesKey resourcesKey2;
        if (arrayMap.isEmpty()) {
            return;
        }
        int size = this.mResourceReferences.size();
        int i = 0;
        while (true) {
            if (i < size) {
                java.lang.ref.WeakReference<android.content.res.Resources> weakReference = this.mResourceReferences.get(i);
                android.content.res.Resources resources = weakReference != null ? weakReference.get() : null;
                if (resources != null && (resourcesKey2 = arrayMap.get(resources.getImpl())) != null) {
                    android.content.res.ResourcesImpl findOrCreateResourcesImplForKeyLocked = findOrCreateResourcesImplForKeyLocked(resourcesKey2);
                    if (findOrCreateResourcesImplForKeyLocked == null) {
                        throw new android.content.res.Resources.NotFoundException("failed to redirect ResourcesImpl");
                    }
                    resources.setImpl(findOrCreateResourcesImplForKeyLocked);
                }
                i++;
            } else {
                for (android.app.ResourcesManager.ActivityResources activityResources : this.mActivityResourceReferences.values()) {
                    int size2 = activityResources.activityResources.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        android.app.ResourcesManager.ActivityResource activityResource = activityResources.activityResources.get(i2);
                        android.content.res.Resources resources2 = activityResource != null ? activityResource.resources.get() : null;
                        if (resources2 != null && (resourcesKey = arrayMap.get(resources2.getImpl())) != null) {
                            android.content.res.ResourcesImpl findOrCreateResourcesImplForKeyLocked2 = findOrCreateResourcesImplForKeyLocked(resourcesKey);
                            if (findOrCreateResourcesImplForKeyLocked2 == null) {
                                throw new android.content.res.Resources.NotFoundException("failed to redirect ResourcesImpl");
                            }
                            resources2.setImpl(findOrCreateResourcesImplForKeyLocked2);
                        }
                    }
                }
                return;
            }
        }
    }

    public android.app.LocaleConfig getLocaleConfig() {
        return this.mLocaleConfig;
    }

    public void setLocaleConfig(android.app.LocaleConfig localeConfig) {
        if (localeConfig != null && localeConfig.getSupportedLocales() != null && !localeConfig.getSupportedLocales().isEmpty()) {
            this.mLocaleConfig = localeConfig;
        }
    }

    private class UpdateHandler implements android.content.res.Resources.UpdateCallbacks {
        private UpdateHandler() {
        }

        @Override // android.content.res.Resources.UpdateCallbacks
        public void onLoadersChanged(android.content.res.Resources resources, java.util.List<android.content.res.loader.ResourcesLoader> list) {
            synchronized (android.app.ResourcesManager.this.mLock) {
                android.content.res.ResourcesKey findKeyForResourceImplLocked = android.app.ResourcesManager.this.findKeyForResourceImplLocked(resources.getImpl());
                if (findKeyForResourceImplLocked == null) {
                    throw new java.lang.IllegalArgumentException("Cannot modify resource loaders of ResourcesImpl not registered with ResourcesManager");
                }
                resources.setImpl(android.app.ResourcesManager.this.findOrCreateResourcesImplForKeyLocked(new android.content.res.ResourcesKey(findKeyForResourceImplLocked.mResDir, findKeyForResourceImplLocked.mSplitResDirs, findKeyForResourceImplLocked.mOverlayPaths, findKeyForResourceImplLocked.mLibDirs, findKeyForResourceImplLocked.mDisplayId, findKeyForResourceImplLocked.mOverrideConfiguration, findKeyForResourceImplLocked.mCompatInfo, (android.content.res.loader.ResourcesLoader[]) list.toArray(new android.content.res.loader.ResourcesLoader[0]))));
            }
        }

        @Override // android.content.res.loader.ResourcesLoader.UpdateCallbacks
        public void onLoaderUpdated(android.content.res.loader.ResourcesLoader resourcesLoader) {
            synchronized (android.app.ResourcesManager.this.mLock) {
                android.util.ArrayMap arrayMap = new android.util.ArrayMap();
                for (int size = android.app.ResourcesManager.this.mResourceImpls.size() - 1; size >= 0; size--) {
                    android.content.res.ResourcesKey resourcesKey = (android.content.res.ResourcesKey) android.app.ResourcesManager.this.mResourceImpls.keyAt(size);
                    java.lang.ref.WeakReference weakReference = (java.lang.ref.WeakReference) android.app.ResourcesManager.this.mResourceImpls.valueAt(size);
                    if (weakReference != null && !weakReference.refersTo(null) && com.android.internal.util.ArrayUtils.contains(resourcesKey.mLoaders, resourcesLoader)) {
                        android.app.ResourcesManager.this.mResourceImpls.remove(resourcesKey);
                        arrayMap.put((android.content.res.ResourcesImpl) weakReference.get(), resourcesKey);
                    }
                }
                android.app.ResourcesManager.this.redirectResourcesToNewImplLocked(arrayMap);
            }
        }
    }
}
