package com.android.server.display.config;

/* loaded from: classes.dex */
public class BrightnessThresholds {
    private com.android.server.display.config.ThresholdPoints brightnessThresholdPoints;

    @android.annotation.NonNull
    private java.math.BigDecimal minimum;

    @android.annotation.NonNull
    public final java.math.BigDecimal getMinimum() {
        return this.minimum;
    }

    boolean hasMinimum() {
        if (this.minimum == null) {
            return false;
        }
        return true;
    }

    public final void setMinimum(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.minimum = bigDecimal;
    }

    public final com.android.server.display.config.ThresholdPoints getBrightnessThresholdPoints() {
        return this.brightnessThresholdPoints;
    }

    boolean hasBrightnessThresholdPoints() {
        if (this.brightnessThresholdPoints == null) {
            return false;
        }
        return true;
    }

    public final void setBrightnessThresholdPoints(com.android.server.display.config.ThresholdPoints thresholdPoints) {
        this.brightnessThresholdPoints = thresholdPoints;
    }

    static com.android.server.display.config.BrightnessThresholds read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.BrightnessThresholds brightnessThresholds = new com.android.server.display.config.BrightnessThresholds();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("minimum")) {
                    brightnessThresholds.setMinimum(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("brightnessThresholdPoints")) {
                    brightnessThresholds.setBrightnessThresholdPoints(com.android.server.display.config.ThresholdPoints.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("BrightnessThresholds is not closed");
        }
        return brightnessThresholds;
    }
}
