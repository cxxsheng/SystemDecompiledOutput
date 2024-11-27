package com.android.server.display.config;

/* loaded from: classes.dex */
public class RefreshRateThrottlingPoint {

    @android.annotation.NonNull
    private com.android.server.display.config.RefreshRateRange refreshRateRange;

    @android.annotation.NonNull
    private com.android.server.display.config.ThermalStatus thermalStatus;

    @android.annotation.NonNull
    public final com.android.server.display.config.ThermalStatus getThermalStatus() {
        return this.thermalStatus;
    }

    boolean hasThermalStatus() {
        if (this.thermalStatus == null) {
            return false;
        }
        return true;
    }

    public final void setThermalStatus(@android.annotation.NonNull com.android.server.display.config.ThermalStatus thermalStatus) {
        this.thermalStatus = thermalStatus;
    }

    @android.annotation.NonNull
    public final com.android.server.display.config.RefreshRateRange getRefreshRateRange() {
        return this.refreshRateRange;
    }

    boolean hasRefreshRateRange() {
        if (this.refreshRateRange == null) {
            return false;
        }
        return true;
    }

    public final void setRefreshRateRange(@android.annotation.NonNull com.android.server.display.config.RefreshRateRange refreshRateRange) {
        this.refreshRateRange = refreshRateRange;
    }

    static com.android.server.display.config.RefreshRateThrottlingPoint read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.RefreshRateThrottlingPoint refreshRateThrottlingPoint = new com.android.server.display.config.RefreshRateThrottlingPoint();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("thermalStatus")) {
                    refreshRateThrottlingPoint.setThermalStatus(com.android.server.display.config.ThermalStatus.fromString(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("refreshRateRange")) {
                    refreshRateThrottlingPoint.setRefreshRateRange(com.android.server.display.config.RefreshRateRange.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("RefreshRateThrottlingPoint is not closed");
        }
        return refreshRateThrottlingPoint;
    }
}
