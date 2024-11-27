package com.android.server.display.config;

/* loaded from: classes.dex */
public class BrightnessThrottlingPoint {

    @android.annotation.NonNull
    private java.math.BigDecimal brightness;

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
    public final java.math.BigDecimal getBrightness() {
        return this.brightness;
    }

    boolean hasBrightness() {
        if (this.brightness == null) {
            return false;
        }
        return true;
    }

    public final void setBrightness(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.brightness = bigDecimal;
    }

    static com.android.server.display.config.BrightnessThrottlingPoint read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.BrightnessThrottlingPoint brightnessThrottlingPoint = new com.android.server.display.config.BrightnessThrottlingPoint();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("thermalStatus")) {
                    brightnessThrottlingPoint.setThermalStatus(com.android.server.display.config.ThermalStatus.fromString(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("brightness")) {
                    brightnessThrottlingPoint.setBrightness(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("BrightnessThrottlingPoint is not closed");
        }
        return brightnessThrottlingPoint;
    }
}
