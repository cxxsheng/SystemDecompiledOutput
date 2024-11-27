package android.content;

/* loaded from: classes.dex */
public class DefaultDataHandler implements android.content.ContentInsertHandler {
    private static final java.lang.String ARG = "arg";
    private static final java.lang.String COL = "col";
    private static final java.lang.String DEL = "del";
    private static final java.lang.String POSTFIX = "postfix";
    private static final java.lang.String ROW = "row";
    private static final java.lang.String SELECT = "select";
    private static final java.lang.String URI_STR = "uri";
    private android.content.ContentResolver mContentResolver;
    private java.util.Stack<android.net.Uri> mUris = new java.util.Stack<>();
    private android.content.ContentValues mValues;

    @Override // android.content.ContentInsertHandler
    public void insert(android.content.ContentResolver contentResolver, java.io.InputStream inputStream) throws java.io.IOException, org.xml.sax.SAXException {
        this.mContentResolver = contentResolver;
        android.util.Xml.parse(inputStream, android.util.Xml.Encoding.UTF_8, this);
    }

    @Override // android.content.ContentInsertHandler
    public void insert(android.content.ContentResolver contentResolver, java.lang.String str) throws org.xml.sax.SAXException {
        this.mContentResolver = contentResolver;
        android.util.Xml.parse(str, this);
    }

    private void parseRow(org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException {
        android.net.Uri lastElement;
        java.lang.String value = attributes.getValue("uri");
        if (value != null) {
            lastElement = android.net.Uri.parse(value);
            if (lastElement == null) {
                throw new org.xml.sax.SAXException("attribute " + attributes.getValue("uri") + " parsing failure");
            }
        } else if (this.mUris.size() > 0) {
            java.lang.String value2 = attributes.getValue(POSTFIX);
            if (value2 != null) {
                lastElement = android.net.Uri.withAppendedPath(this.mUris.lastElement(), value2);
            } else {
                lastElement = this.mUris.lastElement();
            }
        } else {
            throw new org.xml.sax.SAXException("attribute parsing failure");
        }
        this.mUris.push(lastElement);
    }

    private android.net.Uri insertRow() {
        android.net.Uri insert = this.mContentResolver.insert(this.mUris.lastElement(), this.mValues);
        this.mValues = null;
        return insert;
    }

    @Override // org.xml.sax.ContentHandler
    public void startElement(java.lang.String str, java.lang.String str2, java.lang.String str3, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException {
        if (ROW.equals(str2)) {
            if (this.mValues != null) {
                if (this.mUris.empty()) {
                    throw new org.xml.sax.SAXException("uri is empty");
                }
                android.net.Uri insertRow = insertRow();
                if (insertRow == null) {
                    throw new org.xml.sax.SAXException("insert to uri " + this.mUris.lastElement().toString() + " failure");
                }
                this.mUris.pop();
                this.mUris.push(insertRow);
                parseRow(attributes);
                return;
            }
            if (attributes.getLength() == 0) {
                this.mUris.push(this.mUris.lastElement());
                return;
            } else {
                parseRow(attributes);
                return;
            }
        }
        if (COL.equals(str2)) {
            int length = attributes.getLength();
            if (length != 2) {
                throw new org.xml.sax.SAXException("illegal attributes number " + length);
            }
            java.lang.String value = attributes.getValue(0);
            java.lang.String value2 = attributes.getValue(1);
            if (value != null && value.length() > 0 && value2 != null && value2.length() > 0) {
                if (this.mValues == null) {
                    this.mValues = new android.content.ContentValues();
                }
                this.mValues.put(value, value2);
                return;
            }
            throw new org.xml.sax.SAXException("illegal attributes value");
        }
        if (DEL.equals(str2)) {
            android.net.Uri parse = android.net.Uri.parse(attributes.getValue("uri"));
            if (parse == null) {
                throw new org.xml.sax.SAXException("attribute " + attributes.getValue("uri") + " parsing failure");
            }
            int length2 = attributes.getLength() - 2;
            if (length2 <= 0) {
                if (length2 == 0) {
                    this.mContentResolver.delete(parse, attributes.getValue(1), null);
                    return;
                } else {
                    this.mContentResolver.delete(parse, null, null);
                    return;
                }
            }
            java.lang.String[] strArr = new java.lang.String[length2];
            for (int i = 0; i < length2; i++) {
                strArr[i] = attributes.getValue(i + 2);
            }
            this.mContentResolver.delete(parse, attributes.getValue(1), strArr);
            return;
        }
        throw new org.xml.sax.SAXException("unknown element: " + str2);
    }

    @Override // org.xml.sax.ContentHandler
    public void endElement(java.lang.String str, java.lang.String str2, java.lang.String str3) throws org.xml.sax.SAXException {
        if (ROW.equals(str2)) {
            if (this.mUris.empty()) {
                throw new org.xml.sax.SAXException("uri mismatch");
            }
            if (this.mValues != null) {
                insertRow();
            }
            this.mUris.pop();
        }
    }

    @Override // org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws org.xml.sax.SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void endDocument() throws org.xml.sax.SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void endPrefixMapping(java.lang.String str) throws org.xml.sax.SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void ignorableWhitespace(char[] cArr, int i, int i2) throws org.xml.sax.SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void processingInstruction(java.lang.String str, java.lang.String str2) throws org.xml.sax.SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void setDocumentLocator(org.xml.sax.Locator locator) {
    }

    @Override // org.xml.sax.ContentHandler
    public void skippedEntity(java.lang.String str) throws org.xml.sax.SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void startDocument() throws org.xml.sax.SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void startPrefixMapping(java.lang.String str, java.lang.String str2) throws org.xml.sax.SAXException {
    }
}
