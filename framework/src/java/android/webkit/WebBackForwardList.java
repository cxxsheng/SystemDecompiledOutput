package android.webkit;

/* loaded from: classes4.dex */
public abstract class WebBackForwardList implements java.lang.Cloneable, java.io.Serializable {
    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public abstract android.webkit.WebBackForwardList m5732clone();

    public abstract int getCurrentIndex();

    public abstract android.webkit.WebHistoryItem getCurrentItem();

    public abstract android.webkit.WebHistoryItem getItemAtIndex(int i);

    public abstract int getSize();
}
