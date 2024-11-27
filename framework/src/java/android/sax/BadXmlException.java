package android.sax;

/* loaded from: classes3.dex */
class BadXmlException extends org.xml.sax.SAXParseException {
    public BadXmlException(java.lang.String str, org.xml.sax.Locator locator) {
        super(str, locator);
    }

    @Override // org.xml.sax.SAXException, java.lang.Throwable
    public java.lang.String getMessage() {
        return "Line " + getLineNumber() + ": " + super.getMessage();
    }
}
