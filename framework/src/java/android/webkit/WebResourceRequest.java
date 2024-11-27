package android.webkit;

/* loaded from: classes4.dex */
public interface WebResourceRequest {
    java.lang.String getMethod();

    java.util.Map<java.lang.String, java.lang.String> getRequestHeaders();

    android.net.Uri getUrl();

    boolean hasGesture();

    boolean isForMainFrame();

    boolean isRedirect();
}
