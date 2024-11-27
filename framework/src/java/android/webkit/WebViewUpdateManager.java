package android.webkit;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes4.dex */
public final class WebViewUpdateManager {
    private final android.webkit.IWebViewUpdateService mService;

    public WebViewUpdateManager(android.webkit.IWebViewUpdateService iWebViewUpdateService) {
        this.mService = iWebViewUpdateService;
    }

    public static android.webkit.WebViewUpdateManager getInstance() {
        return (android.webkit.WebViewUpdateManager) android.app.SystemServiceRegistry.getSystemServiceWithNoContext(android.content.Context.WEBVIEW_UPDATE_SERVICE);
    }

    public android.webkit.WebViewProviderResponse waitForAndGetProvider() {
        try {
            return this.mService.waitForAndGetProvider();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.pm.PackageInfo getCurrentWebViewPackage() {
        try {
            return this.mService.getCurrentWebViewPackage();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.webkit.WebViewProviderInfo[] getAllWebViewPackages() {
        try {
            return this.mService.getAllWebViewPackages();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.webkit.WebViewProviderInfo[] getValidWebViewPackages() {
        try {
            return this.mService.getValidWebViewPackages();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getCurrentWebViewPackageName() {
        try {
            return this.mService.getCurrentWebViewPackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String changeProviderAndSetting(java.lang.String str) {
        try {
            return this.mService.changeProviderAndSetting(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void notifyRelroCreationCompleted() {
        try {
            this.mService.notifyRelroCreationCompleted();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.webkit.WebViewProviderInfo getDefaultWebViewPackage() {
        try {
            return this.mService.getDefaultWebViewPackage();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
