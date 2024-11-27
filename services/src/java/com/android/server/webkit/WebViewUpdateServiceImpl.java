package com.android.server.webkit;

/* loaded from: classes.dex */
class WebViewUpdateServiceImpl implements com.android.server.webkit.WebViewUpdateServiceInterface {
    private static final int MULTIPROCESS_SETTING_OFF_VALUE = Integer.MIN_VALUE;
    private static final int MULTIPROCESS_SETTING_ON_VALUE = Integer.MAX_VALUE;
    private static final long NS_PER_MS = 1000000;
    private static final int NUMBER_OF_RELROS_UNKNOWN = Integer.MAX_VALUE;
    private static final java.lang.String TAG = com.android.server.webkit.WebViewUpdateServiceImpl.class.getSimpleName();
    private static final int VALIDITY_INCORRECT_SDK_VERSION = 1;
    private static final int VALIDITY_INCORRECT_SIGNATURE = 3;
    private static final int VALIDITY_INCORRECT_VERSION_CODE = 2;
    private static final int VALIDITY_NO_LIBRARY_FLAG = 4;
    private static final int VALIDITY_OK = 0;
    private static final int WAIT_TIMEOUT_MS = 1000;
    private final android.content.Context mContext;
    private final com.android.server.webkit.SystemInterface mSystemInterface;
    private long mMinimumVersionCode = -1;
    private int mNumRelroCreationsStarted = 0;
    private int mNumRelroCreationsFinished = 0;
    private boolean mWebViewPackageDirty = false;
    private boolean mAnyWebViewInstalled = false;
    private android.content.pm.PackageInfo mCurrentWebViewPackage = null;
    private final java.lang.Object mLock = new java.lang.Object();

    private static class WebViewPackageMissingException extends java.lang.Exception {
        WebViewPackageMissingException(java.lang.String str) {
            super(str);
        }

        WebViewPackageMissingException(java.lang.Exception exc) {
            super(exc);
        }
    }

    WebViewUpdateServiceImpl(android.content.Context context, com.android.server.webkit.SystemInterface systemInterface) {
        this.mContext = context;
        this.mSystemInterface = systemInterface;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:9|10|11|(1:13)(1:46)|(2:14|15)|(2:19|(5:21|22|23|(1:25)|27))|42|22|23|(0)|27) */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x005a, code lost:
    
        r8 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x005b, code lost:
    
        r5 = r1;
        r1 = r0;
        r0 = r5;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0056 A[Catch: all -> 0x002a, WebViewPackageMissingException -> 0x005a, TRY_LEAVE, TryCatch #1 {WebViewPackageMissingException -> 0x005a, blocks: (B:23:0x004e, B:25:0x0056), top: B:22:0x004e }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:36:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void packageStateChanged(java.lang.String str, int i, int i2) {
        java.lang.String str2;
        boolean z;
        android.content.pm.PackageInfo findPreferredWebViewPackage;
        boolean z2 = false;
        for (android.webkit.WebViewProviderInfo webViewProviderInfo : this.mSystemInterface.getWebViewPackages()) {
            if (webViewProviderInfo.packageName.equals(str)) {
                synchronized (this.mLock) {
                    try {
                        findPreferredWebViewPackage = findPreferredWebViewPackage();
                        str2 = this.mCurrentWebViewPackage != null ? this.mCurrentWebViewPackage.packageName : null;
                    } catch (com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException e) {
                        e = e;
                        str2 = null;
                    }
                    try {
                    } catch (com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException e2) {
                        e = e2;
                        boolean z3 = false;
                        this.mCurrentWebViewPackage = null;
                        android.util.Slog.e(TAG, "Could not find valid WebView package to create relro with " + e);
                        boolean z4 = z3;
                        z = z2;
                        z2 = z4;
                        if (z) {
                            return;
                        } else {
                            return;
                        }
                    }
                    if (!webViewProviderInfo.packageName.equals(findPreferredWebViewPackage.packageName) && !webViewProviderInfo.packageName.equals(str2)) {
                        if (this.mCurrentWebViewPackage != null) {
                            z = false;
                            z2 = webViewProviderInfo.packageName.equals(str2);
                            if (z) {
                                onWebViewProviderChanged(findPreferredWebViewPackage);
                            }
                        }
                    }
                    z = true;
                    z2 = webViewProviderInfo.packageName.equals(str2);
                    if (z) {
                    }
                }
                if (z || z2 || str2 == null) {
                    return;
                }
                this.mSystemInterface.killPackageDependents(str2);
                return;
            }
        }
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public void prepareWebViewInSystemServer() {
        this.mSystemInterface.notifyZygote(isMultiProcessEnabled());
        try {
            synchronized (this.mLock) {
                try {
                    this.mCurrentWebViewPackage = findPreferredWebViewPackage();
                    java.lang.String userChosenWebViewProvider = this.mSystemInterface.getUserChosenWebViewProvider(this.mContext);
                    if (userChosenWebViewProvider != null && !userChosenWebViewProvider.equals(this.mCurrentWebViewPackage.packageName)) {
                        this.mSystemInterface.updateUserSetting(this.mContext, this.mCurrentWebViewPackage.packageName);
                    }
                    onWebViewProviderChanged(this.mCurrentWebViewPackage);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } catch (java.lang.Throwable th2) {
            android.util.Slog.e(TAG, "error preparing webview provider from system server", th2);
        }
        if (getCurrentWebViewPackage() == null) {
            android.webkit.WebViewProviderInfo fallbackProvider = getFallbackProvider(this.mSystemInterface.getWebViewPackages());
            if (fallbackProvider != null) {
                android.util.Slog.w(TAG, "No valid provider, trying to enable " + fallbackProvider.packageName);
                this.mSystemInterface.enablePackageForAllUsers(this.mContext, fallbackProvider.packageName, true);
                return;
            }
            android.util.Slog.e(TAG, "No valid provider and no fallback available.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startZygoteWhenReady() {
        waitForAndGetProvider();
        this.mSystemInterface.ensureZygoteStarted();
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public void handleNewUser(int i) {
        if (i == 0) {
            return;
        }
        handleUserChange();
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public void handleUserRemoved(int i) {
        handleUserChange();
    }

    private void handleUserChange() {
        updateCurrentWebViewPackage(null);
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public void notifyRelroCreationCompleted() {
        synchronized (this.mLock) {
            this.mNumRelroCreationsFinished++;
            checkIfRelrosDoneLocked();
        }
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public android.webkit.WebViewProviderResponse waitForAndGetProvider() {
        boolean webViewIsReadyLocked;
        android.content.pm.PackageInfo packageInfo;
        int i;
        long nanoTime = (java.lang.System.nanoTime() / NS_PER_MS) + 1000;
        synchronized (this.mLock) {
            webViewIsReadyLocked = webViewIsReadyLocked();
            while (!webViewIsReadyLocked) {
                long nanoTime2 = java.lang.System.nanoTime() / NS_PER_MS;
                if (nanoTime2 >= nanoTime) {
                    break;
                }
                try {
                    this.mLock.wait(nanoTime - nanoTime2);
                } catch (java.lang.InterruptedException e) {
                }
                webViewIsReadyLocked = webViewIsReadyLocked();
            }
            packageInfo = this.mCurrentWebViewPackage;
            if (webViewIsReadyLocked) {
                i = 0;
            } else if (!this.mAnyWebViewInstalled) {
                i = 4;
            } else {
                java.lang.String str = "Timed out waiting for relro creation, relros started " + this.mNumRelroCreationsStarted + " relros finished " + this.mNumRelroCreationsFinished + " package dirty? " + this.mWebViewPackageDirty;
                android.util.Slog.e(TAG, str);
                android.os.Trace.instant(64L, str);
                i = 3;
            }
        }
        if (!webViewIsReadyLocked) {
            android.util.Slog.w(TAG, "creating relro file timed out");
        }
        return new android.webkit.WebViewProviderResponse(packageInfo, i);
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public java.lang.String changeProviderAndSetting(java.lang.String str) {
        android.content.pm.PackageInfo updateCurrentWebViewPackage = updateCurrentWebViewPackage(str);
        return updateCurrentWebViewPackage == null ? "" : updateCurrentWebViewPackage.packageName;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002d A[Catch: all -> 0x0012, TRY_ENTER, TryCatch #0 {, blocks: (B:4:0x0006, B:6:0x000a, B:8:0x0014, B:10:0x001a, B:14:0x002d, B:15:0x0030, B:25:0x003f, B:26:0x0057), top: B:3:0x0006, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.content.pm.PackageInfo updateCurrentWebViewPackage(@android.annotation.Nullable java.lang.String str) {
        android.content.pm.PackageInfo packageInfo;
        android.content.pm.PackageInfo findPreferredWebViewPackage;
        boolean z;
        synchronized (this.mLock) {
            packageInfo = this.mCurrentWebViewPackage;
            if (str != null) {
                this.mSystemInterface.updateUserSetting(this.mContext, str);
            }
            try {
                findPreferredWebViewPackage = findPreferredWebViewPackage();
                if (packageInfo != null) {
                    if (findPreferredWebViewPackage.packageName.equals(packageInfo.packageName)) {
                        z = false;
                        if (z) {
                            onWebViewProviderChanged(findPreferredWebViewPackage);
                        }
                    }
                }
                z = true;
                if (z) {
                }
            } catch (com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException e) {
                this.mCurrentWebViewPackage = null;
                android.util.Slog.e(TAG, "Couldn't find WebView package to use " + e);
                return null;
            }
        }
        if (z && packageInfo != null) {
            this.mSystemInterface.killPackageDependents(packageInfo.packageName);
        }
        return findPreferredWebViewPackage;
    }

    private void onWebViewProviderChanged(android.content.pm.PackageInfo packageInfo) {
        synchronized (this.mLock) {
            try {
                this.mAnyWebViewInstalled = true;
                if (this.mNumRelroCreationsStarted == this.mNumRelroCreationsFinished) {
                    this.mSystemInterface.pinWebviewIfRequired(packageInfo.applicationInfo);
                    this.mCurrentWebViewPackage = packageInfo;
                    this.mNumRelroCreationsStarted = Integer.MAX_VALUE;
                    this.mNumRelroCreationsFinished = 0;
                    this.mNumRelroCreationsStarted = this.mSystemInterface.onWebViewProviderChanged(packageInfo);
                    checkIfRelrosDoneLocked();
                } else {
                    this.mWebViewPackageDirty = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (isMultiProcessEnabled()) {
            android.os.AsyncTask.THREAD_POOL_EXECUTOR.execute(new java.lang.Runnable() { // from class: com.android.server.webkit.WebViewUpdateServiceImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.webkit.WebViewUpdateServiceImpl.this.startZygoteWhenReady();
                }
            });
        }
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public android.webkit.WebViewProviderInfo[] getValidWebViewPackages() {
        com.android.server.webkit.WebViewUpdateServiceImpl.ProviderAndPackageInfo[] validWebViewPackagesAndInfos = getValidWebViewPackagesAndInfos();
        android.webkit.WebViewProviderInfo[] webViewProviderInfoArr = new android.webkit.WebViewProviderInfo[validWebViewPackagesAndInfos.length];
        for (int i = 0; i < validWebViewPackagesAndInfos.length; i++) {
            webViewProviderInfoArr[i] = validWebViewPackagesAndInfos[i].provider;
        }
        return webViewProviderInfoArr;
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public android.webkit.WebViewProviderInfo getDefaultWebViewPackage() {
        throw new java.lang.IllegalStateException("getDefaultWebViewPackage shouldn't be called if update_service_v2 flag is disabled.");
    }

    private static class ProviderAndPackageInfo {
        public final android.content.pm.PackageInfo packageInfo;
        public final android.webkit.WebViewProviderInfo provider;

        ProviderAndPackageInfo(android.webkit.WebViewProviderInfo webViewProviderInfo, android.content.pm.PackageInfo packageInfo) {
            this.provider = webViewProviderInfo;
            this.packageInfo = packageInfo;
        }
    }

    private com.android.server.webkit.WebViewUpdateServiceImpl.ProviderAndPackageInfo[] getValidWebViewPackagesAndInfos() {
        android.webkit.WebViewProviderInfo[] webViewPackages = this.mSystemInterface.getWebViewPackages();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < webViewPackages.length; i++) {
            try {
                android.content.pm.PackageInfo packageInfoForProvider = this.mSystemInterface.getPackageInfoForProvider(webViewPackages[i]);
                if (validityResult(webViewPackages[i], packageInfoForProvider) == 0) {
                    arrayList.add(new com.android.server.webkit.WebViewUpdateServiceImpl.ProviderAndPackageInfo(webViewPackages[i], packageInfoForProvider));
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        }
        return (com.android.server.webkit.WebViewUpdateServiceImpl.ProviderAndPackageInfo[]) arrayList.toArray(new com.android.server.webkit.WebViewUpdateServiceImpl.ProviderAndPackageInfo[arrayList.size()]);
    }

    private android.content.pm.PackageInfo findPreferredWebViewPackage() throws com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException {
        com.android.server.webkit.WebViewUpdateServiceImpl.ProviderAndPackageInfo[] validWebViewPackagesAndInfos = getValidWebViewPackagesAndInfos();
        java.lang.String userChosenWebViewProvider = this.mSystemInterface.getUserChosenWebViewProvider(this.mContext);
        for (com.android.server.webkit.WebViewUpdateServiceImpl.ProviderAndPackageInfo providerAndPackageInfo : validWebViewPackagesAndInfos) {
            if (providerAndPackageInfo.provider.packageName.equals(userChosenWebViewProvider) && isInstalledAndEnabledForAllUsers(this.mSystemInterface.getPackageInfoForProviderAllUsers(this.mContext, providerAndPackageInfo.provider))) {
                return providerAndPackageInfo.packageInfo;
            }
        }
        for (com.android.server.webkit.WebViewUpdateServiceImpl.ProviderAndPackageInfo providerAndPackageInfo2 : validWebViewPackagesAndInfos) {
            if (providerAndPackageInfo2.provider.availableByDefault && isInstalledAndEnabledForAllUsers(this.mSystemInterface.getPackageInfoForProviderAllUsers(this.mContext, providerAndPackageInfo2.provider))) {
                return providerAndPackageInfo2.packageInfo;
            }
        }
        this.mAnyWebViewInstalled = false;
        throw new com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException("Could not find a loadable WebView package");
    }

    private static boolean isInstalledAndEnabledForAllUsers(java.util.List<android.webkit.UserPackage> list) {
        for (android.webkit.UserPackage userPackage : list) {
            if (!userPackage.isInstalledPackage() || !userPackage.isEnabledPackage()) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public android.webkit.WebViewProviderInfo[] getWebViewPackages() {
        return this.mSystemInterface.getWebViewPackages();
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public android.content.pm.PackageInfo getCurrentWebViewPackage() {
        android.content.pm.PackageInfo packageInfo;
        synchronized (this.mLock) {
            packageInfo = this.mCurrentWebViewPackage;
        }
        return packageInfo;
    }

    private boolean webViewIsReadyLocked() {
        return !this.mWebViewPackageDirty && this.mNumRelroCreationsStarted == this.mNumRelroCreationsFinished && this.mAnyWebViewInstalled;
    }

    private void checkIfRelrosDoneLocked() {
        if (this.mNumRelroCreationsStarted == this.mNumRelroCreationsFinished) {
            if (this.mWebViewPackageDirty) {
                this.mWebViewPackageDirty = false;
                try {
                    onWebViewProviderChanged(findPreferredWebViewPackage());
                    return;
                } catch (com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException e) {
                    this.mCurrentWebViewPackage = null;
                    return;
                }
            }
            this.mLock.notifyAll();
        }
    }

    private int validityResult(android.webkit.WebViewProviderInfo webViewProviderInfo, android.content.pm.PackageInfo packageInfo) {
        if (!android.webkit.UserPackage.hasCorrectTargetSdkVersion(packageInfo)) {
            return 1;
        }
        if (!versionCodeGE(packageInfo.getLongVersionCode(), getMinimumVersionCode()) && !this.mSystemInterface.systemIsDebuggable()) {
            return 2;
        }
        if (!providerHasValidSignature(webViewProviderInfo, packageInfo, this.mSystemInterface)) {
            return 3;
        }
        if (android.webkit.WebViewFactory.getWebViewLibrary(packageInfo.applicationInfo) == null) {
            return 4;
        }
        return 0;
    }

    private static boolean versionCodeGE(long j, long j2) {
        return j / 100000 >= j2 / 100000;
    }

    private long getMinimumVersionCode() {
        if (this.mMinimumVersionCode > 0) {
            return this.mMinimumVersionCode;
        }
        long j = -1;
        for (android.webkit.WebViewProviderInfo webViewProviderInfo : this.mSystemInterface.getWebViewPackages()) {
            if (webViewProviderInfo.availableByDefault) {
                try {
                    long factoryPackageVersion = this.mSystemInterface.getFactoryPackageVersion(webViewProviderInfo.packageName);
                    if (j < 0 || factoryPackageVersion < j) {
                        j = factoryPackageVersion;
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                }
            }
        }
        this.mMinimumVersionCode = j;
        return this.mMinimumVersionCode;
    }

    private static boolean providerHasValidSignature(android.webkit.WebViewProviderInfo webViewProviderInfo, android.content.pm.PackageInfo packageInfo, com.android.server.webkit.SystemInterface systemInterface) {
        if (systemInterface.systemIsDebuggable() || packageInfo.applicationInfo.isSystemApp()) {
            return true;
        }
        if (packageInfo.signatures.length != 1) {
            return false;
        }
        for (android.content.pm.Signature signature : webViewProviderInfo.signatures) {
            if (signature.equals(packageInfo.signatures[0])) {
                return true;
            }
        }
        return false;
    }

    private static android.webkit.WebViewProviderInfo getFallbackProvider(android.webkit.WebViewProviderInfo[] webViewProviderInfoArr) {
        for (android.webkit.WebViewProviderInfo webViewProviderInfo : webViewProviderInfoArr) {
            if (webViewProviderInfo.isFallback) {
                return webViewProviderInfo;
            }
        }
        return null;
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public boolean isMultiProcessEnabled() {
        int multiProcessSetting = this.mSystemInterface.getMultiProcessSetting(this.mContext);
        return this.mSystemInterface.isMultiProcessDefaultEnabled() ? multiProcessSetting > Integer.MIN_VALUE : multiProcessSetting >= Integer.MAX_VALUE;
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public void enableMultiProcess(boolean z) {
        android.content.pm.PackageInfo currentWebViewPackage = getCurrentWebViewPackage();
        this.mSystemInterface.setMultiProcessSetting(this.mContext, z ? Integer.MAX_VALUE : Integer.MIN_VALUE);
        this.mSystemInterface.notifyZygote(z);
        if (currentWebViewPackage != null) {
            this.mSystemInterface.killPackageDependents(currentWebViewPackage.packageName);
        }
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public void dumpState(java.io.PrintWriter printWriter) {
        printWriter.println("Current WebView Update Service state");
        printWriter.println(java.lang.String.format("  Multiprocess enabled: %b", java.lang.Boolean.valueOf(isMultiProcessEnabled())));
        synchronized (this.mLock) {
            try {
                if (this.mCurrentWebViewPackage == null) {
                    printWriter.println("  Current WebView package is null");
                } else {
                    printWriter.println(java.lang.String.format("  Current WebView package (name, version): (%s, %s)", this.mCurrentWebViewPackage.packageName, this.mCurrentWebViewPackage.versionName));
                }
                printWriter.println(java.lang.String.format("  Minimum targetSdkVersion: %d", 33));
                printWriter.println(java.lang.String.format("  Minimum WebView version code: %d", java.lang.Long.valueOf(this.mMinimumVersionCode)));
                printWriter.println(java.lang.String.format("  Number of relros started: %d", java.lang.Integer.valueOf(this.mNumRelroCreationsStarted)));
                printWriter.println(java.lang.String.format("  Number of relros finished: %d", java.lang.Integer.valueOf(this.mNumRelroCreationsFinished)));
                printWriter.println(java.lang.String.format("  WebView package dirty: %b", java.lang.Boolean.valueOf(this.mWebViewPackageDirty)));
                printWriter.println(java.lang.String.format("  Any WebView package installed: %b", java.lang.Boolean.valueOf(this.mAnyWebViewInstalled)));
                try {
                    android.content.pm.PackageInfo findPreferredWebViewPackage = findPreferredWebViewPackage();
                    printWriter.println(java.lang.String.format("  Preferred WebView package (name, version): (%s, %s)", findPreferredWebViewPackage.packageName, findPreferredWebViewPackage.versionName));
                } catch (com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException e) {
                    printWriter.println(java.lang.String.format("  Preferred WebView package: none", new java.lang.Object[0]));
                }
                dumpAllPackageInformationLocked(printWriter);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void dumpAllPackageInformationLocked(java.io.PrintWriter printWriter) {
        android.webkit.WebViewProviderInfo[] webViewPackages = this.mSystemInterface.getWebViewPackages();
        printWriter.println("  WebView packages:");
        for (android.webkit.WebViewProviderInfo webViewProviderInfo : webViewPackages) {
            android.content.pm.PackageInfo packageInfo = this.mSystemInterface.getPackageInfoForProviderAllUsers(this.mContext, webViewProviderInfo).get(0).getPackageInfo();
            if (packageInfo == null) {
                printWriter.println(java.lang.String.format("    %s is NOT installed.", webViewProviderInfo.packageName));
            } else {
                int validityResult = validityResult(webViewProviderInfo, packageInfo);
                java.lang.String format = java.lang.String.format("versionName: %s, versionCode: %d, targetSdkVersion: %d", packageInfo.versionName, java.lang.Long.valueOf(packageInfo.getLongVersionCode()), java.lang.Integer.valueOf(packageInfo.applicationInfo.targetSdkVersion));
                if (validityResult == 0) {
                    printWriter.println(java.lang.String.format("    Valid package %s (%s) is %s installed/enabled for all users", packageInfo.packageName, format, isInstalledAndEnabledForAllUsers(this.mSystemInterface.getPackageInfoForProviderAllUsers(this.mContext, webViewProviderInfo)) ? "" : "NOT"));
                } else {
                    printWriter.println(java.lang.String.format("    Invalid package %s (%s), reason: %s", packageInfo.packageName, format, getInvalidityReason(validityResult)));
                }
            }
        }
    }

    private static java.lang.String getInvalidityReason(int i) {
        switch (i) {
            case 1:
                return "SDK version too low";
            case 2:
                return "Version code too low";
            case 3:
                return "Incorrect signature";
            case 4:
                return "No WebView-library manifest flag";
            default:
                return "Unexcepted validity-reason";
        }
    }
}
