package com.android.server.display.config;

/* loaded from: classes.dex */
public class RefreshRateZoneProfiles {
    private java.util.List<com.android.server.display.config.RefreshRateZone> refreshRateZoneProfile;

    public final java.util.List<com.android.server.display.config.RefreshRateZone> getRefreshRateZoneProfile() {
        if (this.refreshRateZoneProfile == null) {
            this.refreshRateZoneProfile = new java.util.ArrayList();
        }
        return this.refreshRateZoneProfile;
    }

    static com.android.server.display.config.RefreshRateZoneProfiles read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.RefreshRateZoneProfiles refreshRateZoneProfiles = new com.android.server.display.config.RefreshRateZoneProfiles();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("refreshRateZoneProfile")) {
                    refreshRateZoneProfiles.getRefreshRateZoneProfile().add(com.android.server.display.config.RefreshRateZone.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("RefreshRateZoneProfiles is not closed");
        }
        return refreshRateZoneProfiles;
    }
}
