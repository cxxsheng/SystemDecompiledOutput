package com.android.server.display.config;

/* loaded from: classes.dex */
public class HdrBrightnessConfig {
    private java.math.BigInteger brightnessDecreaseDebounceMillis;
    private java.math.BigInteger brightnessIncreaseDebounceMillis;

    @android.annotation.NonNull
    private com.android.server.display.config.NonNegativeFloatToFloatMap brightnessMap;
    private java.math.BigDecimal screenBrightnessRampDecrease;
    private java.math.BigDecimal screenBrightnessRampIncrease;

    @android.annotation.NonNull
    public final com.android.server.display.config.NonNegativeFloatToFloatMap getBrightnessMap() {
        return this.brightnessMap;
    }

    boolean hasBrightnessMap() {
        if (this.brightnessMap == null) {
            return false;
        }
        return true;
    }

    public final void setBrightnessMap(@android.annotation.NonNull com.android.server.display.config.NonNegativeFloatToFloatMap nonNegativeFloatToFloatMap) {
        this.brightnessMap = nonNegativeFloatToFloatMap;
    }

    public final java.math.BigInteger getBrightnessIncreaseDebounceMillis() {
        return this.brightnessIncreaseDebounceMillis;
    }

    boolean hasBrightnessIncreaseDebounceMillis() {
        if (this.brightnessIncreaseDebounceMillis == null) {
            return false;
        }
        return true;
    }

    public final void setBrightnessIncreaseDebounceMillis(java.math.BigInteger bigInteger) {
        this.brightnessIncreaseDebounceMillis = bigInteger;
    }

    public final java.math.BigInteger getBrightnessDecreaseDebounceMillis() {
        return this.brightnessDecreaseDebounceMillis;
    }

    boolean hasBrightnessDecreaseDebounceMillis() {
        if (this.brightnessDecreaseDebounceMillis == null) {
            return false;
        }
        return true;
    }

    public final void setBrightnessDecreaseDebounceMillis(java.math.BigInteger bigInteger) {
        this.brightnessDecreaseDebounceMillis = bigInteger;
    }

    public final java.math.BigDecimal getScreenBrightnessRampIncrease() {
        return this.screenBrightnessRampIncrease;
    }

    boolean hasScreenBrightnessRampIncrease() {
        if (this.screenBrightnessRampIncrease == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessRampIncrease(java.math.BigDecimal bigDecimal) {
        this.screenBrightnessRampIncrease = bigDecimal;
    }

    public final java.math.BigDecimal getScreenBrightnessRampDecrease() {
        return this.screenBrightnessRampDecrease;
    }

    boolean hasScreenBrightnessRampDecrease() {
        if (this.screenBrightnessRampDecrease == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessRampDecrease(java.math.BigDecimal bigDecimal) {
        this.screenBrightnessRampDecrease = bigDecimal;
    }

    static com.android.server.display.config.HdrBrightnessConfig read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.HdrBrightnessConfig hdrBrightnessConfig = new com.android.server.display.config.HdrBrightnessConfig();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("brightnessMap")) {
                    hdrBrightnessConfig.setBrightnessMap(com.android.server.display.config.NonNegativeFloatToFloatMap.read(xmlPullParser));
                } else if (name.equals("brightnessIncreaseDebounceMillis")) {
                    hdrBrightnessConfig.setBrightnessIncreaseDebounceMillis(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("brightnessDecreaseDebounceMillis")) {
                    hdrBrightnessConfig.setBrightnessDecreaseDebounceMillis(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("screenBrightnessRampIncrease")) {
                    hdrBrightnessConfig.setScreenBrightnessRampIncrease(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("screenBrightnessRampDecrease")) {
                    hdrBrightnessConfig.setScreenBrightnessRampDecrease(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("HdrBrightnessConfig is not closed");
        }
        return hdrBrightnessConfig;
    }
}
