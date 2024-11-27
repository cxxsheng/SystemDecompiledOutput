package com.android.server.display.config;

/* loaded from: classes.dex */
public class PowerThrottlingPoint {

    @android.annotation.NonNull
    private java.math.BigDecimal powerQuotaMilliWatts;

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
    public final java.math.BigDecimal getPowerQuotaMilliWatts() {
        return this.powerQuotaMilliWatts;
    }

    boolean hasPowerQuotaMilliWatts() {
        if (this.powerQuotaMilliWatts == null) {
            return false;
        }
        return true;
    }

    public final void setPowerQuotaMilliWatts(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.powerQuotaMilliWatts = bigDecimal;
    }

    static com.android.server.display.config.PowerThrottlingPoint read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.PowerThrottlingPoint powerThrottlingPoint = new com.android.server.display.config.PowerThrottlingPoint();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("thermalStatus")) {
                    powerThrottlingPoint.setThermalStatus(com.android.server.display.config.ThermalStatus.fromString(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("powerQuotaMilliWatts")) {
                    powerThrottlingPoint.setPowerQuotaMilliWatts(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("PowerThrottlingPoint is not closed");
        }
        return powerThrottlingPoint;
    }
}
