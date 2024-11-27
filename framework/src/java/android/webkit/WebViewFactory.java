package android.webkit;

@android.annotation.SystemApi
/* loaded from: classes4.dex */
public final class WebViewFactory {
    private static final java.lang.String CHROMIUM_WEBVIEW_FACTORY = "com.android.webview.chromium.WebViewChromiumFactoryProviderForT";
    private static final java.lang.String CHROMIUM_WEBVIEW_FACTORY_METHOD = "create";
    private static final boolean DEBUG = false;
    public static final int LIBLOAD_ADDRESS_SPACE_NOT_RESERVED = 2;
    public static final int LIBLOAD_FAILED_JNI_CALL = 7;
    public static final int LIBLOAD_FAILED_LISTING_WEBVIEW_PACKAGES = 4;
    public static final int LIBLOAD_FAILED_TO_FIND_NAMESPACE = 10;
    public static final int LIBLOAD_FAILED_TO_LOAD_LIBRARY = 6;
    public static final int LIBLOAD_FAILED_TO_OPEN_RELRO_FILE = 5;
    public static final int LIBLOAD_FAILED_WAITING_FOR_RELRO = 3;
    public static final int LIBLOAD_FAILED_WAITING_FOR_WEBVIEW_REASON_UNKNOWN = 8;
    public static final int LIBLOAD_SUCCESS = 0;
    public static final int LIBLOAD_WRONG_PACKAGE_NAME = 1;
    private static final java.lang.String LOGTAG = "WebViewFactory";
    private static java.lang.String sDataDirectorySuffix;
    private static android.content.pm.PackageInfo sPackageInfo;
    private static android.webkit.WebViewFactoryProvider sProviderInstance;
    private static boolean sWebViewDisabled;
    private static java.lang.Boolean sWebViewSupported;
    private static final java.lang.Object sProviderLock = new java.lang.Object();
    static final android.webkit.WebViewFactory.StartupTimestamps sTimestamps = new android.webkit.WebViewFactory.StartupTimestamps();
    private static java.lang.String WEBVIEW_UPDATE_SERVICE_NAME = android.content.Context.WEBVIEW_UPDATE_SERVICE;

    public static class StartupTimestamps {
        long mAddAssetsEnd;
        long mAddAssetsStart;
        long mCreateContextEnd;
        long mCreateContextStart;
        long mGetClassLoaderEnd;
        long mGetClassLoaderStart;
        long mNativeLoadEnd;
        long mNativeLoadStart;
        long mProviderClassForNameEnd;
        long mProviderClassForNameStart;
        long mWebViewLoadStart;

        StartupTimestamps() {
        }

        public long getWebViewLoadStart() {
            return this.mWebViewLoadStart;
        }

        public long getCreateContextStart() {
            return this.mCreateContextStart;
        }

        public long getCreateContextEnd() {
            return this.mCreateContextEnd;
        }

        public long getAddAssetsStart() {
            return this.mAddAssetsStart;
        }

        public long getAddAssetsEnd() {
            return this.mAddAssetsEnd;
        }

        public long getGetClassLoaderStart() {
            return this.mGetClassLoaderStart;
        }

        public long getGetClassLoaderEnd() {
            return this.mGetClassLoaderEnd;
        }

        public long getNativeLoadStart() {
            return this.mNativeLoadStart;
        }

        public long getNativeLoadEnd() {
            return this.mNativeLoadEnd;
        }

        public long getProviderClassForNameStart() {
            return this.mProviderClassForNameStart;
        }

        public long getProviderClassForNameEnd() {
            return this.mProviderClassForNameEnd;
        }
    }

    static android.webkit.WebViewFactory.StartupTimestamps getStartupTimestamps() {
        return sTimestamps;
    }

    private static java.lang.String getWebViewPreparationErrorReason(int i) {
        switch (i) {
            case 3:
                return "Time out waiting for Relro files being created";
            case 4:
                return "No WebView installed";
            case 8:
                return "Crashed for unknown reason";
            default:
                return "Unknown";
        }
    }

    static class MissingWebViewPackageException extends java.lang.Exception {
        public MissingWebViewPackageException(java.lang.String str) {
            super(str);
        }

        public MissingWebViewPackageException(java.lang.Exception exc) {
            super(exc);
        }
    }

    private static boolean isWebViewSupported() {
        if (sWebViewSupported == null) {
            sWebViewSupported = java.lang.Boolean.valueOf(android.app.AppGlobals.getInitialApplication().getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_WEBVIEW));
        }
        return sWebViewSupported.booleanValue();
    }

    static void disableWebView() {
        synchronized (sProviderLock) {
            if (sProviderInstance != null) {
                throw new java.lang.IllegalStateException("Can't disable WebView: WebView already initialized");
            }
            sWebViewDisabled = true;
        }
    }

    static void setDataDirectorySuffix(java.lang.String str) {
        synchronized (sProviderLock) {
            if (sProviderInstance != null) {
                throw new java.lang.IllegalStateException("Can't set data directory suffix: WebView already initialized");
            }
            if (str.indexOf(java.io.File.separatorChar) >= 0) {
                throw new java.lang.IllegalArgumentException("Suffix " + str + " contains a path separator");
            }
            sDataDirectorySuffix = str;
        }
    }

    static java.lang.String getDataDirectorySuffix() {
        java.lang.String str;
        synchronized (sProviderLock) {
            str = sDataDirectorySuffix;
        }
        return str;
    }

    public static java.lang.String getWebViewLibrary(android.content.pm.ApplicationInfo applicationInfo) {
        if (applicationInfo.metaData != null) {
            return applicationInfo.metaData.getString("com.android.webview.WebViewLibrary");
        }
        return null;
    }

    public static android.content.pm.PackageInfo getLoadedPackageInfo() {
        android.content.pm.PackageInfo packageInfo;
        synchronized (sProviderLock) {
            packageInfo = sPackageInfo;
        }
        return packageInfo;
    }

    public static java.lang.Class<android.webkit.WebViewFactoryProvider> getWebViewProviderClass(java.lang.ClassLoader classLoader) throws java.lang.ClassNotFoundException {
        return java.lang.Class.forName(CHROMIUM_WEBVIEW_FACTORY, true, classLoader);
    }

    public static int loadWebViewNativeLibraryFromPackage(java.lang.String str, java.lang.ClassLoader classLoader) {
        android.webkit.WebViewProviderResponse waitForAndGetProvider;
        if (!isWebViewSupported()) {
            return 1;
        }
        android.app.Application initialApplication = android.app.AppGlobals.getInitialApplication();
        try {
            if (android.webkit.Flags.updateServiceIpcWrapper()) {
                waitForAndGetProvider = ((android.webkit.WebViewUpdateManager) initialApplication.getSystemService(android.webkit.WebViewUpdateManager.class)).waitForAndGetProvider();
            } else {
                waitForAndGetProvider = getUpdateService().waitForAndGetProvider();
            }
            if (waitForAndGetProvider.status != 0 && waitForAndGetProvider.status != 3) {
                return waitForAndGetProvider.status;
            }
            if (!waitForAndGetProvider.packageInfo.packageName.equals(str)) {
                return 1;
            }
            try {
                int loadNativeLibrary = android.webkit.WebViewLibraryLoader.loadNativeLibrary(classLoader, getWebViewLibrary(initialApplication.getPackageManager().getPackageInfo(str, 268435584).applicationInfo));
                return loadNativeLibrary == 0 ? waitForAndGetProvider.status : loadNativeLibrary;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Log.e(LOGTAG, "Couldn't find package " + str);
                return 1;
            }
        } catch (java.lang.Exception e2) {
            android.util.Log.e(LOGTAG, "error waiting for relro creation", e2);
            return 8;
        }
    }

    static android.webkit.WebViewFactoryProvider getProvider() {
        synchronized (sProviderLock) {
            if (sProviderInstance != null) {
                return sProviderInstance;
            }
            sTimestamps.mWebViewLoadStart = android.os.SystemClock.uptimeMillis();
            int myUid = android.os.Process.myUid();
            if (myUid == 0 || myUid == 1000 || myUid == 1001 || myUid == 1027 || myUid == 1002) {
                throw new java.lang.UnsupportedOperationException("For security reasons, WebView is not allowed in privileged processes");
            }
            if (!isWebViewSupported()) {
                throw new java.lang.UnsupportedOperationException();
            }
            if (sWebViewDisabled) {
                throw new java.lang.IllegalStateException("WebView.disableWebView() was called: WebView is disabled");
            }
            android.os.Trace.traceBegin(16L, "WebViewFactory.getProvider()");
            try {
                try {
                    java.lang.reflect.Method method = getProviderClass().getMethod(CHROMIUM_WEBVIEW_FACTORY_METHOD, android.webkit.WebViewDelegate.class);
                    android.os.Trace.traceBegin(16L, "WebViewFactoryProvider invocation");
                    try {
                        sProviderInstance = (android.webkit.WebViewFactoryProvider) method.invoke(null, new android.webkit.WebViewDelegate());
                        android.webkit.WebViewFactoryProvider webViewFactoryProvider = sProviderInstance;
                        android.os.Trace.traceEnd(16L);
                        return webViewFactoryProvider;
                    } finally {
                    }
                } catch (java.lang.Exception e) {
                    android.util.Log.e(LOGTAG, "error instantiating provider", e);
                    throw new android.util.AndroidRuntimeException(e);
                }
            } finally {
            }
        }
    }

    private static boolean signaturesEquals(android.content.pm.Signature[] signatureArr, android.content.pm.Signature[] signatureArr2) {
        if (signatureArr == null) {
            return signatureArr2 == null;
        }
        if (signatureArr2 == null) {
            return false;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (android.content.pm.Signature signature : signatureArr) {
            arraySet.add(signature);
        }
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        for (android.content.pm.Signature signature2 : signatureArr2) {
            arraySet2.add(signature2);
        }
        return arraySet.equals(arraySet2);
    }

    private static void verifyPackageInfo(android.content.pm.PackageInfo packageInfo, android.content.pm.PackageInfo packageInfo2) throws android.webkit.WebViewFactory.MissingWebViewPackageException {
        if (!packageInfo.packageName.equals(packageInfo2.packageName)) {
            throw new android.webkit.WebViewFactory.MissingWebViewPackageException("Failed to verify WebView provider, packageName mismatch, expected: " + packageInfo.packageName + " actual: " + packageInfo2.packageName);
        }
        if (packageInfo.getLongVersionCode() > packageInfo2.getLongVersionCode()) {
            throw new android.webkit.WebViewFactory.MissingWebViewPackageException("Failed to verify WebView provider, version code is lower than expected: " + packageInfo.getLongVersionCode() + " actual: " + packageInfo2.getLongVersionCode());
        }
        if (getWebViewLibrary(packageInfo2.applicationInfo) == null) {
            throw new android.webkit.WebViewFactory.MissingWebViewPackageException("Tried to load an invalid WebView provider: " + packageInfo2.packageName);
        }
        if (!signaturesEquals(packageInfo.signatures, packageInfo2.signatures)) {
            throw new android.webkit.WebViewFactory.MissingWebViewPackageException("Failed to verify WebView provider, signature mismatch");
        }
    }

    private static boolean isEnabledPackage(android.content.pm.PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        return packageInfo.applicationInfo.enabled;
    }

    private static boolean isInstalledPackage(android.content.pm.PackageInfo packageInfo) {
        return (packageInfo == null || (packageInfo.applicationInfo.flags & 8388608) == 0 || (packageInfo.applicationInfo.privateFlags & 1) != 0) ? false : true;
    }

    private static android.content.Context getWebViewContextAndSetProvider() throws android.webkit.WebViewFactory.MissingWebViewPackageException {
        android.webkit.WebViewProviderResponse waitForAndGetProvider;
        android.app.Application initialApplication = android.app.AppGlobals.getInitialApplication();
        try {
            android.os.Trace.traceBegin(16L, "WebViewUpdateService.waitForAndGetProvider()");
            try {
                if (android.webkit.Flags.updateServiceIpcWrapper()) {
                    waitForAndGetProvider = ((android.webkit.WebViewUpdateManager) initialApplication.getSystemService(android.webkit.WebViewUpdateManager.class)).waitForAndGetProvider();
                } else {
                    waitForAndGetProvider = getUpdateService().waitForAndGetProvider();
                }
                android.os.Trace.traceEnd(16L);
                if (waitForAndGetProvider.status != 0 && waitForAndGetProvider.status != 3) {
                    throw new android.webkit.WebViewFactory.MissingWebViewPackageException("Failed to load WebView provider: " + getWebViewPreparationErrorReason(waitForAndGetProvider.status));
                }
                android.os.Trace.traceBegin(16L, "ActivityManager.addPackageDependency()");
                try {
                    android.app.ActivityManager.getService().addPackageDependency(waitForAndGetProvider.packageInfo.packageName);
                    android.os.Trace.traceEnd(16L);
                    android.content.pm.PackageManager packageManager = initialApplication.getPackageManager();
                    android.os.Trace.traceBegin(16L, "PackageManager.getPackageInfo()");
                    try {
                        android.content.pm.PackageInfo packageInfo = packageManager.getPackageInfo(waitForAndGetProvider.packageInfo.packageName, 268444864);
                        android.os.Trace.traceEnd(16L);
                        if (android.webkit.Flags.updateServiceV2() && !isInstalledPackage(packageInfo)) {
                            throw new android.webkit.WebViewFactory.MissingWebViewPackageException(android.text.TextUtils.formatSimple("Current WebView Package (%s) is not installed for the current user", packageInfo.packageName));
                        }
                        if (android.webkit.Flags.updateServiceV2() && !isEnabledPackage(packageInfo)) {
                            throw new android.webkit.WebViewFactory.MissingWebViewPackageException(android.text.TextUtils.formatSimple("Current WebView Package (%s) is not enabled for the current user", packageInfo.packageName));
                        }
                        verifyPackageInfo(waitForAndGetProvider.packageInfo, packageInfo);
                        android.content.pm.ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                        android.os.Trace.traceBegin(16L, "initialApplication.createApplicationContext");
                        sTimestamps.mCreateContextStart = android.os.SystemClock.uptimeMillis();
                        try {
                            android.content.Context createApplicationContext = initialApplication.createApplicationContext(applicationInfo, 3);
                            sPackageInfo = packageInfo;
                            return createApplicationContext;
                        } finally {
                            sTimestamps.mCreateContextEnd = android.os.SystemClock.uptimeMillis();
                        }
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException | android.os.RemoteException e) {
            throw new android.webkit.WebViewFactory.MissingWebViewPackageException("Failed to load WebView provider: " + e);
        }
    }

    private static java.lang.Class<android.webkit.WebViewFactoryProvider> getProviderClass() {
        android.app.Application initialApplication = android.app.AppGlobals.getInitialApplication();
        try {
            android.os.Trace.traceBegin(16L, "WebViewFactory.getWebViewContextAndSetProvider()");
            try {
                android.content.Context webViewContextAndSetProvider = getWebViewContextAndSetProvider();
                android.os.Trace.traceEnd(16L);
                android.util.Log.i(LOGTAG, "Loading " + sPackageInfo.packageName + " version " + sPackageInfo.versionName + " (code " + sPackageInfo.getLongVersionCode() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                android.os.Trace.traceBegin(16L, "WebViewFactory.getChromiumProviderClass()");
                try {
                    try {
                        sTimestamps.mAddAssetsStart = android.os.SystemClock.uptimeMillis();
                        for (java.lang.String str : webViewContextAndSetProvider.getApplicationInfo().getAllApkPaths()) {
                            initialApplication.getAssets().addAssetPathAsSharedLibrary(str);
                        }
                        android.webkit.WebViewFactory.StartupTimestamps startupTimestamps = sTimestamps;
                        android.webkit.WebViewFactory.StartupTimestamps startupTimestamps2 = sTimestamps;
                        long uptimeMillis = android.os.SystemClock.uptimeMillis();
                        startupTimestamps2.mGetClassLoaderStart = uptimeMillis;
                        startupTimestamps.mAddAssetsEnd = uptimeMillis;
                        java.lang.ClassLoader classLoader = webViewContextAndSetProvider.getClassLoader();
                        android.os.Trace.traceBegin(16L, "WebViewFactory.loadNativeLibrary()");
                        android.webkit.WebViewFactory.StartupTimestamps startupTimestamps3 = sTimestamps;
                        android.webkit.WebViewFactory.StartupTimestamps startupTimestamps4 = sTimestamps;
                        long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
                        startupTimestamps4.mNativeLoadStart = uptimeMillis2;
                        startupTimestamps3.mGetClassLoaderEnd = uptimeMillis2;
                        android.webkit.WebViewLibraryLoader.loadNativeLibrary(classLoader, getWebViewLibrary(sPackageInfo.applicationInfo));
                        android.os.Trace.traceEnd(16L);
                        android.os.Trace.traceBegin(16L, "Class.forName()");
                        android.webkit.WebViewFactory.StartupTimestamps startupTimestamps5 = sTimestamps;
                        android.webkit.WebViewFactory.StartupTimestamps startupTimestamps6 = sTimestamps;
                        long uptimeMillis3 = android.os.SystemClock.uptimeMillis();
                        startupTimestamps6.mProviderClassForNameStart = uptimeMillis3;
                        startupTimestamps5.mNativeLoadEnd = uptimeMillis3;
                        try {
                            return getWebViewProviderClass(classLoader);
                        } finally {
                            sTimestamps.mProviderClassForNameEnd = android.os.SystemClock.uptimeMillis();
                        }
                    } catch (java.lang.ClassNotFoundException e) {
                        android.util.Log.e(LOGTAG, "error loading provider", e);
                        throw new android.util.AndroidRuntimeException(e);
                    }
                } finally {
                }
            } finally {
            }
        } catch (android.webkit.WebViewFactory.MissingWebViewPackageException e2) {
            android.util.Log.e(LOGTAG, "Chromium WebView package does not exist", e2);
            throw new android.util.AndroidRuntimeException(e2);
        }
    }

    public static void prepareWebViewInZygote() {
        try {
            android.webkit.WebViewLibraryLoader.reserveAddressSpaceInZygote();
        } catch (java.lang.Throwable th) {
            android.util.Log.e(LOGTAG, "error preparing native loader", th);
        }
    }

    public static int onWebViewProviderChanged(android.content.pm.PackageInfo packageInfo) {
        int i;
        try {
            i = android.webkit.WebViewLibraryLoader.prepareNativeLibraries(packageInfo);
        } catch (java.lang.Throwable th) {
            android.util.Log.e(LOGTAG, "error preparing webview native library", th);
            i = 0;
        }
        android.webkit.WebViewZygote.onWebViewProviderChanged(packageInfo);
        return i;
    }

    public static android.webkit.IWebViewUpdateService getUpdateService() {
        if (isWebViewSupported()) {
            return getUpdateServiceUnchecked();
        }
        return null;
    }

    static android.webkit.IWebViewUpdateService getUpdateServiceUnchecked() {
        return android.webkit.IWebViewUpdateService.Stub.asInterface(android.os.ServiceManager.getService(WEBVIEW_UPDATE_SERVICE_NAME));
    }
}
