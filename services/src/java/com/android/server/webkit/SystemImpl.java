package com.android.server.webkit;

/* loaded from: classes.dex */
public class SystemImpl implements com.android.server.webkit.SystemInterface {
    private static final int PACKAGE_FLAGS = 272630976;
    private static final java.lang.String PIN_GROUP = "webview";
    private static final java.lang.String TAG = com.android.server.webkit.SystemImpl.class.getSimpleName();
    private static final java.lang.String TAG_AVAILABILITY = "availableByDefault";
    private static final java.lang.String TAG_DESCRIPTION = "description";
    private static final java.lang.String TAG_FALLBACK = "isFallback";
    private static final java.lang.String TAG_PACKAGE_NAME = "packageName";
    private static final java.lang.String TAG_SIGNATURE = "signature";
    private static final java.lang.String TAG_START = "webviewproviders";
    private static final java.lang.String TAG_WEBVIEW_PROVIDER = "webviewprovider";
    private final android.webkit.WebViewProviderInfo[] mWebViewProviderPackages;

    private static class LazyHolder {
        private static final com.android.server.webkit.SystemImpl INSTANCE = new com.android.server.webkit.SystemImpl();

        private LazyHolder() {
        }
    }

    public static com.android.server.webkit.SystemImpl getInstance() {
        return com.android.server.webkit.SystemImpl.LazyHolder.INSTANCE;
    }

    private SystemImpl() {
        android.content.res.XmlResourceParser xml;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                xml = android.app.AppGlobals.getInitialApplication().getResources().getXml(android.R.xml.config_webview_packages);
            } catch (java.lang.Throwable th) {
                th = th;
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            e = e;
        }
        try {
            try {
                com.android.internal.util.XmlUtils.beginDocument(xml, TAG_START);
                int i = 0;
                int i2 = 0;
                while (true) {
                    com.android.internal.util.XmlUtils.nextElement(xml);
                    java.lang.String name = xml.getName();
                    if (name == null) {
                        xml.close();
                        if (i == 0) {
                            throw new android.util.AndroidRuntimeException("There must be at least one WebView package that is available by default");
                        }
                        this.mWebViewProviderPackages = (android.webkit.WebViewProviderInfo[]) arrayList.toArray(new android.webkit.WebViewProviderInfo[arrayList.size()]);
                        return;
                    }
                    if (name.equals(TAG_WEBVIEW_PROVIDER)) {
                        java.lang.String attributeValue = xml.getAttributeValue(null, "packageName");
                        if (attributeValue == null) {
                            throw new android.util.AndroidRuntimeException("WebView provider in framework resources missing package name");
                        }
                        java.lang.String attributeValue2 = xml.getAttributeValue(null, TAG_DESCRIPTION);
                        if (attributeValue2 == null) {
                            throw new android.util.AndroidRuntimeException("WebView provider in framework resources missing description");
                        }
                        android.webkit.WebViewProviderInfo webViewProviderInfo = new android.webkit.WebViewProviderInfo(attributeValue, attributeValue2, "true".equals(xml.getAttributeValue(null, TAG_AVAILABILITY)), "true".equals(xml.getAttributeValue(null, TAG_FALLBACK)), readSignatures(xml));
                        if (webViewProviderInfo.isFallback) {
                            i2++;
                            if (!webViewProviderInfo.availableByDefault) {
                                throw new android.util.AndroidRuntimeException("Each WebView fallback package must be available by default.");
                            }
                            if (i2 > 1) {
                                throw new android.util.AndroidRuntimeException("There can be at most one WebView fallback package.");
                            }
                        }
                        i = webViewProviderInfo.availableByDefault ? i + 1 : i;
                        arrayList.add(webViewProviderInfo);
                    } else {
                        android.util.Log.e(TAG, "Found an element that is not a WebView provider");
                    }
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                xmlResourceParser = xml;
                if (xmlResourceParser != null) {
                    xmlResourceParser.close();
                }
                throw th;
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
            e = e2;
            xmlResourceParser = xml;
            throw new android.util.AndroidRuntimeException("Error when parsing WebView config " + e);
        }
    }

    @Override // com.android.server.webkit.SystemInterface
    public android.webkit.WebViewProviderInfo[] getWebViewPackages() {
        return this.mWebViewProviderPackages;
    }

    @Override // com.android.server.webkit.SystemInterface
    public long getFactoryPackageVersion(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        return android.app.AppGlobals.getInitialApplication().getPackageManager().getPackageInfo(str, 2097152).getLongVersionCode();
    }

    private static java.lang.String[] readSignatures(android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int depth = xmlResourceParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            if (xmlResourceParser.getName().equals(TAG_SIGNATURE)) {
                arrayList.add(xmlResourceParser.nextText());
            } else {
                android.util.Log.e(TAG, "Found an element in a webview provider that is not a signature");
            }
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
    }

    @Override // com.android.server.webkit.SystemInterface
    public int onWebViewProviderChanged(android.content.pm.PackageInfo packageInfo) {
        return android.webkit.WebViewFactory.onWebViewProviderChanged(packageInfo);
    }

    @Override // com.android.server.webkit.SystemInterface
    public java.lang.String getUserChosenWebViewProvider(android.content.Context context) {
        return android.provider.Settings.Global.getString(context.getContentResolver(), "webview_provider");
    }

    @Override // com.android.server.webkit.SystemInterface
    public void updateUserSetting(android.content.Context context, java.lang.String str) {
        android.content.ContentResolver contentResolver = context.getContentResolver();
        if (str == null) {
            str = "";
        }
        android.provider.Settings.Global.putString(contentResolver, "webview_provider", str);
    }

    @Override // com.android.server.webkit.SystemInterface
    public void killPackageDependents(java.lang.String str) {
        try {
            android.app.ActivityManager.getService().killPackageDependents(str, -1);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // com.android.server.webkit.SystemInterface
    public void enablePackageForAllUsers(android.content.Context context, java.lang.String str, boolean z) {
        java.util.Iterator it = ((android.os.UserManager) context.getSystemService("user")).getUsers().iterator();
        while (it.hasNext()) {
            enablePackageForUser(str, z, ((android.content.pm.UserInfo) it.next()).id);
        }
    }

    private void enablePackageForUser(java.lang.String str, boolean z, int i) {
        try {
            android.app.AppGlobals.getPackageManager().setApplicationEnabledSetting(str, z ? 0 : 3, 0, i, (java.lang.String) null);
        } catch (android.os.RemoteException | java.lang.IllegalArgumentException e) {
            java.lang.String str2 = TAG;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Tried to ");
            sb.append(z ? "enable " : "disable ");
            sb.append(str);
            sb.append(" for user ");
            sb.append(i);
            sb.append(": ");
            sb.append(e);
            android.util.Log.w(str2, sb.toString());
        }
    }

    @Override // com.android.server.webkit.SystemInterface
    public void installExistingPackageForAllUsers(android.content.Context context, java.lang.String str) {
        java.util.Iterator it = ((android.os.UserManager) context.getSystemService(android.os.UserManager.class)).getUsers().iterator();
        while (it.hasNext()) {
            installPackageForUser(str, ((android.content.pm.UserInfo) it.next()).id);
        }
    }

    private void installPackageForUser(java.lang.String str, int i) {
        android.app.AppGlobals.getInitialApplication().createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().getPackageInstaller().installExistingPackage(str, 0, null);
    }

    @Override // com.android.server.webkit.SystemInterface
    public boolean systemIsDebuggable() {
        return android.os.Build.IS_DEBUGGABLE && android.os.Build.IS_ENG;
    }

    @Override // com.android.server.webkit.SystemInterface
    public android.content.pm.PackageInfo getPackageInfoForProvider(android.webkit.WebViewProviderInfo webViewProviderInfo) throws android.content.pm.PackageManager.NameNotFoundException {
        return android.app.AppGlobals.getInitialApplication().getPackageManager().getPackageInfo(webViewProviderInfo.packageName, PACKAGE_FLAGS);
    }

    @Override // com.android.server.webkit.SystemInterface
    public java.util.List<android.webkit.UserPackage> getPackageInfoForProviderAllUsers(android.content.Context context, android.webkit.WebViewProviderInfo webViewProviderInfo) {
        return android.webkit.UserPackage.getPackageInfosAllUsers(context, webViewProviderInfo.packageName, PACKAGE_FLAGS);
    }

    @Override // com.android.server.webkit.SystemInterface
    public int getMultiProcessSetting(android.content.Context context) {
        if (android.webkit.Flags.updateServiceV2()) {
            throw new java.lang.IllegalStateException("getMultiProcessSetting shouldn't be called if update_service_v2 flag is set.");
        }
        return android.provider.Settings.Global.getInt(context.getContentResolver(), "webview_multiprocess", 0);
    }

    @Override // com.android.server.webkit.SystemInterface
    public void setMultiProcessSetting(android.content.Context context, int i) {
        if (android.webkit.Flags.updateServiceV2()) {
            throw new java.lang.IllegalStateException("setMultiProcessSetting shouldn't be called if update_service_v2 flag is set.");
        }
        android.provider.Settings.Global.putInt(context.getContentResolver(), "webview_multiprocess", i);
    }

    @Override // com.android.server.webkit.SystemInterface
    public void notifyZygote(boolean z) {
        if (android.webkit.Flags.updateServiceV2()) {
            throw new java.lang.IllegalStateException("notifyZygote shouldn't be called if update_service_v2 flag is set.");
        }
        android.webkit.WebViewZygote.setMultiprocessEnabled(z);
    }

    @Override // com.android.server.webkit.SystemInterface
    public void ensureZygoteStarted() {
        android.webkit.WebViewZygote.getProcess();
    }

    @Override // com.android.server.webkit.SystemInterface
    public boolean isMultiProcessDefaultEnabled() {
        return true;
    }

    @Override // com.android.server.webkit.SystemInterface
    public void pinWebviewIfRequired(android.content.pm.ApplicationInfo applicationInfo) {
        com.android.server.PinnerService pinnerService = (com.android.server.PinnerService) com.android.server.LocalServices.getService(com.android.server.PinnerService.class);
        int webviewPinQuota = pinnerService.getWebviewPinQuota();
        if (webviewPinQuota <= 0) {
            return;
        }
        pinnerService.unpinGroup(PIN_GROUP);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        boolean z = applicationInfo.metaData.getBoolean("PIN_SHARED_LIBS_FIRST", true);
        for (java.lang.String str : applicationInfo.sharedLibraryFiles) {
            arrayList.add(str);
        }
        arrayList.add(applicationInfo.sourceDir);
        if (!z) {
            java.util.Collections.reverse(arrayList);
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            java.lang.String str2 = (java.lang.String) it.next();
            if (webviewPinQuota > 0) {
                webviewPinQuota -= pinnerService.pinFile(str2, webviewPinQuota, applicationInfo, PIN_GROUP);
            } else {
                return;
            }
        }
    }
}
