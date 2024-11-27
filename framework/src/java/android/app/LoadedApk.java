package android.app;

/* loaded from: classes.dex */
public final class LoadedApk {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final boolean DEBUG = false;
    static final java.lang.String TAG = "LoadedApk";
    private static final android.util.ArrayMap<java.lang.String, android.app.Application> sApplications = new android.util.ArrayMap<>(4);
    private final android.app.ActivityThread mActivityThread;
    private android.app.AppComponentFactory mAppComponentFactory;
    private java.lang.String mAppDir;
    private android.app.Application mApplication;
    private android.content.pm.ApplicationInfo mApplicationInfo;
    private final java.lang.ClassLoader mBaseClassLoader;
    private java.lang.ClassLoader mClassLoader;
    private java.io.File mCredentialProtectedDataDirFile;
    private java.lang.String mDataDir;
    private java.io.File mDataDirFile;
    private java.lang.ClassLoader mDefaultClassLoader;
    private java.io.File mDeviceProtectedDataDirFile;
    private final android.view.DisplayAdjustments mDisplayAdjustments;
    private final boolean mIncludeCode;
    private java.lang.String[] mLegacyOverlayDirs;
    private java.lang.String mLibDir;
    private final java.lang.Object mLock;
    private java.lang.String[] mOverlayPaths;
    final java.lang.String mPackageName;
    private final android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.BroadcastReceiver, android.app.LoadedApk.ReceiverDispatcher>> mReceivers;
    private final boolean mRegisterPackage;
    private java.lang.String mResDir;
    android.content.res.Resources mResources;
    private final boolean mSecurityViolation;
    private final android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.ServiceConnection, android.app.LoadedApk.ServiceDispatcher>> mServices;
    private java.lang.String[] mSplitAppDirs;
    private java.lang.String[] mSplitClassLoaderNames;
    private android.app.LoadedApk.SplitDependencyLoaderImpl mSplitLoader;
    private java.lang.String[] mSplitNames;
    private java.lang.String[] mSplitResDirs;
    private final android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.ServiceConnection, android.app.LoadedApk.ServiceDispatcher>> mUnboundServices;
    private final android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.BroadcastReceiver, android.app.LoadedApk.ReceiverDispatcher>> mUnregisteredReceivers;

    android.app.Application getApplication() {
        return this.mApplication;
    }

    public LoadedApk(android.app.ActivityThread activityThread, android.content.pm.ApplicationInfo applicationInfo, android.content.res.CompatibilityInfo compatibilityInfo, java.lang.ClassLoader classLoader, boolean z, boolean z2, boolean z3) {
        this.mDisplayAdjustments = new android.view.DisplayAdjustments();
        this.mReceivers = new android.util.ArrayMap<>();
        this.mUnregisteredReceivers = new android.util.ArrayMap<>();
        this.mServices = new android.util.ArrayMap<>();
        this.mUnboundServices = new android.util.ArrayMap<>();
        this.mLock = new java.lang.Object();
        this.mActivityThread = activityThread;
        setApplicationInfo(applicationInfo);
        this.mPackageName = applicationInfo.packageName;
        this.mBaseClassLoader = classLoader;
        this.mSecurityViolation = z;
        this.mIncludeCode = z2;
        this.mRegisterPackage = z3;
        this.mDisplayAdjustments.setCompatibilityInfo(compatibilityInfo);
        this.mAppComponentFactory = createAppFactory(this.mApplicationInfo, this.mBaseClassLoader);
    }

    private static android.content.pm.ApplicationInfo adjustNativeLibraryPaths(android.content.pm.ApplicationInfo applicationInfo) {
        if (applicationInfo.primaryCpuAbi != null && applicationInfo.secondaryCpuAbi != null) {
            java.lang.String vmInstructionSet = dalvik.system.VMRuntime.getRuntime().vmInstructionSet();
            java.lang.String instructionSet = dalvik.system.VMRuntime.getInstructionSet(applicationInfo.secondaryCpuAbi);
            java.lang.String str = android.os.SystemProperties.get("ro.dalvik.vm.isa." + instructionSet);
            if (!str.isEmpty()) {
                instructionSet = str;
            }
            if (vmInstructionSet.equals(instructionSet)) {
                android.content.pm.ApplicationInfo applicationInfo2 = new android.content.pm.ApplicationInfo(applicationInfo);
                applicationInfo2.nativeLibraryDir = applicationInfo2.secondaryNativeLibraryDir;
                applicationInfo2.primaryCpuAbi = applicationInfo2.secondaryCpuAbi;
                return applicationInfo2;
            }
        }
        return applicationInfo;
    }

    LoadedApk(android.app.ActivityThread activityThread) {
        this.mDisplayAdjustments = new android.view.DisplayAdjustments();
        this.mReceivers = new android.util.ArrayMap<>();
        this.mUnregisteredReceivers = new android.util.ArrayMap<>();
        this.mServices = new android.util.ArrayMap<>();
        this.mUnboundServices = new android.util.ArrayMap<>();
        this.mLock = new java.lang.Object();
        this.mActivityThread = activityThread;
        this.mApplicationInfo = new android.content.pm.ApplicationInfo();
        this.mApplicationInfo.packageName = "android";
        this.mPackageName = "android";
        this.mAppDir = null;
        this.mResDir = null;
        this.mSplitAppDirs = null;
        this.mSplitResDirs = null;
        this.mSplitClassLoaderNames = null;
        this.mLegacyOverlayDirs = null;
        this.mOverlayPaths = null;
        this.mDataDir = null;
        this.mDataDirFile = null;
        this.mDeviceProtectedDataDirFile = null;
        this.mCredentialProtectedDataDirFile = null;
        this.mLibDir = null;
        this.mBaseClassLoader = null;
        this.mSecurityViolation = false;
        this.mIncludeCode = true;
        this.mRegisterPackage = false;
        this.mResources = android.content.res.Resources.getSystem();
        this.mDefaultClassLoader = java.lang.ClassLoader.getSystemClassLoader();
        this.mAppComponentFactory = createAppFactory(this.mApplicationInfo, this.mDefaultClassLoader);
        this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new android.content.pm.ApplicationInfo(this.mApplicationInfo));
    }

    void installSystemApplicationInfo(android.content.pm.ApplicationInfo applicationInfo, java.lang.ClassLoader classLoader) {
        this.mApplicationInfo = applicationInfo;
        this.mDefaultClassLoader = classLoader;
        this.mAppComponentFactory = createAppFactory(applicationInfo, this.mDefaultClassLoader);
        this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new android.content.pm.ApplicationInfo(this.mApplicationInfo));
    }

    private android.app.AppComponentFactory createAppFactory(android.content.pm.ApplicationInfo applicationInfo, java.lang.ClassLoader classLoader) {
        if (this.mIncludeCode && applicationInfo.appComponentFactory != null && classLoader != null) {
            try {
                return (android.app.AppComponentFactory) classLoader.loadClass(applicationInfo.appComponentFactory).newInstance();
            } catch (java.lang.ClassNotFoundException | java.lang.IllegalAccessException | java.lang.InstantiationException e) {
                android.util.Slog.e(TAG, "Unable to instantiate appComponentFactory", e);
            }
        }
        return android.app.AppComponentFactory.DEFAULT;
    }

    public android.app.AppComponentFactory getAppFactory() {
        return this.mAppComponentFactory;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.content.pm.ApplicationInfo getApplicationInfo() {
        return this.mApplicationInfo;
    }

    public int getTargetSdkVersion() {
        return this.mApplicationInfo.targetSdkVersion;
    }

    public boolean isSecurityViolation() {
        return this.mSecurityViolation;
    }

    public android.content.res.CompatibilityInfo getCompatibilityInfo() {
        return this.mDisplayAdjustments.getCompatibilityInfo();
    }

    public void setCompatibilityInfo(android.content.res.CompatibilityInfo compatibilityInfo) {
        this.mDisplayAdjustments.setCompatibilityInfo(compatibilityInfo);
    }

    private static java.lang.String[] getLibrariesFor(java.lang.String str) {
        try {
            android.content.pm.ApplicationInfo applicationInfo = android.app.ActivityThread.getPackageManager().getApplicationInfo(str, 1024L, android.os.UserHandle.myUserId());
            if (applicationInfo == null) {
                return null;
            }
            return applicationInfo.sharedLibraryFiles;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateApplicationInfo(android.content.pm.ApplicationInfo applicationInfo, java.util.List<java.lang.String> list) {
        java.util.List<android.content.res.loader.ResourcesLoader> loaders;
        boolean z;
        if (!setApplicationInfo(applicationInfo)) {
            return;
        }
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList();
        makePaths(this.mActivityThread, applicationInfo, arrayList);
        java.util.List<java.lang.String> arrayList2 = new java.util.ArrayList<>(arrayList.size());
        if (list != null) {
            for (java.lang.String str : arrayList) {
                java.lang.String substring = str.substring(str.lastIndexOf(java.io.File.separator));
                java.util.Iterator<java.lang.String> it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    java.lang.String next = it.next();
                    if (substring.equals(next.substring(next.lastIndexOf(java.io.File.separator)))) {
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    arrayList2.add(str);
                }
            }
        } else {
            arrayList2.addAll(arrayList);
        }
        synchronized (this.mLock) {
            createOrUpdateClassLoaderLocked(arrayList2);
            if (this.mResources != null) {
                try {
                    java.lang.String[] splitPaths = getSplitPaths(null);
                    android.app.ResourcesManager resourcesManager = android.app.ResourcesManager.getInstance();
                    java.lang.String str2 = this.mResDir;
                    java.lang.String[] strArr = this.mLegacyOverlayDirs;
                    java.lang.String[] strArr2 = this.mOverlayPaths;
                    java.lang.String[] strArr3 = this.mApplicationInfo.sharedLibraryFiles;
                    android.content.res.CompatibilityInfo compatibilityInfo = getCompatibilityInfo();
                    java.lang.ClassLoader classLoader = getClassLoader();
                    if (this.mApplication == null) {
                        loaders = null;
                    } else {
                        loaders = this.mApplication.getResources().getLoaders();
                    }
                    this.mResources = resourcesManager.getResources(null, str2, splitPaths, strArr, strArr2, strArr3, null, null, compatibilityInfo, classLoader, loaders);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    throw new java.lang.AssertionError("null split not found");
                }
            }
        }
        this.mAppComponentFactory = createAppFactory(applicationInfo, this.mDefaultClassLoader);
    }

    private boolean setApplicationInfo(android.content.pm.ApplicationInfo applicationInfo) {
        if (this.mApplicationInfo != null && this.mApplicationInfo.createTimestamp > applicationInfo.createTimestamp) {
            android.util.Slog.w(TAG, "New application info for package " + applicationInfo.packageName + " is out of date with TS " + applicationInfo.createTimestamp + " < the current TS " + this.mApplicationInfo.createTimestamp);
            return false;
        }
        int myUid = android.os.Process.myUid();
        android.content.pm.ApplicationInfo adjustNativeLibraryPaths = adjustNativeLibraryPaths(applicationInfo);
        this.mApplicationInfo = adjustNativeLibraryPaths;
        this.mAppDir = adjustNativeLibraryPaths.sourceDir;
        this.mResDir = adjustNativeLibraryPaths.uid == myUid ? adjustNativeLibraryPaths.sourceDir : adjustNativeLibraryPaths.publicSourceDir;
        this.mLegacyOverlayDirs = adjustNativeLibraryPaths.resourceDirs;
        this.mOverlayPaths = adjustNativeLibraryPaths.overlayPaths;
        this.mDataDir = adjustNativeLibraryPaths.dataDir;
        this.mLibDir = adjustNativeLibraryPaths.nativeLibraryDir;
        this.mDataDirFile = android.os.FileUtils.newFileOrNull(adjustNativeLibraryPaths.dataDir);
        this.mDeviceProtectedDataDirFile = android.os.FileUtils.newFileOrNull(adjustNativeLibraryPaths.deviceProtectedDataDir);
        this.mCredentialProtectedDataDirFile = android.os.FileUtils.newFileOrNull(adjustNativeLibraryPaths.credentialProtectedDataDir);
        this.mSplitNames = adjustNativeLibraryPaths.splitNames;
        this.mSplitAppDirs = adjustNativeLibraryPaths.splitSourceDirs;
        this.mSplitResDirs = adjustNativeLibraryPaths.uid == myUid ? adjustNativeLibraryPaths.splitSourceDirs : adjustNativeLibraryPaths.splitPublicSourceDirs;
        this.mSplitClassLoaderNames = adjustNativeLibraryPaths.splitClassLoaderNames;
        if (adjustNativeLibraryPaths.requestsIsolatedSplitLoading() && !com.android.internal.util.ArrayUtils.isEmpty(this.mSplitNames)) {
            this.mSplitLoader = new android.app.LoadedApk.SplitDependencyLoaderImpl(adjustNativeLibraryPaths.splitDependencies);
            return true;
        }
        return true;
    }

    void setSdkSandboxStorage(java.lang.String str, java.lang.String str2) {
        int myUserId = android.os.UserHandle.myUserId();
        this.mDeviceProtectedDataDirFile = android.os.Environment.getDataMiscDeSharedSdkSandboxDirectory(str, myUserId, str2).getAbsoluteFile();
        this.mCredentialProtectedDataDirFile = android.os.Environment.getDataMiscCeSharedSdkSandboxDirectory(str, myUserId, str2).getAbsoluteFile();
        if ((this.mApplicationInfo.privateFlags & 32) != 0) {
            this.mDataDirFile = this.mDeviceProtectedDataDirFile;
        } else {
            this.mDataDirFile = this.mCredentialProtectedDataDirFile;
        }
        this.mDataDir = this.mDataDirFile.getAbsolutePath();
    }

    public static void makePaths(android.app.ActivityThread activityThread, android.content.pm.ApplicationInfo applicationInfo, java.util.List<java.lang.String> list) {
        makePaths(activityThread, false, applicationInfo, list, null);
    }

    private static void appendSharedLibrariesLibPathsIfNeeded(java.util.List<android.content.pm.SharedLibraryInfo> list, android.content.pm.ApplicationInfo applicationInfo, java.util.Set<java.lang.String> set, java.util.List<java.lang.String> list2) {
        if (list == null) {
            return;
        }
        for (android.content.pm.SharedLibraryInfo sharedLibraryInfo : list) {
            if (!sharedLibraryInfo.isNative()) {
                java.util.List<java.lang.String> allCodePaths = sharedLibraryInfo.getAllCodePaths();
                set.addAll(allCodePaths);
                java.util.Iterator<java.lang.String> it = allCodePaths.iterator();
                while (it.hasNext()) {
                    appendApkLibPathIfNeeded(it.next(), applicationInfo, list2);
                }
                appendSharedLibrariesLibPathsIfNeeded(sharedLibraryInfo.getDependencies(), applicationInfo, set, list2);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x013a A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void makePaths(android.app.ActivityThread activityThread, boolean z, android.content.pm.ApplicationInfo applicationInfo, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2) {
        java.lang.String[] strArr;
        java.lang.String str = applicationInfo.sourceDir;
        java.lang.String str2 = applicationInfo.nativeLibraryDir;
        list.clear();
        list.add(str);
        if (applicationInfo.splitSourceDirs != null && !applicationInfo.requestsIsolatedSplitLoading()) {
            java.util.Collections.addAll(list, applicationInfo.splitSourceDirs);
        }
        if (list2 != null) {
            list2.clear();
        }
        if (activityThread != null) {
            java.lang.String str3 = activityThread.mInstrumentationPackageName;
            java.lang.String str4 = activityThread.mInstrumentationAppDir;
            java.lang.String[] strArr2 = activityThread.mInstrumentationSplitAppDirs;
            java.lang.String str5 = activityThread.mInstrumentationLibDir;
            java.lang.String str6 = activityThread.mInstrumentedAppDir;
            java.lang.String[] strArr3 = activityThread.mInstrumentedSplitAppDirs;
            java.lang.String str7 = activityThread.mInstrumentedLibDir;
            if (str.equals(str4) || str.equals(str6)) {
                list.clear();
                list.add(str4);
                if (!str4.equals(str6)) {
                    list.add(str6);
                }
                if (!applicationInfo.requestsIsolatedSplitLoading()) {
                    if (strArr2 != null) {
                        java.util.Collections.addAll(list, strArr2);
                    }
                    if (!str4.equals(str6) && strArr3 != null) {
                        java.util.Collections.addAll(list, strArr3);
                    }
                }
                if (list2 != null) {
                    list2.add(str5);
                    if (!str5.equals(str7)) {
                        list2.add(str7);
                    }
                }
                if (!str6.equals(str4)) {
                    strArr = getLibrariesFor(str3);
                    if (list2 != null) {
                        if (list2.isEmpty()) {
                            list2.add(str2);
                        }
                        if (applicationInfo.primaryCpuAbi != null) {
                            if (applicationInfo.targetSdkVersion < 24) {
                                list2.add("/system/fake-libs" + (dalvik.system.VMRuntime.is64BitAbi(applicationInfo.primaryCpuAbi) ? "64" : ""));
                            }
                            java.util.Iterator<java.lang.String> it = list.iterator();
                            while (it.hasNext()) {
                                list2.add(it.next() + "!/lib/" + applicationInfo.primaryCpuAbi);
                            }
                        }
                        if (z) {
                            list2.add(java.lang.System.getProperty("java.library.path"));
                        }
                    }
                    java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet();
                    appendSharedLibrariesLibPathsIfNeeded(applicationInfo.sharedLibraryInfos, applicationInfo, linkedHashSet, list2);
                    if (applicationInfo.sharedLibraryFiles != null) {
                        int i = 0;
                        for (java.lang.String str8 : applicationInfo.sharedLibraryFiles) {
                            if (str8.endsWith(".apk") && !linkedHashSet.contains(str8) && !list.contains(str8)) {
                                list.add(i, str8);
                                i++;
                                appendApkLibPathIfNeeded(str8, applicationInfo, list2);
                            }
                        }
                    }
                    if (strArr == null) {
                        for (java.lang.String str9 : strArr) {
                            if (!list.contains(str9)) {
                                list.add(0, str9);
                                appendApkLibPathIfNeeded(str9, applicationInfo, list2);
                            }
                        }
                        return;
                    }
                    return;
                }
            }
        }
        strArr = null;
        if (list2 != null) {
        }
        java.util.LinkedHashSet linkedHashSet2 = new java.util.LinkedHashSet();
        appendSharedLibrariesLibPathsIfNeeded(applicationInfo.sharedLibraryInfos, applicationInfo, linkedHashSet2, list2);
        if (applicationInfo.sharedLibraryFiles != null) {
        }
        if (strArr == null) {
        }
    }

    private static void appendApkLibPathIfNeeded(java.lang.String str, android.content.pm.ApplicationInfo applicationInfo, java.util.List<java.lang.String> list) {
        if (list != null && applicationInfo.primaryCpuAbi != null && str.endsWith(".apk") && applicationInfo.targetSdkVersion >= 26) {
            list.add(str + "!/lib/" + applicationInfo.primaryCpuAbi);
        }
    }

    private class SplitDependencyLoaderImpl extends android.content.pm.split.SplitDependencyLoader<android.content.pm.PackageManager.NameNotFoundException> {
        private final java.lang.ClassLoader[] mCachedClassLoaders;
        private final java.lang.String[][] mCachedResourcePaths;

        SplitDependencyLoaderImpl(android.util.SparseArray<int[]> sparseArray) {
            super(sparseArray);
            this.mCachedResourcePaths = new java.lang.String[android.app.LoadedApk.this.mSplitNames.length + 1][];
            this.mCachedClassLoaders = new java.lang.ClassLoader[android.app.LoadedApk.this.mSplitNames.length + 1];
        }

        @Override // android.content.pm.split.SplitDependencyLoader
        protected boolean isSplitCached(int i) {
            boolean z;
            synchronized (android.app.LoadedApk.this.mLock) {
                z = this.mCachedClassLoaders[i] != null;
            }
            return z;
        }

        @Override // android.content.pm.split.SplitDependencyLoader
        protected void constructSplit(int i, int[] iArr, int i2) throws android.content.pm.PackageManager.NameNotFoundException {
            synchronized (android.app.LoadedApk.this.mLock) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                if (i == 0) {
                    android.app.LoadedApk.this.createOrUpdateClassLoaderLocked(null);
                    this.mCachedClassLoaders[0] = android.app.LoadedApk.this.mClassLoader;
                    for (int i3 : iArr) {
                        arrayList.add(android.app.LoadedApk.this.mSplitResDirs[i3 - 1]);
                    }
                    this.mCachedResourcePaths[0] = (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
                    return;
                }
                int i4 = i - 1;
                this.mCachedClassLoaders[i] = android.app.ApplicationLoaders.getDefault().getClassLoader(android.app.LoadedApk.this.mSplitAppDirs[i4], android.app.LoadedApk.this.getTargetSdkVersion(), false, null, null, this.mCachedClassLoaders[i2], android.app.LoadedApk.this.mSplitClassLoaderNames[i4]);
                java.util.Collections.addAll(arrayList, this.mCachedResourcePaths[i2]);
                arrayList.add(android.app.LoadedApk.this.mSplitResDirs[i4]);
                for (int i5 : iArr) {
                    arrayList.add(android.app.LoadedApk.this.mSplitResDirs[i5 - 1]);
                }
                this.mCachedResourcePaths[i] = (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
            }
        }

        private int ensureSplitLoaded(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
            int i;
            if (str == null) {
                i = 0;
            } else {
                int binarySearch = java.util.Arrays.binarySearch(android.app.LoadedApk.this.mSplitNames, str);
                if (binarySearch < 0) {
                    throw new android.content.pm.PackageManager.NameNotFoundException("Split name '" + str + "' is not installed");
                }
                i = binarySearch + 1;
            }
            loadDependenciesForSplit(i);
            return i;
        }

        java.lang.ClassLoader getClassLoaderForSplit(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
            java.lang.ClassLoader classLoader;
            int ensureSplitLoaded = ensureSplitLoaded(str);
            synchronized (android.app.LoadedApk.this.mLock) {
                classLoader = this.mCachedClassLoaders[ensureSplitLoaded];
            }
            return classLoader;
        }

        java.lang.String[] getSplitPathsForSplit(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
            java.lang.String[] strArr;
            int ensureSplitLoaded = ensureSplitLoaded(str);
            synchronized (android.app.LoadedApk.this.mLock) {
                strArr = this.mCachedResourcePaths[ensureSplitLoaded];
            }
            return strArr;
        }
    }

    java.lang.ClassLoader getSplitClassLoader(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        if (this.mSplitLoader == null) {
            return this.mClassLoader;
        }
        return this.mSplitLoader.getClassLoaderForSplit(str);
    }

    java.lang.String[] getSplitPaths(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        if (this.mSplitLoader == null) {
            return this.mSplitResDirs;
        }
        return this.mSplitLoader.getSplitPathsForSplit(str);
    }

    java.lang.ClassLoader createSharedLibraryLoader(android.content.pm.SharedLibraryInfo sharedLibraryInfo, boolean z, java.lang.String str, java.lang.String str2) {
        java.util.List<java.lang.String> allCodePaths = sharedLibraryInfo.getAllCodePaths();
        android.util.Pair<java.util.List<java.lang.ClassLoader>, java.util.List<java.lang.ClassLoader>> createSharedLibrariesLoaders = createSharedLibrariesLoaders(sharedLibraryInfo.getDependencies(), z, str, str2);
        return android.app.ApplicationLoaders.getDefault().getSharedLibraryClassLoaderWithSharedLibraries(allCodePaths.size() == 1 ? allCodePaths.get(0) : android.text.TextUtils.join(java.io.File.pathSeparator, allCodePaths), this.mApplicationInfo.targetSdkVersion, z, str, str2, null, null, createSharedLibrariesLoaders.first, createSharedLibrariesLoaders.second);
    }

    private android.util.Pair<java.util.List<java.lang.ClassLoader>, java.util.List<java.lang.ClassLoader>> createSharedLibrariesLoaders(java.util.List<android.content.pm.SharedLibraryInfo> list, boolean z, java.lang.String str, java.lang.String str2) {
        if (list == null || list.isEmpty()) {
            return new android.util.Pair<>(null, null);
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Collections.addAll(hashSet, android.content.res.Resources.getSystem().getStringArray(com.android.internal.R.array.config_sharedLibrariesLoadedAfterApp));
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (android.content.pm.SharedLibraryInfo sharedLibraryInfo : list) {
            if (!sharedLibraryInfo.isNative() && !sharedLibraryInfo.isSdk()) {
                if (hashSet.contains(sharedLibraryInfo.getName())) {
                    arrayList2.add(createSharedLibraryLoader(sharedLibraryInfo, z, str, str2));
                } else {
                    arrayList.add(createSharedLibraryLoader(sharedLibraryInfo, z, str, str2));
                }
            }
        }
        return new android.util.Pair<>(arrayList, arrayList2);
    }

    private android.os.StrictMode.ThreadPolicy allowThreadDiskReads() {
        if (this.mActivityThread == null) {
            return null;
        }
        return android.os.StrictMode.allowThreadDiskReads();
    }

    private void setThreadPolicy(android.os.StrictMode.ThreadPolicy threadPolicy) {
        if (this.mActivityThread != null && threadPolicy != null) {
            android.os.StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    private android.os.StrictMode.VmPolicy allowVmViolations() {
        if (this.mActivityThread == null) {
            return null;
        }
        return android.os.StrictMode.allowVmViolations();
    }

    private void setVmPolicy(android.os.StrictMode.VmPolicy vmPolicy) {
        if (this.mActivityThread != null && vmPolicy != null) {
            android.os.StrictMode.setVmPolicy(vmPolicy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void createOrUpdateClassLoaderLocked(java.util.List<java.lang.String> list) {
        boolean z;
        java.lang.String str;
        java.lang.String str2;
        if (this.mPackageName.equals("android")) {
            if (this.mClassLoader != null) {
                return;
            }
            if (this.mBaseClassLoader != null) {
                this.mDefaultClassLoader = this.mBaseClassLoader;
            } else {
                this.mDefaultClassLoader = java.lang.ClassLoader.getSystemClassLoader();
            }
            this.mAppComponentFactory = createAppFactory(this.mApplicationInfo, this.mDefaultClassLoader);
            this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new android.content.pm.ApplicationInfo(this.mApplicationInfo));
            return;
        }
        if (this.mActivityThread != null && !java.util.Objects.equals(this.mPackageName, android.app.ActivityThread.currentPackageName()) && this.mIncludeCode) {
            try {
                android.app.ActivityThread.getPackageManager().notifyPackageUse(this.mPackageName, 6);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        if (this.mRegisterPackage) {
            try {
                android.app.ActivityManager.getService().addPackageDependency(this.mPackageName);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(10);
        java.util.ArrayList arrayList2 = new java.util.ArrayList(10);
        boolean z2 = true;
        boolean z3 = false;
        boolean z4 = this.mApplicationInfo.isSystemApp() && !this.mApplicationInfo.isUpdatedSystemApp();
        java.lang.String property = java.lang.System.getProperty("java.library.path");
        boolean z5 = !property.contains("/vendor/lib");
        if (this.mApplicationInfo.getCodePath() != null && this.mApplicationInfo.isVendor() && z5) {
            z4 = false;
        }
        if (this.mApplicationInfo.getCodePath() != null && this.mApplicationInfo.isProduct()) {
            z = false;
        } else {
            z = z4;
        }
        makePaths(this.mActivityThread, z, this.mApplicationInfo, arrayList, arrayList2);
        java.lang.String str3 = canAccessDataDir() ? this.mDataDir : "";
        if (!z) {
            str = str3;
        } else {
            str = (str3 + java.io.File.pathSeparator + java.nio.file.Paths.get(getAppDir(), new java.lang.String[0]).getParent().toString()) + java.io.File.pathSeparator + property;
        }
        java.lang.String join = android.text.TextUtils.join(java.io.File.pathSeparator, arrayList2);
        if (this.mActivityThread != null) {
            java.lang.String stringCoreSetting = this.mActivityThread.getStringCoreSetting(android.provider.Settings.Global.GPU_DEBUG_APP, "");
            if (!stringCoreSetting.isEmpty() && this.mPackageName.equals(stringCoreSetting)) {
                try {
                    java.lang.String debugLayerPathsFromSettings = android.os.GraphicsEnvironment.getInstance().getDebugLayerPathsFromSettings(this.mActivityThread.getCoreSettings(), android.app.ActivityThread.getPackageManager(), this.mPackageName, android.app.ActivityThread.getPackageManager().getApplicationInfo(this.mPackageName, 128L, android.os.UserHandle.myUserId()));
                    if (debugLayerPathsFromSettings != null) {
                        str = str + java.io.File.pathSeparator + debugLayerPathsFromSettings;
                    }
                    str2 = str;
                } catch (android.os.RemoteException e3) {
                    android.util.Slog.e(android.app.ActivityThread.TAG, "RemoteException when fetching debug layer paths for: " + this.mPackageName);
                }
                if (this.mIncludeCode) {
                    if (this.mDefaultClassLoader == null) {
                        android.os.StrictMode.ThreadPolicy allowThreadDiskReads = allowThreadDiskReads();
                        this.mDefaultClassLoader = android.app.ApplicationLoaders.getDefault().getClassLoader("", this.mApplicationInfo.targetSdkVersion, z, join, str2, this.mBaseClassLoader, null);
                        setThreadPolicy(allowThreadDiskReads);
                        this.mAppComponentFactory = android.app.AppComponentFactory.DEFAULT;
                    }
                    if (this.mClassLoader == null) {
                        this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new android.content.pm.ApplicationInfo(this.mApplicationInfo));
                        return;
                    }
                    return;
                }
                java.lang.String join2 = arrayList.size() == 1 ? (java.lang.String) arrayList.get(0) : android.text.TextUtils.join(java.io.File.pathSeparator, arrayList);
                if (this.mDefaultClassLoader == null) {
                    if (this.mActivityThread != null && !android.app.ActivityThread.isSystem()) {
                        dalvik.system.BaseDexClassLoader.setReporter(android.app.DexLoadReporter.getInstance());
                    }
                    android.os.StrictMode.ThreadPolicy allowThreadDiskReads2 = allowThreadDiskReads();
                    android.util.Pair<java.util.List<java.lang.ClassLoader>, java.util.List<java.lang.ClassLoader>> createSharedLibrariesLoaders = createSharedLibrariesLoaders(this.mApplicationInfo.sharedLibraryInfos, z, join, str2);
                    java.util.ArrayList arrayList3 = new java.util.ArrayList();
                    if (this.mApplicationInfo.sharedLibraryInfos != null) {
                        for (android.content.pm.SharedLibraryInfo sharedLibraryInfo : this.mApplicationInfo.sharedLibraryInfos) {
                            if (sharedLibraryInfo.isNative()) {
                                arrayList3.add(sharedLibraryInfo.getName());
                            }
                        }
                    }
                    this.mDefaultClassLoader = android.app.ApplicationLoaders.getDefault().getClassLoaderWithSharedLibraries(join2, this.mApplicationInfo.targetSdkVersion, z, join, str2, this.mBaseClassLoader, this.mApplicationInfo.classLoaderName, createSharedLibrariesLoaders.first, arrayList3, createSharedLibrariesLoaders.second);
                    this.mAppComponentFactory = createAppFactory(this.mApplicationInfo, this.mDefaultClassLoader);
                    setThreadPolicy(allowThreadDiskReads2);
                    z3 = true;
                }
                if (!arrayList2.isEmpty()) {
                    android.os.StrictMode.ThreadPolicy allowThreadDiskReads3 = allowThreadDiskReads();
                    try {
                        android.app.ApplicationLoaders.getDefault().addNative(this.mDefaultClassLoader, arrayList2);
                        setThreadPolicy(allowThreadDiskReads3);
                    } catch (java.lang.Throwable th) {
                        setThreadPolicy(allowThreadDiskReads3);
                        throw th;
                    }
                }
                if (list != null && list.size() > 0) {
                    android.app.ApplicationLoaders.getDefault().addPath(this.mDefaultClassLoader, android.text.TextUtils.join(java.io.File.pathSeparator, list));
                } else {
                    z2 = z3;
                }
                if (z2 && !android.app.ActivityThread.isSystem() && this.mActivityThread != null) {
                    registerAppInfoToArt();
                }
                if (this.mClassLoader == null) {
                    this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new android.content.pm.ApplicationInfo(this.mApplicationInfo));
                    return;
                }
                return;
            }
        }
        str2 = str;
        if (this.mIncludeCode) {
        }
    }

    private boolean canAccessDataDir() {
        if (this.mActivityThread == null) {
            return false;
        }
        if (java.util.Objects.equals(this.mPackageName, android.app.ActivityThread.currentPackageName())) {
            return true;
        }
        android.os.StrictMode.ThreadPolicy allowThreadDiskReads = allowThreadDiskReads();
        android.os.StrictMode.VmPolicy allowVmViolations = allowVmViolations();
        try {
            return new java.io.File(this.mDataDir).canExecute();
        } finally {
            setThreadPolicy(allowThreadDiskReads);
            setVmPolicy(allowVmViolations);
        }
    }

    public java.lang.ClassLoader getClassLoader() {
        java.lang.ClassLoader classLoader;
        synchronized (this.mLock) {
            if (this.mClassLoader == null) {
                createOrUpdateClassLoaderLocked(null);
            }
            classLoader = this.mClassLoader;
        }
        return classLoader;
    }

    private void registerAppInfoToArt() {
        int i;
        if (this.mApplicationInfo.uid != android.os.Process.myUid()) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((this.mApplicationInfo.flags & 4) != 0) {
            arrayList.add(this.mApplicationInfo.sourceDir);
        }
        if (this.mApplicationInfo.splitSourceDirs != null) {
            java.util.Collections.addAll(arrayList, this.mApplicationInfo.splitSourceDirs);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        int size = arrayList.size() - 1;
        while (size >= 0) {
            java.lang.String str = size == 0 ? null : this.mApplicationInfo.splitNames[size - 1];
            java.lang.String currentProfilePath = android.content.pm.dex.ArtManager.getCurrentProfilePath(this.mPackageName, android.os.UserHandle.myUserId(), str);
            java.lang.String referenceProfilePath = android.content.pm.dex.ArtManager.getReferenceProfilePath(this.mPackageName, android.os.UserHandle.myUserId(), str);
            if (((java.lang.String) arrayList.get(size)).equals(this.mApplicationInfo.sourceDir)) {
                i = 1;
            } else {
                i = 2;
            }
            dalvik.system.VMRuntime.registerAppInfo(this.mPackageName, currentProfilePath, referenceProfilePath, new java.lang.String[]{(java.lang.String) arrayList.get(size)}, i);
            size--;
        }
        android.app.DexLoadReporter.getInstance().registerAppDataDir(this.mPackageName, this.mDataDir);
    }

    private void initializeJavaContextClassLoader() {
        java.lang.ClassLoader classLoader;
        android.app.ActivityThread.getPackageManager();
        android.content.pm.PackageInfo packageInfoAsUserCached = android.content.pm.PackageManager.getPackageInfoAsUserCached(this.mPackageName, 268435456L, android.os.UserHandle.myUserId());
        if (packageInfoAsUserCached == null) {
            throw new java.lang.IllegalStateException("Unable to get package info for " + this.mPackageName + "; is package not installed?");
        }
        boolean z = true;
        boolean z2 = packageInfoAsUserCached.sharedUserId != null;
        boolean z3 = (packageInfoAsUserCached.applicationInfo == null || this.mPackageName.equals(packageInfoAsUserCached.applicationInfo.processName)) ? false : true;
        if (!z2 && !z3) {
            z = false;
        }
        if (z) {
            classLoader = new android.app.LoadedApk.WarningContextClassLoader();
        } else {
            classLoader = this.mClassLoader;
        }
        java.lang.Thread.currentThread().setContextClassLoader(classLoader);
    }

    private static class WarningContextClassLoader extends java.lang.ClassLoader {
        private static boolean warned = false;

        private WarningContextClassLoader() {
        }

        private void warn(java.lang.String str) {
            if (warned) {
                return;
            }
            warned = true;
            java.lang.Thread.currentThread().setContextClassLoader(getParent());
            android.util.Slog.w(android.app.ActivityThread.TAG, "ClassLoader." + str + ": The class loader returned by Thread.getContextClassLoader() may fail for processes that host multiple applications. You should explicitly specify a context class loader. For example: Thread.setContextClassLoader(getClass().getClassLoader());");
        }

        @Override // java.lang.ClassLoader
        public java.net.URL getResource(java.lang.String str) {
            warn("getResource");
            return getParent().getResource(str);
        }

        @Override // java.lang.ClassLoader
        public java.util.Enumeration<java.net.URL> getResources(java.lang.String str) throws java.io.IOException {
            warn("getResources");
            return getParent().getResources(str);
        }

        @Override // java.lang.ClassLoader
        public java.io.InputStream getResourceAsStream(java.lang.String str) {
            warn("getResourceAsStream");
            return getParent().getResourceAsStream(str);
        }

        @Override // java.lang.ClassLoader
        public java.lang.Class<?> loadClass(java.lang.String str) throws java.lang.ClassNotFoundException {
            warn("loadClass");
            return getParent().loadClass(str);
        }

        @Override // java.lang.ClassLoader
        public void setClassAssertionStatus(java.lang.String str, boolean z) {
            warn("setClassAssertionStatus");
            getParent().setClassAssertionStatus(str, z);
        }

        @Override // java.lang.ClassLoader
        public void setPackageAssertionStatus(java.lang.String str, boolean z) {
            warn("setPackageAssertionStatus");
            getParent().setPackageAssertionStatus(str, z);
        }

        @Override // java.lang.ClassLoader
        public void setDefaultAssertionStatus(boolean z) {
            warn("setDefaultAssertionStatus");
            getParent().setDefaultAssertionStatus(z);
        }

        @Override // java.lang.ClassLoader
        public void clearAssertionStatus() {
            warn("clearAssertionStatus");
            getParent().clearAssertionStatus();
        }
    }

    public java.lang.String getAppDir() {
        return this.mAppDir;
    }

    public java.lang.String getLibDir() {
        return this.mLibDir;
    }

    public java.lang.String getResDir() {
        return this.mResDir;
    }

    public java.lang.String[] getSplitAppDirs() {
        return this.mSplitAppDirs;
    }

    public java.lang.String[] getSplitResDirs() {
        return this.mSplitResDirs;
    }

    public java.lang.String[] getOverlayDirs() {
        return this.mLegacyOverlayDirs;
    }

    public java.lang.String[] getOverlayPaths() {
        return this.mOverlayPaths;
    }

    public java.lang.String getDataDir() {
        return this.mDataDir;
    }

    public java.io.File getDataDirFile() {
        return this.mDataDirFile;
    }

    public java.io.File getDeviceProtectedDataDirFile() {
        return this.mDeviceProtectedDataDirFile;
    }

    public java.io.File getCredentialProtectedDataDirFile() {
        return this.mCredentialProtectedDataDirFile;
    }

    public android.content.res.AssetManager getAssets() {
        return getResources().getAssets();
    }

    public android.content.res.Resources getResources() {
        if (this.mResources == null) {
            try {
                java.lang.String[] splitPaths = getSplitPaths(null);
                if (android.os.Process.myUid() == this.mApplicationInfo.uid) {
                    android.app.ResourcesManager.getInstance().initializeApplicationPaths(this.mResDir, splitPaths);
                }
                this.mResources = android.app.ResourcesManager.getInstance().getResources(null, this.mResDir, splitPaths, this.mLegacyOverlayDirs, this.mOverlayPaths, this.mApplicationInfo.sharedLibraryFiles, null, null, getCompatibilityInfo(), getClassLoader(), null);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                throw new java.lang.AssertionError("null split not found");
            }
        }
        return this.mResources;
    }

    public android.app.Application makeApplication(boolean z, android.app.Instrumentation instrumentation) {
        return makeApplicationInner(z, instrumentation, true);
    }

    public android.app.Application makeApplicationInner(boolean z, android.app.Instrumentation instrumentation) {
        return makeApplicationInner(z, instrumentation, false);
    }

    private android.app.Application makeApplicationInner(boolean z, android.app.Instrumentation instrumentation, boolean z2) {
        if (this.mApplication != null) {
            return this.mApplication;
        }
        if (android.os.Trace.isTagEnabled(64L)) {
            android.os.Trace.traceBegin(64L, "makeApplication");
        }
        try {
            synchronized (sApplications) {
                android.app.Application application = sApplications.get(this.mPackageName);
                if (application != null) {
                    if (!"android".equals(this.mPackageName)) {
                        android.util.Slog.wtfStack(TAG, "App instance already created for package=" + this.mPackageName + " instance=" + application);
                    }
                    if (!z2) {
                        this.mApplication = application;
                        return application;
                    }
                }
                java.lang.String customApplicationClassNameForProcess = this.mApplicationInfo.getCustomApplicationClassNameForProcess(android.os.Process.myProcessName());
                if (z || customApplicationClassNameForProcess == null) {
                    customApplicationClassNameForProcess = "android.app.Application";
                }
                android.app.Application application2 = null;
                try {
                    java.lang.ClassLoader classLoader = getClassLoader();
                    if (!this.mPackageName.equals("android")) {
                        android.os.Trace.traceBegin(64L, "initializeJavaContextClassLoader");
                        initializeJavaContextClassLoader();
                        android.os.Trace.traceEnd(64L);
                    }
                    android.util.SparseArray<java.lang.String> assignedPackageIdentifiers = getAssets().getAssignedPackageIdentifiers(false, false);
                    int size = assignedPackageIdentifiers.size();
                    for (int i = 0; i < size; i++) {
                        int keyAt = assignedPackageIdentifiers.keyAt(i);
                        if (keyAt != 1 && keyAt != 127 && keyAt != 63) {
                            rewriteRValues(classLoader, assignedPackageIdentifiers.valueAt(i), keyAt);
                        }
                    }
                    android.app.ContextImpl createAppContext = android.app.ContextImpl.createAppContext(this.mActivityThread, this);
                    android.security.net.config.NetworkSecurityConfigProvider.handleNewApplication(createAppContext);
                    application2 = this.mActivityThread.mInstrumentation.newApplication(classLoader, customApplicationClassNameForProcess, createAppContext);
                    createAppContext.setOuterContext(application2);
                } catch (java.lang.Exception e) {
                    if (!this.mActivityThread.mInstrumentation.onException(application2, e)) {
                        throw new java.lang.RuntimeException("Unable to instantiate application " + customApplicationClassNameForProcess + " package " + this.mPackageName + ": " + e.toString(), e);
                    }
                }
                this.mActivityThread.mAllApplications.add(application2);
                this.mApplication = application2;
                if (!z2) {
                    synchronized (sApplications) {
                        sApplications.put(this.mPackageName, application2);
                    }
                }
                if (instrumentation != null) {
                    try {
                        instrumentation.callApplicationOnCreate(application2);
                    } catch (java.lang.Exception e2) {
                        if (!instrumentation.onException(application2, e2)) {
                            throw new java.lang.RuntimeException("Unable to create application " + application2.getClass().getName() + ": " + e2.toString(), e2);
                        }
                    }
                }
                return application2;
            }
        } finally {
            android.os.Trace.traceEnd(64L);
        }
    }

    private void rewriteRValues(java.lang.ClassLoader classLoader, java.lang.String str, int i) {
        java.lang.Throwable e;
        try {
            try {
                try {
                    classLoader.loadClass(str + ".R").getMethod("onResourcesLoaded", java.lang.Integer.TYPE).invoke(null, java.lang.Integer.valueOf(i));
                } catch (java.lang.IllegalAccessException e2) {
                    e = e2;
                    throw new java.lang.RuntimeException("Failed to rewrite resource references for " + str, e);
                } catch (java.lang.reflect.InvocationTargetException e3) {
                    e = e3.getCause();
                    throw new java.lang.RuntimeException("Failed to rewrite resource references for " + str, e);
                }
            } catch (java.lang.NoSuchMethodException e4) {
            }
        } catch (java.lang.ClassNotFoundException e5) {
            android.util.Log.i(TAG, "No resource references to update in package " + str);
        }
    }

    public void removeContextRegistrations(android.content.Context context, java.lang.String str, java.lang.String str2) {
        int i;
        boolean vmRegistrationLeaksEnabled = android.os.StrictMode.vmRegistrationLeaksEnabled();
        synchronized (this.mReceivers) {
            android.util.ArrayMap<android.content.BroadcastReceiver, android.app.LoadedApk.ReceiverDispatcher> remove = this.mReceivers.remove(context);
            if (remove != null) {
                for (int i2 = 0; i2 < remove.size(); i2++) {
                    android.app.LoadedApk.ReceiverDispatcher valueAt = remove.valueAt(i2);
                    android.app.IntentReceiverLeaked intentReceiverLeaked = new android.app.IntentReceiverLeaked(str2 + " " + str + " has leaked IntentReceiver " + valueAt.getIntentReceiver() + " that was originally registered here. Are you missing a call to unregisterReceiver()?");
                    intentReceiverLeaked.setStackTrace(valueAt.getLocation().getStackTrace());
                    android.util.Slog.e(android.app.ActivityThread.TAG, intentReceiverLeaked.getMessage(), intentReceiverLeaked);
                    if (vmRegistrationLeaksEnabled) {
                        android.os.StrictMode.onIntentReceiverLeaked(intentReceiverLeaked);
                    }
                    try {
                        android.app.ActivityManager.getService().unregisterReceiver(valueAt.getIIntentReceiver());
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
            this.mUnregisteredReceivers.remove(context);
        }
        synchronized (this.mServices) {
            android.util.ArrayMap<android.content.ServiceConnection, android.app.LoadedApk.ServiceDispatcher> remove2 = this.mServices.remove(context);
            if (remove2 != null) {
                for (i = 0; i < remove2.size(); i++) {
                    android.app.LoadedApk.ServiceDispatcher valueAt2 = remove2.valueAt(i);
                    android.app.ServiceConnectionLeaked serviceConnectionLeaked = new android.app.ServiceConnectionLeaked(str2 + " " + str + " has leaked ServiceConnection " + valueAt2.getServiceConnection() + " that was originally bound here");
                    serviceConnectionLeaked.setStackTrace(valueAt2.getLocation().getStackTrace());
                    android.util.Slog.e(android.app.ActivityThread.TAG, serviceConnectionLeaked.getMessage(), serviceConnectionLeaked);
                    if (vmRegistrationLeaksEnabled) {
                        android.os.StrictMode.onServiceConnectionLeaked(serviceConnectionLeaked);
                    }
                    try {
                        android.app.ActivityManager.getService().unbindService(valueAt2.getIServiceConnection());
                        valueAt2.doForget();
                    } catch (android.os.RemoteException e2) {
                        throw e2.rethrowFromSystemServer();
                    }
                }
            }
            this.mUnboundServices.remove(context);
        }
    }

    public android.content.IIntentReceiver getReceiverDispatcher(android.content.BroadcastReceiver broadcastReceiver, android.content.Context context, android.os.Handler handler, android.app.Instrumentation instrumentation, boolean z) {
        android.util.ArrayMap<android.content.BroadcastReceiver, android.app.LoadedApk.ReceiverDispatcher> arrayMap;
        android.content.IIntentReceiver iIntentReceiver;
        synchronized (this.mReceivers) {
            android.app.LoadedApk.ReceiverDispatcher receiverDispatcher = null;
            if (!z) {
                arrayMap = null;
            } else {
                try {
                    arrayMap = this.mReceivers.get(context);
                    if (arrayMap != null) {
                        receiverDispatcher = arrayMap.get(broadcastReceiver);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (receiverDispatcher == null) {
                receiverDispatcher = new android.app.LoadedApk.ReceiverDispatcher(this.mActivityThread.getApplicationThread(), broadcastReceiver, context, handler, instrumentation, z);
                if (z) {
                    if (arrayMap == null) {
                        arrayMap = new android.util.ArrayMap<>();
                        this.mReceivers.put(context, arrayMap);
                    }
                    arrayMap.put(broadcastReceiver, receiverDispatcher);
                }
            } else {
                receiverDispatcher.validate(context, handler);
            }
            receiverDispatcher.mForgotten = false;
            iIntentReceiver = receiverDispatcher.getIIntentReceiver();
        }
        return iIntentReceiver;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00b1, code lost:
    
        throw new java.lang.IllegalStateException("Unbinding Receiver " + r6 + " from Context that is no longer in use: " + r5);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.content.IIntentReceiver forgetReceiverDispatcher(android.content.Context context, android.content.BroadcastReceiver broadcastReceiver) {
        android.app.LoadedApk.ReceiverDispatcher receiverDispatcher;
        android.app.LoadedApk.ReceiverDispatcher receiverDispatcher2;
        android.content.IIntentReceiver iIntentReceiver;
        synchronized (this.mReceivers) {
            android.util.ArrayMap<android.content.BroadcastReceiver, android.app.LoadedApk.ReceiverDispatcher> arrayMap = this.mReceivers.get(context);
            if (arrayMap != null && (receiverDispatcher2 = arrayMap.get(broadcastReceiver)) != null) {
                arrayMap.remove(broadcastReceiver);
                if (arrayMap.size() == 0) {
                    this.mReceivers.remove(context);
                }
                if (broadcastReceiver.getDebugUnregister()) {
                    android.util.ArrayMap<android.content.BroadcastReceiver, android.app.LoadedApk.ReceiverDispatcher> arrayMap2 = this.mUnregisteredReceivers.get(context);
                    if (arrayMap2 == null) {
                        arrayMap2 = new android.util.ArrayMap<>();
                        this.mUnregisteredReceivers.put(context, arrayMap2);
                    }
                    java.lang.IllegalArgumentException illegalArgumentException = new java.lang.IllegalArgumentException("Originally unregistered here:");
                    illegalArgumentException.fillInStackTrace();
                    receiverDispatcher2.setUnregisterLocation(illegalArgumentException);
                    arrayMap2.put(broadcastReceiver, receiverDispatcher2);
                }
                receiverDispatcher2.mForgotten = true;
                iIntentReceiver = receiverDispatcher2.getIIntentReceiver();
            } else {
                android.util.ArrayMap<android.content.BroadcastReceiver, android.app.LoadedApk.ReceiverDispatcher> arrayMap3 = this.mUnregisteredReceivers.get(context);
                if (arrayMap3 != null && (receiverDispatcher = arrayMap3.get(broadcastReceiver)) != null) {
                    throw new java.lang.IllegalArgumentException("Unregistering Receiver " + broadcastReceiver + " that was already unregistered", receiverDispatcher.getUnregisterLocation());
                }
                throw new java.lang.IllegalArgumentException("Receiver not registered: " + broadcastReceiver);
            }
        }
        return iIntentReceiver;
    }

    static final class ReceiverDispatcher {
        final android.os.Handler mActivityThread;
        final android.app.IApplicationThread mAppThread;
        final android.content.Context mContext;
        boolean mForgotten;
        final android.content.IIntentReceiver.Stub mIIntentReceiver;
        final android.app.Instrumentation mInstrumentation;
        final android.app.IntentReceiverLeaked mLocation;
        final android.content.BroadcastReceiver mReceiver;
        final boolean mRegistered;
        java.lang.RuntimeException mUnregisterLocation;

        static final class InnerReceiver extends android.content.IIntentReceiver.Stub {
            final android.app.IApplicationThread mApplicationThread;
            final java.lang.ref.WeakReference<android.app.LoadedApk.ReceiverDispatcher> mDispatcher;
            final android.app.LoadedApk.ReceiverDispatcher mStrongRef;

            InnerReceiver(android.app.IApplicationThread iApplicationThread, android.app.LoadedApk.ReceiverDispatcher receiverDispatcher, boolean z) {
                this.mApplicationThread = iApplicationThread;
                this.mDispatcher = new java.lang.ref.WeakReference<>(receiverDispatcher);
                this.mStrongRef = z ? receiverDispatcher : null;
            }

            @Override // android.content.IIntentReceiver
            public void performReceive(android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2) {
                android.util.Log.wtf(android.app.LoadedApk.TAG, "performReceive() called targeting raw IIntentReceiver for " + intent);
                performReceive(intent, i, str, bundle, z, z2, android.content.BroadcastReceiver.PendingResult.guessAssumeDelivered(1, z), i2, -1, null);
            }

            public void performReceive(android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, java.lang.String str2) {
                android.app.LoadedApk.ReceiverDispatcher receiverDispatcher;
                if (intent == null) {
                    android.util.Log.wtf(android.app.LoadedApk.TAG, "Null intent received");
                    receiverDispatcher = null;
                } else {
                    receiverDispatcher = this.mDispatcher.get();
                }
                if (receiverDispatcher != null) {
                    receiverDispatcher.performReceive(intent, i, str, bundle, z, z2, z3, i2, i3, str2);
                    return;
                }
                if (!z3) {
                    android.app.IActivityManager service = android.app.ActivityManager.getService();
                    if (bundle != null) {
                        try {
                            bundle.setAllowFds(false);
                        } catch (android.os.RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                    service.finishReceiver(this.mApplicationThread.asBinder(), i, str, bundle, false, intent.getFlags());
                }
            }
        }

        final class Args extends android.content.BroadcastReceiver.PendingResult {
            private android.content.Intent mCurIntent;
            private boolean mDispatched;
            private boolean mRunCalled;

            public Args(android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, java.lang.String str2) {
                super(i, str, bundle, android.app.LoadedApk.ReceiverDispatcher.this.mRegistered ? 1 : 2, z, z2, z3, android.app.LoadedApk.ReceiverDispatcher.this.mAppThread.asBinder(), i2, intent.getFlags(), i3, str2);
                this.mCurIntent = intent;
            }

            public final java.lang.Runnable getRunnable() {
                return new java.lang.Runnable() { // from class: android.app.LoadedApk$ReceiverDispatcher$Args$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.LoadedApk.ReceiverDispatcher.Args.this.lambda$getRunnable$0();
                    }
                };
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$getRunnable$0() {
                android.content.BroadcastReceiver broadcastReceiver = android.app.LoadedApk.ReceiverDispatcher.this.mReceiver;
                android.app.IActivityManager service = android.app.ActivityManager.getService();
                android.content.Intent intent = this.mCurIntent;
                if (intent == null) {
                    android.util.Log.wtf(android.app.LoadedApk.TAG, "Null intent being dispatched, mDispatched=" + this.mDispatched + (this.mRunCalled ? ", run() has already been called" : ""));
                }
                this.mCurIntent = null;
                this.mDispatched = true;
                this.mRunCalled = true;
                if (broadcastReceiver == null || intent == null || android.app.LoadedApk.ReceiverDispatcher.this.mForgotten) {
                    sendFinished(service);
                    return;
                }
                if (android.os.Trace.isTagEnabled(64L)) {
                    android.os.Trace.traceBegin(64L, "broadcastReceiveReg: " + intent.getAction());
                }
                try {
                    java.lang.ClassLoader classLoader = android.app.LoadedApk.ReceiverDispatcher.this.mReceiver.getClass().getClassLoader();
                    intent.setExtrasClassLoader(classLoader);
                    intent.prepareToEnterProcess(android.app.ActivityThread.isProtectedBroadcast(intent), android.app.LoadedApk.ReceiverDispatcher.this.mContext.getAttributionSource());
                    setExtrasClassLoader(classLoader);
                    broadcastReceiver.setPendingResult(this);
                    broadcastReceiver.onReceive(android.app.LoadedApk.ReceiverDispatcher.this.mContext, intent);
                } catch (java.lang.Exception e) {
                    sendFinished(service);
                    if (android.app.LoadedApk.ReceiverDispatcher.this.mInstrumentation == null || !android.app.LoadedApk.ReceiverDispatcher.this.mInstrumentation.onException(android.app.LoadedApk.ReceiverDispatcher.this.mReceiver, e)) {
                        android.os.Trace.traceEnd(64L);
                        throw new java.lang.RuntimeException("Error receiving broadcast " + intent + " in " + android.app.LoadedApk.ReceiverDispatcher.this.mReceiver, e);
                    }
                }
                if (broadcastReceiver.getPendingResult() != null) {
                    finish();
                }
                android.os.Trace.traceEnd(64L);
            }
        }

        ReceiverDispatcher(android.app.IApplicationThread iApplicationThread, android.content.BroadcastReceiver broadcastReceiver, android.content.Context context, android.os.Handler handler, android.app.Instrumentation instrumentation, boolean z) {
            if (handler == null) {
                throw new java.lang.NullPointerException("Handler must not be null");
            }
            this.mAppThread = iApplicationThread;
            this.mIIntentReceiver = new android.app.LoadedApk.ReceiverDispatcher.InnerReceiver(this.mAppThread, this, !z);
            this.mReceiver = broadcastReceiver;
            this.mContext = context;
            this.mActivityThread = handler;
            this.mInstrumentation = instrumentation;
            this.mRegistered = z;
            this.mLocation = new android.app.IntentReceiverLeaked(null);
            this.mLocation.fillInStackTrace();
        }

        void validate(android.content.Context context, android.os.Handler handler) {
            if (this.mContext != context) {
                throw new java.lang.IllegalStateException("Receiver " + this.mReceiver + " registered with differing Context (was " + this.mContext + " now " + context + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            if (this.mActivityThread != handler) {
                throw new java.lang.IllegalStateException("Receiver " + this.mReceiver + " registered with differing handler (was " + this.mActivityThread + " now " + handler + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        }

        android.app.IntentReceiverLeaked getLocation() {
            return this.mLocation;
        }

        android.content.BroadcastReceiver getIntentReceiver() {
            return this.mReceiver;
        }

        android.content.IIntentReceiver getIIntentReceiver() {
            return this.mIIntentReceiver;
        }

        void setUnregisterLocation(java.lang.RuntimeException runtimeException) {
            this.mUnregisterLocation = runtimeException;
        }

        java.lang.RuntimeException getUnregisterLocation() {
            return this.mUnregisterLocation;
        }

        public void performReceive(android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, java.lang.String str2) {
            android.app.LoadedApk.ReceiverDispatcher.Args args = new android.app.LoadedApk.ReceiverDispatcher.Args(intent, i, str, bundle, z, z2, z3, i2, i3, str2);
            if (intent == null) {
                android.util.Log.wtf(android.app.LoadedApk.TAG, "Null intent received");
            }
            if (intent == null || !this.mActivityThread.post(args.getRunnable())) {
                args.sendFinished(android.app.ActivityManager.getService());
            }
        }
    }

    public final android.app.IServiceConnection getServiceDispatcher(android.content.ServiceConnection serviceConnection, android.content.Context context, android.os.Handler handler, long j) {
        return getServiceDispatcherCommon(serviceConnection, context, handler, null, j);
    }

    public final android.app.IServiceConnection getServiceDispatcher(android.content.ServiceConnection serviceConnection, android.content.Context context, java.util.concurrent.Executor executor, long j) {
        return getServiceDispatcherCommon(serviceConnection, context, null, executor, j);
    }

    private android.app.IServiceConnection getServiceDispatcherCommon(android.content.ServiceConnection serviceConnection, android.content.Context context, android.os.Handler handler, java.util.concurrent.Executor executor, long j) {
        android.app.LoadedApk.ServiceDispatcher serviceDispatcher;
        android.app.IServiceConnection iServiceConnection;
        synchronized (this.mServices) {
            android.util.ArrayMap<android.content.ServiceConnection, android.app.LoadedApk.ServiceDispatcher> arrayMap = this.mServices.get(context);
            if (arrayMap == null) {
                serviceDispatcher = null;
            } else {
                serviceDispatcher = arrayMap.get(serviceConnection);
            }
            if (serviceDispatcher == null) {
                if (executor != null) {
                    serviceDispatcher = new android.app.LoadedApk.ServiceDispatcher(serviceConnection, context, executor, j);
                } else {
                    serviceDispatcher = new android.app.LoadedApk.ServiceDispatcher(serviceConnection, context, handler, j);
                }
                if (arrayMap == null) {
                    arrayMap = new android.util.ArrayMap<>();
                    this.mServices.put(context, arrayMap);
                }
                arrayMap.put(serviceConnection, serviceDispatcher);
            } else {
                serviceDispatcher.validate(context, handler, executor);
            }
            iServiceConnection = serviceDispatcher.getIServiceConnection();
        }
        return iServiceConnection;
    }

    public android.app.IServiceConnection lookupServiceDispatcher(android.content.ServiceConnection serviceConnection, android.content.Context context) {
        android.app.LoadedApk.ServiceDispatcher serviceDispatcher;
        android.app.IServiceConnection iServiceConnection;
        synchronized (this.mServices) {
            android.util.ArrayMap<android.content.ServiceConnection, android.app.LoadedApk.ServiceDispatcher> arrayMap = this.mServices.get(context);
            if (arrayMap == null) {
                serviceDispatcher = null;
            } else {
                serviceDispatcher = arrayMap.get(serviceConnection);
            }
            iServiceConnection = serviceDispatcher != null ? serviceDispatcher.getIServiceConnection() : null;
        }
        return iServiceConnection;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00b8, code lost:
    
        throw new java.lang.IllegalStateException("Unbinding Service " + r9 + " from Context that is no longer in use: " + r8);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final android.app.IServiceConnection forgetServiceDispatcher(android.content.Context context, android.content.ServiceConnection serviceConnection) {
        android.app.LoadedApk.ServiceDispatcher serviceDispatcher;
        android.app.LoadedApk.ServiceDispatcher serviceDispatcher2;
        android.app.IServiceConnection iServiceConnection;
        synchronized (this.mServices) {
            android.util.ArrayMap<android.content.ServiceConnection, android.app.LoadedApk.ServiceDispatcher> arrayMap = this.mServices.get(context);
            if (arrayMap != null && (serviceDispatcher2 = arrayMap.get(serviceConnection)) != null) {
                arrayMap.remove(serviceConnection);
                serviceDispatcher2.doForget();
                if (arrayMap.size() == 0) {
                    this.mServices.remove(context);
                }
                if ((serviceDispatcher2.getFlags() & 2) != 0) {
                    android.util.ArrayMap<android.content.ServiceConnection, android.app.LoadedApk.ServiceDispatcher> arrayMap2 = this.mUnboundServices.get(context);
                    if (arrayMap2 == null) {
                        arrayMap2 = new android.util.ArrayMap<>();
                        this.mUnboundServices.put(context, arrayMap2);
                    }
                    java.lang.IllegalArgumentException illegalArgumentException = new java.lang.IllegalArgumentException("Originally unbound here:");
                    illegalArgumentException.fillInStackTrace();
                    serviceDispatcher2.setUnbindLocation(illegalArgumentException);
                    arrayMap2.put(serviceConnection, serviceDispatcher2);
                }
                iServiceConnection = serviceDispatcher2.getIServiceConnection();
            } else {
                android.util.ArrayMap<android.content.ServiceConnection, android.app.LoadedApk.ServiceDispatcher> arrayMap3 = this.mUnboundServices.get(context);
                if (arrayMap3 != null && (serviceDispatcher = arrayMap3.get(serviceConnection)) != null) {
                    throw new java.lang.IllegalArgumentException("Unbinding Service " + serviceConnection + " that was already unbound", serviceDispatcher.getUnbindLocation());
                }
                throw new java.lang.IllegalArgumentException("Service not registered: " + serviceConnection);
            }
        }
        return iServiceConnection;
    }

    static final class ServiceDispatcher {
        private final android.util.ArrayMap<android.content.ComponentName, android.app.LoadedApk.ServiceDispatcher.ConnectionInfo> mActiveConnections;
        private final java.util.concurrent.Executor mActivityExecutor;
        private final android.os.Handler mActivityThread;
        private final android.content.ServiceConnection mConnection;
        private final android.content.Context mContext;
        private final long mFlags;
        private boolean mForgotten;
        private final android.app.LoadedApk.ServiceDispatcher.InnerConnection mIServiceConnection;
        private final android.app.ServiceConnectionLeaked mLocation;
        private java.lang.RuntimeException mUnbindLocation;

        private static class ConnectionInfo {
            android.os.IBinder binder;
            android.os.IBinder.DeathRecipient deathMonitor;

            private ConnectionInfo() {
            }
        }

        private static class InnerConnection extends android.app.IServiceConnection.Stub {
            final java.lang.ref.WeakReference<android.app.LoadedApk.ServiceDispatcher> mDispatcher;

            InnerConnection(android.app.LoadedApk.ServiceDispatcher serviceDispatcher) {
                this.mDispatcher = new java.lang.ref.WeakReference<>(serviceDispatcher);
            }

            @Override // android.app.IServiceConnection
            public void connected(android.content.ComponentName componentName, android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.app.LoadedApk.ServiceDispatcher serviceDispatcher = this.mDispatcher.get();
                if (serviceDispatcher != null) {
                    serviceDispatcher.connected(componentName, iBinder, z);
                }
            }
        }

        ServiceDispatcher(android.content.ServiceConnection serviceConnection, android.content.Context context, android.os.Handler handler, long j) {
            this.mActiveConnections = new android.util.ArrayMap<>();
            this.mIServiceConnection = new android.app.LoadedApk.ServiceDispatcher.InnerConnection(this);
            this.mConnection = serviceConnection;
            this.mContext = context;
            this.mActivityThread = handler;
            this.mActivityExecutor = null;
            this.mLocation = new android.app.ServiceConnectionLeaked(null);
            this.mLocation.fillInStackTrace();
            this.mFlags = j;
        }

        ServiceDispatcher(android.content.ServiceConnection serviceConnection, android.content.Context context, java.util.concurrent.Executor executor, long j) {
            this.mActiveConnections = new android.util.ArrayMap<>();
            this.mIServiceConnection = new android.app.LoadedApk.ServiceDispatcher.InnerConnection(this);
            this.mConnection = serviceConnection;
            this.mContext = context;
            this.mActivityThread = null;
            this.mActivityExecutor = executor;
            this.mLocation = new android.app.ServiceConnectionLeaked(null);
            this.mLocation.fillInStackTrace();
            this.mFlags = j;
        }

        void validate(android.content.Context context, android.os.Handler handler, java.util.concurrent.Executor executor) {
            if (this.mContext != context) {
                throw new java.lang.RuntimeException("ServiceConnection " + this.mConnection + " registered with differing Context (was " + this.mContext + " now " + context + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            if (this.mActivityThread != handler) {
                throw new java.lang.RuntimeException("ServiceConnection " + this.mConnection + " registered with differing handler (was " + this.mActivityThread + " now " + handler + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            if (this.mActivityExecutor != executor) {
                throw new java.lang.RuntimeException("ServiceConnection " + this.mConnection + " registered with differing executor (was " + this.mActivityExecutor + " now " + executor + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        }

        void doForget() {
            synchronized (this) {
                for (int i = 0; i < this.mActiveConnections.size(); i++) {
                    android.app.LoadedApk.ServiceDispatcher.ConnectionInfo valueAt = this.mActiveConnections.valueAt(i);
                    valueAt.binder.unlinkToDeath(valueAt.deathMonitor, 0);
                }
                this.mActiveConnections.clear();
                this.mForgotten = true;
            }
        }

        android.app.ServiceConnectionLeaked getLocation() {
            return this.mLocation;
        }

        android.content.ServiceConnection getServiceConnection() {
            return this.mConnection;
        }

        android.app.IServiceConnection getIServiceConnection() {
            return this.mIServiceConnection;
        }

        long getFlags() {
            return this.mFlags;
        }

        void setUnbindLocation(java.lang.RuntimeException runtimeException) {
            this.mUnbindLocation = runtimeException;
        }

        java.lang.RuntimeException getUnbindLocation() {
            return this.mUnbindLocation;
        }

        public void connected(android.content.ComponentName componentName, android.os.IBinder iBinder, boolean z) {
            if (this.mActivityExecutor != null) {
                this.mActivityExecutor.execute(new android.app.LoadedApk.ServiceDispatcher.RunConnection(componentName, iBinder, 0, z));
            } else if (this.mActivityThread != null) {
                this.mActivityThread.post(new android.app.LoadedApk.ServiceDispatcher.RunConnection(componentName, iBinder, 0, z));
            } else {
                doConnected(componentName, iBinder, z);
            }
        }

        public void death(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            if (this.mActivityExecutor != null) {
                this.mActivityExecutor.execute(new android.app.LoadedApk.ServiceDispatcher.RunConnection(componentName, iBinder, 1, false));
            } else if (this.mActivityThread != null) {
                this.mActivityThread.post(new android.app.LoadedApk.ServiceDispatcher.RunConnection(componentName, iBinder, 1, false));
            } else {
                doDeath(componentName, iBinder);
            }
        }

        public void doConnected(android.content.ComponentName componentName, android.os.IBinder iBinder, boolean z) {
            synchronized (this) {
                if (this.mForgotten) {
                    return;
                }
                android.app.LoadedApk.ServiceDispatcher.ConnectionInfo connectionInfo = this.mActiveConnections.get(componentName);
                if (connectionInfo == null || connectionInfo.binder != iBinder) {
                    if (iBinder != null) {
                        android.app.LoadedApk.ServiceDispatcher.ConnectionInfo connectionInfo2 = new android.app.LoadedApk.ServiceDispatcher.ConnectionInfo();
                        connectionInfo2.binder = iBinder;
                        connectionInfo2.deathMonitor = new android.app.LoadedApk.ServiceDispatcher.DeathMonitor(componentName, iBinder);
                        try {
                            iBinder.linkToDeath(connectionInfo2.deathMonitor, 0);
                            this.mActiveConnections.put(componentName, connectionInfo2);
                        } catch (android.os.RemoteException e) {
                            this.mActiveConnections.remove(componentName);
                            return;
                        }
                    } else {
                        this.mActiveConnections.remove(componentName);
                    }
                    if (connectionInfo != null) {
                        connectionInfo.binder.unlinkToDeath(connectionInfo.deathMonitor, 0);
                    }
                    if (connectionInfo != null) {
                        this.mConnection.onServiceDisconnected(componentName);
                    }
                    if (z) {
                        this.mConnection.onBindingDied(componentName);
                    } else if (iBinder != null) {
                        this.mConnection.onServiceConnected(componentName, iBinder);
                    } else {
                        this.mConnection.onNullBinding(componentName);
                    }
                }
            }
        }

        public void doDeath(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            synchronized (this) {
                android.app.LoadedApk.ServiceDispatcher.ConnectionInfo connectionInfo = this.mActiveConnections.get(componentName);
                if (connectionInfo != null && connectionInfo.binder == iBinder) {
                    this.mActiveConnections.remove(componentName);
                    connectionInfo.binder.unlinkToDeath(connectionInfo.deathMonitor, 0);
                    this.mConnection.onServiceDisconnected(componentName);
                }
            }
        }

        private final class RunConnection implements java.lang.Runnable {
            final int mCommand;
            final boolean mDead;
            final android.content.ComponentName mName;
            final android.os.IBinder mService;

            RunConnection(android.content.ComponentName componentName, android.os.IBinder iBinder, int i, boolean z) {
                this.mName = componentName;
                this.mService = iBinder;
                this.mCommand = i;
                this.mDead = z;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (this.mCommand == 0) {
                    android.app.LoadedApk.ServiceDispatcher.this.doConnected(this.mName, this.mService, this.mDead);
                } else if (this.mCommand == 1) {
                    android.app.LoadedApk.ServiceDispatcher.this.doDeath(this.mName, this.mService);
                }
            }
        }

        private final class DeathMonitor implements android.os.IBinder.DeathRecipient {
            final android.content.ComponentName mName;
            final android.os.IBinder mService;

            DeathMonitor(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                this.mName = componentName;
                this.mService = iBinder;
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                android.app.LoadedApk.ServiceDispatcher.this.death(this.mName, this.mService);
            }
        }
    }

    public static void checkAndUpdateApkPaths(android.content.pm.ApplicationInfo applicationInfo) {
        android.app.ActivityThread currentActivityThread = android.app.ActivityThread.currentActivityThread();
        if (currentActivityThread == null) {
            android.util.Log.e(TAG, "Cannot find activity thread");
        } else {
            checkAndUpdateApkPaths(currentActivityThread, applicationInfo, true);
            checkAndUpdateApkPaths(currentActivityThread, applicationInfo, false);
        }
    }

    private static void checkAndUpdateApkPaths(android.app.ActivityThread activityThread, android.content.pm.ApplicationInfo applicationInfo, boolean z) {
        java.lang.String codePath = applicationInfo.getCodePath();
        android.app.LoadedApk peekPackageInfo = activityThread.peekPackageInfo(applicationInfo.packageName, z);
        if (peekPackageInfo == null || peekPackageInfo.getApplicationInfo() == null || peekPackageInfo.getApplicationInfo().getCodePath().equals(codePath)) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        makePaths(activityThread, applicationInfo, arrayList);
        peekPackageInfo.updateApplicationInfo(applicationInfo, arrayList);
    }
}
