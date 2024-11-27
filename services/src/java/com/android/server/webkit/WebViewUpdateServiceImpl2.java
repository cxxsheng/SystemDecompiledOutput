package com.android.server.webkit;

/* loaded from: classes.dex */
class WebViewUpdateServiceImpl2 implements com.android.server.webkit.WebViewUpdateServiceInterface {
    private static final long NS_PER_MS = 1000000;
    private static final int NUMBER_OF_RELROS_UNKNOWN = Integer.MAX_VALUE;
    private static final java.lang.String TAG = com.android.server.webkit.WebViewUpdateServiceImpl2.class.getSimpleName();
    private static final int VALIDITY_INCORRECT_SDK_VERSION = 1;
    private static final int VALIDITY_INCORRECT_SIGNATURE = 3;
    private static final int VALIDITY_INCORRECT_VERSION_CODE = 2;
    private static final int VALIDITY_NO_LIBRARY_FLAG = 4;
    private static final int VALIDITY_OK = 0;
    private static final int WAIT_TIMEOUT_MS = 1000;
    private final android.content.Context mContext;
    private final android.webkit.WebViewProviderInfo mDefaultProvider;
    private final com.android.server.webkit.SystemInterface mSystemInterface;
    private long mMinimumVersionCode = -1;
    private int mNumRelroCreationsStarted = 0;
    private int mNumRelroCreationsFinished = 0;
    private boolean mWebViewPackageDirty = false;
    private boolean mAnyWebViewInstalled = false;
    private boolean mAttemptedToRepairBefore = false;
    private android.content.pm.PackageInfo mCurrentWebViewPackage = null;
    private final java.lang.Object mLock = new java.lang.Object();

    private static class WebViewPackageMissingException extends java.lang.Exception {
        WebViewPackageMissingException(java.lang.String str) {
            super(str);
        }
    }

    WebViewUpdateServiceImpl2(android.content.Context context, com.android.server.webkit.SystemInterface systemInterface) {
        int i = 0;
        android.webkit.WebViewProviderInfo webViewProviderInfo = null;
        this.mContext = context;
        this.mSystemInterface = systemInterface;
        android.webkit.WebViewProviderInfo[] webViewPackages = getWebViewPackages();
        int length = webViewPackages.length;
        while (true) {
            if (i >= length) {
                break;
            }
            android.webkit.WebViewProviderInfo webViewProviderInfo2 = webViewPackages[i];
            if (!webViewProviderInfo2.availableByDefault) {
                i++;
            } else {
                webViewProviderInfo = webViewProviderInfo2;
                break;
            }
        }
        if (webViewProviderInfo == null) {
            throw new android.util.AndroidRuntimeException("No available by default WebView Provider.");
        }
        this.mDefaultProvider = webViewProviderInfo;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:9|(5:10|11|(1:13)(1:47)|15|16)|(2:20|(6:22|23|24|(1:26)|28|29))|44|23|24|(0)|28|29) */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x005b, code lost:
    
        r8 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x005c, code lost:
    
        r5 = r1;
        r1 = r0;
        r0 = r5;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0057 A[Catch: all -> 0x002b, WebViewPackageMissingException -> 0x005b, TRY_LEAVE, TryCatch #1 {, blocks: (B:11:0x001e, B:13:0x0026, B:16:0x0033, B:18:0x003d, B:20:0x0045, B:24:0x004f, B:26:0x0057, B:28:0x007d, B:29:0x0081, B:42:0x0062), top: B:10:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0084 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void packageStateChanged(java.lang.String str, int i, int i2) {
        java.lang.String str2;
        boolean z;
        boolean shouldTriggerRepairLocked;
        android.content.pm.PackageInfo findPreferredWebViewPackage;
        boolean z2 = false;
        for (android.webkit.WebViewProviderInfo webViewProviderInfo : this.mSystemInterface.getWebViewPackages()) {
            if (webViewProviderInfo.packageName.equals(str)) {
                synchronized (this.mLock) {
                    try {
                        findPreferredWebViewPackage = findPreferredWebViewPackage();
                        str2 = this.mCurrentWebViewPackage != null ? this.mCurrentWebViewPackage.packageName : null;
                        try {
                        } catch (com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException e) {
                            e = e;
                            boolean z3 = false;
                            this.mCurrentWebViewPackage = null;
                            android.util.Slog.e(TAG, "Could not find valid WebView package to create relro with " + e);
                            boolean z4 = z3;
                            z = z2;
                            z2 = z4;
                            shouldTriggerRepairLocked = shouldTriggerRepairLocked();
                            if (z) {
                                this.mSystemInterface.killPackageDependents(str2);
                            }
                            if (shouldTriggerRepairLocked) {
                            }
                        }
                    } catch (com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException e2) {
                        e = e2;
                        str2 = null;
                    }
                    if (!webViewProviderInfo.packageName.equals(findPreferredWebViewPackage.packageName) && !webViewProviderInfo.packageName.equals(str2)) {
                        if (this.mCurrentWebViewPackage != null) {
                            z = false;
                            z2 = webViewProviderInfo.packageName.equals(str2);
                            if (z) {
                                onWebViewProviderChanged(findPreferredWebViewPackage);
                            }
                            shouldTriggerRepairLocked = shouldTriggerRepairLocked();
                        }
                    }
                    z = true;
                    z2 = webViewProviderInfo.packageName.equals(str2);
                    if (z) {
                    }
                    shouldTriggerRepairLocked = shouldTriggerRepairLocked();
                }
                if (z && !z2 && str2 != null) {
                    this.mSystemInterface.killPackageDependents(str2);
                }
                if (shouldTriggerRepairLocked) {
                    attemptRepair();
                    return;
                }
                return;
            }
        }
    }

    private boolean shouldTriggerRepairLocked() {
        if (this.mAttemptedToRepairBefore) {
            return false;
        }
        if (this.mCurrentWebViewPackage == null) {
            return true;
        }
        if (this.mCurrentWebViewPackage.packageName.equals(this.mDefaultProvider.packageName)) {
            return !isInstalledAndEnabledForAllUsers(this.mSystemInterface.getPackageInfoForProviderAllUsers(this.mContext, this.mDefaultProvider));
        }
        return false;
    }

    private void attemptRepair() {
        synchronized (this.mLock) {
            try {
                if (this.mAttemptedToRepairBefore) {
                    return;
                }
                this.mAttemptedToRepairBefore = true;
                android.util.Slog.w(TAG, "No provider available for all users, trying to install and enable " + this.mDefaultProvider.packageName);
                this.mSystemInterface.installExistingPackageForAllUsers(this.mContext, this.mDefaultProvider.packageName);
                this.mSystemInterface.enablePackageForAllUsers(this.mContext, this.mDefaultProvider.packageName, true);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public void prepareWebViewInSystemServer() {
        boolean shouldTriggerRepairLocked;
        try {
            synchronized (this.mLock) {
                try {
                    this.mCurrentWebViewPackage = findPreferredWebViewPackage();
                    shouldTriggerRepairLocked = shouldTriggerRepairLocked();
                    java.lang.String userChosenWebViewProvider = this.mSystemInterface.getUserChosenWebViewProvider(this.mContext);
                    if (userChosenWebViewProvider != null && !userChosenWebViewProvider.equals(this.mCurrentWebViewPackage.packageName)) {
                        this.mSystemInterface.updateUserSetting(this.mContext, this.mCurrentWebViewPackage.packageName);
                    }
                    onWebViewProviderChanged(this.mCurrentWebViewPackage);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (shouldTriggerRepairLocked) {
                attemptRepair();
            }
        } catch (java.lang.Throwable th2) {
            android.util.Slog.e(TAG, "error preparing webview provider from system server", th2);
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

    /* JADX WARN: Removed duplicated region for block: B:14:0x002f A[Catch: all -> 0x0013, TRY_ENTER, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000b, B:8:0x0015, B:10:0x001c, B:14:0x002f, B:16:0x0034, B:17:0x0038, B:29:0x004c, B:30:0x0064), top: B:3:0x0007, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0034 A[Catch: all -> 0x0013, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000b, B:8:0x0015, B:10:0x001c, B:14:0x002f, B:16:0x0034, B:17:0x0038, B:29:0x004c, B:30:0x0064), top: B:3:0x0007, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.content.pm.PackageInfo updateCurrentWebViewPackage(@android.annotation.Nullable java.lang.String str) {
        android.content.pm.PackageInfo packageInfo;
        android.content.pm.PackageInfo findPreferredWebViewPackage;
        boolean z;
        boolean z2;
        synchronized (this.mLock) {
            packageInfo = this.mCurrentWebViewPackage;
            if (str != null) {
                this.mSystemInterface.updateUserSetting(this.mContext, str);
            }
            try {
                findPreferredWebViewPackage = findPreferredWebViewPackage();
                z = false;
                if (packageInfo != null) {
                    if (findPreferredWebViewPackage.packageName.equals(packageInfo.packageName)) {
                        z2 = false;
                        if (z2) {
                            onWebViewProviderChanged(findPreferredWebViewPackage);
                        }
                        if (str == null) {
                            z = shouldTriggerRepairLocked();
                        }
                    }
                }
                z2 = true;
                if (z2) {
                }
                if (str == null) {
                }
            } catch (com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException e) {
                this.mCurrentWebViewPackage = null;
                android.util.Slog.e(TAG, "Couldn't find WebView package to use " + e);
                return null;
            }
        }
        if (z2 && packageInfo != null) {
            this.mSystemInterface.killPackageDependents(packageInfo.packageName);
        }
        if (z) {
            attemptRepair();
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
        android.os.AsyncTask.THREAD_POOL_EXECUTOR.execute(new java.lang.Runnable() { // from class: com.android.server.webkit.WebViewUpdateServiceImpl2$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.webkit.WebViewUpdateServiceImpl2.this.startZygoteWhenReady();
            }
        });
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public android.webkit.WebViewProviderInfo[] getValidWebViewPackages() {
        com.android.server.webkit.WebViewUpdateServiceImpl2.ProviderAndPackageInfo[] validWebViewPackagesAndInfos = getValidWebViewPackagesAndInfos();
        android.webkit.WebViewProviderInfo[] webViewProviderInfoArr = new android.webkit.WebViewProviderInfo[validWebViewPackagesAndInfos.length];
        for (int i = 0; i < validWebViewPackagesAndInfos.length; i++) {
            webViewProviderInfoArr[i] = validWebViewPackagesAndInfos[i].provider;
        }
        return webViewProviderInfoArr;
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public android.webkit.WebViewProviderInfo getDefaultWebViewPackage() {
        return this.mDefaultProvider;
    }

    private static class ProviderAndPackageInfo {
        public final android.content.pm.PackageInfo packageInfo;
        public final android.webkit.WebViewProviderInfo provider;

        ProviderAndPackageInfo(android.webkit.WebViewProviderInfo webViewProviderInfo, android.content.pm.PackageInfo packageInfo) {
            this.provider = webViewProviderInfo;
            this.packageInfo = packageInfo;
        }
    }

    private com.android.server.webkit.WebViewUpdateServiceImpl2.ProviderAndPackageInfo[] getValidWebViewPackagesAndInfos() {
        android.webkit.WebViewProviderInfo[] webViewPackages = this.mSystemInterface.getWebViewPackages();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < webViewPackages.length; i++) {
            try {
                android.content.pm.PackageInfo packageInfoForProvider = this.mSystemInterface.getPackageInfoForProvider(webViewPackages[i]);
                if (validityResult(webViewPackages[i], packageInfoForProvider) == 0) {
                    arrayList.add(new com.android.server.webkit.WebViewUpdateServiceImpl2.ProviderAndPackageInfo(webViewPackages[i], packageInfoForProvider));
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        }
        return (com.android.server.webkit.WebViewUpdateServiceImpl2.ProviderAndPackageInfo[]) arrayList.toArray(new com.android.server.webkit.WebViewUpdateServiceImpl2.ProviderAndPackageInfo[arrayList.size()]);
    }

    private android.content.pm.PackageInfo findPreferredWebViewPackage() throws com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException {
        java.lang.String userChosenWebViewProvider = this.mSystemInterface.getUserChosenWebViewProvider(this.mContext);
        android.webkit.WebViewProviderInfo webViewProviderForPackage = getWebViewProviderForPackage(userChosenWebViewProvider);
        if (webViewProviderForPackage != null) {
            try {
                android.content.pm.PackageInfo packageInfoForProvider = this.mSystemInterface.getPackageInfoForProvider(webViewProviderForPackage);
                if (validityResult(webViewProviderForPackage, packageInfoForProvider) == 0) {
                    if (isInstalledAndEnabledForAllUsers(this.mSystemInterface.getPackageInfoForProviderAllUsers(this.mContext, webViewProviderForPackage))) {
                        return packageInfoForProvider;
                    }
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.w(TAG, "User chosen WebView package (" + userChosenWebViewProvider + ") not found");
            }
        }
        try {
            android.content.pm.PackageInfo packageInfoForProvider2 = this.mSystemInterface.getPackageInfoForProvider(this.mDefaultProvider);
            if (validityResult(this.mDefaultProvider, packageInfoForProvider2) == 0) {
                return packageInfoForProvider2;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            android.util.Slog.w(TAG, "Default WebView package (" + this.mDefaultProvider.packageName + ") not found");
        }
        this.mAnyWebViewInstalled = false;
        throw new com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException("Could not find a loadable WebView package");
    }

    private android.webkit.WebViewProviderInfo getWebViewProviderForPackage(java.lang.String str) {
        android.webkit.WebViewProviderInfo[] webViewPackages = getWebViewPackages();
        for (int i = 0; i < webViewPackages.length; i++) {
            if (webViewPackages[i].packageName.equals(str)) {
                return webViewPackages[i];
            }
        }
        return null;
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
                } catch (com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException e) {
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
        throw new java.lang.IllegalStateException("isMultiProcessEnabled shouldn't be called if update_service_v2 flag is set.");
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public void enableMultiProcess(boolean z) {
        throw new java.lang.IllegalStateException("enableMultiProcess shouldn't be called if update_service_v2 flag is set.");
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public void dumpState(java.io.PrintWriter printWriter) {
        printWriter.println("Current WebView Update Service state");
        synchronized (this.mLock) {
            try {
                if (this.mCurrentWebViewPackage == null) {
                    printWriter.println("  Current WebView package is null");
                } else {
                    printWriter.println(android.text.TextUtils.formatSimple("  Current WebView package (name, version): (%s, %s)", new java.lang.Object[]{this.mCurrentWebViewPackage.packageName, this.mCurrentWebViewPackage.versionName}));
                }
                printWriter.println(android.text.TextUtils.formatSimple("  Minimum targetSdkVersion: %d", new java.lang.Object[]{33}));
                printWriter.println(android.text.TextUtils.formatSimple("  Minimum WebView version code: %d", new java.lang.Object[]{java.lang.Long.valueOf(this.mMinimumVersionCode)}));
                printWriter.println(android.text.TextUtils.formatSimple("  Number of relros started: %d", new java.lang.Object[]{java.lang.Integer.valueOf(this.mNumRelroCreationsStarted)}));
                printWriter.println(android.text.TextUtils.formatSimple("  Number of relros finished: %d", new java.lang.Object[]{java.lang.Integer.valueOf(this.mNumRelroCreationsFinished)}));
                printWriter.println(android.text.TextUtils.formatSimple("  WebView package dirty: %b", new java.lang.Object[]{java.lang.Boolean.valueOf(this.mWebViewPackageDirty)}));
                printWriter.println(android.text.TextUtils.formatSimple("  Any WebView package installed: %b", new java.lang.Object[]{java.lang.Boolean.valueOf(this.mAnyWebViewInstalled)}));
                try {
                    android.content.pm.PackageInfo findPreferredWebViewPackage = findPreferredWebViewPackage();
                    printWriter.println(android.text.TextUtils.formatSimple("  Preferred WebView package (name, version): (%s, %s)", new java.lang.Object[]{findPreferredWebViewPackage.packageName, findPreferredWebViewPackage.versionName}));
                } catch (com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException e) {
                    printWriter.println("  Preferred WebView package: none");
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
                printWriter.println(android.text.TextUtils.formatSimple("    %s is NOT installed.", new java.lang.Object[]{webViewProviderInfo.packageName}));
            } else {
                int validityResult = validityResult(webViewProviderInfo, packageInfo);
                java.lang.String formatSimple = android.text.TextUtils.formatSimple("versionName: %s, versionCode: %d, targetSdkVersion: %d", new java.lang.Object[]{packageInfo.versionName, java.lang.Long.valueOf(packageInfo.getLongVersionCode()), java.lang.Integer.valueOf(packageInfo.applicationInfo.targetSdkVersion)});
                if (validityResult == 0) {
                    printWriter.println(android.text.TextUtils.formatSimple("    Valid package %s (%s) is %s installed/enabled for all users", new java.lang.Object[]{packageInfo.packageName, formatSimple, isInstalledAndEnabledForAllUsers(this.mSystemInterface.getPackageInfoForProviderAllUsers(this.mContext, webViewProviderInfo)) ? "" : "NOT"}));
                } else {
                    printWriter.println(android.text.TextUtils.formatSimple("    Invalid package %s (%s), reason: %s", new java.lang.Object[]{packageInfo.packageName, formatSimple, getInvalidityReason(validityResult)}));
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
