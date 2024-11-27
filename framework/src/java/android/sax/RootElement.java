package android.sax;

/* loaded from: classes3.dex */
public class RootElement extends android.sax.Element {
    final android.sax.RootElement.Handler handler;

    public RootElement(java.lang.String str, java.lang.String str2) {
        super(null, str, str2, 0);
        this.handler = new android.sax.RootElement.Handler();
    }

    public RootElement(java.lang.String str) {
        this("", str);
    }

    public org.xml.sax.ContentHandler getContentHandler() {
        return this.handler;
    }

    class Handler extends org.xml.sax.helpers.DefaultHandler {
        org.xml.sax.Locator locator;
        int depth = -1;
        android.sax.Element current = null;
        java.lang.StringBuilder bodyBuilder = null;

        Handler() {
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void setDocumentLocator(org.xml.sax.Locator locator) {
            this.locator = locator;
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(java.lang.String str, java.lang.String str2, java.lang.String str3, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException {
            android.sax.Children children;
            android.sax.Element element;
            int i = this.depth + 1;
            this.depth = i;
            if (i == 0) {
                startRoot(str, str2, attributes);
                return;
            }
            if (this.bodyBuilder != null) {
                throw new android.sax.BadXmlException("Encountered mixed content within text element named " + this.current + android.media.MediaMetrics.SEPARATOR, this.locator);
            }
            if (i == this.current.depth + 1 && (children = this.current.children) != null && (element = children.get(str, str2)) != null) {
                start(element, attributes);
            }
        }

        void startRoot(java.lang.String str, java.lang.String str2, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException {
            android.sax.RootElement rootElement = android.sax.RootElement.this;
            if (rootElement.uri.compareTo(str) != 0 || rootElement.localName.compareTo(str2) != 0) {
                throw new android.sax.BadXmlException("Root element name does not match. Expected: " + rootElement + ", Got: " + android.sax.Element.toString(str, str2), this.locator);
            }
            start(rootElement, attributes);
        }

        void start(android.sax.Element element, org.xml.sax.Attributes attributes) {
            this.current = element;
            if (element.startElementListener != null) {
                element.startElementListener.start(attributes);
            }
            if (element.endTextElementListener != null) {
                this.bodyBuilder = new java.lang.StringBuilder();
            }
            element.resetRequiredChildren();
            element.visited = true;
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void characters(char[] cArr, int i, int i2) throws org.xml.sax.SAXException {
            if (this.bodyBuilder != null) {
                this.bodyBuilder.append(cArr, i, i2);
            }
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(java.lang.String str, java.lang.String str2, java.lang.String str3) throws org.xml.sax.SAXException {
            android.sax.Element element = this.current;
            if (this.depth == element.depth) {
                element.checkRequiredChildren(this.locator);
                if (element.endElementListener != null) {
                    element.endElementListener.end();
                }
                if (this.bodyBuilder != null) {
                    java.lang.String sb = this.bodyBuilder.toString();
                    this.bodyBuilder = null;
                    element.endTextElementListener.end(sb);
                }
                this.current = element.parent;
            }
            this.depth--;
        }
    }
}
