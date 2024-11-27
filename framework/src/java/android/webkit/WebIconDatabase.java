package android.webkit;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public abstract class WebIconDatabase {

    @java.lang.Deprecated
    public interface IconListener {
        void onReceivedIcon(java.lang.String str, android.graphics.Bitmap bitmap);
    }

    @android.annotation.SystemApi
    public abstract void bulkRequestIconForPageUrl(android.content.ContentResolver contentResolver, java.lang.String str, android.webkit.WebIconDatabase.IconListener iconListener);

    public abstract void close();

    public abstract void open(java.lang.String str);

    public abstract void releaseIconForPageUrl(java.lang.String str);

    public abstract void removeAllIcons();

    public abstract void requestIconForPageUrl(java.lang.String str, android.webkit.WebIconDatabase.IconListener iconListener);

    public abstract void retainIconForPageUrl(java.lang.String str);

    public static android.webkit.WebIconDatabase getInstance() {
        return android.webkit.WebViewFactory.getProvider().getWebIconDatabase();
    }
}
