package com.android.server.display.config;

/* loaded from: classes.dex */
public class BrightnessThrottlingMap {

    @android.annotation.NonNull
    private java.util.List<com.android.server.display.config.BrightnessThrottlingPoint> brightnessThrottlingPoint;
    private java.lang.String id;

    @android.annotation.NonNull
    public final java.util.List<com.android.server.display.config.BrightnessThrottlingPoint> getBrightnessThrottlingPoint() {
        if (this.brightnessThrottlingPoint == null) {
            this.brightnessThrottlingPoint = new java.util.ArrayList();
        }
        return this.brightnessThrottlingPoint;
    }

    public java.lang.String getId() {
        return this.id;
    }

    boolean hasId() {
        if (this.id == null) {
            return false;
        }
        return true;
    }

    public void setId(java.lang.String str) {
        this.id = str;
    }

    static com.android.server.display.config.BrightnessThrottlingMap read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.BrightnessThrottlingMap brightnessThrottlingMap = new com.android.server.display.config.BrightnessThrottlingMap();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "id");
        if (attributeValue != null) {
            brightnessThrottlingMap.setId(attributeValue);
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("brightnessThrottlingPoint")) {
                    brightnessThrottlingMap.getBrightnessThrottlingPoint().add(com.android.server.display.config.BrightnessThrottlingPoint.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("BrightnessThrottlingMap is not closed");
        }
        return brightnessThrottlingMap;
    }
}
