package android.webkit;

/* loaded from: classes4.dex */
public abstract class WebViewDatabase {
    protected static final java.lang.String LOGTAG = "webviewdatabase";

    @java.lang.Deprecated
    public abstract void clearFormData();

    public abstract void clearHttpAuthUsernamePassword();

    @java.lang.Deprecated
    public abstract void clearUsernamePassword();

    public abstract java.lang.String[] getHttpAuthUsernamePassword(java.lang.String str, java.lang.String str2);

    @java.lang.Deprecated
    public abstract boolean hasFormData();

    public abstract boolean hasHttpAuthUsernamePassword();

    @java.lang.Deprecated
    public abstract boolean hasUsernamePassword();

    public abstract void setHttpAuthUsernamePassword(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4);

    @java.lang.Deprecated
    public WebViewDatabase() {
    }

    public static android.webkit.WebViewDatabase getInstance(android.content.Context context) {
        return android.webkit.WebViewFactory.getProvider().getWebViewDatabase(context);
    }
}
