package com.android.server.display.config;

/* loaded from: classes.dex */
public class DisplayBrightnessPoint {
    private java.math.BigInteger lux;
    private java.math.BigDecimal nits;

    public final java.math.BigInteger getLux() {
        return this.lux;
    }

    boolean hasLux() {
        if (this.lux == null) {
            return false;
        }
        return true;
    }

    public final void setLux(java.math.BigInteger bigInteger) {
        this.lux = bigInteger;
    }

    public final java.math.BigDecimal getNits() {
        return this.nits;
    }

    boolean hasNits() {
        if (this.nits == null) {
            return false;
        }
        return true;
    }

    public final void setNits(java.math.BigDecimal bigDecimal) {
        this.nits = bigDecimal;
    }

    static com.android.server.display.config.DisplayBrightnessPoint read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.DisplayBrightnessPoint displayBrightnessPoint = new com.android.server.display.config.DisplayBrightnessPoint();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("lux")) {
                    displayBrightnessPoint.setLux(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("nits")) {
                    displayBrightnessPoint.setNits(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("DisplayBrightnessPoint is not closed");
        }
        return displayBrightnessPoint;
    }
}
