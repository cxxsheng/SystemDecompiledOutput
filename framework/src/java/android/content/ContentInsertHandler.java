package android.content;

/* loaded from: classes.dex */
public interface ContentInsertHandler extends org.xml.sax.ContentHandler {
    void insert(android.content.ContentResolver contentResolver, java.io.InputStream inputStream) throws java.io.IOException, org.xml.sax.SAXException;

    void insert(android.content.ContentResolver contentResolver, java.lang.String str) throws org.xml.sax.SAXException;
}
