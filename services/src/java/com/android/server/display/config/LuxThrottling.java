package com.android.server.display.config;

/* loaded from: classes.dex */
public class LuxThrottling {

    @android.annotation.NonNull
    private java.util.List<com.android.server.display.config.BrightnessLimitMap> brightnessLimitMap;

    @android.annotation.NonNull
    public final java.util.List<com.android.server.display.config.BrightnessLimitMap> getBrightnessLimitMap() {
        if (this.brightnessLimitMap == null) {
            this.brightnessLimitMap = new java.util.ArrayList();
        }
        return this.brightnessLimitMap;
    }

    static com.android.server.display.config.LuxThrottling read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.LuxThrottling luxThrottling = new com.android.server.display.config.LuxThrottling();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("brightnessLimitMap")) {
                    luxThrottling.getBrightnessLimitMap().add(com.android.server.display.config.BrightnessLimitMap.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("LuxThrottling is not closed");
        }
        return luxThrottling;
    }
}
