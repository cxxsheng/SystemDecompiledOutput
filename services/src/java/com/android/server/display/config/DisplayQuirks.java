package com.android.server.display.config;

/* loaded from: classes.dex */
public class DisplayQuirks {
    private java.util.List<java.lang.String> quirk;

    public java.util.List<java.lang.String> getQuirk() {
        if (this.quirk == null) {
            this.quirk = new java.util.ArrayList();
        }
        return this.quirk;
    }

    static com.android.server.display.config.DisplayQuirks read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.DisplayQuirks displayQuirks = new com.android.server.display.config.DisplayQuirks();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("quirk")) {
                    displayQuirks.getQuirk().add(com.android.server.display.config.XmlParser.readText(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("DisplayQuirks is not closed");
        }
        return displayQuirks;
    }
}
