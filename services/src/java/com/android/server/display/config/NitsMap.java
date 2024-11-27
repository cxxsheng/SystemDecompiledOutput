package com.android.server.display.config;

/* loaded from: classes.dex */
public class NitsMap {
    private java.lang.String interpolation;

    @android.annotation.NonNull
    private java.util.List<com.android.server.display.config.Point> point;

    @android.annotation.NonNull
    public final java.util.List<com.android.server.display.config.Point> getPoint() {
        if (this.point == null) {
            this.point = new java.util.ArrayList();
        }
        return this.point;
    }

    public java.lang.String getInterpolation() {
        return this.interpolation;
    }

    boolean hasInterpolation() {
        if (this.interpolation == null) {
            return false;
        }
        return true;
    }

    public void setInterpolation(java.lang.String str) {
        this.interpolation = str;
    }

    static com.android.server.display.config.NitsMap read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.NitsMap nitsMap = new com.android.server.display.config.NitsMap();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "interpolation");
        if (attributeValue != null) {
            nitsMap.setInterpolation(attributeValue);
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("point")) {
                    nitsMap.getPoint().add(com.android.server.display.config.Point.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("NitsMap is not closed");
        }
        return nitsMap;
    }
}
