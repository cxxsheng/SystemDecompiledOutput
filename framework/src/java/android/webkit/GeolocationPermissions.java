package android.webkit;

/* loaded from: classes4.dex */
public class GeolocationPermissions {

    public interface Callback {
        void invoke(java.lang.String str, boolean z, boolean z2);
    }

    public static android.webkit.GeolocationPermissions getInstance() {
        return android.webkit.WebViewFactory.getProvider().getGeolocationPermissions();
    }

    public void getOrigins(android.webkit.ValueCallback<java.util.Set<java.lang.String>> valueCallback) {
    }

    public void getAllowed(java.lang.String str, android.webkit.ValueCallback<java.lang.Boolean> valueCallback) {
    }

    public void clear(java.lang.String str) {
    }

    public void allow(java.lang.String str) {
    }

    public void clearAll() {
    }

    @android.annotation.SystemApi
    public GeolocationPermissions() {
    }
}
