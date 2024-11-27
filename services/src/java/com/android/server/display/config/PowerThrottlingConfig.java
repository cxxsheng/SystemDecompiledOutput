package com.android.server.display.config;

/* loaded from: classes.dex */
public class PowerThrottlingConfig {

    @android.annotation.NonNull
    private java.math.BigDecimal brightnessLowestCapAllowed;

    @android.annotation.NonNull
    private java.math.BigInteger pollingWindowMillis;
    private java.util.List<com.android.server.display.config.PowerThrottlingMap> powerThrottlingMap;

    @android.annotation.NonNull
    public final java.math.BigDecimal getBrightnessLowestCapAllowed() {
        return this.brightnessLowestCapAllowed;
    }

    boolean hasBrightnessLowestCapAllowed() {
        if (this.brightnessLowestCapAllowed == null) {
            return false;
        }
        return true;
    }

    public final void setBrightnessLowestCapAllowed(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.brightnessLowestCapAllowed = bigDecimal;
    }

    @android.annotation.NonNull
    public final java.math.BigInteger getPollingWindowMillis() {
        return this.pollingWindowMillis;
    }

    boolean hasPollingWindowMillis() {
        if (this.pollingWindowMillis == null) {
            return false;
        }
        return true;
    }

    public final void setPollingWindowMillis(@android.annotation.NonNull java.math.BigInteger bigInteger) {
        this.pollingWindowMillis = bigInteger;
    }

    public final java.util.List<com.android.server.display.config.PowerThrottlingMap> getPowerThrottlingMap() {
        if (this.powerThrottlingMap == null) {
            this.powerThrottlingMap = new java.util.ArrayList();
        }
        return this.powerThrottlingMap;
    }

    static com.android.server.display.config.PowerThrottlingConfig read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.PowerThrottlingConfig powerThrottlingConfig = new com.android.server.display.config.PowerThrottlingConfig();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("brightnessLowestCapAllowed")) {
                    powerThrottlingConfig.setBrightnessLowestCapAllowed(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("pollingWindowMillis")) {
                    powerThrottlingConfig.setPollingWindowMillis(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("powerThrottlingMap")) {
                    powerThrottlingConfig.getPowerThrottlingMap().add(com.android.server.display.config.PowerThrottlingMap.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("PowerThrottlingConfig is not closed");
        }
        return powerThrottlingConfig;
    }
}
