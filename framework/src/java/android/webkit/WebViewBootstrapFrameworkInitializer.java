package android.webkit;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes4.dex */
public class WebViewBootstrapFrameworkInitializer {
    private WebViewBootstrapFrameworkInitializer() {
    }

    public static void registerServiceWrappers() {
        android.app.SystemServiceRegistry.registerForeverStaticService(android.content.Context.WEBVIEW_UPDATE_SERVICE, android.webkit.WebViewUpdateManager.class, new android.app.SystemServiceRegistry.StaticServiceProducerWithBinder() { // from class: android.webkit.WebViewBootstrapFrameworkInitializer$$ExternalSyntheticLambda0
            @Override // android.app.SystemServiceRegistry.StaticServiceProducerWithBinder
            public final java.lang.Object createService(android.os.IBinder iBinder) {
                return android.webkit.WebViewBootstrapFrameworkInitializer.lambda$registerServiceWrappers$0(iBinder);
            }
        });
    }

    static /* synthetic */ android.webkit.WebViewUpdateManager lambda$registerServiceWrappers$0(android.os.IBinder iBinder) {
        return new android.webkit.WebViewUpdateManager(android.webkit.IWebViewUpdateService.Stub.asInterface(iBinder));
    }
}
