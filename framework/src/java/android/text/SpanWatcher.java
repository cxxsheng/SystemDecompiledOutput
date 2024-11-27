package android.text;

/* loaded from: classes3.dex */
public interface SpanWatcher extends android.text.NoCopySpan {
    void onSpanAdded(android.text.Spannable spannable, java.lang.Object obj, int i, int i2);

    void onSpanChanged(android.text.Spannable spannable, java.lang.Object obj, int i, int i2, int i3, int i4);

    void onSpanRemoved(android.text.Spannable spannable, java.lang.Object obj, int i, int i2);
}
