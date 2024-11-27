package com.android.server.display.config;

/* loaded from: classes.dex */
public class DensityMapping {
    private java.util.List<com.android.server.display.config.Density> density;

    public java.util.List<com.android.server.display.config.Density> getDensity() {
        if (this.density == null) {
            this.density = new java.util.ArrayList();
        }
        return this.density;
    }

    static com.android.server.display.config.DensityMapping read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.DensityMapping densityMapping = new com.android.server.display.config.DensityMapping();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("density")) {
                    densityMapping.getDensity().add(com.android.server.display.config.Density.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("DensityMapping is not closed");
        }
        return densityMapping;
    }
}
