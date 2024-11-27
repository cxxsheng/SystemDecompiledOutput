package android.webkit;

/* loaded from: classes4.dex */
public abstract class ServiceWorkerController {
    public abstract android.webkit.ServiceWorkerWebSettings getServiceWorkerWebSettings();

    public abstract void setServiceWorkerClient(android.webkit.ServiceWorkerClient serviceWorkerClient);

    @java.lang.Deprecated
    public ServiceWorkerController() {
    }

    public static android.webkit.ServiceWorkerController getInstance() {
        return android.webkit.WebViewFactory.getProvider().getServiceWorkerController();
    }
}
