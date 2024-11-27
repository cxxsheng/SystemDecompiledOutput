package com.android.server.display.config;

/* loaded from: classes.dex */
public class NonNegativeFloatToFloatMap {

    @android.annotation.NonNull
    private java.util.List<com.android.server.display.config.NonNegativeFloatToFloatPoint> point;

    @android.annotation.NonNull
    public final java.util.List<com.android.server.display.config.NonNegativeFloatToFloatPoint> getPoint() {
        if (this.point == null) {
            this.point = new java.util.ArrayList();
        }
        return this.point;
    }

    static com.android.server.display.config.NonNegativeFloatToFloatMap read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.NonNegativeFloatToFloatMap nonNegativeFloatToFloatMap = new com.android.server.display.config.NonNegativeFloatToFloatMap();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("point")) {
                    nonNegativeFloatToFloatMap.getPoint().add(com.android.server.display.config.NonNegativeFloatToFloatPoint.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("NonNegativeFloatToFloatMap is not closed");
        }
        return nonNegativeFloatToFloatMap;
    }
}
