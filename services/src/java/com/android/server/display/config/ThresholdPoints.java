package com.android.server.display.config;

/* loaded from: classes.dex */
public class ThresholdPoints {

    @android.annotation.NonNull
    private java.util.List<com.android.server.display.config.ThresholdPoint> brightnessThresholdPoint;

    @android.annotation.NonNull
    public final java.util.List<com.android.server.display.config.ThresholdPoint> getBrightnessThresholdPoint() {
        if (this.brightnessThresholdPoint == null) {
            this.brightnessThresholdPoint = new java.util.ArrayList();
        }
        return this.brightnessThresholdPoint;
    }

    static com.android.server.display.config.ThresholdPoints read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.ThresholdPoints thresholdPoints = new com.android.server.display.config.ThresholdPoints();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("brightnessThresholdPoint")) {
                    thresholdPoints.getBrightnessThresholdPoint().add(com.android.server.display.config.ThresholdPoint.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("ThresholdPoints is not closed");
        }
        return thresholdPoints;
    }
}
