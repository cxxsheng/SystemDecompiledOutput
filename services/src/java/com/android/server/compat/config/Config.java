package com.android.server.compat.config;

/* loaded from: classes.dex */
public class Config {
    private java.util.List<com.android.server.compat.config.Change> compatChange;

    public java.util.List<com.android.server.compat.config.Change> getCompatChange() {
        if (this.compatChange == null) {
            this.compatChange = new java.util.ArrayList();
        }
        return this.compatChange;
    }

    static com.android.server.compat.config.Config read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.compat.config.Config config = new com.android.server.compat.config.Config();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("compat-change")) {
                    config.getCompatChange().add(com.android.server.compat.config.Change.read(xmlPullParser));
                } else {
                    com.android.server.compat.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Config is not closed");
        }
        return config;
    }
}
