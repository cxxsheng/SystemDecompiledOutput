package com.android.server.display.config;

/* loaded from: classes.dex */
public class SensorDetails {

    @android.annotation.Nullable
    private java.lang.String name;

    @android.annotation.Nullable
    private com.android.server.display.config.RefreshRateRange refreshRate;

    @android.annotation.Nullable
    private com.android.server.display.config.NonNegativeFloatToFloatMap supportedModes;

    @android.annotation.Nullable
    private java.lang.String type;

    @android.annotation.Nullable
    public final java.lang.String getType() {
        return this.type;
    }

    boolean hasType() {
        if (this.type == null) {
            return false;
        }
        return true;
    }

    public final void setType(@android.annotation.Nullable java.lang.String str) {
        this.type = str;
    }

    @android.annotation.Nullable
    public final java.lang.String getName() {
        return this.name;
    }

    boolean hasName() {
        if (this.name == null) {
            return false;
        }
        return true;
    }

    public final void setName(@android.annotation.Nullable java.lang.String str) {
        this.name = str;
    }

    @android.annotation.Nullable
    public final com.android.server.display.config.RefreshRateRange getRefreshRate() {
        return this.refreshRate;
    }

    boolean hasRefreshRate() {
        if (this.refreshRate == null) {
            return false;
        }
        return true;
    }

    public final void setRefreshRate(@android.annotation.Nullable com.android.server.display.config.RefreshRateRange refreshRateRange) {
        this.refreshRate = refreshRateRange;
    }

    @android.annotation.Nullable
    public final com.android.server.display.config.NonNegativeFloatToFloatMap getSupportedModes() {
        return this.supportedModes;
    }

    boolean hasSupportedModes() {
        if (this.supportedModes == null) {
            return false;
        }
        return true;
    }

    public final void setSupportedModes(@android.annotation.Nullable com.android.server.display.config.NonNegativeFloatToFloatMap nonNegativeFloatToFloatMap) {
        this.supportedModes = nonNegativeFloatToFloatMap;
    }

    static com.android.server.display.config.SensorDetails read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.SensorDetails sensorDetails = new com.android.server.display.config.SensorDetails();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE)) {
                    sensorDetails.setType(com.android.server.display.config.XmlParser.readText(xmlPullParser));
                } else if (name.equals("name")) {
                    sensorDetails.setName(com.android.server.display.config.XmlParser.readText(xmlPullParser));
                } else if (name.equals("refreshRate")) {
                    sensorDetails.setRefreshRate(com.android.server.display.config.RefreshRateRange.read(xmlPullParser));
                } else if (name.equals("supportedModes")) {
                    sensorDetails.setSupportedModes(com.android.server.display.config.NonNegativeFloatToFloatMap.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("SensorDetails is not closed");
        }
        return sensorDetails;
    }
}
