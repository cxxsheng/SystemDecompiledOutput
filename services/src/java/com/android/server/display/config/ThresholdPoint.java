package com.android.server.display.config;

/* loaded from: classes.dex */
public class ThresholdPoint {

    @android.annotation.NonNull
    private java.math.BigDecimal percentage;

    @android.annotation.NonNull
    private java.math.BigDecimal threshold;

    @android.annotation.NonNull
    public final java.math.BigDecimal getThreshold() {
        return this.threshold;
    }

    boolean hasThreshold() {
        if (this.threshold == null) {
            return false;
        }
        return true;
    }

    public final void setThreshold(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.threshold = bigDecimal;
    }

    @android.annotation.NonNull
    public final java.math.BigDecimal getPercentage() {
        return this.percentage;
    }

    boolean hasPercentage() {
        if (this.percentage == null) {
            return false;
        }
        return true;
    }

    public final void setPercentage(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.percentage = bigDecimal;
    }

    static com.android.server.display.config.ThresholdPoint read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.ThresholdPoint thresholdPoint = new com.android.server.display.config.ThresholdPoint();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("threshold")) {
                    thresholdPoint.setThreshold(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("percentage")) {
                    thresholdPoint.setPercentage(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("ThresholdPoint is not closed");
        }
        return thresholdPoint;
    }
}
