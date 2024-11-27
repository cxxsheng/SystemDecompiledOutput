package com.android.internal.os;

/* loaded from: classes4.dex */
class WebViewZygoteInit {
    public static final java.lang.String TAG = "WebViewZygoteInit";

    WebViewZygoteInit() {
    }

    private static class WebViewZygoteServer extends com.android.internal.os.ZygoteServer {
        private WebViewZygoteServer() {
        }

        @Override // com.android.internal.os.ZygoteServer
        protected com.android.internal.os.ZygoteConnection createNewConnection(android.net.LocalSocket localSocket, java.lang.String str) throws java.io.IOException {
            return new com.android.internal.os.WebViewZygoteInit.WebViewZygoteConnection(localSocket, str);
        }
    }

    private static class WebViewZygoteConnection extends com.android.internal.os.ZygoteConnection {
        WebViewZygoteConnection(android.net.LocalSocket localSocket, java.lang.String str) throws java.io.IOException {
            super(localSocket, str);
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected void preload() {
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected boolean isPreloadComplete() {
            return true;
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected boolean canPreloadApp() {
            return true;
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected void handlePreloadApp(android.content.pm.ApplicationInfo applicationInfo) {
            android.util.Log.i(com.android.internal.os.WebViewZygoteInit.TAG, "Beginning application preload for " + applicationInfo.packageName);
            doPreload(new android.app.LoadedApk(null, applicationInfo, null, null, false, true, false).getClassLoader(), android.webkit.WebViewFactory.getWebViewLibrary(applicationInfo));
            com.android.internal.os.Zygote.allowAppFilesAcrossFork(applicationInfo);
            android.util.Log.i(com.android.internal.os.WebViewZygoteInit.TAG, "Application preload done");
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected void handlePreloadPackage(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
            android.util.Log.i(com.android.internal.os.WebViewZygoteInit.TAG, "Beginning package preload");
            java.lang.ClassLoader createAndCacheWebViewClassLoader = android.app.ApplicationLoaders.getDefault().createAndCacheWebViewClassLoader(str, str2, str4);
            for (java.lang.String str5 : android.text.TextUtils.split(str, java.io.File.pathSeparator)) {
                com.android.internal.os.Zygote.nativeAllowFileAcrossFork(str5);
            }
            doPreload(createAndCacheWebViewClassLoader, str3);
            android.util.Log.i(com.android.internal.os.WebViewZygoteInit.TAG, "Package preload done");
        }

        private void doPreload(java.lang.ClassLoader classLoader, java.lang.String str) {
            boolean z;
            android.webkit.WebViewLibraryLoader.loadNativeLibrary(classLoader, str);
            int i = 1;
            try {
                java.lang.Class<android.webkit.WebViewFactoryProvider> webViewProviderClass = android.webkit.WebViewFactory.getWebViewProviderClass(classLoader);
                java.lang.reflect.Method method = webViewProviderClass.getMethod("preloadInZygote", new java.lang.Class[0]);
                method.setAccessible(true);
                if (method.getReturnType() != java.lang.Boolean.TYPE) {
                    android.util.Log.e(com.android.internal.os.WebViewZygoteInit.TAG, "Unexpected return type: preloadInZygote must return boolean");
                    z = false;
                } else {
                    z = ((java.lang.Boolean) webViewProviderClass.getMethod("preloadInZygote", new java.lang.Class[0]).invoke(null, new java.lang.Object[0])).booleanValue();
                    if (!z) {
                        try {
                            android.util.Log.e(com.android.internal.os.WebViewZygoteInit.TAG, "preloadInZygote returned false");
                        } catch (java.lang.ReflectiveOperationException e) {
                            android.util.Log.e(com.android.internal.os.WebViewZygoteInit.TAG, "Exception while preloading package", e);
                            z = z;
                        }
                    }
                }
            } catch (java.lang.ReflectiveOperationException e2) {
                android.util.Log.e(com.android.internal.os.WebViewZygoteInit.TAG, "Exception while preloading package", e2);
                z = false;
            }
            try {
                java.io.DataOutputStream socketOutputStream = getSocketOutputStream();
                if (!z) {
                    i = 0;
                }
                socketOutputStream.writeInt(i);
            } catch (java.io.IOException e3) {
                throw new java.lang.IllegalStateException("Error writing to command socket", e3);
            }
        }
    }

    public static void main(java.lang.String[] strArr) {
        android.util.Log.i(TAG, "Starting WebViewZygoteInit");
        com.android.internal.os.ChildZygoteInit.runZygoteServer(new com.android.internal.os.WebViewZygoteInit.WebViewZygoteServer(), strArr);
    }
}
