package android.webkit;

/* loaded from: classes4.dex */
public abstract class WebHistoryItem implements java.lang.Cloneable {
    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public abstract android.webkit.WebHistoryItem m5733clone();

    public abstract android.graphics.Bitmap getFavicon();

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract int getId();

    public abstract java.lang.String getOriginalUrl();

    public abstract java.lang.String getTitle();

    public abstract java.lang.String getUrl();
}
