package com.android.server.display.config.layout;

/* loaded from: classes.dex */
public class Layouts {
    private java.util.List<com.android.server.display.config.layout.Layout> layout;

    public java.util.List<com.android.server.display.config.layout.Layout> getLayout() {
        if (this.layout == null) {
            this.layout = new java.util.ArrayList();
        }
        return this.layout;
    }

    static com.android.server.display.config.layout.Layouts read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.layout.Layouts layouts = new com.android.server.display.config.layout.Layouts();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("layout")) {
                    layouts.getLayout().add(com.android.server.display.config.layout.Layout.read(xmlPullParser));
                } else {
                    com.android.server.display.config.layout.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Layouts is not closed");
        }
        return layouts;
    }
}
