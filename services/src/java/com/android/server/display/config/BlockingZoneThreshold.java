package com.android.server.display.config;

/* loaded from: classes.dex */
public class BlockingZoneThreshold {
    private java.util.List<com.android.server.display.config.DisplayBrightnessPoint> displayBrightnessPoint;

    public final java.util.List<com.android.server.display.config.DisplayBrightnessPoint> getDisplayBrightnessPoint() {
        if (this.displayBrightnessPoint == null) {
            this.displayBrightnessPoint = new java.util.ArrayList();
        }
        return this.displayBrightnessPoint;
    }

    static com.android.server.display.config.BlockingZoneThreshold read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.BlockingZoneThreshold blockingZoneThreshold = new com.android.server.display.config.BlockingZoneThreshold();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("displayBrightnessPoint")) {
                    blockingZoneThreshold.getDisplayBrightnessPoint().add(com.android.server.display.config.DisplayBrightnessPoint.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("BlockingZoneThreshold is not closed");
        }
        return blockingZoneThreshold;
    }
}
