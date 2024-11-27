package android.webkit;

@android.annotation.SystemApi
/* loaded from: classes4.dex */
public interface PacProcessor {
    java.lang.String findProxyForUrl(java.lang.String str);

    boolean setProxyScript(java.lang.String str);

    static android.webkit.PacProcessor getInstance() {
        return android.webkit.WebViewFactory.getProvider().getPacProcessor();
    }

    static android.webkit.PacProcessor createInstance() {
        return android.webkit.WebViewFactory.getProvider().createPacProcessor();
    }

    default void release() {
        throw new java.lang.UnsupportedOperationException("Not implemented");
    }

    default void setNetwork(android.net.Network network) {
        throw new java.lang.UnsupportedOperationException("Not implemented");
    }

    default android.net.Network getNetwork() {
        throw new java.lang.UnsupportedOperationException("Not implemented");
    }
}
