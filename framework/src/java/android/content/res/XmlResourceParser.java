package android.content.res;

/* loaded from: classes.dex */
public interface XmlResourceParser extends org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, java.lang.AutoCloseable {
    void close();

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    java.lang.String getAttributeNamespace(int i);
}
