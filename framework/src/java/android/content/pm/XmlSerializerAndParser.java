package android.content.pm;

/* loaded from: classes.dex */
public interface XmlSerializerAndParser<T> {
    T createFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException;

    void writeAsXml(T t, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException;

    default void writeAsXml(T t, org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        writeAsXml((android.content.pm.XmlSerializerAndParser<T>) t, com.android.internal.util.XmlUtils.makeTyped(xmlSerializer));
    }

    default T createFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        return createFromXml(com.android.internal.util.XmlUtils.makeTyped(xmlPullParser));
    }
}
