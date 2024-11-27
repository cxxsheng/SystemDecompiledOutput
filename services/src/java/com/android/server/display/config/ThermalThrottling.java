package com.android.server.display.config;

/* loaded from: classes.dex */
public class ThermalThrottling {
    private java.util.List<com.android.server.display.config.BrightnessThrottlingMap> brightnessThrottlingMap;
    private java.util.List<com.android.server.display.config.RefreshRateThrottlingMap> refreshRateThrottlingMap;

    public final java.util.List<com.android.server.display.config.BrightnessThrottlingMap> getBrightnessThrottlingMap() {
        if (this.brightnessThrottlingMap == null) {
            this.brightnessThrottlingMap = new java.util.ArrayList();
        }
        return this.brightnessThrottlingMap;
    }

    public final java.util.List<com.android.server.display.config.RefreshRateThrottlingMap> getRefreshRateThrottlingMap() {
        if (this.refreshRateThrottlingMap == null) {
            this.refreshRateThrottlingMap = new java.util.ArrayList();
        }
        return this.refreshRateThrottlingMap;
    }

    static com.android.server.display.config.ThermalThrottling read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.ThermalThrottling thermalThrottling = new com.android.server.display.config.ThermalThrottling();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("brightnessThrottlingMap")) {
                    thermalThrottling.getBrightnessThrottlingMap().add(com.android.server.display.config.BrightnessThrottlingMap.read(xmlPullParser));
                } else if (name.equals("refreshRateThrottlingMap")) {
                    thermalThrottling.getRefreshRateThrottlingMap().add(com.android.server.display.config.RefreshRateThrottlingMap.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("ThermalThrottling is not closed");
        }
        return thermalThrottling;
    }
}
