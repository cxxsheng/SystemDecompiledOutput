package com.android.server.display.config;

/* loaded from: classes.dex */
public class PowerThrottlingMap {
    private java.lang.String id;

    @android.annotation.NonNull
    private java.util.List<com.android.server.display.config.PowerThrottlingPoint> powerThrottlingPoint;

    @android.annotation.NonNull
    public final java.util.List<com.android.server.display.config.PowerThrottlingPoint> getPowerThrottlingPoint() {
        if (this.powerThrottlingPoint == null) {
            this.powerThrottlingPoint = new java.util.ArrayList();
        }
        return this.powerThrottlingPoint;
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

    static com.android.server.display.config.PowerThrottlingMap read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.PowerThrottlingMap powerThrottlingMap = new com.android.server.display.config.PowerThrottlingMap();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "id");
        if (attributeValue != null) {
            powerThrottlingMap.setId(attributeValue);
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("powerThrottlingPoint")) {
                    powerThrottlingMap.getPowerThrottlingPoint().add(com.android.server.display.config.PowerThrottlingPoint.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("PowerThrottlingMap is not closed");
        }
        return powerThrottlingMap;
    }
}