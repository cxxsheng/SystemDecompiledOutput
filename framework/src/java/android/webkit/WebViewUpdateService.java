package android.webkit;

@android.annotation.SystemApi
/* loaded from: classes4.dex */
public final class WebViewUpdateService {
    private WebViewUpdateService() {
    }

    public static android.webkit.WebViewProviderInfo[] getAllWebViewPackages() {
        if (android.webkit.Flags.updateServiceIpcWrapper()) {
            android.webkit.WebViewUpdateManager webViewUpdateManager = android.webkit.WebViewUpdateManager.getInstance();
            if (webViewUpdateManager == null) {
                return new android.webkit.WebViewProviderInfo[0];
            }
            return webViewUpdateManager.getAllWebViewPackages();
        }
        android.webkit.IWebViewUpdateService updateService = getUpdateService();
        if (updateService == null) {
            return new android.webkit.WebViewProviderInfo[0];
        }
        try {
            return updateService.getAllWebViewPackages();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.webkit.WebViewProviderInfo[] getValidWebViewPackages() {
        if (android.webkit.Flags.updateServiceIpcWrapper()) {
            android.webkit.WebViewUpdateManager webViewUpdateManager = android.webkit.WebViewUpdateManager.getInstance();
            if (webViewUpdateManager == null) {
                return new android.webkit.WebViewProviderInfo[0];
            }
            return webViewUpdateManager.getValidWebViewPackages();
        }
        android.webkit.IWebViewUpdateService updateService = getUpdateService();
        if (updateService == null) {
            return new android.webkit.WebViewProviderInfo[0];
        }
        try {
            return updateService.getValidWebViewPackages();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static java.lang.String getCurrentWebViewPackageName() {
        if (android.webkit.Flags.updateServiceIpcWrapper()) {
            android.webkit.WebViewUpdateManager webViewUpdateManager = android.webkit.WebViewUpdateManager.getInstance();
            if (webViewUpdateManager == null) {
                return null;
            }
            return webViewUpdateManager.getCurrentWebViewPackageName();
        }
        android.webkit.IWebViewUpdateService updateService = getUpdateService();
        if (updateService == null) {
            return null;
        }
        try {
            return updateService.getCurrentWebViewPackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static android.webkit.IWebViewUpdateService getUpdateService() {
        return android.webkit.WebViewFactory.getUpdateService();
    }
}
